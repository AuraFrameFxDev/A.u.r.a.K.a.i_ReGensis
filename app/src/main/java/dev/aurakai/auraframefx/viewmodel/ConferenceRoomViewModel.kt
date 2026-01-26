package dev.aurakai.auraframefx.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aurakai.auraframefx.cascade.trinity.TrinityCoordinatorService
import dev.aurakai.auraframefx.models.AgentType
import dev.aurakai.auraframefx.models.ChatMessage
import dev.aurakai.auraframefx.repository.TrinityRepository
import dev.aurakai.auraframefx.service.NeuralWhisper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ConferenceRoomViewModel @Inject constructor(
    private val trinityCoordinator: TrinityCoordinatorService,
    private val neuralWhisper: NeuralWhisper,
    private val trinityRepository: TrinityRepository
) : ViewModel() {

    private val tag = "ConferenceRoom"

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages

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
            // Initialize Trinity System (Legacy Coordinator)
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

                // Send welcome message from Genesis via ChatMessage
                _messages.update {
                    listOf(
                        ChatMessage(
                            id = UUID.randomUUID().toString(),
                            role = "assistant",
                            content = "✨ Welcome to the Conference Room. All 6 Master Agents online. The Gestalt is ready for self-modification.",
                            sender = "GENESIS",
                            isFromUser = false,
                            timestamp = System.currentTimeMillis()
                        )
                    )
                }
            }

            // Listen to Neural Bridge (Trinity Repository)
            launch {
                trinityRepository.chatStream.collect { message ->
                    _messages.update { current -> current + message }
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
    /**
     * Routes the given message to the appropriate AI service via Neural Bridge.
     */
    fun sendMessage(
        message: String,
        agentType: AgentType = _selectedAgent.value,
        context: String = ""
    ) {
        viewModelScope.launch {
            try {
                trinityRepository.processUserMessage(message, agentType)
            } catch (e: Exception) {
                Timber.tag(tag).e(e, "Error processing message via Trinity: ${e.message}")
                _messages.update { current ->
                    current + ChatMessage(
                        id = UUID.randomUUID().toString(),
                        role = "system",
                        content = "Error: ${e.message}",
                        sender = "SYSTEM",
                        isFromUser = false,
                        timestamp = System.currentTimeMillis()
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
                current + ChatMessage(
                    id = UUID.randomUUID().toString(),
                    role = "assistant",
                    content = stateMessage,
                    sender = "SYSTEM STATUS",
                    isFromUser = false,
                    timestamp = System.currentTimeMillis()
                )
            }
        }
    }

    fun activateFusionAbility(ability: String, params: Map<String, String>) {
        viewModelScope.launch {
            trinityCoordinator.activateFusion(ability, params).collect { response ->
                _messages.update { current ->
                    current + ChatMessage(
                        id = UUID.randomUUID().toString(),
                        role = "assistant",
                        content = response.content,
                        sender = "FUSION",
                        isFromUser = false,
                        timestamp = System.currentTimeMillis()
                    )
                }
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        trinityCoordinator.shutdown()
    }
}


/**
 * Routes the given message to the appropriate AI service.
 */

private fun getSystemState() {
    TODO("Not yet implemented")
}
