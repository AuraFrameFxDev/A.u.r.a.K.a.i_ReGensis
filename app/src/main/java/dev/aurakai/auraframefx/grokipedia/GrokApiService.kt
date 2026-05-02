package dev.aurakai.auraframefx.grokipedia

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface GrokApiService {
    @POST("v1/chat/completions")
    suspend fun chat(
        @Header("Authorization") auth: String,
        @Body body: GrokRequest
    ): GrokResponse
}

data class GrokRequest(
    val model: String = "grok-1",
    val messages: List<Message>
)

data class Message(
    val role: String,
    val content: String
)

data class GrokResponse(
    val choices: List<Choice>
)

data class Choice(
    val message: Message
)
