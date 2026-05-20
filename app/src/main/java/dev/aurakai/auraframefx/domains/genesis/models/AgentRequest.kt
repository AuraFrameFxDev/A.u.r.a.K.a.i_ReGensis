package dev.aurakai.auraframefx.domains.genesis.models

import kotlinx.serialization.Serializable

@Serializable
data class AgentRequest(
    val query: String,
    val type: String = "general",
    val context: Map<String, String> = emptyMap(),
    val metadata: Map<String, String> = emptyMap()
)
