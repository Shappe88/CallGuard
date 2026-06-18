package com.org.callguard.sync

import retrofit2.Response
import retrofit2.http.*

data class LoginRequest(val username: String, val password: String)
data class LoginResponse(val token: String, val error: String?)

data class ContactEntry(
    val id: Int,
    val normalizedNumber: String,
    val displayLabel: String?,
    val type: String,
    val reasonCode: String?,
    val severity: String?,
    val category: String?
)

interface AdminApi {
    @POST("api/v1/admin/login")
    suspend fun login(@Body req: LoginRequest): Response<LoginResponse>

    @POST("api/v1/admin/register")
    suspend fun register(@Body req: LoginRequest): Response<LoginResponse>

    @GET("api/v1/admin/numbers")
    suspend fun getNumbers(@Header("Authorization") auth: String): Response<List<ContactEntry>>

    @POST("api/v1/admin/numbers")
    suspend fun addNumber(
        @Header("Authorization") auth: String,
        @Body req: ContactEntry
    ): Response<ContactEntry>

    @DELETE("api/v1/admin/numbers/{id}")
    suspend fun deleteNumber(
        @Header("Authorization") auth: String,
        @Path("id") id: Int
    ): Response<Void>
}
