package dev.aurakai.auraframefx.oracle.drive.utils

import dev.aurakai.auraframefx.core.security.EncryptionManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SecureFileManager @Inject constructor(
    private val encryptionManager: EncryptionManager
) {
    fun saveFile(data: ByteArray, fileName: String): Boolean = true
    fun readFile(fileName: String): ByteArray? = null
    fun deleteFile(fileName: String): Boolean = true
}
