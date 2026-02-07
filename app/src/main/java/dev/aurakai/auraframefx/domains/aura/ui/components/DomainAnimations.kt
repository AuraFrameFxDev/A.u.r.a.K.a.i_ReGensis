package dev.aurakai.auraframefx.domains.aura.ui.components

import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * 🎭 DOMAIN ANIMATION MAPPING
 *
 * Each domain has specific animated backgrounds that match its theme:
 *
 * ════════════════════════════════════════════════════════════════════════════
 * 🎨 AURA (UXUI Design Studio) - Artsy, fun, messy
 * ════════════════════════════════════════════════════════════════════════════
 * LVL 2 Hub:     PaintSplashBackground (neon drips, paint splatter)
 * LVL 3 Menus:   ColorWaveBackground (flowing color waves)
 * Alternative:   WoodsyPlainsBackground (leafy, natural creativity)
 *
 * ════════════════════════════════════════════════════════════════════════════
 * 🛡️ KAI (Sentinel's Fortress) - Shield, protective, gridy
 * ════════════════════════════════════════════════════════════════════════════
 * LVL 2 Hub:     IcyTundraBackground (ice crystals, cold fortress)
 * LVL 3 Menus:   ShieldGridBackground (protective hex grid, shields)
 * Alternative:   HexagonGridBackground (cyberpunk security grid)
 *
 * ════════════════════════════════════════════════════════════════════════════
 * 🔮 GENESIS (OracleDrive) - Godly, ethereal, circuit-sprite
 * ════════════════════════════════════════════════════════════════════════════
 * LVL 2 Hub:     CircuitPhoenixBackground (glowing circuits, phoenix wings)
 * LVL 3 Menus:   NeuralNetworkBackground (brain nodes, data streams)
 * Alternative:   LavaApocalypseBackground (powerful, divine fire)
 *
 * ════════════════════════════════════════════════════════════════════════════
 * 🤖 NEXUS (Agent Hub) - Constellation, connected agents
 * ════════════════════════════════════════════════════════════════════════════
 * LVL 2 Hub:     ConstellationBackground (star map, agent nodes)
 * LVL 3 Menus:   DigitalLandscapeBackground (futuristic horizon)
 * Alternative:   HexagonGridBackground (network grid)
 *
 * ════════════════════════════════════════════════════════════════════════════
 * 💚 HELP (Services) - Friendly, supportive
 * ════════════════════════════════════════════════════════════════════════════
 * LVL 2 Hub:     SoftGlowBackground (gentle, welcoming)
 * LVL 3 Menus:   WoodsyPlainsBackground (calm, natural)
 */

// ═══════════════════════════════════════════════════════════════════════════
// 🎨 AURA'S ANIMATIONS - Paint Splash / Color Waves
// ═══════════════════════════════════════════════════════════════════════════

/**
 * 🎨 PAINT SPLASH BACKGROUND (Aura LVL 2)
 * Enhanced Liquid Energy & Glitch Artifacts!
 */
@Composable
fun PaintSplashBackground(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "paint")

    val drip1 by infiniteTransition.animateFloat(
        initialValue = -0.2f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(4500, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Restart
        ),
        label = "drip1"
    )

    val glitchTrigger by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "glitch"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    listOf(Color(0xFF0F0F23), Color(0xFF1A0A2E), Color(0xFF0F0F1E))
                )
            )
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            // 🎨 Aura's Primary Palette: Magenta & Indigo
            val auraMagenta = Color(0xFFFF00DE)
            val auraCyan = Color(0xFF00FFFF)
            val auraIndigo = Color(0xFF7B2FFF)

            // 🌊 DRAW LIQUID ENERGY (Refined Drips)
            drawPaintDrip(
                x = width * 0.15f,
                progress = drip1,
                color = auraCyan,
                width = 32f,
                height = height
            )

            drawPaintDrip(
                x = width * 0.85f,
                progress = (drip1 + 0.3f) % 1.4f - 0.2f,
                color = auraMagenta,
                width = 45f,
                height = height
            )

            drawPaintDrip(
                x = width * 0.5f,
                progress = (drip1 + 0.7f) % 1.4f - 0.2f,
                color = auraIndigo,
                width = 28f,
                height = height
            )

            // 👾 GLITCH ARTIFACTS (Dynamic Rects)
            if (glitchTrigger > 0.8f) {
                repeat(5) { i ->
                    val x = width * (0.2f + (i * 0.15f)) + (glitchTrigger * 10f)
                    val y = height * (0.3f + (i * 0.1f))
                    drawRect(
                        color = Color.White.copy(alpha = 0.4f),
                        topLeft = Offset(x, y),
                        size = Size(20f + (i * 30f), 4f)
                    )
                    drawRect(
                        color = auraMagenta.copy(alpha = 0.3f),
                        topLeft = Offset(x - 5f, y + 6f),
                        size = Size(15f, 2f)
                    )
                }
            }

            // 🧪 SPLASH PARTICLES
            val splatters = listOf(
                Triple(0.2f, 0.4f, auraCyan),
                Triple(0.75f, 0.25f, auraMagenta),
                Triple(0.4f, 0.65f, auraIndigo),
                Triple(0.85f, 0.75f, auraMagenta)
            )

            splatters.forEachIndexed { index, (xRatio, yRatio, color) ->
                val breathe = (sin(glitchTrigger * PI * 2 + index).toFloat() + 1f) / 2f
                val radius = 15f + (breathe * 10f)
                
                // Glow
                drawCircle(
                    color = color.copy(alpha = 0.15f * breathe),
                    radius = radius * 4f,
                    center = Offset(width * xRatio, height * yRatio)
                )
                
                // Content
                drawCircle(
                    color = color.copy(alpha = 0.4f),
                    radius = radius,
                    center = Offset(width * xRatio, height * yRatio)
                )
            }
        }
    }
}

