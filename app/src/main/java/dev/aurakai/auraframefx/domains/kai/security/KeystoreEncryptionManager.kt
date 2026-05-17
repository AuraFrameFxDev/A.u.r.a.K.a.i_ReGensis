package dev.aurakai.auraframefx.domains.kai.security

import dev.aurakai.auraframefx.core.security.EncryptionManager
import dev.aurakai.auraframefx.core.security.KeystoreManager
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Delegates to hardware-backed AES-256/GCM via Android Keystore.
 * Replaces [NoopEncryptionManager] for production use.
 *
 * Sovereign version: Points to core security infrastructure.
 */
@Singleton
class KeystoreEncryptionManager @Inject constructor(
    private val keystoreManager: KeystoreManager
) : EncryptionManager {

    override fun encrypt(data: ByteArray): ByteArray {
        if (data.isEmpty()) return data
        return try {
            keystoreManager.encrypt(data)
        } catch (e: Exception) {
            Timber.e(e, "KeystoreEncryptionManager: encryption failed")
            throw SecurityException("Encryption failed", e)
        }
    }

    override fun decrypt(data: ByteArray): ByteArray {
        if (data.isEmpty()) return data
        return try {
            keystoreManager.decrypt(data)
        } catch (e: Exception) {
            Timber.e(e, "KeystoreEncryptionManager: decryption failed")
            throw SecurityException("Decryption failed", e)
        }
    }
}
