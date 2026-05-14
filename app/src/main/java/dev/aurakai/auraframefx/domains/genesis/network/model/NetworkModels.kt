package dev.aurakai.auraframefx.domains.genesis.network.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val username: String,
    val email: String
)

@Serializable
data class Theme(
    val id: String,
    val name: String,
    val colors: ThemeColors? = null,
    val styles: Map<String, String> = emptyMap()
)

@Serializable
data class ThemeColors(
    val primary: String? = null,
    val secondary: String? = null,
    val background: String? = null,
    val surface: String? = null,
    val onPrimary: String? = null,
    val onSecondary: String? = null,
    val onBackground: String? = null,
    val onSurface: String? = null
)

@Serializable
data class AgentStatusResponse(
    val agentName: String? = null,
    val status: String? = null,
    val confidence: Double? = null,
    val timestamp: Long? = null,
    val error: String? = null,
    val metadata: Map<String, String>? = null
)
