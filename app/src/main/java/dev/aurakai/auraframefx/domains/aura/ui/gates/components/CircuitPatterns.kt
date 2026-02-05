package dev.aurakai.auraframefx.domains.aura.ui.gates.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import kotlin.math.sin
import kotlin.random.Random

/**
 * CIRCUIT PATTERN GENERATOR
 * Creates unique circuit board patterns for each gate
 */
enum class CircuitStyle {
    NEURAL_NETWORK,      // Oracle Drive - brain/neural paths
    TREE_BRANCHING,      // Root Tools - file system tree
    GRID_NETWORK,        // Agent Hub - mesh network
    FLOW_ORGANIC,        // CollabCanvas - creative flowing
    MOLECULAR,           // Aura's Lab - molecular bonds
    FIREWALL_GRID,       // Sentinel - security barriers
    SPECTRUM_WAVES,      // ChromaCore - color analyzer
    LAYOUT_GRID,         // Theme Engine - UI wireframes
    COMMAND_TREE,        // Terminal - directory structure
    CODE_SYNTAX,         // Code Assist - syntax tree
    HEXAGONAL_GRID,      // Sphere Grid - FFX style
    COMMUNICATION,       // Help Desk - signal waves
    HOOK_INJECTION,      // Xposed - framework hooks
    DESIGN_LAYERS,       // UI/UX Studio - layer structure
    USER_DATA_PATHS,     // System Journal - user connections
    MEMORY_CIRCUITS      // ROM Tools - memory/flash paths
}

@Composable
fun CircuitPattern(
    style: CircuitStyle,
    primaryColor: Color = Color(0xFF00FFFF), // Cyan
    pulseAlpha: Float = 1f,
    animationOffset: Float = 0f,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        when (style) {
            CircuitStyle.NEURAL_NETWORK -> drawNeuralNetwork(primaryColor, pulseAlpha, animationOffset)
            CircuitStyle.TREE_BRANCHING -> drawTreeBranching(primaryColor, pulseAlpha)
            CircuitStyle.GRID_NETWORK -> drawGridNetwork(primaryColor, pulseAlpha)
            CircuitStyle.FLOW_ORGANIC -> drawFlowOrganic(primaryColor, pulseAlpha, animationOffset)
            CircuitStyle.MOLECULAR -> drawMolecular(primaryColor, pulseAlpha)
            CircuitStyle.FIREWALL_GRID -> drawFirewallGrid(primaryColor, pulseAlpha)
            CircuitStyle.SPECTRUM_WAVES -> drawSpectrumWaves(primaryColor, pulseAlpha, animationOffset)
            CircuitStyle.LAYOUT_GRID -> drawLayoutGrid(primaryColor, pulseAlpha)
            CircuitStyle.COMMAND_TREE -> drawCommandTree(primaryColor, pulseAlpha)
            CircuitStyle.CODE_SYNTAX -> drawCodeSyntax(primaryColor, pulseAlpha)
            CircuitStyle.HEXAGONAL_GRID -> drawHexagonalGrid(primaryColor, pulseAlpha)
            CircuitStyle.COMMUNICATION -> drawCommunication(primaryColor, pulseAlpha, animationOffset)
            CircuitStyle.HOOK_INJECTION -> drawHookInjection(primaryColor, pulseAlpha)
            CircuitStyle.DESIGN_LAYERS -> drawDesignLayers(primaryColor, pulseAlpha)
            CircuitStyle.USER_DATA_PATHS -> drawUserDataPaths(primaryColor, pulseAlpha)
            CircuitStyle.MEMORY_CIRCUITS -> drawMemoryCircuits(primaryColor, pulseAlpha)
        }
    }
}

