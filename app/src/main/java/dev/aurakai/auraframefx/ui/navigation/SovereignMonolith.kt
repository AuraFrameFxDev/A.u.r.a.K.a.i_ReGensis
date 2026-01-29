package dev.aurakai.auraframefx.ui.navigation

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.request.CachePolicy
import coil3.request.ImageRequest

/**
 * 🗿 SOVEREIGN MONOLITH
 * 8K High-Fi, 16% Rounded Corners, Upright Breathing Float.
 * No fallbacks. Forced load from filesystem.
 */
@Composable
fun SovereignMonolith(
    imagePath: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val infiniteTransition = rememberInfiniteTransition(label = "breathing")
    val breathingOffset by infiniteTransition.animateFloat(
        initialValue = -10f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = EaseInOutSine),
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
            .clip(RoundedCornerShape(percent = 16)) // THE 16% RADIUS
            .background(Color.Black.copy(alpha = 0.4f))
            .border(2.dp, SovereignTeal, RoundedCornerShape(percent = 16))
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(imagePath)
                .memoryCachePolicy(CachePolicy.DISABLED) // Forced Load
                .diskCachePolicy(CachePolicy.DISABLED)   // No "placeholder" garbage
                .build(),
            contentDescription = "Sovereign Monolith: $imagePath",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            onError = { result ->
                Timber.e("CRITICAL: Failed to load Sovereign Monolith from $imagePath. Result: ${result.result}")
            }
        )
    }
}
