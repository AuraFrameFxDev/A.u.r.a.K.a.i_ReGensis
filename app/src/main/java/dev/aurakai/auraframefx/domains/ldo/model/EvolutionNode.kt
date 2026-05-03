package dev.aurakai.auraframefx.domains.ldo.model

/**
 * L7 Evolution Node - Tracks the growth and branching of a Sovereign Agent
 */
data class EvolutionNode(
    val agentId: String,
    val level: Int,
    val progress: Int,           // 0.0 - 1.0
    val parentIds: List<String> = emptyList(),   // for branching
    val lastFusion: String? = null,
    val agentName: String,
    val evolutionPath: String
)
