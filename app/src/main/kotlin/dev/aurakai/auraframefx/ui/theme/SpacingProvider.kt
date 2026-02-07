
package dev.aurakai.auraframefx.ui.theme
import androidx.compose.ui.platform.LocalContext
import dev.aurakai.auraframefx.domains.aura.lab.CustomizationPreferences

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.aurakai.auraframefx.domains.aura.lab.SpacingConfig

/**
 * CompositionLocal for providing [SpacingConfig] throughout the UI hierarchy.
 * This allows UI components to react to changes in spacing dynamically.
 */
val LocalSpacing = staticCompositionLocalOf { SpacingConfig() }
@Composable
fun SpacingProvider(
    spacing: SpacingConfig = CustomizationPreferences.getSpacingConfig(LocalContext.current),
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalSpacing provides spacing) {
        content()
    }
}




