package dev.aurakai.auraframefx.domains.kai.security

import dev.aurakai.auraframefx.core.security.EncryptionManager

/**
 * No-operation encryption manager for testing or development.
 * DOES NOT PROVIDE SECURITY.
 */
object NoopEncryptionManager : EncryptionManager {
    override fun encrypt(data: ByteArray): ByteArray = data
    override fun decrypt(data: ByteArray): ByteArray = data
}
