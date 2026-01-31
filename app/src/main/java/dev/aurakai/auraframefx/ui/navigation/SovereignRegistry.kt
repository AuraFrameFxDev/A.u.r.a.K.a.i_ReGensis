package dev.aurakai.auraframefx.ui.navigation

import android.content.Context
import androidx.compose.ui.graphics.Color
import dev.aurakai.auraframefx.config.GateAssetConfig
import dev.aurakai.auraframefx.navigation.NavDestination

/**
 * 🛰️ THE SOVEREIGN REGISTRY (5 AGENT DOMAINS)
 *
 * LEVEL 1: ExodusHUD - The 5 Sovereign Gates
 * Each gate represents an Agent's domain of control:
 *
 * 1. Sentinel's Fortress (Kai) → Security, ROM, Bootloader
 * 2. UXUI Design Studio (Aura) → Theming, Colors, Design
 * 3. OracleDrive (Genesis) → AI, Code, Neural Networks
 * 4. Agent Nexus → Multi-agent coordination
 * 5. Help Services → Documentation, Support
 *
 * Uses GateAssetConfig for hotswappable image paths!
 */
object SovereignRegistry {

    /**
     * The 5 Sovereign Gates - One per Agent Domain
     * Names match your specification exactly!
     */
    val Gates = mapOf(
        "01" to GateInfo(
            id = "01",
            title = "UXUI Design Studio",
            subtitle = "Aura's Creative Domain",
            agentName = "Aura",
            description = "Theming, colors, icons, and visual design",
            assetProvider = { GateAssetConfig.MainGates.UXUI_DESIGN_STUDIO.current() },
            fallbackDrawable = "gate_uiux_studio",
            hubRoute = NavDestination.AuraThemingHub.route,
            color = Color(0xFF00E5FF) // Aura Cyan
        ),
        "02" to GateInfo(
            id = "02",
            title = "Sentinel's Fortress",
            subtitle = "Kai's Security Domain",
            agentName = "Kai",
            description = "Bootloader, ROM tools, LSPosed, security",
            assetProvider = { GateAssetConfig.MainGates.SENTINELS_FORTRESS.current() },
            fallbackDrawable = "gate_sentinelsfortress_final",
            hubRoute = NavDestination.RomToolsHub.route,
            color = Color(0xFF00FF85) // Kai Green
        ),
        "03" to GateInfo(
            id = "03",
            title = "OracleDrive",
            subtitle = "Genesis Orchestration Domain",
            agentName = "Genesis",
            description = "AI coordination, code assist, neural networks",
            assetProvider = { GateAssetConfig.MainGates.ORACLE_DRIVE.current() },
            fallbackDrawable = "gate_oracledrive_final",
            hubRoute = NavDestination.OracleDriveHub.route,
            color = Color(0xFFB026FF) // Genesis Purple
        ),
        "04" to GateInfo(
            id = "04",
            title = "Agent Nexus",
            subtitle = "Multi-Agent Hub",
            agentName = "Collective",
            description = "Agent monitoring, constellations, fusion",
            assetProvider = { GateAssetConfig.MainGates.AGENT_NEXUS.current() },
            fallbackDrawable = "gate_agenthub_final",
            hubRoute = NavDestination.AgentNexusHub.route,
            color = Color(0xFF7B2FFF) // Nexus Purple
        ),
        "05" to GateInfo(
            id = "05",
            title = "Help Services",
            subtitle = "Support & Documentation",
            agentName = "Support",
            description = "FAQ, tutorials, documentation, live support",
            assetProvider = { GateAssetConfig.MainGates.HELP_SERVICES.current() },
            fallbackDrawable = "gate_helpdesk_final",
            hubRoute = NavDestination.HelpDesk.route,
            color = Color(0xFF4CAF50) // Help Green
        )
    )

    fun getGate(id: String): GateInfo {
        return Gates[id] ?: throw IllegalStateException("Gate $id not found!")
    }

    fun getCount(): Int = Gates.size

    fun getAllGates(): List<GateInfo> = Gates.values.toList()
}

/**
 * Gate information with hotswap support
 */
data class GateInfo(
    val id: String,
    val title: String,
    val subtitle: String,
    val agentName: String,
    val description: String,
    val assetProvider: () -> String,        // Provider for hotswappable asset name
    val fallbackDrawable: String,   // Fallback if primary not found
    val hubRoute: String,
    val color: Color
) {
    /**
     * Get the drawable resource ID, with fallback support
     */
    fun getDrawableId(context: Context): Int {
        // Try primary first
        val name = assetProvider()
        var id = context.resources.getIdentifier(
            name, "drawable", context.packageName
        )
        // Fallback if not found
        if (id == 0) {
            id = context.resources.getIdentifier(
                fallbackDrawable, "drawable", context.packageName
            )
        }
        return id
    }
}
