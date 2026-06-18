package com.org.callguard.util

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.org.callguard.data.db.CallGuardDatabase
import com.org.callguard.data.db.entity.*
import com.org.callguard.data.model.BlocklistPayload

/**
 * Loads the embedded seed_blocklist.json (bundled in app assets) into the
 * local database on first run. This guarantees the app has a usable
 * blocklist/allowlist immediately after install, before any network sync.
 */
object SeedDataLoader {

    private const val TAG = "SeedDataLoader"
    private const val SEED_ASSET = "seed_blocklist.json"

    suspend fun loadSeedData(context: Context, db: CallGuardDatabase) {
        try {
            val json = context.assets.open(SEED_ASSET).bufferedReader().use { it.readText() }
            val payload = Gson().fromJson(json, BlocklistPayload::class.java)
            val now = System.currentTimeMillis()

            // Seed payload is part of the signed APK; not verified against network key,
            // but we record its declared signature status for audit purposes.
            val signatureStatus = if (SignatureVerifier.isSeedPlaceholder(payload.signature)) {
                "SEED"
            } else {
                "VERIFIED"
            }

            val blocked = payload.blockedNumbers.map {
                BlockedNumberEntity(
                    normalizedNumber = it.normalizedNumber,
                    displayLabel = it.displayLabel,
                    reasonCode = it.reasonCode,
                    severity = it.severity,
                    source = it.source,
                    version = payload.version,
                    signatureStatus = signatureStatus,
                    active = true,
                    createdAt = now,
                    updatedAt = now,
                    expiresAt = null
                )
            }
            db.blockedNumberDao().insertAll(blocked)

            val allowlisted = payload.allowlistedNumbers.map {
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
            db.allowlistedNumberDao().insertAll(allowlisted)

            payload.policy.forEach { (key, value) ->
                db.policyMetadataDao().upsert(
                    PolicyMetadataEntity(key = key, value = value, updatedAt = now)
                )
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

            db.syncStatusDao().upsert(
                SyncStatusEntity(
                    id = 1,
                    lastSyncAttempt = null,
                    lastSuccessfulSync = null,
                    currentVersion = payload.version,
                    pendingVersion = null,
                    lastError = null,
                    deviceId = DeviceIdProvider.getOrCreateDeviceId(context)
                )
            )

            Log.i(TAG, "Seed blocklist v${payload.version} loaded: " +
                    "${blocked.size} blocked, ${allowlisted.size} allowlisted entries")
        } catch (e: Exception) {
            // If seed load fails for any reason, the app still functions —
            // decision logic fails open (ALLOW) when no data is present.
            Log.e(TAG, "Failed to load seed blocklist", e)
        }
    }
}
