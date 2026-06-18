package com.org.callguard.redirection

import android.net.Uri
import android.telecom.CallRedirectionService
import android.telecom.PhoneAccountHandle
import android.util.Log
import com.org.callguard.data.db.CallGuardDatabase
import com.org.callguard.domain.AuditLogger
import com.org.callguard.domain.CallDecision
import com.org.callguard.domain.CallDecisionEngine
import com.org.callguard.domain.DecisionResult
import com.org.callguard.domain.MatchedRule
import kotlinx.coroutines.*

/**
 * Evaluates OUTGOING calls before they are placed.
 *
 * CallRedirectionService callbacks (placeCallUnmodified / redirectCall /
 * cancelCall) must be called synchronously from onPlaceCall — there is no
 * officially documented async pattern, and the OS enforces a short timeout.
 * To keep this reliable, we run the (fast, local-only) decision with a
 * blocking-with-timeout wrapper. If anything is slow or fails, we place the
 * call unmodified (fail-safe ALLOW).
 */
class CallGuardRedirectionService : CallRedirectionService() {

    companion object {
        private const val TAG = "CallGuardRedirection"
    }

    override fun onPlaceCall(
        handle: Uri,
        initialPhoneAccount: PhoneAccountHandle,
        allowInteractiveResponse: Boolean
    ) {
        val rawNumber = handle.schemeSpecificPart
        val db = CallGuardDatabase.getInstance(applicationContext)
        val engine = CallDecisionEngine(db)

        val result: DecisionResult = try {
            runBlocking {
                withTimeout(CallDecisionEngine.DECISION_TIMEOUT_MS) {
                    engine.evaluate(rawNumber)
                }
            }
        } catch (e: TimeoutCancellationException) {
            Log.w(TAG, "Decision timed out — failing open (ALLOW)")
            DecisionResult(CallDecision.ALLOW, MatchedRule.FAILSAFE_ERROR, rawNumber)
        } catch (e: Exception) {
            Log.e(TAG, "Unexpected error — failing open (ALLOW)", e)
            DecisionResult(CallDecision.ALLOW, MatchedRule.FAILSAFE_ERROR, rawNumber)
        }

        when (result.decision) {
            CallDecision.BLOCK -> {
                try {
                    cancelCall()
                } catch (e: Exception) {
                    Log.e(TAG, "cancelCall failed", e)
                }
            }
            CallDecision.ALLOW -> {
                try {
                    placeCallUnmodified()
                } catch (e: Exception) {
                    Log.e(TAG, "placeCallUnmodified failed", e)
                }
            }
        }

        // Audit log fire-and-forget on a separate scope; does not block onPlaceCall.
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val currentVersion = db.blocklistVersionDao().getCurrentApplied()?.version ?: 0L
                AuditLogger.log(db, "OUT", result, currentVersion)
            } catch (e: Exception) {
                Log.e(TAG, "Audit log failed (non-blocking)", e)
            }
        }
    }
}
