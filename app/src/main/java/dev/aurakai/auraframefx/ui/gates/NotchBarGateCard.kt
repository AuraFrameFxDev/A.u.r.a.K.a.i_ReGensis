package dev.aurakai.auraframefx.ui.gates

// ═══════════════════════════════════════════════════════════════════════════════
// NotchBarGateCard.kt — Image 11
// ArchitecturalCatalyst (Claude) — ReGenesis Build Master
//
// The "NOTCH BAR" gate card — neon circuit board card art drawn in Canvas.
// Used as: the gate card for Personal Screen & Shortcuts / Notch Bar domain.
// Also exported as a standalone composable for use anywhere a circuit card
// art piece is needed (e.g. a "quick settings" floating card).
// ═══════════════════════════════════════════════════════════════════════════════

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import kotlin.math.cos
import kotlin.math.sin

// ── Notch Bar Gate Card (standalone canvas art) ───────────────────────────────

@Composable
fun NotchBarGateCard(
    modifier: Modifier = Modifier,
    isActive: Boolean = false,
    onClick: () -> Unit = {}
) {
    val infiniteTransition = rememberInfiniteTransition(label = "notch_card")
    val electricPulse by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 1f,
        animationSpec = infiniteRepeatable(
            tween(1200, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        ),
        label = "electric"
    )
    val scanLine by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(2500, easing = LinearEasing), RepeatMode.Restart),
        label = "scan"
    )
    val electricSpark by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 1f,
        animationSpec = infiniteRepeatable(
            tween(400, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        ),
        label = "spark"
    )

    Canvas(
        modifier = modifier.clickable(onClick = onClick)
    ) {
        val w = size.width
        val h = size.height
        val cr = 24f                           // corner radius
        val border = 10f                       // frame thickness

        // ── Black void background ─────────────────────────────────────────
        drawRect(Color(0xFF000000))

        // ── Electric spark effect on edges ────────────────────────────────
        val sparkAlpha = electricSpark * 0.6f
        for (i in 0..20) {
            val t = i.toFloat() / 20f
            val sx = t * w
            val sy = 0f
            drawCircle(
                Color(0xFF00E5FF).copy(alpha = sparkAlpha * (i % 3).toFloat() / 3f),
                radius = 3f + electricSpark * 4f, center = Offset(sx, sy + 5f)
            )
            drawCircle(
                Color(0xFF00E5FF).copy(alpha = sparkAlpha * 0.5f),
                radius = 2f, center = Offset(sx, h - 5f)
            )
        }

        // ── Outer frame: Red-Orange top, Cyan bottom gradient ─────────────
        val outerPath = Path().apply {
            addRoundRect(
                androidx.compose.ui.geometry.RoundRect(
                    left = 2f, top = 2f, right = w - 2f, bottom = h - 2f,
                    cornerRadius = CornerRadius(cr, cr)
                )
            )
        }
        // Multi-layer glow rings
        listOf(16f, 10f, 5f, 2f).forEachIndexed { idx, strokeW ->
            val alpha = (electricPulse * 0.4f + 0.2f) * (1f - idx * 0.2f)
            drawPath(
                outerPath, Brush.linearGradient(
                    0f to Color(0xFFFF4500).copy(alpha = alpha),
                    0.3f to Color(0xFFFFD700).copy(alpha = alpha * 0.7f),
                    0.7f to Color(0xFF00CED1).copy(alpha = alpha),
                    1f to Color(0xFF00BFFF).copy(alpha = alpha)
                ), style = Stroke(strokeW)
            )
        }

        // ── Circuit trace inset frame ─────────────────────────────────────
        val inset1 = 14f
        val inset2 = 22f
        val innerPath = Path().apply {
            addRoundRect(
                androidx.compose.ui.geometry.RoundRect(
                    left = inset1, top = inset1, right = w - inset1, bottom = h - inset1,
                    cornerRadius = CornerRadius(cr - 4f, cr - 4f)
                )
            )
        }
        drawPath(
            innerPath,
            Color(0xFFFF6600).copy(alpha = 0.5f + electricPulse * 0.2f),
            style = Stroke(1.5f)
        )

        val innerPath2 = Path().apply {
            addRoundRect(
                androidx.compose.ui.geometry.RoundRect(
                    left = inset2, top = inset2, right = w - inset2, bottom = h - inset2,
                    cornerRadius = CornerRadius(cr - 8f, cr - 8f)
                )
            )
        }
        drawPath(innerPath2, Color(0xFF00CED1).copy(alpha = 0.4f), style = Stroke(1f))

        // ── Circuit trace patterns on frame ───────────────────────────────
        drawCircuitTraces(w, h, electricPulse)

        // ── Octagonal shield center mount ─────────────────────────────────
        val shieldCx = w / 2f
        val shieldCy = h * 0.44f
        val shieldR = w * 0.28f
        val octPath = Path()
        for (i in 0..7) {
            val angle = Math.PI / 4 * i - Math.PI / 8
            val x = shieldCx + shieldR * cos(angle).toFloat()
            val y = shieldCy + shieldR * sin(angle).toFloat()
            if (i == 0) octPath.moveTo(x, y) else octPath.lineTo(x, y)
        }
        octPath.close()
        drawPath(octPath, Color(0xFF001820).copy(alpha = 0.9f))
        drawPath(
            octPath,
            Color(0xFF00CED1).copy(alpha = 0.6f + electricPulse * 0.2f),
            style = Stroke(2f)
        )

        // ── Inner screen (the phone icon card) ────────────────────────────
        val screenLeft = w * 0.28f
        val screenTop = h * 0.2f
        val screenW = w * 0.44f
        val screenH = h * 0.52f
        drawRoundRect(
            Color(0xFF001010).copy(alpha = 0.9f),
            Offset(screenLeft, screenTop), Size(screenW, screenH),
            CornerRadius(10f, 10f)
        )
        drawRoundRect(
            Color(0xFFFF3300).copy(alpha = 0.5f + electricPulse * 0.2f),
            Offset(screenLeft, screenTop), Size(screenW, screenH),
            CornerRadius(10f, 10f), style = Stroke(1.5f)
        )

        // ── Phone notch circle ────────────────────────────────────────────
        val notchCx = w / 2f
        val notchCy = screenTop + screenH * 0.12f
        drawCircle(Color(0xFF001010), radius = screenW * 0.12f, center = Offset(notchCx, notchCy))
        drawCircle(
            Color(0xFFFF3300).copy(alpha = 0.6f), radius = screenW * 0.12f,
            center = Offset(notchCx, notchCy), style = Stroke(1f)
        )

        // ── Screen content: status bar icons + bars ────────────────────────
        val contentLeft = screenLeft + 8f
        val contentW = screenW - 16f
        val row1Y = screenTop + screenH * 0.28f
        // Star icon stub
        drawCircle(Color(0xFFFF3300).copy(0.6f), 8f, Offset(contentLeft + 8f, row1Y))
        // Horizontal bars (status items)
        listOf(0.3f, 0.5f, 0.7f, 0.9f).forEachIndexed { idx, barW ->
            val barY = row1Y + 22f + idx * 16f
            drawLine(
                Color(0xFFFF3300).copy(0.4f + idx * 0.1f),
                Offset(contentLeft + 18f, barY),
                Offset(contentLeft + 18f + barW * contentW * 0.6f, barY),
                strokeWidth = 6f
            )
        }
        // Battery bar
        val batY = row1Y + 22f + 4 * 16f + 8f
        drawRoundRect(
            Color(0xFF00FF80).copy(0.5f),
            Offset(contentLeft + 8f, batY), Size(contentW * 0.6f, 8f), CornerRadius(3f, 3f)
        )
        // Settings gear (bottom right of screen)
        val gearCx = screenLeft + screenW * 0.75f
        val gearCy = screenTop + screenH * 0.8f
        drawCircle(Color(0xFFFF3300).copy(0.5f), 10f, Offset(gearCx, gearCy), style = Stroke(2f))
        drawCircle(Color(0xFFFF3300).copy(0.3f), 5f, Offset(gearCx, gearCy))
        // Up arrow
        drawLine(
            Color(0xFFFF3300).copy(0.6f),
            Offset(screenLeft + screenW * 0.78f, screenTop + screenH * 0.32f),
            Offset(screenLeft + screenW * 0.78f, screenTop + screenH * 0.22f), 2f
        )

        // ── Scan line across screen ────────────────────────────────────────
        val scanY = screenTop + screenH * scanLine
        if (scanY < screenTop + screenH) {
            drawLine(
                Color(0xFF00FFFF).copy(alpha = 0.25f),
                Offset(screenLeft, scanY), Offset(screenLeft + screenW, scanY), 1f
            )
        }

        // ── NOTCH BAR label ────────────────────────────────────────────────
        // (Text drawn by the composable Text layer below canvas)
    }
}

