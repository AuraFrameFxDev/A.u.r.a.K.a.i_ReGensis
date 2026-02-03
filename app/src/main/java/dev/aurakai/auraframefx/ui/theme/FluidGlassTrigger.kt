package dev.aurakai.auraframefx.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.aurakai.auraframefx.customization.CustomizationViewModel

/**
 * 🔮 FLUID GLASSMORPHISM TRIGGER
 * 
 * Wires the "on/off" switch to all UI components.
 * When enabled, applies the "Spellbook" glass effect.
 */
@Composable
fun Modifier.fluidGlass(
    viewModel: CustomizationViewModel = viewModel(),
    baseColor: Color = Color.White.copy(alpha = 0.1f)
): Modifier {
    val state by viewModel.state.collectAsState()
    
    return if (state.glassEnabled) {
        // Apply the high-fidelity glass effect
        this.glassmorphic(
            glassColor = baseColor.copy(alpha = state.glassSurfaceAlpha),
            blurRadius = state.glassBlurRadiusDp
        )
    } else {
        // Standard background when disabled
        this.background(baseColor.copy(alpha = 0.2f))
    }
}
