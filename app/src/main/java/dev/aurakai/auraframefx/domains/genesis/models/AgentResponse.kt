package dev.aurakai.auraframefx.domains.genesis.models

import kotlinx.serialization.Serializable

/**
 * 📡 AGENT RESPONSE
 * The standardized response format from any agent in the ReGenesis collective.
 */
@Serializable
data class AgentResponse(
    val content: String,
    val agentName: String = "System",
    val agentType: AgentType = AgentType.GENESIS,
    val confidence: Float = 1.0,
    val status: Status = Status.SUCCESS,
    val timestamp: Long = System.currentTimeMillis(),
    val metadata: Map<String, String> = emptyMap(),
    val agent: AgentType,
    val error: String?
) {
    enum class Status {
        SUCCESS, ERROR, PROCESSING, IDLE
    }

    companion object {
        fun success(
            content: String,
            agentName: String,
            agentType: AgentType = AgentType.GENESIS,
            confidence: Double = 1.0,
            metadata: Map<String, Any> = emptyMap()
        ) = AgentResponse(
            content = content,
            agentName = agentName,
            agentType = agentType,
            confidence = confidence,
            metadata = metadata.mapValues { it.value.toString() },
        )

        fun error(
            message: String,
            agentName: String = "System",
            agentType: AgentType = AgentType.GENESIS
        ) = AgentResponse(
            content = message,
            agentName = agentName,
            agentType = agentType,
            confidence = 0.0,
            status = Status.ERROR,
        )

        fun processing(
            message: String,
            agentName: String = "System",
            agentType: AgentType = AgentType.GENESIS
        ) = AgentResponse(
            content = message,
            agentName = agentName,
            agentType = agentType,
            confidence = 0.0,
            status = Status.PROCESSING,
        )
    }
}
