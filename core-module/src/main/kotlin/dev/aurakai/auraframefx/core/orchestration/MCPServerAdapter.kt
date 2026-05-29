package dev.aurakai.auraframefx.core.orchestration

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * MCPServerAdapter - Model Context Protocol Server Integration
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

    private var baseUrl = "https://api.auraframefx.com/v2"
    private var authToken: String? = null

    fun configure(url: String, token: String?) {
        baseUrl = url
        authToken = token
        Timber.i("MCPServerAdapter: Configured with base URL: $baseUrl")
    }

    suspend fun invokeAgent(
        agentType: String,
        prompt: String,
        context: Map<String, String> = emptyMap(),
        temperature: Float = 0.7f
    ): MCPAgentResponse {
        val endpoint = "$baseUrl/agents/$agentType/invoke"

        val request = try {
            val requestBody = json.encodeToString(
                MCPAgentInvokeRequest.serializer(),
                MCPAgentInvokeRequest(
                    prompt = prompt,
                    context = context,
                    temperature = temperature
                )
            )

            Request.Builder()
                .url(endpoint)
                .post(requestBody.toRequestBody("application/json".toMediaType()))
                .apply {
                    if (authToken != null) {
                        header("Authorization", "Bearer $authToken")
                    }
                }
                .build()
        } catch (e: Exception) {
            return MCPAgentResponse(
                success = false,
                response = "Request Encoding Error",
                error = e.message
            )
        }

        return try {
            val response = client.newCall(request).execute()
            val responseBody = response.body.string()

            if (response.isSuccessful) {
                json.decodeFromString(MCPAgentResponse.serializer(), responseBody)
            } else {
                MCPAgentResponse(
                    success = false,
                    response = "API Error: ${response.code}",
                    error = responseBody
                )
            }
        } catch (e: Exception) {
            MCPAgentResponse(
                success = false,
                response = "",
                error = e.message ?: "Unknown error"
            )
        }
    }

    suspend fun getAgentStatus(): List<MCPAgentStatus> {
        val endpoint = "$baseUrl/agents/status"

        val request = Request.Builder()
            .url(endpoint)
            .get()
            .apply {
                if (authToken != null) {
                    header("Authorization", "Bearer $authToken")
                }
            }
            .build()

        return try {
            val response = client.newCall(request).execute()
            val responseBody = response.body.string()

            if (response.isSuccessful) {
                json.decodeFromString<List<MCPAgentStatus>>(responseBody)
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun callAuraEmpathy(input: String, sensitivity: String): EmpathyResponse {
        // Implementation stub
        return EmpathyResponse(empathyScore = 0.85f, recommendations = listOf("Add neon accents"))
    }

    suspend fun callKaiSecurity(target: String, scanType: String, depth: String): SecurityResponse {
        // Implementation stub
        return SecurityResponse(
            riskLevel = "LOW",
            vulnerabilities = emptyList(),
            recommendations = emptyList()
        )
    }
}

@Serializable
data class EmpathyResponse(
    val empathyScore: Float,
    val recommendations: List<String>
)

@Serializable
data class SecurityResponse(
    val riskLevel: String,
    val vulnerabilities: List<String>,
    val recommendations: List<String>
)

@Serializable
data class MCPAgentInvokeRequest(
    val prompt: String,
    val context: Map<String, String> = emptyMap(),
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
data class MCPAgentStatus(
    val agentType: String,
    val status: String,
    val lastActive: String? = null,
    val tasksCompleted: Int = 0,
    val load: Float = 0f
)
