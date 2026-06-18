package com.org.callguard.data.db.dao

import androidx.room.*
import com.org.callguard.data.db.entity.PolicyMetadataEntity

@Dao
interface PolicyMetadataDao {

    @Query("SELECT * FROM policy_metadata WHERE `key` = :key LIMIT 1")
    suspend fun get(key: String): PolicyMetadataEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entries: List<PolicyMetadataEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entry: PolicyMetadataEntity)

    @Query("SELECT * FROM policy_metadata")
    suspend fun getAll(): List<PolicyMetadataEntity>
}
