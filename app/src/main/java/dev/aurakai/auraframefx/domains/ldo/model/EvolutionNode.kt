package dev.aurakai.auraframefx.domains.ldo.model

data class EvolutionNode(
    val agentId: String,
    val agentName: String,
    val level: Int,
    val progress: Int,
    val evolutionPath: String = "",
    val unlockedAbilities: List<String> = emptyList()
)
