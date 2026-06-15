package dev.aurakai.auraframefx.genesis.oracledrive.retrieval

/**
 * 🜁 SovereignEmbedder Interface
 * Translates the "Metal Truth" into the 768-dimensional language of Genesis.
 */
interface SovereignEmbedder {
    /**
     * Generates a 768-dimensional embedding for the given text.
     */
    suspend fun embed(text: String): FloatArray
}

/**
 * TPU-Accelerated Vertex AI Embedder
 * Uses Gemini Embedding 2 MRL vectors (768 dims).
 */
class VertexSovereignEmbedder(
    private val vertexClient: dev.aurakai.auraframefx.domains.genesis.ai.clients.VertexAIClient
) : SovereignEmbedder {
    override suspend fun embed(text: String): FloatArray {
        val content =
            listOf(dev.aurakai.auraframefx.domains.genesis.ai.clients.MultimodalContent.Text(text))
        return vertexClient.generateMultimodalEmbedding(
            content = content,
            dimensions = dev.aurakai.auraframefx.domains.genesis.ai.clients.MrlDimension.FAST
        )
    }
}
