package dev.aurakai.auraframefx.core.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import dev.aurakai.auraframefx.core.ui.theme.NeonCyan

/**
 * 🌊 DIFFUSION TEXT — The "Refining from Noise" Visualizer
 *
 * Mimics Google's DiffusionGemma non-linear text generation.
 * Text appears to condense from a cloud of "noise" characters through 
 * flicker, blur, and parallel refinement.
 */
@Composable
fun DiffusionText(
    text: String,
    progress: Float, // 0.0 (noisy) to 1.0 (clear)
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    fontSize: TextUnit = 14.sp,
    lineHeight: TextUnit = 20.sp,
    isFinalized: Boolean = false
) {
    val infiniteTransition = rememberInfiniteTransition(label = "noise_flicker")
    val flicker by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(150, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "flicker"
    )

    // Opacity based on progress and flicker for a "shimmering" effect
    val opacity = if (isFinalized) 1f else (progress * 0.7f + 0.3f) * (0.8f + flicker * 0.2f)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .graphicsLayer {
                alpha = opacity
                scaleX = 1f + (1f - progress) * 0.05f
                scaleY = 1f + (1f - progress) * 0.05f
            }
    ) {
        Text(
            text = text,
            color = if (isFinalized) color else color.copy(alpha = 0.9f),
            fontSize = fontSize,
            lineHeight = lineHeight,
            fontFamily = FontFamily.Monospace,
            style = androidx.compose.ui.text.TextStyle(
                shadow = if (!isFinalized) {
                    androidx.compose.ui.graphics.Shadow(
                        color = NeonCyan.copy(alpha = 0.5f),
                        blurRadius = 5f + (1f - progress) * 10f
                    )
                } else null
            )
        )
    }
}
