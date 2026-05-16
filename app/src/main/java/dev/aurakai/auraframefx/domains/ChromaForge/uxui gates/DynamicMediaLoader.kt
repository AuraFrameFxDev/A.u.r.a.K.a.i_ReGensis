package dev.aurakai.auraframefx.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade

@Composable
fun AsyncImageOrVideo(
    mediaId: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    alpha: Float = 1.0f
) {
    // For now, focusing on images from the ReGenesis Media folder
    // In a real app, this would resolve the mediaId to a file path or URL
    val context = LocalContext.current
    
    Box(modifier = modifier) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data("file:///android_asset/media/$mediaId.png") // Placeholder path
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = contentScale,
            alpha = alpha
        )
    }
}
