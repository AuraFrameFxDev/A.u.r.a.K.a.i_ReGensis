package dev.aurakai.auraframefx.domains.aura.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.launch

/**
 * 🌌 PARALLAX DEPTH STACK — The 4-Tier Visual Core
 * Implements true 4D parallax mapping for the Exodus Citadel architecture.
 *
 * Layer 0: Bedrock (Background Image) - Multiplier: 0.1f (Slowest)
 * Layer 1: Geometry (Wireframes/Runes) - Multiplier: 0.3f
 * Layer 2: Interaction (UI Panels/Cards) - Multiplier: 0.6f
 * Layer 3: Overlay (Particles/Orbs) - Multiplier: 1.0f (Fastest)
 */
@Composable
fun ParallaxDepthStack(
    modifier: Modifier = Modifier,
    bedrock: @Composable BoxScope.() -> Unit,
    geometry: @Composable BoxScope.() -> Unit = {},
    interaction: @Composable BoxScope.() -> Unit = {},
    overlay: @Composable BoxScope.() -> Unit = {}
) {
    val offset = remember { Animatable(Offset.Zero, Offset.VectorConverter) }
    val scope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        if (event.type == PointerEventType.Move) {
                            val position = event.changes.first().position
                            val centerX = size.width / 2f
                            val centerY = size.height / 2f

                            // Normalize offset from center (-1.0 to 1.0)
                            val normalizedX = (position.x - centerX) / centerX
                            val normalizedY = (position.y - centerY) / centerY

                            scope.launch {
                                offset.animateTo(
                                    Offset(normalizedX, normalizedY),
                                    spring(stiffness = 150f, dampingRatio = 0.8f)
                                )
                            }
                        }
                    }
                }
            }
    ) {
        // LAYER 0: BEDROCK (Background)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    translationX = offset.value.x * 30f // Subtle movement
                    translationY = offset.value.y * 30f
                    scaleX = 1.05f // Slight overscan to prevent edges showing
                    scaleY = 1.05f
                },
            contentAlignment = Alignment.Center,
            content = bedrock
        )

        // LAYER 1: GEOMETRY (Wireframes)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    translationX = offset.value.x * 60f
                    translationY = offset.value.y * 60f
                },
            contentAlignment = Alignment.Center,
            content = geometry
        )

        // LAYER 2: INTERACTION (The UI)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    translationX = offset.value.x * 90f
                    translationY = offset.value.y * 90f
                    // Add slight rotation for 3D feel
                    rotationY = offset.value.x * 5f
                    rotationX = -offset.value.y * 5f
                },
            contentAlignment = Alignment.Center,
            content = interaction
        )

        // LAYER 3: OVERLAY (Particles)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    translationX = offset.value.x * 120f
                    translationY = offset.value.y * 120f
                },
            contentAlignment = Alignment.Center,
            content = overlay
        )
    }
}
