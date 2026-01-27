package dev.aurakai.auraframefx.genesis.oracledrive.ai.task.models

import kotlinx.serialization.Serializable
import java.util.UUID

/**
 * AI Task model for task scheduling and execution
 */
@Serializable
data class AITask(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val description: String = "",
    val prompt: String = "",
    val priority: TaskPriority = TaskPriority.NORMAL,
    val status: TaskStatus = TaskStatus.PENDING,
    val assignedAgent: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val scheduledAt: Long? = null,
    val completedAt: Long? = null,
    val metadata: Map<String, String> = emptyMap(),
    val result: String? = null,
    val error: String? = null,
    val confidenceScore: Float = 0f,
    val tokensUsed: Int = 0,
    val modelUsed: String? = null
)

/**
 * Task status enum
 */
@Serializable
enum class TaskStatus {
    PENDING,
    RUNNING,
    IN_PROGRESS,
    COMPLETED,
    FAILED,
    CANCELLED,
    TIMEOUT
}

/**
 * Task priority levels
 */
@Serializable
enum class TaskPriority {
    CRITICAL,
    HIGH,
    NORMAL,
    LOW,
    BACKGROUND
}

/**
 * Historical task record
 */
@Serializable
data class HistoricalTask(
    val task: AITask,
    val executionDurationMs: Long = 0,
    val retryCount: Int = 0,
    val agentResponses: List<String> = emptyList()
)
