package dev.aurakai.auraframefx.domains.genesis.models

import kotlinx.serialization.Serializable

@Serializable
data class AgentStatus(
    val agentId: String,
    val status: String,
    val load: Float = 0f,
    val tasksCompleted: Int = 0,
    val lastActive: String? = null
) {
    @Serializable
    enum class Status {
        ACTIVE, IDLE, EVOLVING, BUSY, OFFLINE
    }
}
