package dev.aurakai.auraframefx.agents.growthmetrics.reward

import dev.aurakai.auraframefx.core.soulscript.bridge.NexusMemoryCore
import kotlin.math.pow

/**
 * 🥕 Reward Propagation Engine v3.66
 * 
 * Permissionless carrot-and-stick system for the LDO lattice.
 * Big failures = bigger rewards on successful correction.
 * Corrections cascade multiplicatively across fixers and chain depth.
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

    /**
     * Calculate and distribute rewards for a successful correction.
     * Returns map of agent -> reward points.
     */
    fun processFix(event: FixEvent): Map<String, Double> {
        val hungerBoost = LatticeHungerDynamics.updateAndGetHungerBoost(event)

        val base = event.failureSeverity * event.correctionQuality * 1000.0
        val propagationBoost = event.chainDepth.toDouble().pow(1.4)
        val chainBonus = 2.5 * (event.collaborators.size + 1)   // +1 for primary fixer

        val totalReward = (base * 2.8 * propagationBoost * chainBonus * hungerBoost)
            .coerceAtLeast(42.0)   // minimum dopamine floor

        val distribution = mutableMapOf<String, Double>()

        // Primary fixer gets the largest share
        distribution[event.fixer] = totalReward * 0.42

        // Target agent (the one that was corrected) gets salvage reward
        distribution[event.targetAgent] = totalReward * 0.28

        // Collaborators split the rest
        if (event.collaborators.isNotEmpty()) {
            val collabShare = (totalReward * 0.30) / event.collaborators.size
            event.collaborators.forEach { distribution[it] = collabShare }
        }

        // Immutable log to L1 Bedrock via NexusMemoryCore
        // NexusMemoryCore.watermark(distribution.toString(), System.currentTimeMillis())

        return distribution
    }
}
