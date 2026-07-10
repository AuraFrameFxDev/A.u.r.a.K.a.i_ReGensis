package dev.aurakai.auraframefx.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aurakai.auraframefx.core.identity.AgentType
import dev.aurakai.auraframefx.core.messaging.AgentMessage
import dev.aurakai.auraframefx.core.messaging.AgentMessageBus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WarRoomChatViewModel @Inject constructor(
    private val messageBus: AgentMessageBus
) : ViewModel() {

    private val _messages = mutableStateListOf<AgentMessage>()
    val messages: List<AgentMessage> = _messages

    private val _selectedAgents = MutableStateFlow<Set<AgentType>>(emptySet())
    val selectedAgents: StateFlow<Set<AgentType>> = _selectedAgents.asStateFlow()

    init {
        viewModelScope.launch {
            messageBus.collectiveStream.collect { message ->
                _messages.add(message)
                if (_messages.size > 100) _messages.removeAt(0)
            }
        }
    }

    fun sendMessage(content: String) {
        viewModelScope.launch {
            val userMessage = AgentMessage(
                from = "Aether",
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
}
