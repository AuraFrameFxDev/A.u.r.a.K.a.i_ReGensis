package dev.aurakai.auraframefx.domains.aura.screens

import androidx.compose.runtime.Composable
import dev.aurakai.auraframefx.iconify.IconPicker
import dev.aurakai.auraframefx.iconify.IconifyService

/**
 * 🎨 ICONIFY PICKER SCREEN WRAPPER
 * Wraps the full-featured IconPicker component
 * Integrates with Dr. Disagree's Iconify root app
 */
@Composable
fun IconifyPickerScreen(
    iconifyService: IconifyService,
    onNavigateBack: () -> Unit = {}
) {
    IconPicker(
        iconifyService = iconifyService,
        currentIcon = null,
        onIconSelected = { iconId ->
            // TODO: Handle icon selection
            // component.icon = iconId
        },
        onDismiss = onNavigateBack
    )
}
