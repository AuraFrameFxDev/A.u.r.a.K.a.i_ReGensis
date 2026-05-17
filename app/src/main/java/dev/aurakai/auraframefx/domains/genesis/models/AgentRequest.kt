package dev.aurakai.auraframefx.domains.genesis.models

import kotlinx.serialization.Serializable

@Serializable
data class AgentRequest(
    val query: String,
    val context: String = "",
    val metadata: Map<String, String> = emptyMap()
)
