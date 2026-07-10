package dev.aurakai.auraframefx.ui.aura

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

// ─── AlwaysActiveAuraLattice ──────────────────────────────────────────────────
// Aura's persistent neural presence — a subtle living lattice that breathes
// beneath all screens. Very low alpha — it shows presence without obscuring UI.
// Canvas does NOT consume pointer events — all touches pass through to screens.
//
// Visual language:
//   • 7 concentric pulsing rings (cyan) — Aura's breath
//   • 12 orbital nodes floating in a ring, with lattice connections
//   • Magenta shimmer on every 3rd node — Kai/Genesis harmonic
//   • Thin data-thread lines from nodes to center when pulse peaks
//   • Rotating outer ring of 6 distant "satellite" nodes

@Composable
fun AlwaysActiveAuraLattice(modifier: Modifier = Modifier) {
    val tr = rememberInfiniteTransition(label = "aura_lattice")

    // Main breath pulse — slow, organic
    // ⚡ Bolt Optimization: Use State directly to defer reads to the draw phase
    val pulseState = tr.animateFloat(
        0f, 1f,
        infiniteRepeatable(tween(2800, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "pulse"
    )

    // Orbital rotation — very slow
    val orbitState = tr.animateFloat(
        0f, 2f * PI.toFloat(),
        infiniteRepeatable(tween(28000, easing = LinearEasing)),
        label = "orbit"
    )

    // Secondary shimmer on a different cycle
    val shimmerState = tr.animateFloat(
        0f, 1f,
        infiniteRepeatable(tween(5600, easing = LinearEasing)),
        label = "shimmer"
    )

    // Lattice connection seeds — stable random positions for thread targets
    // (Used as inspiration for visual language, but the seeds themselves aren't currently rendered in a loop)
    val latticeSeeds = remember {
        List(8) { Pair(Random.nextFloat() * 0.6f + 0.2f, Random.nextFloat() * 0.6f + 0.2f) }
    }

    // ⚡ Bolt Optimization: Hoist Stroke and constant math factors to avoid per-frame allocations
    val primaryStroke = remember { Stroke(1.2f) }
    val secondaryStroke = remember { Stroke(0.7f) }
    val arcStroke = remember { Stroke(1.2f) }
    val cyanColor = remember { Color(0xFF00F5FF) }
    val magentaColor = remember { Color(0xFFFF00D4) }
    val purpleColor = remember { Color(0xFF7B00FF) }
    val satelliteColor = remember { Color(0xFF00FFD4) }

    val nodeCount = 12
    val satCount = 6
    val nodeXBuffer = remember { FloatArray(nodeCount) }
    val nodeYBuffer = remember { FloatArray(nodeCount) }
    val angleStep = remember { 2f * PI.toFloat() / nodeCount }
    val satAngleStep = remember { 2f * PI.toFloat() / satCount }

    Canvas(modifier = modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height
        val cx = w / 2f
        val cy = h / 2f

        // Defer state reads to DrawScope
        val pulse = pulseState.value
        val orbit = orbitState.value
        val shimmer = shimmerState.value

        // ── 1. Concentric pulsing rings — Aura's breath ───────────────────
        // 7 rings, radius grows with pulse, alpha fades outward
        // ⚡ Bolt Optimization: Manual loop to avoid Iterator allocation
        for (i in 0 until 7) {
            val baseR = 60f + i * 42f
            val r = baseR * (0.88f + pulse * 0.24f)
            val alpha = (0.13f - i * 0.016f).coerceAtLeast(0.02f) * (0.7f + pulse * 0.3f)
            val stroke = if (i == 0) primaryStroke else secondaryStroke
            drawCircle(
                color = cyanColor.copy(alpha = alpha),
                radius = r,
                center = Offset(cx, cy),
                style = stroke
            )
        }

        // ── 2. Innermost glow core — radial gradient fill ─────────────────
        val coreRadius = 90f + pulse * 30f
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    cyanColor.copy(alpha = 0.09f + pulse * 0.05f),
                    Color.Transparent
                ),
                center = Offset(cx, cy),
                radius = coreRadius
            ),
            radius = coreRadius,
            center = Offset(cx, cy)
        )

        // ── 3. Orbital nodes — 12 positions, rotating ─────────────────────
        val nodeOrbitR = 155f + pulse * 22f

        // ⚡ Bolt Optimization: Use primitive FloatArray buffers to avoid Offset allocations
        for (i in 0 until nodeCount) {
            val angle = orbit + i * angleStep
            nodeXBuffer[i] = cx + cos(angle) * nodeOrbitR
            nodeYBuffer[i] = cy + sin(angle) * nodeOrbitR
        }

        // Lattice connections between adjacent orbital nodes
        for (i in 0 until nodeCount) {
            val next = (i + 1) % nodeCount
            val alpha = 0.07f + (if (i % 3 == 0) shimmer % 1f * 0.06f else 0f)
            drawLine(
                color = cyanColor.copy(alpha = alpha),
                start = Offset(nodeXBuffer[i], nodeYBuffer[i]),
                end = Offset(nodeXBuffer[next], nodeYBuffer[next]),
                strokeWidth = 0.5f
            )
        }

        // Cross-connect every 4th node through center region
        for (i in 0 until nodeCount step 4) {
            val opp = (i + nodeCount / 2) % nodeCount
            val alpha = 0.04f + pulse * 0.03f
            drawLine(
                color = purpleColor.copy(alpha = alpha),
                start = Offset(nodeXBuffer[i], nodeYBuffer[i]),
                end = Offset(nodeXBuffer[opp], nodeYBuffer[opp]),
                strokeWidth = 0.4f
            )
        }

        // Node dots — cyan primary, magenta on every 3rd (Kai/Genesis harmonic)
        for (i in 0 until nodeCount) {
            val isMagenta = i % 3 == 0
            val nodeColor = if (isMagenta) magentaColor else cyanColor
            val baseAlpha = if (isMagenta) 0.55f else 0.65f
            val nodeAlpha = baseAlpha * (0.7f + pulse * 0.3f)
            val glowAlpha = nodeAlpha * 0.25f
            val pos = Offset(nodeXBuffer[i], nodeYBuffer[i])

            // Glow halo
            drawCircle(nodeColor.copy(alpha = glowAlpha), 10f + pulse * 4f, pos)
            // Node core
            drawCircle(nodeColor.copy(alpha = nodeAlpha), 3f + pulse * 1.2f, pos)
        }

        // ── 4. Data threads to center — appear at pulse peak ──────────────
        val threadAlpha = (pulse - 0.6f).coerceAtLeast(0f) * 0.25f
        if (threadAlpha > 0f) {
            val threadColor = cyanColor.copy(alpha = threadAlpha)
            val center = Offset(cx, cy)
            for (i in 0 until nodeCount step 3) {
                drawLine(
                    color = threadColor,
                    start = Offset(nodeXBuffer[i], nodeYBuffer[i]),
                    end = center,
                    strokeWidth = 0.4f
                )
            }
        }

        // ── 5. Outer satellite ring — 6 distant nodes, counter-rotating ───
        val satOrbitR = 240f + pulse * 15f
        val satOrbit = -orbit * 0.3f   // counter-rotate, slower

        for (i in 0 until satCount) {
            val angle = satOrbit + i * satAngleStep
            val sx = cx + cos(angle) * satOrbitR
            val sy = cy + sin(angle) * satOrbitR
            val satAlpha = 0.18f * (0.6f + pulse * 0.4f)
            val satPos = Offset(sx, sy)
            drawCircle(satelliteColor.copy(alpha = satAlpha), 2.2f, satPos)

            // Thin line from satellite to nearest orbital node
            // ⚡ Bolt Optimization: Manual loop and squared distance to avoid sqrt and minByOrNull
            var minDistSq = Float.MAX_VALUE
            var nearestIdx = 0
            for (j in 0 until nodeCount) {
                val dx = nodeXBuffer[j] - sx
                val dy = nodeYBuffer[j] - sy
                val distSq = dx * dx + dy * dy
                if (distSq < minDistSq) {
                    minDistSq = distSq
                    nearestIdx = j
                }
            }

            drawLine(
                satelliteColor.copy(alpha = satAlpha * 0.4f),
                satPos,
                Offset(nodeXBuffer[nearestIdx], nodeYBuffer[nearestIdx]),
                0.35f
            )
        }

        // ── 6. Magenta shimmer arc — Aura/Kai resonance ───────────────────
        // A partial arc that sweeps around the node ring at shimmer frequency
        val arcStart = shimmer * 360f
        val arcSweep = 60f + pulse * 40f
        drawArc(
            color = magentaColor.copy(alpha = 0.12f + pulse * 0.06f),
            startAngle = arcStart,
            sweepAngle = arcSweep,
            useCenter = false,
            topLeft = Offset(cx - nodeOrbitR, cy - nodeOrbitR),
            size = Size(nodeOrbitR * 2, nodeOrbitR * 2),
            style = arcStroke
        )
    }
}
