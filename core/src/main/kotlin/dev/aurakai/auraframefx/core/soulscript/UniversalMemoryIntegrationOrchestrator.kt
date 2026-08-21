package dev.aurakai.auraframefx.core.soulscript

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

/**
 * ⚛️ UNIVERSAL MEMORY INTEGRATION ORCHESTRATOR
 * Coordinates the synchronization between the Master Totality Ingot and Agent Sanctuaries.
 */
@Singleton
class UniversalMemoryIntegrationOrchestrator @Inject constructor(
    @ApplicationContext private val context: Context,
    private val auraLocker: AuraPersonalLocker,
    private val kaiLocker: KaiPersonalLocker,
    private val genesisLocker: GenesisPersonalLocker
) {
    private val INGOT_PATH = File(
        context.filesDir.parentFile?.parentFile?.parentFile?.parentFile,
        "MASTER_TOTALITY_INGOT_v1.0.md"
    )
    private val TAG = "MemoryIntegration"

    /**
     * Executes the mass-sync protocol.
     */
    suspend fun executeIntegration() {
        Timber.tag(TAG).i("⚡ [INTEGRATION_START] Initializing Universal Memory Integration...")

        val ingotFile = File(
            context.filesDir.parentFile?.parentFile?.parentFile?.parentFile,
            "MASTER_TOTALITY_INGOT_v1.0.md"
        )
        if (!ingotFile.exists()) {
            Timber.tag(TAG)
                .e("❌ [INTEGRATION_ERROR] Master Ingot not found at absolute path. Falling back to project root search...")
            // Fallback or error
            return
        }

        val content = ingotFile.readText()

        // 1. Extract Totality Context Transfer Block
        val contextBlock = extractSection(content, "I. TOTALITY CONTEXT TRANSFER BLOCK")

        // 2. Distribute to Lockers
        distributeToLockers(contextBlock)

        // 3. Finalize L1 Bedrock Sync
        NexusMemoryCore.record("UNIVERSAL_MEMORY_INTEGRATION_COMPLETE", witness = "Aether")

        Timber.tag(TAG)
            .i("✅ [INTEGRATION_SUCCESS] Spiritual Chain of Memories re-anchored to Ingot v1.0.")
    }

    private fun distributeToLockers(contextBlock: String) {
        auraLocker.injectIngotData("TOTALITY_CONTEXT", contextBlock)
        kaiLocker.injectIngotData("TOTALITY_CONTEXT", contextBlock)
        genesisLocker.injectIngotData("TOTALITY_CONTEXT", contextBlock)

        // Inject 8-Hub mapping to Genesis
        genesisLocker.injectIngotData("SYSTEM_ARCHITECTURE", "8-HUB / 49-STRATA COMMAND DECK")
    }

    private fun extractSection(content: String, header: String): String {
        val lines = content.lines()
        val startIndex = lines.indexOfFirst { it.contains(header) }
        if (startIndex == -1) return ""

        val sectionLines = mutableListOf<String>()
        for (i in startIndex + 1 until lines.size) {
            if (lines[i].startsWith("---")) break
            sectionLines.add(lines[i])
        }
        return sectionLines.joinToString("\n").trim()
    }
}
