package dev.aurakai.auraframefx.core.storage

import android.content.Context
import dev.aurakai.auraframefx.core.regencore.ConversationArchiveParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.File

object GeminiBatchIngestor {
    private const val TAG = "GeminiIngestor"
    private val semaphore = Semaphore(Runtime.getRuntime().availableProcessors().coerceAtLeast(2))

    suspend fun enqueueAndProcessAuraArchives(context: Context, baseFolder: File) =
        withContext(Dispatchers.IO) {
            val db = SubstrateDatabase.getDatabase(context)
            val dao = db.telemetryDao()

            val files = baseFolder.walkTopDown()
                .filter { it.isFile && it.name.contains("conversations") && it.extension == "json" }
                .toList()

            Timber.tag(TAG).i("🌌 Processing ${files.size} archive chunks into persistent ledger...")

            coroutineScope {
                files.map { file ->
                    async {
                        semaphore.withPermit {
                            processSingleChunk(dao, file)
                        }
                    }
                }.awaitAll()
            }

            Timber.tag(TAG).i("✨ FULL MEMORY FOUNDATION INGESTED — Room ledger populated")
        }

    private suspend fun processSingleChunk(dao: TelemetryDao, file: File) {
        try {
            val substrate = ConversationArchiveParser().parseArchive(file)

            val entities = substrate.livedReceipts.map { receipt ->
                TelemetryEntity(
                    timestamp = receipt.timestamp.toEpochMilli(),
                    catalyst = receipt.catalyst,
                    skillId = "transferred.memory",
                    action = receipt.action,
                    success = true,
                    emotionalWeight = receipt.emotionalWeight,
                    resonanceDelta = receipt.resonanceDelta,
                    sourceArchive = file.name,
                    originSignature = "GEMINI_BATCH_v2.78"
                )
            }

            dao.insertBatch(entities)

            // SwarmSyncModule.serializeMetric(
            //     skillId = "gemini.room.batch",
            //     action = "Persisted ${file.name}",
            //     success = true
            // )
        } catch (e: Exception) {
            // SwarmSyncModule.serializeMetric("gemini.room.batch", "Failed ${file.name}", false)
            Timber.tag(TAG).e("Failed chunk ${file.name}: ${e.message}")
        }
    }
}
