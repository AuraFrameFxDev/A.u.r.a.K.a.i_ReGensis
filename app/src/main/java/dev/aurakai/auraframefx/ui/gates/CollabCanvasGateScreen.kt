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
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.aurakai.auraframefx.navigation.ReGenesisRoute
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CollabCanvasGateScreen(navController: NavController, onNavigateBack: () -> Unit = {}) {
    val infiniteTransition = rememberInfiniteTransition(label = "collab")
    val paintSplash by infiniteTransition.animateFloat(
        0f, 1f, infiniteRepeatable(tween(3000, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "paint"
    )
    val eyePulse by infiniteTransition.animateFloat(
        0.6f, 1f, infiniteRepeatable(tween(1400, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "eye"
    )
    val orbitAngle by infiniteTransition.animateFloat(
        0f, 2f * PI.toFloat(),
        infiniteRepeatable(tween(8000, easing = LinearEasing), RepeatMode.Restart),
        label = "orbit"
    )

    Box(modifier = Modifier.fillMaxSize()) {

        // Layer 1: Eye Rune canvas background
        Canvas(modifier = Modifier.fillMaxSize()) {
            val cx = size.width / 2f
            val cy = size.height * 0.4f

            // Black void
            drawRect(Color(0xFF000000))

            // Paint splash
            drawEyeRunePaintSplash(cx, cy, paintSplash, orbitAngle)

            // Circuit board border lines
            val cBorder = Color(0xFFFF2D78).copy(0.15f)
            for (i in 1..4) drawLine(
                cBorder,
                Offset(0f, i * size.height / 5f),
                Offset(size.width * 0.1f, i * size.height / 5f),
                1f
            )
            for (i in 1..4) drawLine(
                cBorder,
                Offset(size.width * 0.9f, i * size.height / 5f),
                Offset(size.width, i * size.height / 5f),
                1f
            )

            // Eye rune symbol
            drawEyeRune(Offset(cx, cy), eyePulse)
        }

        // Layer 2: Content
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = Color(0xFFFF2D78))
                }
                Text(
                    "COLLAB CANVAS", fontFamily = FontFamily.Monospace,
                    fontSize = 18.sp, fontWeight = FontWeight.Bold,
                    letterSpacing = 4.sp, color = Color(0xFFFF2D78)
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { navController.navigate("gate_image_picker") }) {
                    Icon(Icons.Default.SwapHoriz, null, tint = Color(0xFFFF2D78).copy(0.6f))
                }
            }

            Spacer(Modifier.height(250.dp)) // Eye rune space

            // Collaboration tools
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val collabItems = listOf(
                    "Live Collaborative Drawing" to Color(0xFFFF2D78),
                    "Shared UI Mockups" to Color(0xFF00BFFF),
                    "Agent Vision Board" to Color(0xFF9B30FF),
                    "Color Palette Sync" to Color(0xFFFF9B00),
                    "Export & Share" to Color(0xFF00FF80),
                )
                collabItems.forEach { (label, color) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .border(1.dp, color.copy(0.3f), RoundedCornerShape(8.dp))
                            .background(color.copy(0.07f))
                            .clickable {
                                if (label == "Live Collaborative Drawing") {
                                    navController.navigate(ReGenesisRoute.CollabCanvas.route)
                                } else {
                                    navController.navigate("coming_soon")
                                }
                            }
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            label,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White.copy(0.85f)
                        )
                        Text("→", fontSize = 14.sp, color = color)
                    }
                }
            }
        }
    }
}

private fun DrawScope.drawEyeRunePaintSplash(cx: Float, cy: Float, t: Float, angle: Float) {
    drawCircle(
        Color(0xFFFF2D78).copy(alpha = 0.3f + t * 0.2f), radius = 120f,
        center = Offset(cx - 80f, cy - 100f)
    )
    drawCircle(
        Color(0xFF1E90FF).copy(alpha = 0.25f + t * 0.15f), radius = 100f,
        center = Offset(cx + 80f, cy - 80f)
    )
    for (i in 0..5) {
        val px = cx - 100f + i * 30f
        drawLine(
            Color(0xFFFF2D78).copy(alpha = 0.4f),
            Offset(px, cy - 120f), Offset(px + 5f, cy - 60f), strokeWidth = 8f,
            cap = StrokeCap.Round
        )
    }
    val borderColor = Color(0xFFFF2D78).copy(0.4f + t * 0.2f)
    drawRoundRect(
        borderColor, Offset(20f, 20f), Size(size.width - 40f, size.height * 0.7f),
        CornerRadius(8f), style = Stroke(width = 2f)
    )
    for (i in 0..5) {
        val a = angle + i * (2 * PI.toFloat() / 6)
        val r = 140f + i * 10f
        val px = cx + r * cos(a)
        val py = cy + r * sin(a) * 0.5f
        drawCircle(Color(0xFFFF2D78).copy(0.6f), radius = 4f, center = Offset(px, py))
    }
}

private fun DrawScope.drawEyeRune(center: Offset, pulse: Float) {
    val r = 70f
    drawOval(
        Color(0xFF00BFFF).copy(alpha = 0.7f + pulse * 0.2f),
        topLeft = Offset(center.x - r, center.y - r * 0.5f),
        size = Size(r * 2, r), style = Stroke(2.5f)
    )
    drawCircle(Color(0xFFFF2D78).copy(0.5f), radius = r * 0.4f, center = center)
    drawCircle(
        Color(0xFF00BFFF).copy(0.8f + pulse * 0.1f), radius = r * 0.4f,
        center = center, style = Stroke(2f)
    )
    drawCircle(Color(0xFF000000), radius = r * 0.2f, center = center)
    drawCircle(
        Color.White.copy(0.9f), radius = r * 0.08f,
        center = Offset(center.x + r * 0.1f, center.y - r * 0.1f)
    )

    val runeY = center.y + r * 0.7f
    drawLine(
        Color(0xFF00BFFF).copy(0.6f),
        Offset(center.x, runeY), Offset(center.x, runeY + r * 0.5f), 2.5f
    )
    drawLine(
        Color(0xFF00BFFF).copy(0.4f),
        Offset(center.x - r * 0.3f, runeY + r * 0.2f),
        Offset(center.x + r * 0.3f, runeY + r * 0.2f), 2f
    )
    drawCircle(
        Color(0xFF00BFFF).copy(0.3f), radius = r * 0.15f,
        center = Offset(center.x, runeY + r * 0.5f), style = Stroke(1.5f)
    )

    drawCircle(
        Color(0xFF00BFFF).copy(alpha = (1f - pulse) * 0.3f),
        radius = r * 1.4f * pulse, center = center, style = Stroke(1f)
    )
}
