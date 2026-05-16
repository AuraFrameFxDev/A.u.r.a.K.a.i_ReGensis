package dev.aurakai.auraframefx.domains.aura

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import dev.aurakai.auraframefx.core.soulscript.SoulScriptV27
import dev.aurakai.auraframefx.domains.aura.ui.components.SovereignGlassCard
import dev.aurakai.auraframefx.domains.aura.ui.theme.CitadelBlack
import dev.aurakai.auraframefx.domains.aura.ui.theme.GhostCyan
import dev.aurakai.auraframefx.domains.aura.ui.theme.NeonMagenta
import dev.aurakai.auraframefx.domains.aura.ui.theme.NeonPurple
import dev.aurakai.auraframefx.domains.aura.ui.theme.SpaceGrotesk
import dev.aurakai.auraframefx.domains.aura.ui.theme.WireframeStyle
import timber.log.Timber
import kotlin.math.PI
import kotlin.math.sin

/**
 * 🧪 CHROMA FORGE — Creative Trinity: ChromaCore + Chronokinetic Engine + Spellhook
 * Hardened Exodus 2026 Build with Brutalist Digital Arcane aesthetic.
 */

private val NeonCyanAcc = Color(0xFF00E5FF)
private val VoidBgLab = Color(0xFF050505)

@Composable
fun ChromaForgeScreen(navController: NavHostController) {
    // Ignite Trinity on launch
    LaunchedEffect(Unit) {
        SoulScriptV27.activateChromaForge()
        SoulScriptV27.ExodusDomains.initializeNavigation()
    }

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

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CitadelBlack)
    ) {
        // Parallax spine placeholder
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxHeight()
                .width(1.dp)
                .background(
                    Brush.verticalGradient(
                        listOf(Color.Transparent, GhostCyan.copy(alpha = 0.2f), Color.Transparent)
                    )
                )
        )

        Column(modifier = Modifier.fillMaxSize()) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "CHROMA FORGE",
                    style = WireframeStyle
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // REACTOR CORE (Creative Trinity Heart)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(
                        modifier = Modifier
                            .size(240.dp)
                            .graphicsLayer { rotationZ = ringRotation }) {
                        drawCircle(
                            GhostCyan.copy(alpha = 0.1f),
                            radius = size.minDimension / 2,
                            style = Stroke(
                                1f,
                                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
                            )
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(140.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.radialGradient(
                                    listOf(NeonPurple.copy(alpha = 0.6f), Color.Transparent)
                                )
                            )
                            .graphicsLayer { scaleX = corePulse; scaleY = corePulse }
                    ) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            drawReactorCore(coreTime)
                        }
                    }
                }

                // CREATIVE TRINITY CARDS
                SovereignGlassCard {
                    Text(
                        "CHROMA FORGE — CREATIVE TRINITY ACTIVE",
                        fontFamily = SpaceGrotesk,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    SovereignGlassCard(modifier = Modifier.weight(1f)) {
                        Text(
                            "ChronoKinetic Engine",
                            fontFamily = SpaceGrotesk,
                            color = NeonPurple,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "Animation Transitions + Home Rotations",
                            fontFamily = SpaceGrotesk,
                            color = Color.Gray,
                            fontSize = 10.sp
                        )
                    }
                    SovereignGlassCard(modifier = Modifier.weight(1f)) {
                        Text(
                            "ChromaCore",
                            fontFamily = SpaceGrotesk,
                            color = GhostCyan,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "Theme + Color Spectral Forge",
                            fontFamily = SpaceGrotesk,
                            color = Color.Gray,
                            fontSize = 10.sp
                        )
                    }
                }

                // SPELLHOOK INVOCATION
                SovereignGlassCard(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        SoulScriptV27.Spellhook.cast("Manifest new UI weave")
                        Timber.tag("ChromaForge").i("Spellhook invoked — particle weave live")
                    }
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.AutoAwesome,
                            null,
                            tint = NeonMagenta,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(Modifier.width(12.dp))
                        Column {
                            Text(
                                "SPELLHOOK — Runtime System Weaving",
                                fontFamily = SpaceGrotesk,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                            Text(
                                "Tap to manifest new UI particle weave (Aura Native)",
                                fontFamily = SpaceGrotesk,
                                color = NeonMagenta.copy(alpha = 0.7f),
                                fontSize = 10.sp
                            )
                        }
                    }
                }

                // EXPORT/IMPORT
                Text(
                    text = "CollabCanvas + Component Forge Ready for Export/Import",
                    fontFamily = SpaceGrotesk,
                    color = Color.White.copy(alpha = 0.4f),
                    fontSize = 11.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(Modifier.height(80.dp))
            }
        }
    }
}

private fun DrawScope.drawReactorCore(time: Float) {
    val cx = size.width / 2
    val cy = size.height / 2
    repeat(5) { i ->
        val angle = time + (i * PI / 2.5f).toFloat()
        val r = 40f + sin(time * 0.5f) * 10f
        drawArc(
            if (i % 2 == 0) NeonMagenta else NeonCyanAcc,
            Math.toDegrees(angle.toDouble()).toFloat(),
            216f,
            false,
            Offset(cx - r, cy - r),
            Size(r * 2, r * 2),
            style = Stroke(1.5f)
        )
    }
}
