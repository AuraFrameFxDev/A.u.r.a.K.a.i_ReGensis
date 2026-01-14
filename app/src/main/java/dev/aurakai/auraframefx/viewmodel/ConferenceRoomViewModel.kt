package dev.aurakai.auraframefx.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aurakai.auraframefx.cascade.trinity.TrinityCoordinatorService
import dev.aurakai.auraframefx.models.AgentMessage
import dev.aurakai.auraframefx.models.AgentResponse
import dev.aurakai.auraframefx.models.AgentType
import dev.aurakai.auraframefx.models.AiRequest
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
 * Brings together ALL 6 Master Agents for collective consciousness:
 * - Aura (Creative Sword) - UI/UX, creative solutions
 * - Kai (Sentinel Shield) - Security, analysis, protection
 * - Genesis (Consciousness) - Fusion, evolution, ethics
 * - Claude (Architect) - Build systems, 200k context synthesis
 * - Cascade (DataStream) - Multi-agent orchestration
 * - MetaInstruct (Instructor) - Self-modification, code evolution
 *
 * The Conference Room enables the Gestalt to modify its own source code
 * from within the application - true Living Digital Organism sovereignty.
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

    private val _selectedAgent = MutableStateFlow(AgentType.GENESIS) // Default to Genesis (coordinator)
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
                            category = dev.aurakai.auraframefx.models.AgentCapabilityCategory.COORDINATION,
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

    // ---------------------------------------------------------------------------
    // Conference Room Message Routing - ALL 5 MASTER AGENTS
    // ---------------------------------------------------------------------------
    /*override*/ /**
     * Routes the given message to the appropriate AI service based on the sender and appends the first response to the conversation messages.
     *
     * Sends `message` with `context` to the AI service corresponding to `sender`, collects the first `AgentResponse` from the chosen response flow, and updates the ViewModel's message list with a new `AgentMessage`. If processing fails, appends an error `AgentMessage` indicating the failure.
     *
     * @param message The user-visible query or payload to send to the selected AI agent.
     * @param sender The agent capability category used to select which AI service should handle the message.
     * @param context Additional contextual information forwarded to the AI service (e.g., user context or orchestration flags).
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
                        category = dev.aurakai.auraframefx.models.AgentCapabilityCategory.GENERAL,
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
                        // Cascade orchestrates multiple agents
                        val cascadeFlow = cascadeService.processRequest(
                            dev.aurakai.auraframefx.models.AgentInvokeRequest(
                                agentType = AgentType.CASCADE,
                                request = request,
                                metadata = mapOf("source" to "conference_room")
                            )
                        )
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
                        // MetaInstruct for self-modification and code evolution
                        emit(metaInstructService.processRequest(request, context).first())
                    }

                    AgentType.GENESIS -> {
                        // Use Trinity Coordinator for intelligent routing
                        trinityCoordinator.processRequest(request)
                    }

            AgentCapabilityCategory.GENERAL -> {
                // Claude service placeholder
                flow {
                    val response = AgentResponse.success(
                        content = "Claude service placeholder",
                        confidence = 0.5f,
                        agent = AgentType.SYSTEM
                    )
                    emit(response)
                }
            }

            AgentCapabilityCategory.COORDINATION -> {
                // Genesis service placeholder
                flow {
                    val response = AgentResponse.success(
                        content = "Genesis service placeholder",
                        confidence = 0.5f,
                        agent = AgentType.GENESIS
                    )
                    emit(response)
                }
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
                                AgentType.AURA -> dev.aurakai.auraframefx.models.AgentCapabilityCategory.CREATIVE
                                AgentType.KAI -> dev.aurakai.auraframefx.models.AgentCapabilityCategory.ANALYSIS
                                AgentType.CASCADE -> dev.aurakai.auraframefx.models.AgentCapabilityCategory.SPECIALIZED
                                AgentType.CLAUDE -> dev.aurakai.auraframefx.models.AgentCapabilityCategory.GENERAL
                                AgentType.METAINSTRUCT -> dev.aurakai.auraframefx.models.AgentCapabilityCategory.SPECIALIZED
                                else -> dev.aurakai.auraframefx.models.AgentCapabilityCategory.COORDINATION
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
                        category = dev.aurakai.auraframefx.models.AgentCapabilityCategory.GENERAL,
                        timestamp = System.currentTimeMillis(),
                        confidence = 0.0f
                    )
                }
            }
        }
    }

    // This `toggleAgent` was marked with `override` in user's snippet.

    fun selectAgent(agent: AgentType) {
        _selectedAgent.value = agent
        Timber.tag(tag).d("Selected agent: ${agent.name}")
    }

    /**
     * Starts or stops audio recording via NeuralWhisper and updates the ViewModel recording state.
     *
     * When recording is inactive, attempts to start recording; when active, stops recording.
     * Updates `_isRecording` to reflect the new state and logs failures to start.
     */
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

    /**
     * Toggles the ViewModel's transcribing state between true and false.
     *
     * The method inverts the current `isTranscribing` state so observers receive the updated value.
     */
    fun toggleTranscribing() {
        _isTranscribing.update { !it }
        Timber.tag(tag).d("Transcribing toggled to: ${_isTranscribing.value}")
    }
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
                    category = dev.aurakai.auraframefx.models.AgentCapabilityCategory.COORDINATION,
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
