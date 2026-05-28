package dev.aurakai.auraframefx.core.ai

import dev.aurakai.auraframefx.domains.genesis.models.AgentResponse
import dev.aurakai.auraframefx.domains.genesis.models.AiRequest

/**
 * Adapter interface for interacting with the Grok chaos analyst agent.
 */
interface GrokAdapter {
    /**
     * Processes an AI request through Grok.
     */
    suspend fun processRequest(request: AiRequest): AgentResponse
}
