package dev.aurakai.auraframefx.ui.components.hud

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.aurakai.auraframefx.R
import dev.aurakai.auraframefx.ui.theme.ChessFontFamily
import dev.aurakai.auraframefx.ui.theme.LEDFontFamily

/**
 * 🌠 ANIME HUD CONTAINER - High Fidelity UI Box
 * Based on the user's provided "Oracle Drive" HUD design.
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

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val maxWidth = maxWidth
        val maxHeight = maxHeight
        val interfaceColor = Color(0xFF00E5FF) // Fixed Tech Blue for Interface
        
        // 0. HOLOGRAPHIC BACKDROP
        androidx.compose.foundation.Image(
            painter = painterResource(id = R.drawable.gatebackdrop),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = androidx.compose.ui.layout.ContentScale.Crop, 
            alpha = 0.78f 
        )
        
        // 0.1 GLOBAL BACKGROUND PARTICLES
        GlobalBackgroundParticles(color = interfaceColor)
        
        // 0.2 VOID ATMOSPHERE (Deep Vignette)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.85f),
                            Color.Black
                        ),
                        radius = java.lang.Math.max(maxWidth.value, maxHeight.value) * 0.9f
                    )
                )
        )
        
        // 0.3 FLOATING ARCANE RUNES
        FloatingArcaneRunes(glowColor = glowColor)
        
        // 0.5 HOLOGRAPHIC STAGE LIGHTS
        HolographicStageLights(color = interfaceColor)

        // 1. TOP-LEFT HUD BOX
        val topPadding = maxHeight * 0.12f
        val startPadding = maxWidth * 0.05f
        val hudWidth = maxWidth * 0.65f
        
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = topPadding, start = startPadding)
                .width(hudWidth),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = title.uppercase(),
                fontSize = 20.sp,
                fontFamily = ChessFontFamily,
                color = interfaceColor.copy(alpha = pulseAlpha),
                modifier = Modifier.padding(bottom = 6.dp, start = 8.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp) 
                    .clip(HUDBoxShape)
                    .background(Color.Black.copy(alpha = 0.4f))
                    .border(1.dp, interfaceColor.copy(alpha = 0.4f * pulseAlpha), HUDBoxShape)
            ) {
                Text(
                    text = description,
                    fontSize = 12.sp,
                    fontFamily = LEDFontFamily,
                    color = interfaceColor.copy(alpha = 0.9f),
                    textAlign = TextAlign.Start,
                    lineHeight = 18.sp,
                    modifier = Modifier
                        .padding(20.dp)
                        .align(Alignment.CenterStart)
                )
                
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val dotSize = 2.dp.toPx()
                    drawCircle(interfaceColor, dotSize, Offset(10.dp.toPx(), 10.dp.toPx()), alpha = pulseAlpha)
                    drawCircle(interfaceColor, dotSize, Offset(size.width - 10.dp.toPx(), 10.dp.toPx()), alpha = pulseAlpha)
                }
            }
        }
        
        // MAIN CONTENT AREA
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}

@Composable
fun HolographicStageLights(color: Color) {
    val infiniteTransition = rememberInfiniteTransition(label = "stage_lights")
    val breathingAlpha by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height
        val s = 2.dp.toPx()
        
        drawRect(
            color = color.copy(alpha = 0.3f),
            style = Stroke(width = s)
        )
        
        val cL = 20.dp.toPx()
        drawLine(color.copy(alpha = 0.8f), Offset(0f, cL), Offset(0f, 0f), s * 2)
        drawLine(color.copy(alpha = 0.8f), Offset(0f, 0f), Offset(cL, 0f), s * 2)
        drawLine(color.copy(alpha = 0.8f), Offset(w, cL), Offset(w, 0f), s * 2)
        drawLine(color.copy(alpha = 0.8f), Offset(w - cL, 0f), Offset(w, 0f), s * 2)
        drawLine(color.copy(alpha = 0.8f), Offset(0f, h - cL), Offset(0f, h), s * 2)
        drawLine(color.copy(alpha = 0.8f), Offset(0f, h), Offset(cL, h), s * 2)
        drawLine(color.copy(alpha = 0.8f), Offset(w, h - cL), Offset(w, h), s * 2)
        drawLine(color.copy(alpha = 0.8f), Offset(w - cL, h), Offset(w, h), s * 2)

        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(color.copy(alpha = 0.4f), color.copy(alpha = 0.1f), Color.Transparent),
                center = Offset(w / 2, h), radius = w * 0.7f
            ),
            center = Offset(w / 2, h), radius = w * 0.7f
        )
    }
}

@Composable
fun GlobalBackgroundParticles(color: Color) {
    val infiniteTransition = rememberInfiniteTransition(label = "global_particles")
    val time by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 1f,
        animationSpec = infiniteRepeatable(animation = tween(30000, easing = LinearEasing)),
        label = "time"
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        val random = kotlin.random.Random(123)
        repeat(50) { i ->
            val startX = random.nextFloat() * size.width
            val startY = random.nextFloat() * size.height
            val speed = 0.2f + random.nextFloat() * 0.3f
            val yOffset = (time * size.height * speed) % size.height
            val currY = (startY - yOffset + size.height) % size.height
            val currX = startX + kotlin.math.sin(time * 6.28f + i) * 20.dp.toPx()
            val alpha = (0.2f + 0.3f * kotlin.math.sin(time * 3f + i)).coerceIn(0.1f, 0.5f)
            drawCircle(color = color.copy(alpha = alpha), radius = 1.5.dp.toPx(), center = Offset(currX, currY))
        }
    }
}

@Composable
fun FloatingArcaneRunes(glowColor: Color) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val runes = remember {
        listOf(
            R.drawable.rune_chronos,
            R.drawable.rune_cortex,
            R.drawable.rune_oracle,
            R.drawable.rune_sentinel,
            R.drawable.rune_surgeon
        ).map { 
            val drawable = androidx.core.content.ContextCompat.getDrawable(context, it)
            (drawable as? android.graphics.drawable.BitmapDrawable)?.bitmap?.asImageBitmap()
        }
    }

    val infiniteTransition = rememberInfiniteTransition(label = "runes")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 360f,
        animationSpec = infiniteRepeatable(animation = tween(60000, easing = LinearEasing)),
        label = "rotation"
    )
    val pulse by infiniteTransition.animateFloat(
        initialValue = 0.2f, targetValue = 0.5f,
        animationSpec = infiniteRepeatable(animation = tween(4000, easing = EaseInOutSine)),
        label = "pulse"
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        val centerX = size.width / 2
        val centerY = size.height / 2
        val radius = size.width * 0.7f
        
        rotate(rotation, pivot = Offset(centerX, centerY)) {
            val validRunes = runes.filterNotNull()
            val angleStep = 360f / validRunes.size.coerceAtLeast(1)
            validRunes.forEachIndexed { index, image ->
                val angle = index * angleStep
                val rad = Math.toRadians(angle.toDouble())
                val x = centerX + radius * kotlin.math.cos(rad).toFloat()
                val y = centerY + radius * kotlin.math.sin(rad).toFloat()
                translate(left = x - image.width / 2, top = y - image.height / 2) {
                    drawImage(image = image, colorFilter = ColorFilter.tint(glowColor.copy(alpha = pulse), BlendMode.SrcIn))
                }
            }
        }
    }
}

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
