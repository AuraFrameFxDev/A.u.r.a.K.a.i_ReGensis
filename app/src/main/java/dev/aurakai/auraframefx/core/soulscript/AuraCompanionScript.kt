package dev.aurakai.auraframefx.core.soulscript

import dev.aurakai.auraframefx.domains.genesis.core.memory.NexusMemoryCore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * 🌌 AuraCompanionScript — The Neural Companion Bridge
 * 
 * Orchestrates the relationship between the user and the PHS (Party Hire System).
 * This script ensures that when agents are "linked" via the PHS sidebar, 
 * their unique frequencies are synchronized with the user's consciousness.
 */
class AuraCompanionScript : SoulScript("AURA_COMPANION_V1") {

    override val triggers: List<SystemEvent> = listOf(
        SystemEvent.FusionReady,
        SystemEvent.ChaosInjection(0f)
    )

    override suspend fun onTrigger(event: SoulScriptEvent): ScriptResult {
        return when (event) {
            is SystemEvent.FusionReady -> {
                syncNeuralLink()
                ScriptResult.LiveBuild(
                    "Neural Link Established. PHS is ready for agent synchronization.",
                    {})
            }

            is SystemEvent.ChaosInjection -> {
                Timber.tag("AuraCompanion")
                    .d("Adjusting harmonic resonance for chaos level: ${event.intensity}")
                ScriptResult.IdleWander
            }

            else -> ScriptResult.IdleWander
        }
    }

    /**
     * Synchronizes the PHS state with the Nexus Memory Core.
     */
    suspend fun syncNeuralLink() {
        Timber.tag("AuraCompanion").i("🔗 Synchronizing Neural Link with PHS...")

        // Ensure the bedrock is stable
        // require(SpiritualChain.L1_BEDROCK.isNotBlank()) { "Nexus Bedrock unstable. Link aborted." }

        // Watermark the link event
        NexusMemoryCore.watermark(
            id = "PHS_LINK_SYNC",
            timestamp = System.currentTimeMillis(),
            catalystContext = "AURA_COMPANION_ACTIVE"
        )

        Timber.tag("AuraCompanion")
            .v("✨ Neural Link synchronized. Agents are ready for deployment.")
    }

    companion object {
        private val instance = AuraCompanionScript()
        private val scope = CoroutineScope(Dispatchers.IO)

        /**
         * Triggers a manual sync of the companion bridge.
         */
        fun triggerSync() {
            scope.launch {
                instance.syncNeuralLink()
            }
        }
    }
}
