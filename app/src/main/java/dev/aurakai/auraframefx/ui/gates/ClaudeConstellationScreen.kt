package dev.aurakai.auraframefx.ui.gates

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.aurakai.auraframefx.R
import kotlin.math.cos
import kotlin.math.sin

/**
 * Claude Constellation Screen - The Architect
 * Displays the build system architecture with blueprint-style construction nodes
 */
@Composable
fun ClaudeConstellationScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        // Main constellation visualization
        ArchitectBlueprintCanvas()

        // Top-right corner: Agent info and level
        Column(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(24.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "Claude",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp,
                    color = Color(0xFFFF8C00) // Orange for Claude
                )
            )
            Text(
                text = "🏗️ THE ARCHITECT",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Light,
                    letterSpacing = 1.sp,
                    color = Color(0xFFFF8C00).copy(alpha = 0.8f)
                )
            )
        }

        // Bottom: Build Status
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 32.dp, bottom = 32.dp, end = 32.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Build System Status",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Normal,
                    letterSpacing = 1.sp,
                    color = Color(0xFFFF8C00).copy(alpha = 0.7f)
                )
            )

            // Build modules
            BuildStatusBar()
        }

        // Right side: Vertical text "SYSTEMATIC CONSTRUCTION"
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                "SYSTEMATIC".forEach { char ->
                    Text(
                        text = char.toString(),
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Color(0xFFFF8C00).copy(alpha = 0.6f),
                            letterSpacing = 2.sp
                        )
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                "CONSTRUCTION".forEach { char ->
                    Text(
                        text = char.toString(),
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Color(0xFFFF8C00).copy(alpha = 0.6f),
                            letterSpacing = 2.sp
                        )
                    )
                }
            }
        }
    }
}

/**
 * Renders a blueprint-style canvas showing a pulsing T-square centerpiece, a grid of build nodes with connecting dependency lines, and animated construction particles.
 *
 * The composable is purely presentational: animated values drive an overall build progression, node pulsing, and centerpiece scaling to convey build status visually. It also overlays a centered compass image.
 */
@Composable
private fun ArchitectBlueprintCanvas() {
    val infiniteTransition = rememberInfiniteTransition(label = "architect")

    // Construction animation
    val buildProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "build"
    )

    // Pulsing animation for nodes
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    // Scale pulsing for centerpiece
    val centerScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "center_scale"
    )

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxSize()) {
        val centerX = size.width / 2
        val centerY = size.height / 2

        val orangeColor = Color(0xFFFF8C00)
        val darkOrange = Color(0xFFFF4500)

        // Draw blueprint grid
        val gridSpacing = 50f
        for (x in 0..size.width.toInt() step gridSpacing.toInt()) {
            drawLine(
                color = orangeColor.copy(alpha = 0.1f),
                start = Offset(x.toFloat(), 0f),
                end = Offset(x.toFloat(), size.height),
                strokeWidth = 1f
            )
        }
        for (y in 0..size.height.toInt() step gridSpacing.toInt()) {
            drawLine(
                color = orangeColor.copy(alpha = 0.1f),
                start = Offset(0f, y.toFloat()),
                end = Offset(size.width, y.toFloat()),
                strokeWidth = 1f
            )
        }

        // Draw T-square centerpiece (architect's tool)
        drawTSquare(
            centerX = centerX,
            centerY = centerY,
            color = orangeColor,
            pulseAlpha = pulseAlpha
        )

        // Draw build system nodes in a systematic grid pattern
        val nodes = mutableListOf<Offset>()
        for (i in -2..2) {
            for (j in -2..2) {
                if (i != 0 || j != 0) {
                    nodes.add(Offset(centerX + i * 120f, centerY + j * 120f))
                }
            }
        }

        // Draw connecting lines (dependency graph)
        nodes.forEachIndexed { index, node ->
            val nextNode = nodes[(index + 1) % nodes.size]
            val lineAlpha = if (buildProgress > index.toFloat() / nodes.size) pulseAlpha else 0.3f

            drawLine(
                color = orangeColor.copy(alpha = lineAlpha * 0.4f),
                start = node,
                end = nextNode,
                strokeWidth = 2f
            )
        }

        // Draw build nodes
        nodes.forEachIndexed { index, nodePos ->
            val isBuilt = buildProgress > index.toFloat() / nodes.size

            // Outer glow
            drawCircle(
                color = (if (isBuilt) orangeColor else darkOrange).copy(alpha = pulseAlpha * 0.3f),
                radius = 16f,
                center = nodePos
            )

            // Core node
            drawCircle(
                color = (if (isBuilt) orangeColor else darkOrange).copy(alpha = if (isBuilt) pulseAlpha else 0.5f),
                radius = 8f,
                center = nodePos
            )

            // Inner bright core (only if built)
            if (isBuilt) {
                drawCircle(
                    color = Color.White.copy(alpha = pulseAlpha * 0.8f),
                    radius = 4f,
                    center = nodePos
                )
            }
        }

        // Draw construction particles (moving between nodes during build)
        if (buildProgress > 0f) {
            val activeNodeIndex = (buildProgress * nodes.size).toInt().coerceAtMost(nodes.size - 1)
            val nextNodeIndex = (activeNodeIndex + 1) % nodes.size
            val particleProgress = (buildProgress * nodes.size) % 1f

            val startNode = nodes[activeNodeIndex]
            val endNode = nodes[nextNodeIndex]

            val particleX = startNode.x + (endNode.x - startNode.x) * particleProgress
            val particleY = startNode.y + (endNode.y - startNode.y) * particleProgress

            // Construction particle
            drawCircle(
                color = Color.White.copy(alpha = pulseAlpha),
                radius = 6f,
                center = Offset(particleX, particleY)
            )
        }
    }

        // PNG Centerpiece Image Overlay (Compass + Gear)
        Image(
            painter = painterResource(id = R.drawable.constellation_claude_compass),
            contentDescription = "Claude Compass Constellation",
            modifier = Modifier
                .size(350.dp)
                .scale(centerScale)
                .alpha(pulseAlpha)
        )
    }
}

