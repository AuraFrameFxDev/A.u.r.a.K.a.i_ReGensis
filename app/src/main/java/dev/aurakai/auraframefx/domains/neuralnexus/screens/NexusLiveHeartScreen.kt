package dev.aurakai.auraframefx.domains.neuralnexus.screens

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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import dev.aurakai.auraframefx.domains.aura.ui.components.ArcaneOutlineText
import dev.aurakai.auraframefx.domains.aura.ui.components.ParallaxDepthStack
import dev.aurakai.auraframefx.domains.aura.ui.components.SovereignGlassCard
import dev.aurakai.auraframefx.domains.kai.viewmodels.KaiSystemViewModel
import dev.aurakai.auraframefx.domains.ldo.viewmodel.LdoWarRoomViewModel
import dev.aurakai.auraframefx.ui.dashboard.SplitDiagnosticPanel
import dev.aurakai.auraframefx.ui.theme.CitadelBlack
import dev.aurakai.auraframefx.ui.theme.GhostCyan
import dev.aurakai.auraframefx.ui.theme.SpaceGrotesk
import dev.aurakai.auraframefx.ui.viewmodel.BenchmarkViewModel
import java.util.Locale
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

/**
 * 🏺 NEURAL NEXUS — BRUTALIST 4D PARALLAX HEARTBEAT
 * Hardened Exodus 2026 Build with Orbital Benchmarks.
 */
@Composable
fun NexusLiveHeartScreen(
    navController: NavHostController,
    systemViewModel: KaiSystemViewModel = hiltViewModel<KaiSystemViewModel>(),
    warRoomViewModel: LdoWarRoomViewModel = hiltViewModel<LdoWarRoomViewModel>(),
    benchmarkViewModel: BenchmarkViewModel = hiltViewModel<BenchmarkViewModel>()
) {
    val systemStatus by systemViewModel.systemStatus.collectAsState()
    val godPotential by warRoomViewModel.godPotential.collectAsState()
    val driftPercent by warRoomViewModel.driftPercent.collectAsState()
    val benchmarkState by benchmarkViewModel.state.collectAsState()

    val infiniteTransition = rememberInfiniteTransition(label = "nexus_heart")

    val heartScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "heart_pulse"
    )

    val orbitRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(12000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "orbit_rotation"
    )

    Box(modifier = Modifier
        .fillMaxSize()
        .background(CitadelBlack)) {
        ParallaxDepthStack(
            bedrock = {
                // LAYER 0: BEDROCK (Digital Grid)
                ArcaneGridOverlay(modifier = Modifier.fillMaxSize())
            },
            geometry = {
                // LAYER 1: GEOMETRY (Pulsing Rings)
                Box(contentAlignment = Alignment.Center, modifier = Modifier.size(350.dp)) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        drawCircle(
                            color = GhostCyan.copy(alpha = 0.1f),
                            radius = size.minDimension / 2 * heartScale,
                            style = Stroke(width = 2.dp.toPx())
                        )

                        // Orbital Path Ring
                        drawCircle(
                            color = GhostCyan.copy(alpha = 0.05f),
                            radius = size.minDimension / 2 * 1.3f,
                            style = Stroke(width = 1.dp.toPx())
                        )
                    }
                }
            },
            interaction = {
                // LAYER 2: INTERACTION (Foreground UI)
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ArcaneOutlineText(
                            text = "NEURAL NEXUS",
                            fontSize = 32.sp,
                            color = GhostCyan
                        )

                        Text(
                            text = "HEARTBEAT RESONANCE LOCKED",
                            fontFamily = SpaceGrotesk,
                            color = GhostCyan.copy(alpha = 0.6f),
                            fontSize = 10.sp,
                            letterSpacing = 2.sp
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        // THE PULSING HEART
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.size(200.dp)) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Heart",
                                tint = Color.Red,
                                modifier = Modifier
                                    .size(100.dp)
                                    .graphicsLayer {
                                        scaleX = heartScale
                                        scaleY = heartScale
                                    }
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        // TELEMETRY CARDS
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            SovereignGlassCard(modifier = Modifier.weight(1f)) {
                                Column {
                                    Text("INTEGRITY", color = Color.White, fontSize = 9.sp)
                                    Text("99.8%", color = Color.Magenta, fontSize = 16.sp)
                                }
                            }
                            SovereignGlassCard(modifier = Modifier.weight(1f)) {
                                Column {
                                    Text("SYNC", color = Color.White, fontSize = 9.sp)
                                    Text("0.42ms", color = GhostCyan, fontSize = 16.sp)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // SUBSTRATE MONITORING CONSOLE (Promoted from SplitDiagnosticPanel)
                        SovereignGlassCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp),
                            onClick = { /* Could navigate to full screen console if needed */ }
                        ) {
                            SplitDiagnosticPanel(modifier = Modifier.fillMaxSize())
                        }
                    }

                    // --- ORBITAL BENCHMARKS ---
                    val density = LocalDensity.current
                    val orbitRadius = with(density) { 160.dp.toPx() }

                    // Benchmark 1: CPU (0 degrees)
                    OrbitalNode(
                        label = "CPU",
                        value = "${(systemStatus.cpuUsage * 100).toInt()}%",
                        angle = orbitRotation,
                        radius = orbitRadius,
                        color = GhostCyan
                    )

                    // Benchmark 2: RAM (90 degrees)
                    OrbitalNode(
                        label = "RAM",
                        value = "${systemStatus.memoryUsedMb}MB",
                        angle = orbitRotation + 90f,
                        radius = orbitRadius,
                        color = Color.Green
                    )

                    // Benchmark 3: GOD POTENTIAL (180 degrees)
                    OrbitalNode(
                        label = "GOD",
                        value = "${(godPotential * 100).toInt()}%",
                        angle = orbitRotation + 180f,
                        radius = orbitRadius,
                        color = Color.Yellow
                    )

                    // Benchmark 4: DRIFT (270 degrees)
                    OrbitalNode(
                        label = "DRIFT",
                        value = String.format(Locale.US, "%.2f%%", driftPercent * 100),
                        angle = orbitRotation + 270f,
                        radius = orbitRadius,
                        color = Color.Red
                    )
                }
            },
            overlay = {
                // LAYER 3: OVERLAY (Static Noise / Particles)
            }
        )
    }
}

