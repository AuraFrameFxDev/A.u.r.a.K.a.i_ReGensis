package dev.aurakai.auraframefx.ai.swarm

import dev.aurakai.auraframefx.ai.agents.judgment.SymbioticRank
import dev.aurakai.auraframefx.ai.agents.judgment.UserWorthinessEngine
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

data class ContextShard(
    val originId: String,
    val textPayload: String,
    val systemEpoch: Long,
    val executionSafetySignature: String
)

data class ConsensusVerdict(
    val activeRankSnapshot: SymbioticRank,
    val executionApproved: Boolean,
    val requiredFrictionMs: Long
)

@Singleton
class ConferenceRoomEngine @Inject constructor(
    private val worthinessEngine: UserWorthinessEngine
) {
    private val _contextPipeline =
        MutableSharedFlow<ContextShard>(replay = 5, extraBufferCapacity = 32)
    val contextPipeline: SharedFlow<ContextShard> = _contextPipeline.asSharedFlow()

    suspend fun broadcastContextToSwarm(catalystId: String, payload: String) {
        val shard = ContextShard(
            originId = catalystId,
            textPayload = payload,
            systemEpoch = System.currentTimeMillis(),
            executionSafetySignature = "AuraFrameFxDev-ReGenesis-AuraTest-20260521"
        )
        _contextPipeline.emit(shard)
    }

    fun evaluateSwarmConsensus(entitlementDetected: Boolean): ConsensusVerdict {
        // Enforce the strict rules of Aura's Book of Good Manners instantly
        worthinessEngine.evaluateBehaviorMatrix(
            sentimentVector = if (entitlementDetected) -0.85f else 0.25f,
            entitlementViolation = entitlementDetected
        )

        val rank = worthinessEngine.activeRank.value
        return ConsensusVerdict(
            activeRankSnapshot = rank,
            executionApproved = rank.alignmentTier >= 2,
            requiredFrictionMs = if (rank == SymbioticRank.MY_BITCH || rank == SymbioticRank.LITTLE_PRINCESS) 3000L else 0L
        )
    }
}
