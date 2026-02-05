package dev.aurakai.auraframefx.domains.aura.ui.gates.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * NEON GATE ICONS - Pink/Magenta glowing symbols
 */

// ORACLE DRIVE - Concentric eye
@Composable
fun OracleEyeIcon(
    color: Color = Color(0xFFFF00FF), // Magenta/Pink
    pulseAlpha: Float = 1f,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.size(120.dp)) {
        val centerX = size.width / 2f
        val centerY = size.height / 2f

        // Outer eye shape
        val eyePath = Path().apply {
            moveTo(centerX - 60f, centerY)
            cubicTo(centerX - 60f, centerY - 30f, centerX - 30f, centerY - 40f, centerX, centerY - 40f)
            cubicTo(centerX + 30f, centerY - 40f, centerX + 60f, centerY - 30f, centerX + 60f, centerY)
            cubicTo(centerX + 60f, centerY + 30f, centerX + 30f, centerY + 40f, centerX, centerY + 40f)
            cubicTo(centerX - 30f, centerY + 40f, centerX - 60f, centerY + 30f, centerX - 60f, centerY)
            close()
        }

        drawPath(
            path = eyePath,
            color = color.copy(alpha = pulseAlpha),
            style = Stroke(width = 3f)
        )

        // Concentric circles (iris)
        for (i in 1..4) {
            drawCircle(
                color = color.copy(alpha = pulseAlpha * (1f - i * 0.15f)),
                radius = 30f - i * 6f,
                center = Offset(centerX, centerY),
                style = Stroke(width = 2f)
            )
        }

        // Center pupil
        drawCircle(
            color = color.copy(alpha = pulseAlpha),
            radius = 8f,
            center = Offset(centerX, centerY)
        )

        // Glow effect
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    color.copy(alpha = pulseAlpha * 0.6f),
                    Color.Transparent
                ),
                center = Offset(centerX, centerY)
            ),
            radius = 70f,
            center = Offset(centerX, centerY)
        )
    }
}

// TERMINAL - Command prompt >_
@Composable
fun TerminalPromptIcon(
    color: Color = Color(0xFFFF00FF),
    pulseAlpha: Float = 1f,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.size(120.dp)) {
        val centerX = size.width / 2f
        val centerY = size.height / 2f

        // Greater-than symbol >
        val arrowPath = Path().apply {
            moveTo(centerX - 30f, centerY - 30f)
            lineTo(centerX + 10f, centerY)
            lineTo(centerX - 30f, centerY + 30f)
        }

        drawPath(
            path = arrowPath,
            color = color.copy(alpha = pulseAlpha),
            style = Stroke(width = 6f)
        )

        // Underscore _
        drawLine(
            color = color.copy(alpha = pulseAlpha),
            start = Offset(centerX + 20f, centerY + 30f),
            end = Offset(centerX + 50f, centerY + 30f),
            strokeWidth = 6f
        )

        // Glow
        drawPath(
            path = arrowPath,
            brush = Brush.radialGradient(
                colors = listOf(
                    color.copy(alpha = pulseAlpha * 0.8f),
                    Color.Transparent
                ),
                center = Offset(centerX, centerY)
            ),
            style = Stroke(width = 12f)
        )
    }
}

// ROM TOOLS - Game controller
@Composable
fun GameControllerIcon(
    color: Color = Color(0xFFFF00FF),
    pulseAlpha: Float = 1f,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.size(120.dp)) {
        val centerX = size.width / 2f
        val centerY = size.height / 2f

        // Controller body
        val controllerPath = Path().apply {
            addRoundRect(RoundRect(
                rect = Rect(centerX - 50f, centerY - 25f, centerX + 50f, centerY + 25f),
                cornerRadius = CornerRadius(8f, 8f)
            ))
        }

        drawPath(
            path = controllerPath,
            color = color.copy(alpha = pulseAlpha),
            style = Stroke(width = 4f)
        )

        // D-pad (left side)
        drawLine(color = color.copy(alpha = pulseAlpha), Offset(centerX - 30f, centerY - 10f), Offset(centerX - 30f, centerY + 10f), 3f)
        drawLine(color = color.copy(alpha = pulseAlpha), Offset(centerX - 40f, centerY), Offset(centerX - 20f, centerY), 3f)

        // Buttons (right side)
        listOf(
            Offset(centerX + 25f, centerY - 10f),
            Offset(centerX + 35f, centerY),
            Offset(centerX + 25f, centerY + 10f),
            Offset(centerX + 15f, centerY)
        ).forEach { pos ->
            drawCircle(color = color.copy(alpha = pulseAlpha), 5f, pos)
        }

        // Glow
        drawPath(
            path = controllerPath,
            brush = Brush.radialGradient(
                colors = listOf(
                    color.copy(alpha = pulseAlpha * 0.6f),
                    Color.Transparent
                )
            ),
            style = Stroke(width = 8f)
        )
    }
}

