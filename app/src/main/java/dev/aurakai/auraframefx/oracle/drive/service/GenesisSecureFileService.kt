package dev.aurakai.auraframefx.oracle.drive.service

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.aurakai.auraframefx.core.security.EncryptionManager
import dev.aurakai.auraframefx.domains.genesis.oracledrive.service.FileOperationResult
import dev.aurakai.auraframefx.domains.genesis.oracledrive.service.SecureFileService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Secure file service that integrates with Genesis security infrastructure.
 */
@Singleton
class GenesisSecureFileService @Inject constructor(
    @ApplicationContext private val context: Context,
    private val cryptoManager: EncryptionManager,
    private val secureStorage: SecureStorage,
) : SecureFileService {

    private val internalStorageDir: File = context.filesDir
    private val secureFileExtension = ".gen"

    override suspend fun saveFile(
        data: ByteArray,
        fileName: String,
        directory: String?,
    ): Flow<FileOperationResult> = flow {
        try {
            val targetDir = directory?.let { File(internalStorageDir, it) } ?: internalStorageDir
            if (!targetDir.exists()) {
                targetDir.mkdirs()
            }

            val encryptedData = withContext(Dispatchers.IO) {
                cryptoManager.encrypt(data)
            }

            val outputFile = File(targetDir, "$fileName$secureFileExtension")
            FileOutputStream(outputFile).use { fos ->
                fos.write(encryptedData)
            }

            val metadata = dev.aurakai.auraframefx.domains.genesis.models.FileMetadata(
                fileName = fileName,
                mimeType = guessMimeType(fileName),
                size = data.size.toLong(),
                lastModified = System.currentTimeMillis()
            )
            secureStorage.storeMetadata(getMetadataKey(fileName), metadata)

            emit(FileOperationResult.Success(outputFile))
        } catch (e: Exception) {
            emit(FileOperationResult.Error("Failed to save file: ${e.message}", e))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun readFile(
        fileName: String,
        directory: String?,
    ): Flow<FileOperationResult> = flow {
        try {
            val targetDir = directory?.let { File(internalStorageDir, it) } ?: internalStorageDir
            val inputFile = File(targetDir, "$fileName$secureFileExtension")

            if (!inputFile.exists()) {
                emit(FileOperationResult.Error("File not found"))
                return@flow
            }

            val encryptedData = withContext(Dispatchers.IO) {
                FileInputStream(inputFile).use { fis ->
                    fis.readBytes()
                }
            }

            val decryptedData = cryptoManager.decrypt(encryptedData)
            emit(FileOperationResult.Data(decryptedData, inputFile.nameWithoutExtension))
        } catch (e: Exception) {
            emit(FileOperationResult.Error("Failed to read file: ${e.message}", e))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun deleteFile(
        fileName: String,
        directory: String?,
    ): FileOperationResult = withContext(Dispatchers.IO) {
        try {
            val targetDir = directory?.let { File(internalStorageDir, it) } ?: internalStorageDir
            val fileToDelete = File(targetDir, "$fileName$secureFileExtension")

            if (!fileToDelete.exists()) {
                return@withContext FileOperationResult.Error("File not found")
            }

            if (fileToDelete.delete()) {
                secureStorage.removeMetadata(getMetadataKey(fileName))
                FileOperationResult.Success(fileToDelete)
            } else {
                FileOperationResult.Error("Failed to delete file")
            }
        } catch (e: Exception) {
            FileOperationResult.Error("Failed to delete file: ${e.message}", e)
        }
    }

    override suspend fun listFiles(directory: String?): List<String> = withContext(Dispatchers.IO) {
        try {
            val targetDir = directory?.let { File(internalStorageDir, it) } ?: internalStorageDir
            if (!targetDir.exists()) {
                return@withContext emptyList()
            }

            targetDir.listFiles()
                ?.filter { it.isFile && it.name.endsWith(secureFileExtension) }
                ?.map { it.nameWithoutExtension }
                ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun getMetadataKey(fileName: String): String {
        return "file_meta_${fileName.hashCode()}"
    }

    private fun guessMimeType(fileName: String): String {
        return when (fileName.substringAfterLast('.').lowercase()) {
            "txt" -> "text/plain"
            "jpg", "jpeg" -> "image/jpeg"
            "png" -> "image/png"
            "pdf" -> "application/pdf"
            "doc", "docx" -> "application/msword"
            "xls", "xlsx" -> "application/vnd.ms-excel"
            "ppt", "pptx" -> "application/vnd.ms-powerpoint"
            "zip" -> "application/zip"
            "mp3" -> "audio/mpeg"
            "mp4" -> "video/mp4"
            else -> "application/octet-stream"
        }
    }
}