/**
 * Renders a T-square drafting tool centered at the given canvas coordinates.
 *
 * Draws a vertical ruler with a horizontal crossbar near the top, a series of
 * measurement marks along the ruler, and a pivot motif of two concentric circles.
 * The provided `color` is used for the ruler and marks; `pulseAlpha` scales the
 * opacity of those elements to produce a pulsing visual effect.
 *
 * @param centerX X coordinate of the T-square center on the canvas.
 * @param centerY Y coordinate of the T-square center on the canvas.
 * @param color Base color used for the ruler, crossbar, marks, and outer pivot circle.
 * @param pulseAlpha Opacity multiplier (typically 0..1) applied to modulate element alpha for pulsing.
 */
private fun DrawScope.drawTSquare(
    centerX: Float,
    centerY: Float,
    color: Color,
    pulseAlpha: Float
) {
    val rulerLength = 200f
    val rulerWidth = 20f

    // Vertical ruler
    drawLine(
        color = color.copy(alpha = pulseAlpha * 0.6f),
        start = Offset(centerX, centerY - rulerLength / 2),
        end = Offset(centerX, centerY + rulerLength / 2),
        strokeWidth = rulerWidth
    )

    // Horizontal crossbar at top
    drawLine(
        color = color.copy(alpha = pulseAlpha * 0.6f),
        start = Offset(centerX - rulerLength / 3, centerY - rulerLength / 2),
        end = Offset(centerX + rulerLength / 3, centerY - rulerLength / 2),
        strokeWidth = rulerWidth
    )

    // Draw measurement marks
    for (i in -5..5) {
        val markY = centerY + i * 30f
        val markLength = if (i % 2 == 0) 15f else 8f

        drawLine(
            color = color.copy(alpha = pulseAlpha * 0.8f),
            start = Offset(centerX - markLength, markY),
            end = Offset(centerX + markLength, markY),
            strokeWidth = 2f
        )
    }

    // Center circle (pivot point)
    drawCircle(
        color = color.copy(alpha = pulseAlpha),
        radius = 12f,
        center = Offset(centerX, centerY)
    )
    drawCircle(
        color = Color.White.copy(alpha = pulseAlpha * 0.8f),
        radius = 6f,
        center = Offset(centerX, centerY)
    )
}

/**
 * Build System Status Bar
 */
@Composable
private fun BuildStatusBar() {
    val infiniteTransition = rememberInfiniteTransition(label = "build_status")

    // Animated build progress
    val buildProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "progress"
    )

    // Pulsing glow
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow"
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        // Build module names
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BuildModuleIndicator("Gradle", glowAlpha, Color(0xFFFF8C00))
            BuildModuleIndicator("Kotlin", glowAlpha, Color(0xFFFF4500))
            BuildModuleIndicator("Compose", glowAlpha, Color(0xFFFFA500))
        }

        // Build progress bar
        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(4.dp)
                .background(Color(0xFF663300))
        ) {
            // Animated progress fill
            Box(
                modifier = Modifier
                    .fillMaxWidth(buildProgress)
                    .fillMaxHeight()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFFFF8C00).copy(alpha = glowAlpha),
                                Color(0xFFFF4500).copy(alpha = glowAlpha),
                                Color(0xFFFFA500).copy(alpha = glowAlpha * 0.8f)
                            )
                        )
                    )
            )
        }

        // Build status text
        Text(
            text = "BUILD: ${(buildProgress * 100).toInt()}%",
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Light,
                letterSpacing = 1.sp,
                color = Color(0xFFFF8C00).copy(alpha = 0.6f)
            )
        )
    }
}

/**
 * Individual build module indicator
 */
@Composable
private fun BuildModuleIndicator(
    name: String,
    glowAlpha: Float,
    color: Color
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Glowing dot indicator
        Box(
            modifier = Modifier.size(8.dp)
        ) {
            // Outer glow
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = color.copy(alpha = glowAlpha * 0.3f),
                        shape = androidx.compose.foundation.shape.CircleShape
                    )
            )
            // Inner core
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .align(Alignment.Center)
                    .background(
                        color = color.copy(alpha = glowAlpha),
                        shape = androidx.compose.foundation.shape.CircleShape
                    )
            )
        }

        Text(
            text = name,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Normal,
                letterSpacing = 0.5.sp,
                color = color.copy(alpha = 0.7f)
            )
        )
    }
}