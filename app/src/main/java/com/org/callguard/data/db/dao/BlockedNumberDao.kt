package com.org.callguard.data.db.dao

import androidx.room.*
import com.org.callguard.data.db.entity.BlockedNumberEntity

@Dao
interface BlockedNumberDao {

    @Query("SELECT * FROM blocked_numbers WHERE normalizedNumber = :number AND active = 1 LIMIT 1")
    suspend fun findActive(number: String): BlockedNumberEntity?

    @Query("SELECT COUNT(*) FROM blocked_numbers WHERE active = 1")
    suspend fun activeCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entries: List<BlockedNumberEntity>)

    @Query("DELETE FROM blocked_numbers")
    suspend fun deleteAll()

    @Query("DELETE FROM blocked_numbers WHERE normalizedNumber IN (:numbers)")
    suspend fun deleteByNumbers(numbers: List<String>)

    @Query("SELECT * FROM blocked_numbers")
    suspend fun getAll(): List<BlockedNumberEntity>
}
