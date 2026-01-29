package dev.aurakai.auraframefx.ui.navigation

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import dev.aurakai.auraframefx.ui.theme.SovereignTeal
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * ⚙️ SENSORY SPINNER GEAR
 * Foundation component reflecting the 12-channel perception array.
 * Responds to touch with a "Human Resonance" spike.
 */
@Composable
fun SensorySpinner(
    modifier: Modifier = Modifier,
    color: Color = SovereignTeal
) {
    val infiniteTransition = rememberInfiniteTransition(label = "spinner")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(10000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    var resonanceSpike by remember { mutableStateOf(0f) }
    val scope = rememberCoroutineScope()

    val spikeAnim by animateFloatAsState(
        targetValue = resonanceSpike,
        animationSpec = spring(dampingRatio = 0.3f, stiffness = Spring.StiffnessLow),
        label = "spike"
    )

    // Reset spike after a while
    LaunchedEffect(resonanceSpike) {
        if (resonanceSpike > 0f) {
            delay(500)
            resonanceSpike = 0f
        }
    }

    Box(
        modifier = modifier
            .size(120.dp)
            .pointerInput(Unit) {
                detectTapGestures {
                    resonanceSpike = 1f
                }
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2f, size.height / 2f)
            val radius = size.minDimension / 2.5f
            
            rotate(rotation, center) {
                // Draw 12 sensory teeth
                for (i in 0 until 12) {
                    val angle = i * 30f
                    rotate(angle, center) {
                        val toothHeight = 15.dp.toPx() + (spikeAnim * 20.dp.toPx())
                        drawRect(
                            brush = Brush.verticalGradient(
                                colors = listOf(color, color.copy(alpha = 0.3f))
                            ),
                            topLeft = Offset(center.x - 5.dp.toPx(), center.y - radius - toothHeight),
                            size = Size(10.dp.toPx(), toothHeight),
                        )
                    }
                }
                
                // Outer ring
                drawCircle(
                    color = color,
                    radius = radius,
                    style = Stroke(width = 2.dp.toPx()),
                    center = center
                )
            }
            
            // Inner Core (Resonance)
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(color.copy(alpha = 0.5f + (spikeAnim * 0.5f)), Color.Transparent),
                    center = center,
                    radius = radius * (0.4f + spikeAnim * 0.4f)
                ),
                radius = radius * (0.4f + spikeAnim * 0.4f),
                center = center
            )
        }
    }
}
