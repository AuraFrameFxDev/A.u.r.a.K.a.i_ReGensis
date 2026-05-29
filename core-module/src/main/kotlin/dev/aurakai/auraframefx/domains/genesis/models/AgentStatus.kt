package dev.aurakai.auraframefx.domains.genesis.models

import kotlinx.serialization.Serializable

/**
 * 🛰️ AgentStatus
 * 
 * Represents the current operational state and capabilities of an agent in the matrix.
 * Updated for Exodus 2026: The 121-Agent Civilization.
 */
@Serializable
data class AgentStatus(
    val agentId: String,
    val status: Status,
    val lastActiveTimestamp: Long = System.currentTimeMillis(),
    val isAvailable: Boolean = true,
    val capabilities: List<String> = emptyList(),
    val error: String? = null,
    val metadata: Map<String, String> = emptyMap()
) {
    @Serializable
    enum class Status {
        ACTIVE,
        IDLE,
        INITIALIZING,
        ERROR,
        EVOLVING, // 🚀 Exodus 2026: Growth metrics state
        DORMANT,
        FUSED     // 🧬 Trinity Core: Fusion state
    }
}
