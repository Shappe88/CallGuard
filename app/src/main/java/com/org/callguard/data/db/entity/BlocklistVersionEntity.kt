package com.org.callguard.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "blocklist_versions")
data class BlocklistVersionEntity(
    @PrimaryKey
    val version: Long,
    val releasedAt: Long,
    val signature: String,
    val checksum: String,
    val appliedAt: Long? = null,
    val status: String                 // APPLIED, REJECTED, PENDING
)