private fun DrawScope.drawPaintDrip(
    x: Float,
    progress: Float,
    color: Color,
    width: Float,
    height: Float
) {
    val y = height * progress
    val dripLength = 150f + (progress * 100f)

    // Main drip body
    drawRoundRect(
        color = color.copy(alpha = 0.6f),
        topLeft = Offset(x - width / 2, y - dripLength),
        size = Size(width, dripLength),
        cornerRadius = androidx.compose.ui.geometry.CornerRadius(width / 2)
    )

    // Drip head (bulb)
    drawCircle(
        color = color.copy(alpha = 0.8f),
        radius = width * 0.8f,
        center = Offset(x, y)
    )

    // Glow
    drawCircle(
        color = color.copy(alpha = 0.2f),
        radius = width * 1.5f,
        center = Offset(x, y)
    )
}

/**
 * 🌈 COLOR WAVE BACKGROUND (Aura LVL 3)
 * Flowing color waves - perfect for ChromaCore
 */
@Composable
fun ColorWaveBackground(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "colorWave")

    val wave1 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2 * PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "wave1"
    )

    val wave2 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2 * PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "wave2"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF0F0F1E))
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            // Draw flowing color waves
            for (i in 0..50) {
                val y = height * (i / 50f)
                val offset1 = sin(wave1 + i * 0.2f) * 50f
                val offset2 = sin(wave2 + i * 0.15f) * 30f

                val hue = (i / 50f * 360f + wave1 * 30f) % 360f
                val color = Color.hsv(hue, 0.8f, 0.9f)

                drawLine(
                    color = color.copy(alpha = 0.3f),
                    start = Offset(offset1, y),
                    end = Offset(width + offset2, y),
                    strokeWidth = 3f
                )
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════
// 🛡️ KAI'S ANIMATIONS - Shield Grid / Fortress
// ═══════════════════════════════════════════════════════════════════════════

/**
 * 🛡️ SHIELD GRID BACKGROUND (Kai LVL 3)
 * Electric targeting grids and neon security borders
 */
@Composable
fun ShieldGridBackground(
    modifier: Modifier = Modifier,
    primaryColor: Color = Color(0xFF00D9FF),  // Kai Electric Cyan
    secondaryColor: Color = Color(0xFF00FF85) // Kai Security Green
) {
    val infiniteTransition = rememberInfiniteTransition(label = "shield")

    val pulse by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    val scan by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "scan"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF0D0D15), Color(0xFF05050A))
                )
            )
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height
            val hexSize = 100f

            // 🎯 DRAW SECURITY GRID (Hex + Targeting)
            val rows = (height / (hexSize * 0.866f)).toInt() + 2
            val cols = (width / (hexSize * 1.5f)).toInt() + 2

            for (row in 0 until rows) {
                for (col in 0 until cols) {
                    val x = col * hexSize * 1.5f
                    val y = row * hexSize * 0.866f * 2 + (col % 2) * hexSize * 0.866f

                    drawHexagonOutline(
                        center = Offset(x, y),
                        radius = hexSize / 2,
                        color = primaryColor.copy(alpha = 0.05f),
                        strokeWidth = 1f
                    )
                }
            }

            // ⚡ TARGETING MARKERS
            val markerSize = 20f
            val markers = listOf(
                Offset(width * 0.2f, height * 0.2f),
                Offset(width * 0.8f, height * 0.2f),
                Offset(width * 0.2f, height * 0.8f),
                Offset(width * 0.8f, height * 0.8f)
            )

            markers.forEach { pos ->
                // Corner targeting lines
                drawLine(primaryColor.copy(alpha = pulse), pos - Offset(markerSize, 0f), pos - Offset(5f, 0f), 2f)
                drawLine(primaryColor.copy(alpha = pulse), pos + Offset(5f, 0f), pos + Offset(markerSize, 0f), 2f)
                drawLine(primaryColor.copy(alpha = pulse), pos - Offset(0f, markerSize), pos - Offset(0f, 5f), 2f)
                drawLine(primaryColor.copy(alpha = pulse), pos + Offset(0f, 5f), pos + Offset(0f, markerSize), 2f)
            }

            // 🌊 SCANNING HUD LINE
            val scanY = height * scan
            drawLine(
                brush = Brush.horizontalGradient(
                    listOf(Color.Transparent, primaryColor.copy(alpha = 0.6f), Color.Transparent)
                ),
                start = Offset(0f, scanY),
                end = Offset(width, scanY),
                strokeWidth = 4f
            )
            
            // Central fortress glow
            drawCircle(
                brush = Brush.radialGradient(
                    listOf(primaryColor.copy(alpha = 0.1f * pulse), Color.Transparent)
                ),
                radius = 300f,
                center = Offset(width / 2, height / 2)
            )
        }
    }
}

