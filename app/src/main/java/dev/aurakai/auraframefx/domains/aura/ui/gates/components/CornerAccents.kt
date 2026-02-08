package dev.aurakai.auraframefx.domains.aura.ui.gates.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke

/**
 * Corner accent style variants for gate cards
 */
enum class CornerAccentStyle {
    GEOMETRIC_BRACKETS,    // [ ] style brackets (Aura's Lab)
    MASSIVE_ANGLED,        // Large diagonal cuts (Project gates)
    ASYMMETRIC_RAILS,      // Full-height one side (Character gates)
    SMALL_MARKS,           // Minimal corner indicators
    HEXAGONAL_NODES,       // Gaming/network style
    CIRCUIT_PATTERN,       // Tech circuit lines
    NONE                   // No corner accents
}

/**
 * Corner accent overlay for gate cards
 */
@Composable
fun CornerAccents(
    style: CornerAccentStyle,
    primaryColor: Color,
    secondaryColor: Color? = null,
    pulseAlpha: Float = 1f,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        when (style) {
            CornerAccentStyle.GEOMETRIC_BRACKETS -> {
                GeometricBrackets(
                    color = primaryColor,
                    pulseAlpha = pulseAlpha
                )
            }
            CornerAccentStyle.MASSIVE_ANGLED -> {
                MassiveAngledShapes(
                    primaryColor = primaryColor,
                    secondaryColor = secondaryColor ?: primaryColor,
                    pulseAlpha = pulseAlpha
                )
            }
            CornerAccentStyle.ASYMMETRIC_RAILS -> {
                AsymmetricRails(
                    leftColor = primaryColor,
                    rightColor = secondaryColor ?: primaryColor,
                    pulseAlpha = pulseAlpha
                )
            }
            CornerAccentStyle.SMALL_MARKS -> {
                SmallCornerMarks(
                    color = primaryColor,
                    pulseAlpha = pulseAlpha
                )
            }
            CornerAccentStyle.HEXAGONAL_NODES -> {
                HexagonalNodes(
                    color = primaryColor,
                    pulseAlpha = pulseAlpha
                )
            }
            CornerAccentStyle.CIRCUIT_PATTERN -> {
                CircuitPattern(
                    color = primaryColor,
                    pulseAlpha = pulseAlpha
                )
            }
            CornerAccentStyle.NONE -> { /* No accents */ }
        }
    }
}

/**
 * Geometric brackets [ ] on left and right sides (Aura's Lab style)
 */
@Composable
private fun GeometricBrackets(
    color: Color,
    pulseAlpha: Float
) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val bracketWidth = 40f
        val bracketHeight = size.height * 0.4f
        val centerY = size.height / 2f
        val strokeWidth = 3f

        // Left bracket [
        drawPath(
            path = Path().apply {
                moveTo(bracketWidth, centerY - bracketHeight / 2f)
                lineTo(0f, centerY - bracketHeight / 2f)
                lineTo(0f, centerY + bracketHeight / 2f)
                lineTo(bracketWidth, centerY + bracketHeight / 2f)
            },
            color = color.copy(alpha = pulseAlpha),
            style = Stroke(width = strokeWidth)
        )

        // Right bracket ]
        drawPath(
            path = Path().apply {
                moveTo(size.width - bracketWidth, centerY - bracketHeight / 2f)
                lineTo(size.width, centerY - bracketHeight / 2f)
                lineTo(size.width, centerY + bracketHeight / 2f)
                lineTo(size.width - bracketWidth, centerY + bracketHeight / 2f)
            },
            color = color.copy(alpha = pulseAlpha),
            style = Stroke(width = strokeWidth)
        )

        // Horizontal lines extending from brackets
        drawLine(
            color = color.copy(alpha = pulseAlpha * 0.5f),
            start = Offset(bracketWidth, centerY - bracketHeight / 2f),
            end = Offset(bracketWidth + 60f, centerY - bracketHeight / 2f),
            strokeWidth = 1f
        )
        drawLine(
            color = color.copy(alpha = pulseAlpha * 0.5f),
            start = Offset(bracketWidth, centerY + bracketHeight / 2f),
            end = Offset(bracketWidth + 60f, centerY + bracketHeight / 2f),
            strokeWidth = 1f
        )
        drawLine(
            color = color.copy(alpha = pulseAlpha * 0.5f),
            start = Offset(size.width - bracketWidth - 60f, centerY - bracketHeight / 2f),
            end = Offset(size.width - bracketWidth, centerY - bracketHeight / 2f),
            strokeWidth = 1f
        )
        drawLine(
            color = color.copy(alpha = pulseAlpha * 0.5f),
            start = Offset(size.width - bracketWidth - 60f, centerY + bracketHeight / 2f),
            end = Offset(size.width - bracketWidth, centerY + bracketHeight / 2f),
            strokeWidth = 1f
        )
    }
}

