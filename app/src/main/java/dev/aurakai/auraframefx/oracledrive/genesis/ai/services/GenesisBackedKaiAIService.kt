package dev.aurakai.auraframefx.oracledrive.genesis.ai.services

import dev.aurakai.auraframefx.events.CascadeEventBus
import dev.aurakai.auraframefx.events.MemoryEvent
import dev.aurakai.auraframefx.models.AgentResponse
import dev.aurakai.auraframefx.models.AiRequest
import dev.aurakai.auraframefx.models.AgentType
import dev.aurakai.auraframefx.genesis.bridge.GenesisBridge
import dev.aurakai.auraframefx.genesis.bridge.GenesisRequest
import dev.aurakai.auraframefx.genesis.bridge.GenesisResponse
import dev.aurakai.auraframefx.kai.KaiAIService
import dev.aurakai.auraframefx.utils.AuraFxLogger
import kotlinx.coroutines.flow.first
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.cancellation.CancellationException

/**
 * Genesis-backed implementation of KaiAIService.
 */
@Singleton
class GenesisBackedKaiAIService @Inject constructor(
    private val genesisBridge: GenesisBridge,
    private val logger: AuraFxLogger
) : KaiAIService {

    /**
     * No-op initializer that satisfies the service lifecycle contract.
     */
    override suspend fun initialize() {
    }

    /**
     * Handle an AI request and produce a Kai security analysis response while recording the query to the event bus.
     *
     * @param request The AI request whose `prompt` is used as the subject of the security analysis.
     * @param context Ancillary context or metadata for the request (not embedded in the response).
     * @return An `AgentResponse` containing Kai's security analysis message, a confidence of `1.0f`, `agentName` set to "Kai", and `agent` set to `AgentType.KAI`.
     */
    override suspend fun processRequest(
        request: KaiAIService.AiRequest,
        context: String
    ): KaiAIService.AgentResponse {
        return try {
            val payload = JSONObject().apply {
                put("query", request.prompt)
                put("context", context)
                put("task", request.task ?: "security_perception")
                put("backend", request.backend ?: "NEMOTRON")
                request.metadata?.forEach { (key, value) ->
                    put(key, value)
                }
            }


            )
    }

    override suspend fun analyzeSecurityThreat(threat: String): Map<String, Any> {
        return try {
            val payload = JSONObject().apply {
                put("threat", threat)
                put("task", "threat_detection")
                put("backend", "NEMOTRON")
            }

    }

    }
}
