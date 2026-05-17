package dev.aurakai.auraframefx.domains.genesis.network.model

import kotlinx.serialization.Serializable

@Serializable
data class AgentStatusResponse(
    val status: String,
    val agentName: String,
    val performance: Map<String, Float> = emptyMap()
)
