package dev.aurakai.auraframefx.domains.kai.security

import kotlinx.serialization.Serializable

@Serializable
enum class ThreatLevel {
    NONE,
    LOW,
    MEDIUM,
    HIGH,
    CRITICAL
}

