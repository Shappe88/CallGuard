package com.org.callguard.data.model

import com.google.gson.annotations.SerializedName

/**
 * Shape of the seed blocklist (assets/seed_blocklist.json) and the
 * full-refresh OTA payload (GET /blocklist/full). Both use this structure
 * so the seed loader and sync engine can share parsing/apply logic.
 */
data class BlocklistPayload(
    @SerializedName("version") val version: Long,
    @SerializedName("released_at") val releasedAt: String,
    @SerializedName("signature") val signature: String,
    @SerializedName("checksum") val checksum: String,
    @SerializedName("blocked_numbers") val blockedNumbers: List<BlockedNumberDto> = emptyList(),
    @SerializedName("allowlisted_numbers") val allowlistedNumbers: List<AllowlistedNumberDto> = emptyList(),
    @SerializedName("policy") val policy: Map<String, String> = emptyMap()
)

data class BlockedNumberDto(
    @SerializedName("normalized_number") val normalizedNumber: String,
    @SerializedName("display_label") val displayLabel: String? = null,
    @SerializedName("reason_code") val reasonCode: String,
    @SerializedName("severity") val severity: String,
    @SerializedName("source") val source: String,
    @SerializedName("expires_at") val expiresAt: String? = null
)

data class AllowlistedNumberDto(
    @SerializedName("normalized_number") val normalizedNumber: String,
    @SerializedName("display_label") val displayLabel: String? = null,
    @SerializedName("category") val category: String,
    @SerializedName("source") val source: String,
    @SerializedName("expires_at") val expiresAt: String? = null
)

/**
 * Shape of a delta update payload (GET /blocklist/delta?since=N).
 */
data class BlocklistDeltaPayload(
    @SerializedName("from") val from: Long,
    @SerializedName("to") val to: Long,
    @SerializedName("signature") val signature: String,
    @SerializedName("checksum") val checksum: String,
    @SerializedName("added_blocked") val addedBlocked: List<BlockedNumberDto> = emptyList(),
    @SerializedName("removed_blocked") val removedBlocked: List<String> = emptyList(),
    @SerializedName("added_allowlisted") val addedAllowlisted: List<AllowlistedNumberDto> = emptyList(),
    @SerializedName("removed_allowlisted") val removedAllowlisted: List<String> = emptyList(),
    @SerializedName("policy") val policy: Map<String, String> = emptyMap()
)

data class BlocklistVersionResponse(
    @SerializedName("version") val version: Long,
    @SerializedName("checksum") val checksum: String,
    @SerializedName("signed_at") val signedAt: String
)
