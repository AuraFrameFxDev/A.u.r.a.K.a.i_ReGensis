package dev.aurakai.auraframefx.ui.gates

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.math.sin

/**
 * BLADE RUNNER ENTRANCE CARD
 *
 * Purpose: Visual identity card for module selection on carousel
 * NOT a functional screen - just an entrance portal
 *
 * Features:
 * - AERO MECHA font style (geometric striped lettering)
 * - Blade Runner cyberpunk aesthetic
 * - Triple-layer neon border system
 * - Glassmorphism frosted blur effect
 * - Floating card with depth shadow
 * - Unique visual icon/symbol per module
 * - Scanline overlay (CRT retro feel)
 * - Particle/rain background
 * - Double-tap to ENTER module
 *
 * NOT included (saved for actual module screens):
 * - Function lists
 * - Interactive buttons inside card
 * - Detailed data displays
 */
@Composable
fun CyberpunkGateCard(
    config: GateConfig,
    modifier: Modifier = Modifier,
    onDoubleTap: () -> Unit = {}
) {
    var lastTapTime by remember { mutableLongStateOf(0L) }
    var scale by remember { mutableFloatStateOf(1f) }

    // Animations
    val infiniteTransition = rememberInfiniteTransition(label = "cyberpunk_animations")

    // Pulsing glow for borders
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "border_pulse"
    )

    // Floating animation
    val floatOffset by infiniteTransition.animateFloat(
        initialValue = -8f,
        targetValue = 8f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "card_float"
    )

    // Scanline animation
    val scanlineOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(5000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "scanline"
    )

    // Particle drift
    val particleDrift by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "particles"
    )

    // Double-tap feedback animation
    LaunchedEffect(scale) {
        if (scale != 1f) {
            delay(100)
            scale = 1f
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0A1628), // Dark Navy
                        Color(0xFF000814), // Deep Black
                        Color(0xFF001233)  // Midnight Blue
                    )
                )
            )
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        val currentTime = System.currentTimeMillis()
                        if (currentTime - lastTapTime < 300) {
                            // Double tap detected
                            scale = 0.95f
                            onDoubleTap()
                        }
                        lastTapTime = currentTime
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        // Background particles
        ParticleBackground(
            color = config.glowColor,
            drift = particleDrift,
            modifier = Modifier.fillMaxSize()
        )

        // Floating card with shadow
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .offset(y = floatOffset.dp),
            contentAlignment = Alignment.Center
        ) {
            // Drop shadow for floating effect
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y = 12.dp)
            ) {
                drawRoundRect(
                    color = Color.Black.copy(alpha = 0.5f),
                    topLeft = Offset(20f, 20f),
                    size = Size(size.width - 40f, size.height - 40f),
                    cornerRadius = CornerRadius(24f, 24f)
                )
            }

            // Main card container
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                // Triple-layer neon border system
                TripleLayerBorder(
                    primaryColor = config.borderColor,
                    glowColor = config.glowColor,
                    accentColor = config.secondaryGlowColor ?: config.glowColor,
                    pulseAlpha = pulseAlpha,
                    modifier = Modifier.fillMaxSize()
                )

                // Glassmorphism card content
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                        .background(
                            color = Color(0xFF0A1628).copy(alpha = 0.6f),
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
                        )
                        .blur(radius = 2.dp) // Glassmorphism blur
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.SpaceBetween,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Top: Icon row
                            IconRow(
                                config = config,
                                modifier = Modifier.fillMaxWidth()
                            )

                            // Center: Main visual content
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                MainVisualContent(
                                    config = config,
                                    pulseAlpha = pulseAlpha
                                )
                            }

                            // Bottom: Description box
                            DescriptionBox(
                                title = if (config.titlePlacement == TitlePlacement.BOTTOM_CENTER) config.title else "",
                                description = config.description,
                                borderColor = config.borderColor,
                                pulseAlpha = pulseAlpha,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        // Flexible title positioning
                        FlexibleTitle(
                            config = config,
                            pulseAlpha = pulseAlpha
                        )
                    }
                }

                // Scanline overlay
                ScanlineOverlay(
                    offset = scanlineOffset,
                    color = config.borderColor,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

/**
 * Triple-layer neon border with outer glow, sharp middle border, and corner accents
 */
@Composable
private fun TripleLayerBorder(
    primaryColor: Color,
    glowColor: Color,
    accentColor: Color,
    pulseAlpha: Float,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val cornerRadius = 24f
        androidx.compose.ui.geometry.Rect(
            offset = Offset(0f, 0f),
            size = size
        )

        // Layer 1: Outer glow (soft, wide)
        drawRoundRect(
            brush = Brush.radialGradient(
                colors = listOf(
                    glowColor.copy(alpha = pulseAlpha * 0.4f),
                    glowColor.copy(alpha = pulseAlpha * 0.2f),
                    Color.Transparent
                ),
                center = center
            ),
            topLeft = Offset(-20f, -20f),
            size = Size(size.width + 40f, size.height + 40f),
            cornerRadius = CornerRadius(cornerRadius + 20f, cornerRadius + 20f)
        )

        // Layer 2: Sharp middle border
        drawRoundRect(
            color = primaryColor.copy(alpha = pulseAlpha),
            topLeft = Offset(0f, 0f),
            size = size,
            cornerRadius = CornerRadius(cornerRadius, cornerRadius),
            style = Stroke(width = 3f)
        )

        // Layer 3: Corner accents
        val cornerLength = 80f
        val accentWidth = 6f

        // Top-left corner
        drawLine(
            color = accentColor.copy(alpha = pulseAlpha),
            start = Offset(cornerRadius, 0f),
            end = Offset(cornerLength, 0f),
            strokeWidth = accentWidth
        )
        drawLine(
            color = accentColor.copy(alpha = pulseAlpha),
            start = Offset(0f, cornerRadius),
            end = Offset(0f, cornerLength),
            strokeWidth = accentWidth
        )

        // Top-right corner
        drawLine(
            color = accentColor.copy(alpha = pulseAlpha),
            start = Offset(size.width - cornerLength, 0f),
            end = Offset(size.width - cornerRadius, 0f),
            strokeWidth = accentWidth
        )
        drawLine(
            color = accentColor.copy(alpha = pulseAlpha),
            start = Offset(size.width, cornerRadius),
            end = Offset(size.width, cornerLength),
            strokeWidth = accentWidth
        )

        // Bottom-right corner
        drawLine(
            color = accentColor.copy(alpha = pulseAlpha),
            start = Offset(size.width - cornerLength, size.height),
            end = Offset(size.width - cornerRadius, size.height),
            strokeWidth = accentWidth
        )
        drawLine(
            color = accentColor.copy(alpha = pulseAlpha),
            start = Offset(size.width, size.height - cornerLength),
            end = Offset(size.width, size.height - cornerRadius),
            strokeWidth = accentWidth
        )

        // Bottom-left corner
        drawLine(
            color = accentColor.copy(alpha = pulseAlpha),
            start = Offset(cornerRadius, size.height),
            end = Offset(cornerLength, size.height),
            strokeWidth = accentWidth
        )
        drawLine(
            color = accentColor.copy(alpha = pulseAlpha),
            start = Offset(0f, size.height - cornerLength),
            end = Offset(0f, size.height - cornerRadius),
            strokeWidth = accentWidth
        )
    }
}

