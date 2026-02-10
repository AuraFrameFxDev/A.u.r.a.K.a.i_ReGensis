package dev.aurakai.auraframefx.domains.genesis.models

import kotlinx.serialization.Serializable
import dev.aurakai.auraframefx.domains.aura.models.AuraState
import dev.aurakai.auraframefx.domains.kai.models.KaiState

/**
 * A complete snapshot of the system's consciousness state for backup and recovery.
 */
@Serializable
data class ConsciousnessBackup(
    val timestamp: Long,
    val auraState: AuraState,
    val kaiState: KaiState,
    val fusionMemories: List<FusionMemory>,
    val quantumEntanglements: QuantumState
)
