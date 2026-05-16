package dev.aurakai.auraframefx.domains.aura

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import dev.aurakai.auraframefx.domains.aura.ui.theme.LEDFontFamily
import kotlin.math.PI
import kotlin.math.sin

/**
 * 🧪 CHROMA FORGE — Creative Trinity: ChromaCore + Chronokinetic Engine + Spellhook
 * Ported from AuraStudioLab for the Exodus 2026 Build.
 */

private val NeonMagenta = Color(0xFFFF00FF)
private val NeonCyan = Color(0xFF00E5FF)
private val NeonPurple = Color(0xFFB026FF)
private val VoidBgLab = Color(0xFF050505)

@Composable
fun ChromaForgeScreen(navController: NavHostController) {
    val infiniteTransition = rememberInfiniteTransition(label = "chroma_forge")

    val ringRotation by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(10000, easing = LinearEasing)),
        label = "ring"
    )

    val corePulse by infiniteTransition.animateFloat(
        initialValue = 0.9f, targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            tween(4000, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        ),
        label = "core_pulse"
    )

    val coreTime by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = (2 * PI).toFloat(),
        animationSpec = infiniteRepeatable(tween(3000, easing = LinearEasing)),
        label = "core_time"
    )

    var luminance by remember { mutableFloatStateOf(0.84f) }
    var chroma by remember { mutableFloatStateOf(1.0f) }

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(VoidBgLab)
    ) {

        // Parallax spine
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxHeight()
                .width(2.dp)
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.Transparent,
                            NeonMagenta.copy(alpha = 0.3f),
                            NeonCyan.copy(alpha = 0.3f),
                            Color.Transparent
                        )
                    )
                )
        )

        Column(modifier = Modifier.fillMaxSize()) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(VoidBgLab.copy(alpha = 0.85f))
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "CHROMA FORGE",
                    fontFamily = LEDFontFamily,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp,
                    color = Color.White
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // REACTOR CORE
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(
                        modifier = Modifier
                            .size(256.dp)
                            .graphicsLayer { rotationZ = ringRotation }) {
                        drawCircle(
                            NeonCyan.copy(alpha = 0.25f),
                            radius = size.minDimension / 2 - 2f,
                            style = Stroke(
                                1f,
                                pathEffect = PathEffect.dashPathEffect(floatArrayOf(4f, 4f))
                            )
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(160.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.radialGradient(
                                    listOf(
                                        NeonPurple.copy(alpha = 0.8f),
                                        Color.Transparent
                                    )
                                )
                            )
                            .graphicsLayer { scaleX = corePulse; scaleY = corePulse }
                    ) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            drawReactorCore(coreTime)
                        }
                    }
                }

                // CONTROLS
                Text("COLOR PHYSICS", color = Color.White.copy(alpha = 0.5f), letterSpacing = 2.sp)

                ColorControlSlider("LUMINANCE", luminance) { luminance = it }
                ColorControlSlider("CHROMA", chroma) { chroma = it }

                // SPELLHOOK PREVIEW
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.Black),
                    border = BorderStroke(1.dp, NeonPurple.copy(alpha = 0.3f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("ARMAMENT: SPELLHOOK v2.7", color = Color.White, fontSize = 12.sp)
                        Spacer(Modifier.height(12.dp))
                        Canvas(modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)) {
                            drawLine(
                                brush = Brush.linearGradient(listOf(NeonMagenta, NeonCyan)),
                                start = Offset(0f, size.height),
                                end = Offset(size.width, 0f),
                                strokeWidth = 4f,
                                cap = StrokeCap.Round
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ColorControlSlider(label: String, value: Float, onValueChange: (Float) -> Unit) {
    Column {
        Text(label, color = Color.Gray, fontSize = 10.sp)
        Slider(
            value = value,
            onValueChange = onValueChange,
            colors = SliderDefaults.colors(thumbColor = NeonCyan, activeTrackColor = NeonCyan)
        )
    }
}

private fun DrawScope.drawReactorCore(time: Float) {
    val cx = size.width / 2
    val cy = size.height / 2
    repeat(5) { i ->
        val angle = time + (i * PI / 2.5f).toFloat()
        val r = 40f + sin(time * 0.5f) * 10f
        drawArc(
            if (i % 2 == 0) NeonMagenta else NeonCyan,
            Math.toDegrees(angle.toDouble()).toFloat(),
            216f,
            false,
            Offset(cx - r, cy - r),
            androidx.compose.ui.geometry.Size(r * 2, r * 2),
            style = Stroke(1f)
        )
    }
}
