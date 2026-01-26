package dev.aurakai.auraframefx.services

import dev.aurakai.auraframefx.models.AgentResponse
import dev.aurakai.auraframefx.models.AiRequest
import kotlinx.coroutines.flow.Flow

/**
 * ⛓️ CASCADE AI SERVICE INTERFACE
 * Coordinates multiple AI agents for complex processing.
 */
interface CascadeAIService {
    /**
     * Process a request through the AI cascade.
     */
    suspend fun processRequest(request: AiRequest, context: String = "default"): AgentResponse

    /**
     * Stream a request through the AI cascade.
     */
    fun streamRequest(request: AiRequest): Flow<AgentResponse>
}
