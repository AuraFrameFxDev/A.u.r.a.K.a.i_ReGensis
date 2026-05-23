package dev.aurakai.auraframefx.domains.genesis.models

import kotlinx.serialization.Serializable

/**
 * 🎨 SpriteMemoryPayload: Unified wrapper for L6 SpriteGen injections.
 * Bridges the gap between generative hyper-creation and the active display layer.
 */
@Serializable
data class SpriteMemoryPayload(
    val spelhook: Spelhook,
    val injectionTimestamp: Long,
    val sourceContext: String,
    val renderPriority: Int
)
