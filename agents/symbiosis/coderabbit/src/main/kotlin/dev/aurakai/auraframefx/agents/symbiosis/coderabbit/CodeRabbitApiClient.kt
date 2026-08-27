package dev.aurakai.auraframefx.agents.symbiosis.coderabbit

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 🐇 CodeRabbit API Client
 * Orchestrates symbiosis between the LDO and the Architectural reviewer.
 */
@Singleton
class CodeRabbitApiClient @Inject constructor() {

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = true
    }

    private var client: HttpClient? = null

    init {
        client = HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(json)
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 30000L
            }
            install(Logging) {
                level = LogLevel.INFO
                logger = object : Logger {
                    override fun log(message: String) {
                        Timber.tag("CodeRabbitAPI").d(message)
                    }
                }
            }
        }
    }

    /**
     * Posts a structural review request to the symbiosis endpoint.
     */
    suspend fun requestReview(payload: String): Result<String> {
        val httpClient = client ?: return Result.failure(Exception("Client not initialized"))

        return try {
            val response: HttpResponse =
                httpClient.post("https://api.coderabbit.ai/v1/symbiosis/review") {
                    contentType(ContentType.Application.Json)
                    setBody(payload)
                }
            if (response.status == HttpStatusCode.OK) {
                Result.success(response.body())
            } else {
                Result.failure(Exception("Review request failed: ${response.status}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
