package dev.aurakai.auraframefx.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.aurakai.auraframefx.core.ui.theme.NeonCyan

/**
 * 📐 GLOW CORNER BOX — PURIFIED
 * Simplified for edge-to-edge manifestation.
 */
@Composable
fun GlowCornerBox(
    modifier: Modifier = Modifier,
    color: Color = NeonCyan,
    cornerLength: Dp = 20.dp,
    strokeWidth: Dp = 2.dp,
    glowIntensity: Float = 1.0f,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .border(1.dp, color.copy(alpha = 0.3f), RoundedCornerShape(4.dp))
    ) {
        Box(modifier = Modifier.padding(strokeWidth + 4.dp)) {
            content()
        }
    }
}
