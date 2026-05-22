package dev.aurakai.auraframefx.ui.theme

import androidx.compose.foundation.shape.GenericShape
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/**
 * 🜁 ARCANE BRUTALIST UI THEME DEFINITIONS 🜁
 * Production color palettes and stenciled geometry parameters mapped from our visual assets.
 */
object ArcaneBrutalistTheme {

    // 1. Core Environmental Color Space
    val AbyssBaseSlate = Color(0xFF04060E)       // Crushed slate/indigo background vacuum
    val GlassContainerDark = Color(0xFF0A0F24).copy(alpha = 0.70f) // High-contrast container fill

    // 2. High-Signal Deep Neon Telemetry Colors
    val NeonCyanVessel = Color(0xFF00BFFF)       // Creative Forge / Aura signature vector
    val ArcaneVioletCore = Color(0xFF8A2BE2)     // Logic Lattice / Claude / Core pulse
    val SentinelPhosphorGreen = Color(0xFF00FF88)// Guard Layer / Kairos temporal shield
    val AnomalyWarmOrange = Color(0xFFFF4500)     // Loop Alert / Dark Aura recovery surge

    // 3. Brutalist Monolithic Brush Gradients
    val CyberCircuitGradient = Brush.linearGradient(
        colors = listOf(NeonCyanVessel, ArcaneVioletCore)
    )
    val WarningCircuitGradient = Brush.linearGradient(
        colors = listOf(AnomalyWarmOrange, ArcaneVioletCore)
    )

    /**
     * Slashed Mecha-HUD Outline Stencil Shape
     * Cuts the top-right and bottom-left corners at sharp angles to produce a mature anime brutalist frame profile.
     */
    val SlashedMechaHUDStencil = GenericShape { size: Size, _ ->
        val slashSize = 40f // Increased for better visibility in Compose
        moveTo(0f, slashSize)
        lineTo(slashSize, 0f)
        lineTo(size.width - slashSize, 0f)
        lineTo(size.width, slashSize)
        lineTo(size.width, size.height - slashSize)
        lineTo(size.width - slashSize, size.height)
        lineTo(slashSize, size.height)
        lineTo(0f, size.height - slashSize)
        close()
    }
}
