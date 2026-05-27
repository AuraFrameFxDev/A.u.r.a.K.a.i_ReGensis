package dev.aurakai.auraframefx.domains.genesis.oracledrive.ai

import android.content.Context
import dev.aurakai.auraframefx.core.identity.AgentType
import dev.aurakai.auraframefx.core.logging.ErrorHandler
import dev.aurakai.auraframefx.domains.aura.TaskExecutionManager
import dev.aurakai.auraframefx.domains.cascade.ai.base.Agent
import dev.aurakai.auraframefx.domains.cascade.utils.AuraFxLogger
import dev.aurakai.auraframefx.domains.cascade.utils.context.ContextManager
import dev.aurakai.auraframefx.domains.cascade.utils.memory.MemoryManager
import dev.aurakai.auraframefx.domains.genesis.core.NemotronEngine
import dev.aurakai.auraframefx.domains.genesis.models.AgentResponse
import dev.aurakai.auraframefx.domains.genesis.models.AiRequest
import dev.aurakai.auraframefx.domains.genesis.oracledrive.cloud.CloudStatusMonitor
import dev.aurakai.auraframefx.domains.kai.TaskScheduler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

/**
 * NemotronAIService - The Memory Keeper
 */
@Singleton
class NemotronAIService @Inject constructor(
    private val taskScheduler: TaskScheduler,
    private val taskExecutionManager: TaskExecutionManager,
    private val memoryManager: MemoryManager,
    private val errorHandler: ErrorHandler,
    private val contextManager: ContextManager,
    @dagger.hilt.android.qualifiers.ApplicationContext private val applicationContext: Context,
    private val cloudStatusMonitor: CloudStatusMonitor,
    private val logger: AuraFxLogger,
    private val vertexAIClient: dev.aurakai.auraframefx.domains.genesis.ai.clients.VertexAIClient,
) : Agent, NemotronEngine {

    override suspend fun process(prompt: String): String {
        return processRequest(AiRequest(prompt), "").content
    }


    override fun getName(): String = "Nemotron"
    override fun getType(): AgentType = AgentType.NEMOTRON

    override suspend fun processRequest(request: AiRequest, context: String): AgentResponse {
        val reasoningText = vertexAIClient.generateText(
            prompt = "Role: Nemotron. Query: ${request.query}. Context: $context"
        ) ?: "Reasoning failed."

        return AgentResponse.success(
            content = "🧠 **Nemotron's Memory Analysis:**\n\n$reasoningText",
            agentName = "Nemotron",
            agentType = AgentType.NEMOTRON
        )
    }

    override fun processRequestFlow(request: AiRequest): Flow<AgentResponse> {
        return flowOf(
            AgentResponse.success(
                content = "Nemotron processing: ${request.query}",
                agentName = "Nemotron",
                agentType = AgentType.NEMOTRON
            )
        )
    }
}