/**
 * Massive angled diagonal cuts in corners (Project gate style)
 */
@Composable
private fun MassiveAngledShapes(
    primaryColor: Color,
    secondaryColor: Color,
    pulseAlpha: Float
) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val cornerSize = size.minDimension * 0.3f

        // Top-left massive angled shape (primary color)
        drawPath(
            path = Path().apply {
                moveTo(0f, 0f)
                lineTo(cornerSize, 0f)
                lineTo(0f, cornerSize)
                close()
            },
            color = primaryColor.copy(alpha = pulseAlpha * 0.8f)
        )

        // Diagonal lines in top-left
        for (i in 1..5) {
            val offset = i * 20f
            drawLine(
                color = Color.Black.copy(alpha = 0.3f),
                start = Offset(offset, 0f),
                end = Offset(0f, offset),
                strokeWidth = 2f
            )
        }

        // Bottom-right massive angled shape (secondary color)
        drawPath(
            path = Path().apply {
                moveTo(size.width, size.height)
                lineTo(size.width - cornerSize, size.height)
                lineTo(size.width, size.height - cornerSize)
                close()
            },
            color = secondaryColor.copy(alpha = pulseAlpha * 0.8f)
        )

        // Diagonal lines in bottom-right
        for (i in 1..5) {
            val offset = i * 20f
            drawLine(
                color = Color.Black.copy(alpha = 0.3f),
                start = Offset(size.width - offset, size.height),
                end = Offset(size.width, size.height - offset),
                strokeWidth = 2f
            )
        }

        // Small triangle accents scattered
        val triangleSize = 15f
        listOf(
            Offset(cornerSize + 40f, 30f),
            Offset(cornerSize + 80f, 60f),
            Offset(size.width - cornerSize - 40f, size.height - 30f),
            Offset(size.width - cornerSize - 80f, size.height - 60f)
        ).forEach { pos ->
            drawPath(
                path = Path().apply {
                    moveTo(pos.x, pos.y - triangleSize)
                    lineTo(pos.x - triangleSize, pos.y + triangleSize)
                    lineTo(pos.x + triangleSize, pos.y + triangleSize)
                    close()
                },
                color = primaryColor.copy(alpha = pulseAlpha * 0.3f),
                style = Stroke(width = 1f)
            )
        }
    }
}

/**
 * Asymmetric full-height rail on one side (Character gate style)
 */
@Composable
private fun AsymmetricRails(
    leftColor: Color,
    rightColor: Color,
    pulseAlpha: Float
) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val railWidth = 30f

        // Left full-height rail
        drawRect(
            color = leftColor.copy(alpha = pulseAlpha * 0.6f),
            topLeft = Offset(0f, 0f),
            size = Size(railWidth, size.height)
        )

        // Decorative elements on left rail
        for (i in 0..10) {
            val y = size.height * i / 10f
            drawCircle(
                color = Color.Black.copy(alpha = 0.5f),
                radius = 3f,
                center = Offset(railWidth / 2f, y)
            )
        }

        // Right bottom angular cut (smaller, asymmetric)
        val cutSize = size.minDimension * 0.2f
        drawPath(
            path = Path().apply {
                moveTo(size.width - cutSize, size.height)
                lineTo(size.width, size.height - cutSize)
                lineTo(size.width, size.height)
                close()
            },
            color = rightColor.copy(alpha = pulseAlpha * 0.7f)
        )

        // Angular lines in right cut
        drawLine(
            color = rightColor.copy(alpha = pulseAlpha),
            start = Offset(size.width - cutSize / 2f, size.height),
            end = Offset(size.width, size.height - cutSize / 2f),
            strokeWidth = 2f
        )
    }
}

/**
 * Small minimal corner marks
 */
