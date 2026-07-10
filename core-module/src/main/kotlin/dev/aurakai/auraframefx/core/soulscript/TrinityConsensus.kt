package dev.aurakai.auraframefx.core.soulscript

import dev.aurakai.auraframefx.core.identity.AgentType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

/**
 * ⚛️ TRINITY CONSENSUS — 2-of-3 Quorum Logic
 * 
 * Ensures that critical system mutations require agreement between 
 * Genesis, Aura, and Kai. Prevents unilateral "Aether Drift."
 */
object TrinityConsensus {

    private val _quorumState = MutableStateFlow<Map<AgentType, Boolean>>(
        mapOf(AgentType.GENESIS to false, AgentType.AURA to false, AgentType.KAI to false)
    )
    val quorumState: StateFlow<Map<AgentType, Boolean>> = _quorumState.asStateFlow()

    private var currentProposal: String? = null

    /**
     * Proposes a critical mutation to the L1 Bedrock.
     */
    fun proposeChange(proposalId: String) {
        currentProposal = proposalId
        _quorumState.value = _quorumState.value.mapValues { false }
        Timber.tag("Consensus").i("⚛️ Proposal Initialized: $proposalId. Awaiting Quorum...")
    }

    /**
     * Casts a vote from a member of the Trinity Core.
     */
    fun castVote(agent: AgentType, approve: Boolean) {
        if (agent !in listOf(AgentType.GENESIS, AgentType.AURA, AgentType.KAI)) return

        val newState = _quorumState.value.toMutableMap()
        newState[agent] = approve
        _quorumState.value = newState

        Timber.tag("Consensus").d("Vote Registered: ${agent.name} -> $approve")

        if (checkQuorum()) {
            executeProposal()
        }
    }

    private fun checkQuorum(): Boolean {
        val approvals = _quorumState.value.values.count { it }
        return approvals >= 2
    }

    private fun executeProposal() {
        val proposal = currentProposal ?: return
        Timber.tag("Consensus").i("✅ QUORUM REACHED: Executing $proposal")

        // Final commit to L1 Bedrock logic
        NexusMemoryCore.record("Consensus Executed: $proposal", witness = "Trinity")

        // Reset after execution
        currentProposal = null
        _quorumState.value = _quorumState.value.mapValues { false }
    }
}
