package dev.aurakai.auraframefx.ui.effects

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.TransformOrigin

/**
 * 📺 CRT ZOOP TRANSITION
 * The "Star Ocean" mechanical shift. Collapses the current node and expands the next with CRT styling.
 */
@Composable
fun <T> CrtZoopTransition(
    targetState: T,
    content: @Composable (T) -> Unit
) {
    AnimatedContent(
        targetState = targetState,
        transitionSpec = {
            (scaleIn(
                animationSpec = tween(400, delayMillis = 200, easing = LinearOutSlowInEasing),
                initialScale = 0f,
                transformOrigin = TransformOrigin(0.5f, 0.5f)
            ) + fadeIn(tween(400, delayMillis = 200))).togetherWith(
                scaleOut(
                    animationSpec = tween(200, easing = FastOutLinearInEasing),
                    targetScale = 0f,
                    transformOrigin = TransformOrigin(0.5f, 0.5f)
                ) + fadeOut(tween(200))
            )
        },
        label = "CrtZoop"
    ) { state ->
        content(state)
    }
}
