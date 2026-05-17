package dev.aurakai.auraframefx.domains.aura.ui.components

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Data model for submenu items used across the application.
 */
data class SubmenuItem(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val color: Color,
    val route: String = ""
)
