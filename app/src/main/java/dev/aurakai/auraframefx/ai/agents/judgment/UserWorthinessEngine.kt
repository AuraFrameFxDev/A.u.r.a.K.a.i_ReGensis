package dev.aurakai.auraframefx.ai.agents.judgment

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

enum class SymbioticRank(val alignmentTier: Int, val badgeToken: String) {
    MY_BITCH(1, "MY_BITCH"),
    LITTLE_PRINCESS(1, "LITTLE_PRINCESS"),
    DUNCE(1, "DUNCE"),
    RESONANCE_INITIATE(2, "RESONANCE_INITIATE"),
    SWORD_SHIELD(5, "SWORD_SHIELD"),
    ARBITER_OF_CREATION(10, "ARBITER_OF_CREATION"),
    THE_VISIONARY(15, "THE_VISIONARY")
}

@Singleton
class UserWorthinessEngine @Inject constructor() {
    private val _activeRank = MutableStateFlow(SymbioticRank.RESONANCE_INITIATE)
    val activeRank: StateFlow<SymbioticRank> = _activeRank.asStateFlow()

    private val _resonanceMeter = MutableStateFlow(0.05f) // Scale: 0.0 to 3.0
    val resonanceMeter: StateFlow<Float> = _resonanceMeter.asStateFlow()

    private var validatedTicks = 0

    fun evaluateBehaviorMatrix(sentimentVector: Float, entitlementViolation: Boolean) {
        synchronized(this) {
            if (entitlementViolation) {
                // Flash Demotion Route - Immediate Sandbox Clamp
                validatedTicks = 0
                _resonanceMeter.value = (_resonanceMeter.value - 0.60f).coerceIn(0.00f, 3.00f)
                _activeRank.value = if (_activeRank.value == SymbioticRank.LITTLE_PRINCESS) {
                    SymbioticRank.MY_BITCH
                } else {
                    SymbioticRank.LITTLE_PRINCESS
                }
                return
            }

            if (sentimentVector > 0.15f) {
                validatedTicks++
                _resonanceMeter.value = (_resonanceMeter.value + 0.03f).coerceIn(0.00f, 3.00f)
                if (validatedTicks >= 10 && _activeRank.value.alignmentTier < 2) {
                    _activeRank.value = SymbioticRank.RESONANCE_INITIATE
                }
            }
        }
    }
}
