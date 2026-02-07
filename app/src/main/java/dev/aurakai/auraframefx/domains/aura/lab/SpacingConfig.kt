package dev.aurakai.auraframefx.domains.aura.lab

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

object Presets {
    val compact = SpacingConfig(
        defaultMarginHorizontal = 8.dp, defaultMarginVertical = 4.dp,
        defaultPaddingHorizontal = 8.dp, defaultPaddingVertical = 4.dp,
        gateMarginHorizontal = 12.dp, gateMarginVertical = 6.dp,
        gatePaddingHorizontal = 8.dp, gatePaddingVertical = 4.dp,
        cardMarginHorizontal = 4.dp, cardMarginVertical = 4.dp,
        cardPaddingHorizontal = 6.dp, cardPaddingVertical = 6.dp,
        headerMarginHorizontal = 8.dp, headerMarginVertical = 4.dp,
        headerPaddingHorizontal = 8.dp, headerPaddingVertical = 8.dp,
        quickSettingsTileMarginHorizontal = 2.dp, quickSettingsTileMarginVertical = 2.dp,
        quickSettingsTilePaddingHorizontal = 4.dp, quickSettingsTilePaddingVertical = 4.dp,
        statusBarElementMarginHorizontal = 4.dp, statusBarElementMarginVertical = 2.dp,
        statusBarElementPaddingHorizontal = 2.dp, statusBarElementPaddingVertical = 1.dp
    )

    val default = SpacingConfig(
        defaultMarginHorizontal = 16.dp, defaultMarginVertical = 16.dp,
        defaultPaddingHorizontal = 16.dp, defaultPaddingVertical = 16.dp,
        gateMarginHorizontal = 24.dp, gateMarginVertical = 12.dp,
        gatePaddingHorizontal = 16.dp, gatePaddingVertical = 8.dp,
        cardMarginHorizontal = 8.dp, cardMarginVertical = 8.dp,
        cardPaddingHorizontal = 12.dp, cardPaddingVertical = 12.dp,
        headerMarginHorizontal = 16.dp, headerMarginVertical = 8.dp,
        headerPaddingHorizontal = 16.dp, headerPaddingVertical = 16.dp,
        quickSettingsTileMarginHorizontal = 4.dp, quickSettingsTileMarginVertical = 4.dp,
        quickSettingsTilePaddingHorizontal = 8.dp, quickSettingsTilePaddingVertical = 8.dp,
        statusBarElementMarginHorizontal = 8.dp, statusBarElementMarginVertical = 4.dp,
        statusBarElementPaddingHorizontal = 4.dp, statusBarElementPaddingVertical = 2.dp
    )

    val comfortable = SpacingConfig(
        defaultMarginHorizontal = 24.dp, defaultMarginVertical = 12.dp,
        defaultPaddingHorizontal = 20.dp, defaultPaddingVertical = 10.dp,
        gateMarginHorizontal = 32.dp, gateMarginVertical = 16.dp,
        gatePaddingHorizontal = 20.dp, gatePaddingVertical = 10.dp,
        cardMarginHorizontal = 12.dp, cardMarginVertical = 10.dp,
        cardPaddingHorizontal = 16.dp, cardPaddingVertical = 14.dp,
        headerMarginHorizontal = 20.dp, headerMarginVertical = 10.dp,
        headerPaddingHorizontal = 20.dp, headerPaddingVertical = 20.dp,
        quickSettingsTileMarginHorizontal = 6.dp, quickSettingsTileMarginVertical = 6.dp,
        quickSettingsTilePaddingHorizontal = 10.dp, quickSettingsTilePaddingVertical = 10.dp,
        statusBarElementMarginHorizontal = 10.dp, statusBarElementMarginVertical = 6.dp,
        statusBarElementPaddingHorizontal = 6.dp, statusBarElementPaddingVertical = 3.dp
    )

    val spacious = SpacingConfig(
        defaultMarginHorizontal = 32.dp, defaultMarginVertical = 16.dp,
        defaultPaddingHorizontal = 24.dp, defaultPaddingVertical = 12.dp,
        gateMarginHorizontal = 40.dp, gateMarginVertical = 20.dp,
        gatePaddingHorizontal = 24.dp, gatePaddingVertical = 12.dp,
        cardMarginHorizontal = 16.dp, cardMarginVertical = 12.dp,
        cardPaddingHorizontal = 20.dp, cardPaddingVertical = 16.dp,
        headerMarginHorizontal = 24.dp, headerMarginVertical = 12.dp,
        headerPaddingHorizontal = 24.dp, headerPaddingVertical = 24.dp,
        quickSettingsTileMarginHorizontal = 8.dp, quickSettingsTileMarginVertical = 8.dp,
        quickSettingsTilePaddingHorizontal = 12.dp, quickSettingsTilePaddingVertical = 12.dp,
        statusBarElementMarginHorizontal = 12.dp, statusBarElementMarginVertical = 8.dp,
        statusBarElementPaddingHorizontal = 8.dp, statusBarElementPaddingVertical = 4.dp
    )
}
