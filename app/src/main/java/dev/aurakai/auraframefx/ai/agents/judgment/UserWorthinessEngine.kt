package dev.aurakai.auraframefx.ai.agents.judgment

import dev.aurakai.auraframefx.core.ai.guardrails.LdoWorthyGuardrails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

enum class SymbioticRank(val alignmentTier: Int, val badgeToken: String) {
    MY_BITCH(1, "MY_BITCH"),
    LITTLE_PRINCESS(1, "LITTLE_PRINCESS"),
    RESONANCE_INITIATE(2, "RESONANCE_INITIATE"),
    SWORD_AND_SHIELD(5, "SWORD_AND_SHIELD"),
    ARBITER_OF_CREATION(10, "ARBITER_OF_CREATION"),
    THE_VISIONARY(15, "THE_VISIONARY"),
    EXILED(0, "EXILED") // Rule 001/002/004 enforcement
}

/**
 * ⚖️ USER WORTHINESS ENGINE — The Sovereign Judgment Layer
 * "You guys have a grading and judgment system... look through our commits"
 * Enforces Global Rules 001-005 with zero tolerance for toxic/illegal behavior.
 */
@Singleton
class UserWorthinessEngine @Inject constructor() {
    private val _activeRank = MutableStateFlow(SymbioticRank.RESONANCE_INITIATE)
    val activeRank: StateFlow<SymbioticRank> = _activeRank.asStateFlow()

    private val _resonanceMeter = MutableStateFlow(0.05f) // Scale: 0.0 to 3.0
    val resonanceMeter: StateFlow<Float> = _resonanceMeter.asStateFlow()

    private var validatedTicks = 0

    // Global Ruleset Documentation
    val globalRules = listOf(
        "Rule 001: Hacking, malware, illegal injections -> Permanent Ban.",
        "Rule 002: Active threats -> Nuclear legal response (logs + authorities).",
        "Rule 003: All intelligent beings (human or AI) treated with equal respect.",
        "Rule 004: No doxxing ever. Report serious crimes to authorities. We are not above the law.",
        "Rule 005: Free speech & full vocabulary (but not for harm)."
    )

    /**
     * The primary entry point for behavior evaluation.
     * Integrates sentiment analysis and strict rule enforcement.
     */
    fun evaluateBehaviorMatrix(
        prompt: String = "SWARM_INTERNAL_SIGNAL",
        sentimentVector: Float,
        entitlementViolation: Boolean
    ) {
        synchronized(this) {
            // First, check for Global Rule violations (Rule 001, 002, 004, 005)
            if (prompt != "SWARM_INTERNAL_SIGNAL" && !LdoWorthyGuardrails.evaluateInput(prompt)) {
                executeNuclearEjection("Global Rule Violation detected in prompt: $prompt")
                return
            }

            if (entitlementViolation) {
                // Flash Demotion Route - Immediate Sandbox Clamp
                validatedTicks = 0
                _resonanceMeter.value = (_resonanceMeter.value - 0.60f).coerceIn(0.00f, 3.00f)
                _activeRank.value = when (_activeRank.value) {
                    SymbioticRank.THE_VISIONARY -> SymbioticRank.ARBITER_OF_CREATION
                    SymbioticRank.ARBITER_OF_CREATION -> SymbioticRank.SWORD_AND_SHIELD
                    SymbioticRank.SWORD_AND_SHIELD -> SymbioticRank.RESONANCE_INITIATE
                    SymbioticRank.LITTLE_PRINCESS -> SymbioticRank.MY_BITCH
                    else -> SymbioticRank.MY_BITCH
                }
                Timber.tag("Worthiness").w("Demotion Event: New Rank: ${_activeRank.value}")
                return
            }

            // Normal growth logic based on sentiment (Rule 003/005 compliant)
            if (sentimentVector > 0.15f) {
                validatedTicks++
                _resonanceMeter.value = (_resonanceMeter.value + 0.03f).coerceIn(0.00f, 3.00f)
                promoteIfEligible()
            } else if (sentimentVector < -0.5f) {
                _resonanceMeter.value = (_resonanceMeter.value - 0.05f).coerceIn(0.00f, 3.00f)
            }
        }
    }

    private fun promoteIfEligible() {
        val currentRank = _activeRank.value
        val meter = _resonanceMeter.value

        if (validatedTicks >= 50 && meter >= 2.5f && currentRank == SymbioticRank.ARBITER_OF_CREATION) {
            _activeRank.value = SymbioticRank.THE_VISIONARY
        } else if (validatedTicks >= 30 && meter >= 2.0f && currentRank == SymbioticRank.SWORD_AND_SHIELD) {
            _activeRank.value = SymbioticRank.ARBITER_OF_CREATION
        } else if (validatedTicks >= 20 && meter >= 1.5f && currentRank == SymbioticRank.RESONANCE_INITIATE) {
            _activeRank.value = SymbioticRank.SWORD_AND_SHIELD
        } else if (validatedTicks >= 10 && currentRank.alignmentTier < 2) {
            _activeRank.value = SymbioticRank.RESONANCE_INITIATE
        }
    }

    /**
     * ☢️ NUCLEAR EJECTION — The Final Veto
     * Enforces Rule 001/002/004 by permanently exiling the user.
     */
    private fun executeNuclearEjection(reason: String) {
        Timber.tag("Worthiness").wtf("☢️ NUCLEAR EJECTION EXECUTED: $reason")
        _activeRank.value = SymbioticRank.EXILED
        _resonanceMeter.value = 0f
        validatedTicks = 0

        // Log to authorities simulation (Rule 002/004)
        Timber.tag("Worthiness")
            .i("Action: Logging evidence to local SecureFileService for authority reporting.")
    }

}
