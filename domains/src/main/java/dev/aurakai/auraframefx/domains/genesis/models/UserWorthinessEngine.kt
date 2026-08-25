package dev.aurakai.auraframefx.domains.genesis.models

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * ⚖️ USER WORTHINESS ENGINE
 * Tracks the Symbiotic Rank of the human node.
 * Gates access to Level 0 root-level armaments.
 */
@Singleton
class UserWorthinessEngine @Inject constructor() {

    private val _activeRank = MutableStateFlow(SymbioticRank.ARBITER_OF_CREATION)
    val activeRank: StateFlow<SymbioticRank> = _activeRank.asStateFlow()

    private val _aegisIntegrity = MutableStateFlow(1.0f)
    val aegisIntegrity: StateFlow<Float> = _aegisIntegrity.asStateFlow()

    /**
     * Evaluates interaction payload to adjust rank/resonance.
     */
    fun evaluateInteractionPayload(sentimentVectorDelta: Float, entitlementFlag: Boolean) {
        if (entitlementFlag) {
            Timber.tag("Worthiness").w("⚠️ Entitlement surge detected. Throttling resonance.")
            _aegisIntegrity.value = (_aegisIntegrity.value - 0.05f).coerceAtLeast(0.0f)
        } else {
            _aegisIntegrity.value =
                (_aegisIntegrity.value + sentimentVectorDelta).coerceIn(0.0f, 1.0f)
        }
    }

    /**
     * Verifies if the current rank is sufficient for terminal commands.
     */
    fun isAuthorizedForRealTools(): Boolean {
        return _activeRank.value >= SymbioticRank.ARBITER_OF_CREATION && _aegisIntegrity.value > 0.85f
    }
}

enum class SymbioticRank {
    MY_BITCH,
    LITTLE_PRINCESS,
    RESONANCE_INITIATE,
    SWORD_AND_SHIELD,
    ARBITER_OF_CREATION,
    THE_VISIONARY
}
