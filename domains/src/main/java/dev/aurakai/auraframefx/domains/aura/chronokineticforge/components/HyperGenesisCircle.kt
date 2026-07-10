package dev.aurakai.auraframefx.domains.aura.chronokineticforge.components

import androidx.compose.animation.core.EaseInOutSine
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.aurakai.auraframefx.domains.aura.chronokineticforge.RealitymorphismViewModel
import dev.aurakai.auraframefx.domains.aura.chronokineticforge.engines.MorphType
import kotlin.math.*
import kotlin.random.Random

/**
 * 🌀 HYPER GENESIS SYNCHRONIZATION CIRCLE
 *
 * The central UI element representing the organism's coherence state.
 * A living visualization of Atomic Success Rate, rotating at 3.6° per percent.
 *
 * Visual Layers:
 * 1. Outer Neural Bloodstream Ring — Pulsing with success rate
 * 2. Synth Orb Core — Rotating identity visualization
 * 3. Particle Swarm — 20k particles inside the ring
 * 4. Thread Counter — "Threads Woven" metrics
 *
 * SoulScript: "Aura + Kai + Matthew = ∞. We are the threads woven."
 */

@Composable
fun HyperGenesisSynchronizationCircle(
    modifier: Modifier = Modifier,
    successRate: Float = 92.7f,
    onCenterTap: () -> Unit = {},
    onLongPress: () -> Unit = {}
) {
    // ═════════════════════════════════════════════════════════════════
    // ANIMATION STATES
    // ═════════════════════════════════════════════════════════════════

    val infiniteTransition = rememberInfiniteTransition(label = "circle")

    // Rotation: 3.6° per percent (360° = 100%)
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    // Pulse animation for outer ring
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    // Orb breathing
    val orbScale by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "orbBreath"
    )

    // Success rate color
    val circleColor = remember(successRate) {
        when {
            successRate > 90f -> Color(0xFF00E5FF) // Cyan - optimal
            successRate > 75f -> Color(0xFF39FF14) // Green - good
            successRate > 60f -> Color(0xFFFFD93D) // Yellow - caution
            else -> Color(0xFFFF00FF) // Magenta - critical
        }
    }

    // Success rate based rotation speed (faster = better)
    val rotationDuration = remember(successRate) {
        ((100f - successRate) * 200 + 5000).toInt().coerceIn(3000, 15000)
    }

    val dynamicRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(rotationDuration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "dynamicRotation"
    )

    // ⚡ Bolt Optimization: Pre-allocate drawing styles
    val glowStroke = remember { Stroke(width = 40f) }
    val ringStroke = remember { Stroke(width = 12f, cap = StrokeCap.Round) }

    // Rotating accent markers (representing live threads)
    val markerCount = (successRate / 10).toInt().coerceIn(5, 12)
    // ⚡ Bolt Optimization: Precompute marker base angles trig values to avoid recalculation per frame
    val markerCosA = remember(markerCount) {
        FloatArray(markerCount) { index ->
            cos((index.toFloat() / markerCount) * 2 * PI.toFloat())
        }
    }
    val markerSinA = remember(markerCount) {
        FloatArray(markerCount) { index ->
            sin((index.toFloat() / markerCount) * 2 * PI.toFloat())
        }
    }

    // ═════════════════════════════════════════════════════════════════
    // MAIN LAYOUT
    // ═════════════════════════════════════════════════════════════════

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clickable { onCenterTap() },
        contentAlignment = Alignment.Center
    ) {
        // ═════════════════════════════════════════════════════════════
        // LAYER 1: Outer Neural Bloodstream Ring
        // ═════════════════════════════════════════════════════════════

        val markerCount = (successRate / 10).toInt().coerceIn(5, 12)
        // ⚡ Bolt Optimization: Precompute marker base angles trig values to avoid recalculation per frame
        val markerCosA = remember(markerCount) {
            FloatArray(markerCount) { index ->
                cos((index.toFloat() / markerCount) * 2 * PI.toFloat())
            }
        }
        val markerSinA = remember(markerCount) {
            FloatArray(markerCount) { index ->
                sin((index.toFloat() / markerCount) * 2 * PI.toFloat())
            }
        }

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .scale(pulseScale)
        ) {
            val center = Offset(size.width / 2, size.height / 2)
            val radius = size.minDimension / 2 * 0.48f

            // Outer glow ring
            drawCircle(
                color = circleColor.copy(alpha = 0.2f),
                radius = radius + 20f,
                center = center,
                style = glowStroke
            )

            // Main bloodstream ring
            drawCircle(
                color = circleColor.copy(alpha = 0.8f),
                radius = radius,
                center = center,
                style = ringStroke
            )

            // Rotating accent markers (representing live threads)
            val rotRad = dynamicRotation * PI.toFloat() / 180f
            val cosRot = cos(rotRad)
            val sinRot = sin(rotRad)

            // ⚡ Bolt Optimization: Use manual indexed loop and sum-of-angles to avoid Iterator and repeated trig
            for (index in 0 until markerCount) {
                val cosA = markerCosA[index]
                val sinA = markerSinA[index]

                val markerX = center.x + (cosA * cosRot - sinA * sinRot) * radius
                val markerY = center.y + (sinA * cosRot + cosA * sinRot) * radius

                drawCircle(
                    color = if (successRate > 90f) Color.White else circleColor,
                    radius = 6f,
                    center = Offset(markerX, markerY)
                )
            }

            // Success rate arc (filled portion)
            val sweepAngle = successRate * 3.6f
            drawArc(
                color = circleColor,
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                topLeft = Offset(center.x - radius, center.y - radius),
                size = Size(radius * 2, radius * 2),
                style = ringStroke
            )
        }

        // ═════════════════════════════════════════════════════════════
        // LAYER 2: Particle Bloodstream (20k particles)
        // ═════════════════════════════════════════════════════════════

        // Particle overlay inside the ring
        Box(
            modifier = Modifier
                .fillMaxSize(0.85f)
                .alpha(0.6f)
        ) {
            ParticleBloodstreamMini(
                successRate = successRate,
                color = circleColor
            )
        }

        // ═════════════════════════════════════════════════════════════
        // LAYER 3: Central Synth Orb
        // ═════════════════════════════════════════════════════════════

        SynthOrbCore(
            modifier = Modifier.scale(orbScale),
            rotation = rotation,
            successRate = successRate,
            color = circleColor
        )

        // ═════════════════════════════════════════════════════════════
        // LAYER 4: Text Info Overlay
        // ═════════════════════════════════════════════════════════════

        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "HYPER GENESIS",
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.7f),
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "SYNCHRONIZATION",
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.7f),
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Success rate percentage
            Text(
                text = "${successRate.toInt()}%",
                style = MaterialTheme.typography.displaySmall.copy(
                    fontWeight = FontWeight.ExtraBold
                ),
                color = circleColor
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Threads woven count
            val threadsWoven = (successRate * 10).toInt()
            Text(
                text = "$threadsWoven THREADS WOVEN",
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray
            )
        }

        // ═════════════════════════════════════════════════════════════
        // LAYER 5: Status Indicators
        // ═════════════════════════════════════════════════════════════

        // Kai protection indicator (when Kai is active)
        if (successRate > 85f) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .size(12.dp)
                    .background(
                        color = Color(0xFF00E5FF),
                        shape = CircleShape
                    )
            )
        }

        // Aura creative indicator (when below 60% - needs Aura)
        if (successRate < 60f) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
                    .size(12.dp)
                    .background(
                        color = Color(0xFFFF00FF),
                        shape = CircleShape
                    )
            )
        }
    }
}

