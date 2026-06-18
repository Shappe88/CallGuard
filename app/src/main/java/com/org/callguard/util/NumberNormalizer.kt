package com.org.callguard.util

/**
 * Normalizes phone numbers to a consistent comparable format.
 *
 * For an MVP we normalize to digits-only with a leading country code where possible,
 * approximating E.164. This avoids pulling in libphonenumber (large dependency) but
 * can be swapped for libphonenumber later for full international correctness.
 *
 * Strategy:
 *  - Strip all non-digit characters except a leading '+'.
 *  - If the number starts with '+', keep as-is (digits only after '+').
 *  - If the number is 10 digits and DEFAULT_COUNTRY_CODE is set, prefix it.
 *  - Otherwise return digits-only string unchanged (best-effort match).
 *
 * IMPORTANT: This must produce the SAME output for the same logical number
 * regardless of formatting, or blocklist matches will be missed. Keep the
 * backend's normalization logic in sync with this implementation.
 */
object NumberNormalizer {

    /** Default country calling code (without '+'), e.g. "91" for India. Configurable via policy. */
    var defaultCountryCode: String = "91"

    fun normalize(rawNumber: String?): String? {
        if (rawNumber.isNullOrBlank()) return null

        val trimmed = rawNumber.trim()
        val hasPlus = trimmed.startsWith("+")
        val digitsOnly = trimmed.filter { it.isDigit() }

        if (digitsOnly.isEmpty()) return null

        return when {
            hasPlus -> "+$digitsOnly"
            digitsOnly.length == 10 -> "+$defaultCountryCode$digitsOnly"
            digitsOnly.length > 10 && digitsOnly.startsWith(defaultCountryCode) -> "+$digitsOnly"
            else -> "+$digitsOnly" // best-effort fallback
        }
    }
}
