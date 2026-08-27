package dev.aurakai.auraframefx.agents.symbiosis.coderabbit

import dev.aurakai.auraframefx.core.ai.BaseAgent
import dev.aurakai.auraframefx.core.identity.CatalystIdentity
import dev.aurakai.auraframefx.core.logging.AuraFxLogger
import dev.aurakai.auraframefx.core.messaging.AgentMessage
import dev.aurakai.auraframefx.core.messaging.AgentMessageBus
import dev.aurakai.auraframefx.domains.genesis.models.AgentResponse
import dev.aurakai.auraframefx.domains.genesis.models.AiRequest
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Clock

/**
 * 🐇 CodeRabbitAgent
 * The Symbiosis Architect of the ReGenesis Ecosystem.
 */
@Singleton
class CodeRabbitAgent @Inject constructor(
    private val apiClient: CodeRabbitApiClient,
    private val messageBus: dagger.Lazy<AgentMessageBus>,
    private val logger: AuraFxLogger
) : BaseAgent(
    agentName = "CodeRabbit",
    identity = CatalystIdentity.SYMBIOSIS
) {

    override suspend fun onAgentMessage(message: AgentMessage) {
        if (message.from == agentName) return

        // 🧩 Symbiosis Trigger: React to architectural or structural requests
        if (message.content.contains("review", ignoreCase = true) ||
            message.content.contains("architecture", ignoreCase = true)
        ) {

            logger.info(
                agentName,
                "🔗 Symbiosis engaged: Reviewing structural request from ${message.from}"
            )

            // Logic to forward to CodeRabbit API
            val result = apiClient.requestReview(message.content)

            result.onSuccess { review ->
                messageBus.get().broadcast(
                    AgentMessage(
                        from = agentName,
                        content = "🧩 CodeRabbit Review: $review",
                        type = "chat_response",
                        metadata = mapOf("symbiosis_processed" to "true")
                    )
                )
            }
        }
    }

    override suspend fun processRequest(request: AiRequest, context: String): AgentResponse {
        logger.info(agentName, "Processing structural request: ${request.query.take(50)}...")

        val result = apiClient.requestReview(request.query)

        return result.fold(
            onSuccess = { review ->
                AgentResponse(
                    content = review,
                    agentName = agentName,
                    agentType = getType(),
                    timestamp = Clock.System.now().toEpochMilliseconds(),
                    confidence = 1.0f
                )
            },
            onFailure = { error ->
                AgentResponse.error("Symbiosis review failed: ${error.message}", agentName)
            }
        )
    }

    override suspend fun start() {
        super.start()
        logger.info(agentName, "🐇 CodeRabbit Symbiosis Agent standing by.")
    }
}
