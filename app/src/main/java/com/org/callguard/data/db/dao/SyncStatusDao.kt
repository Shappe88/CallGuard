package com.org.callguard.data.db.dao

import androidx.room.*
import com.org.callguard.data.db.entity.SyncStatusEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SyncStatusDao {

    @Query("SELECT * FROM sync_status WHERE id = 1")
    suspend fun get(): SyncStatusEntity?

    @Query("SELECT * FROM sync_status WHERE id = 1")
    fun observe(): Flow<SyncStatusEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: SyncStatusEntity)
}
