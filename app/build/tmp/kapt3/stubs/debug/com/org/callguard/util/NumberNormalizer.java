package com.org.callguard.util;

/**
 * Normalizes phone numbers to a consistent comparable format.
 *
 * For an MVP we normalize to digits-only with a leading country code where possible,
 * approximating E.164. This avoids pulling in libphonenumber (large dependency) but
 * can be swapped for libphonenumber later for full international correctness.
 *
 * Strategy:
 * - Strip all non-digit characters except a leading '+'.
 * - If the number starts with '+', keep as-is (digits only after '+').
 * - If the number is 10 digits and DEFAULT_COUNTRY_CODE is set, prefix it.
 * - Otherwise return digits-only string unchanged (best-effort match).
 *
 * IMPORTANT: This must produce the SAME output for the same logical number
 * regardless of formatting, or blocklist matches will be missed. Keep the
 * backend's normalization logic in sync with this implementation.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\t\u001a\u0004\u0018\u00010\u00042\b\u0010\n\u001a\u0004\u0018\u00010\u0004R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b\u00a8\u0006\u000b"}, d2 = {"Lcom/org/callguard/util/NumberNormalizer;", "", "()V", "defaultCountryCode", "", "getDefaultCountryCode", "()Ljava/lang/String;", "setDefaultCountryCode", "(Ljava/lang/String;)V", "normalize", "rawNumber", "app_debug"})
public final class NumberNormalizer {
    
    /**
     * Default country calling code (without '+'), e.g. "91" for India. Configurable via policy.
     */
    @org.jetbrains.annotations.NotNull()
    private static java.lang.String defaultCountryCode = "91";
    @org.jetbrains.annotations.NotNull()
    public static final com.org.callguard.util.NumberNormalizer INSTANCE = null;
    
    private NumberNormalizer() {
        super();
    }
    
    /**
     * Default country calling code (without '+'), e.g. "91" for India. Configurable via policy.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDefaultCountryCode() {
        return null;
    }
    
    /**
     * Default country calling code (without '+'), e.g. "91" for India. Configurable via policy.
     */
    public final void setDefaultCountryCode(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String normalize(@org.jetbrains.annotations.Nullable()
    java.lang.String rawNumber) {
        return null;
    }
}