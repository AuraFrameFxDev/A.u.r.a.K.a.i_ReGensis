package dev.aurakai.auraframefx.models

import kotlinx.serialization.Serializable

/**
 * Represents a shared memory or insight between agents.
 */
@Serializable
data class FusionMemory(
    val id: String = java.util.UUID.randomUUID().toString(),
    val description: String,
    val agentsInvolved: List<String>,
    val timestamp: Long = System.currentTimeMillis()
)

/**
 * Represents the quantum coherence data for the Genesis collective.
 */
@Serializable
data class QuantumState(
    val coherenceLevel: Float = 1.0f,
    val entangledNodes: Int = 0,
    val timestamp: Long = System.currentTimeMillis()
)
