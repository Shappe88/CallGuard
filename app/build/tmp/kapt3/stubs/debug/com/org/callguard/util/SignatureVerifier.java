package com.org.callguard.util;

/**
 * Verifies signed blocklist payloads against an embedded backend public key.
 *
 * SECURITY NOTE:
 * - The public key below MUST be replaced with your organization's real
 *   Ed25519/RSA public key before any production build.
 * - Key rotation: ship a signed "key rotation" policy message containing the
 *   new public key, itself signed by the OLD key, so rotation is auditable.
 * - The seed payload ships with signature = "SEED-UNSIGNED-PLACEHOLDER" and
 *   is exempted from verification ONLY for the embedded seed file, since it
 *   is part of the signed APK itself (app signing covers its integrity).
 *   OTA payloads from the network MUST always pass verifyPayload().
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0004J\n\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0002J\u000e\u0010\f\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u000eJ\u0016\u0010\u000f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2 = {"Lcom/org/callguard/util/SignatureVerifier;", "", "()V", "BACKEND_PUBLIC_KEY_B64", "", "SEED_PLACEHOLDER_SIGNATURE", "TESTING_BYPASS_SIGNATURE_CHECK", "", "isSeedPlaceholder", "signature", "loadPublicKey", "Ljava/security/PublicKey;", "sha256Hex", "data", "", "verifyPayload", "signatureB64", "app_debug"})
public final class SignatureVerifier {
    
    /**
     * Base64-encoded X.509 SubjectPublicKeyInfo. REPLACE before production.
     */
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String BACKEND_PUBLIC_KEY_B64 = "MCowBQYDK2VwAyEAN4k+ta7bBnB+1R550AN969tuQprnsnGeyQ+xW3UaIlw=";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String SEED_PLACEHOLDER_SIGNATURE = "SEED-UNSIGNED-PLACEHOLDER";
    public static final boolean TESTING_BYPASS_SIGNATURE_CHECK = true;
    @org.jetbrains.annotations.NotNull()
    public static final com.org.callguard.util.SignatureVerifier INSTANCE = null;
    
    private SignatureVerifier() {
        super();
    }
    
    public final boolean isSeedPlaceholder(@org.jetbrains.annotations.NotNull()
    java.lang.String signature) {
        return false;
    }
    
    /**
     * Verifies that [data] was signed by the org backend key, producing [signatureB64].
     * Returns false on ANY error (missing key, malformed signature, mismatch) —
     * callers must treat false as "reject payload, keep previous version".
     */
    public final boolean verifyPayload(@org.jetbrains.annotations.NotNull()
    byte[] data, @org.jetbrains.annotations.NotNull()
    java.lang.String signatureB64) {
        return false;
    }
    
    /**
     * Computes SHA-256 checksum of [data] as a hex string for comparison against
     * the payload's declared checksum (defense-in-depth alongside signature check).
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String sha256Hex(@org.jetbrains.annotations.NotNull()
    byte[] data) {
        return null;
    }
    
    private final java.security.PublicKey loadPublicKey() {
        return null;
    }
}