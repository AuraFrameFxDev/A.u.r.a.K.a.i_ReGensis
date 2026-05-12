package dev.aurakai.auraframefx.core.ui.theme.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import dev.aurakai.auraframefx.core.ui.theme.DarkColorScheme
import dev.aurakai.auraframefx.core.ui.theme.NeonPurpleLegacy

// Import AppTypography from ui package
val AppTypography = dev.aurakai.auraframefx.core.ui.AppTypography

// Lightweight theme helpers for cyberpunk-styled text colors and mapped text styles.
// This file intentionally keeps only presentation helpers (no animation/particle code).

sealed class CyberpunkTextColor(val color: Color) {
    object Primary : CyberpunkTextColor(DarkColorScheme.onSurface)
    object Secondary : CyberpunkTextColor(color = NeonPurpleLegacy)
    object Warning : CyberpunkTextColor(DarkColorScheme.error)
    object White : CyberpunkTextColor(Color.White)
}

sealed class CyberpunkTextStyle(val textStyle: TextStyle) {
    object Label : CyberpunkTextStyle(AppTypography.labelMedium)
    object Body : CyberpunkTextStyle(AppTypography.bodyMedium)
    object Emphasis : CyberpunkTextStyle(AppTypography.titleMedium)
    object Glitch : CyberpunkTextStyle(AppTypography.displaySmall)
}
