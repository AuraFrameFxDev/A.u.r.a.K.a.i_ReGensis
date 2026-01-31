package dev.aurakai.auraframefx.ui.navigation

import androidx.compose.ui.graphics.Color
import dev.aurakai.auraframefx.R
import dev.aurakai.auraframefx.models.AgentType
import dev.aurakai.auraframefx.navigation.NavDestination
import dev.aurakai.auraframefx.ui.theme.AuraNeonCyan
import dev.aurakai.auraframefx.ui.theme.GenesisNeonPink
import dev.aurakai.auraframefx.ui.theme.KaiNeonGreen
import dev.aurakai.auraframefx.ui.theme.NeonPurple

/**
 * 🛰️ GATE CONFIGURATION
 *
 * The 5 Sovereign Gates - aligned with SovereignRegistry
 * This file provides drawable resource mapping for the gates.
 */
enum class SovereignGate(
    val title: String,
    val description: String,
    val route: String,
    val color: Color,
    val agentType: AgentType,
    val imageRes: Int
) {
    AURA_STUDIO(
        "AURA'S STUDIO",
        "UX/UI Design Domain - Creative Synthesis",
        NavDestination.AuraThemingHub.route,
        AuraNeonCyan,
        AgentType.AURA,
        R.drawable.gate_uiux_studio
    ),
    SENTINEL_FORTRESS(
        "SENTINEL'S FORTRESS",
        "Security & System Domain - Hardware Protection",
        NavDestination.RomToolsHub.route,
        KaiNeonGreen,
        AgentType.KAI,
        R.drawable.gate_sentinelsfortress_final
    ),
    ORACLE_DRIVE(
        "ORACLE DRIVE",
        "AI Orchestration Domain - Genesis Core",
        NavDestination.OracleDriveHub.route,
        GenesisNeonPink,
        AgentType.GENESIS,
        R.drawable.gate_oracledrive_final
    ),
    AGENT_NEXUS(
        "AGENT NEXUS",
        "Multi-Agent Coordination Hub",
        NavDestination.AgentNexusHub.route,
        NeonPurple,
        AgentType.GENESIS,
        R.drawable.gate_agenthub_final
    ),
    HELP_SERVICES(
        "HELP SERVICES",
        "Support & Documentation Center",
        NavDestination.HelpDesk.route,
        Color(0xFF4CAF50),
        AgentType.GENESIS,
        R.drawable.gate_helpdesk_final
    );

    companion object {
        fun getAllGates(): Array<SovereignGate> = entries.toTypedArray()
        fun getCount(): Int = entries.size
    }
}

/**
 * Level 2 Portal cards (used in domain hubs)
 */
data class GateModel(
    val id: String,
    val title: String,
    val imageRes: Int,
    val color: Color,
    val route: String
)