@Composable
private fun SmallCornerMarks(
    color: Color,
    pulseAlpha: Float
) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val markLength = 30f
        val strokeWidth = 2f

        // Top-left
        drawLine(
            color = color.copy(alpha = pulseAlpha),
            start = Offset(0f, 0f),
            end = Offset(markLength, 0f),
            strokeWidth = strokeWidth
        )
        drawLine(
            color = color.copy(alpha = pulseAlpha),
            start = Offset(0f, 0f),
            end = Offset(0f, markLength),
            strokeWidth = strokeWidth
        )

        // Top-right
        drawLine(
            color = color.copy(alpha = pulseAlpha),
            start = Offset(size.width, 0f),
            end = Offset(size.width - markLength, 0f),
            strokeWidth = strokeWidth
        )
        drawLine(
            color = color.copy(alpha = pulseAlpha),
            start = Offset(size.width, 0f),
            end = Offset(size.width, markLength),
            strokeWidth = strokeWidth
        )

        // Bottom-right
        drawLine(
            color = color.copy(alpha = pulseAlpha),
            start = Offset(size.width, size.height),
            end = Offset(size.width - markLength, size.height),
            strokeWidth = strokeWidth
        )
        drawLine(
            color = color.copy(alpha = pulseAlpha),
            start = Offset(size.width, size.height),
            end = Offset(size.width, size.height - markLength),
            strokeWidth = strokeWidth
        )

        // Bottom-left
        drawLine(
            color = color.copy(alpha = pulseAlpha),
            start = Offset(0f, size.height),
            end = Offset(markLength, size.height),
            strokeWidth = strokeWidth
        )
        drawLine(
            color = color.copy(alpha = pulseAlpha),
            start = Offset(0f, size.height),
            end = Offset(0f, size.height - markLength),
            strokeWidth = strokeWidth
        )
    }
}

/**
 * Hexagonal nodes at corners (gaming/network style)
 */
@Composable
private fun HexagonalNodes(
    color: Color,
    pulseAlpha: Float
) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val nodeRadius = 20f
        val positions = listOf(
            Offset(nodeRadius * 2f, nodeRadius * 2f),
            Offset(size.width - nodeRadius * 2f, nodeRadius * 2f),
            Offset(size.width - nodeRadius * 2f, size.height - nodeRadius * 2f),
            Offset(nodeRadius * 2f, size.height - nodeRadius * 2f)
        )

        positions.forEach { pos ->
            // Hexagon shape
            drawPath(
                path = Path().apply {
                    val angle = 60f
                    for (i in 0..5) {
                        val rad = Math.toRadians((angle * i).toDouble())
                        val x = pos.x + (nodeRadius * kotlin.math.cos(rad)).toFloat()
                        val y = pos.y + (nodeRadius * kotlin.math.sin(rad)).toFloat()
                        if (i == 0) moveTo(x, y) else lineTo(x, y)
                    }
                    close()
                },
                color = color.copy(alpha = pulseAlpha * 0.5f),
                style = Stroke(width = 2f)
            )

            // Center dot
            drawCircle(
                color = color.copy(alpha = pulseAlpha),
                radius = 3f,
                center = pos
            )
        }
    }
}

/**
 * Circuit board pattern overlay
 */
@Composable
private fun CircuitPattern(
    color: Color,
    pulseAlpha: Float
) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        // Horizontal circuit lines
        listOf(
            size.height * 0.2f,
            size.height * 0.5f,
            size.height * 0.8f
        ).forEach { y ->
            drawLine(
                color = color.copy(alpha = pulseAlpha * 0.3f),
                start = Offset(0f, y),
                end = Offset(size.width, y),
                strokeWidth = 1f
            )

            // Circuit nodes
            for (x in 0..(size.width.toInt()) step 100) {
                drawCircle(
                    color = color.copy(alpha = pulseAlpha * 0.5f),
                    radius = 2f,
                    center = Offset(x.toFloat(), y)
                )
            }
        }

        // Vertical circuit lines
        listOf(
            size.width * 0.2f,
            size.width * 0.8f
        ).forEach { x ->
            drawLine(
                color = color.copy(alpha = pulseAlpha * 0.3f),
                start = Offset(x, 0f),
                end = Offset(x, size.height),
                strokeWidth = 1f
            )
        }
    }
}

