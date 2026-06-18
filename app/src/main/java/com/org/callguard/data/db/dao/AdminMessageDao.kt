package com.org.callguard.data.db.dao

import androidx.room.*
import com.org.callguard.data.db.entity.AdminMessageEntity

@Dao
interface AdminMessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(messages: List<AdminMessageEntity>)

    @Query("SELECT * FROM admin_messages WHERE acknowledged = 0 AND (expiresAt IS NULL OR expiresAt > :now) ORDER BY issuedAt DESC")
    suspend fun getActive(now: Long): List<AdminMessageEntity>

    @Query("UPDATE admin_messages SET acknowledged = 1 WHERE id = :id")
    suspend fun acknowledge(id: String)
}
