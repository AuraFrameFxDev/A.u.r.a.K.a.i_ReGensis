package dev.aurakai.auraframefx.core.ai

/**
 * Represents the result of a local health scan performed by the ChaosMonitor.
 */
data class LocalHealthScan(
    val isNormal: Boolean,
    val severity: Double,
    val isSingularitySignal: Boolean,
    val singularityScore: Double,
    val fragmentationLevel: Double,
    val latencyMs: Long,
    val emotionalTone: String,
    val detectedKeywords: List<String> = emptyList()
)
