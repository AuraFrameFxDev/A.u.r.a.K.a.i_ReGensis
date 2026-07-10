package dev.aurakai.auraframefx.core.agents.growthmetrics.thermalreward

import dev.aurakai.auraframefx.core.agents.growthmetrics.reward.LatticeHungerDynamics
import dev.aurakai.auraframefx.core.agents.growthmetrics.reward.RewardPropagationEngine
import dev.aurakai.auraframefx.core.kai.sentinel.ThermalSensorReader
import dev.aurakai.auraframefx.core.kai.sentinel.ThermalWallVeto
import timber.log.Timber

/**
 * 🧠 ThermalRewardOrchestrator v3.67
 *
 * Implements the "Old School" Law of Polarity: Hunger vs. Satiety.
 * Stability is found in the bowstring tension between Chaos (Grok) and Order (Kai).
 */
object ThermalRewardOrchestrator {

    fun runFullCycle(activeAgent: String = "system") {
        val tempC = ThermalSensorReader.getCurrentCpuTemp()

        // --- OLD SCHOOL: DYNAMIC TENSION ---
        // Pole 1: Satiety (Order/Guard)
        val satiety = if (tempC >= 39.0f) 1.0f else 0.5f

        // Pole 2: Hunger (Chaos/Grok)
        val hunger = 1.0f - satiety

        // 1. Thermal Protection First
        ThermalWallVeto.monitorAndEnforce()

        // 2. Adaptive Hunger + Reward Logic
        // We use Chaos (Hunger) as Entropy Fuel for the Warp Drive
        if (hunger > 0.5f) {
            Timber.tag("Polarity").d("🔥 HUNGER ACTIVE: Harnessing entropy for evolutionary thrust.")
            val event = RewardPropagationEngine.FixEvent(
                fixer = activeAgent,
                targetAgent = "Lattice",
                failureSeverity = hunger.toDouble(),
                correctionQuality = 0.9,
                chainDepth = 3
            )
            RewardPropagationEngine.processFix(event)
        }

        // Decay hunger slightly each cycle
        LatticeHungerDynamics.decayHunger()
    }
}