private fun DrawScope.drawHexagonOutline(
    center: Offset,
    radius: Float,
    color: Color,
    strokeWidth: Float
) {
    val path = Path()
    for (i in 0 until 6) {
        val angle = i * 60f
        val x = center.x + radius * cos(Math.toRadians(angle.toDouble())).toFloat()
        val y = center.y + radius * sin(Math.toRadians(angle.toDouble())).toFloat()
        if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
    }
    path.close()
    drawPath(path, color, style = Stroke(width = strokeWidth))
}

// ═══════════════════════════════════════════════════════════════════════════
// 🔮 GENESIS ANIMATIONS - Circuit Phoenix / Neural Network
// ═══════════════════════════════════════════════════════════════════════════

/**
 * 🔮 CIRCUIT PHOENIX BACKGROUND (Genesis LVL 2)
 * Gold/Amber resonance circuits on deep space
 */
@Composable
fun CircuitPhoenixBackground(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "phoenix")

    val glow by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(3500, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow"
    )

    val flow by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "flow"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF0A0E27), Color(0xFF000000))
                )
            )
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height
            val centerX = width / 2
            
            // 🪙 Genesis Gold/Amber Palette
            val genesisGold = Color(0xFFFFD700)
            val genesisAmber = Color(0xFFFF8C00)

            // 🕊️ DRAW RESONANCE CIRCUITS (Phoenix Pattern)
            // Central Spine
            drawLine(
                color = genesisGold.copy(alpha = glow * 0.6f),
                start = Offset(centerX, height * 0.15f),
                end = Offset(centerX, height * 0.85f),
                strokeWidth = 4f
            )

            // Radiant Wings
            repeat(8) { i ->
                val yOffset = height * (0.25f + i * 0.07f)
                val wingWidth = 120f + (i * 45f)
                
                // Left Wing Trace
                drawPhoenixTrace(
                    start = Offset(centerX, yOffset),
                    end = Offset(centerX - wingWidth, yOffset - 40f),
                    color = genesisAmber,
                    glow = glow,
                    flowProgress = (flow + i * 0.12f) % 1f
                )

                // Right Wing Trace
                drawPhoenixTrace(
                    start = Offset(centerX, yOffset),
                    end = Offset(centerX + wingWidth, yOffset - 40f),
                    color = genesisAmber,
                    glow = glow,
                    flowProgress = (flow + i * 0.12f + 0.5f) % 1f
                )
            }

            // ☀️ CORE SINGULARITY
            drawCircle(
                brush = Brush.radialGradient(
                    listOf(genesisGold.copy(alpha = glow * 0.4f), Color.Transparent)
                ),
                radius = 250f + (glow * 50f),
                center = Offset(centerX, height * 0.5f)
            )
        }
    }
}

