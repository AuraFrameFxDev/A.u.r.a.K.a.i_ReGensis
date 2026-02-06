
package dev.aurakai.auraframefx.aura.lab

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class SpacingConfig(
    // Global defaults or base spacing units (can be overridden by specific elements)
    val defaultMarginHorizontal: Dp = 16.dp,
    val defaultMarginVertical: Dp = 16.dp,
    val defaultPaddingHorizontal: Dp = 16.dp,
    val defaultPaddingVertical: Dp = 16.dp,

    // Gate-specific spacing
    val gateMarginHorizontal: Dp = 24.dp,
    val gateMarginVertical: Dp = 12.dp,
    val gatePaddingHorizontal: Dp = 16.dp,
    val gatePaddingVertical: Dp = 8.dp,

    // Card-specific spacing
    val cardMarginHorizontal: Dp = 8.dp,
    val cardMarginVertical: Dp = 8.dp,
    val cardPaddingHorizontal: Dp = 12.dp,
    val cardPaddingVertical: Dp = 12.dp,

    // Header-specific spacing
    val headerMarginHorizontal: Dp = 16.dp,
    val headerMarginVertical: Dp = 8.dp,
    val headerPaddingHorizontal: Dp = 16.dp,
    val headerPaddingVertical: Dp = 16.dp,

    // Quick Settings Tile-specific spacing
    val quickSettingsTileMarginHorizontal: Dp = 4.dp,
    val quickSettingsTileMarginVertical: Dp = 4.dp,
    val quickSettingsTilePaddingHorizontal: Dp = 8.dp,
    val quickSettingsTilePaddingVertical: Dp = 8.dp,

    // Status Bar elements spacing
    val statusBarElementMarginHorizontal: Dp = 8.dp,
    val statusBarElementMarginVertical: Dp = 4.dp,
    val statusBarElementPaddingHorizontal: Dp = 4.dp,
    val statusBarElementPaddingVertical: Dp = 2.dp
)
