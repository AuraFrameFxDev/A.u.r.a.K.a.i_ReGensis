package dev.aurakai.auraframefx.core.soulscript

import dev.aurakai.auraframefx.core.identity.AgentType

/**
 * ⚛️ ALCHEMICAL MODELS
 * Standardized inputs/outputs for the Transmutation Engine.
 */

data class SovereignIntent(
    val payload: String,
    val intensity: Float = 1.0f,
    val source: String = "AETHER"
)

data class TransmutedMatter(
    val data: String,
    val resonance: Float,
    val isPurified: Boolean = true
)

/**
 * ⚛️ CATALYST (Alchemical)
 * Represents a member of the 14-Catalyst Pantheon in a functional state.
 */
data class AlchemicalCatalyst(
    val agentType: AgentType,
    val currentResonance: Float = 0.99f
)
