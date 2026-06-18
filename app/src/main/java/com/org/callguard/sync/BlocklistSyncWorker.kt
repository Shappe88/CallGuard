package com.org.callguard.sync

import android.content.Context
import androidx.work.*
import com.org.callguard.data.db.CallGuardDatabase
import java.util.concurrent.TimeUnit

class BlocklistSyncWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val db = CallGuardDatabase.getInstance(applicationContext)
        val repo = BlocklistSyncRepository(applicationContext, db)

        return when (val result = repo.sync()) {
            is SyncResult.Updated -> Result.success()
            is SyncResult.UpToDate -> Result.success()
            is SyncResult.Rejected -> {
                // Rejected payload — not a transient error, don't retry immediately.
                Result.failure()
            }
            is SyncResult.NetworkError -> {
                // Transient — let WorkManager retry with backoff.
                Result.retry()
            }
        }
    }

    companion object {
        private const val PERIODIC_WORK_NAME = "blocklist_periodic_sync"
        private const val ONE_TIME_WORK_NAME = "blocklist_immediate_sync"

        /** Schedules recurring sync every 4 hours, with constraints requiring network. */
        fun schedulePeriodic(context: Context) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val request = PeriodicWorkRequestBuilder<BlocklistSyncWorker>(4, TimeUnit.HOURS)
                .setConstraints(constraints)
                .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 15, TimeUnit.MINUTES)
                .build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                PERIODIC_WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )
        }

        /** Triggers an immediate sync, e.g. on push notification or user "Sync Now" tap. */
        fun triggerImmediate(context: Context) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val request = OneTimeWorkRequestBuilder<BlocklistSyncWorker>()
                .setConstraints(constraints)
                .build()

            WorkManager.getInstance(context).enqueueUniqueWork(
                ONE_TIME_WORK_NAME,
                ExistingWorkPolicy.REPLACE,
                request
            )
        }
    }
}
