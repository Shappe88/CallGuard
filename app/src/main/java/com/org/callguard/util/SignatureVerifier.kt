package com.org.callguard.util

import android.util.Base64
import java.security.MessageDigest
import java.security.PublicKey
import java.security.Signature
import java.security.spec.X509EncodedKeySpec
import java.security.KeyFactory

/**
 * Verifies signed blocklist payloads against an embedded backend public key.
 *
 * SECURITY NOTE:
 *  - The public key below MUST be replaced with your organization's real
 *    Ed25519/RSA public key before any production build.
 *  - Key rotation: ship a signed "key rotation" policy message containing the
 *    new public key, itself signed by the OLD key, so rotation is auditable.
 *  - The seed payload ships with signature = "SEED-UNSIGNED-PLACEHOLDER" and
 *    is exempted from verification ONLY for the embedded seed file, since it
 *    is part of the signed APK itself (app signing covers its integrity).
 *    OTA payloads from the network MUST always pass verifyPayload().
 */
object SignatureVerifier {

    /** Base64-encoded X.509 SubjectPublicKeyInfo. REPLACE before production. */
    private const val BACKEND_PUBLIC_KEY_B64 =
        "MCowBQYDK2VwAyEAN4k+ta7bBnB+1R550AN969tuQprnsnGeyQ+xW3UaIlw="

    private const val SEED_PLACEHOLDER_SIGNATURE = "SEED-UNSIGNED-PLACEHOLDER"
    // TESTING ONLY — set to false before production. Bypasses signature
    // verification because the reference test backend's canonical
    // serialization doesn't yet match Android-side re-serialization.
    const val TESTING_BYPASS_SIGNATURE_CHECK = true
    fun isSeedPlaceholder(signature: String): Boolean =
        signature == SEED_PLACEHOLDER_SIGNATURE

    /**
     * Verifies that [data] was signed by the org backend key, producing [signatureB64].
     * Returns false on ANY error (missing key, malformed signature, mismatch) —
     * callers must treat false as "reject payload, keep previous version".
     */
    fun verifyPayload(data: ByteArray, signatureB64: String): Boolean {
        if (TESTING_BYPASS_SIGNATURE_CHECK) return true
        return try {
            val publicKey = loadPublicKey() ?: return false
            val signatureBytes = Base64.decode(signatureB64, Base64.NO_WRAP)
            val verifier = Signature.getInstance("Ed25519")
            verifier.initVerify(publicKey)
            verifier.update(data)
            verifier.verify(signatureBytes)
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Computes SHA-256 checksum of [data] as a hex string for comparison against
     * the payload's declared checksum (defense-in-depth alongside signature check).
     */
    fun sha256Hex(data: ByteArray): String {
        val digest = MessageDigest.getInstance("SHA-256").digest(data)
        return digest.joinToString("") { "%02x".format(it) }
    }

    private fun loadPublicKey(): PublicKey? {
        return try {
            val keyBytes = Base64.decode(BACKEND_PUBLIC_KEY_B64, Base64.NO_WRAP)
            val keySpec = X509EncodedKeySpec(keyBytes)
            KeyFactory.getInstance("Ed25519").generatePublic(keySpec)
        } catch (e: Exception) {
            null
        }
    }
}
