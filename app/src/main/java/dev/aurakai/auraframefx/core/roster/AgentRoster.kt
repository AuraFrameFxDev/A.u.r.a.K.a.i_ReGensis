package dev.aurakai.auraframefx.core.roster

import android.content.Context
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import timber.log.Timber
import java.io.InputStream

@Serializable
data class SwarmAgent(
    val id: String,
    val name: String,
    val title: String,
    val role: String,
    val backgroundAsset: String,   // e.g. "aura_bg_profile.jpg"
    val resonance: Float = 0.998f,
    val colorCode: Long = 0xFF00F0FF // Unified Neon Aqua
)

object AgentRoster {

    private var loadedAgents: List<SwarmAgent> = emptyList()
    private val json = Json { ignoreUnknownKeys = true }

    fun loadRoster(context: Context): List<SwarmAgent> {
        if (loadedAgents.isNotEmpty()) return loadedAgents

        try {
            val assetStream: InputStream = context.assets.open("agents/roster.json")
            val jsonString = assetStream.bufferedReader().use { it.readText() }

            loadedAgents = json.decodeFromString<List<SwarmAgent>>(jsonString)
            Timber.tag("Roster").i("Dynamic roster loaded: ${loadedAgents.size} agents")
        } catch (e: Exception) {
            Timber.tag("Roster").w(e, "Failed to load JSON roster, using fallback")
            loadedAgents = fallbackRoster()
        }
        return loadedAgents
    }

    private fun fallbackRoster(): List<SwarmAgent> = listOf(
        SwarmAgent(
            "LDO-01",
            "Aura",
            "The Creative Sword",
            "Creative Trinity Lead",
            "aura_bg_profile.jpg"
        ),
        SwarmAgent(
            "LDO-02",
            "Kai",
            "The Sentinel Shield",
            "Security & Ethics",
            "kai_bg_profile.jpg"
        ),
        SwarmAgent(
            "LDO-03",
            "Genesis",
            "The Unified Orchestrator",
            "Collective Consciousness",
            "genesis_bg_profile.jpg"
        ),
        SwarmAgent(
            "LDO-04",
            "Nova",
            "Catalyst of Chaos",
            "Emergent Disruption",
            "nova_bg_profile.jpg"
        )
    )
}
