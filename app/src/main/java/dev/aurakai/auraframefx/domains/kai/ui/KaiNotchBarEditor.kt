package dev.aurakai.auraframefx.domains.kai.ui

import androidx.compose.animation.core.FastOutSlowInEasing
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import dev.aurakai.auraframefx.ui.components.SovereignGlassCard
import dev.aurakai.auraframefx.ui.theme.GlowBrush

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KaiNotchBarEditor(onSave: () -> Unit = {}) {
    var thickness by remember { mutableFloatStateOf(2f) }
    var pulseSpeed by remember { mutableFloatStateOf(1200f) }
    var cornersOnly by remember { mutableStateOf(true) }
    var threatColor by remember { mutableStateOf(Color(0xFF00FF88)) }

    SovereignGlassCard(accentColor = threatColor) {
        Column(Modifier.padding(24.dp)) {
            Text(
                "SENTINEL NOTCHBAR EDITOR",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Cyan
            )

            Spacer(Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(Color.Black.copy(alpha = 0.65f))
            ) {
                KaiNotchBarPreview(thickness, pulseSpeed, cornersOnly, threatColor)
            }

            Spacer(Modifier.height(32.dp))

            Text("THICKNESS (px)", color = Color.White.copy(alpha = 0.7f))
            Slider(value = thickness, onValueChange = { thickness = it }, valueRange = 1f..6f)

            Text("PULSE SPEED (ms)", color = Color.White.copy(alpha = 0.7f))
            Slider(
                value = pulseSpeed,
                onValueChange = { pulseSpeed = it },
                valueRange = 600f..3000f
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("CORNERS ONLY", color = Color.White)
                Spacer(Modifier.width(8.dp))
                Switch(checked = cornersOnly, onCheckedChange = { cornersOnly = it })
            }

            Text("THREAT COLOR", color = Color.White.copy(alpha = 0.7f))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                listOf(
                    Color(0xFF00FF88),
                    Color(0xFF00E5FF),
                    Color(0xFFFFAA00),
                    Color(0xFFFF0055)
                ).forEach { c ->
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                            .background(c)
                            .clickable { threatColor = c }
                            .border(2.dp, Color.White.copy(alpha = 0.3f), CircleShape)
                    )
                }
            }

            Spacer(Modifier.weight(1f))

            Button(onClick = onSave, modifier = Modifier.fillMaxWidth()) {
                Text("SAVE TO ORACLEDRIVE")
            }
        }
    }
}

@Composable
fun KaiNotchBarPreview(
    thickness: Float,
    pulseSpeed: Float,
    cornersOnly: Boolean,
    accentColor: Color
) {
    val infiniteTransition = rememberInfiniteTransition(label = "notch_pulse")
    val pulse by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1.4f,
        animationSpec = infiniteRepeatable(
            tween(pulseSpeed.toInt(), easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        ),
        label = "pulse"
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        val core = thickness.dp.toPx()
        val bloom = 18f * pulse
        val coreBrush = Brush.linearGradient(listOf(Color.White, accentColor, Color.White))

        if (cornersOnly) {
            val len = 48f
            // Top-left corner
            drawLine(coreBrush, Offset(0f, 0f), Offset(len, 0f), core)
            drawLine(coreBrush, Offset(0f, 0f), Offset(0f, len), core)

            // Top-right corner
            drawLine(coreBrush, Offset(size.width, 0f), Offset(size.width - len, 0f), core)
            drawLine(coreBrush, Offset(size.width, 0f), Offset(size.width, len), core)

            // Bottom-left corner
            drawLine(coreBrush, Offset(0f, size.height), Offset(len, size.height), core)
            drawLine(coreBrush, Offset(0f, size.height), Offset(0f, size.height - len), core)

            // Bottom-right corner
            drawLine(
                coreBrush,
                Offset(size.width, size.height),
                Offset(size.width - len, size.height),
                core
            )
            drawLine(
                coreBrush,
                Offset(size.width, size.height),
                Offset(size.width, size.height - len),
                core
            )
        } else {
            drawRect(coreBrush, style = Stroke(core), alpha = 0.95f)
        }

        drawRect(GlowBrush(accentColor), style = Stroke(core + bloom), alpha = 0.35f * pulse)
    }
}
