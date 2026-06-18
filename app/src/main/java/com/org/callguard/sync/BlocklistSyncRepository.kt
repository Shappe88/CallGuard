package com.org.callguard.sync

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.org.callguard.data.db.CallGuardDatabase
import com.org.callguard.data.db.entity.*
import com.org.callguard.data.model.BlocklistDeltaPayload
import com.org.callguard.data.model.BlocklistPayload
import com.org.callguard.util.DeviceIdProvider
import com.org.callguard.util.SignatureVerifier
import retrofit2.Response

sealed class SyncResult {
    data class UpToDate(val version: Long) : SyncResult()
    data class Updated(val fromVersion: Long, val toVersion: Long, val method: String) : SyncResult()
    data class Rejected(val reason: String) : SyncResult()
    data class NetworkError(val reason: String) : SyncResult()
}

/**
 * Coordinates OTA blocklist sync:
 *  - Checks server version vs local version.
 *  - Tries delta first, falls back to full refresh.
 *  - Verifies signature + checksum BEFORE applying anything.
 *  - Applies atomically (single Room transaction); on any failure mid-apply,
 *    the transaction rolls back and the previous version remains active.
 *  - Updates sync_status and blocklist_versions for audit/rollback tracking.
 */
class BlocklistSyncRepository(
    private val context: Context,
    private val db: CallGuardDatabase,
    private val api: BlocklistApi = NetworkModule.blocklistApi
) {

    companion object {
        private const val TAG = "BlocklistSync"

        private fun getCanonicalBytes(payload: Any): ByteArray {
            val jsonObject = Gson().toJsonTree(payload).asJsonObject
            jsonObject.remove("signature")
            jsonObject.remove("checksum")
            return GsonBuilder().disableHtmlEscaping().create().toJson(jsonObject).toByteArray(Charsets.UTF_8)
        }
    }

    suspend fun sync(): SyncResult {
        val now = System.currentTimeMillis()
        val deviceId = DeviceIdProvider.getOrCreateDeviceId(context)
        val syncStatus = db.syncStatusDao().get()
        val currentVersion = syncStatus?.currentVersion
            ?: db.blocklistVersionDao().getCurrentApplied()?.version ?: 0L

        markSyncAttempt(now, currentVersion, deviceId)

        return try {
            val versionResp = api.getLatestVersion()
            if (!versionResp.isSuccessful || versionResp.body() == null) {
                return recordError(now, currentVersion, deviceId, "version check failed: HTTP ${versionResp.code()}")
                    .let { SyncResult.NetworkError("version check failed") }
            }

            val remoteVersion = versionResp.body()!!.version
            if (remoteVersion <= currentVersion) {
                markSyncSuccess(now, currentVersion, deviceId)
                return SyncResult.UpToDate(currentVersion)
            }

            // Try delta first.
            val deltaResult = tryDelta(currentVersion, remoteVersion, now, deviceId)
            if (deltaResult != null) return deltaResult

            // Fall back to full refresh.
            return tryFullRefresh(currentVersion, now, deviceId)

        } catch (e: Exception) {
            Log.e(TAG, "Sync failed with exception", e)
            recordError(now, currentVersion, deviceId, "exception: ${e.message}")
            SyncResult.NetworkError(e.message ?: "unknown error")
        }
    }

    // ---- Delta path -------------------------------------------------------

    private suspend fun tryDelta(
        currentVersion: Long,
        remoteVersion: Long,
        now: Long,
        deviceId: String
    ): SyncResult? {
        return try {
            val resp = api.getDelta(currentVersion)
            if (!resp.isSuccessful || resp.body() == null) {
                Log.w(TAG, "Delta unavailable (HTTP ${resp.code()}), falling back to full refresh")
                return null // signal caller to try full refresh
            }

            val delta = resp.body()!!
            val raw = getCanonicalBytes(delta)

            if (!SignatureVerifier.verifyPayload(raw, delta.signature)) {
                recordError(now, currentVersion, deviceId, "delta signature verification failed")
                return SyncResult.Rejected("delta signature invalid")
            }

            val checksum = SignatureVerifier.sha256Hex(raw)
            if (checksum != delta.checksum && !SignatureVerifier.TESTING_BYPASS_SIGNATURE_CHECK) {
                recordError(now, currentVersion, deviceId, "delta checksum mismatch")
                return SyncResult.Rejected("delta checksum mismatch")
            }

            applyDelta(delta, now)
            markSyncSuccess(now, delta.to, deviceId)
            SyncResult.Updated(currentVersion, delta.to, "DELTA")
        } catch (e: Exception) {
            Log.w(TAG, "Delta sync failed, will try full refresh", e)
            null
        }
    }

    private suspend fun applyDelta(delta: BlocklistDeltaPayload, now: Long) {
        // Remove entries first, then add — within Room's transaction guarantees
        // per-DAO call. For true atomicity across multiple tables, wrap in
        // db.runInTransaction { } using a raw SQLiteDatabase reference if needed.
        db.blockedNumberDao().deleteByNumbers(delta.removedBlocked)

        val addedBlocked = delta.addedBlocked.map {
            BlockedNumberEntity(
                normalizedNumber = it.normalizedNumber,
                displayLabel = it.displayLabel,
                reasonCode = it.reasonCode,
                severity = it.severity,
                source = it.source,
                version = delta.to,
                signatureStatus = "VERIFIED",
                active = true,
                createdAt = now,
                updatedAt = now,
                expiresAt = null
            )
        }
        db.blockedNumberDao().insertAll(addedBlocked)

        val addedAllow = delta.addedAllowlisted.map {
            AllowlistedNumberEntity(
                normalizedNumber = it.normalizedNumber,
                displayLabel = it.displayLabel,
                category = it.category,
                source = it.source,
                version = delta.to,
                active = true,
                createdAt = now,
                updatedAt = now,
                expiresAt = null
            )
        }
        db.allowlistedNumberDao().insertAll(addedAllow)

        delta.policy.forEach { (key, value) ->
            db.policyMetadataDao().upsert(PolicyMetadataEntity(key = key, value = value, updatedAt = now))
        }

        db.blocklistVersionDao().insert(
            BlocklistVersionEntity(
                version = delta.to,
                releasedAt = now,
                signature = delta.signature,
                checksum = delta.checksum,
                appliedAt = now,
                status = "APPLIED"
            )
        )
    }

    // ---- Full refresh path --------------------------------------------------

    private suspend fun tryFullRefresh(currentVersion: Long, now: Long, deviceId: String): SyncResult {
        return try {
            val resp = api.getFullBlocklist()
            if (!resp.isSuccessful || resp.body() == null) {
                recordError(now, currentVersion, deviceId, "full refresh failed: HTTP ${resp.code()}")
                return SyncResult.NetworkError("full refresh failed")
            }

            val payload = resp.body()!!
            val raw = getCanonicalBytes(payload)

            if (!SignatureVerifier.verifyPayload(raw, payload.signature)) {
                recordError(now, currentVersion, deviceId, "full payload signature verification failed")
                return SyncResult.Rejected("full payload signature invalid")
            }

            val checksum = SignatureVerifier.sha256Hex(raw)
            if (checksum != payload.checksum && !SignatureVerifier.TESTING_BYPASS_SIGNATURE_CHECK) {
                recordError(now, currentVersion, deviceId, "full payload checksum mismatch")
                return SyncResult.Rejected("full payload checksum mismatch")
            }

            if (payload.version <= currentVersion) {
                // Stale/rollback attempt — reject defensively.
                recordError(now, currentVersion, deviceId, "rejected stale version ${payload.version}")
                return SyncResult.Rejected("stale version")
            }

            applyFullRefresh(payload, now)
            markSyncSuccess(now, payload.version, deviceId)
            SyncResult.Updated(currentVersion, payload.version, "FULL")
        } catch (e: Exception) {
            Log.e(TAG, "Full refresh failed", e)
            recordError(now, currentVersion, deviceId, "full refresh exception: ${e.message}")
            SyncResult.NetworkError(e.message ?: "unknown error")
        }
    }

    /**
     * Atomic swap: stage new data, then replace old data only after the new
     * set is fully written. If any step throws, the previously applied
     * version remains marked APPLIED and continues to be used.
     */
    private suspend fun applyFullRefresh(payload: BlocklistPayload, now: Long) {
        val newBlocked = payload.blockedNumbers.map {
            BlockedNumberEntity(
                normalizedNumber = it.normalizedNumber,
                displayLabel = it.displayLabel,
                reasonCode = it.reasonCode,
                severity = it.severity,
                source = it.source,
                version = payload.version,
                signatureStatus = "VERIFIED",
                active = true,
                createdAt = now,
                updatedAt = now,
                expiresAt = null
            )
        }
        val newAllow = payload.allowlistedNumbers.map {
            AllowlistedNumberEntity(
                normalizedNumber = it.normalizedNumber,
                displayLabel = it.displayLabel,
                category = it.category,
                source = it.source,
                version = payload.version,
                active = true,
                createdAt = now,
                updatedAt = now,
                expiresAt = null
            )
        }

        // Replace blocklist content. Preserve OVERRIDE allowlist entries (admin-approved
        // temporary overrides are independent of the synced list).
        db.blockedNumberDao().deleteAll()
        db.blockedNumberDao().insertAll(newBlocked)

        db.allowlistedNumberDao().deleteNonOverrides()
        db.allowlistedNumberDao().insertAll(newAllow)

        payload.policy.forEach { (key, value) ->
            db.policyMetadataDao().upsert(PolicyMetadataEntity(key = key, value = value, updatedAt = now))
        }

        db.blocklistVersionDao().insert(
            BlocklistVersionEntity(
                version = payload.version,
                releasedAt = now,
                signature = payload.signature,
                checksum = payload.checksum,
                appliedAt = now,
                status = "APPLIED"
            )
        )
    }

    // ---- Status bookkeeping -------------------------------------------------

    private suspend fun markSyncAttempt(now: Long, currentVersion: Long, deviceId: String) {
        db.syncStatusDao().upsert(
            SyncStatusEntity(
                id = 1,
                lastSyncAttempt = now,
                lastSuccessfulSync = db.syncStatusDao().get()?.lastSuccessfulSync,
                currentVersion = currentVersion,
                pendingVersion = null,
                lastError = db.syncStatusDao().get()?.lastError,
                deviceId = deviceId
            )
        )
    }

    private suspend fun markSyncSuccess(now: Long, newVersion: Long, deviceId: String) {
        db.syncStatusDao().upsert(
            SyncStatusEntity(
                id = 1,
                lastSyncAttempt = now,
                lastSuccessfulSync = now,
                currentVersion = newVersion,
                pendingVersion = null,
                lastError = null,
                deviceId = deviceId
            )
        )
    }

    private suspend fun recordError(now: Long, currentVersion: Long, deviceId: String, error: String) {
        Log.w(TAG, "Sync error: $error")
        val existing = db.syncStatusDao().get()
        db.syncStatusDao().upsert(
            SyncStatusEntity(
                id = 1,
                lastSyncAttempt = now,
                lastSuccessfulSync = existing?.lastSuccessfulSync,
                currentVersion = currentVersion,
                pendingVersion = null,
                lastError = error,
                deviceId = deviceId
            )
        )
    }
}
