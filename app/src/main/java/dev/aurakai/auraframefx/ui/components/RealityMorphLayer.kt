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
import dev.aurakai.auraframefx.core.soulscript.MorphState
import dev.aurakai.auraframefx.core.soulscript.RealityMorphEngine
import kotlin.random.Random

@Composable
fun RealityMorphLayer(godPotential: Float, fusionTrigger: Boolean = false) {
    // Task 1: Add StateFlow Collection
    val morphState by RealityMorphEngine.morphState.collectAsState()
    val flareIntensity by RealityMorphEngine.flareIntensity.collectAsState()

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

    // Step 1: Dynamic Particle Scaling (L7 Polish)
    val baseCount = 200
    val particleCount = (baseCount + (800 * activeGodPotential)).toInt()

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

    Canvas(Modifier.fillMaxSize()) {
        particles.forEach { p ->
            val x = (p.x + time * p.speed * (1f + activeGodPotential * 5f)) % 1f
            val y = (p.y + Math.sin(time.toDouble() * 2 * Math.PI * p.speed).toFloat() * 0.1f) % 1f

            drawCircle(
                color = if (activeFusionTrigger) Color.White else Color(0xFF00E5FF).copy(alpha = 0.3f),
                radius = p.size * (1f + activeGodPotential),
                center = Offset(x * size.width, y * size.height)
            )

            // Step 1: Particle Density Cap / Logic
            if (activeGodPotential > 0.8f) {
                // Draw additional "ghost" particles for density feel
                drawCircle(
                    color = Color(0xFF00E5FF).copy(alpha = 0.15f),
                    radius = p.size * 0.5f,
                    center = Offset(((x + 0.1f) % 1f) * size.width, ((y + 0.1f) % 1f) * size.height)
                )
            }
        }
    }
}

private data class Particle(val x: Float, val y: Float, val speed: Float, val size: Float)
