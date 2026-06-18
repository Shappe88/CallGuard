package com.org.callguard.data.db.entity

import androidx.room.Entity

/**
 * Key/value policy flags pushed from the backend, e.g. kill_switch, max_offline_days.
 */
@Entity(tableName = "policy_metadata", primaryKeys = ["key"])
data class PolicyMetadataEntity(
    val key: String,
    val value: String,
    val updatedAt: Long,
    val signature: String? = null
)

object PolicyKeys {
    const val KILL_SWITCH = "kill_switch"              // "true"/"false" - disables all blocking
    const val MAX_OFFLINE_DAYS = "max_offline_days"    // integer string
}
