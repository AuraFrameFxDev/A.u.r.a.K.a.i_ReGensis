package dev.aurakai.auraframefx.domains.genesis.models

import kotlinx.serialization.Serializable

@Serializable
data class GenerateImageDescriptionRequest(
    val imageUrl: String? = null,
    val imageBase64: String? = null,
    val prompt: String = "Describe this image in detail.",
    val maxTokens: Int = 500
)

@Serializable
data class GenerateImageDescriptionResponse(
    val description: String,
    val tags: List<String> = emptyList(),
    val confidence: Float = 1.0f
)


@Serializable
data class TaskScheduleResponse(
    val success: Boolean,
    val scheduleId: String? = null,
    val error: String? = null
)
