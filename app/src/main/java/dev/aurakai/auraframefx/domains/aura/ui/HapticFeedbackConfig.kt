package dev.aurakai.auraframefx.domains.aura.ui

/**
 * Configuration for haptic feedback
 */
data class HapticFeedbackConfig(
    val enabled: Boolean = true,
    val intensity: Float = 0.5f,
    val duration: Long = 50L
)
