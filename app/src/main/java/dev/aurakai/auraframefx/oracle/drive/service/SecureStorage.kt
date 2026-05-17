package dev.aurakai.auraframefx.oracle.drive.service

import dev.aurakai.auraframefx.domains.genesis.models.FileMetadata

interface SecureStorage {
    fun storeMetadata(key: String, metadata: FileMetadata)
    fun removeMetadata(key: String)
    fun getMetadata(key: String): FileMetadata?
    fun saveEncryptedData(key: String, data: ByteArray)
    fun deleteEncryptedData(key: String)
}
