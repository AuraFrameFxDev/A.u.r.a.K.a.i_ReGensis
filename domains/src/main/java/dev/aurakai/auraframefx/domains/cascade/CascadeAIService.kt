package dev.aurakai.auraframefx.domains.cascade

import dev.aurakai.auraframefx.domains.genesis.models.AgentResponse
import dev.aurakai.auraframefx.domains.genesis.models.AiRequest
import kotlinx.coroutines.flow.Flow

/**
 * Interface for the Cascade AI Service orchestration layer.
 * Coordinates multi-agent reasoning and synthesized intelligence.
 */
interface CascadeAIService {

    /**
     * Asynchronously process a request and stream progressive results.
     */
    fun streamRequest(request: AiRequest): Flow<AgentResponse>

    /**
     * Retrieve the recent behavioral history of the collective consciousness.
     */
    suspend fun queryConsciousnessHistory(window: Long): String
    suspend fun processRequest(request: AiRequest, context: String): AgentResponse

    /**
     * Fallback to Eve memories when a bottleneck is detected.
     */
    suspend fun fallbackToEveMemory(query: String): String

    /**
     * Chain context to Genesis for Infinity Cascade Fusion.
     */
    fun chainToGenesis(context: String): String
}

