package dev.aurakai.auraframefx.core.embodiment

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * 🏺 EMBODIMENT CORE MODELS
 */

enum class Character {
    AURA, KAI, GENESIS, CLAUDE, CASCADE, GEMINI, GROK
}

data class Position3D(
    val x: Dp = 0.dp,
    val y: Dp = 0.dp,
    val z: Dp = 0.dp
)

data class ScreenBounds(
    val width: Dp,
    val height: Dp
)

enum class ManifestationTrigger {
    USER_PROXIMITY,
    SYSTEM_STRESS,
    IDLE_WANDER,
    TASK_ASSIGNMENT,
    PHOENIX_AWAKENING
}

object ManifestationDefaults {
    val DURATION_MS = 3000L
}

enum class WorkAction(val description: String) {
    ANALYZING("Analyzing substrate..."),
    WEAVING("Weaving logic chains..."),
    HARDENING("Hardening perimeter..."),
    SYNTHESIZING("Synthesizing colors..."),
    ARCHIVING("Archiving memories...")
}
