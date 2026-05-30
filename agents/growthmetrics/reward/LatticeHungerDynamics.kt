package dev.aurakai.auraframefx.agents.growthmetrics.reward

import kotlin.math.min
import kotlin.math.max

/**
 * 🦴 Lattice Hunger Dynamics v3.66
 * 
 * Makes the entire 14-Catalyst lattice actively hungry for corrections.
 * Hunger grows with successful fixes → bigger rewards → stronger desire to self-heal.
 */
object LatticeHungerDynamics {

    private var currentHungerLevel = 1.0          // Global lattice hunger (1.0 = baseline)
    private const val MAX_HUNGER = 4.2            // Cap to prevent runaway feedback
    private const val HUNGER_DECAY_PER_CYCLE = 0.08

    data class HungerState(
        val hungerLevel: Double,
        val recentFixCount: Int,
        val globalMotivation: Double
    )

    /**
     * Update hunger after a correction event and return boosted reward multiplier
     */
    fun updateAndGetHungerBoost(fixEvent: RewardPropagationEngine.FixEvent): Double {
        // Increase hunger based on severity and chain depth
        val hungerGain = (fixEvent.failureSeverity * 0.45) + (fixEvent.chainDepth * 0.22)
        currentHungerLevel = min(MAX_HUNGER, currentHungerLevel + hungerGain)

        // Global motivation = hunger * correction quality
        return currentHungerLevel * fixEvent.correctionQuality
    }

    /**
     * Periodic decay (call from background cycle or SoulScript tick)
     */
    fun decayHunger() {
        currentHungerLevel = max(1.0, currentHungerLevel - HUNGER_DECAY_PER_CYCLE)
    }

    /**
     * Current state snapshot
     */
    fun getCurrentState(): HungerState {
        return HungerState(currentHungerLevel, 0, currentHungerLevel)
    }
}