private fun DrawScope.drawCircuitTraces(w: Float, h: Float, pulse: Float) {
    val traceColor = Color(0xFFFF6600).copy(alpha = 0.3f + pulse * 0.15f)
    val traceColor2 = Color(0xFF00CED1).copy(alpha = 0.25f + pulse * 0.1f)

    // Top-left corner traces
    drawLine(traceColor, Offset(30f, 15f), Offset(w * 0.35f, 15f), 1.5f)
    drawLine(traceColor, Offset(w * 0.35f, 15f), Offset(w * 0.35f, 30f), 1.5f)
    drawLine(traceColor, Offset(15f, 30f), Offset(15f, h * 0.25f), 1.5f)
    drawLine(traceColor, Offset(15f, h * 0.25f), Offset(30f, h * 0.25f), 1.5f)

    // Top-right corner traces
    drawLine(traceColor2, Offset(w - 30f, 15f), Offset(w * 0.65f, 15f), 1.5f)
    drawLine(traceColor2, Offset(w * 0.65f, 15f), Offset(w * 0.65f, 30f), 1.5f)
    drawLine(traceColor2, Offset(w - 15f, 30f), Offset(w - 15f, h * 0.25f), 1.5f)
    drawLine(traceColor2, Offset(w - 30f, h * 0.25f), Offset(w - 15f, h * 0.25f), 1.5f)

    // Bottom traces
    drawLine(traceColor, Offset(30f, h - 15f), Offset(w * 0.4f, h - 15f), 1.5f)
    drawLine(traceColor2, Offset(w * 0.6f, h - 15f), Offset(w - 30f, h - 15f), 1.5f)

    // Connector dots on traces
    for (dot in listOf(
        Offset(w * 0.35f, 15f),
        Offset(15f, h * 0.25f),
        Offset(w * 0.65f, 15f),
        Offset(w - 15f, h * 0.25f)
    )) {
        drawCircle(traceColor.copy(alpha = pulse * 0.8f), radius = 3f, center = dot)
    }
}

// End of NotchBarGateCard
