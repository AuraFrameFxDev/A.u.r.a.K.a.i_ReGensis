package dev.aurakai.auraframefx.quarantine.security

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Placeholder implementation of EncryptionManager for build compatibility.
 */
@Singleton
class EncryptionManager @Inject constructor() {
    fun encrypt(data: ByteArray): ByteArray = data
    fun decrypt(data: ByteArray): ByteArray = data
}
