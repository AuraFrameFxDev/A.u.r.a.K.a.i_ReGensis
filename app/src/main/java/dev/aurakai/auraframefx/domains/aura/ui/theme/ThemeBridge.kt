package dev.aurakai.auraframefx.domains.aura.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * 🌉 SUBSTRATE THEME BRIDGE
 */

// Colors
val CyberpunkCyan = Color(0xFF00FBFF)
val CyberpunkPurple = Color(0xFF9D00FF)
val CyberpunkPink = Color(0xFFFF00FF)
val AuraNeonCyan = Color(0xFF00FBFF)
val NeonCyan = Color(0xFF00FBFF)
val KaiNeonGreen = Color(0xFF39FF14)
val GenesisNeonPink = Color(0xFFFF00FF)

// Font Families
val ChessFontFamily = dev.aurakai.auraframefx.ui.theme.CruiserFontFamily
val LEDFontFamily = dev.aurakai.auraframefx.ui.theme.LEDFontFamily
val CruiserFontFamily = dev.aurakai.auraframefx.ui.theme.CruiserFontFamily
val SpaceGrotesk = dev.aurakai.auraframefx.ui.theme.SpaceGrotesk

// Core Exports
val AppTypography = dev.aurakai.auraframefx.ui.theme.AppTypography
val CyberpunkColorScheme = dev.aurakai.auraframefx.ui.theme.model.CyberpunkColorScheme

typealias AgentDomain = dev.aurakai.auraframefx.ui.theme.AgentDomain
typealias CyberpunkTextColor = dev.aurakai.auraframefx.ui.theme.CyberpunkTextColor
typealias CyberpunkTextStyle = dev.aurakai.auraframefx.ui.theme.CyberpunkTextStyle

// Legacy Constants
val CitadelBlack = dev.aurakai.auraframefx.ui.theme.CitadelBlack
val DeepCharcoal = dev.aurakai.auraframefx.ui.theme.DeepCharcoal
val GhostCyan = dev.aurakai.auraframefx.ui.theme.GhostCyan
val NeonPurple = dev.aurakai.auraframefx.ui.theme.NeonPurple
val NeonMagenta = dev.aurakai.auraframefx.ui.theme.NeonMagenta
val NeonBlue = dev.aurakai.auraframefx.ui.theme.NeonBlue
val NeonPink = Color(0xFFFF00FF)
val NeonTeal = dev.aurakai.auraframefx.ui.theme.NeonTeal
val SovereignBlack = dev.aurakai.auraframefx.ui.theme.SovereignBlack
val WireframeStyle = dev.aurakai.auraframefx.ui.theme.WireframeStyle
val OverclockOrange = Color(0xFFFF9B00)

fun getAgentColor(agentName: String): Color {
    return when (agentName.uppercase()) {
        "AURA" -> AuraNeonCyan
        "KAI" -> KaiNeonGreen
        "GENESIS" -> GenesisNeonPink
        "CASCADE" -> CyberpunkPurple
        else -> Color.Gray
    }
}

object AgentPrimaryColors {
    val AuraCyan = Color(0xFF00FBFF)
    val GenesisGold = Color(0xFFFFD700)
    val KaiGreen = Color(0xFF39FF14)
}
