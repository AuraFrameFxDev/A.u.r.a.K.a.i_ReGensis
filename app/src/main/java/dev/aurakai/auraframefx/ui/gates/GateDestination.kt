package dev.aurakai.auraframefx.ui.gates

import androidx.compose.ui.graphics.Color

/**
 * 🗺️ GATE DESTINATION DATA
 * Represents a major domain within the ReGenesis platform.
 */
data class GateDestination(
    val title: String,
    val description: String,
    val route: String,
    val primaryColor: Color
) {
    companion object {
        val KAI_FORTRESS = GateDestination(
            title = "Kai Fortress",
            description = "Sentinel security and system shielding protocols. Protect the core.",
            route = "kai_gate",
            primaryColor = Color(0xFF39FF14) // Electric Lime
        )
        
        val AURA_CANVAS = GateDestination(
            title = "Aura Canvas",
            description = "Vibrant creativity and design synthesis. Forge the interface.",
            route = "aura_gate",
            primaryColor = Color(0xFFFF1493) // Deep Pink
        )
        
        val GENESIS_ORACLE = GateDestination(
            title = "Genesis Oracle",
            description = "High-level AI orchestration and multi-agent coordination.",
            route = "genesis_gate",
            primaryColor = Color(0xFF00E5FF) // Electric Cyan
        )

        val DEFAULT_LIST = listOf(KAI_FORTRESS, AURA_CANVAS, GENESIS_ORACLE)
    }
}
