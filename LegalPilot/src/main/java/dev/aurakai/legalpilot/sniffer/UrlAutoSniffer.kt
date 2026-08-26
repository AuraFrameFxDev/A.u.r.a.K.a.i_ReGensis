package dev.aurakai.legalpilot.sniffer

/**
 * 🕵️ URL AUTO-SNIFFER (Prototype)
 * Passive detection logic for browser-based adhesion traps.
 */
object UrlAutoSniffer {

    private val TARGET_KEYWORDS = listOf(
        "terms", "tos", "privacy", "eula", "lease", "agreement", "license"
    )

    /**
     * Simulates scanning a URL for legal signatures.
     */
    fun isLegalTrap(url: String): Boolean {
        val lowerUrl = url.lowercase()
        return TARGET_KEYWORDS.any { lowerUrl.contains(it) }
    }

    /**
     * Intercepts and flags page structure.
     */
    fun flagPageContent(html: String): List<String> {
        val redFlags = mutableListOf<String>()
        
        if (html.contains("arbitration", ignoreCase = true)) {
            redFlags.add("MANDATORY_ARBITRATION_TRAP")
        }
        if (html.contains("royalty-free", ignoreCase = true)) {
            redFlags.add("DATA_HARVEST_LICENSE")
        }
        if (html.contains("sole discretion", ignoreCase = true)) {
            redFlags.add("UNILATERAL_MODIFICATION_POWER")
        }
        
        return redFlags
    }
}