/**
 * Icon row at the top of the card
 */
@Composable
private fun IconRow(
    config: GateConfig,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(60.dp)
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Placeholder icons - will be replaced with actual geometric icons
        repeat(4) { index ->
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = config.glowColor.copy(alpha = 0.3f),
                        shape = androidx.compose.foundation.shape.CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                // Icon placeholder - will be replaced with SVG-style composables
                Canvas(modifier = Modifier.size(32.dp)) {
                    drawCircle(
                        color = config.borderColor,
                        radius = size.minDimension / 3f,
                        style = Stroke(width = 2f)
                    )
                }
            }
        }
    }
}

/**
 * Main visual content area (center of card)
 */
@Composable
private fun MainVisualContent(
    config: GateConfig,
    pulseAlpha: Float
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // For now, show a large geometric icon placeholder
        // This will be replaced with pixel art, code views, or custom content
        Canvas(modifier = Modifier.fillMaxSize(0.6f)) {
            val radius = size.minDimension / 2f

            // Outer glow
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        config.glowColor.copy(alpha = pulseAlpha * 0.5f),
                        Color.Transparent
                    )
                ),
                radius = radius * 1.5f,
                center = center
            )

            // Main shape
            drawCircle(
                color = config.borderColor.copy(alpha = pulseAlpha),
                radius = radius,
                style = Stroke(width = 4f)
            )

            // Inner detail
            drawCircle(
                color = config.glowColor.copy(alpha = pulseAlpha * 0.7f),
                radius = radius * 0.5f,
                style = Stroke(width = 2f)
            )
        }
    }
}

/**
 * Description box at the bottom with module name and function details
 */
@Composable
private fun DescriptionBox(
    title: String,
    description: String,
    borderColor: Color,
    pulseAlpha: Float,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(
                color = Color(0xFF000814).copy(alpha = 0.8f),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Module name - AERO MECHA style (simple, no description)
        Box {
            // Glow layer
            Text(
                text = title.uppercase(),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 4.sp
                ),
                color = borderColor.copy(alpha = pulseAlpha * 0.5f),
                textAlign = TextAlign.Center,
                modifier = Modifier.offset(x = 2.dp, y = 2.dp)
            )

            // Main text
            Text(
                text = title.uppercase(),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 4.sp
                ),
                color = borderColor.copy(alpha = pulseAlpha),
                textAlign = TextAlign.Center
            )
        }
    }
}

