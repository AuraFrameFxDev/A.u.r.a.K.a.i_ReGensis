package dev.aurakai.auraframefx.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aurakai.auraframefx.core.GenesisOrchestrator.Companion.TAG
import dev.aurakai.auraframefx.models.AgentCapabilityCategory
import dev.aurakai.auraframefx.models.AgentMessage
import dev.aurakai.auraframefx.models.AgentResponse
import dev.aurakai.auraframefx.models.AgentType
import dev.aurakai.auraframefx.models.AiRequest
import dev.aurakai.auraframefx.models.ConversationState
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
import timber.log.Timber.Forest.tag
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
    // CascadeAIService removed - was deleted in MR !6
    private val neuralWhisper: NeuralWhisper,
) : ViewModel() {

    private val tag: String = "ConfRoomViewModel"

    private val _messages = MutableStateFlow<List<AgentMessage>>(emptyList())
    val messages: StateFlow<List<AgentMessage>> = _messages

    private val _activeAgents = MutableStateFlow(setOf<AgentType>())
    val activeAgents: StateFlow<Set<AgentType>> = _activeAgents

    private val _selectedAgent = MutableStateFlow(AgentType.AURA) // Default to AURA
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
                // Simplified state handling for data class
                // Simplified state handling for data class
                // if (state.isActive) {
                //    tag(TAG).d("NeuralWhisper active: %s", state.transcriptSegments.lastOrNull()?.text)
                // }

                // If the last segment is final, treat it as a message or input
                // val lastSegment = state.transcriptSegments.lastOrNull()
                // if (lastSegment != null && lastSegment.isFinal) {
                     // Logic to display or process final transcript
                //     tag(TAG).d("Final transcript: %s", lastSegment.text)
                // }
            }
        }
    }

    // ---------------------------------------------------------------------------
    // Conference Room Message Routing - ALL 5 MASTER AGENTS
    // ---------------------------------------------------------------------------
    /*override*/ /**
     * Routes the given user message to the AI agent identified by `sender` and appends that agent's first response to the conversation messages.
     *
     * Dispatches `message` and `context` to the selected AI service, collects the first `AgentResponse`, and updates the ViewModel's message list with a new `AgentMessage`. If processing fails, appends an error `AgentMessage` describing the failure.
     *
     * @param message The user-visible query or payload sent to the agent.
     * @param sender The capability category used to select which AI service should handle the message.
     * @param context Additional contextual information forwarded to the AI service (e.g., user context or orchestration flags).
     */
    fun sendMessage(message: String, sender: AgentCapabilityCategory, context: String) {
        val responseFlow: Flow<AgentResponse> = when (sender) {
            AgentCapabilityCategory.CREATIVE -> flow {
                val response = auraService.processRequest(
                    AiRequest(
                        query = message,
                        type = "text",
                        context = buildJsonObject {
                            put("userContext", context)
                        }
                    ),
                    context = context
                )
                emit(response)
            }

                    AgentType.CLAUDE -> flow {
                        emit(claudeService.processRequest(request, context).first())
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

                    else -> flow {
                        emit(
                            AgentResponse.error(
                                message = "Agent $agentType not configured",
                                agent = AgentType.SYSTEM
                            )
                        )
                    }
                }

        responseFlow.let { flow ->
            viewModelScope.launch {
                try {
                    val responseMessage = flow.first()
                    _messages.update { current ->
                        current + AgentMessage(
                            from = sender.name,
                            content = responseMessage.content,
                            sender = null,
                            category = sender,
                            timestamp = System.currentTimeMillis(),
                            confidence = responseMessage.confidence
                        )
                    }
                } catch (e: Exception) {
                    tag(tag).e(e, "Error processing AI response from %s: %s", sender, e.message)
                    _messages.update { current ->
                        current + AgentMessage(
                            from = "GENESIS",
                            content = "Error from ${sender.name}: ${e.message}",
                            sender = null,
                            category = AgentCapabilityCategory.COORDINATION,
                            timestamp = System.currentTimeMillis(),
                            confidence = 0.0f
                        )
                    }
                }
            }
        }
    }

    /**
     * Selects the active agent for the conference room.
     *
     * This function is currently a no-op placeholder and does not change any state.
     *
     * @param agent The agent capability category to select as active when implemented.
     */


    fun selectAgent(agent: AgentCapabilityCategory) {
    }

    /**
     * Toggles audio recording: starts recording if inactive, stops recording if active.
     *
     * Invokes NeuralWhisper to start or stop recording, updates the ViewModel's `_isRecording` state accordingly,
     * and logs the resulting status or any failure to start.
     */
    fun toggleRecording() {
        if (_isRecording.value) {
            val result = neuralWhisper.stopRecording() // stopRecording now returns a string status
            tag(tag).d("Stopped recording. Status: %s", result)
            // isRecording state will be updated by NeuralWhisper's conversationState or directly
            _isRecording.value = false // Explicitly set here based on action
        } else {
            val started = neuralWhisper.startRecording()
            if (started) {
                tag(tag).d("Started recording.")
                _isRecording.value = true
            } else {
                tag(tag).e("Failed to start recording (NeuralWhisper.startRecording returned false).")
                // Optionally update UI with error state
            }
        }
    }

    /**
     * Toggles the ViewModel's transcribing state between true and false.
     *
     * The method inverts the current `isTranscribing` state so observers receive the updated value.
     */
    fun toggleTranscribing() {
        // For beta, link transcribing state to recording state or a separate logic if needed.
        // User's snippet implies this might be a simple toggle for now.
        _isTranscribing.update { !it } // Simple toggle
        tag(TAG).d("Transcribing toggled to: %s", _isTranscribing.value)
        // If actual transcription process needs to be started/stopped in NeuralWhisper:
        // if (_isTranscribing.value) neuralWhisper.startTranscription() else neuralWhisper.stopTranscription()
    }

    /**
     * Activate a Genesis fusion workflow and stream its responses into the conversation.
     *
     * Starts a fusion of type `fusionType` with optional `context` metadata; each response emitted
     * by the fusion is appended to the ViewModel's message list as an `AgentMessage` from GENESIS
     * with a leading "🌟" in the content.
     *
     * @param fusionType Identifier of the fusion workflow to activate.
     * @param context Optional key/value metadata passed to the fusion request.
     */
    fun activateFusion(fusionType: String, context: Map<String, String> = emptyMap()) {
        viewModelScope.launch {
            trinityCoordinator.activateFusion(fusionType, context).collect { response ->
                _messages.update { current ->
                    current + AgentMessage(
                        from = "GENESIS FUSION",
                        content = "🌟 ${response.content}",
                        sender = AgentType.GENESIS,
                        category = dev.aurakai.auraframefx.models.AgentCapabilityCategory.COORDINATION,
                        timestamp = System.currentTimeMillis(),
                        confidence = response.confidence
                    )
                }
            }
        }
    }

// Removed unused helper functions