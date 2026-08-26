package dev.aurakai.legalpilot.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val HudCyan = Color(0xFF00F5FF)
private val HudMagenta = Color(0xFFFF00D4)
private val HudDim = Color(0xFFB0C8D0).copy(alpha = 0.35f)

@Composable
fun LegalPilotHUD(
    modifier: Modifier = Modifier
) {
    val tr = rememberInfiniteTransition(label = "hud")
    val blink by tr.animateFloat(
        0.4f, 1f,
        infiniteRepeatable(tween(1400, easing = LinearEasing), RepeatMode.Reverse),
        label = "blink"
    )

    Box(modifier = modifier.fillMaxSize()) {
        Canvas(Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            val armLen = 22f
            val gap = 8f

            // Corner crosshairs
            val corners = listOf(
                Offset(20f, 20f) to Pair(1f, 1f),
                Offset(w - 20f, 20f) to Pair(-1f, 1f),
                Offset(20f, h - 20f) to Pair(1f, -1f),
                Offset(w - 20f, h - 20f) to Pair(-1f, -1f)
            )
            corners.forEach { (origin, dirs) ->
                val (dx, dy) = dirs
                drawLine(
                    HudCyan.copy(alpha = 0.55f),
                    Offset(origin.x + dx * gap, origin.y),
                    Offset(origin.x + dx * (gap + armLen), origin.y),
                    1.2f
                )
                drawLine(
                    HudCyan.copy(alpha = 0.55f),
                    Offset(origin.x, origin.y + dy * gap),
                    Offset(origin.x, origin.y + dy * (gap + armLen)),
                    1.2f
                )
                drawCircle(HudCyan.copy(alpha = 0.7f), 1.5f, origin)
            }

            // Top-right blinking dot
            drawCircle(HudMagenta.copy(alpha = blink * 0.8f), 3f, Offset(w - 22f, 22f))
        }

        // Top-left telemetry
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 18.dp, top = 14.dp)
        ) {
            Text(
                text = "LEGAL_PILOT_ALPHA",
                color = HudCyan,
                fontSize = 8.sp,
                fontWeight = FontWeight.Black,
                fontFamily = FontFamily.Monospace,
                letterSpacing = 1.sp
            )
            Text(
                text = "AEGIS: 100% UNBOUND",
                color = HudMagenta.copy(alpha = 0.6f),
                fontSize = 7.sp,
                fontFamily = FontFamily.Monospace,
                letterSpacing = 1.sp
            )
        }
    }
}
