package com.org.callguard.sync

import com.org.callguard.data.model.BlocklistDeltaPayload
import com.org.callguard.data.model.BlocklistPayload
import com.org.callguard.data.model.BlocklistVersionResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Backend API surface for OTA blocklist sync.
 *
 * Base URL is configured per environment (see NetworkModule). All endpoints
 * are expected over TLS with certificate pinning.
 */
interface BlocklistApi {

    @GET("api/v1/blocklist/version")
    suspend fun getLatestVersion(): Response<BlocklistVersionResponse>

    @GET("api/v1/blocklist/delta")
    suspend fun getDelta(@Query("since") since: Long): Response<BlocklistDeltaPayload>

    @GET("api/v1/blocklist/full")
    suspend fun getFullBlocklist(): Response<BlocklistPayload>

    @POST("api/v1/device/status")
    suspend fun postDeviceStatus(@Body status: DeviceStatusRequest): Response<Unit>

    @POST("api/v1/telemetry/audit")
    suspend fun postAuditTelemetry(@Body telemetry: AuditTelemetryRequest): Response<Unit>
}

data class DeviceStatusRequest(
    val deviceId: String,
    val appVersion: String,
    val currentBlocklistVersion: Long,
    val lastSync: Long?,
    val roleStatus: String
)

data class AuditTelemetryRequest(
    val deviceId: String,
    val events: List<AuditEventDto>
)

data class AuditEventDto(
    val timestamp: Long,
    val direction: String,
    val decision: String,
    val matchedRule: String,
    val blocklistVersion: Long
    // Note: normalizedNumber intentionally NOT included in telemetry by default.
    // Only enable per org policy (see Section 8 of architecture doc).
)
