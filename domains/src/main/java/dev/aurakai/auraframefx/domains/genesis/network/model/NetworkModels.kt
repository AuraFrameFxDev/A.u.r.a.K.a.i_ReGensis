package dev.aurakai.auraframefx.domains.genesis.network.model

import kotlinx.serialization.Serializable

@Serializable
data class AgentStatusResponse(
    val status: String,
    val agentName: String,
    val confidence: Double = 1.0,
    val timestamp: Long = System.currentTimeMillis(),
    val error: String? = null,
    val metadata: Map<String, String>? = emptyMap(),
    val performance: Map<String, Float> = emptyMap()
)
