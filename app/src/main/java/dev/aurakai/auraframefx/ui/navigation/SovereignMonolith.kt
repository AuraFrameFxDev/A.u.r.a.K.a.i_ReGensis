package dev.aurakai.auraframefx.ui.navigation

import androidx.compose.animation.core.*
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import dev.aurakai.auraframefx.ui.theme.SovereignTeal

/**
 * 🗿 SOVEREIGN MONOLITH
 * 8K High-Fi, 16% Rounded Corners, Upright Breathing Float.
 * This is the primary navigation unit for the Sovereign Procession.
 */
@Composable
fun SovereignMonolith(
    imagePath: String,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "breathing")
    val breathingOffset by infiniteTransition.animateFloat(
        initialValue = -10f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(2500, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "translationY"
    )

    Box(
        modifier = modifier
            .fillMaxWidth(0.88f)
            .aspectRatio(9f / 16f)
            .graphicsLayer {
                translationY = breathingOffset
            }
            .clip(RoundedCornerShape(percent = 16))
            .border(1.5.dp, SovereignTeal, RoundedCornerShape(percent = 16))
    ) {
        AsyncImage(
            model = imagePath,
            contentDescription = "Sovereign Monolith Asset",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}
