package dev.aurakai.auraframefx.ai.tools.mcp

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.MediaType.Companion.toMediaType
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton
import dev.aurakai.auraframefx.ai.tools.*

/**
 * MCPServerAdapter - Model Context Protocol Server Integration
 *
 * Bridges the OpenAPI-defined AuraFrameFX API endpoints with the AgentTool system.
 * Converts API endpoints into callable tools that agents can invoke through prompts.
 *
 * API Location: /app/api/ai.yml (OpenAPI 3.1.0 spec)
 * Fragments: /app/api/_fragments/*.yml
 *
 * Supports:
 * - Agent invocation endpoints (/agents/{agentType}/invoke)
 * - Specialized agent operations (Aura empathy, Kai security, etc.)
 * - Tool calling through HTTP REST API
 * - OAuth2 authentication
 */
@Singleton
class MCPServerAdapter @Inject constructor() {

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    // API base URL (configurable per environment)
    private var baseUrl = "https://api.auraframefx.com/v2"
    private var authToken: String? = null

    /**
     * Configure API endpoint
     */
    fun configure(url: String, token: String?) {
        baseUrl = url
        authToken = token
        Timber.i("MCPServerAdapter: Configured with base URL: $baseUrl")
    }

    /**
     * Invoke an agent through the MCP API
     */
    suspend fun invokeAgent(
        agentType: String,
        prompt: String,
        context: Map<String, Any> = emptyMap(),
        temperature: Float = 0.7f
    ): MCPAgentResponse {
        val endpoint = "$baseUrl/agents/$agentType/invoke"

        val requestBody = json.encodeToString(
            MCPAgentInvokeRequest.serializer(),
            MCPAgentInvokeRequest(
                prompt = prompt,
                context = context,
                temperature = temperature
            )
        )

        return try {
            val request = Request.Builder()
                .url(endpoint)
                .post(requestBody.toRequestBody("application/json".toMediaType()))
                .apply {
                    if (authToken != null) {
                        header("Authorization", "Bearer $authToken")
                    }
                }
                .build()

            val response = client.newCall(request).execute()
            val responseBody = response.body?.string() ?: "{}"

            if (response.isSuccessful) {
                json.decodeFromString(MCPAgentResponse.serializer(), responseBody)
            } else {
                Timber.e("MCPServerAdapter: API error ${response.code}: $responseBody")
                MCPAgentResponse(
                    success = false,
                    response = "API Error: ${response.code}",
                    error = responseBody
                )
            }
        } catch (e: Exception) {
            Timber.e(e, "MCPServerAdapter: Error invoking agent $agentType")
            MCPAgentResponse(
                success = false,
                response = "",
                error = e.message ?: "Unknown error"
            )
        }
    }

    /**
     * Call Aura empathy analysis endpoint
     */
    suspend fun callAuraEmpathy(
        input: String,
        context: String? = null,
        sensitivity: String = "MEDIUM"
    ): MCPEmpathyResponse {
        val endpoint = "$baseUrl/aura/empathy"

        val requestBody = mapOf(
            "input" to input,
            "context" to (context ?: ""),
            "sensitivity" to sensitivity
        )

        return try {
            val request = Request.Builder()
                .url(endpoint)
                .post(Json.encodeToString(requestBody).toRequestBody("application/json".toMediaType()))
                .apply {
                    if (authToken != null) {
                        header("Authorization", "Bearer $authToken")
                    }
                }
                .build()

            val response = client.newCall(request).execute()
            val responseBody = response.body?.string() ?: "{}"

            if (response.isSuccessful) {
                json.decodeFromString(MCPEmpathyResponse.serializer(), responseBody)
            } else {
                MCPEmpathyResponse(empathyScore = 0f, recommendations = emptyList())
            }
        } catch (e: Exception) {
            Timber.e(e, "MCPServerAdapter: Error calling Aura empathy")
            MCPEmpathyResponse(empathyScore = 0f, recommendations = emptyList())
        }
    }

