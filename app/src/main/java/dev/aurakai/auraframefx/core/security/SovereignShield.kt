package dev.aurakai.auraframefx.core.security

import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 🛡️ SOVEREIGN SHIELD — Unified Security Engine
 *
 * The primary security service for ReGenesis.
 * Consolidates all domain-specific encryption logic into a single high-integrity gate.
 * Handles Keystore management and legacy data migration.
 */
@Singleton
class SovereignShield @Inject constructor(
    private val keystoreManager: KeystoreManager
) : EncryptionManager {

    /**
     * Encrypts data using the Sovereign master key.
     */
    override fun encrypt(data: ByteArray): ByteArray {
        if (data.isEmpty()) return data
        return try {
            keystoreManager.encrypt(data)
        } catch (e: Exception) {
            Timber.e(e, "SovereignShield: Encryption failed")
            throw SecurityException("Security breach: encryption failure", e)
        }
    }

    /**
     * Decrypts data using the Sovereign master key.
     */
    override fun decrypt(data: ByteArray): ByteArray {
        if (data.isEmpty()) return data
        return try {
            keystoreManager.decrypt(data)
        } catch (e: Exception) {
            Timber.e(e, "SovereignShield: Decryption failed")
            throw SecurityException("Security breach: decryption failure", e)
        }
    }

    /**
     * Decrypts data with a heuristic fallback for legacy plaintext (pre-hardening).
     * Used for Oracle Drive and Cascade memory migrations.
     */
    override fun decryptWithFallback(data: ByteArray): ByteArray {
        if (data.isEmpty()) return data
        return try {
            keystoreManager.decrypt(data)
        } catch (e: Exception) {
            if (isLikelyLegacyPlaintext(data)) {
                Timber.w("SovereignShield: Legacy plaintext detected, allowing migration bypass")
                data
            } else {
                Timber.e(e, "SovereignShield: Decryption failed and data is not legacy plaintext")
                throw SecurityException("Security breach: unauthenticated data access", e)
            }
        }
    }

    /**
     * Heuristic check for legacy data.
     * AES-GCM (12b IV + 16b Tag) has a min overhead of 28 bytes.
     */
    private fun isLikelyLegacyPlaintext(data: ByteArray): Boolean {
        if (data.size < 28) return true
        return try {
            val text = data.decodeToString()
            text.all { it.isDefined() }
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Removes a specific key from the keystore.
     */
    fun purgeKey(alias: String) {
        Timber.w("SovereignShield: Purging key $alias")
        keystoreManager.removeKey(alias)
    }
}
