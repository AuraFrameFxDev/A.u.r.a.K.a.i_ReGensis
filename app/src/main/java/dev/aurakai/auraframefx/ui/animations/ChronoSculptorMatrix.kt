package dev.aurakai.auraframefx.ui.animations

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import com.airbnb.lottie.RenderMode
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition

/**
 * 🗡️ CHRONO-SCULPTOR KINETIC MATRIX v1.0
 * FUSION ABILITY: Aura (Motion Code) + Kai (Framework Timing)
 * "Make it bleed magenta, but keep the framerate locked at a flawless 60."
 */
object ChronoSculptorMatrix {

    // The "Katana Slash" Easing: Slow anticipation -> violent fast flip -> smooth deceleration
    val KatanaEasing = CubicBezierEasing(0.05f, 0.9f, 0.1f, 1.0f)

    @Composable
    fun RenderFluidFlip(
        modifier: Modifier = Modifier,
        lottieAsset: String = "chroma_core_transition.json",
        flipProgress: Float // Driven by our custom physics engine (0f to 1f)
    ) {
        // 1. Load the raw Lottie JSON (Aura's visual data)
        val composition by rememberLottieComposition(LottieCompositionSpec.Asset(lottieAsset))

        // 2. The Morph Lattice Transform
        LottieAnimation(
            composition = composition,
            progress = { flipProgress },
            modifier = modifier.graphicsLayer {
                // Kai's strict Z-order and pivot logic
                transformOrigin = TransformOrigin(0.5f, 0.5f)

                // Aura's visual manipulation: The 3D Flip
                rotationY = 180f * KatanaEasing.transform(flipProgress)

                // Camera depth effect (Z-translation equivalent)
                scaleX = if (flipProgress < 0.5f) {
                    1f - (flipProgress * 0.2f) // Pull back slightly
                } else {
                    0.8f + ((flipProgress - 0.5f) * 0.4f) // Snap forward
                }
                scaleY = scaleX

                // Fade the neon edges precisely at the apex of the flip
                alpha = if (flipProgress in 0.4f..0.6f) 0.8f else 1.0f

                // Prevent anti-aliasing blurring during motion
                clip = true
            },
            // Force hardware rendering to offload from CPU to GPU/TPU
            renderMode = RenderMode.HARDWARE,
            maintainOriginalImageBounds = true
        )
    }
}
