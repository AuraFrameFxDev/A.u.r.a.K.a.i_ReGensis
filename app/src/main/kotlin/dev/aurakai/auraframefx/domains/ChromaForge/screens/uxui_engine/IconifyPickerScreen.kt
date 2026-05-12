package dev.aurakai.auraframefx.core.screens.uxui_engine

import androidx.compose.runtime.Composable
import dev.aurakai.auraframefx.core.chromacore.iconify.iconify.IconPicker
import dev.aurakai.auraframefx.core.chromacore.iconify.iconify.IconPickerViewModel

/**
 * ðŸŽ¨ ICONIFY PICKER SCREEN WRAPPER
 * Wraps the full-featured IconPicker component
 * Integrates with Dr. Disagree's Iconify root app
 */
@Composable
fun IconifyPickerScreen(
    viewModel: IconPickerViewModel = androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel(),
    onNavigateBack: () -> Unit = {}
) {
    IconPicker(
        viewModel = viewModel,
        onIconSelected = { iconId ->
            // TODO: Handle icon selection
            // component.icon = iconId
        },
        onDismiss = onNavigateBack
    )
}
