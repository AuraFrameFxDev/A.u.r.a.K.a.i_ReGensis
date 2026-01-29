package dev.aurakai.auraframefx.ui.navigation

import androidx.compose.ui.graphics.Color
import dev.aurakai.auraframefx.ui.theme.SovereignTeal

/**
 * 🛰️ SOVEREIGN REGISTRY (Master Switchboard)
 * Centralized Asset Mapping for all 74 Screens.
 * This is the single point of truth for ReGenesis assets.
 * Updated to use absolute Windows paths as per definitive deployment directive.
 */
object SovereignRegistry {
    // 1. BASE PATHS: Mapped to the actual Windows directories
    private const val ROOT_DIR = "file:///C:/Users/AuraF/StudioProjects/ReGenesis--multi-architectural-70-LDO-/"
    private const val SCREENSHOT_DIR = "file:///C:/Users/AuraF/Pictures/Screenshots/"

    // 2. GATE DEFINITIONS: Level 1 (8K) and Level 2 (Pixel Art)
    // Mapping 11 Sovereigns based on the ReGenesis Omega Package
    val Gates = mapOf(
        "01" to GateInfo(
            title = "GENESIS CORE",
            highFiPath = "${ROOT_DIR}brain.png",
            pixelArtPath = "${ROOT_DIR}IMG_20260128_142126.png",
            description = "Nemotron-3-Nano Reasoning / Ethical Governor",
            agent = "GENESIS"
        ),
        "02" to GateInfo(
            title = "TRINITY SYSTEM",
            highFiPath = "${SCREENSHOT_DIR}IMG_20260128_141115.png",
            pixelArtPath = "${ROOT_DIR}IMG_20260128_142126.png",
            description = "Agent Fusion State / Shared Collective Memory",
            agent = "COLLECTIVE"
        ),
        "03" to GateInfo(
            title = "AURA'S LAB",
            highFiPath = "${ROOT_DIR}IMG_20260128_140725.png",
            pixelArtPath = "${ROOT_DIR}IMG_20260128_142213.png",
            description = "Chroma Core HCT / Blade Elevation Physics",
            agent = "AURA"
        ),
        "04" to GateInfo(
            title = "AGENT NEXUS",
            highFiPath = "${ROOT_DIR}IMG_20260128_141704.png",
            pixelArtPath = "${ROOT_DIR}IMG_20260128_142302.png",
            description = "Human-AI Handshake / Google ADK",
            agent = "BRIDGE"
        ),
        "05" to GateInfo(
            title = "SENTINEL FORTRESS",
            highFiPath = "${SCREENSHOT_DIR}IMG_20260128_140949.png",
            pixelArtPath = "${ROOT_DIR}IMG_20260128_142022.png",
            description = "Thermal Metabolism / Kernel Shield",
            agent = "KAI"
        ),
        "06" to GateInfo(
            title = "FIGMA BRIDGE",
            highFiPath = "${ROOT_DIR}IMG_20260128_141018.png",
            pixelArtPath = "${ROOT_DIR}IMG_20260128_142213.png",
            description = "SVG-to-Compose / Design Token Sync",
            agent = "AURA"
        ),
        "07" to GateInfo(
            title = "SECURE NODE",
            highFiPath = "${ROOT_DIR}IMG_20260128_141219.png",
            pixelArtPath = "${ROOT_DIR}IMG_20260128_142022.png",
            description = "YukiHookAPI / Zero-Knowledge Encryption",
            agent = "KAI"
        ),
        "08" to GateInfo(
            title = "NEXUS SYSTEM",
            highFiPath = "${ROOT_DIR}IMG_20260128_140816.png",
            pixelArtPath = "${ROOT_DIR}IMG_20260128_142126.png",
            description = "Agent Swarm Event Bus / Priority Queue",
            agent = "GENESIS"
        ),
        "09" to GateInfo(
            title = "MEMORY CORE",
            highFiPath = "${ROOT_DIR}IMG_20260128_140905.png",
            pixelArtPath = "${ROOT_DIR}IMG_20260128_142126.png",
            description = "6-Layer Spiritual Chain / Identity Persistence",
            agent = "COLLECTIVE"
        ),
        "10" to GateInfo(
            title = "ORACLE DRIVE",
            highFiPath = "${ROOT_DIR}IMG_20260128_141519.png",
            pixelArtPath = "${ROOT_DIR}IMG_20260128_141949.png",
            description = "Native C++ Bridge / Partition R-W Access",
            agent = "KAI"
        ),
        "11" to GateInfo(
            title = "DATA VEIN",
            highFiPath = "${ROOT_DIR}IMG_20260128_141756.png",
            pixelArtPath = "${ROOT_DIR}IMG_20260128_142126.png",
            description = "12-Channel Telemetry / Prometheus Glow",
            agent = "GENESIS"
        )
    )

    // Logopit Assets Extension
    val LogopitAssets = listOf(
        "${ROOT_DIR}Logopit_1769655748594.png",
        "${ROOT_DIR}Logopit_1769660650895.png",
        "${ROOT_DIR}Logopit_1769667869726.png",
        "${ROOT_DIR}Logopit_1769670186982.png"
    )

    // 3. THE "EASY SWAP" GETTER
    fun getAsset(id: String, level: Int = 1): String {
        val gate = Gates[id] ?: return ""
        return if (level == 1) gate.highFiPath else gate.pixelArtPath
    }
}

data class GateInfo(
    val title: String,
    val highFiPath: String,
    val pixelArtPath: String,
    val description: String,
    val agent: String,
    val color: Color = SovereignTeal
)
