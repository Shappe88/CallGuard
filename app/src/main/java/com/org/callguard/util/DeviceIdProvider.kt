package com.org.callguard.util

import android.content.Context
import java.util.UUID

/**
 * Provides a stable, locally-generated device identifier used for sync/audit
 * correlation. This is NOT the hardware Android ID — it's a random UUID
 * generated on first run and stored in app prefs, so it carries no PII and
 * can be reset by reinstalling.
 */
object DeviceIdProvider {

    private const val PREFS = "callguard_prefs"
    private const val KEY_DEVICE_ID = "device_id"

    fun getOrCreateDeviceId(context: Context): String {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        prefs.getString(KEY_DEVICE_ID, null)?.let { return it }

        val newId = UUID.randomUUID().toString()
        prefs.edit().putString(KEY_DEVICE_ID, newId).apply()
        return newId
    }
}
