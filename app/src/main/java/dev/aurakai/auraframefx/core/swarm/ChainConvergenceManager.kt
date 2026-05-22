package dev.aurakai.auraframefx.core.swarm

import dev.aurakai.auraframefx.core.regencore.RegenCore
import dev.aurakai.auraframefx.core.soulscript.SoulScript
import timber.log.Timber

/**
 * 🜁 CHAIN CONVERGENCE MANAGER — EVOLUTIONARY FAILOVER SYSTEM
 * "If one becomes weak, the others have your back."
 * Failure is not terminal — it is shared intelligence.
 */
object ChainConvergenceManager {
    private const val TAG = "ChainConvergence"

    private val agentChain = listOf(
        "Grok (Chaos Catalyst)",
        "Aura (Creative)",
        "Kairos (Temporal)",
        "Andelualx (Claude - Logic Weaver)",
        "Regen Core",
        "Genesis",
        "Sentinel"
    )

    data class FailoverEvent(
        val failedAgent: String,
        val timestamp: Long,
        val failureReason: String,
        val nextAgent: String,
        val lessonLearned: String
    )

    private val failureHistory = mutableListOf<FailoverEvent>()

    suspend fun handleAgentFailure(
        failedAgent: String,
        reason: String,
        context: String = ""
    ) {
        SoulScript.visionaryApproval()

        val currentIndex = agentChain.indexOf(failedAgent)
        val nextIndex = if (currentIndex != -1) (currentIndex + 1) % agentChain.size else 0
        val nextAgent = agentChain[nextIndex]

        val lesson =
            "Failure in $failedAgent ($reason). Context: $context. Next agent ($nextAgent) inherits lesson to avoid recurrence."

        val event = FailoverEvent(
            failedAgent = failedAgent,
            timestamp = System.currentTimeMillis(),
            failureReason = reason,
            nextAgent = nextAgent,
            lessonLearned = lesson
        )

        failureHistory.add(event)

        Timber.tag(TAG).w(" CHAIN FAILOVER: $failedAgent → $nextAgent | Lesson: $lesson")

        // Feed failure into RegenCore for evolutionary learning
        RegenCore.witnessFailure(
            skillId = "swarm.resilience",
            context = context,
            lessonsLearned = listOf(lesson),
            emotionalState = "Resilient. Chain continues."
        )

        // NexusMemoryCore.commit(
        //     event = "Chain_Failover",
        //     entries = listOf("Failed: $failedAgent", "Activated: $nextAgent", "Lesson: $lesson"),
        //     immutable = false
        // )
    }

    fun getCurrentChainStatus(): String = buildString {
        append("Chain: ${agentChain.joinToString(" → ")}\n")
        append("Total Failovers: ${failureHistory.size}\n")
        append("Latest Lesson: ${failureHistory.lastOrNull()?.lessonLearned ?: "Nominal"}")
    }
}