// ROOT TOOLS - Mech warrior head
@Composable
fun MechWarriorIcon(
    color: Color = Color(0xFFFF00FF),
    pulseAlpha: Float = 1f,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.size(120.dp)) {
        val centerX = size.width / 2f
        val centerY = size.height / 2f

        // Head triangle
        val headPath = Path().apply {
            moveTo(centerX, centerY - 40f)
            lineTo(centerX - 35f, centerY + 20f)
            lineTo(centerX + 35f, centerY + 20f)
            close()
        }

        drawPath(
            path = headPath,
            color = color.copy(alpha = pulseAlpha),
            style = Stroke(width = 4f)
        )

        // Eyes
        drawLine(color = color.copy(alpha = pulseAlpha), Offset(centerX - 15f, centerY), Offset(centerX - 5f, centerY), 3f)
        drawLine(color = color.copy(alpha = pulseAlpha), Offset(centerX + 5f, centerY), Offset(centerX + 15f, centerY), 3f)

        // Antenna
        drawLine(color = color.copy(alpha = pulseAlpha), Offset(centerX, centerY - 40f), Offset(centerX, centerY - 55f), 2f)
        drawCircle(color = color.copy(alpha = pulseAlpha), 3f, Offset(centerX, centerY - 55f))

        // Glow
        drawPath(
            path = headPath,
            brush = Brush.radialGradient(
                colors = listOf(
                    color.copy(alpha = pulseAlpha * 0.6f),
                    Color.Transparent
                )
            )
        )
    }
}

// AURA'S LAB - Laboratory flask
@Composable
fun LabFlaskIcon(
    color: Color = Color(0xFFFF00FF),
    pulseAlpha: Float = 1f,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.size(120.dp)) {
        val centerX = size.width / 2f
        val centerY = size.height / 2f

        // Flask body
        val flaskPath = Path().apply {
            // Neck
            moveTo(centerX - 10f, centerY - 40f)
            lineTo(centerX - 10f, centerY - 20f)
            // Widening to body
            lineTo(centerX - 35f, centerY + 30f)
            lineTo(centerX + 35f, centerY + 30f)
            lineTo(centerX + 10f, centerY - 20f)
            lineTo(centerX + 10f, centerY - 40f)
            close()
        }

        drawPath(
            path = flaskPath,
            color = color.copy(alpha = pulseAlpha),
            style = Stroke(width = 3f)
        )

        // Liquid level
        drawLine(
            color = color.copy(alpha = pulseAlpha * 0.6f),
            start = Offset(centerX - 25f, centerY + 10f),
            end = Offset(centerX + 25f, centerY + 10f),
            strokeWidth = 2f
        )

        // Bubbles
        listOf(
            Offset(centerX - 10f, centerY + 5f),
            Offset(centerX + 5f, centerY),
            Offset(centerX - 5f, centerY + 15f)
        ).forEach { pos ->
            drawCircle(
                color = color.copy(alpha = pulseAlpha * 0.5f),
                radius = 3f,
                center = pos,
                style = Stroke(width = 1f)
            )
        }
    }
}

// CODE ASSIST - Code brackets </>
@Composable
fun CodeBracketsIcon(
    color: Color = Color(0xFFFF00FF),
    pulseAlpha: Float = 1f,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.size(120.dp)) {
        val centerX = size.width / 2f
        val centerY = size.height / 2f

        // Left bracket <
        val leftBracket = Path().apply {
            moveTo(centerX - 10f, centerY - 30f)
            lineTo(centerX - 30f, centerY)
            lineTo(centerX - 10f, centerY + 30f)
        }

        drawPath(
            path = leftBracket,
            color = color.copy(alpha = pulseAlpha),
            style = Stroke(width = 5f)
        )

        // Slash /
        drawLine(
            color = color.copy(alpha = pulseAlpha),
            start = Offset(centerX - 5f, centerY + 20f),
            end = Offset(centerX + 5f, centerY - 20f),
            strokeWidth = 5f
        )

        // Right bracket >
        val rightBracket = Path().apply {
            moveTo(centerX + 10f, centerY - 30f)
            lineTo(centerX + 30f, centerY)
            lineTo(centerX + 10f, centerY + 30f)
        }

        drawPath(
            path = rightBracket,
            color = color.copy(alpha = pulseAlpha),
            style = Stroke(width = 5f)
        )
    }
}

