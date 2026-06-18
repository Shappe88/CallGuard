package com.org.callguard.sync;

/**
 * Backend API surface for OTA blocklist sync.
 *
 * Base URL is configured per environment (see NetworkModule). All endpoints
 * are expected over TLS with certificate pinning.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u001e\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0007J\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0003H\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0003H\u00a7@\u00a2\u0006\u0002\u0010\nJ\u001e\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00032\b\b\u0001\u0010\u000f\u001a\u00020\u0010H\u00a7@\u00a2\u0006\u0002\u0010\u0011J\u001e\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00032\b\b\u0001\u0010\u0013\u001a\u00020\u0014H\u00a7@\u00a2\u0006\u0002\u0010\u0015\u00a8\u0006\u0016"}, d2 = {"Lcom/org/callguard/sync/BlocklistApi;", "", "getDelta", "Lretrofit2/Response;", "Lcom/org/callguard/data/model/BlocklistDeltaPayload;", "since", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getFullBlocklist", "Lcom/org/callguard/data/model/BlocklistPayload;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getLatestVersion", "Lcom/org/callguard/data/model/BlocklistVersionResponse;", "postAuditTelemetry", "", "telemetry", "Lcom/org/callguard/sync/AuditTelemetryRequest;", "(Lcom/org/callguard/sync/AuditTelemetryRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "postDeviceStatus", "status", "Lcom/org/callguard/sync/DeviceStatusRequest;", "(Lcom/org/callguard/sync/DeviceStatusRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface BlocklistApi {
    
    @retrofit2.http.GET(value = "api/v1/blocklist/version")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getLatestVersion(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.org.callguard.data.model.BlocklistVersionResponse>> $completion);
    
    @retrofit2.http.GET(value = "api/v1/blocklist/delta")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getDelta(@retrofit2.http.Query(value = "since")
    long since, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.org.callguard.data.model.BlocklistDeltaPayload>> $completion);
    
    @retrofit2.http.GET(value = "api/v1/blocklist/full")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getFullBlocklist(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.org.callguard.data.model.BlocklistPayload>> $completion);
    
    @retrofit2.http.POST(value = "api/v1/device/status")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object postDeviceStatus(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.org.callguard.sync.DeviceStatusRequest status, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<kotlin.Unit>> $completion);
    
    @retrofit2.http.POST(value = "api/v1/telemetry/audit")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object postAuditTelemetry(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.org.callguard.sync.AuditTelemetryRequest telemetry, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<kotlin.Unit>> $completion);
}