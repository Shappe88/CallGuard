package com.org.callguard.domain

import android.util.Log
import com.org.callguard.data.db.CallGuardDatabase
import com.org.callguard.data.db.entity.AuditEventEntity

object AuditLogger {

    private const val TAG = "AuditLogger"

    /**
     * Logs a call decision. This must never throw or block the call path —
     * any failure here is swallowed and only logged to logcat.
     */
    suspend fun log(
        db: CallGuardDatabase,
        direction: String, // "IN" or "OUT"
        result: DecisionResult,
        currentBlocklistVersion: Long
    ) {
        try {
            db.auditEventDao().insert(
                AuditEventEntity(
                    timestamp = System.currentTimeMillis(),
                    direction = direction,
                    normalizedNumber = result.normalizedNumber ?: "UNKNOWN",
                    decision = result.decision.name,
                    matchedRule = result.matchedRule.name,
                    blocklistVersion = currentBlocklistVersion,
                    uploaded = false
                )
            )
        } catch (e: Exception) {
            Log.e(TAG, "Failed to write audit event", e)
        }
    }
}
