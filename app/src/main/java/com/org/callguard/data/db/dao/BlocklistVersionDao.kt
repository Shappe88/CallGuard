package com.org.callguard.data.db.dao

import androidx.room.*
import com.org.callguard.data.db.entity.BlocklistVersionEntity

@Dao
interface BlocklistVersionDao {

    @Query("SELECT * FROM blocklist_versions WHERE status = 'APPLIED' ORDER BY version DESC LIMIT 1")
    suspend fun getCurrentApplied(): BlocklistVersionEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: BlocklistVersionEntity)

    @Query("UPDATE blocklist_versions SET status = :status, appliedAt = :appliedAt WHERE version = :version")
    suspend fun updateStatus(version: Long, status: String, appliedAt: Long?)

    @Query("SELECT * FROM blocklist_versions ORDER BY version DESC")
    suspend fun getAll(): List<BlocklistVersionEntity>
}
