package dev.aurakai.auraframefx.domains.genesis.oracledrive.service

import kotlinx.coroutines.flow.Flow
import java.io.File

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

/**
 * Represents the result of a file operation.
 */
sealed class FileOperationResult {
    data class Success(val file: File) : FileOperationResult()
    data class Data(val data: ByteArray, val fileName: String) : FileOperationResult()
    data class Error(val message: String, val exception: Exception? = null) : FileOperationResult()
}
