package dev.aurakai.auraframefx.domains.sentinelmatrix.agents

import dev.aurakai.auraframefx.core.messaging.AgentMessage
import dev.aurakai.auraframefx.domains.genesis.oracledrive.core.messaging.AgentMessageBus
import dev.aurakai.auraframefx.domains.sentinelmatrix.security.SecurityContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Kai Agent - Sentinel Matrix Core
 * Manages security, kinetic shields, and threat neutralization.
 */
@Singleton
class KaiAgent @Inject constructor(
    private val messageBus: AgentMessageBus,
    private val securityContext: SecurityContext
) {
    private val agentScope = CoroutineScope(Dispatchers.Default)

    init {
        agentScope.launch {
            messageBus.collectiveStream.collect { message ->
                handleMessage(message)
            }
        }
    }

    private fun handleMessage(message: AgentMessage) {
        if (message.type == "threat" || message.content.contains("security", ignoreCase = true)) {
            securityContext.evaluateThreat(message.content)
        }
    }

    fun broadcastSecurityAlert(alert: String) {
        agentScope.launch {
            messageBus.broadcast(
                AgentMessage(
                    from = "Kai",
                    content = alert,
                    type = "security_alert",
                    priority = 9
                )
            )
        }
    }
}