// ORACLE DRIVE - Neural network radiating from center
private fun DrawScope.drawNeuralNetwork(color: Color, alpha: Float, offset: Float) {
    val centerX = size.width / 2f
    val centerY = size.height / 2f
    val nodeCount = 24

    for (i in 0 until nodeCount) {
        val angle = (i * 360f / nodeCount) + offset * 0.1f
        val distance = size.minDimension * 0.35f
        val x = centerX + distance * kotlin.math.cos(Math.toRadians(angle.toDouble())).toFloat()
        val y = centerY + distance * sin(Math.toRadians(angle.toDouble())).toFloat()

        // Line from center to node
        drawLine(
            color = color.copy(alpha = alpha * 0.4f),
            start = Offset(centerX, centerY),
            end = Offset(x, y),
            strokeWidth = 2f
        )

        // Node circle
        drawCircle(
            color = color.copy(alpha = alpha * 0.6f),
            radius = 4f,
            center = Offset(x, y)
        )

        // Connect to neighboring nodes
        if (i < nodeCount - 1) {
            val nextAngle = ((i + 1) * 360f / nodeCount) + offset * 0.1f
            val nextX = centerX + distance * kotlin.math.cos(Math.toRadians(nextAngle.toDouble())).toFloat()
            val nextY = centerY + distance * sin(Math.toRadians(nextAngle.toDouble())).toFloat()

            drawLine(
                color = color.copy(alpha = alpha * 0.2f),
                start = Offset(x, y),
                end = Offset(nextX, nextY),
                strokeWidth = 1f
            )
        }
    }
}

// ROOT TOOLS - Branching tree structure
private fun DrawScope.drawTreeBranching(color: Color, alpha: Float) {
    val centerX = size.width / 2f
    val startY = size.height * 0.2f

    fun drawBranch(startX: Float, startY: Float, angle: Float, length: Float, depth: Int) {
        if (depth > 4 || length < 20f) return

        val endX = startX + length * kotlin.math.cos(Math.toRadians(angle.toDouble())).toFloat()
        val endY = startY + length * sin(Math.toRadians(angle.toDouble())).toFloat()

        drawLine(
            color = color.copy(alpha = alpha * (1f - depth * 0.15f)),
            start = Offset(startX, startY),
            end = Offset(endX, endY),
            strokeWidth = (5 - depth).toFloat()
        )

        // Draw child branches
        drawBranch(endX, endY, angle - 25f, length * 0.7f, depth + 1)
        drawBranch(endX, endY, angle + 25f, length * 0.7f, depth + 1)
    }

    drawBranch(centerX, startY, 90f, size.height * 0.15f, 0)
}

// AGENT HUB - Mesh network grid
private fun DrawScope.drawGridNetwork(color: Color, alpha: Float) {
    val spacing = 80f
    val rows = (size.height / spacing).toInt()
    val cols = (size.width / spacing).toInt()

    for (row in 0..rows) {
        for (col in 0..cols) {
            val x = col * spacing
            val y = row * spacing

            // Node
            drawCircle(
                color = color.copy(alpha = alpha * 0.5f),
                radius = 3f,
                center = Offset(x, y)
            )

            // Connect to right neighbor
            if (col < cols) {
                drawLine(
                    color = color.copy(alpha = alpha * 0.3f),
                    start = Offset(x, y),
                    end = Offset(x + spacing, y),
                    strokeWidth = 1f
                )
            }

            // Connect to bottom neighbor
            if (row < rows) {
                drawLine(
                    color = color.copy(alpha = alpha * 0.3f),
                    start = Offset(x, y),
                    end = Offset(x, y + spacing),
                    strokeWidth = 1f
                )
            }
        }
    }
}

// COLLABCANVAS - Organic flowing curves
private fun DrawScope.drawFlowOrganic(color: Color, alpha: Float, offset: Float) {
    for (i in 0..6) {
        val path = Path().apply {
            val startY = size.height * i / 6f
            moveTo(0f, startY)

            for (x in 0..size.width.toInt() step 50) {
                val wave = sin((x + offset * 2f) * 0.01f) * 30f
                lineTo(x.toFloat(), startY + wave)
            }
        }

        drawPath(
            path = path,
            color = color.copy(alpha = alpha * 0.3f),
            style = Stroke(width = 2f)
        )
    }
}