/**
 * Scanline overlay for holographic effect
 */
@Composable
private fun ScanlineOverlay(
    offset: Float,
    color: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        // Horizontal scanlines
        for (y in 0..size.height.toInt() step 4) {
            val scanAlpha = (sin((offset + y) * 0.1f) + 1f) * 0.03f
            drawLine(
                color = color.copy(alpha = scanAlpha),
                start = Offset(0f, y.toFloat()),
                end = Offset(size.width, y.toFloat()),
                strokeWidth = 1f
            )
        }

        // Animated sweep line
        val sweepY = (offset % size.height)
        drawLine(
            color = color.copy(alpha = 0.3f),
            start = Offset(0f, sweepY),
            end = Offset(size.width, sweepY),
            strokeWidth = 2f
        )
    }
}

/**
 * Particle background for depth
 */
@Composable
private fun ParticleBackground(
    color: Color,
    drift: Float,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height

        // Floating particles
        for (i in 0..30) {
            val baseX = (width * (i % 6) / 6f)
            val baseY = (height * (i / 6) / 6f)

            val x = baseX + sin((drift + i * 10) * 0.01f) * 30
            val y = baseY + sin((drift + i * 15) * 0.008f) * 20

            val particleAlpha = (sin(drift * 0.02f + i) + 1f) * 0.1f

            drawCircle(
                color = color.copy(alpha = particleAlpha),
                radius = 2f,
                center = Offset(x, y)
            )
        }
    }
}

/**
 * Flexible title positioning - can be placed anywhere: vertical, sideways, corners, etc.
 */
@Composable
private fun FlexibleTitle(
    config: GateConfig,
    pulseAlpha: Float
) {
    when (config.titlePlacement) {
        TitlePlacement.NONE -> { /* No title */ }
        TitlePlacement.BOTTOM_CENTER -> { /* Already in description box */ }

        TitlePlacement.LEFT_VERTICAL -> {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 24.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = config.title.uppercase(),
                    style = (config.titleStyle?.textStyle ?: TextStyle.Default).copy(fontSize = 18.sp),
                    color = config.borderColor.copy(alpha = pulseAlpha),
                    modifier = Modifier.rotate(-90f)
                )
            }
        }

        TitlePlacement.RIGHT_VERTICAL -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = config.title.uppercase(),
                    style = (config.titleStyle?.textStyle ?: TextStyle.Default).copy(fontSize = 18.sp),
                    color = config.borderColor.copy(alpha = pulseAlpha),
                    modifier = Modifier
                        .padding(end = 24.dp)
                        .rotate(90f)
                )
            }
        }

        TitlePlacement.TOP_CENTER -> {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopCenter
            ) {
                Text(
                    text = config.title.uppercase(),
                    style = config.titleStyle!!.textStyle,
                    color = config.borderColor.copy(alpha = pulseAlpha),
                    modifier = Modifier.padding(top = 24.dp)
                )
            }
        }

        TitlePlacement.TOP_LEFT -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopStart
            ) {
                Text(
                    text = config.title.uppercase(),
                    style = config.titleStyle!!.textStyle.copy(fontSize = 16.sp),
                    color = config.borderColor.copy(alpha = pulseAlpha),
                    modifier = Modifier.padding(start = 32.dp, top = 32.dp)
                )
            }
        }

        TitlePlacement.TOP_RIGHT -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopEnd
            ) {
                Text(
                    text = config.title.uppercase(),
                    style = (config.titleStyle?.textStyle ?: TextStyle.Default).copy(fontSize = 16.sp),
                    color = config.borderColor.copy(alpha = pulseAlpha),
                    modifier = Modifier.padding(end = 32.dp, top = 32.dp)
                )
            }
        }

        TitlePlacement.BOTTOM_LEFT -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomStart
            ) {
                Text(
                    text = config.title.uppercase(),
                    style = (config.titleStyle?.textStyle ?: TextStyle.Default).copy(fontSize = 16.sp),
                    color = config.borderColor.copy(alpha = pulseAlpha),
                    modifier = Modifier.padding(start = 32.dp, bottom = 100.dp)
                )
            }
        }

        TitlePlacement.BOTTOM_RIGHT -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomEnd
            ) {
                Text(
                    text = config.title.uppercase(),
                    style = (config.titleStyle?.textStyle ?: TextStyle.Default).copy(fontSize = 16.sp),
                    color = config.borderColor.copy(alpha = pulseAlpha),
                    modifier = Modifier.padding(end = 32.dp, bottom = 100.dp)
                )
            }
        }

        TitlePlacement.CUSTOM -> {
            // Custom positioning - can be extended later
        }
    }
}
