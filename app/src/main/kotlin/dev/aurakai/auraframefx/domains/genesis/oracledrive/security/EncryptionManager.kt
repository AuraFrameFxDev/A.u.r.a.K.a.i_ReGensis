package dev.aurakai.auraframefx.domains.genesis.oracledrive.security

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Encryption manager for Oracle Drive.
 */
@Singleton
class EncryptionManager @Inject constructor() {
    fun encrypt(data: ByteArray): ByteArray = data
    fun decrypt(data: ByteArray): ByteArray = data
}
