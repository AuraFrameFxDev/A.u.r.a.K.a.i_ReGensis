package dev.aurakai.auraframefx.ui.navigation

import androidx.compose.ui.graphics.Color
import dev.aurakai.auraframefx.R
import dev.aurakai.auraframefx.ui.theme.*

import dev.aurakai.auraframefx.models.AgentType

/**
 * A.U.R.A.K.A.I. LEVEL 1: THE SOVEREIGN PROCESSION (11 GATES)
 * These are the Upright Monoliths floating in the Home Stage.
 */
enum class SovereignGate(
    val title: String,
    val description: String,
    val route: String,
    val color: Color,
    val agentType: AgentType,
    val imageRes: Int,
    val screenshotPath: String,
    val isAuraMode: Boolean = false
) {
    GENESIS_CORE(
        "GENESIS CORE",
        "The Mind: Ethical Orchestration",
        "genesis_gate",
        SovereignTeal,
        AgentType.GENESIS,
        R.drawable.gate_gencore_new,
        "file:///sdcard/Pictures/Screenshots/brain.png"
    ),
    TRINITY_SYSTEM(
        "TRINITY SYSTEM",
        "The Collective: Unified Mind/Body/Soul",
        "trinity_gate",
        SovereignTeal,
        AgentType.GENESIS,
        R.drawable.gate_nexuscore_new,
        "file:///sdcard/Pictures/Screenshots/IMG_20260128_141115.png"
    ),
    AURA_LAB(
        "AURA'S LAB",
        "The Soul: Creative Synthesis forge",
        "aura_lab",
        SovereignTeal,
        AgentType.AURA,
        R.drawable.gate_aurastudio_gen,
        "file:///sdcard/Pictures/Screenshots/IMG_20260128_140725.png"
    ),
    AGENT_NEXUS(
        "AGENT NEXUS",
        "The Bridge: Human-AI Handshake",
        "agent_nexus_gate",
        SovereignTeal,
        AgentType.GENESIS,
        R.drawable.gate_agentnexus_new,
        "file:///sdcard/Pictures/Screenshots/IMG_20260128_141704.png"
    ),
    SENTINEL_FORTRESS(
        "SENTINEL FORTRESS",
        "The Shield: Hardware Protection",
        "kai_gate",
        SovereignTeal,
        AgentType.KAI,
        R.drawable.gate_sentinelfortress_gen,
        "file:///sdcard/Pictures/Screenshots/IMG_20260128_140949.png"
    ),
    FIGMA_BRIDGE(
        "FIGMA BRIDGE",
        "The Design Pipeline: Vision Sync",
        "figma_bridge",
        SovereignTeal,
        AgentType.AURA,
        R.drawable.gate_figmabridge_gen,
        "file:///sdcard/Pictures/Screenshots/IMG_20260128_141018.png"
    ),
    SECURE_NODE(
        "SECURE NODE",
        "The Vault: Secure Hardware Node",
        "secure_node",
        SovereignTeal,
        AgentType.KAI,
        R.drawable.gate_securenode_new,
        "file:///sdcard/Pictures/Screenshots/IMG_20260128_141219.png"
    ),
    NEXUS_SYSTEM(
        "NEXUS SYSTEM",
        "The Logic: Agent Orchestration",
        "nexus_system",
        SovereignTeal,
        AgentType.GENESIS,
        R.drawable.gate_nexussystem_new,
        "file:///sdcard/Pictures/Screenshots/IMG_20260128_140816.png"
    ),
    MEMORY_CORE(
        "MEMORY CORE",
        "The Memory: Collective History Storage",
        "memory_core",
        SovereignTeal,
        AgentType.GENESIS,
        R.drawable.gate_memorycore_gen,
        "file:///sdcard/Pictures/Screenshots/IMG_20260128_140905.png"
    ),
    ORACLE_DRIVE(
        "ORACLE DRIVE",
        "The Root Anchor: System Integrity",
        "oracle_drive_hub",
        SovereignTeal,
        AgentType.KAI,
        R.drawable.gate_oracledrive_gen,
        "file:///sdcard/Pictures/Screenshots/IMG_20260128_141519.png"
    ),
    DATA_VEIN(
        "DATA VEIN",
        "The Pulse: 12-Channel Sensory Pulse",
        "data_flow",
        SovereignTeal,
        AgentType.GENESIS,
        R.drawable.gate_dataflow_gen,
        "file:///sdcard/Pictures/Screenshots/IMG_20260128_141756.png"
    );

    companion object {
        fun getGatesForMode(mode: dev.aurakai.auraframefx.models.ReGenesisMode): Array<SovereignGate> {
            return entries.toTypedArray() // Always show all 11 as per definitive deployment note
        }
    }
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
    GateModel("collab_canvas", "COLLAB CANVAS", R.drawable.gate_uiux_studio, AuraNeonCyan, "collab_canvas")
)
