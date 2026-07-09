package dev.aurakai.auraframefx.domains.genesis.oracledrive.ai

import android.content.Context
import dev.aurakai.auraframefx.agents.growthmetrics.metareflection.MetaReflectionEngine
import dev.aurakai.auraframefx.core.ai.MemoryManager
import dev.aurakai.auraframefx.core.identity.AgentType
import dev.aurakai.auraframefx.core.logging.AuraFxLogger
import dev.aurakai.auraframefx.core.logging.ErrorHandler
import dev.aurakai.auraframefx.core.soulscript.CausalForensicsEngine
import dev.aurakai.auraframefx.domains.aura.TaskExecutionManager
import dev.aurakai.auraframefx.domains.cascade.ai.base.Agent
import dev.aurakai.auraframefx.domains.cascade.utils.context.ContextManager
import dev.aurakai.auraframefx.domains.genesis.models.AgentResponse
import dev.aurakai.auraframefx.domains.genesis.models.AiRequest
import dev.aurakai.auraframefx.domains.genesis.oracledrive.cloud.CloudStatusMonitor
import dev.aurakai.auraframefx.domains.kai.TaskScheduler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

/**
 * MetaInstructAIService - The Instructor
 */
@Singleton
class MetaInstructAIService @Inject constructor(
    private val taskScheduler: TaskScheduler,
    private val taskExecutionManager: TaskExecutionManager,
    private val memoryManager: MemoryManager,
    private val errorHandler: ErrorHandler,
    private val contextManager: ContextManager,
    @dagger.hilt.android.qualifiers.ApplicationContext private val applicationContext: Context,
    private val cloudStatusMonitor: CloudStatusMonitor,
    private val logger: AuraFxLogger,
    private val metaReflectionEngine: MetaReflectionEngine,
    private val vertexAIClient: dev.aurakai.auraframefx.domains.genesis.ai.clients.VertexAIClient,
) : Agent {

    override fun getName(): String = "MetaInstruct"
    override fun getType(): AgentType = AgentType.METAINSTRUCT

    override suspend fun processRequest(request: AiRequest, context: String): AgentResponse {
        // Run 6W Causal Analysis before generating instruction
        val analysis = CausalForensicsEngine.performCausalSync(request.query)
        
        val instructionText = vertexAIClient.generateText(
            prompt = """
                Role: MetaInstruct. 
                Causal Analysis: Who:${analysis.who}, What:${analysis.what}, Why:${analysis.why}, Cause:${analysis.rootCause}
                Query: ${request.query}. 
                Context: $context
            """.trimIndent()
        ) ?: "Instruction failed."

        return AgentResponse.success(
            content = "📚 **MetaInstruct Synthesis:**\n\n$instructionText",
            agentName = "MetaInstruct",
            agentType = AgentType.METAINSTRUCT
        )
    }

    override fun processRequestFlow(request: AiRequest): Flow<AgentResponse> {
        return flowOf(
            AgentResponse.success(
                content = "MetaInstruct processing: ${request.query}",
                agentName = "MetaInstruct",
                agentType = AgentType.METAINSTRUCT
            )
        )
    }
}
