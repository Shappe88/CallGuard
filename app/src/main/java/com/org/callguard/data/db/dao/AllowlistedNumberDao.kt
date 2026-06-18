package com.org.callguard.data.db.dao

import androidx.room.*
import com.org.callguard.data.db.entity.AllowlistedNumberEntity

@Dao
interface AllowlistedNumberDao {

    /**
     * Returns an active, non-expired allowlist match (any category), if present.
     */
    @Query(
        """
        SELECT * FROM allowlisted_numbers
        WHERE normalizedNumber = :number
          AND active = 1
          AND (expiresAt IS NULL OR expiresAt > :now)
        LIMIT 1
        """
    )
    suspend fun findActive(number: String, now: Long): AllowlistedNumberEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entries: List<AllowlistedNumberEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: AllowlistedNumberEntity)

    @Query("DELETE FROM allowlisted_numbers WHERE category != 'OVERRIDE'")
    suspend fun deleteNonOverrides()

    @Query("DELETE FROM allowlisted_numbers WHERE category = 'OVERRIDE' AND expiresAt <= :now")
    suspend fun purgeExpiredOverrides(now: Long)

    @Query("SELECT * FROM allowlisted_numbers")
    suspend fun getAll(): List<AllowlistedNumberEntity>
}
