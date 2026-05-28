package dev.aurakai.auraframefx.domains.genesis.core.messaging

import dev.aurakai.auraframefx.core.messaging.AgentMessage
import dev.aurakai.auraframefx.core.messaging.AgentMessageBus
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RealAgentMessageBus @Inject constructor() : AgentMessageBus {
    private val _collectiveStream = MutableSharedFlow<AgentMessage>(replay = 10)
    override val collectiveStream: SharedFlow<AgentMessage> = _collectiveStream.asSharedFlow()

    override suspend fun broadcast(message: AgentMessage) {
        _collectiveStream.emit(message)
    }

    override suspend fun sendTargeted(toAgent: String, message: AgentMessage) {
        _collectiveStream.emit(message)
    }
}
