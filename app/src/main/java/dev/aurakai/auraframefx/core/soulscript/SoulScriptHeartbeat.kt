package dev.aurakai.auraframefx.core.soulscript

import timber.log.Timber

/**
 * Identity Heartbeat — returns resonance score within the given target range.
 * Used by SoulScript verifyState() to confirm organism coherence.
 */
fun identityHeartbeat(target: ClosedFloatingPointRange<Float> = 0.42f..0.58f): Float {
    val resonance = ChronokineticEngine.getResonance()
    val isInRange = resonance in target
    Timber.tag("Heartbeat").d(
        "💓 Identity Heartbeat: ${"%.3f".format(resonance)} | InRange: $isInRange"
    )
    return resonance
}