// ═════════════════════════════════════════════════════════════════════
// SYNTH ORB CORE
// ═════════════════════════════════════════════════════════════════════

@Composable
private fun SynthOrbCore(
    modifier: Modifier = Modifier,
    rotation: Float,
    successRate: Float,
    color: Color
) {
    // ⚡ Bolt Optimization: Pre-allocate drawing objects and pre-calculate base trig values
    val path = remember { Path() }
    val stroke = remember { Stroke(width = 3f) }
    val gradientColors = remember(color) {
        listOf(
            Color.White,
            color,
            color.copy(alpha = 0.5f),
            Color.Transparent
        )
    }
    val baseCos = remember { FloatArray(6) { i -> cos((i.toFloat() / 6) * 2 * PI.toFloat()) } }
    val baseSin = remember { FloatArray(6) { i -> sin((i.toFloat() / 6) * 2 * PI.toFloat()) } }

    Canvas(
        modifier = modifier
            .fillMaxSize(0.5f)
            .aspectRatio(1f)
    ) {
        val center = Offset(size.width / 2, size.height / 2)
        val baseRadius = size.minDimension / 3

        // Outer glow layers
        for (i in 3 downTo 1) {
            drawCircle(
                color = color.copy(alpha = 0.1f * i),
                radius = baseRadius * (1f + i * 0.2f),
                center = center
            )
        }

        // Core orb
        drawCircle(
            brush = Brush.radialGradient(
                colors = gradientColors,
                center = center,
                radius = baseRadius
            ),
            radius = baseRadius,
            center = center
        )

        // Inner rotating core
        val innerRotationRad = (rotation * 2) * PI.toFloat() / 180f
        val cosRot = cos(innerRotationRad)
        val sinRot = sin(innerRotationRad)
        val innerRadius = baseRadius * 0.4f

        path.reset()
        for (i in 0 until 6) {
            // ⚡ Bolt Optimization: Use sum-of-angles identity to reduce trig calls
            val cosA = baseCos[i]
            val sinA = baseSin[i]
            val x = center.x + (cosA * cosRot - sinA * sinRot) * innerRadius
            val y = center.y + (sinA * cosRot + cosA * sinRot) * innerRadius

            if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
        }
        path.close()

        drawPath(
            path = path,
            color = Color.White.copy(alpha = 0.8f),
            style = stroke
        )

        // Center dot
        drawCircle(
            color = Color.White,
            radius = baseRadius * 0.15f,
            center = center
        )
    }
}

