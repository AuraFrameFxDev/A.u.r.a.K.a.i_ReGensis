package dev.aurakai.auraframefx.ui.gates

import androidx.compose.animation.core.EaseInOutSine
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * PURE GATE IMAGE - High Fidelity Card
 * Uses 9:21 aspect ratio with slow kinetic floating animation.
 */
@Composable
fun GateCard(
    config: GateConfig,
    modifier: Modifier = Modifier,
    onDoubleTap: () -> Unit
) {
    val context = LocalContext.current
    val primaryName = config.pixelArtUrl
    var resId = if (primaryName != null) {
        context.resources.getIdentifier(primaryName, "drawable", context.packageName)
    } else 0
    
    // Fallback if primary not found
    if (resId == 0 && config.fallbackUrl != null) {
        resId = context.resources.getIdentifier(config.fallbackUrl, "drawable", context.packageName)
    }
    
    // Final fallback to default
    if (resId == 0) {
        resId = context.resources.getIdentifier("gate_auralab_final", "drawable", context.packageName)
    }

    // SLOW KINETIC FLOATING ANIMATION
    val infiniteTransition = rememberInfiniteTransition(label = "gateFloat")
    val rotation by infiniteTransition.animateFloat(
        initialValue = -1f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "rotation"
    )
    val offsetY by infiniteTransition.animateFloat(
        initialValue = -10f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "offsetY"
    )

    Box(
        modifier = modifier
            .fillMaxHeight()
            .aspectRatio(9f / 21f)
            .graphicsLayer {
                rotationZ = rotation
                translationY = offsetY
            }
            .background(Color.Black)
            .drawBehind {
                val borderWidth = 4f
                val innerPadding = 8f
                
                // MAIN OUTER FRAME
                drawRect(
                    color = config.glowColor.copy(alpha = 0.6f),
                    topLeft = androidx.compose.ui.geometry.Offset(borderWidth / 2, borderWidth / 2),
                    size = androidx.compose.ui.geometry.Size(
                        size.width - borderWidth,
                        size.height - borderWidth
                    ),
                    style = Stroke(borderWidth)
                )

                // INNER ACCENT FRAME
                drawRect(
                    color = config.glowColor.copy(alpha = 0.3f),
                    topLeft = androidx.compose.ui.geometry.Offset(innerPadding, innerPadding),
                    size = androidx.compose.ui.geometry.Size(
                        size.width - innerPadding * 2,
                        size.height - innerPadding * 2
                    ),
                    style = Stroke(1f)
                )

                // CORNER BRACKETS
                val bracketLen = 30f
                // Top-Left
                drawLine(config.glowColor, androidx.compose.ui.geometry.Offset(0f, 0f), androidx.compose.ui.geometry.Offset(bracketLen, 0f), 6f)
                drawLine(config.glowColor, androidx.compose.ui.geometry.Offset(0f, 0f), androidx.compose.ui.geometry.Offset(0f, bracketLen), 6f)
                // Top-Right
                drawLine(config.glowColor, androidx.compose.ui.geometry.Offset(size.width, 0f), androidx.compose.ui.geometry.Offset(size.width - bracketLen, 0f), 6f)
                drawLine(config.glowColor, androidx.compose.ui.geometry.Offset(size.width, 0f), androidx.compose.ui.geometry.Offset(size.width, bracketLen), 6f)
                // Bottom-Left
                drawLine(config.glowColor, androidx.compose.ui.geometry.Offset(0f, size.height), androidx.compose.ui.geometry.Offset(bracketLen, size.height), 6f)
                drawLine(config.glowColor, androidx.compose.ui.geometry.Offset(0f, size.height), androidx.compose.ui.geometry.Offset(0f, size.height - bracketLen), 6f)
                // Bottom-Right
                drawLine(config.glowColor, androidx.compose.ui.geometry.Offset(size.width, size.height), androidx.compose.ui.geometry.Offset(size.width - bracketLen, size.height), 6f)
                drawLine(config.glowColor, androidx.compose.ui.geometry.Offset(size.width, size.height), androidx.compose.ui.geometry.Offset(size.width, size.height - bracketLen), 6f)
            },
        contentAlignment = Alignment.Center
    ) {
        // SCENE IMAGE - Framed inside tech borders
        if (resId > 0) {
            Image(
                painter = painterResource(id = resId),
                contentDescription = config.title,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp), // PADDING TO FIT INSIDE TECH FRAME
                contentScale = ContentScale.Fit, // PRESERVE "WHOLE IMAGE" FIDELITY
                alpha = 0.95f
            )
        }

        // SPECIAL TITLES & OVERLAYS
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = when (config.titlePlacement) {
                TitlePlacement.TOP_CENTER -> Arrangement.Top
                TitlePlacement.BOTTOM_CENTER -> Arrangement.Bottom
                else -> Arrangement.Center
            }
        ) {
            if (config.titlePlacement != TitlePlacement.HIDDEN) {
                // Try to load a special title image if it exists: title_module_id
                val titleImageId = context.resources.getIdentifier(
                    "title_${config.moduleId.replace("-", "_")}", 
                    "drawable", 
                    context.packageName
                )

                if (titleImageId > 0) {
                    Image(
                        painter = painterResource(id = titleImageId),
                        contentDescription = config.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                } else {
                    // Fallback to stylized text
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Brush.verticalGradient(
                                colors = listOf(Color.Black.copy(alpha = 0.8f), Color.Transparent)
                            ))
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        androidx.compose.material3.Text(
                            text = config.title,
                            color = config.glowColor,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = dev.aurakai.auraframefx.ui.theme.LEDFontFamily
                        )
                        androidx.compose.material3.Text(
                            text = config.subtitle,
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 10.sp,
                            fontFamily = dev.aurakai.auraframefx.ui.theme.LEDFontFamily
                        )
                    }
                }
            }
        }
    }
}
