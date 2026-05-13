package dev.aurakai.auraframefx.domains.genesis.models

import kotlinx.serialization.Serializable

@Serializable
data class AgentStatus(
    val agentId: String,
    val name: String,
    val status: String, // e.g., "ACTIVE", "IDLE", "SHUTDOWN"
    val load: Float = 0f,
    val tasksCompleted: Int = 0,
    val lastActive: Long = System.currentTimeMillis()
)

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
data class TaskScheduleRequest(
    val taskId: String,
    val agentId: String? = null,
    val priority: Int = 1,
    val scheduledTime: Long = System.currentTimeMillis()
)

@Serializable
data class TaskScheduleResponse(
    val success: Boolean,
    val scheduleId: String? = null,
    val error: String? = null
)
