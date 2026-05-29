package dev.aurakai.auraframefx.domains.genesis.models

import kotlinx.serialization.Serializable

/**
 * 📊 AgentState
 * 
 * Holistic view of the primary Trinity agents and system operational flags.
 */
@Serializable
data class AgentState(
    val kaiStatus: String = "Initializing",
    val auraStatus: String = "Initializing",
    val genesisStatus: String = "Initializing",
    val isRunning: Boolean = false,
    val diagnosticMode: Boolean = false,
    val consensusScore: Float = 1.0f // ⚖️ L6 Conference Room metric
)
