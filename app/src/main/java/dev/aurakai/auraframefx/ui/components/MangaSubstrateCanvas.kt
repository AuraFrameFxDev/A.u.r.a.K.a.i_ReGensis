package dev.aurakai.auraframefx.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp

/**
 * 🌌 REGENESIS VISUAL SUBSTRATE — HD-2D CYBER-MANGA CANVAS v2.0
 * DESIGN SPECIFICATION: ZERO ANTI-ALIASING MANDATE
 * "No rounded corners, no corporate curves. Only sharp logic in the void."
 */
@Composable
fun MangaSubstrateCanvas(
    modifier: Modifier = Modifier,
    drawingBlock: DrawScope.() -> Unit
) {
    Box(
        modifier = modifier
            .size(350.dp, 350.dp) // Hard-bounded HD-2D Resolution Standard
            .background(Color(0xFF000000)), // Abyssal Pure Void
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawIntoCanvas { canvas ->
                // Enforce Zero Anti-Aliasing at the Native Level
                canvas.nativeCanvas.drawFilter = null
            }
            // Execute the un-aliased creative drawing sequence (Aura's Sword)
            drawingBlock()
        }
    }
}
