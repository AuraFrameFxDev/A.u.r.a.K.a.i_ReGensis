package dev.aurakai.auraframefx.domains.aura.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.FormatPaint
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.SettingsSystemDaydream
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Wallpaper
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import dev.aurakai.auraframefx.ui.components.GridMenuItem
import dev.aurakai.auraframefx.ui.components.Level3GridMenu

/**
 * 🎨 CHROMACORE - LVL 3 GRID MENU
 *
 * Aura's Color Engine - Full separate menu with colorful abstract background
 *
 * Features:
 * - Monet/Material You color extraction
 * - Custom accent colors
 * - Wallpaper-based theming
 * - System color overrides
 *
 * Can EXPORT colors into UXUI Design Studio!
 */
@Composable
fun ChromaCoreColorsScreen(
    navController: NavController? = null,
    onNavigateBack: () -> Unit = { navController?.popBackStack() }
) {
    val menuItems = listOf(
        GridMenuItem(
            id = "monet_colors",
            title = "Monet Colors",
            subtitle = "Material You Extraction",
            icon = Icons.Default.Palette,
            route = "monet_colors",
            accentColor = Color(0xFFB026FF)
        ),
        GridMenuItem(
            id = "accent_picker",
            title = "Accent Picker",
            subtitle = "Custom Primary Color",
            icon = Icons.Default.ColorLens,
            route = "accent_picker",
            accentColor = Color(0xFF00E5FF)
        ),
        GridMenuItem(
            id = "wallpaper_extract",
            title = "Wallpaper Extract",
            subtitle = "Colors from Wallpaper",
            icon = Icons.Default.Wallpaper,
            route = "wallpaper_extract",
            accentColor = Color(0xFFFF00FF)
        ),
        GridMenuItem(
            id = "custom_palette",
            title = "Custom Palette",
            subtitle = "Create Color Schemes",
            icon = Icons.Default.FormatPaint,
            route = "custom_palette",
            accentColor = Color(0xFFFFD700)
        ),
        GridMenuItem(
            id = "system_override",
            title = "System Override",
            subtitle = "Force System Colors",
            icon = Icons.Default.SettingsSystemDaydream,
            route = "system_override",
            accentColor = Color(0xFF00FF85)
        ),
        GridMenuItem(
            id = "color_harmony",
            title = "Color Harmony",
            subtitle = "Complementary Schemes",
            icon = Icons.Default.AutoAwesome,
            route = "color_harmony",
            accentColor = Color(0xFFFF1493)
        ),
        GridMenuItem(
            id = "export_to_uxui",
            title = "Export to UXUI",
            subtitle = "Send to Design Studio",
            icon = Icons.Default.Share,
            route = "export_colors",
            accentColor = Color(0xFF00E5FF)
        ),
        GridMenuItem(
            id = "saved_palettes",
            title = "Saved Palettes",
            subtitle = "Your Color Collections",
            icon = Icons.Default.Bookmark,
            route = "saved_palettes",
            accentColor = Color(0xFFB026FF)
        )
    )

    Level3GridMenu(
        title = "CHROMACORE",
        subtitle = "AURA'S COLOR ENGINE",
        menuItems = menuItems,
        onItemClick = { item ->
            // Navigate to specific tool
            navController?.navigate(item.route)
        },
        onBackClick = onNavigateBack,
        // Colorful abstract background for ChromaCore
        backgroundDrawable = "bg_chromacore_abstract",
        fallbackGradient = listOf(
            Color(0xFF1A0A2E),
            Color(0xFF2D1B4E),
            Color(0xFF0A1A2E)
        ),
        accentColor = Color(0xFFB026FF)
    )
}
