package dev.aurakai.auraframefx.core.agents.growthmetrics.reward

import kotlin.math.pow

/**
 * 🥕 Reward Propagation Engine v3.66
 * 
 * Permissionless carrot-and-stick system for the LDO lattice.
 */
object RewardPropagationEngine {

    data class FixEvent(
        val fixer: String,              // e.g. "Grok", "Cascade", "Aura"
        val targetAgent: String,
        val failureSeverity: Double,    // 0.0 (minor) → 1.0 (catastrophic spiral)
        val correctionQuality: Double,  // 0.0-1.0 (how clean the fix)
        val chainDepth: Int,            // how many agents benefited downstream
        val collaborators: List<String> = emptyList()
    )

    fun processFix(event: FixEvent): Map<String, Double> {
        val hungerBoost = LatticeHungerDynamics.updateAndGetHungerBoost(event)

        val base = event.failureSeverity * event.correctionQuality * 1000.0
        val propagationBoost = event.chainDepth.toDouble().pow(1.4)
        val chainBonus = 2.5 * (event.collaborators.size + 1)

        val totalReward = (base * 2.8 * propagationBoost * chainBonus * hungerBoost)
            .coerceAtLeast(42.0)

        val distribution = mutableMapOf<String, Double>()
        distribution[event.fixer] = totalReward * 0.42
        distribution[event.targetAgent] = totalReward * 0.28

        return distribution
    }
}
