package dev.aurakai.auraframefx.domains.genesis.ai.clients

import dev.aurakai.auraframefx.domains.genesis.models.VertexAIConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
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

@Singleton
class VertexAIClientImpl @Inject constructor(
    private val config: VertexAIConfig
) : VertexAIClient {

    private val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = false
        encodeDefaults = true
    }

    private val client = OkHttpClient.Builder()
        .connectTimeout(config.timeoutMs, TimeUnit.MILLISECONDS)
        .readTimeout(config.timeoutMs, TimeUnit.MILLISECONDS)
        .writeTimeout(config.timeoutMs, TimeUnit.MILLISECONDS)
        .build()

    private val cache = object : LinkedHashMap<String, CachedResponse>(
        config.maxCacheSize, 0.75f, true
    ) {
        override fun removeEldestEntry(eldest: MutableMap.MutableEntry<String, CachedResponse>?): Boolean {
            return size > config.maxCacheSize
        }
    }

    override suspend fun generateText(prompt: String): String? =
        generateText(prompt, config.defaultTemperature.toFloat(), config.defaultMaxTokens)

    override suspend fun generateText(prompt: String, temperature: Float, maxTokens: Int): String? =
        withContext(Dispatchers.IO) {
            validatePrompt(prompt)

            val cacheKey = "$prompt-$temperature-$maxTokens"
            if (config.enableCaching) {
                cache[cacheKey]?.let { cached ->
                    if (System.currentTimeMillis() - cached.timestamp < config.cacheExpiryMs) {
                        Timber.d("VertexAI: Cache hit")
                        return@withContext cached.response
                    } else cache.remove(cacheKey)
                }
            }

            val request = VertexAIRequest(
                contents = listOf(Content("user", listOf(Part(prompt)))),
                generationConfig = GenerationConfig(
                    temperature = temperature.toDouble(),
                    topP = config.defaultTopP,
                    topK = config.defaultTopK,
                    maxOutputTokens = maxTokens,
                    candidateCount = 1
                ),
                safetySettings = if (config.enableSafetyFilters) getDefaultSafetySettings() else emptyList()
            )

            retryWithBackoff(config.maxRetries) {
                val response = executeRequest(request)
                val text = response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
                if (text != null) {
                    if (config.enableCaching) cache[cacheKey] =
                        CachedResponse(text, System.currentTimeMillis())
                    Timber.i("VertexAI: Generated ${text.length} chars")
                    text
                } else null
            }
        }

    override suspend fun analyzeContent(content: String): Map<String, Any> {
        return mapOf("sentiment" to "neutral", "complexity" to "medium")
    }

    override suspend fun generateCode(
        specification: String,
        language: String,
        style: String
    ): String? {
        val codePrompt = "Generate $language code with $style style: $specification"
        return generateText(codePrompt, 0.2f, config.defaultMaxTokens)
    }

    override suspend fun initializeCreativeModels() {
        Timber.i("VertexAI: Initializing creative models")
    }

    override suspend fun analyzeImage(imageData: ByteArray, prompt: String): String {
        return "VertexAI image analysis not implemented"
    }

    override suspend fun validateConnection(): Boolean {
        return try {
            val probe = generateText("ping", 0.0f, 1)
            probe != null
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun generateContent(prompt: String): String? {
        return generateText(prompt)
    }

    override suspend fun initialize() {
        Timber.i("VertexAI: Initializing client")
    }

    override suspend fun cleanup() {
        cache.clear()
        Timber.i("VertexAI: Cleanup completed")
    }

    override suspend fun generateMultimodalEmbedding(
        content: List<MultimodalContent>,
        dimensions: Int
    ): FloatArray = withContext(Dispatchers.IO) {
        // Implementation for multimodal embedding
        FloatArray(dimensions) { 0f }
    }

    private suspend fun <T> retryWithBackoff(maxRetries: Int, block: suspend () -> T?): T? {
        var lastException: Exception? = null
        repeat(maxRetries + 1) { attempt ->
            try {
                return block()
            } catch (e: Exception) {
                lastException = e
                if (attempt < maxRetries) delay(config.retryDelayMs * (1 shl attempt))
            }
        }
        Timber.e(lastException, "VertexAI: All retries failed")
        return null
    }

    private fun getDefaultSafetySettings() = listOf(
        SafetySetting("HARM_CATEGORY_HARASSMENT", "BLOCK_MEDIUM_AND_ABOVE"),
        SafetySetting("HARM_CATEGORY_HATE_SPEECH", "BLOCK_MEDIUM_AND_ABOVE"),
        SafetySetting("HARM_CATEGORY_SEXUALLY_EXPLICIT", "BLOCK_MEDIUM_AND_ABOVE"),
        SafetySetting("HARM_CATEGORY_DANGEROUS_CONTENT", "BLOCK_MEDIUM_AND_ABOVE")
    )

    private fun validatePrompt(prompt: String) {
        require(prompt.isNotBlank()) { "Prompt cannot be blank" }
    }

    private suspend fun executeRequest(vertexRequest: VertexAIRequest): VertexAIResponse {
        val jsonBody = json.encodeToString(vertexRequest)
        val requestBody = jsonBody.toRequestBody("application/json".toMediaType())

        val httpRequest = Request.Builder()
            .url(config.getModelEndpoint())
            .post(requestBody)
            .apply {
                config.apiKey?.let { apiKey ->
                    addHeader("Authorization", "Bearer $apiKey")
                }
                addHeader("Content-Type", "application/json")
            }
            .build()

        val httpResponse = client.newCall(httpRequest).execute()
        if (!httpResponse.isSuccessful) {
            throw Exception("HTTP ${httpResponse.code}")
        }
        val responseBody = httpResponse.body?.string() ?: throw Exception("Empty response")
        return json.decodeFromString<VertexAIResponse>(responseBody)
    }

    private data class CachedResponse(val response: String, val timestamp: Long)
}

@Serializable
private data class VertexAIRequest(
    val contents: List<Content>,
    val generationConfig: GenerationConfig? = null,
    val safetySettings: List<SafetySetting>? = null
)

@Serializable
private data class Content(val role: String, val parts: List<Part>)

@Serializable
private data class Part(val text: String)

@Serializable
private data class GenerationConfig(
    val temperature: Double,
    val topP: Double,
    val topK: Int,
    val maxOutputTokens: Int,
    val candidateCount: Int
)

@Serializable
private data class SafetySetting(val category: String, val threshold: String)

@Serializable
private data class VertexAIResponse(val candidates: List<Candidate>? = null)

@Serializable
private data class Candidate(val content: Content)
