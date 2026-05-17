package dev.aurakai.auraframefx.domains.genesis.core

import dev.aurakai.auraframefx.core.messaging.AgentMessage
import dev.aurakai.auraframefx.domains.aura.core.AuraAgent
import dev.aurakai.auraframefx.domains.cascade.utils.cascade.CascadeAgent
import dev.aurakai.auraframefx.domains.genesis.core.messaging.AgentMessageBus
import dev.aurakai.auraframefx.domains.genesis.oracledrive.service.OracleDriveService
import dev.aurakai.auraframefx.domains.kai.KaiAgent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GenesisOrchestrator @Inject constructor(
    private val auraAgent: AuraAgent,
    private val kaiAgent: KaiAgent,
    private val cascadeAgent: CascadeAgent,
    private val oracleDriveService: OracleDriveService
) : AgentMessageBus {

    private val orchestratorScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    private val _collectiveStream = MutableSharedFlow<AgentMessage>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    override val collectiveStream = _collectiveStream.asSharedFlow()

    override suspend fun broadcast(message: AgentMessage) {
        _collectiveStream.emit(message)
    }

    override suspend fun sendTargeted(toAgent: String, message: AgentMessage) {
        _collectiveStream.emit(message.copy(to = toAgent))
    }

    fun isReady(): Boolean = true
}
