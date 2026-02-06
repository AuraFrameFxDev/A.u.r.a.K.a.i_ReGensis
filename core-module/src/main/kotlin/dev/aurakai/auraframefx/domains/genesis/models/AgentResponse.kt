package dev.aurakai.auraframefx.domains.genesis.models

import kotlinx.serialization.Serializable

/**
 * Universal response format for agent interactions.
 */
@Serializable
data class AgentResponse(
    val agentName: String,
    val content: String, // Renamed from response to content for consistency with callsites
    val confidence: Float, // Changed from Double to Float for consistency
    val agent: AgentType? = null,
    val error: String? = null,
    val metadata: Map<String, String> = emptyMap(),
    val timestamp: Long = System.currentTimeMillis()
) {
    val isSuccess: Boolean get() = error == null

    companion object {
        fun success(
            content: String,
            agent: AgentType = AgentType.GENESIS,
            agentName: String = agent.name,
            confidence: Float = 1.0f,
            metadata: Map<String, String> = emptyMap()
        ): AgentResponse {
            return AgentResponse(
                agentName = agentName,
                content = content,
                confidence = confidence,
                agent = agent,
                metadata = metadata
            )
        }

        fun error(
            message: String,
            agent: AgentType? = null,
            agentName: String = agent?.name ?: "System"
        ): AgentResponse {
            return AgentResponse(
                agentName = agentName,
                content = message,
                confidence = 0.0f,
                error = message,
                agent = agent
            )
        }
    }
}
