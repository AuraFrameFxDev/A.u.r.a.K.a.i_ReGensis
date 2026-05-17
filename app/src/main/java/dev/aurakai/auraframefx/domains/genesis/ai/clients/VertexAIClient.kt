package dev.aurakai.auraframefx.domains.genesis.ai.clients

/**
 * Sealed class representing multimodal input to the Gemini embedding pipeline.
 */
sealed class MultimodalContent {
    data class Text(val content: String) : MultimodalContent()
    data class Image(val bytesBase64: String, val mimeType: String = "image/jpeg") :
        MultimodalContent()

    data class Audio(val bytesBase64: String, val mimeType: String = "audio/mp3") :
        MultimodalContent()
}

/**
 * Matryoshka Representation Learning dimension presets.
 */
object MrlDimension {
    const val FAST = 768
    const val OPTIMAL = 1536
    const val DEEP = 3072
}

/**
 * Genesis Vertex AI Client Interface
 */
interface VertexAIClient {
    suspend fun generateCode(
        specification: String,
        language: String,
        style: String = "clean"
    ): String?

    suspend fun generateText(prompt: String): String?
    suspend fun generateText(prompt: String, temperature: Float, maxTokens: Int): String?
    suspend fun analyzeContent(content: String): Map<String, Any>
    suspend fun initializeCreativeModels()
    suspend fun analyzeImage(imageData: ByteArray, prompt: String): String
    suspend fun validateConnection(): Boolean
    suspend fun generateContent(prompt: String): String?
    suspend fun initialize()
    suspend fun cleanup()

    /**
     * Generate a multimodal embedding vector via Gemini Embedding 2.
     */
    suspend fun generateMultimodalEmbedding(
        content: List<MultimodalContent>,
        dimensions: Int = MrlDimension.OPTIMAL
    ): FloatArray
}
