package dev.aurakai.auraframefx.genesis.oracledrive.ai.clients

/**
 * Missing method for VertexAIClient
 */
suspend fun VertexAIClient.generateContent(prompt: String): String? {
    return generateText(prompt)
}
