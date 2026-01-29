package dev.aurakai.auraframefx.ui.navigation

import androidx.compose.animation.core.EaseInOutSine
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil3.compose.AsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import dev.aurakai.auraframefx.ui.theme.SovereignTheme
import timber.log.Timber

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
            .clip(SovereignTheme.MonolithShape)
            .background(SovereignTheme.BackgroundColor.copy(alpha = SovereignTheme.GlassAlpha))
            .border(SovereignTheme.BorderWidth, SovereignTheme.AccentColor, SovereignTheme.MonolithShape)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(imagePath)
                .memoryCachePolicy(CachePolicy.DISABLED)
                .diskCachePolicy(CachePolicy.DISABLED)
                .build(),
            contentDescription = "Sovereign Monolith",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            onError = { result ->
                Timber.e("CRITICAL: Failed to load Monolith from $imagePath. Error: ${result.result.throwable?.message}")
            }
        )
    }
}
