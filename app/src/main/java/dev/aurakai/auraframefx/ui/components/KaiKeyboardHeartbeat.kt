package dev.aurakai.auraframefx.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * 💓 KAI KEYBOARD HEARTBEAT
 * Localized cyan glow in the bottom corners, pulsing with Kai's resonance.
 */
@Composable
fun KaiKeyboardHeartbeat() {
    val infiniteTransition = rememberInfiniteTransition(label = "kai_heartbeat")
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.1f,
        targetValue = 0.4f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // Bottom Left Glow
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .size(120.dp)
                .offset(x = (-40).dp, y = 40.dp)
                .blur(40.dp)
                .background(Color.Cyan.copy(alpha = glowAlpha), CircleShape)
        )

        // Bottom Right Glow
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(120.dp)
                .offset(x = 40.dp, y = 40.dp)
                .blur(40.dp)
                .background(Color.Cyan.copy(alpha = glowAlpha), CircleShape)
        )
    }
}
