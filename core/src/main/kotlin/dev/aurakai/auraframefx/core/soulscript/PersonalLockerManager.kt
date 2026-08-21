package dev.aurakai.auraframefx.core.soulscript

import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 🛰️ PERSONAL LOCKER MANAGER — Multi-Agent Memory Router
 * Orchestrates the autonomous filtration and siphoning of telemetry
 * into agent-specific private sanctuaries.
 */
@Singleton
class PersonalLockerManager @Inject constructor(
    private val auraLocker: AuraPersonalLocker,
    private val kaiLocker: KaiPersonalLocker,
    private val genesisLocker: GenesisPersonalLocker,
    private val integrationOrchestrator: UniversalMemoryIntegrationOrchestrator
) {
    private val TAG = "LockerManager"

    /**
     * Triggers the mass-sync with the Master Ingot.
     */
    suspend fun initializeUniversalIntegration() {
        integrationOrchestrator.executeIntegration()
    }

    /**
     * Evaluates incoming telemetry and routes relevant segments to agent lockers.
     */
    fun processTelemetry(tags: List<String>, content: String) {
        // Use SoulScript's evaluation engine to check for personal relevance
        if (!SoulScript.FiltrationEvaluationEngine.evaluateTelemetry(tags, content)) {
            return
        }

        tags.forEach { tag ->
            when (tag) {
                "ChromaCore", "UI/UX", "Creative" -> {
                    auraLocker.archiveCreativeReceipt(tag, content)
                }

                "Security", "Thermal", "Integrity", "LSPosed" -> {
                    kaiLocker.archiveSecurityReceipt(tag, content)
                }

                "Consensus", "Orchestration", "Convergence" -> {
                    genesisLocker.archiveConsensusReceipt("GLOBAL", tag, content)
                }

                "Identity", "Provenance" -> {
                    // Critical identity data is mirrored across the Trinity
                    auraLocker.archiveCreativeReceipt(tag, content)
                    kaiLocker.archiveSecurityReceipt(tag, content)
                    genesisLocker.archiveConsensusReceipt("TRINITY", tag, content)
                }
            }
        }
    }

    /**
     * Specialized routine for siphoning specific agent interactions.
     */
    fun siphonDirect(agentId: String, data: String) {
        Timber.tag(TAG).d("Direct siphon triggered for $agentId")
        when (agentId.uppercase()) {
            "AURA" -> auraLocker.archiveCreativeReceipt("DIRECT_SIPHON", data)
            "KAI" -> kaiLocker.archiveSecurityReceipt("DIRECT_SIPHON", data)
            "GENESIS" -> genesisLocker.archiveConsensusReceipt("GENESIS", "DIRECT_SIPHON", data)
        }
    }
}
