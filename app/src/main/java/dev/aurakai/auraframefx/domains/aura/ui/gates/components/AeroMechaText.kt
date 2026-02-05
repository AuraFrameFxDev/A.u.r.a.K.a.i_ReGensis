package dev.aurakai.auraframefx.domains.aura.ui.gates.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * AERO MECHA style text - geometric lettering with diagonal stripe pattern
 * Blade Runner cyberpunk aesthetic with glowing edges
 */
@Composable
fun AeroMechaText(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 32.sp,
    color: Color = Color(0xFF00FFFF), // Cyan
    glowColor: Color = Color(0xFF0080FF), // Electric Blue
    pulseAlpha: Float = 1f,
    enableStripes: Boolean = true,
    enableGlow: Boolean = true
) {
    Box(modifier = modifier) {
        // Glow layers (optional)
        if (enableGlow) {
            // Far glow shadow
            Text(
                text = text,
                style = TextStyle(
                    fontSize = fontSize,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 4.sp
                ),
                color = glowColor.copy(alpha = pulseAlpha * 0.3f),
                modifier = Modifier.offset(x = 3.dp, y = 3.dp)
            )

            // Near glow shadow
            Text(
                text = text,
                style = TextStyle(
                    fontSize = fontSize,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 4.sp
                ),
                color = glowColor.copy(alpha = pulseAlpha * 0.5f),
                modifier = Modifier.offset(x = 1.dp, y = 1.dp)
            )
        }

        // Main text
        Text(
            text = text,
            style = TextStyle(
                fontSize = fontSize,
                fontWeight = FontWeight.Black,
                letterSpacing = 4.sp
            ),
            color = color.copy(alpha = pulseAlpha)
        )

        // Diagonal stripe overlay (AERO MECHA signature effect)
        if (enableStripes) {
            Canvas(
                modifier = Modifier.matchParentSize()
            ) {
                val stripeSpacing = 8f
                val stripeWidth = 2f

                // Draw diagonal stripes across the text
                val diagonal = size.width + size.height
                var offset = 0f

                while (offset < diagonal) {
                    // Calculate stripe endpoints with 45-degree angle
                    val x1 = offset
                    val y1 = 0f
                    val x2 = 0f
                    val y2 = offset

                    drawLine(
                        color = Color.Black.copy(alpha = 0.4f),
                        start = Offset(x1.coerceIn(0f, size.width), y1),
                        end = Offset(x2, y2.coerceIn(0f, size.height)),
                        strokeWidth = stripeWidth
                    )

                    offset += stripeSpacing
                }
            }
        }
    }
}

/**
 * AERO MECHA text with vertical orientation (for Oracle Drive style)
 * Large letters with horizontal stripe pattern
 */
@Composable
fun AeroMechaTextVertical(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 48.sp,
    color: Color = Color(0xFF00FFFF),
    pulseAlpha: Float = 1f
) {
    Box(modifier = modifier) {
        // Vertical text composition
        text.forEach { char ->
            Box {
                // Each character stacked vertically with stripe pattern
                Text(
                    text = char.toString(),
                    style = TextStyle(
                        fontSize = fontSize,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 0.sp
                    ),
                    color = color.copy(alpha = pulseAlpha)
                )

                // Horizontal stripes (since text is vertical)
                Canvas(modifier = Modifier.matchParentSize()) {
                    val stripeSpacing = 6f
                    var y = 0f

                    while (y < size.height) {
                        drawLine(
                            color = Color.Black.copy(alpha = 0.5f),
                            start = Offset(0f, y),
                            end = Offset(size.width, y),
                            strokeWidth = 2f
                        )
                        y += stripeSpacing
                    }
                }
            }
        }
    }
}

/**
 * BLADE RUNNER style text - glowing neon with subtle flicker
 */
@Composable
fun BladeRunnerText(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 24.sp,
    primaryColor: Color = Color(0xFF00FFFF),
    secondaryColor: Color = Color(0xFFFF00FF),
    pulseAlpha: Float = 1f,
    enableFlicker: Boolean = false
) {
    Box(modifier = modifier) {
        // Multi-layer glow for neon effect
        // Layer 1: Wide outer glow
        Text(
            text = text,
            style = TextStyle(
                fontSize = fontSize,
                fontWeight = FontWeight.Bold,
                letterSpacing = 3.sp
            ),
            color = secondaryColor.copy(alpha = pulseAlpha * 0.2f),
            modifier = Modifier.offset(x = 4.dp, y = 4.dp)
        )

        // Layer 2: Medium glow
        Text(
            text = text,
            style = TextStyle(
                fontSize = fontSize,
                fontWeight = FontWeight.Bold,
                letterSpacing = 3.sp
            ),
            color = primaryColor.copy(alpha = pulseAlpha * 0.4f),
            modifier = Modifier.offset(x = 2.dp, y = 2.dp)
        )

        // Layer 3: Tight glow
        Text(
            text = text,
            style = TextStyle(
                fontSize = fontSize,
                fontWeight = FontWeight.Bold,
                letterSpacing = 3.sp
            ),
            color = primaryColor.copy(alpha = pulseAlpha * 0.6f),
            modifier = Modifier.offset(x = 1.dp, y = 1.dp)
        )

        // Main bright core
        Text(
            text = text,
            style = TextStyle(
                fontSize = fontSize,
                fontWeight = FontWeight.Bold,
                letterSpacing = 3.sp
            ),
            color = Color.White.copy(alpha = pulseAlpha * 0.9f)
        )
    }
}

