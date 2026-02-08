package dev.aurakai.auraframefx.models

import kotlinx.serialization.Serializable

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
