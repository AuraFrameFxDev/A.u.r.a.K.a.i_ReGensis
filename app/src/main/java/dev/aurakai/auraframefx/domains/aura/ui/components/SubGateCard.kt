package dev.aurakai.auraframefx.domains.aura.ui.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

data class SubGateCard(
    val id: String = "",
    val title: String,
    val subtitle: String,
    val styleADrawable: String,      // Style A image name
    val styleBDrawable: String,      // Style B image name
    val route: String,
    val fallbackDrawable: String? = null,   // Legacy fallback
    val accentColor: Color = Color.Cyan,
    val parallaxOffset: Offset = Offset.Zero // Optional 3D shift for Whisk
)
