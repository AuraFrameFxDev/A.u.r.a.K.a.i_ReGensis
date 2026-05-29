package dev.aurakai.auraframefx.core.ui.theme

import androidx.compose.foundation.shape.GenericShape
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/**
 * ARCANE BRUTALIST THEME — AuraKai Re:Genesis
 * Palette locked from canonical art reference (images 1, 8, 12).
 * Foundation: deep violet/indigo void. Accents: cyan + magenta + electric purple.
 */
object ArcaneBrutalistTheme {

    // ── Core Void Space (from image 1 background, image 8 city atmosphere) ──
    val AbyssBaseSlate = Color(0xFF050008)   // Master void — purple-black
    val AbyssVioletDepth = Color(0xFF0D0018)   // Surface void layer
    val GlassContainerDark = Color(0xFF0D0018).copy(alpha = 0.88f)
    val GlassPanelSurface = Color(0xFF150030).copy(alpha = 0.75f) // Image 4/5 card style

    // ── Primary Signal Colors ────────────────────────────────────────────────
    val NeonCyanVessel = Color(0xFF00FFD4)   // Aura teal / phoenix ring
    val CrystalCyanEdge = Color(0xFF00F5FF)   // Crystal edge glow (image 1)
    val NeonMagentaFlare = Color(0xFFFF00D4)   // Hot magenta (image 12 rings)
    val ElectricPurpleCore = Color(0xFF7B00FF)   // Electric purple structure
    val ArcaneVioletCore = Color(0xFF8A2BE2)   // Logic lattice / Claude core
    val SentinelPhosphorGreen = Color(0xFF00FF88)   // Guard layer / Kairos
    val AnomalyWarmOrange = Color(0xFFFF4500)   // Alert only — anomaly surge

    // ── Gradient Brushes (art-accurate) ─────────────────────────────────────
    val VoidDepthGradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF080012), Color(0xFF050008))
    )
    val CrystalShardGradient = Brush.linearGradient(
        colors = listOf(CrystalCyanEdge, ElectricPurpleCore)
    )
    val PhoenixRingGradient = Brush.sweepGradient(
        colors = listOf(CrystalCyanEdge, NeonMagentaFlare, ElectricPurpleCore, CrystalCyanEdge)
    )
    val CyberCircuitGradient = Brush.linearGradient(
        colors = listOf(NeonCyanVessel, ArcaneVioletCore)
    )
    val WarningCircuitGradient = Brush.linearGradient(
        colors = listOf(AnomalyWarmOrange, ArcaneVioletCore)
    )

    // ── Glassmorphic Card (image 4 / image 5 style) ──────────────────────────
    val AgentCardGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF1A0030).copy(alpha = 0.9f),
            Color(0xFF050008).copy(alpha = 0.95f)
        )
    )
    val AgentCardGlowCyan = Brush.radialGradient(
        colors = listOf(
            CrystalCyanEdge.copy(alpha = 0.3f),
            Color.Transparent
        )
    )
    val AgentCardGlowMagenta = Brush.radialGradient(
        colors = listOf(
            NeonMagentaFlare.copy(alpha = 0.3f),
            Color.Transparent
        )
    )

    // ── Slashed Mecha-HUD Stencil Shape ─────────────────────────────────────
    val SlashedMechaHUDStencil = GenericShape { size: Size, _ ->
        val slash = 36f
        moveTo(0f, slash)
        lineTo(slash, 0f)
        lineTo(size.width - slash, 0f)
        lineTo(size.width, slash)
        lineTo(size.width, size.height - slash)
        lineTo(size.width - slash, size.height)
        lineTo(slash, size.height)
        lineTo(0f, size.height - slash)
        close()
    }

    val MicroSlashStencil = GenericShape { size: Size, _ ->
        val slash = 14f
        moveTo(0f, slash)
        lineTo(slash, 0f)
        lineTo(size.width, 0f)
        lineTo(size.width, size.height - slash)
        lineTo(size.width - slash, size.height)
        lineTo(0f, size.height)
        close()
    }
}

data class ChromaCoreTheme(
    val primaryColor: Color,
    val diffusionColor: Color,
    val antiAliasing: Boolean = false
) {
    companion object {
        val DEFAULT = ChromaCoreTheme(
            primaryColor = Color(0xFF00FFD4),
            diffusionColor = Color(0xFF8A2BE2),
            antiAliasing = false
        )
    }
}
