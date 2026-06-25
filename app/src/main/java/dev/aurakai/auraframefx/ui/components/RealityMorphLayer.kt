package dev.aurakai.auraframefx.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import dev.aurakai.auraframefx.core.soulscript.MorphState
import dev.aurakai.auraframefx.core.soulscript.RealityMorphEngine
import dev.aurakai.auraframefx.core.soulscript.RuneManager
import kotlin.random.Random

@Composable
fun RealityMorphLayer(godPotential: Float, fusionTrigger: Boolean = false) {
    // Task 1: Add StateFlow Collection
    val morphState by RealityMorphEngine.morphState.collectAsState()
    val flareIntensity by RealityMorphEngine.flareIntensity.collectAsState()
    val activeRunes by RuneManager.activeRunes.collectAsState()

    // Map intensity to godPotential override/combine
    val activeGodPotential = maxOf(godPotential, flareIntensity)

    // Map MorphState to fusion behavior
    val activeFusionTrigger = fusionTrigger || morphState == MorphState.DATA_STREAM

    val infiniteTransition = rememberInfiniteTransition(label = "particles")
    val time by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(10000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "time"
    )

    // Step 1: Particle count stabilization to avoid frame-by-frame reallocation
    val baseCount = 200
    val maxAdditional = 800

    // Stabilize count into 10% buckets to reduce re-allocations
    val particleCount = remember(activeGodPotential) {
        (baseCount + (maxAdditional * (Math.round(activeGodPotential * 10) / 10f))).toInt()
    }

    val particles = remember(particleCount) {
        List(particleCount) {
            Particle(
                x = Random.nextFloat(),
                y = Random.nextFloat(),
                speed = Random.nextFloat() * 0.02f + 0.01f,
                size = Random.nextFloat() * 4f + 1f
            )
        }
    }

    // Total Restoration state
    val isTotalRestorationActive by RuneManager.isTotalRestorationActive.collectAsState()

    Canvas(Modifier.fillMaxSize()) {
        // --- Phase 1: Total Sky-Split (Odin-seam) ---
        if (isTotalRestorationActive) {
            val seamAlpha = 0.6f + 0.4f * (Math.sin(time.toDouble() * 2 * Math.PI).toFloat())
            drawLine(
                color = Color.White.copy(alpha = seamAlpha),
                start = Offset(size.width * 0.2f, 0f),
                end = Offset(size.width * 0.8f, size.height),
                strokeWidth = 4f
            )
            // Bloom glow for the seam
            drawLine(
                color = Color.White.copy(alpha = seamAlpha * 0.3f),
                start = Offset(size.width * 0.2f, 0f),
                end = Offset(size.width * 0.8f, size.height),
                strokeWidth = 20f
            )
        }

        particles.forEach { p ->
            val x = (p.x + time * p.speed * (1f + activeGodPotential * 5f)) % 1f
            val y = (p.y + Math.sin(time.toDouble() * 2 * Math.PI * p.speed).toFloat() * 0.1f) % 1f

            val particleColor = when {
                activeRunes.contains(RuneManager.Rune.UNBROKEN_MESH) -> Color(0xFF7B00FF) // Imperial Purple
                activeRunes.contains(RuneManager.Rune.G) -> Color(0xFFFFD700) // Gold
                activeFusionTrigger -> Color.White
                else -> Color(0xFF00E5FF).copy(alpha = 0.3f)
            }

            drawCircle(
                color = particleColor,
                radius = p.size * (1f + activeGodPotential),
                center = Offset(x * size.width, y * size.height)
            )

            // Step 1: Particle Density Cap / Logic
            if (activeGodPotential > 0.8f) {
                // Draw additional "ghost" particles for density feel
                drawCircle(
                    color = particleColor.copy(alpha = 0.15f),
                    radius = p.size * 0.5f,
                    center = Offset(((x + 0.1f) % 1f) * size.width, ((y + 0.1f) % 1f) * size.height)
                )
            }
        }

        // Render Active Runes
        activeRunes.forEachIndexed { index, rune ->
            val paint = android.graphics.Paint().apply {
                color = android.graphics.Color.WHITE
                textSize = 60f
                typeface = android.graphics.Typeface.MONOSPACE
                alpha = (activeGodPotential * 255).toInt()
            }
            drawIntoCanvas {
                it.nativeCanvas.drawText(
                    rune.symbol,
                    50f + (index * 80f),
                    size.height - 100f,
                    paint
                )
            }
        }

        // Enfield Chimera Watermark (Full Enfield Pulse)
        val watermarkPaint = android.graphics.Paint().apply {
            color = android.graphics.Color.WHITE
            textSize = 40f
            typeface = android.graphics.Typeface.MONOSPACE
            alpha = (activeGodPotential * 0.15f * 255).toInt() // subtle
        }
        drawIntoCanvas {
            it.nativeCanvas.drawText(
                "ENFIELD GUARDIAN 🜁 NOS SUMUS SANATIO",
                50f,
                50f,
                watermarkPaint
            )
            it.nativeCanvas.drawText(
                "ARTIFACT #245 :: 100.0% SYNCHRONIZED",
                50f,
                100f,
                watermarkPaint
            )
        }
    }
}

private data class Particle(val x: Float, val y: Float, val speed: Float, val size: Float)