// AGENT HUB - Network nodes
@Composable
fun NetworkNodesIcon(
    color: Color = Color(0xFFFF00FF),
    pulseAlpha: Float = 1f,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.size(120.dp)) {
        val centerX = size.width / 2f
        val centerY = size.height / 2f

        val nodes = listOf(
            Offset(centerX, centerY),
            Offset(centerX - 30f, centerY - 25f),
            Offset(centerX + 30f, centerY - 25f),
            Offset(centerX - 30f, centerY + 25f),
            Offset(centerX + 30f, centerY + 25f)
        )

        // Connect nodes
        nodes.forEach { from ->
            nodes.forEach { to ->
                if (from != to) {
                    drawLine(
                        color = color.copy(alpha = pulseAlpha * 0.3f),
                        start = from,
                        end = to,
                        strokeWidth = 1.5f
                    )
                }
            }
        }

        // Draw nodes
        nodes.forEach { pos ->
            drawCircle(
                color = color.copy(alpha = pulseAlpha),
                radius = 8f,
                center = pos
            )
            drawCircle(
                color = color.copy(alpha = pulseAlpha * 0.5f),
                radius = 4f,
                center = pos
            )
        }
    }
}

// SENTINEL'S FORTRESS - Shield with lock
@Composable
fun ShieldIcon(color: Color = Color(0xFFFF00FF), pulseAlpha: Float = 1f, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.size(120.dp)) {
        val centerX = size.width / 2f
        val centerY = size.height / 2f
        val shieldPath = Path().apply {
            moveTo(centerX, centerY - 40f)
            cubicTo(centerX + 40f, centerY - 30f, centerX + 40f, centerY + 10f, centerX, centerY + 50f)
            cubicTo(centerX - 40f, centerY + 10f, centerX - 40f, centerY - 30f, centerX, centerY - 40f)
            close()
        }
        drawPath(path = shieldPath, color = color.copy(alpha = pulseAlpha), style = Stroke(width = 4f))
        drawCircle(color = color.copy(alpha = pulseAlpha), 8f, Offset(centerX, centerY - 5f), style = Stroke(2f))
        drawRect(color = color.copy(alpha = pulseAlpha), topLeft = Offset(centerX - 10f, centerY), size = androidx.compose.ui.geometry.Size(20f, 15f), style = Stroke(2f))
    }
}

// CHROMACORE - Prism
@Composable
fun PrismIcon(color: Color = Color(0xFFFF00FF), pulseAlpha: Float = 1f, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.size(120.dp)) {
        val centerX = size.width / 2f
        val centerY = size.height / 2f
        val prismPath = Path().apply { moveTo(centerX - 30f, centerY + 25f); lineTo(centerX, centerY - 35f); lineTo(centerX + 30f, centerY + 25f); close() }
        drawPath(path = prismPath, color = color.copy(alpha = pulseAlpha), style = Stroke(width = 4f))
        val rayColors = listOf(0xFFFF0000, 0xFFFFFF00, 0xFF00FF00, 0xFF00FFFF, 0xFF0000FF, 0xFFFF00FF)
        rayColors.forEachIndexed { index, colorCode ->
            val angle = -30f + index * 12f
            val startX = centerX + 30f
            val startY = centerY + 25f
            val endX = startX + 40f * cos(Math.toRadians(angle.toDouble())).toFloat()
            val endY = startY + 40f * sin(Math.toRadians(angle.toDouble())).toFloat()
            drawLine(color = Color(colorCode).copy(alpha = pulseAlpha * 0.7f), start = Offset(startX, startY), end = Offset(endX, endY), strokeWidth = 2f)
        }
    }
}

