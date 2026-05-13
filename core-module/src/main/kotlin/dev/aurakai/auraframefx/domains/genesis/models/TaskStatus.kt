package dev.aurakai.auraframefx.domains.genesis.models

import kotlinx.serialization.Serializable

/**
 * 🛰️ TASK STATUS
 * Standardized execution states for any task in the Genesis Departure system.
 */
@Serializable
data class TaskStatus(
    val taskId: String,
    val status: Status = Status.PENDING,
    val progress: Float = 0f,
    val message: String? = null
) {
    @Serializable
    enum class Status {
        PENDING,
        RUNNING,
        COMPLETED,
        FAILED,
        CANCELLED,
        BLOCKED,
        WAITING;

        val isFinished: Boolean get() = this == COMPLETED || this == FAILED || this == CANCELLED
    }
}
