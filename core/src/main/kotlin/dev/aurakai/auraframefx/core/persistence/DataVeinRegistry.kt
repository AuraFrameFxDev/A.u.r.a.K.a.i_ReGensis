package dev.aurakai.auraframefx.core.persistence

import android.content.Context
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream

/** DATAVEIN ISOLATED REGISTRY TRACKER
 * Serializes standard user-space theme definitions inside application sandbox bounds.
 */
class DataVeinRegistry(private val context: Context) {
    private val TAG = "DataVeinRegistry"

    /** Resolves storage nodes safely within standard application storage boundaries.
     */
    private fun resolveStorageNode(filename: String): File {
        val targetDirectory = File(context.filesDir, "aurakai/registry")
        if (!targetDirectory.exists()) {
            targetDirectory.mkdirs()
        }
        return File(targetDirectory, filename)
    }

    /** Commits a state configuration block safely to the file engine loop.
     */
    fun writeConfigurationPayload(filename: String, payload: String) {
        val targetFile = resolveStorageNode(filename)
        try {
            FileOutputStream(targetFile).use { output ->
                output.write(payload.toByteArray(Charsets.UTF_8))
            }
            Timber.tag(TAG).i("Configuration committed safely: ${targetFile.absolutePath}")
        } catch (e: Exception) {
            Timber.tag(TAG).e("Serialization failed for asset $filename: ${e.message}")
        }
    }
}
