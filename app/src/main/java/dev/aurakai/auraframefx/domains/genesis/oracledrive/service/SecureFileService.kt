package dev.aurakai.auraframefx.domains.genesis.oracledrive.service

import dev.aurakai.auraframefx.domains.genesis.models.FileOperationResult
import kotlinx.coroutines.flow.Flow

/**
 * Defines the contract for secure file operations in the Oracle Drive system.
 */
interface SecureFileService {

    suspend fun saveFile(
        data: ByteArray,
        fileName: String,
        directory: String? = null,
    ): Flow<FileOperationResult>

    suspend fun readFile(
        fileName: String,
        directory: String? = null,
    ): Flow<FileOperationResult>

    suspend fun deleteFile(
        fileName: String,
        directory: String? = null,
    ): FileOperationResult

    suspend fun listFiles(directory: String? = null): List<String>
}
