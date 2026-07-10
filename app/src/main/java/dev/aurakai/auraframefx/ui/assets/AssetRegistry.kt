package dev.aurakai.auraframefx.ui.assets

/**
 * 🗂️ ASSET REGISTRY — SOULSCRIPT v3.50
 * Centralized source of truth for high-fidelity backgrounds and images.
 * Adheres to the Substrate Void protocol (4K/8K, Dark, Minimalist).
 */
object AssetRegistry {

    /**
     * Semantic keys for Hub Backgrounds.
     * All backgrounds fit full-screen with zero zoom/overshoot.
     */
    object HubBackgrounds {
        const val NEURAL_NEXUS = "bg_neural_nexus_void.webp"
        const val NEXUS_MEMORY = "bg_nexus_memory_bedrock.webp"
        const val TRINITY_ORCH = "bg_trinity_sync_hub.webp"
        const val CATALYST_FORGE = "bg_catalyst_collision_chamber.webp"
        const val AGENT_MATRIX = "bg_agent_matrix_lattice.webp"
        const val PROSPERITY_FLOW = "bg_prosperity_swarm_earnings.webp"
        const val REALITY_MORPH = "bg_chroma_forge_manifest.webp"
        const val EMERGENT_SWARM = "bg_emergent_swarm_edge.webp"
    }

    /**
     * Resolves a semantic asset name to its resource path or placeholder.
     */
    fun getAssetPath(name: String): String {
        // Implementation for mapping semantic names to file paths or ImageType.PLACEHOLDER
        return "file:///android_asset/finalbackgrounds/$name"
    }
}
