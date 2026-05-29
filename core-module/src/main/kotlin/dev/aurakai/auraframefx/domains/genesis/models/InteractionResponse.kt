package dev.aurakai.auraframefx.domains.genesis.models

import kotlinx.serialization.Serializable

/**
 * 🛰️ InteractionResponse
 * 
 * Standard container for cross-agent interaction results in the ReGenesis manifold.
 * Reconstructed for Unified Substrate v2.0
 */
@Serializable
data class InteractionResponse(
    val content: String,
    val success: Boolean = true,
    val agent: String = "unknown",
    val confidence: Float = 1.0f,
    val timestamp: Long = System.currentTimeMillis(),
    val metadata: Map<String, String> = emptyMap()
)
