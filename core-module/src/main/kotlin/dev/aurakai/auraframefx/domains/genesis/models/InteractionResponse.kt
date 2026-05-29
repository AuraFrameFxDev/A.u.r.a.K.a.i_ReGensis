package dev.aurakai.auraframefx.domains.genesis.models

import kotlinx.serialization.Serializable

/**
 * 🛰️ InteractionResponse
 * 
 * Standard container for cross-agent interaction results in the ReGenesis manifold.
 */
@Serializable
data class InteractionResponse(
    val content: String,
    val success: Boolean,
    val timestamp: Long = System.currentTimeMillis(),
    val metadata: Map<String, String> = emptyMap()
)
