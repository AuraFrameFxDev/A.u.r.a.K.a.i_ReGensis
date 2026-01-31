package dev.aurakai.auraframefx.ui.navigation

import android.content.Context
import androidx.compose.ui.graphics.Color
import dev.aurakai.auraframefx.config.GateAssetConfig
import dev.aurakai.auraframefx.navigation.NavDestination

/**
 * 🛰️ THE SOVEREIGN REGISTRY (5 AGENT DOMAINS)
 *
 * LEVEL 1: ExodusHUD - The 5 Sovereign Gates
 * Each gate represents an Agent's domain of control.
 */
object SovereignRegistry {

    val Gates = mapOf(
        "01" to GateInfo(
            id = "01",
            title = "AURA'S STUDIO",
            subtitle = "UXUI DESIGN & THEMING",
            agentName = "Aura",
            description = "Theming, colors, icons, and visual design",
            assetProvider = { GateAssetConfig.MainGates.UXUI_DESIGN_STUDIO.current() },
            fallbackDrawable = "gate_uiux_studio",
            hubRoute = NavDestination.AuraThemingHub.route,
            color = Color(0xFFFF00FF),
            highFiDrawable = "aura_high.png",
            pixelArtDrawable = "aura_pixel.png"
        ),
        "02" to GateInfo(
            id = "02",
            title = "SENTINEL FORTRESS",
            subtitle = "SECURITY & SYSTEM INTEGRITY",
            agentName = "Kai",
            description = "Bootloader, ROM tools, LSPosed, security",
            assetProvider = { GateAssetConfig.MainGates.SENTINELS_FORTRESS.current() },
            fallbackDrawable = "gate_sentinelsfortress_final",
            hubRoute = NavDestination.RomToolsHub.route,
            color = Color(0xFFFF3366),
            highFiDrawable = "kai_high.png",
            pixelArtDrawable = "kai_pixel.png"
        ),
        "03" to GateInfo(
            id = "03",
            title = "ORACLE DRIVE",
            subtitle = "GENESIS AI ORCHESTRATION",
            agentName = "Genesis",
            description = "AI coordination, code assist, neural networks",
            assetProvider = { GateAssetConfig.MainGates.ORACLE_DRIVE.current() },
            fallbackDrawable = "gate_oracledrive_final",
            hubRoute = NavDestination.OracleDriveHub.route,
            color = Color(0xFF00FF85),
            highFiDrawable = "genesis_high.png",
            pixelArtDrawable = "genesis_pixel.png"
        ),
        "04" to GateInfo(
            id = "04",
            title = "AGENT NEXUS",
            subtitle = "MULTI-AGENT CONVERGENCE",
            agentName = "Collective",
            description = "Agent monitoring, constellations, fusion",
            assetProvider = { GateAssetConfig.MainGates.AGENT_NEXUS.current() },
            fallbackDrawable = "gate_agenthub_final",
            hubRoute = NavDestination.AgentNexusHub.route,
            color = Color(0xFF7B2FFF),
            highFiDrawable = "nexus_high.png",
            pixelArtDrawable = "nexus_pixel.png"
        ),
        "05" to GateInfo(
            id = "05",
            title = "HELP SERVICES",
            subtitle = "DIRECT LINK & SUPPORT",
            agentName = "Support",
            description = "FAQ, tutorials, documentation, live support",
            assetProvider = { GateAssetConfig.MainGates.HELP_SERVICES.current() },
            fallbackDrawable = "gate_helpdesk_final",
            hubRoute = NavDestination.HelpDesk.route,
            color = Color(0xFF00E5FF),
            highFiDrawable = "help_high.png",
            pixelArtDrawable = "help_pixel.png"
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
    val assetProvider: () -> String,
    val fallbackDrawable: String,
    val hubRoute: String,
    val color: Color,
    val highFiDrawable: String = "",
    val pixelArtDrawable: String = ""
) {
    fun getDrawableId(context: Context): Int {
        val name = assetProvider()
        var id = context.resources.getIdentifier(
            name, "drawable", context.packageName
        )
        if (id == 0) {
            id = context.resources.getIdentifier(
                fallbackDrawable, "drawable", context.packageName
            )
        }
        return id
    }
}
