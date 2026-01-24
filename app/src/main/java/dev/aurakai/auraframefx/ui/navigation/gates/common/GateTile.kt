package dev.aurakai.auraframefx.ui.navigation.gates.common

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

/**
 * 🎴 GATE TILE DATA MODEL
 * Shared across all gate screens for consistency
 */
data class GateTile(
    val title: String,
    val subtitle: String,
    val route: String,
    @DrawableRes val imageRes: Int?,
    val glowColor: Color
)
