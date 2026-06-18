package com.org.callguard.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * A number on the centrally managed malicious/prohibited list.
 */
@Entity(tableName = "blocked_numbers")
data class BlockedNumberEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val normalizedNumber: String,      // E.164 format, e.g. +911234567890
    val displayLabel: String? = null,
    val reasonCode: String,            // e.g. FRAUD, SPAM, THREAT
    val severity: String,              // LOW, MEDIUM, HIGH
    val source: String,                // feed/source identifier
    val version: Long,                 // blocklist version this entry arrived with
    val signatureStatus: String,       // VERIFIED, PENDING, FAILED
    val active: Boolean = true,
    val createdAt: Long,
    val updatedAt: Long,
    val expiresAt: Long? = null
)
