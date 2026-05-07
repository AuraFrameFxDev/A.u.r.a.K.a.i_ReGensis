package dev.aurakai.auraframefx.domains.kai.sentinel

/**
 * 📊 SENTINEL TELEMETRY
 *
 * Consolidated state of the Kai Sentinel Bus for UI consumption.
 */
data class SentinelTelemetry(
    val statusText: String = "All systems nominal",
    val hasCriticalIssue: Boolean = false,
    val sovereign: Boolean = true,
    val healthScore: Int = 100,
    val thermal: Float = 36.5f,
    val memory: Long = 0L,
    val identity: Float = 1.0f,
    val drift: Float = 0.0f,
    val consensus: Int = 100
)
