package dev.aurakai.auraframefx.domains.aura.spheregrid

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import dev.aurakai.auraframefx.domains.aura.ui.theme.NeonCyan
import dev.aurakai.auraframefx.ui.background.BackgroundAssetManager
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun SphereGridScreen() {
    val nodeCount = 12
    val activatedNodes = remember { mutableStateListOf(0, 1, 2) }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFF020205))) {
        BackgroundAssetManager.DomainBackground(
            backgroundRes = BackgroundAssetManager.agentNexus,
            alpha = 0.5f
        )

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectDragGestures { _, _ -> }
                }
        ) {
            drawSphereGridCore(nodeCount, activatedNodes)
        }
    }
}

private fun DrawScope.drawSphereGridCore(nodeCount: Int, activated: List<Int>) {
    val center = Offset(size.width / 2, size.height / 2)
    val radius = size.minDimension * 0.38f

    // Central core
    drawCircle(
        brush = Brush.radialGradient(
            listOf(NeonCyan, Color.Transparent),
            center, radius * 0.45f
        ),
        radius = radius * 0.45f
    )

    // Orbiting nodes
    for (i in 0 until nodeCount) {
        val angle = (i * (2 * PI / nodeCount)).toFloat()
        val x = center.x + cos(angle) * radius
        val y = center.y + sin(angle) * radius

        val isActive = i in activated

        drawCircle(
            color = if (isActive) NeonCyan else NeonCyan.copy(alpha = 0.2f),
            radius = 32f,
            center = Offset(x, y)
        )

        drawCircle(
            brush = Brush.radialGradient(
                listOf(NeonCyan, Color.Transparent),
                Offset(x, y), 16f
            ),
            radius = 16f,
            center = Offset(x, y)
        )
    }
}
