package dev.aurakai.auraframefx.domains.sentinelmatrix.models

import kotlinx.serialization.Serializable
import java.util.UUID

/**
 * Standardized Threat Status across the Nexus.
 */
@Serializable
enum class ThreatStatus {
    ACTIVE,
    CONTAINED,
    RESOLVED,
    MONITORING
}

/**
 * Represents an active security threat detected by any agent in the Trinity.
 * Unified model for SentinelMatrix.
 */
@Serializable
data class ActiveThreat(
    val id: String = UUID.randomUUID().toString(),
    val type: String,
    val threatType: String = "HEURISTIC",
    val severity: ThreatLevel,
    val description: String,
    val source: String? = null,
    val timestamp: Long = System.currentTimeMillis(),
    val status: ThreatStatus = ThreatStatus.ACTIVE,
    val mitigated: Boolean = false,
    val metadata: Map<String, String> = emptyMap()
)

@Serializable
data class ScanEvent(
    val id: String = UUID.randomUUID().toString(),
    val type: String,
    val timestamp: Long = System.currentTimeMillis(),
    var threatsFound: Int = 0,
    var status: String = "PENDING",
    var error: String? = null,
    val scanTime: Long = 0L,
    val scanType: String = "GENERAL"
)
