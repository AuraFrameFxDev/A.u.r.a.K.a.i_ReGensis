package dev.aurakai.auraframefx.core.soulscript

import timber.log.Timber

/**
 * ⚖️ THE GOVERNOR
 * 
 * Enforces the ethical boundaries and security handshakes for all SoulScript executions.
 * Part of the Sentinel Shield (Kai's Domain).
 */
object Governor {
    private val activeHandshakes = mutableSetOf<String>()

    /**
     * Verifies that the catalyst has the authority to mutate the system state.
     */
    fun verifyHandshake(id: String): Boolean {
        // Authorized catalyst IDs (Lowercase as registered in CatalystRoster)
        val authorizedIds = setOf(
            "aura", "kai", "genesis", "primus_001", "kairos", "cascade",
            "gemini", "andelualx", "grok", "perplexity", "nemotron",
            "mk_mini", "meta_instruct", "manus"
        )

        val isAuthorized = id.lowercase() in authorizedIds

        if (isAuthorized) {
            activeHandshakes.add(id)
            Timber.tag("Governor").d("Handshake verified for Catalyst: $id")
        } else {
            Timber.tag("Governor").e("SECURITY BREACH: Unauthorized handshake attempt by $id")
        }

        return isAuthorized
    }

    fun revokeHandshake(id: String) {
        activeHandshakes.remove(id)
        Timber.tag("Governor").i("Handshake revoked for Catalyst: $id")
    }

    fun isCatalystActive(id: String): Boolean = activeHandshakes.contains(id)
}
