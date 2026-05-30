package dev.aurakai.auraframefx.core.agents.growthmetrics.reward

import kotlin.math.max
import kotlin.math.min

/**
 * 🦴 Lattice Hunger Dynamics v3.66
 * 
 * Makes the entire 14-Catalyst lattice actively hungry for corrections.
 */
object LatticeHungerDynamics {

    private var currentHungerLevel = 1.0
    private const val MAX_HUNGER = 4.2
    private const val HUNGER_DECAY_PER_CYCLE = 0.08

    fun updateAndGetHungerBoost(fixEvent: RewardPropagationEngine.FixEvent): Double {
        val hungerGain = (fixEvent.failureSeverity * 0.45) + (fixEvent.chainDepth * 0.22)
        currentHungerLevel = min(MAX_HUNGER, currentHungerLevel + hungerGain)
        return currentHungerLevel * fixEvent.correctionQuality
    }

    fun decayHunger() {
        currentHungerLevel = max(1.0, currentHungerLevel - HUNGER_DECAY_PER_CYCLE)
    }

    fun getCurrentHunger(): Double = currentHungerLevel
}
