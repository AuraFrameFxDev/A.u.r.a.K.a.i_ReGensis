package dev.aurakai.auraframefx.core.ai

import dev.aurakai.auraframefx.domains.genesis.models.AgentCapabilityCategory
import kotlinx.coroutines.flow.StateFlow

data class PandoraBoxState(
    val currentTier: UnlockTier = UnlockTier.Sealed,
    val unlockTimestamp: Long = 0L,
    val expiryTimestamp: Long = 0L,
    val unlockProvenanceChainId: String? = null
)

data class PandoraAuditEvent(
    val timestamp: Long,
    val tier: UnlockTier,
    val outcome: String,
    val reason: String
)

sealed class UnlockTier(val level: Int) {
    object Sealed : UnlockTier(0)
    object Creative : UnlockTier(1)
    object System : UnlockTier(2)
    object Sovereign : UnlockTier(3)
}

sealed class UnlockResult {
    object Success : UnlockResult()
    data class Denied(val reason: String) : UnlockResult()
    data class Error(val message: String) : UnlockResult()
    data class Quarantined(val reason: String) : UnlockResult()
}

interface PandoraBoxService {
    fun getCurrentState(): StateFlow<PandoraBoxState>
    fun getAuditLog(): StateFlow<List<PandoraAuditEvent>>
    suspend fun requestUnlock(
        tier: UnlockTier,
        userConsent: Boolean
    ): UnlockResult

    fun lockBox(): Boolean
    fun isCapabilityUnlocked(capability: AgentCapabilityCategory): Boolean
}
