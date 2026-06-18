package com.org.callguard.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Single-row table (id always = 1) tracking the device's sync state.
 */
@Entity(tableName = "sync_status")
data class SyncStatusEntity(
    @PrimaryKey
    val id: Int = 1,
    val lastSyncAttempt: Long? = null,
    val lastSuccessfulSync: Long? = null,
    val currentVersion: Long = 0,
    val pendingVersion: Long? = null,
    val lastError: String? = null,
    val deviceId: String
)
