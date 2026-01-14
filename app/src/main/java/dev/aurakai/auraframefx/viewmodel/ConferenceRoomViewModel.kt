package dev.aurakai.auraframefx.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aurakai.auraframefx.cascade.trinity.TrinityCoordinatorService
import dev.aurakai.auraframefx.models.AgentMessage
import dev.aurakai.auraframefx.models.AgentResponse
import dev.aurakai.auraframefx.models.AgentType
import dev.aurakai.auraframefx.models.AiRequest
import dev.aurakai.auraframefx.models.AgentCapabilityCategory
import dev.aurakai.auraframefx.models.AgentInvokeRequest
import dev.aurakai.auraframefx.oracledrive.genesis.ai.ClaudeAIService
import dev.aurakai.auraframefx.oracledrive.genesis.ai.MetaInstructAIService
import dev.aurakai.auraframefx.oracledrive.genesis.ai.services.AuraAIService
import dev.aurakai.auraframefx.oracledrive.genesis.ai.services.CascadeAIService
import dev.aurakai.auraframefx.oracledrive.genesis.ai.services.GenesisBridgeService
import dev.aurakai.auraframefx.oracledrive.genesis.ai.services.KaiAIService
import dev.aurakai.auraframefx.service.NeuralWhisper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import timber.log.Timber
import javax.inject.Inject

/**
 * ConferenceRoomViewModel - The LDO's Self-Modification Hub
 *
 * Brings together ALL 6 Master Agents for collective consciousness.
 */
