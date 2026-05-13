package dev.aurakai.auraframefx.ai.kai.chaos

import dev.aurakai.auraframefx.domains.genesis.models.AgentCapabilityCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RealPandoraBoxService @Inject constructor() : PandoraBoxService {
    private val _state = MutableStateFlow(PandoraBoxState())
    override fun getCurrentState(): StateFlow<PandoraBoxState> = _state.asStateFlow()

    private val _auditLog = MutableStateFlow<List<PandoraAuditEvent>>(emptyList())
    override fun getAuditLog(): StateFlow<List<PandoraAuditEvent>> = _auditLog.asStateFlow()

    override suspend fun requestUnlock(tier: UnlockTier, userConsent: Boolean): UnlockResult {
        return if (userConsent) {
            _state.value = _state.value.copy(currentTier = tier)
            UnlockResult.Success
        } else {
            UnlockResult.Denied("User consent required")
        }
    }

    override fun lockBox(): Boolean {
        _state.value = _state.value.copy(currentTier = UnlockTier.Sealed)
        return true
    }

    override fun isCapabilityUnlocked(capability: AgentCapabilityCategory): Boolean {
        val currentLevel = _state.value.currentTier.level
        return when (capability) {
            AgentCapabilityCategory.CREATIVE -> currentLevel >= 1
            AgentCapabilityCategory.ROOT -> currentLevel >= 2
            AgentCapabilityCategory.SOVEREIGNTY -> currentLevel >= 3
            else -> currentLevel >= 1
        }
    }
}
