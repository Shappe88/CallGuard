package com.org.callguard.screening

import android.telecom.Call
import android.telecom.CallScreeningService
import android.util.Log
import com.org.callguard.data.db.CallGuardDatabase
import com.org.callguard.domain.AuditLogger
import com.org.callguard.domain.CallDecision
import com.org.callguard.domain.CallDecisionEngine
import com.org.callguard.domain.DecisionResult
import com.org.callguard.domain.MatchedRule
import com.org.callguard.CallGuardApp
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.coroutines.*

class CallGuardScreeningService : CallScreeningService() {

    companion object {
        private const val TAG = "CallGuardScreening"
    }

    override fun onScreenCall(callDetails: Call.Details) {
        val rawNumber = callDetails.handle?.schemeSpecificPart
        Log.i(TAG, "onScreenCall invoked for number: $rawNumber")

        // CRITICAL FIX: We MUST use runBlocking instead of serviceScope.launch.
        // OPPO/ColorOS will instantly freeze the process if the main thread goes idle while the screen is off.
        // Blocking the main thread here forces the OS to keep the app awake just long enough to check the database.
        runBlocking(Dispatchers.IO) {
            val db = CallGuardDatabase.getInstance(applicationContext)
            val engine = CallDecisionEngine(db)

            val result: DecisionResult = try {
                withTimeout(CallDecisionEngine.DECISION_TIMEOUT_MS) {
                    engine.evaluate(rawNumber)
                }
            } catch (e: TimeoutCancellationException) {
                Log.w(TAG, "Decision timed out — failing open (ALLOW)")
                DecisionResult(CallDecision.ALLOW, MatchedRule.FAILSAFE_ERROR, rawNumber)
            } catch (e: Exception) {
                Log.e(TAG, "Unexpected error — failing open (ALLOW)", e)
                DecisionResult(CallDecision.ALLOW, MatchedRule.FAILSAFE_ERROR, rawNumber)
            }

            respond(callDetails, result)

            try {
                val currentVersion = db.blocklistVersionDao().getCurrentApplied()?.version ?: 0L
                AuditLogger.log(db, "IN", result, currentVersion)
            } catch (e: Exception) {
                Log.e(TAG, "Audit log failed (non-blocking)", e)
            }
        }
    }

    private fun respond(callDetails: Call.Details, result: DecisionResult) {
        val response = if (result.decision == CallDecision.BLOCK) {
            CallResponse.Builder()
                .setDisallowCall(true)
                .setRejectCall(true)
                .setSkipNotification(false)
                .setSkipCallLog(false)
                .build()
        } else {
            CallResponse.Builder()
                .setDisallowCall(false)
                .setRejectCall(false)
                .build()
        }

        try {
            respondToCall(callDetails, response)
            Log.i(TAG, "Successfully responded to Telecom for ${result.normalizedNumber} with decision: ${result.decision}")
            
            if (result.decision == CallDecision.BLOCK) {
                postBlockedCallNotification(result)
            }
        } catch (e: Exception) {
            Log.e(TAG, "respondToCall failed — call proceeds via OS default", e)
        }
    }

    private fun postBlockedCallNotification(result: DecisionResult) {
        try {
            val notification = NotificationCompat.Builder(applicationContext, CallGuardApp.BLOCKED_CALLS_CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentTitle("Blocked malicious number attempted to call")
                .setContentText(result.normalizedNumber ?: "Unknown number")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .build()
            NotificationManagerCompat.from(applicationContext)
                .notify(System.currentTimeMillis().toInt(), notification)
            Log.i(TAG, "Notification posted successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to post blocked-call notification (non-blocking)", e)
        }
    }
}