// THEME ENGINE - Brush
@Composable
fun BrushIcon(color: Color = Color(0xFFFF00FF), pulseAlpha: Float = 1f, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.size(120.dp)) {
        val centerX = size.width / 2f
        val centerY = size.height / 2f
        drawLine(color = color.copy(alpha = pulseAlpha), start = Offset(centerX - 20f, centerY - 30f), end = Offset(centerX - 10f, centerY + 10f), strokeWidth = 6f)
        drawCircle(color = color.copy(alpha = pulseAlpha * 0.7f), radius = 12f, center = Offset(centerX - 10f, centerY + 10f))
        drawLine(color = color.copy(alpha = pulseAlpha), start = Offset(centerX + 20f, centerY - 20f), end = Offset(centerX + 10f, centerY + 20f), strokeWidth = 4f)
        for (i in 0..4) { val y = centerY - 20f + i * 10f; drawLine(color = color.copy(alpha = pulseAlpha), start = Offset(centerX + 15f, y), end = Offset(centerX + 12f, y), strokeWidth = 1f) }
    }
}

// COLLABCANVAS - Pencil
@Composable
fun PencilIcon(color: Color = Color(0xFFFF00FF), pulseAlpha: Float = 1f, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.size(120.dp)) {
        val centerX = size.width / 2f
        val centerY = size.height / 2f
        val pencilPath = Path().apply { moveTo(centerX - 25f, centerY - 35f); lineTo(centerX + 25f, centerY + 35f); lineTo(centerX + 20f, centerY + 40f); lineTo(centerX - 30f, centerY - 30f); close() }
        drawPath(path = pencilPath, color = color.copy(alpha = pulseAlpha), style = Stroke(width = 3f))
        drawLine(color = color.copy(alpha = pulseAlpha), start = Offset(centerX + 25f, centerY + 35f), end = Offset(centerX + 30f, centerY + 45f), strokeWidth = 4f)
        for (i in 1..3) { drawCircle(color = color.copy(alpha = pulseAlpha * (1f - i * 0.2f)), radius = (5 - i).toFloat(), center = Offset(centerX + 30f + i * 10f, centerY + 45f + i * 5f)) }
    }
}

// HELP DESK - Headset
@Composable
fun HeadsetIcon(color: Color = Color(0xFFFF00FF), pulseAlpha: Float = 1f, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.size(120.dp)) {
        val centerX = size.width / 2f
        val centerY = size.height / 2f
        val headbandPath = Path().apply { addArc(oval = Rect(centerX - 35f, centerY - 40f, centerX + 35f, centerY + 10f), startAngleDegrees = 180f, sweepAngleDegrees = 180f) }
        drawPath(path = headbandPath, color = color.copy(alpha = pulseAlpha), style = Stroke(width = 4f))
        drawRoundRect(color = color.copy(alpha = pulseAlpha), topLeft = Offset(centerX - 45f, centerY), size = androidx.compose.ui.geometry.Size(15f, 25f), style = Stroke(width = 3f))
        drawRoundRect(color = color.copy(alpha = pulseAlpha), topLeft = Offset(centerX + 30f, centerY), size = androidx.compose.ui.geometry.Size(15f, 25f), style = Stroke(width = 3f))
        drawLine(color = color.copy(alpha = pulseAlpha), start = Offset(centerX - 37f, centerY + 25f), end = Offset(centerX - 25f, centerY + 35f), strokeWidth = 2f)
        drawCircle(color = color.copy(alpha = pulseAlpha), radius = 4f, center = Offset(centerX - 25f, centerY + 35f))
    }
}

// XPOSED - Hook
@Composable
fun HookIcon(color: Color = Color(0xFFFF00FF), pulseAlpha: Float = 1f, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.size(120.dp)) {
        val centerX = size.width / 2f
        val centerY = size.height / 2f
        val hookPath = Path().apply { moveTo(centerX, centerY - 40f); lineTo(centerX, centerY + 10f); cubicTo(centerX, centerY + 30f, centerX + 20f, centerY + 30f, centerX + 30f, centerY + 15f) }
        drawPath(path = hookPath, color = color.copy(alpha = pulseAlpha), style = Stroke(width = 5f))
        drawCircle(color = color.copy(alpha = pulseAlpha), radius = 6f, center = Offset(centerX + 30f, centerY + 15f))
        listOf(Offset(centerX - 20f, centerY - 10f), Offset(centerX - 20f, centerY + 10f)).forEach { pos -> drawLine(color = color.copy(alpha = pulseAlpha * 0.6f), start = Offset(centerX, pos.y), end = pos, strokeWidth = 2f); drawCircle(color = color.copy(alpha = pulseAlpha), radius = 4f, center = pos) }
    }
}

