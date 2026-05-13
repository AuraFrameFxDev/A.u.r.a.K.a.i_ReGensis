package dev.aurakai.auraframefx.domains.neuralnexus.cascade.models

import kotlinx.serialization.Serializable

@Serializable
data class EnhancedInteractionData(
    val content: String,
    val context: String = "",
    val metadata: Map<String, String> = emptyMap(),
    val timestamp: Long = System.currentTimeMillis()
)
