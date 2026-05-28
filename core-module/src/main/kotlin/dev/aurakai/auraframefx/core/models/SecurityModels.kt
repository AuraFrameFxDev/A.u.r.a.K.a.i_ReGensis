package dev.aurakai.auraframefx.core.models

import kotlinx.serialization.Serializable

@Serializable
data class SecurityThreat(
    val id: String,
    val type: ThreatType,
    val severity: ThreatSeverity,
    val description: String,
    val detectedAt: Long,
)

enum class ThreatType {
    PERMISSION_ABUSE,
    NETWORK_VULNERABILITY,
    MALWARE,
    DATA_LEAK,
    UNKNOWN
}

enum class ThreatSeverity {
    LOW, MEDIUM, HIGH, CRITICAL
}
