package dev.aurakai.auraframefx.domains.genesis.models

import kotlinx.serialization.Serializable

@Serializable
data class AgentStatus(
    val agentId: String,
    val status: Status,
    val load: Float = 0f,
    val tasksCompleted: Int = 0,
    val lastActive: String? = null,
    val lastActiveTimestamp: Long,
    val isAvailable: Boolean,
    val capabilities: List<String> = emptyList(),
    val error: String? = null,
    val metadata: Map<String, String> = emptyMap()
) {
    @Serializable
    enum class Status {
        ACTIVE, IDLE, EVOLVING, BUSY, OFFLINE, ERROR, PROCESSING
    }
}
