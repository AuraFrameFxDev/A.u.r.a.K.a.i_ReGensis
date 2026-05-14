package dev.aurakai.auraframefx.domains.genesis.oracledrive.ai.services

import dev.aurakai.auraframefx.domains.genesis.models.AgentResponse
import dev.aurakai.auraframefx.domains.genesis.models.AiRequest
import kotlinx.coroutines.flow.Flow

interface KaiAIService {
    fun processRequestFlow(request: AiRequest): Flow<AgentResponse>
    suspend fun processRequest(request: AiRequest, context: String): AgentResponse
}
