package dev.aurakai.auraframefx.domains.aura.screens

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dev.aurakai.auraframefx.iconify.IconPicker
import dev.aurakai.auraframefx.iconify.IconPickerViewModel

/**
 * 🎨 ICONIFY PICKER SCREEN WRAPPER
 * Wraps the full-featured IconPicker component
 * Integrates with Dr. Disagree's Iconify root app
 */
@Composable
fun IconifyPickerScreen(
    viewModel: IconPickerViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {}
) {
    IconPicker(
        iconifyService = viewModel.iconifyService,
        currentIcon = null,
        onIconSelected = { iconId ->
            // TODO: Handle icon selection
            // component.icon = iconId
        },
        onDismiss = onNavigateBack
    )
}
