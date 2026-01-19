package dev.aurakai.auraframefx.ui.gates

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource

/**
 * PURE GATE IMAGE - Just the card, no text, no animation
 * Fills entire screen at 9:16 aspect ratio
 */
@Composable
fun GateCard(
    config: GateConfig,
    modifier: Modifier = Modifier,
    onDoubleTap: () -> Unit
) {
    val context = LocalContext.current
    val resourceName = config.pixelArtUrl ?: "gate_auralab_final"
    val resId = context.resources.getIdentifier(
        resourceName,
        "drawable",
        context.packageName
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .drawBehind {
                // NEON BLUE BORDER
                val borderWidth = 4f
                drawRect(
                    color = config.glowColor,
                    topLeft = androidx.compose.ui.geometry.Offset(borderWidth / 2, borderWidth / 2),
                    size = androidx.compose.ui.geometry.Size(
                        size.width - borderWidth,
                        size.height - borderWidth
                    ),
                    style = Stroke(borderWidth)
                )
            },
        contentAlignment = Alignment.Center
    ) {
        if (resId > 0) {
            Image(
                painter = painterResource(id = resId),
                contentDescription = config.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit,
                alpha = 0.95f
            )
        }
    }
}
