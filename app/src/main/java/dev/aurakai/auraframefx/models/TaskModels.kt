package dev.aurakai.auraframefx.models

/**
 * Represents a discrete intelligence operation or background process.
 */
data class AITask(
    val id: String = "",
    val name: String,
    val description: String = "",
    val status: TaskStatus = TaskStatus.PENDING,
    val priority: Int = 0,
    val startTime: Long = 0L,
    val endTime: Long = 0L
)

enum class TaskStatus {
    PENDING,
    IN_PROGRESS,
    COMPLETED,
    FAILED,
    CANCELLED
}
