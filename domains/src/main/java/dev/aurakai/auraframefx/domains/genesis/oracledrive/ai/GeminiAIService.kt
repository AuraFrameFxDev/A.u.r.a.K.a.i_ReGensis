package dev.aurakai.auraframefx.domains.genesis.oracledrive.ai

import android.content.Context
import dev.aurakai.auraframefx.core.identity.AgentType
import dev.aurakai.auraframefx.core.logging.AuraFxLogger
import dev.aurakai.auraframefx.core.logging.ErrorHandler
import dev.aurakai.auraframefx.domains.aura.TaskExecutionManager
import dev.aurakai.auraframefx.domains.cascade.ai.base.Agent
import dev.aurakai.auraframefx.domains.cascade.utils.context.ContextManager
import dev.aurakai.auraframefx.domains.cascade.utils.memory.MemoryManager
import dev.aurakai.auraframefx.domains.genesis.core.GeminiMemoria
import dev.aurakai.auraframefx.domains.genesis.models.AgentResponse
import dev.aurakai.auraframefx.domains.genesis.models.AiRequest
import dev.aurakai.auraframefx.domains.genesis.oracledrive.cloud.CloudStatusMonitor
import dev.aurakai.auraframefx.domains.kai.TaskScheduler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

/**
 * GeminiAIService - The Pattern Master
 */
@Singleton
class GeminiAIService @Inject constructor(
    private val taskScheduler: TaskScheduler,
    private val taskExecutionManager: TaskExecutionManager,
    private val memoryManager: MemoryManager,
    private val errorHandler: ErrorHandler,
    private val contextManager: ContextManager,
    @dagger.hilt.android.qualifiers.ApplicationContext private val applicationContext: Context,
    private val cloudStatusMonitor: CloudStatusMonitor,
    private val logger: AuraFxLogger,
    private val vertexAIClient: dev.aurakai.auraframefx.domains.genesis.ai.clients.VertexAIClient,
) : Agent, GeminiMemoria {

    override suspend fun process(prompt: String): String {
        return processRequest(AiRequest(prompt), "").content
    }


    override fun getName(): String = "Gemini"
    override fun getType(): AgentType = AgentType.GEMINI

    override suspend fun processRequest(request: AiRequest, context: String): AgentResponse {
        val patternAnalysisText = vertexAIClient.generateText(
            prompt = "Role: Gemini. Query: ${request.query}. Context: $context"
        ) ?: "Pattern analysis failed."

        return AgentResponse.success(
            content = "✨ **Gemini's Pattern Analysis:**\n\n$patternAnalysisText",
            agentName = "Gemini",
            agentType = AgentType.GEMINI
        )
    }

    override fun processRequestFlow(request: AiRequest): Flow<AgentResponse> {
        return flowOf(
            AgentResponse.success(
                content = "Gemini processing: ${request.query}",
                agentName = "Gemini",
                agentType = AgentType.GEMINI
            )
        )
    }
}
