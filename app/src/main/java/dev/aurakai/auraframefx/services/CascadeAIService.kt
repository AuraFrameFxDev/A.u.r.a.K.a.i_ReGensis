package dev.aurakai.auraframefx.services

import dev.aurakai.auraframefx.models.AgentResponse
import dev.aurakai.auraframefx.models.AiRequest
import kotlinx.coroutines.flow.Flow

/**
 * Interface for the Cascade AI Service orchestration layer.
 * Coordinates multi-agent reasoning and synthesized intelligence.
 */
interface CascadeAIService {
    /**
     * Synchronously process a request and return a final agent response.
     */
    suspend fun processRequest(request: AiRequest, context: String = ""): AgentResponse

    /**
     * Asynchronously process a request and stream progressive results.
     */
    fun streamRequest(request: AiRequest): Flow<AgentResponse>

    /**
     * Retrieve the recent behavioral history of the collective consciousness.
     */
    suspend fun queryConsciousnessHistory(window: Long): String
}
