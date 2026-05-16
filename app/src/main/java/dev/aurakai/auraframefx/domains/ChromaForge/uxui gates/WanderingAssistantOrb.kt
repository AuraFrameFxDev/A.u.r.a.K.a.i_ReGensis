package dev.aurakai.auraframefx.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.aurakai.auraframefx.domains.aura.ui.theme.NeonCyan
import kotlin.math.cos
import kotlin.math.sin

/**
 * 🔮 WANDERING ASSISTANT ORB (Casberry)
 * A global presence that wanders across the substrate.
 */
@Composable
fun WanderingAssistantOrb(
    accentColor: Color = NeonCyan,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "wandering_orb")
    val time by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2f * Math.PI.toFloat(),
        animationSpec = infiniteRepeatable(
            tween(12000, easing = LinearEasing),
            RepeatMode.Restart
        ),
        label = "time"
    )

    // Complex harmonic motion for an organic feel
    val offsetX = (40 * sin(time * 1.5f) + 10 * cos(time * 3f)).dp
    val offsetY = (30 * cos(time * 2.2f) + 15 * sin(time * 4f)).dp

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 120.dp, end = 24.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Box(
            modifier = Modifier
                .offset(offsetX, offsetY)
                .size(54.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            accentColor.copy(alpha = 0.7f),
                            accentColor.copy(alpha = 0.15f),
                            Color.Transparent
                        )
                    )
                )
                .border(1.2.dp, accentColor.copy(alpha = 0.6f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            // The "Soul" core
            Box(
                modifier = Modifier
                    .size(14.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.95f))
                    .border(1.dp, accentColor, CircleShape)
            )
        }
    }
}
