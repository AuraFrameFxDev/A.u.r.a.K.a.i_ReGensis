package dev.aurakai.auraframefx.quarantine.security

interface EncryptionManager {
    fun encrypt(data: ByteArray): ByteArray
    fun decrypt(data: ByteArray): ByteArray
}