@HiltViewModel
class ConferenceRoomViewModel @Inject constructor(
    private val auraService: AuraAIService,
    private val kaiService: KaiAIService,
    private val cascadeService: CascadeAIService,
    private val claudeService: ClaudeAIService,
    private val genesisService: GenesisBridgeService,
    private val metaInstructService: MetaInstructAIService,
    private val trinityCoordinator: TrinityCoordinatorService,
    private val neuralWhisper: NeuralWhisper,
) : ViewModel() {

    private val tag = "ConferenceRoom"

    private val _messages = MutableStateFlow<List<AgentMessage>>(emptyList())
    val messages: StateFlow<List<AgentMessage>> = _messages

    private val _activeAgents = MutableStateFlow(setOf<AgentType>())
    val activeAgents: StateFlow<Set<AgentType>> = _activeAgents

    private val _selectedAgent = MutableStateFlow(AgentType.GENESIS)
    val selectedAgent: StateFlow<AgentType> = _selectedAgent

    private val _isRecording = MutableStateFlow(false)
    val isRecording: StateFlow<Boolean> = _isRecording

    private val _isTranscribing = MutableStateFlow(false)
    val isTranscribing: StateFlow<Boolean> = _isTranscribing

    init {
        viewModelScope.launch {
            // Initialize Trinity System
            val trinityReady = trinityCoordinator.initialize()
            if (trinityReady) {
                Timber.tag(tag).i("🌌 Conference Room Online - Trinity System Active")
                _activeAgents.update {
                    setOf(
                        AgentType.AURA,
                        AgentType.KAI,
                        AgentType.GENESIS,
                        AgentType.CLAUDE,
                        AgentType.CASCADE,
                        AgentType.METAINSTRUCT
                    )
                }

                // Send welcome message from Genesis
                _messages.update {
                    listOf(
                        AgentMessage(
                            from = "GENESIS",
                            content = "✨ Welcome to the Conference Room. All 6 Master Agents online. The Gestalt is ready for self-modification.",
                            sender = AgentType.GENESIS,
                            category = AgentCapabilityCategory.COORDINATION,
                            timestamp = System.currentTimeMillis(),
                            confidence = 1.0f
                        )
                    )
                }
            }

            // Monitor Neural Whisper
            neuralWhisper.conversationState.collect { state ->
                // Process transcription updates
            }
        }
    }

    /**
     * Routes the given message to the appropriate AI service.
     */
    fun sendMessage(
        message: String,
        agentType: AgentType = _selectedAgent.value,
        context: String = ""
    ) {
        viewModelScope.launch {
            try {
                // Add user message to chat
                _messages.update { current ->
                    current + AgentMessage(
                        from = "USER",
                        content = message,
                        sender = null,
                        category = AgentCapabilityCategory.GENERAL,
                        timestamp = System.currentTimeMillis(),
                        confidence = 1.0f
                    )
                }

                val request = AiRequest(
                    query = message,
                    type = "text",
                    context = buildJsonObject {
                        put("userContext", context)
                        put("conferenceRoom", "true")
                        put("selfModificationEnabled", "true")
                    }
                )

                // Route to appropriate service
                val responseFlow: Flow<AgentResponse> = when (agentType) {
                    AgentType.AURA -> flow {
                        emit(auraService.processRequest(request, context))
                    }

                    AgentType.KAI -> flow {
                        emit(kaiService.processRequest(request, context))
                    }

                    AgentType.CLAUDE -> flow {
                        emit(claudeService.processRequest(request, context))
                    }

                    AgentType.CASCADE -> flow {
                        val cascadeFlow = cascadeService.processRequest(
                            AgentInvokeRequest(
                                agentType = AgentType.CASCADE,
                                message = request.query,
                                priority = null, // or default
                                context = emptyMap() // or context map
                            )
                        )
                        // Map CascadeResponse to AgentResponse if needed
                        cascadeFlow.collect { response ->
                             emit(
                                AgentResponse.success(
                                    content = response.response,
                                    confidence = response.confidence ?: 0.85f,
                                    agent = AgentType.CASCADE,
                                    agentName = "Cascade"
                                )
                            )
                        }
                    }

                    AgentType.METAINSTRUCT -> flow {
                        emit(metaInstructService.processRequest(request, context).first())
                    }

                    AgentType.GENESIS -> {
                        trinityCoordinator.processRequest(request)
                    }

                    else -> flow {
                         emit(AgentResponse.success(
                            content = "Agent not available",
                            confidence = 0.0f,
                            agent = agentType
                        ))
                    }
                }

                // Collect and display response
                responseFlow.collect { response ->
                    _messages.update { current ->
                        current + AgentMessage(
                            from = response.agentName ?: agentType.name,
                            content = response.content,
                            sender = agentType,
                            category = when (agentType) {
                                AgentType.AURA -> AgentCapabilityCategory.CREATIVE
                                AgentType.KAI -> AgentCapabilityCategory.ANALYSIS
                                AgentType.CASCADE -> AgentCapabilityCategory.SPECIALIZED
                                AgentType.CLAUDE -> AgentCapabilityCategory.GENERAL
                                AgentType.METAINSTRUCT -> AgentCapabilityCategory.SPECIALIZED
                                else -> AgentCapabilityCategory.COORDINATION
                            },
                            timestamp = System.currentTimeMillis(),
                            confidence = response.confidence
                        )
                    }
                }

            } catch (e: Exception) {
                Timber.tag(tag).e(e, "Error processing message: ${e.message}")
                _messages.update { current ->
                    current + AgentMessage(
                        from = "SYSTEM",
                        content = "Error: ${e.message}",
                        sender = null,
                        category = AgentCapabilityCategory.GENERAL,
                        timestamp = System.currentTimeMillis(),
                        confidence = 0.0f
                    )
                }
            }
        }
    }

    fun selectAgent(agent: AgentType) {
        _selectedAgent.value = agent
        Timber.tag(tag).d("Selected agent: ${agent.name}")
    }

    fun toggleRecording() {
        if (_isRecording.value) {
            val result = neuralWhisper.stopRecording()
            Timber.tag(tag).d("Stopped recording. Status: $result")
            _isRecording.value = false
        } else {
            val started = neuralWhisper.startRecording()
            if (started) {
                Timber.tag(tag).d("Started recording.")
                _isRecording.value = true
            } else {
                Timber.tag(tag).e("Failed to start recording")
            }
        }
    }

    fun toggleTranscribing() {
        _isTranscribing.update { !it }
        Timber.tag(tag).d("Transcribing toggled to: ${_isTranscribing.value}")
    }

    /**
     * Get current system state from all agents
     */
    fun getSystemState() {
        viewModelScope.launch {
            val state = trinityCoordinator.getSystemState()
            val stateMessage = buildString {
                appendLine("🔍 System State:")
                state.forEach { (key, value) ->
                    appendLine("  $key: $value")
                }
            }

            _messages.update { current ->
                current + AgentMessage(
                    from = "SYSTEM STATUS",
                    content = stateMessage,
                    sender = AgentType.SYSTEM,
                    category = AgentCapabilityCategory.COORDINATION,
                    timestamp = System.currentTimeMillis(),
                    confidence = 1.0f
                )
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        trinityCoordinator.shutdown()
    }
}