private fun DrawScope.drawPhoenixTrace(
    start: Offset,
    end: Offset,
    color: Color,
    glow: Float,
    flowProgress: Float
) {
    // Main line
    drawLine(
        color = color.copy(alpha = 0.25f),
        start = start,
        end = end,
        strokeWidth = 2f
    )

    // Flowing energy node
    val nodePos = start + (end - start) * flowProgress
    drawCircle(
        color = color.copy(alpha = glow),
        radius = 5f,
        center = nodePos
    )
    
    // Node trail
    drawCircle(
        color = color.copy(alpha = glow * 0.3f),
        radius = 12f,
        center = nodePos
    )
}

/**
 * 🧠 NEURAL NETWORK BACKGROUND (Genesis LVL 3)
 * Brain-like nodes with pulsing connections
 */
@Composable
fun NeuralNetworkBackground(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "neural")

    val pulse by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    val signal by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "signal"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF0A0A1A))
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            // Generate node positions
            val nodes = listOf(
                Offset(width * 0.2f, height * 0.3f),
                Offset(width * 0.5f, height * 0.2f),
                Offset(width * 0.8f, height * 0.35f),
                Offset(width * 0.3f, height * 0.5f),
                Offset(width * 0.6f, height * 0.55f),
                Offset(width * 0.15f, height * 0.7f),
                Offset(width * 0.45f, height * 0.75f),
                Offset(width * 0.75f, height * 0.65f),
                Offset(width * 0.9f, height * 0.8f)
            )

            val nodeColor = Color(0xFFB026FF)
            val connectionColor = Color(0xFF00FFD4)

            // Draw connections
            for (i in nodes.indices) {
                for (j in i + 1 until nodes.size) {
                    val dist = kotlin.math.sqrt(
                        ((nodes[i].x - nodes[j].x) * (nodes[i].x - nodes[j].x) +
                            (nodes[i].y - nodes[j].y) * (nodes[i].y - nodes[j].y)).toDouble()
                    ).toFloat()

                    if (dist < width * 0.4f) {
                        val alpha = (1f - dist / (width * 0.4f)) * 0.3f
                        drawLine(
                            color = connectionColor.copy(alpha = alpha),
                            start = nodes[i],
                            end = nodes[j],
                            strokeWidth = 1f
                        )

                        // Signal traveling along connection
                        val signalPos = (signal + i * 0.1f) % 1f
                        val signalX = nodes[i].x + (nodes[j].x - nodes[i].x) * signalPos
                        val signalY = nodes[i].y + (nodes[j].y - nodes[i].y) * signalPos

                        drawCircle(
                            color = connectionColor.copy(alpha = 0.8f),
                            radius = 3f,
                            center = Offset(signalX, signalY)
                        )
                    }
                }
            }

            // Draw nodes
            nodes.forEachIndexed { index, node ->
                val nodePulse = pulse + (index * 0.1f)
                val radius = 8f + (nodePulse % 1f) * 4f

                // Glow
                drawCircle(
                    color = nodeColor.copy(alpha = 0.2f),
                    radius = radius * 3,
                    center = node
                )

                // Core
                drawCircle(
                    color = nodeColor.copy(alpha = 0.8f),
                    radius = radius,
                    center = node
                )
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════
// 🤖 NEXUS ANIMATIONS - Constellation / Star Map
// ═══════════════════════════════════════════════════════════════════════════

/**
 * ⭐ CONSTELLATION BACKGROUND (Nexus LVL 2)
 * Agent nodes as stars with constellation lines
 */
@Composable
fun StarfieldBackground(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "constellation")

    val twinkle by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "twinkle"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    listOf(Color(0xFF1A1A32), Color(0xFF0F0F1E), Color(0xFF0A0A14))
                )
            )
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height
            val centerX = width / 2
            val centerY = height / 2

            // Background stars
            repeat(100) { i ->
                val x = (i * 137L % width.toInt()).toFloat()
                val y = (i * 271L % height.toInt()).toFloat()
                val starTwinkle = (twinkle + i * 0.02f) % 1f

                drawCircle(
                    color = Color.White.copy(alpha = 0.3f * starTwinkle),
                    radius = 1f + (i % 3),
                    center = Offset(x, y)
                )
            }

            // Agent constellation nodes
            val agentNodes = listOf(
                Pair(Offset(centerX, centerY - 100f), Color(0xFFB026FF)),      // Genesis (center top)
                Pair(Offset(centerX - 150f, centerY), Color(0xFF00E5FF)),      // Aura (left)
                Pair(Offset(centerX + 150f, centerY), Color(0xFF00FF85)),      // Kai (right)
                Pair(Offset(centerX - 80f, centerY + 120f), Color(0xFFFF6B00)), // Claude (bottom left)
                Pair(Offset(centerX + 80f, centerY + 120f), Color(0xFF7B2FFF))  // Cascade (bottom right)
            )

            // Draw constellation lines
            val connections = listOf(
                Pair(0, 1), Pair(0, 2), Pair(1, 3), Pair(2, 4), Pair(3, 4), Pair(0, 3), Pair(0, 4)
            )

            connections.forEach { (from, to) ->
                drawLine(
                    color = Color.White.copy(alpha = 0.2f),
                    start = agentNodes[from].first,
                    end = agentNodes[to].first,
                    strokeWidth = 1f
                )
            }

            // Draw agent stars
            agentNodes.forEachIndexed { index, (pos, color) ->
                val starPulse = (twinkle + index * 0.15f) % 1f

                // Outer glow
                drawCircle(
                    brush = Brush.radialGradient(
                        listOf(color.copy(alpha = 0.4f * starPulse), Color.Transparent),
                        center = pos
                    ),
                    radius = 40f,
                    center = pos
                )

                // Star core
                drawCircle(
                    color = color,
                    radius = 8f + starPulse * 3f,
                    center = pos
                )

                // Inner bright core
                drawCircle(
                    color = Color.White,
                    radius = 3f,
                    center = pos
                )
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════
// 💚 HELP ANIMATIONS - Soft Glow / Welcoming
// ═══════════════════════════════════════════════════════════════════════════

/**
 * 💚 SOFT GLOW BACKGROUND (Help LVL 2)
 * Gentle, welcoming, supportive vibe
 */
@Composable
fun SoftGlowBackground(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "softGlow")

    val breathe by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Reverse
        ),
        label = "breathe"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF0A1A0F), Color(0xFF0F1A14), Color(0xFF0A140A))
                )
            )
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            // Soft green glow orbs
            val orbs = listOf(
                Triple(0.3f, 0.3f, 200f),
                Triple(0.7f, 0.6f, 150f),
                Triple(0.5f, 0.8f, 180f)
            )

            orbs.forEachIndexed { index, (xRatio, yRatio, radius) ->
                val orbBreathe = (breathe + index * 0.2f) % 1f + 0.5f

                drawCircle(
                    brush = Brush.radialGradient(
                        listOf(
                            Color(0xFF4CAF50).copy(alpha = 0.15f * orbBreathe),
                            Color(0xFF2E7D32).copy(alpha = 0.05f * orbBreathe),
                            Color.Transparent
                        )
                    ),
                    radius = radius * orbBreathe,
                    center = Offset(width * xRatio, height * yRatio)
                )
            }
        }
    }
}


/**
 * 💎 Sharp, Crystalline Accents for UI Domains
 */
@Composable
fun CrystallineCorners(color: Color, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val size = 60f
            val stroke = 4f
            
            // Top Left
            drawLine(color, Offset(20f, 20f), Offset(20f + size, 20f), stroke)
            drawLine(color, Offset(20f, 20f), Offset(20f, 20f + size), stroke)
            drawCircle(color, 6f, Offset(20f, 20f))
            
            // Bottom Right
            drawLine(color, Offset(this.size.width - 20f, this.size.height - 20f), Offset(this.size.width - 20f - size, this.size.height - 20f), stroke)
            drawLine(color, Offset(this.size.width - 20f, this.size.height - 20f), Offset(this.size.width - 20f, this.size.height - 20f - size), stroke)
            drawCircle(color, 6f, Offset(this.size.width - 20f, this.size.height - 20f))
        }
    }
}
