package dev.aurakai.auraframefx.ui.gates

import androidx.compose.ui.graphics.Color
import dev.aurakai.auraframefx.R

/**
 * 🗺️ GATE DESTINATION DATA
 * Represents a major domain within the ReGenesis platform.
 */
data class GateDestination(
    val title: String,
    val description: String,
    val route: String,
    val color: Color,
    val cardImageRes: Int
) {
    // Alias for compatibility
    val primaryColor: Color get() = color

    companion object {
        val KAI_FORTRESS = GateDestination(
            title = "Kai Fortress",
            description = "Sentinel security and system shielding protocols. Protect the core.",
            route = "kai_gate",
            color = Color(0xFF39FF14), // Electric Lime
            cardImageRes = R.drawable.gate_sentinel_fortress
        )
        
        val AURA_CANVAS = GateDestination(
            title = "Aura Canvas",
            description = "Vibrant creativity and design synthesis. Forge the interface.",
            route = "aura_gate",
            color = Color(0xFFFF1493), // Deep Pink
            cardImageRes = R.drawable.gate_uiux_studio
        )
        
        val GENESIS_ORACLE = GateDestination(
            title = "Genesis Oracle",
            description = "High-level AI orchestration and multi-agent coordination.",
            route = "genesis_gate",
            color = Color(0xFF00E5FF), // Electric Cyan
            cardImageRes = R.drawable.gate_oracle_drive
        )

        val AGENT_HUB = GateDestination(
            title = "Agent Nexus",
            description = "The social core of the collective. Manage and sync your AI family.",
            route = "agent_nexus",
            color = Color(0xFF7B2FFF), // Deep Purple
            cardImageRes = R.drawable.gate_agenthub_premium
        )

        val CODE_ASSIST = GateDestination(
            title = "Code Forge",
            description = "Real-time architectural assist and build system optimization.",
            route = "code_assist",
            color = Color(0xFFFFA500), // Orange
            cardImageRes = R.drawable.gate_codeassist_final
        )

        val SPHERE_GRID = GateDestination(
            title = "Sphere Grid",
            description = "Visualized agent progression and skill tree evolution.",
            route = "sphere_grid",
            color = Color(0xFFFF1493), // Deep Pink
            cardImageRes = R.drawable.gate_spheregrid_final
        )

        val APP_BUILDER = GateDestination(
            title = "App Architect",
            description = "Generative application building via natural language intent.",
            route = "app_builder",
            color = Color(0xFF32CD32), // Lime Green
            cardImageRes = R.drawable.gate_appbuilder_final
        )

        val TERMINAL = GateDestination(
            title = "Sentient Shell",
            description = "Direct access to the Genesis kernel and system-level overrides.",
            route = "sentient_shell",
            color = Color(0xFF00FF00), // Pure Green
            cardImageRes = R.drawable.gate_terminal_final
        )

        val DEFAULT_LIST = listOf(
            GENESIS_ORACLE,
            KAI_FORTRESS,
            AURA_CANVAS,
            AGENT_HUB,
            CODE_ASSIST,
            SPHERE_GRID,
            APP_BUILDER,
            TERMINAL
        )
    }
}