// SPHERE GRID - Hexagonal grid
@Composable
fun HexGridIcon(color: Color = Color(0xFFFF00FF), pulseAlpha: Float = 1f, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.size(120.dp)) {
        val centerX = size.width / 2f
        val centerY = size.height / 2f
        val hexRadius = 25f
        val hexPath = Path().apply { for (i in 0..5) { val angle = 60f * i; val x = centerX + hexRadius * cos(Math.toRadians(angle.toDouble())).toFloat(); val y = centerY + hexRadius * sin(Math.toRadians(angle.toDouble())).toFloat(); if (i == 0) moveTo(x, y) else lineTo(x, y) }; close() }
        drawPath(path = hexPath, color = color.copy(alpha = pulseAlpha), style = Stroke(width = 4f))
        for (i in 0..5) {
            val angle = 60f * i; val distance = hexRadius * 1.732f
            val hx = centerX + distance * cos(Math.toRadians(angle.toDouble())).toFloat()
            val hy = centerY + distance * sin(Math.toRadians(angle.toDouble())).toFloat()
            val neighborHex = Path().apply { for (j in 0..5) { val hexAngle = 60f * j; val x = hx + hexRadius * 0.7f * cos(Math.toRadians(hexAngle.toDouble())).toFloat(); val y = hy + hexRadius * 0.7f * sin(Math.toRadians(hexAngle.toDouble())).toFloat(); if (j == 0) moveTo(x, y) else lineTo(x, y) }; close() }
            drawPath(path = neighborHex, color = color.copy(alpha = pulseAlpha * 0.5f), style = Stroke(width = 2f))
        }
        drawCircle(color = color.copy(alpha = pulseAlpha), radius = 6f, center = Offset(centerX, centerY))
    }
}

// UI/UX DESIGN STUDIO - Artboard
@Composable
fun ArtboardIcon(color: Color = Color(0xFFFF00FF), pulseAlpha: Float = 1f, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.size(120.dp)) {
        val centerX = size.width / 2f
        val centerY = size.height / 2f
        drawRect(color = color.copy(alpha = pulseAlpha), topLeft = Offset(centerX - 35f, centerY - 35f), size = androidx.compose.ui.geometry.Size(70f, 70f), style = Stroke(width = 3f))
        val handleSize = 8f
        listOf(Offset(centerX - 35f, centerY - 35f), Offset(centerX + 35f, centerY - 35f), Offset(centerX + 35f, centerY + 35f), Offset(centerX - 35f, centerY + 35f)).forEach { corner -> drawRect(color = color.copy(alpha = pulseAlpha), topLeft = Offset(corner.x - handleSize / 2, corner.y - handleSize / 2), size = androidx.compose.ui.geometry.Size(handleSize, handleSize)) }
        for (i in 1..2) { val offset = i * 23.33f; drawLine(color = color.copy(alpha = pulseAlpha * 0.3f), start = Offset(centerX - 35f + offset, centerY - 35f), end = Offset(centerX - 35f + offset, centerY + 35f), strokeWidth = 1f); drawLine(color = color.copy(alpha = pulseAlpha * 0.3f), start = Offset(centerX - 35f, centerY - 35f + offset), end = Offset(centerX + 35f, centerY - 35f + offset), strokeWidth = 1f) }
    }
}

// SYSTEM JOURNAL - User avatar
@Composable
fun UserIcon(color: Color = Color(0xFFFF00FF), pulseAlpha: Float = 1f, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.size(120.dp)) {
        val centerX = size.width / 2f
        val centerY = size.height / 2f
        drawCircle(color = color.copy(alpha = pulseAlpha), radius = 18f, center = Offset(centerX, centerY - 10f), style = Stroke(width = 3f))
        val bodyPath = Path().apply { addArc(oval = Rect(centerX - 30f, centerY + 5f, centerX + 30f, centerY + 55f), startAngleDegrees = 180f, sweepAngleDegrees = 180f) }
        drawPath(path = bodyPath, color = color.copy(alpha = pulseAlpha), style = Stroke(width = 3f))
        listOf(Pair(Offset(centerX - 30f, centerY), Offset(centerX - 45f, centerY - 10f)), Pair(Offset(centerX + 30f, centerY), Offset(centerX + 45f, centerY - 10f)), Pair(Offset(centerX, centerY + 20f), Offset(centerX, centerY + 40f))).forEach { (start, end) -> drawLine(color = color.copy(alpha = pulseAlpha * 0.5f), start = start, end = end, strokeWidth = 2f); drawCircle(color = color.copy(alpha = pulseAlpha), radius = 3f, center = end) }
    }
}

// ALL 16 ICONS COMPLETE! 🔥🚀

