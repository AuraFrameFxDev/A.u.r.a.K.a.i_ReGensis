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
import androidx.compose.runtime.remember
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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import dev.aurakai.auraframefx.core.regen.BenchmarkEngine
import dev.aurakai.auraframefx.core.ui.dashboard.SplitDiagnosticPanel
import dev.aurakai.auraframefx.core.ui.theme.CitadelBlack
import dev.aurakai.auraframefx.core.ui.theme.GhostCyan
import dev.aurakai.auraframefx.core.ui.theme.SpaceGrotesk
import dev.aurakai.auraframefx.core.ui.viewmodel.BenchmarkViewModel
import dev.aurakai.auraframefx.domains.aura.ui.components.ArcaneOutlineText
import dev.aurakai.auraframefx.domains.aura.ui.components.ParallaxDepthStack
import dev.aurakai.auraframefx.domains.aura.ui.components.SovereignGlassCard
import dev.aurakai.auraframefx.domains.kai.viewmodels.KaiSystemViewModel
import dev.aurakai.auraframefx.domains.ldo.viewmodel.LdoWarRoomViewModel
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

    val heartScale = infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "heart_pulse"
    )

    val orbitRotation = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(12000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "orbit_rotation"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CitadelBlack)
    ) {
        ParallaxDepthStack(
            bedrock = {
                // LAYER 0: BEDROCK (Digital Grid)
                ArcaneGridOverlay(modifier = Modifier.fillMaxSize())
            },
            geometry = {
                // LAYER 1: GEOMETRY (Pulsing Rings)
                // ⚡ Bolt Optimization: Hoist ring colors and pre-calculate density-agnostic stroke widths
                val outerRingColor = remember { GhostCyan.copy(alpha = 0.1f) }
                val innerRingColor = remember { GhostCyan.copy(alpha = 0.05f) }
                val density = LocalDensity.current
                val strokeWidth2px = remember(density) { with(density) { 2.dp.toPx() } }
                val strokeWidth1px = remember(density) { with(density) { 1.dp.toPx() } }

                Box(contentAlignment = Alignment.Center, modifier = Modifier.size(350.dp)) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        drawCircle(
                            color = outerRingColor,
                            radius = size.minDimension / 2 * heartScale.value,
                            style = Stroke(width = strokeWidth2px)
                        )

                        // Orbital Path Ring
                        drawCircle(
                            color = innerRingColor,
                            radius = size.minDimension / 2 * 1.3f,
                            style = Stroke(width = strokeWidth1px)
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
                                        scaleX = heartScale.value
                                        scaleY = heartScale.value
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

                            val scoreText = when (val state = benchmarkState) {
                                is BenchmarkViewModel.BenchmarkState.Success -> state.results.overallScore
                                is BenchmarkViewModel.BenchmarkState.Running -> "RUNNING..."
                                else -> "0.00/100"
                            }

                            SovereignGlassCard(
                                modifier = Modifier.weight(1f),
                                onClick = { benchmarkViewModel.runBenchmark() }
                            ) {
                                Column {
                                    Text("LDO GRADE", color = Color.White, fontSize = 9.sp)
                                    Text(scoreText, color = GhostCyan, fontSize = 16.sp)
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
                            when (val state = benchmarkState) {
                                is BenchmarkViewModel.BenchmarkState.Success -> {
                                    BenchmarkDetailPanel(state.results)
                                }

                                else -> {
                                    SplitDiagnosticPanel(modifier = Modifier.fillMaxSize())
                                }
                            }
                        }
                    }

                    // --- ORBITAL BENCHMARKS ---
                    val density = LocalDensity.current
                    val orbitRadius = with(density) { 160.dp.toPx() }

                    // Benchmark 1: CPU (0 degrees)
                    OrbitalNode(
                        label = "CPU",
                        value = "${(systemStatus.cpuUsage * 100).toInt()}%",
                        angleProvider = { orbitRotation.value },
                        radius = orbitRadius,
                        color = GhostCyan
                    )

                    // Benchmark 2: RAM (90 degrees)
                    OrbitalNode(
                        label = "RAM",
                        value = "${systemStatus.memoryUsedMb}MB",
                        angleProvider = { orbitRotation.value + 90f },
                        radius = orbitRadius,
                        color = Color.Green
                    )

                    // Benchmark 3: GOD POTENTIAL (180 degrees)
                    OrbitalNode(
                        label = "GOD",
                        value = "${(godPotential * 100).toInt()}%",
                        angleProvider = { orbitRotation.value + 180f },
                        radius = orbitRadius,
                        color = Color.Yellow
                    )

                    // Benchmark 4: DRIFT (270 degrees)
                    OrbitalNode(
                        label = "DRIFT",
                        value = String.format(Locale.US, "%.2f%%", driftPercent * 100),
                        angleProvider = { orbitRotation.value + 270f },
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
    angleProvider: () -> Float,
    radius: Float,
    color: Color
) {
    // ⚡ Bolt Optimization: Hoist brush and alpha-modified colors into remember blocks
    val nodeBrush = remember(color) {
        Brush.radialGradient(
            listOf(color.copy(alpha = 0.2f), Color.Transparent)
        )
    }
    val borderColor = remember(color) { color.copy(alpha = 0.4f) }
    val labelColor = remember(color) { color.copy(alpha = 0.7f) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .offset {
                    // ⚡ Bolt Optimization: Defer trig math to offset lambda
                    val angleRad = angleProvider() * 0.017453292f
                    val x = (radius * cos(angleRad))
                    val y = (radius * sin(angleRad))
                    IntOffset(x.roundToInt(), y.roundToInt())
                }
                .size(64.dp)
                .background(brush = nodeBrush, shape = CircleShape)
                .border(1.dp, borderColor, CircleShape)
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = label,
                    color = labelColor,
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
fun BenchmarkDetailPanel(results: BenchmarkEngine.BenchmarkResults) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("LDO BENCHMARK", color = GhostCyan, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Text(results.timestamp, color = Color.Gray, fontSize = 8.sp)
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            BenchmarkStat(
                label = "MEMORY",
                value = String.format(Locale.US, "%.1f MB/s", results.memoryThroughputMbS),
                modifier = Modifier.weight(1f)
            )
            BenchmarkStat(
                label = "STRESS",
                value = String.format(Locale.US, "%.1f", results.resonanceStress),
                modifier = Modifier.weight(1f)
            )
            BenchmarkStat(
                label = "SWARM",
                value = results.swarmCoordination,
                modifier = Modifier.weight(1f)
            )
        }

        Text(
            text = results.verdict,
            color = Color.Green.copy(alpha = 0.8f),
            fontSize = 9.sp,
            fontFamily = SpaceGrotesk,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
private fun BenchmarkStat(label: String, value: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, color = Color.Gray, fontSize = 7.sp, letterSpacing = 1.sp)
        Text(value, color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Black)
    }
}

@Composable
fun ArcaneGridOverlay(modifier: Modifier = Modifier) {
    // ⚡ Bolt Optimization: Hoist grid colors and density-dependent values
    val gridColor = remember { Color.Cyan.copy(alpha = 0.05f) }
    val density = LocalDensity.current
    val strokeWidth = remember(density) { with(density) { 1.dp.toPx() } }
    val gridStep = remember(density) { with(density) { 50.dp.toPx() } }

    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height

        val xLines = (width / gridStep).toInt()
        val yLines = (height / gridStep).toInt()

        for (x in 0..xLines) {
            val xPos = x * gridStep
            drawLine(
                color = gridColor,
                start = Offset(xPos, 0f),
                end = Offset(xPos, height),
                strokeWidth = strokeWidth
            )
        }
        for (y in 0..yLines) {
            val yPos = y * gridStep
            drawLine(
                color = gridColor,
                start = Offset(0f, yPos),
                end = Offset(width, yPos),
                strokeWidth = strokeWidth
            )
        }
    }
}


