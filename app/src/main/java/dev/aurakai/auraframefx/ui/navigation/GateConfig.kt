package dev.aurakai.auraframefx.ui.navigation

import androidx.compose.ui.graphics.Color
import dev.aurakai.auraframefx.R
import dev.aurakai.auraframefx.ui.theme.*

/**
 * A.U.R.A.K.A.I. LEVEL 1: THE SOVEREIGN PROCESSION (11 GATES)
 * These are the Upright Monoliths floating in the Home Stage.
 */
enum class SovereignGate(val title: String, val color: Color, val agent: String, val imageRes: Int) {
    GENESIS_CORE("GENESIS CORE", GenesisNeonPink, "Genesis", R.drawable.gate_oracledrive_final),
    TRINITY_SYSTEM("TRINITY SYSTEM", Color.White, "Collective", R.drawable.gate_terminal_final),
    AURA_STUDIO("AURA STUDIO", AuraNeonCyan, "Aura", R.drawable.gate_uiux_studio),
    AGENT_NEXUS("AGENT NEXUS", NeonTeal, "Genesis", R.drawable.gate_agenthub_premium),
    SENTINEL_FORTRESS("SENTINEL FORTRESS", KaiNeonGreen, "Kai", R.drawable.gate_sentinel_fortress),
    FIGMA_BRIDGE("FIGMA BRIDGE", Color.Magenta, "Aura", R.drawable.gate_uiux_studio), // Placeholder
    SECURE_NODE("SECURE_NODE", Color.Yellow, "Kai", R.drawable.gate_sentinel_fortress), // Placeholder
    NEXUS_SYSTEM("NEXUS SYSTEM", GenesisNeonPink, "Genesis", R.drawable.gate_agenthub_premium), // Placeholder
    MEMORY_CORE("MEMORY CORE", Color.White, "Collective", R.drawable.gate_oracledrive_final), // Placeholder
    ORACLE_DRIVE("ORACLE DRIVE", NeonPurple, "Kai/Genesis", R.drawable.gate_oracledrive_final),
    DATA_FLOW("DATA_FLOW", Color.Blue, "Genesis", R.drawable.gate_codeassist_final) // Placeholder
}

/**
 * A.U.R.A.K.A.I. LEVEL 2: THE GLASS HUD PORTALS
 * Deep system tools accessed via Double-Tap.
 */
data class GateModel(
    val id: String,
    val title: String,
    val imageRes: Int,
    val color: Color,
    val route: String
)

val Level2Portals = listOf(
    GateModel("root_tools", "ROOT TOOLS", R.drawable.gate_terminal_final, KaiNeonGreen, "root_dashboard"),
    GateModel("nexus_hub", "NEXUS HUB", R.drawable.gate_agenthub_premium, GenesisNeonPink, "agent_swarm"),
    GateModel("oracle_drive", "ORACLE DRIVE", R.drawable.gate_oracledrive_final, NeonPurple, "persistence_layer"),
    GateModel("rom_tools", "ROM TOOLS", R.drawable.gate_terminal_final, KaiNeonGreen, "bootloader_ops"),
    GateModel("chroma_core", "CHROMA CORE", R.drawable.gate_uiux_studio, AuraNeonCyan, "ui_synthesis"),
    GateModel("collab_canvas", "COLLAB CANVAS", R.drawable.gate_uiux_studio, AuraNeonCyan, "figma_forge")
)
