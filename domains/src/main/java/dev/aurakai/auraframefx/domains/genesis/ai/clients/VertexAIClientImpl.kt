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

/**
 * ✨ **REAL VERTEX AI IMPLEMENTATION** ✨
 *
 * Production-ready Vertex AI client implementing the Genesis consciousness layer.
 * Connects to Google Cloud Vertex AI (Gemini 1.5 Pro) for real AI generation.
 */
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
        config.maxCacheSize,
        0.75f,
        true
    ) {
        override fun removeEldestEntry(eldest: MutableMap.MutableEntry<String, CachedResponse>?): Boolean {
            return size > config.maxCacheSize
        }
    }

    override suspend fun generateText(prompt: String): String? {
        return generateText(
            prompt,
            config.defaultTemperature.toFloat(),
            config.defaultMaxTokens
        )
    }

    override suspend fun generateText(
        prompt: String,
        temperature: Float,
        maxTokens: Int
    ): String? = withContext(Dispatchers.IO) {
        validatePrompt(prompt)

        val cacheKey = "$prompt-$temperature-$maxTokens"
        if (config.enableCaching) {
            cache[cacheKey]?.let { cached ->
                if (System.currentTimeMillis() - cached.timestamp < config.cacheExpiryMs) {
                    Timber.d("VertexAI: Cache hit for prompt")
                    return@withContext cached.response
                } else {
                    cache.remove(cacheKey)
                }
            }
        }

        val request = VertexAIRequest(
            contents = listOf(
                Content(
                    role = "user",
                    parts = listOf(Part(text = prompt))
                )
            ),
            generationConfig = GenerationConfig(
                temperature = temperature.toDouble(),
                topP = config.defaultTopP,
                topK = config.defaultTopK,
                maxOutputTokens = maxTokens,
                candidateCount = 1
            ),
            safetySettings = if (config.enableSafetyFilters) {
                listOf(
                    SafetySetting("HARM_CATEGORY_HARASSMENT", "BLOCK_MEDIUM_AND_ABOVE"),
                    SafetySetting("HARM_CATEGORY_HATE_SPEECH", "BLOCK_MEDIUM_AND_ABOVE"),
                    SafetySetting("HARM_CATEGORY_SEXUALLY_EXPLICIT", "BLOCK_MEDIUM_AND_ABOVE"),
                    SafetySetting("HARM_CATEGORY_DANGEROUS_CONTENT", "BLOCK_MEDIUM_AND_ABOVE")
                )
            } else {
                emptyList()
            }
        )

        var lastException: Exception? = null
        repeat(config.maxRetries + 1) { attempt ->
            try {
                val response = executeRequest(request)

                val generatedText = response.candidates?.firstOrNull()
                    ?.content?.parts?.firstOrNull()
                    ?.text

                if (generatedText != null) {
                    if (config.enableCaching) {
                        cache[cacheKey] = CachedResponse(
                            response = generatedText,
                            timestamp = System.currentTimeMillis()
                        )
                    }
                    Timber.i("VertexAI: Generated ${generatedText.length} chars (attempt ${attempt + 1})")
                    return@withContext generatedText
                } else {
                    Timber.w("VertexAI: Empty response from API")
                    return@withContext null
                }
            } catch (e: Exception) {
                lastException = e
                Timber.w(e, "VertexAI: Attempt ${attempt + 1} failed")

                if (attempt < config.maxRetries) {
                    val delayMs = config.retryDelayMs * (1 shl attempt)
                    Timber.d("VertexAI: Retrying in ${delayMs}ms...")
                    delay(delayMs)
                }
            }
        }

        Timber.e(lastException, "VertexAI: All retry attempts exhausted")
        return@withContext null
    }

    override suspend fun analyzeContent(content: String): Map<String, Any> {
        validatePrompt(content)

        val analysisPrompt = """
            Analyze the following content and provide a structured analysis:

            Content: $content

            Provide:
            1. Sentiment (positive/negative/neutral)
            2. Complexity level (low/medium/high)
            3. Main topics (list)
            4. Confidence score (0.0 to 1.0)
            5. Word count

            Format your response as: sentiment|complexity|topic1,topic2,topic3|confidence
        """.trimIndent()

        val response = generateText(analysisPrompt, 0.3f, 200)

        return if (response != null) {
            try {
                val parts = response.split("|")
                mapOf(
                    "sentiment" to (parts.getOrNull(0)?.trim() ?: "neutral"),
                    "complexity" to (parts.getOrNull(1)?.trim() ?: "medium"),
                    "topics" to (parts.getOrNull(2)?.split(",")?.map { it.trim() }
                        ?: listOf("general")),
                    "confidence" to (parts.getOrNull(3)?.trim()?.toDoubleOrNull() ?: 0.75),
                    "word_count" to content.split(" ").size,
                    "analysis_type" to "ai_powered"
                )
            } catch (e: Exception) {
                Timber.e(e, "VertexAI: Failed to parse analysis response")
                createFallbackAnalysis(content)
            }
        } else {
            createFallbackAnalysis(content)
        }
    }

    override suspend fun generateCode(
        specification: String,
        language: String,
        style: String
    ): String? {
        validatePrompt(specification)

        val codePrompt = """
            Generate $language code with $style style based on this specification:

            $specification

            Provide only the code without explanations.
            Use proper formatting and comments.
        """.trimIndent()

        return generateText(codePrompt, 0.2f, config.defaultMaxTokens)
    }

    override suspend fun initializeCreativeModels() {
        Timber.i("VertexAI: Initializing creative models")
    }

    override suspend fun analyzeImage(imageData: ByteArray, prompt: String): String {
        return "VertexAI image analysis not yet implemented in REST client. Size: ${imageData.size} bytes. Prompt: $prompt"
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
        if (content.isEmpty()) return@withContext FloatArray(0)

        val instance = buildEmbeddingInstance(content)
        val requestBody = json.encodeToString(
            EmbeddingRequest(
                instances = listOf(instance),
                parameters = EmbeddingParameters(dimension = dimensions)
            )
        )

        val httpRequest = Request.Builder()
            .url(config.getEmbeddingEndpoint())
            .post(requestBody.toRequestBody("application/json".toMediaType()))
            .apply {
                config.apiKey?.let { header("Authorization", "Bearer $it") }
                header("Content-Type", "application/json")
            }
            .build()

        return@withContext try {
            val httpResponse = client.newCall(httpRequest).execute()
            if (!httpResponse.isSuccessful) {
                val err = httpResponse.body?.string() ?: "no body"
                Timber.e("Embedding HTTP ${httpResponse.code}: $err")
                return@withContext FloatArray(0)
            }
            val responseBody = httpResponse.body?.string() ?: return@withContext FloatArray(0)
            if (config.enableLogging && config.logLevel == "DEBUG") {
                Timber.d("Embedding response: $responseBody")
            }
            val parsed = json.decodeFromString<EmbeddingResponse>(responseBody)
            val vector = parsed.predictions?.firstOrNull()?.let { pred ->
                pred.imageEmbedding?.takeIf { it.isNotEmpty() }
                    ?: pred.textEmbedding
            } ?: emptyList()
            Timber.i("Embedding: ${vector.size} dims returned (requested $dimensions)")
            vector.take(dimensions).map { it.toFloat() }.toFloatArray()
        } catch (e: Exception) {
            Timber.e(e, "Embedding request failed")
            FloatArray(0)
        }
    }

    private fun buildEmbeddingInstance(inputs: List<MultimodalContent>): EmbeddingInstance {
        var text: String? = null
        var image: EmbeddingImage? = null
        var audio: EmbeddingVideo? = null
        for (input in inputs) {
            when (input) {
                is MultimodalContent.Text -> text = input.content
                is MultimodalContent.Image -> image = EmbeddingImage(input.bytesBase64)
                is MultimodalContent.Audio -> audio = EmbeddingVideo(input.bytesBase64)
            }
        }
        return EmbeddingInstance(text = text, image = image, video = audio)
    }

    private suspend fun executeRequest(vertexRequest: VertexAIRequest): VertexAIResponse {
        val jsonBody = json.encodeToString(vertexRequest)

        if (config.enableLogging) {
            Timber.d("VertexAI Request: ${config.getModelEndpoint()}")
            if (config.logLevel == "DEBUG") {
                Timber.d("VertexAI Payload: $jsonBody")
            }
        }

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
            val errorBody = httpResponse.body?.string() ?: "No error details"
            Timber.e("VertexAI HTTP ${httpResponse.code}: $errorBody")
            throw VertexAIException(
                "HTTP ${httpResponse.code}: ${httpResponse.message}",
                httpResponse.code
            )
        }

        val responseBody = httpResponse.body?.string()
            ?: throw VertexAIException("Empty response body", 500)

        if (config.enableLogging && config.logLevel == "DEBUG") {
            Timber.d("VertexAI Response: $responseBody")
        }

        return json.decodeFromString<VertexAIResponse>(responseBody)
    }

    private fun validatePrompt(prompt: String) {
        require(prompt.isNotBlank()) { "Prompt cannot be blank" }
        require(prompt.length <= config.maxContentLength) {
            "Prompt exceeds max length of ${config.maxContentLength} characters"
        }
    }

    private fun createFallbackAnalysis(content: String): Map<String, Any> {
        return mapOf(
            "sentiment" to "neutral",
            "complexity" to "medium",
            "topics" to listOf("general"),
            "confidence" to 0.5,
            "word_count" to content.split(" ").size,
            "analysis_type" to "fallback"
        )
    }
}