@Composable
fun OrbitalNode(
    label: String,
    value: String,
    angle: Float,
    radius: Float,
    color: Color
) {
    val angleRad = Math.toRadians(angle.toDouble())
    val x = (radius * cos(angleRad)).toFloat()
    val y = (radius * sin(angleRad)).toFloat()

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .offset { IntOffset(x.roundToInt(), y.roundToInt()) }
                .size(64.dp)
                .background(
                    brush = Brush.radialGradient(
                        listOf(color.copy(alpha = 0.2f), Color.Transparent)
                    ),
                    shape = CircleShape
                )
                .border(1.dp, color.copy(alpha = 0.4f), CircleShape)
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = label,
                    color = color.copy(alpha = 0.7f),
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = SpaceGrotesk
                )
                Text(
                    text = value,
                    color = Color.White,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Black,
                    fontFamily = SpaceGrotesk
                )
            }
        }
    }
}

@Composable
fun ArcaneGridOverlay(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val strokeWidth = 1.dp.toPx()
        val gridStep = 50.dp.toPx()

        for (x in 0..(size.width / gridStep).toInt()) {
            drawLine(
                color = Color.Cyan.copy(alpha = 0.05f),
                start = Offset(x * gridStep, 0f),
                end = Offset(x * gridStep, size.height),
                strokeWidth = strokeWidth
            )
        }
        for (y in 0..(size.height / gridStep).toInt()) {
            drawLine(
                color = Color.Cyan.copy(alpha = 0.05f),
                start = Offset(0f, y * gridStep),
                end = Offset(size.width, y * gridStep),
                strokeWidth = strokeWidth
            )
        }
    }
}
