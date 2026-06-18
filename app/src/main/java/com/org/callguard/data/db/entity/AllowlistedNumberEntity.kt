package com.org.callguard.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Numbers that must always be allowed: internal/org numbers, emergency numbers,
 * SOC/helpdesk numbers, and approved temporary overrides.
 */
@Entity(tableName = "allowlisted_numbers")
data class AllowlistedNumberEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val normalizedNumber: String,
    val displayLabel: String? = null,
    val category: String,              // EMERGENCY, INTERNAL, SOC, OVERRIDE
    val source: String,
    val approvedBy: String? = null,    // admin identity for OVERRIDE entries
    val version: Long,
    val active: Boolean = true,
    val createdAt: Long,
    val updatedAt: Long,
    val expiresAt: Long? = null        // used for temporary overrides
)