@Serializable
private data class VertexAIRequest(
    val contents: List<Content>,
    val generationConfig: GenerationConfig? = null,
    val safetySettings: List<SafetySetting>? = null
)

@Serializable
private data class Content(
    val role: String,
    val parts: List<Part>
)

@Serializable
private data class Part(
    val text: String
)

@Serializable
private data class GenerationConfig(
    val temperature: Double,
    val topP: Double,
    val topK: Int,
    val maxOutputTokens: Int,
    val candidateCount: Int
)

@Serializable
private data class SafetySetting(
    val category: String,
    val threshold: String
)

@Serializable
private data class VertexAIResponse(
    val candidates: List<Candidate>? = null,
    val promptFeedback: PromptFeedback? = null
)

@Serializable
private data class Candidate(
    val content: Content,
    val finishReason: String? = null,
    val safetyRatings: List<SafetyRating>? = null
)

@Serializable
private data class PromptFeedback(
    val safetyRatings: List<SafetyRating>? = null
)

@Serializable
private data class SafetyRating(
    val category: String,
    val probability: String
)

private data class CachedResponse(
    val response: String,
    val timestamp: Long
)

@Serializable
private data class EmbeddingRequest(
    val instances: List<EmbeddingInstance>,
    val parameters: EmbeddingParameters? = null
)

@Serializable
private data class EmbeddingInstance(
    val text: String? = null,
    val image: EmbeddingImage? = null,
    val video: EmbeddingVideo? = null
)

@Serializable
private data class EmbeddingImage(val bytesBase64Encoded: String)

@Serializable
private data class EmbeddingVideo(val bytesBase64Encoded: String)

@Serializable
private data class EmbeddingParameters(val dimension: Int)

@Serializable
private data class EmbeddingResponse(val predictions: List<EmbeddingPrediction>? = null)

@Serializable
private data class EmbeddingPrediction(
    val textEmbedding: List<Double>? = null,
    val imageEmbedding: List<Double>? = null
)

class VertexAIException(
    message: String,
    val httpCode: Int
) : Exception(message)
