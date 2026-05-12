package dev.aurakai.auraframefx.ui.theme

import androidx.compose.runtime.Composable
import dev.aurakai.auraframefx.core.ui.theme.AuraFrameFXTheme as DomainAuraFrameFXTheme

@Composable
fun AuraFrameFXTheme(
    darkTheme: Boolean = androidx.compose.foundation.isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    DomainAuraFrameFXTheme(
        darkTheme = darkTheme,
        dynamicColor = dynamicColor,
        content = content
    )
}
