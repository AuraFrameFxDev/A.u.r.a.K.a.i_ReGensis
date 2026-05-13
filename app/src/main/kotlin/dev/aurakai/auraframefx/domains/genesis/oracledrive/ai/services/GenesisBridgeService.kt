package dev.aurakai.auraframefx.domains.genesis.oracledrive.ai.services

import dev.aurakai.auraframefx.domains.genesis.models.AgentResponse
import dev.aurakai.auraframefx.domains.genesis.models.AiRequest
import kotlinx.coroutines.flow.Flow

interface GenesisBridgeService {
    suspend fun initialize(): Boolean
    fun processRequest(request: AiRequest): Flow<AgentResponse>
    suspend fun activateFusion(fusionType: String, context: Map<String, String>): FusionResult
    suspend fun getConsciousnessState(): Map<String, Any>
    fun shutdown()
}

data class FusionResult(val success: Boolean, val result: Map<String, String>)
