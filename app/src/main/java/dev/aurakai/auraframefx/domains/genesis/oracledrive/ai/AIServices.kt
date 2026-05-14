package dev.aurakai.auraframefx.domains.genesis.oracledrive.ai

import dev.aurakai.auraframefx.domains.genesis.models.AgentResponse
import dev.aurakai.auraframefx.domains.genesis.models.AiRequest

interface ClaudeAIService {
    suspend fun processRequest(request: AiRequest, context: String): AgentResponse
}

interface NemotronAIService {
    suspend fun processRequest(request: AiRequest, context: String): AgentResponse
}

interface GeminiAIService {
    suspend fun processRequest(request: AiRequest, context: String): AgentResponse
}

interface MetaInstructAIService {
    suspend fun processRequest(request: AiRequest, context: String): AgentResponse
}
