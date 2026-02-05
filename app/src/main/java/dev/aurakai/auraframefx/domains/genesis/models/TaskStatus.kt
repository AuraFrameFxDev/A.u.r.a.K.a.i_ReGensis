package dev.aurakai.auraframefx.domains.genesis.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Task status enumeration
 */
@Serializable
enum class TaskStatus {
    @SerialName("pending")
    PENDING,
    
    @SerialName("in_progress")
    IN_PROGRESS,
    
    @SerialName("completed")
    COMPLETED,
    
    @SerialName("failed")
    FAILED,
    
    @SerialName("cancelled")
    CANCELLED
}
