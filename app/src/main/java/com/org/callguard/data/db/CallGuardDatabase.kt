package com.org.callguard.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.org.callguard.data.db.dao.*
import com.org.callguard.data.db.entity.*
import com.org.callguard.util.SeedDataLoader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        BlockedNumberEntity::class,
        AllowlistedNumberEntity::class,
        BlocklistVersionEntity::class,
        SyncStatusEntity::class,
        PolicyMetadataEntity::class,
        AuditEventEntity::class,
        AdminMessageEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class CallGuardDatabase : RoomDatabase() {

    abstract fun blockedNumberDao(): BlockedNumberDao
    abstract fun allowlistedNumberDao(): AllowlistedNumberDao
    abstract fun blocklistVersionDao(): BlocklistVersionDao
    abstract fun syncStatusDao(): SyncStatusDao
    abstract fun policyMetadataDao(): PolicyMetadataDao
    abstract fun auditEventDao(): AuditEventDao
    abstract fun adminMessageDao(): AdminMessageDao

    companion object {
        @Volatile
        private var INSTANCE: CallGuardDatabase? = null

        fun getInstance(context: Context): CallGuardDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: build(context).also { INSTANCE = it }
            }
        }

        private fun build(context: Context): CallGuardDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                CallGuardDatabase::class.java,
                "callguard.db"
            )
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // First-run seed load: embedded blocklist + default allowlist.
                        CoroutineScope(Dispatchers.IO).launch {
                            val instance = getInstance(context)
                            SeedDataLoader.loadSeedData(context, instance)
                        }
                    }
                })
                .fallbackToDestructiveMigration() // acceptable for MVP; replace with real migrations before production
                .build()
        }
    }
}
