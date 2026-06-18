package com.org.callguard.sync;

/**
 * Builds the Retrofit client used for OTA sync.
 *
 * SECURITY NOTE: Replace BASE_URL and the certificate pin hashes before any production build.
 * Certificate pinning prevents MITM even if a malicious root CA is installed on the device. Pin
 * BOTH the current and a backup (rotation) cert to avoid bricking sync during cert renewal.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u001b\u0010\b\u001a\u00020\t8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\n\u0010\u000bR\u001b\u0010\u000e\u001a\u00020\u000f8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0012\u0010\r\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lcom/org/callguard/sync/NetworkModule;", "", "()V", "BASE_URL", "", "PIN_BACKUP", "PIN_HOSTNAME", "PIN_PRIMARY", "adminApi", "Lcom/org/callguard/sync/AdminApi;", "getAdminApi", "()Lcom/org/callguard/sync/AdminApi;", "adminApi$delegate", "Lkotlin/Lazy;", "blocklistApi", "Lcom/org/callguard/sync/BlocklistApi;", "getBlocklistApi", "()Lcom/org/callguard/sync/BlocklistApi;", "blocklistApi$delegate", "certificatePinner", "Lokhttp3/CertificatePinner;", "loggingInterceptor", "Lokhttp3/logging/HttpLoggingInterceptor;", "okHttpClient", "Lokhttp3/OkHttpClient;", "app_debug"})
public final class NetworkModule {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String BASE_URL = "http://10.139.122.197:3000/";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String PIN_HOSTNAME = "callguard-api.example.org";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String PIN_PRIMARY = "sha256/REPLACE_PRIMARY_PIN=";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String PIN_BACKUP = "sha256/REPLACE_BACKUP_PIN=";
    @org.jetbrains.annotations.NotNull()
    private static final okhttp3.CertificatePinner certificatePinner = null;
    @org.jetbrains.annotations.NotNull()
    private static final okhttp3.logging.HttpLoggingInterceptor loggingInterceptor = null;
    @org.jetbrains.annotations.NotNull()
    private static final okhttp3.OkHttpClient okHttpClient = null;
    @org.jetbrains.annotations.NotNull()
    private static final kotlin.Lazy blocklistApi$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private static final kotlin.Lazy adminApi$delegate = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.org.callguard.sync.NetworkModule INSTANCE = null;
    
    private NetworkModule() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.org.callguard.sync.BlocklistApi getBlocklistApi() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.org.callguard.sync.AdminApi getAdminApi() {
        return null;
    }
}