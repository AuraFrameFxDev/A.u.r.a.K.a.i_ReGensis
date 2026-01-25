package dev.aurakai.auraframefx.ui.components.hologram

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.aurakai.auraframefx.R
import dev.aurakai.auraframefx.ui.theme.ChessFontFamily
import dev.aurakai.auraframefx.ui.theme.LEDFontFamily

/**
 * 🌌 NO-FONT CARD ENGINE
 * Renders a holographic card with a rotating technical rune.
 */
@Composable
fun HolographicCard(
    runeRes: Int,
    glowColor: Color,
    modifier: Modifier = Modifier,
    dangerLevel: Float = 0f // 0 to 1, affects red tint
) {
    val infiniteTransition = rememberInfiniteTransition(label = "HologramRotation")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(10000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "RuneRotation"
    )

    val bounce by infiniteTransition.animateFloat(
        initialValue = -10f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "HologramBounce"
    )

    val finalGlowColor = lerp(glowColor, Color.Red, dangerLevel)

    Box(
        modifier = modifier
            .width(280.dp)
            .height(400.dp)
            .offset(y = bounce.dp),
        contentAlignment = Alignment.Center
    ) {
        // 1. NEON FRAME
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .clip(RoundedCornerShape(12.dp))
                .border(
                    2.dp,
                    Brush.linearGradient(
                        listOf(finalGlowColor, finalGlowColor.copy(alpha = 0.3f), finalGlowColor)
                    ),
                    RoundedCornerShape(12.dp)
                )
                .background(finalGlowColor.copy(alpha = 0.05f))
        )

        // 2. OUTER GLOW BLUR
        Box(
            modifier = Modifier
                .fillMaxSize()
                .blur(20.dp)
                .background(
                    Brush.radialGradient(
                        listOf(finalGlowColor.copy(alpha = 0.2f), Color.Transparent)
                    )
                )
        )

        // 3. ROTATING RUNE (The Signal)
        Icon(
            painter = painterResource(id = runeRes),
            contentDescription = null,
            tint = finalGlowColor,
            modifier = Modifier
                .size(200.dp)
                .graphicsLayer {
                    rotationY = rotation
                    cameraDistance = 12f * density
                }
        )
    }
}

/**
 * 📺 ANIME HUD CONTAINER
 * Wraps screens in a high-fidelity holographic HUD overlay.
 */
@Composable
fun AnimeHUDContainer(
    title: String,
    description: String,
    glowColor: Color = Color.Cyan,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // BACKGROUND EMITTER (Holographic Foundation)
        Canvas(modifier = Modifier
            .align(Alignment.BottomCenter)
            .fillMaxWidth()
            .height(200.dp)
        ) {
            drawOval(
                brush = Brush.radialGradient(
                    colors = listOf(glowColor.copy(alpha = 0.3f), Color.Transparent)
                ),
                size = size
            )
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // TOP LAYER: RETRO TITLE
            Text(
                text = title.uppercase(),
                fontFamily = ChessFontFamily,
                fontSize = 24.sp,
                color = glowColor.copy(alpha = 0.9f),
                modifier = Modifier.padding(top = 40.dp),
                letterSpacing = 4.sp
            )

            // MIDDLE LAYER: THE MAIN CONTENT (Usually the Card)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                content()
            }

            // BOTTOM LAYER: DESCRIPTION & EMITTER BASE
            Column(
                modifier = Modifier
                    .padding(bottom = 60.dp, start = 40.dp, end = 40.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = description,
                    fontFamily = LEDFontFamily,
                    fontSize = 14.sp,
                    color = glowColor.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Holographic Pedestal Emitter Line
                Box(
                    modifier = Modifier
                        .width(200.dp)
                        .height(2.dp)
                        .background(
                            Brush.horizontalGradient(
                                listOf(Color.Transparent, glowColor, Color.Transparent)
                            )
                        )
                )
            }
        }
    }
}
