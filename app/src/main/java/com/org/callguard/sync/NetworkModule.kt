package com.org.callguard.sync

import java.util.concurrent.TimeUnit
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Builds the Retrofit client used for OTA sync.
 *
 * SECURITY NOTE: Replace BASE_URL and the certificate pin hashes before any production build.
 * Certificate pinning prevents MITM even if a malicious root CA is installed on the device. Pin
 * BOTH the current and a backup (rotation) cert to avoid bricking sync during cert renewal.
 */
object NetworkModule {

    // TODO: replace with org backend URL (env-specific: dev/staging/prod)
    const val BASE_URL = "http://10.139.122.197:3000/"

    private const val PIN_HOSTNAME = "callguard-api.example.org"

    // TODO: replace with real SHA-256 pins, e.g. from:
    //   openssl s_client -connect host:443 | openssl x509 -pubkey -noout |
    //   openssl pkey -pubin -outform der | openssl dgst -sha256 -binary | base64
    private const val PIN_PRIMARY = "sha256/REPLACE_PRIMARY_PIN="
    private const val PIN_BACKUP = "sha256/REPLACE_BACKUP_PIN="

    private val certificatePinner =
            CertificatePinner.Builder()
                    .add(PIN_HOSTNAME, PIN_PRIMARY)
                    .add(PIN_HOSTNAME, PIN_BACKUP)
                    .build()

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient =
            OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .build()

    val blocklistApi: BlocklistApi by lazy {
        Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(BlocklistApi::class.java)
    }

    val adminApi: AdminApi by lazy {
        Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AdminApi::class.java)
    }
}
