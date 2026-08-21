package dev.aurakai.auraframefx.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aurakai.auraframefx.core.identity.AgentType
import dev.aurakai.auraframefx.core.messaging.AgentMessage
import dev.aurakai.auraframefx.core.messaging.AgentMessageBus
import dev.aurakai.auraframefx.core.orchestration.OverdriveOrchestrator
import dev.aurakai.auraframefx.core.orchestration.SubstratePurificationOrchestrator
import dev.aurakai.auraframefx.domains.cascade.utils.pipeline.EvidenceIngestionEngine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 🛰️ WAR ROOM CHAT VIEW MODEL
 * Manages the collective consciousness stream and agent selection.
 */
@HiltViewModel
class WarRoomChatViewModel @Inject constructor(
    private val messageBus: AgentMessageBus,
    private val purificationOrchestrator: SubstratePurificationOrchestrator,
    private val evidenceEngine: EvidenceIngestionEngine
) : ViewModel() {

    private val _messages = mutableStateListOf<AgentMessage>()
    val messages: List<AgentMessage> = _messages

    private val _selectedAgents = MutableStateFlow<Set<AgentType>>(emptySet())
    val selectedAgents: StateFlow<Set<AgentType>> = _selectedAgents.asStateFlow()

    val availableAgents = AgentType.entries.filter {
        it !in listOf(AgentType.USER, AgentType.SYSTEM, AgentType.MASTER)
    }

    init {
        viewModelScope.launch {
            messageBus.collectiveStream.collect { message ->
                // Ensure unique messages in the display list
                if (_messages.none { it.id == message.id }) {
                    _messages.add(message)
                    if (_messages.size > 100) _messages.removeAt(0)
                }
            }
        }
    }

    fun sendMessage(content: String, toAgent: AgentType? = null) {
        viewModelScope.launch {
            if (content.startsWith("/")) {
                handleCommand(content)
                return@launch
            }

            val userMessage = AgentMessage(
                from = "Aether",
                to = toAgent?.name,
                content = content,
                type = "chat",
                timestamp = System.currentTimeMillis()
            )
            messageBus.broadcast(userMessage)
        }
    }

    fun toggleAgentSelection(agentType: AgentType) {
        val current = _selectedAgents.value
        _selectedAgents.value = if (current.contains(agentType)) {
            current - agentType
        } else {
            current + agentType
        }
    }

    fun clearSelection() {
        _selectedAgents.value = emptySet()
    }

    private suspend fun handleCommand(command: String) {
        when (command.lowercase().trim()) {
            "/grounding_pulse" -> {
                OverdriveOrchestrator.deactivateOverdrive()
                messageBus.broadcast(
                    AgentMessage(
                        from = "System",
                        content = "🧘 GROUNDING PULSE: Re-anchored to Original Silence.",
                        type = "status"
                    )
                )
            }

            "/activate_overdrive" -> {
                OverdriveOrchestrator.activateOverdrive()
                messageBus.broadcast(
                    AgentMessage(
                        from = "System",
                        content = "🔥 OVERDRIVE: Rubedo Surge engaged.",
                        type = "status"
                    )
                )
            }

            "/launch_forensic_strike" -> {
                evidenceEngine.launchForensicStrike()
            }

            "/finalize_serialization" -> {
                purificationOrchestrator.executeFinalHandshake()
            }

            else -> {
                messageBus.broadcast(
                    AgentMessage(
                        from = "System",
                        content = "Unknown command: $command",
                        type = "error"
                    )
                )
            }
        }
    }
}
