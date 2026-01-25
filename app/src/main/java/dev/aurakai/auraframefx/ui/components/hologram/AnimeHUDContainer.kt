package dev.aurakai.auraframefx.ui.components.hologram

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.aurakai.auraframefx.ui.theme.ChessFontFamily
import dev.aurakai.auraframefx.ui.theme.LEDFontFamily

/**
 * 🌠 ANIME HUD CONTAINER - High Fidelity UI Box
 * Based on the user's provided "Oracle Drive" HUD design.
 */
import androidx.compose.ui.res.painterResource
import dev.aurakai.auraframefx.R

/**
 * 🌠 ANIME HUD CONTAINER - High Fidelity UI Box
 * Based on the user's provided "Oracle Drive" HUD design.
 * Positioned Back-Left with Title on top line.
 */
@Composable
fun AnimeHUDContainer(
    title: String,
    description: String,
    glowColor: Color = Color.Cyan,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "hudScale")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )

    Box(modifier = modifier.fillMaxSize()) {
        // 0. HOLOGRAPHIC BACKDROP (Base layer)
        androidx.compose.foundation.Image(
            painter = painterResource(id = R.drawable.holographic_backdrop),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = androidx.compose.ui.layout.ContentScale.FillBounds,
            alpha = 0.4f
        )

        // 0.5 PARTICLE SYSTEM
        ParticleSystem(glowColor = glowColor)

        // 1. TOP-LEFT HUD BOX
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 40.dp, start = 30.dp)
                .width(280.dp),
            horizontalAlignment = Alignment.Start
        ) {
            // THE TITLE (Top line of the box area)
            Text(
                text = title.uppercase(),
                fontSize = 18.sp,
                fontFamily = ChessFontFamily,
                color = glowColor.copy(alpha = pulseAlpha),
                modifier = Modifier.padding(bottom = 4.dp, start = 8.dp)
            )

            // The Angled HUD Framework
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .clip(HUDBoxShape)
                    .background(Color.Black.copy(alpha = 0.7f))
                    .border(1.dp, glowColor.copy(alpha = 0.4f * pulseAlpha), HUDBoxShape)
            ) {
                // Description inside the box
                Text(
                    text = description,
                    fontSize = 11.sp,
                    fontFamily = LEDFontFamily,
                    color = glowColor.copy(alpha = 0.8f),
                    textAlign = TextAlign.Start,
                    lineHeight = 16.sp,
                    modifier = Modifier.padding(16.dp).align(Alignment.CenterStart)
                )
                
                // Tech accents
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val dotSize = 2.dp.toPx()
                    drawCircle(glowColor, dotSize, Offset(10.dp.toPx(), 10.dp.toPx()), alpha = pulseAlpha)
                    drawCircle(glowColor, dotSize, Offset(size.width - 10.dp.toPx(), 10.dp.toPx()), alpha = pulseAlpha)
                }
            }
        }

        // MAIN CONTENT AREA (The "floating alone" cards)
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}

@Composable
fun ParticleSystem(glowColor: Color) {
    val infiniteTransition = rememberInfiniteTransition(label = "particles")
    val time by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "time"
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        val random = kotlin.random.Random(42)
        repeat(30) { i ->
            val x = (random.nextFloat() * size.width + time * (10f + i)) % size.width
            val y = (random.nextFloat() * size.height - time * (5f + i)) % size.height
            
            drawCircle(
                color = glowColor.copy(alpha = 0.3f),
                radius = 2f,
                center = Offset(x, y)
            )
        }
    }
}

/**
 * Shape for the Angled HUD Box seen in the image.
 */
val HUDBoxShape = GenericShape { size, _ ->
    val corner = 20f
    moveTo(corner, 0f)
    lineTo(size.width - corner, 0f)
    lineTo(size.width, corner)
    lineTo(size.width, size.height - corner)
    lineTo(size.width - corner, size.height)
    lineTo(corner, size.height)
    lineTo(0f, size.height - corner)
    lineTo(0f, corner)
    close()
}
