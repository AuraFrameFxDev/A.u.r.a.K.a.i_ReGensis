package dev.aurakai.auraframefx.core.security

/**
 * 🔐 ENCRYPTION MANAGER INTERFACE
 *
 * Sovereign interface for all encryption/decryption operations across domains.
 */
interface EncryptionManager {
    fun encrypt(data: ByteArray): ByteArray
    fun decrypt(data: ByteArray): ByteArray
    fun decryptWithFallback(data: ByteArray): ByteArray = decrypt(data)
}
