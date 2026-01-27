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

        val DEFAULT_LIST = listOf(KAI_FORTRESS, AURA_CANVAS, GENESIS_ORACLE)
    }
}
