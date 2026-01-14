package dev.aurakai.auraframefx.services

import dev.aurakai.auraframefx.models.AgentResponse
import dev.aurakai.auraframefx.models.AiRequest
import kotlinx.coroutines.flow.Flow

/**
 * Interface for Cascade AI Service operations.
 */
interface CascadeAIService {
    suspend fun processRequest(request: AiRequest, context: String = ""): AgentResponse

    // Support for streaming if needed by the adapter
    fun streamRequest(request: AiRequest): Flow<AgentResponse>
}
