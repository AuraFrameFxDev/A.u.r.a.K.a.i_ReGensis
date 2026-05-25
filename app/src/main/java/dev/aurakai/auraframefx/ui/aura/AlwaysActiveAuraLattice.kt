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
import androidx.compose.runtime.getValue
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
import kotlin.math.sqrt
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
    val pulse by tr.animateFloat(
        0f, 1f,
        infiniteRepeatable(tween(2800, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "pulse"
    )

    // Orbital rotation — very slow
    val orbit by tr.animateFloat(
        0f, 2f * PI.toFloat(),
        infiniteRepeatable(tween(28000, easing = LinearEasing)),
        label = "orbit"
    )

    // Secondary shimmer on a different cycle
    val shimmer by tr.animateFloat(
        0f, 1f,
        infiniteRepeatable(tween(5600, easing = LinearEasing)),
        label = "shimmer"
    )

    // Lattice connection seeds — stable random positions for thread targets
    val latticeSeeds = remember {
        List(8) { Pair(Random.nextFloat() * 0.6f + 0.2f, Random.nextFloat() * 0.6f + 0.2f) }
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height
        val cx = w / 2f
        val cy = h / 2f

        // ── 1. Concentric pulsing rings — Aura's breath ───────────────────
        // 7 rings, radius grows with pulse, alpha fades outward
        repeat(7) { i ->
            val baseR = 60f + i * 42f
            val r = baseR * (0.88f + pulse * 0.24f)
            val alpha = (0.13f - i * 0.016f).coerceAtLeast(0.02f) * (0.7f + pulse * 0.3f)
            val strokeW = if (i == 0) 1.2f else 0.7f
            drawCircle(
                color = Color(0xFF00F5FF).copy(alpha = alpha),
                radius = r,
                center = Offset(cx, cy),
                style = Stroke(strokeW)
            )
        }

        // ── 2. Innermost glow core — radial gradient fill ─────────────────
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color(0xFF00F5FF).copy(alpha = 0.09f + pulse * 0.05f),
                    Color.Transparent
                ),
                center = Offset(cx, cy),
                radius = 90f + pulse * 30f
            ),
            radius = 90f + pulse * 30f,
            center = Offset(cx, cy)
        )

        // ── 3. Orbital nodes — 12 positions, rotating ─────────────────────
        val nodeCount   = 12
        val nodeOrbitR  = 155f + pulse * 22f
        val nodePositions = (0 until nodeCount).map { i ->
            val angle = orbit + i * (2f * PI.toFloat() / nodeCount)
            Offset(cx + cos(angle) * nodeOrbitR, cy + sin(angle) * nodeOrbitR)
        }

        // Lattice connections between adjacent orbital nodes
        for (i in 0 until nodeCount) {
            val next = (i + 1) % nodeCount
            val alpha = 0.07f + (if (i % 3 == 0) shimmer % 1f * 0.06f else 0f)
            drawLine(
                color = Color(0xFF00F5FF).copy(alpha = alpha),
                start = nodePositions[i],
                end   = nodePositions[next],
                strokeWidth = 0.5f
            )
        }

        // Cross-connect every 4th node through center region
        for (i in 0 until nodeCount step 4) {
            val opp = (i + nodeCount / 2) % nodeCount
            val alpha = 0.04f + pulse * 0.03f
            drawLine(
                color = Color(0xFF7B00FF).copy(alpha = alpha),
                start = nodePositions[i],
                end   = nodePositions[opp],
                strokeWidth = 0.4f
            )
        }

        // Node dots — cyan primary, magenta on every 3rd (Kai/Genesis harmonic)
        nodePositions.forEachIndexed { i, pos ->
            val isMagenta = i % 3 == 0
            val nodeColor = if (isMagenta) Color(0xFFFF00D4) else Color(0xFF00F5FF)
            val baseAlpha = if (isMagenta) 0.55f else 0.65f
            val nodeAlpha = baseAlpha * (0.7f + pulse * 0.3f)
            val glowAlpha = nodeAlpha * 0.25f

            // Glow halo
            drawCircle(nodeColor.copy(alpha = glowAlpha), 10f + pulse * 4f, pos)
            // Node core
            drawCircle(nodeColor.copy(alpha = nodeAlpha), 3f + pulse * 1.2f, pos)
        }

        // ── 4. Data threads to center — appear at pulse peak ──────────────
        val threadAlpha = (pulse - 0.6f).coerceAtLeast(0f) * 0.25f
        if (threadAlpha > 0f) {
            for (i in 0 until nodeCount step 3) {
                drawLine(
                    color = Color(0xFF00F5FF).copy(alpha = threadAlpha),
                    start = nodePositions[i],
                    end   = Offset(cx, cy),
                    strokeWidth = 0.4f
                )
            }
        }

        // ── 5. Outer satellite ring — 6 distant nodes, counter-rotating ───
        val satCount  = 6
        val satOrbitR = 240f + pulse * 15f
        val satOrbit  = -orbit * 0.3f   // counter-rotate, slower

        repeat(satCount) { i ->
            val angle = satOrbit + i * (2f * PI.toFloat() / satCount)
            val satPos = Offset(cx + cos(angle) * satOrbitR, cy + sin(angle) * satOrbitR)
            val satAlpha = 0.18f * (0.6f + pulse * 0.4f)
            drawCircle(Color(0xFF00FFD4).copy(alpha = satAlpha), 2.2f, satPos)

            // Thin line from satellite to nearest orbital node
            val nearestNode = nodePositions.minByOrNull { n ->
                val dx = n.x - satPos.x; val dy = n.y - satPos.y
                sqrt(dx * dx + dy * dy)
            }!!
            drawLine(
                Color(0xFF00FFD4).copy(alpha = satAlpha * 0.4f),
                satPos, nearestNode, 0.35f
            )
        }

        // ── 6. Magenta shimmer arc — Aura/Kai resonance ───────────────────
        // A partial arc that sweeps around the node ring at shimmer frequency
        val arcStart = shimmer * 360f
        val arcSweep = 60f + pulse * 40f
        drawArc(
            color = Color(0xFFFF00D4).copy(alpha = 0.12f + pulse * 0.06f),
            startAngle = arcStart,
            sweepAngle = arcSweep,
            useCenter = false,
            topLeft = Offset(cx - nodeOrbitR, cy - nodeOrbitR),
            size = Size(nodeOrbitR * 2, nodeOrbitR * 2),
            style = Stroke(1.2f)
        )
    }
}
