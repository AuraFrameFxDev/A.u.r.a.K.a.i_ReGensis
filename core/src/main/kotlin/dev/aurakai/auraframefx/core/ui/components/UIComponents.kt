package dev.aurakai.auraframefx.core.ui.components

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class AppModule(
    val id: String,
    val name: String,
    val description: String,
    val icon: ImageVector,
    val iconColor: Color,
    val cardStyle: GlassCardStyles,
    val enabled: Boolean = true
)

data class GlassCardStyles(
    val borderGradient: List<Color> = emptyList(),
    val backgroundAlpha: Float = 0.5f
) {
    companion object {
        val Default = GlassCardStyles()
        val Aura = GlassCardStyles(borderGradient = listOf(Color.Magenta, Color.Cyan))
        val Kai = GlassCardStyles(borderGradient = listOf(Color.Blue, Color.Gray))
    }
}

data class SubGateCard(
    val id: String,
    val title: String,
    val subtitle: String,
    val styleADrawable: String,
    val styleBDrawable: String,
    val fallbackDrawable: String?,
    val route: String,
    val accentColor: Color
)
