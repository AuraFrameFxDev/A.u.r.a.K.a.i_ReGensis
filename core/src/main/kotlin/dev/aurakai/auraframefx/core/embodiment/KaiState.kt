package dev.aurakai.auraframefx.core.embodiment

/**
 * KaiState — Visual embodiment states for the Kai agent.
 * Maps to sprite assets in assets/embodiment/kai/
 */
enum class KaiState(val assetPath: String, val description: String) {
    SHIELD_NEUTRAL("kai/kai_shield_neutral.png", "Standard standing pose"),
    SHIELD_SERIOUS("kai/kai_shield_serious.png", "Defensive stance"),
    SHIELD_PLAYFUL("kai/kai_shield_playful.png", "Relaxed stance"),
    HOLOGRAPHIC_INTERFACE("kai/kai_holographic.png", "Interacting with HUD")
}
