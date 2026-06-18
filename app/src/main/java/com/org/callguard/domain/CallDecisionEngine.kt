package com.org.callguard.domain

import android.util.Log
import com.org.callguard.data.db.CallGuardDatabase
import com.org.callguard.data.db.entity.PolicyKeys
import com.org.callguard.util.NumberNormalizer

/**
 * Implements the call-blocking decision tree, evaluated in strict priority order.
 * Every path that is NOT a confirmed blocklist match returns ALLOW.
 *
 * Priority order:
 *  1. Emergency numbers          -> ALLOW (hard bypass)
 *  2. Internal/org allowlist     -> ALLOW
 *  3. SOC/helpdesk/admin numbers -> ALLOW
 *  4. Active temporary override  -> ALLOW
 *  5. Valid signed blocklist hit -> BLOCK
 *  6. Anything else / any error  -> ALLOW (fail-safe)
 *
 * This class is intentionally defensive: ANY exception anywhere in the
 * evaluation results in ALLOW, never BLOCK. A live call decision must never
 * be held up or fail closed due to a software bug, corrupted data, or a
 * slow/locked database.
 */
class CallDecisionEngine(private val db: CallGuardDatabase) {

    companion object {
        private const val TAG = "CallDecisionEngine"

        /** Hard timeout for the entire decision — CallScreeningService has ~5s budget. */
        const val DECISION_TIMEOUT_MS = 4000L
    }

    suspend fun evaluate(rawNumber: String?, now: Long = System.currentTimeMillis()): DecisionResult {
        return try {
            val normalized = NumberNormalizer.normalize(rawNumber)
                ?: return DecisionResult(CallDecision.ALLOW, MatchedRule.INVALID_NUMBER, null)

            // 0. Kill switch — admin emergency disable of ALL blocking.
            val killSwitch = db.policyMetadataDao().get(PolicyKeys.KILL_SWITCH)?.value
            if (killSwitch == "true") {
                return DecisionResult(CallDecision.ALLOW, MatchedRule.KILL_SWITCH_ACTIVE, normalized)
            }

            // 1-4. Allowlist categories (emergency, internal, SOC, override) — single table,
            // category determines the matched-rule label for audit purposes.
            val allowMatch = db.allowlistedNumberDao().findActive(normalized, now)
            if (allowMatch != null) {
                val rule = when (allowMatch.category) {
                    "EMERGENCY" -> MatchedRule.EMERGENCY
                    "SOC" -> MatchedRule.SOC_HELPDESK
                    "OVERRIDE" -> MatchedRule.TEMPORARY_OVERRIDE
                    else -> MatchedRule.INTERNAL_ALLOWLIST
                }
                return DecisionResult(CallDecision.ALLOW, rule, normalized)
            }

            // 5. Blocklist match — only entries with VERIFIED (or SEED, for first-run) signature
            // status and not expired are honored.
            val blockMatch = db.blockedNumberDao().findActive(normalized)
            if (blockMatch != null) {
                val notExpired = blockMatch.expiresAt == null || blockMatch.expiresAt > now
                val signatureOk = blockMatch.signatureStatus == "VERIFIED" || blockMatch.signatureStatus == "SEED"
                if (notExpired && signatureOk) {
                    return DecisionResult(CallDecision.BLOCK, MatchedRule.BLOCKLIST_MATCH, normalized)
                }
            }

            // 6. No match -> allow.
            DecisionResult(CallDecision.ALLOW, MatchedRule.NO_MATCH_ALLOW, normalized)
        } catch (e: Exception) {
            // Absolute fail-safe: any error -> ALLOW.
            Log.e(TAG, "Decision engine error — failing open (ALLOW)", e)
            DecisionResult(CallDecision.ALLOW, MatchedRule.FAILSAFE_ERROR, NumberNormalizer.normalize(rawNumber))
        }
    }
}