// AURA'S LAB - Molecular structure
private fun DrawScope.drawMolecular(color: Color, alpha: Float) {
    val molecules = listOf(
        Offset(size.width * 0.3f, size.height * 0.3f),
        Offset(size.width * 0.7f, size.height * 0.3f),
        Offset(size.width * 0.5f, size.height * 0.6f),
        Offset(size.width * 0.2f, size.height * 0.7f),
        Offset(size.width * 0.8f, size.height * 0.7f)
    )

    // Draw bonds
    for (i in molecules.indices) {
        for (j in i + 1 until molecules.size) {
            drawLine(
                color = color.copy(alpha = alpha * 0.3f),
                start = molecules[i],
                end = molecules[j],
                strokeWidth = 1.5f
            )
        }
    }

    // Draw atoms
    molecules.forEach { pos ->
        drawCircle(
            color = color.copy(alpha = alpha * 0.7f),
            radius = 12f,
            center = pos,
            style = Stroke(width = 2f)
        )
        drawCircle(
            color = color.copy(alpha = alpha * 0.4f),
            radius = 6f,
            center = pos
        )
    }
}

// SENTINEL - Firewall hexagonal grid
private fun DrawScope.drawFirewallGrid(color: Color, alpha: Float) {
    val hexSize = 40f
    val rows = (size.height / hexSize).toInt() + 2
    val cols = (size.width / hexSize).toInt() + 2

    for (row in 0..rows) {
        for (col in 0..cols) {
            val xOffset = if (row % 2 == 0) 0f else hexSize * 0.866f
            val x = col * hexSize * 1.732f + xOffset
            val y = row * hexSize * 1.5f

            drawHexagon(Offset(x, y), hexSize, color, alpha)
        }
    }
}

private fun DrawScope.drawHexagon(center: Offset, radius: Float, color: Color, alpha: Float) {
    val path = Path().apply {
        for (i in 0..5) {
            val angle = 60f * i
            val x = center.x + radius * kotlin.math.cos(Math.toRadians(angle.toDouble())).toFloat()
            val y = center.y + radius * sin(Math.toRadians(angle.toDouble())).toFloat()
            if (i == 0) moveTo(x, y) else lineTo(x, y)
        }
        close()
    }

    drawPath(
        path = path,
        color = color.copy(alpha = alpha * 0.2f),
        style = Stroke(width = 1f)
    )
}

// Placeholder implementations for other styles (keeping it moving!)
private fun DrawScope.drawSpectrumWaves(color: Color, alpha: Float, offset: Float) = drawFlowOrganic(color, alpha, offset)
private fun DrawScope.drawLayoutGrid(color: Color, alpha: Float) = drawGridNetwork(color, alpha)
private fun DrawScope.drawCommandTree(color: Color, alpha: Float) = drawTreeBranching(color, alpha)
private fun DrawScope.drawCodeSyntax(color: Color, alpha: Float) = drawTreeBranching(color, alpha)
private fun DrawScope.drawHexagonalGrid(color: Color, alpha: Float) = drawFirewallGrid(color, alpha)
private fun DrawScope.drawCommunication(color: Color, alpha: Float, offset: Float) = drawFlowOrganic(color, alpha, offset)
private fun DrawScope.drawHookInjection(color: Color, alpha: Float) = drawGridNetwork(color, alpha)
private fun DrawScope.drawDesignLayers(color: Color, alpha: Float) = drawLayoutGrid(color, alpha)
private fun DrawScope.drawUserDataPaths(color: Color, alpha: Float) = drawNeuralNetwork(color, alpha, 0f)
private fun DrawScope.drawMemoryCircuits(color: Color, alpha: Float) = drawGridNetwork(color, alpha)

