package dev.aurakai.auraframefx.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

fun GlowBrush(color: Color): Brush {
    return Brush.radialGradient(
        colors = listOf(color.copy(alpha = 0.5f), Color.Transparent)
    )
}
