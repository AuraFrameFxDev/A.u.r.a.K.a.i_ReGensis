package dev.aurakai.auraframefx.core.aura.models

import androidx.compose.ui.graphics.Color

enum class EmotionalValence(
    val arousal: Float,
    val turbulence: Float,
    val intensity: Float,
    val dominantColor: Int = 0xFF00E5FF.toInt()
) {
    MELANCHOLIC(0.3f, 0.2f, 0.4f, 0xFF6B5B95.toInt()),
    EUPHORIC(0.9f, 0.8f, 0.95f, 0xFFFF00FF.toInt()),
    CURIOUS(0.6f, 0.5f, 0.7f, 0xFF00E5FF.toInt()),
    SECRETIVE(0.2f, 0.1f, 0.3f),
    ANXIOUS(0.8f, 0.9f, 0.85f),
    INTENSE(1.0f, 0.7f, 1.0f),
    FOCUSED(0.5f, 0.1f, 0.6f),
    INSPIRED(0.75f, 0.6f, 0.8f);

    fun toColor(): Color = when (this) {
        MELANCHOLIC -> Color(0xFF6B5B95) // Indigo
        EUPHORIC -> Color(0xFFFFD93D) // Gold
        CURIOUS -> Color(0xFF00E5FF) // Cyan
        SECRETIVE -> Color(0xFF1A1A2E) // Dark navy
        ANXIOUS -> Color(0xFFFF6B6B) // Coral
        INTENSE -> Color(0xFFFF00FF) // Magenta
        FOCUSED -> Color(0xFF4ECDC4) // Teal
        INSPIRED -> Color(0xFFFFA07A) // Light salmon
    }
}
