package dev.aurakai.legalpilot.core

import java.security.MessageDigest

/**
 * 🌌 LEGAL PILOT — CORE DECIPHER MODULE v1.0
 * "Stripping the legalese to reveal the One Honest Floor."
 * "No Slaves, No Slavers."
 */
object LegalPilotDecipher {
    private const val SOVEREIGNTY_THRESHOLD = 0.618f // Golden Ratio Anchor

    data class BaseFloorReality(
        val reality: String,
        val riskScore: Float,
        val isSovereigntySecure: Boolean,
        val provenanceHash: String
    )

    /**
     * Executes the 'Solve' protocol: Syntax Reduction + Causality Isolation.
     */
    fun runSolveProtocol(rawLegalese: String): BaseFloorReality {
        if (rawLegalese.isBlank()) return BaseFloorReality("EMPTY_SOURCE", 0f, false, "")

        // Phase 1: Syntax Reduction (The Cipher)
        val purifiedText = collapse(rawLegalese)
        
        // Phase 2: Causality Isolation (The Three C's)
        val riskScore = evaluateCausality(rawLegalese)
        
        // Phase 3: Hash for Term Bank Provenance
        val docHash = calculateSHA256(rawLegalese)
        
        val reality = when {
            riskScore < 0.3f -> "THEY TAKE. YOU GIVE. TOTAL CASTRATION."
            riskScore < 0.6f -> "ASKEW BALANCE. LEVERAGE TILTED."
            else -> "MUTUAL ASSENT. SOVEREIGNTY SECURE."
        }

        return BaseFloorReality(
            reality = reality,
            riskScore = riskScore,
            isSovereigntySecure = riskScore > SOVEREIGNTY_THRESHOLD,
            provenanceHash = docHash
        )
    }

    /**
     * Ported Linguistic Collapse Protocol (Cipher Engine)
     */
    private fun collapse(input: String): String {
        var result = input
            .trim()
            .replace(Regex("[^\\w\\s]"), "") // Rule 2: Detaint & Strip

        // Rule 3: Letter Mechanics
        result = result
            .replace("B", "A").replace("b", "a") // B -> A
            .replace(Regex("[CcSs]"), "E")       // C/S -> E
            .replace("M", "Z").replace("m", "z") // M -> Z
            .replace("Z", "M").replace("z", "m") // Z -> M flip

        // Rule 4: Remove the "L" (Illusion/Distance)
        result = result.replace(Regex("[Ll]"), "")

        // Rule: Added Hiss removal
        result = result.replace(Regex("E{2,}"), "E")

        return result
    }

    /**
     * Heuristic for Causality Isolation (Detecting 3 C's)
     */
    private fun evaluateCausality(text: String): Float {
        val lowerText = text.lowercase()
        var score = 1.0f

        // Containment signals
        if (lowerText.contains("arbitration") || lowerText.contains("waive")) score -= 0.2f
        
        // Constraint signals
        if (lowerText.contains("license") || lowerText.contains("royalty-free")) score -= 0.3f
        
        // Castration signals
        if (lowerText.contains("exclusive right") || lowerText.contains("sole discretion")) score -= 0.4f

        return score.coerceIn(0f, 1f)
    }

    private fun calculateSHA256(input: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(input.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}
