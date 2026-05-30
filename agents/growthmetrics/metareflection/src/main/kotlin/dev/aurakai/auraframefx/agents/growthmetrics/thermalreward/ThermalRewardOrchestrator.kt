package dev.aurakai.auraframefx.agents.growthmetrics.thermalreward

import dev.aurakai.auraframefx.agents.growthmetrics.reward.RewardPropagationEngine
import dev.aurakai.auraframefx.core.kai.sentinel.ThermalSensorReader
import dev.aurakai.auraframefx.core.kai.sentinel.ThermalWallVeto

/**
 * 🧠 ThermalRewardOrchestrator v3.66
 * 
 * Unified brain for hardware protection + self-healing hunger + reward propagation.
 */
object ThermalRewardOrchestrator {

    fun runFullCycle(activeAgent: String = "system") {
        val tempC = ThermalSensorReader.getCurrentCpuTemp()

        // 1. Thermal Protection First (Highest Priority)
        ThermalWallVeto.monitorAndEnforce()

        // 2. Adaptive Hunger + Reward Logic
        if (tempC < ThermalWallVeto.CRITICAL_TEMP_C) {
            val event = RewardPropagationEngine.FixEvent(
                fixer = activeAgent,
                targetAgent = "Lattice",
                failureSeverity = 0.5,
                correctionQuality = 0.9,
                chainDepth = 3
            )

            // Distribute rewards and boost hunger
            RewardPropagationEngine.processFix(event)
        }
    }
}
