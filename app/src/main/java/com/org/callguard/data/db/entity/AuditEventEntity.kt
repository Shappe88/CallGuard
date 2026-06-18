package com.org.callguard.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "audit_events")
data class AuditEventEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val timestamp: Long,
    val direction: String,     // IN, OUT
    val normalizedNumber: String,
    val decision: String,      // ALLOW, BLOCK
    val matchedRule: String,   // e.g. EMERGENCY, INTERNAL_ALLOWLIST, BLOCKLIST_MATCH, FAILSAFE_ALLOW
    val blocklistVersion: Long,
    val uploaded: Boolean = false
)
