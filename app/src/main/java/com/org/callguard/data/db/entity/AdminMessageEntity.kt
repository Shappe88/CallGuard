package com.org.callguard.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "admin_messages")
data class AdminMessageEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val body: String,
    val severity: String,      // INFO, WARNING, CRITICAL
    val issuedAt: Long,
    val expiresAt: Long? = null,
    val acknowledged: Boolean = false
)
