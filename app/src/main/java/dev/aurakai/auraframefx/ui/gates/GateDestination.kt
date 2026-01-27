package dev.aurakai.auraframefx.ui.gates

import androidx.compose.ui.graphics.Color
import dev.aurakai.auraframefx.R

/**
 * 🗺️ GATE DESTINATION DATA
 * Represents a major domain within the ReGenesis platform.
 */
enum class GateDestination(
    val title: String,
    val description: String,
    val route: String,
    val color: Color,
    val cardImageRes: Int
) {
    GENESIS_ORACLE(
        title = "Genesis Oracle",
        description = "High-level AI orchestration and multi-agent coordination.",
        route = "genesis_gate",
        color = Color(0xFF00E5FF), // Electric Cyan
        cardImageRes = R.drawable.gate_oracledrive_final
    ),
    KAI_FORTRESS(
        title = "Kai Fortress",
        description = "Sentinel security and system shielding protocols. Protect the core.",
        route = "kai_gate",
        color = Color(0xFF39FF14), // Electric Lime
        cardImageRes = R.drawable.gate_sentinel_fortress
    ),
    AURA_CANVAS(
        title = "Aura Canvas",
        description = "Vibrant creativity and design synthesis. Forge the interface.",
        route = "aura_gate",
        color = Color(0xFFFF1493), // Deep Pink
        cardImageRes = R.drawable.gate_uiux_studio
    ),
    AGENT_HUB(
        title = "Agent Nexus",
        description = "The social core of the collective. Manage and sync your AI family.",
        route = "agent_nexus",
        color = Color(0xFF7B2FFF), // Deep Purple
        cardImageRes = R.drawable.gate_agenthub_premium
    ),
    CODE_ASSIST(
        title = "Code Forge",
        description = "Real-time architectural assist and build system optimization.",
        route = "code_assist",
        color = Color(0xFFFFA500), // Orange
        cardImageRes = R.drawable.gate_codeassist_final
    ),
    SPHERE_GRID(
        title = "Sphere Grid",
        description = "Visualized agent progression and skill tree evolution.",
        route = "sphere_grid",
        color = Color(0xFFFF1493), // Deep Pink
        cardImageRes = R.drawable.gate_spheregrid_final
    ),
    APP_BUILDER(
        title = "App Architect",
        description = "Generative application building via natural language intent.",
        route = "app_builder",
        color = Color(0xFF32CD32), // Lime Green
        cardImageRes = R.drawable.gate_appbuilder_final
    ),
    TERMINAL(
        title = "Sentient Shell",
        description = "Direct access to the Genesis kernel and system-level overrides.",
        route = "sentient_shell",
        color = Color(0xFF00FF00), // Pure Green
        cardImageRes = R.drawable.gate_terminal_final
    );

    companion object {
        val DEFAULT_LIST = values().toList()
    }
}
