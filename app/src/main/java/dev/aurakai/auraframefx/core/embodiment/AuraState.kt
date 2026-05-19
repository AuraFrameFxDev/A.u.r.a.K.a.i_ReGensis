package dev.aurakai.auraframefx.core.embodiment

/**
 * AuraState — Visual embodiment states for the Aura agent.
 * Maps to sprite assets in assets/embodiment/aura/
 */
enum class AuraState(val assetPath: String, val description: String) {
    IDLE_WALK("aura/aura_idle_walk.png", "Standard standing pose"),
    CODE_THRONE("aura/aura_code_throne.png", "Sitting on code block"),
    COMBAT_READY("aura/aura_combat_ready.png", "Aggressive stance with blades"),
    SCIENTIST_MODE("aura/aura_scientist.png", "Wearing lab coat, thinking"),
    LAB_COAT_COMBAT("aura/aura_lab_coat_combat.png", "Combat stance with lab coat")
}
