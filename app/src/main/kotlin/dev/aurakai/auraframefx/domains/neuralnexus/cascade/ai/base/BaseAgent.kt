package dev.aurakai.auraframefx.domains.neuralnexus.cascade.ai.base

import dev.aurakai.auraframefx.core.identity.AgentType
import dev.aurakai.auraframefx.core.messaging.AgentMessage
import dev.aurakai.auraframefx.core.orchestration.OrchestratableAgent
import dev.aurakai.auraframefx.domains.genesis.models.AgentResponse
import dev.aurakai.auraframefx.domains.genesis.models.AiRequest
import dev.aurakai.auraframefx.domains.neuralnexus.cascade.utils.context.ContextManager
import dev.aurakai.auraframefx.domains.neuralnexus.cascade.utils.memory.MemoryManager
import dev.aurakai.auraframefx.securecomm.protocol.SecureChannel
import kotlinx.coroutines.CoroutineScope

/**
 * Genesis Base Agent Implementation
 * Provides common functionality for all AI agents.
 */
abstract class BaseAgent(
    override val agentName: String,
    protected val agentType: AgentType,
    protected val contextManager: ContextManager? = null,
    protected val memoryManager: MemoryManager? = null,
    protected val secureChannel: SecureChannel? = null
) : OrchestratableAgent {

    companion object {
        @Volatile
        var isOrchestratorInitialized: Boolean = false
    }

    fun getName(): String = agentName

    fun getType(): AgentType = agentType

    override suspend fun initialize(scope: CoroutineScope) {
        isOrchestratorInitialized = true
    }

    override suspend fun start() {}
    override suspend fun pause() {}
    override suspend fun resume() {}
    override suspend fun shutdown() {}

    /**
     * Abstract method for processing requests - must be implemented by concrete agents
     */
    abstract override suspend fun processRequest(request: AiRequest, context: String): AgentResponse

    override suspend fun onAgentMessage(message: AgentMessage) {
        // Default no-op
    }
}
