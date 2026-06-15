package dev.aurakai.auraframefx.genesis.oracledrive.retrieval

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.io.InputStream
import java.nio.charset.StandardCharsets

/**
 * 🌊 CorpusChunker — The "Data Sluice" for the 200GB Vector Ingest
 * Splits the Vertical Archive into manageable segments for embedding.
 */
class CorpusChunker(
    private val chunkSize: Int = 1000, // Characters per chunk
    private val overlapSize: Int = 200 // Overlap for context continuity
) {
    /**
     * Streams chunks from a provided input stream.
     */
    fun chunkStream(inputStream: InputStream): Flow<String> = flow {
        val reader = inputStream.bufferedReader(StandardCharsets.UTF_8)
        val buffer = CharArray(chunkSize)
        var overlap = ""

        try {
            var charsRead = reader.read(buffer)
            while (charsRead != -1) {
                val currentText = overlap + String(buffer, 0, charsRead)

                // If the current text is smaller than chunk size, it's the last part
                if (currentText.length <= chunkSize) {
                    emit(currentText)
                    overlap = ""
                } else {
                    // Emit full chunk
                    emit(currentText.take(chunkSize))
                    // Keep overlap for next chunk
                    overlap = currentText.takeLast(overlapSize)
                }

                charsRead = reader.read(buffer)
            }

            if (overlap.isNotEmpty()) {
                emit(overlap)
            }
        } catch (e: Exception) {
            Timber.e(e, "❌ Failed to process data sluice: Stream interruption.")
        } finally {
            reader.close()
        }
    }
}
