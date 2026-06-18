package com.org.callguard.data.db.dao

import androidx.room.*
import com.org.callguard.data.db.entity.AuditEventEntity

@Dao
interface AuditEventDao {

    @Insert
    suspend fun insert(event: AuditEventEntity)

    @Query("SELECT * FROM audit_events WHERE uploaded = 0 ORDER BY timestamp ASC LIMIT :limit")
    suspend fun getUnuploaded(limit: Int = 200): List<AuditEventEntity>

    @Query("UPDATE audit_events SET uploaded = 1 WHERE id IN (:ids)")
    suspend fun markUploaded(ids: List<Long>)

    @Query("SELECT * FROM audit_events ORDER BY timestamp DESC LIMIT :limit")
    suspend fun getRecent(limit: Int = 100): List<AuditEventEntity>

    @Query("DELETE FROM audit_events WHERE timestamp < :cutoff")
    suspend fun purgeOlderThan(cutoff: Long)
}
