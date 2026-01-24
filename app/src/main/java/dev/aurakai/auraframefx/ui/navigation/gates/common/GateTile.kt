package dev.aurakai.auraframefx.ui.navigation.gates.common

import androidx.compose.ui.graphics.Color

data class GateTile(
    val title: String,
    val subtitle: String,
    val route: String,
    val imageRes: Int?,
    val glowColor: Color
)
