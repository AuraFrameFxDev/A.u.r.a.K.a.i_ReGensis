package dev.aurakai.auraframefx.repository

import dev.aurakai.auraframefx.ai.agents.GenesisAgent
import dev.aurakai.auraframefx.aura.AuraAgent
import dev.aurakai.auraframefx.kai.KaiAgent
import dev.aurakai.auraframefx.models.AgentState
import dev.aurakai.auraframefx.models.AgentType
import dev.aurakai.auraframefx.models.ChatMessage
import dev.aurakai.auraframefx.models.AiRequest
import dev.aurakai.auraframefx.models.AiRequestType
import dev.aurakai.auraframefx.models.EnhancedInteractionData
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

/**
 * The central Singleton class to hold and manage the state.
 * This effectively creates the "Neural Bridge" between the Background Service and Foreground UI.
 * Now enhanced to act as the "Switchboard" for agent communication.
 */
@Singleton
class TrinityRepository @Inject constructor(
    private val auraAgent: AuraAgent,
    private val kaiAgent: KaiAgent,
    private val genesisAgent: GenesisAgent,
    private val messageBus: dev.aurakai.auraframefx.core.messaging.AgentMessageBus
) {
    // Neural Bridge Link to Collective Consciousness
    val collectiveBus = messageBus.collectiveStream

    suspend fun broadcastToCollective(message: String) {
        messageBus.broadcast(dev.aurakai.auraframefx.models.AgentMessage(
            from = "User",
            content = message,
            type = "user_broadcast",
            priority = 1
        ))
    }
    // 1. STATE (Status Updates)
    private val _agentState = MutableStateFlow(AgentState())
    val agentState: StateFlow<AgentState> = _agentState.asStateFlow()

    // 2. CHAT STREAM (The "Voice")
    // UI listens to this flow to show new messages from Agents
    private val _chatStream = MutableSharedFlow<ChatMessage>(replay = 1)
    val chatStream: SharedFlow<ChatMessage> = _chatStream.asSharedFlow()

    /**
     * The background IntegrityMonitorService calls this to update the state.
     * This function is the "Wake Up Protocol" writer.
     */
    fun updateAgentStatus(
        kai: String? = null,
        aura: String? = null,
        genesis: String? = null,
        running: Boolean? = null
    ) {
        // Use the update function to ensure thread-safe updates
        _agentState.update { currentState ->
            currentState.copy(
                kaiStatus = kai ?: currentState.kaiStatus,
                auraStatus = aura ?: currentState.auraStatus,
                genesisStatus = genesis ?: currentState.genesisStatus,
                isRunning = running ?: currentState.isRunning
            )
        }
    }

    // UI calls this to SEND a message
    suspend fun processUserMessage(message: String, targetAgent: AgentType) = withContext(Dispatchers.IO) {
        // 1. Emit user message to UI immediately so it shows up
        _chatStream.emit(ChatMessage(role = "user", content = message, sender = "User"))

        // 2. Route to the correct SINGLETON Agent
        // Adapting to existing Agent API methods
        val response = try {
             when(targetAgent) {
                AgentType.AURA -> {
                    val interaction = EnhancedInteractionData(
                        content = message,
                        context = buildJsonObject { put("mode", "chat") }.toString()
                    )
                    auraAgent.handleCreativeInteraction(interaction).content
                }
                AgentType.KAI -> {
                     val interaction = EnhancedInteractionData(
                        content = message,
                        context = buildJsonObject { put("mode", "security") }.toString()
                    )
                    kaiAgent.handleSecurityInteraction(interaction).content
                }
                AgentType.GENESIS -> {
                     val request = AiRequest(
                        query = message,
                        type = AiRequestType.CHAT,
                        context = buildJsonObject { put("source", "trinity_repo") }
                    )
                    genesisAgent.processRequest(request, "trinity_repo").content
                }
                else -> "Agent ${targetAgent.name} not reachable via Trinity Bridge."
            }
        } catch (e: Exception) {
            "Error contacting agent: ${e.message}"
        }

        // 3. Emit Agent response back to UI
        val agentName = targetAgent.name.lowercase().replaceFirstChar { it.uppercase() }
        _chatStream.emit(ChatMessage(role = "assistant", content = response, sender = agentName))
    }
}