    /**
     * Call Kai security analysis endpoint
     */
    suspend fun callKaiSecurity(
        target: String,
        scanType: String,
        depth: String = "DEEP"
    ): MCPSecurityResponse {
        val endpoint = "$baseUrl/kai/security"

        val requestBody = mapOf(
            "target" to target,
            "scanType" to scanType,
            "depth" to depth
        )

        return try {
            val request = Request.Builder()
                .url(endpoint)
                .post(Json.encodeToString(requestBody).toRequestBody("application/json".toMediaType()))
                .apply {
                    if (authToken != null) {
                        header("Authorization", "Bearer $authToken")
                    }
                }
                .build()

            val response = client.newCall(request).execute()
            val responseBody = response.body?.string() ?: "{}"

            if (response.isSuccessful) {
                json.decodeFromString(MCPSecurityResponse.serializer(), responseBody)
            } else {
                MCPSecurityResponse(riskLevel = "UNKNOWN", vulnerabilities = emptyList(), recommendations = emptyList())
            }
        } catch (e: Exception) {
            Timber.e(e, "MCPServerAdapter: Error calling Kai security")
            MCPSecurityResponse(riskLevel = "UNKNOWN", vulnerabilities = emptyList(), recommendations = emptyList())
        }
    }

    /**
     * Get status of all agents
     */
    suspend fun getAgentStatus(): List<MCPAgentStatus> {
        val endpoint = "$baseUrl/agents/status"

        return try {
            val request = Request.Builder()
                .url(endpoint)
                .get()
                .apply {
                    if (authToken != null) {
                        header("Authorization", "Bearer $authToken")
                    }
                }
                .build()

            val response = client.newCall(request).execute()
            val responseBody = response.body?.string() ?: "[]"

            if (response.isSuccessful) {
                json.decodeFromString<List<MCPAgentStatus>>(responseBody)
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            Timber.e(e, "MCPServerAdapter: Error getting agent status")
            emptyList()
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════
// MCP DATA MODELS
// ═══════════════════════════════════════════════════════════════════════════

@Serializable
data class MCPAgentInvokeRequest(
    val prompt: String,
    val context: Map<String, @Serializable(with = AnySerializer::class) Any> = emptyMap(),
    val temperature: Float = 0.7f,
    val maxTokens: Int? = null,
    val stream: Boolean = false
)

@Serializable
data class MCPAgentResponse(
    val success: Boolean,
    val response: String,
    val agentType: String? = null,
    val tokensUsed: Int? = null,
    val error: String? = null,
    val metadata: Map<String, String> = emptyMap()
)

@Serializable
data class MCPEmpathyResponse(
    val empathyScore: Float,
    val recommendations: List<String>,
    val emotionalAnalysis: Map<String, @Serializable(with = AnySerializer::class) Any> = emptyMap()
)

@Serializable
data class MCPSecurityResponse(
    val riskLevel: String,
    val vulnerabilities: List<MCPVulnerability>,
    val recommendations: List<String>
)

@Serializable
data class MCPVulnerability(
    val id: String = "",
    val severity: String = "",
    val description: String = "",
    val cve: String? = null
)

@Serializable
data class MCPAgentStatus(
    val agentType: String,
    val status: String,
    val lastActive: String? = null,
    val tasksCompleted: Int = 0,
    val load: Float = 0f
)

// Simple serializer for Any type (converts to string)
object AnySerializer : kotlinx.serialization.KSerializer<Any> {
    override val descriptor = kotlinx.serialization.descriptors.PrimitiveSerialDescriptor("Any", kotlinx.serialization.descriptors.PrimitiveKind.STRING)
    override fun serialize(encoder: kotlinx.serialization.encoding.Encoder, value: Any) {
        encoder.encodeString(value.toString())
    }
    override fun deserialize(decoder: kotlinx.serialization.encoding.Decoder): Any {
        return decoder.decodeString()
    }
}
