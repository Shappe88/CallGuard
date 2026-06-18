package com.org.callguard

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.org.callguard.sync.BlocklistSyncWorker

class CallGuardApp : Application() {
    companion object {
        const val BLOCKED_CALLS_CHANNEL_ID = "callguard_blocked_calls"
    }

    override fun onCreate() {
        super.onCreate()
        createBlockedCallsNotificationChannel()
        BlocklistSyncWorker.schedulePeriodic(this)
    }

    private fun createBlockedCallsNotificationChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return
        val channel = NotificationChannel(
            BLOCKED_CALLS_CHANNEL_ID,
            "Blocked Calls",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Alerts when CallGuard blocks a call from a known bad number"
        }
        val manager = getSystemService(NotificationManager::class.java)
        manager?.createNotificationChannel(channel)
    }
}