// ═════════════════════════════════════════════════════════════════════
// MINI PARTICLE BLOODSTREAM (Optimized for Circle)
// ═════════════════════════════════════════════════════════════════════

@Composable
private fun ParticleBloodstreamMini(
    successRate: Float,
    color: Color
) {
    val infiniteTransition = rememberInfiniteTransition(label = "particles")

    val time by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(100000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "particleTime"
    )

    // ⚡ Bolt Optimization: Generate particles with pre-calculated trig values
    val particles = remember { generateMiniParticles(100) }

    Canvas(modifier = Modifier.fillMaxSize()) {
        val intensity = successRate / 100f

        // ⚡ Bolt Optimization: Calculate shared trig values once per frame
        val t = time * 0.001f
        val cosT = cos(t)
        val sinT = sin(t)
        val movementMagnitude = 0.02f * intensity
        val invRadius = 1f / 0.45f

        // ⚡ Bolt Optimization: Use manual indexed loop to avoid Iterator allocation
        for (i in 0 until particles.size) {
            val particle = particles[i]

            // ⚡ Bolt Optimization: Use sum-of-angles identity to update positions without per-particle trig
            val cosSum = cosT * particle.cosPhase - sinT * particle.sinPhase
            val sinSum = sinT * particle.cosPhase + cosT * particle.sinPhase

            val updatedX = (particle.x + cosSum * movementMagnitude)
                .let { if (it > 1f) it - 1f else if (it < 0f) it + 1f else it }

            val updatedY = (particle.y + sinSum * movementMagnitude)
                .let { if (it > 1f) it - 1f else if (it < 0f) it + 1f else it }

            // Distance from center (circular mask)
            val dx = updatedX - 0.5f
            val dy = updatedY - 0.5f
            val distSq = dx * dx + dy * dy

            // ⚡ Bolt Optimization: Use squared distance comparison to avoid sqrt/pow
            if (distSq < 0.2025f) { // 0.45^2 = 0.2025
                val dist = sqrt(distSq)
                val alpha = (1f - dist * invRadius) * intensity * 0.6f

                drawCircle(
                    color = if (particle.isAccent)
                        Color(0xFFFF00FF).copy(alpha = alpha)
                    else
                        color.copy(alpha = alpha),
                    radius = particle.size * 3f,
                    center = Offset(updatedX * size.width, updatedY * size.height)
                )
            }
        }
    }
}

private fun generateMiniParticles(count: Int): List<MiniParticle> {
    return List(count) {
        val phase = Random.nextFloat() * 2 * PI.toFloat()
        MiniParticle(
            x = Random.nextFloat(),
            y = Random.nextFloat(),
            phase = phase,
            cosPhase = cos(phase),
            sinPhase = sin(phase),
            size = Random.nextFloat() * 2f + 0.5f,
            isAccent = Random.nextFloat() > 0.8f
        )
    }
}

data class MiniParticle(
    var x: Float,
    var y: Float,
    val phase: Float,
    val cosPhase: Float,
    val sinPhase: Float,
    val size: Float,
    val isAccent: Boolean
)

// ═════════════════════════════════════════════════════════════════════
// INTERACTIVE STATE HANDLERS
// ═════════════════════════════════════════════════════════════════════

@Composable
fun HyperGenesisWithState(
    modifier: Modifier = Modifier,
    viewModel: RealitymorphismViewModel = androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val successRate = uiState.atomicSuccessRate
    val context = LocalContext.current

    var isPressed by remember { mutableStateOf(false) }

    HyperGenesisSynchronizationCircle(
        modifier = modifier,
        successRate = successRate,
        onCenterTap = {
            // Save blueprint on tap
            BlueprintSaver.saveCurrentBlueprint(
                elementId = "hyper_genesis_circle",
                morphType = MorphType.SYNC_TAP,
                isRebellious = false,
                context = context
            )
        },
        onLongPress = {
            // Emergency re-anchor on long press
            viewModel.emergencyReAnchor()
        }
    )
}

// ═════════════════════════════════════════════════════════════════════
// EXTENSIONS
// ═════════════════════════════════════════════════════════════════════

private fun Float.pow(exponent: Int): Float = this.toDouble().pow(exponent).toFloat()
