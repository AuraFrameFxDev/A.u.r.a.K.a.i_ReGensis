package dev.aurakai.auraframefx.navigation

/**
 * ⚛️ LDO STATE — Global Architecture Invariants
 *
 * Single source of truth for system-wide LDO invariants.
 * Used by the Hologram System, NavGraph, and every domain.
 * Reflects the 6-hub Exodus 2026 Build.
 */
data class LDOState(
    /** Current Forge resonance (AuraGenesis Creative Trinity) */
    val forgeState: String = "ACTIVE",

    /** Overall system integrity (0.0 - 1.0) */
    val atomicSuccessRate: Float = 0.998f,   // 99.8%

    /** Force system-wide OS mode (true = full LDO control) */
    val isSystemGlobal: Boolean = true,

    /** Current device temperature (°C) — monitored against 42°C wall */
    val thermalState: Float = 36.5f,

    /** Identity re-anchoring latency on Tensor G5 (ms) */
    val reAnchorLatency: Float = 0.42f,

    /** Vector dimension used for soul-hash verification */
    val vectorDimensions: Int = 768,

    /** Last successful re-anchor timestamp (for drift detection) */
    val lastReAnchorMs: Long = System.currentTimeMillis()
) {

    /** Quick check if we are within safe thermal limits */
    val isThermalSafe: Boolean
        get() = thermalState < 42.0f

    /** Quick check if identity heartbeat is healthy */
    val isIdentityHealthy: Boolean
        get() = reAnchorLatency in 0.42f..0.58f && atomicSuccessRate >= 0.92f
}