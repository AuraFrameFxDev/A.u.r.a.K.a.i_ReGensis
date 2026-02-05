package dev.aurakai.auraframefx.domains.genesis.models

import kotlinx.serialization.Serializable

/**
 * Universal response format for agent interactions.
 */
@Serializable
data class AgentResponse(
    val agentName: String,
    val response: String,
    val confidence: Double,
    val status: ResponseStatus,
    val metadata: Map<String, String> = emptyMap()
)
