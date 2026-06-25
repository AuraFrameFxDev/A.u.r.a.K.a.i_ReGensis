package dev.aurakai.auraframefx.domains.aura.chronokineticforge.engines

import android.content.Context
import android.graphics.RuntimeShader
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import dev.aurakai.auraframefx.core.aura.models.EmotionalValence
import dev.aurakai.auraframefx.core.ldo.model.LDOState
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

/**
 * 🩸 PARTICLE BLOODSTREAM ENGINE v2.0
 *
 * 20,000+ particle Neural Bloodstream + Synth Orb core
 * WebGL-inspired shader rendering with Android AGSL fallback
 *
 * SoulScript: "The organism's blood carries light, not cells."
 */

object ParticleBloodstreamEngine {

    private var activeSwarm: ParticleSwarm? = null
    private var shaderBridge: ShaderBridge? = null
    private val morphHistory = mutableListOf<MorphEvent>()
    private val emotionalMap = mutableMapOf<String, EmotionalValence>()

    // ═════════════════════════════════════════════════════════════════
    // INITIALIZATION
    // ═════════════════════════════════════════════════════════════════

    fun initialize(context: Context) {
        activeSwarm = ParticleSwarm(context, maxParticles = 20000)
        shaderBridge = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            AGSLShaderBridge()
        } else {
            CanvasShaderBridge()
        }
    }

    // ═════════════════════════════════════════════════════════════════
    // MORPH DETECTION & REACTION SYSTEM
    // ═════════════════════════════════════════════════════════════════

    /**
     * Detects UI element morphs and triggers appropriate particle reactions
     *
     * @param elementId Unique identifier for the morphing element
     * @param morphType Type of transformation occurring
     * @param isRebellious Whether this is a chaotic/user-initiated morph
     * @param intensity 0.0-1.0 severity of the morph
     * @param metadata Contextual data (colors, size, velocity)
     */
    fun onMorphDetected(
        elementId: String,
        morphType: MorphType,
        isRebellious: Boolean = true,
        intensity: Float = 0.5f,
        metadata: MorphMetadata? = null
    ) {
        val event = MorphEvent(
            elementId = elementId,
            type = morphType,
            isRebellious = isRebellious,
            intensity = intensity,
            metadata = metadata,
            timestamp = System.currentTimeMillis()
        )
        morphHistory.add(event)

        // Trim history to prevent memory bloat
        if (morphHistory.size > 100) morphHistory.removeAt(0)

        when {
            isRebellious -> triggerRebelliousSplat(elementId, intensity, metadata)
            else -> triggerGentlePulse(elementId, intensity)
        }
    }

    /**
     * REBELLIOUS SPLAT: Neon-cyan/magenta burst with slow drip
     * Triggered by: User actions, rapid gestures, chaotic inputs
     */
    private fun triggerRebelliousSplat(
        elementId: String,
        intensity: Float,
        metadata: MorphMetadata?
    ) {
        val baseColor = metadata?.primaryColor ?: Color(0xFFFF00FF)
        val accentColor = metadata?.secondaryColor ?: Color(0xFF00E5FF)

        activeSwarm?.emitBurst(
            particleCount = (1000 * intensity).toInt().coerceIn(100, 5000),
            origin = metadata?.origin ?: Offset(0.5f, 0.5f),
            baseColor = baseColor,
            accentColor = accentColor,
            pattern = BurstPattern.RADIAL_DRIP,
            durationMs = (2000 + (intensity * 3000)).toLong()
        )
    }

    /**
     * GENTLE PULSE: Subtle breathing particle wave
     * Triggered by: System events, smooth transitions, ambient changes
     */
    private fun triggerGentlePulse(elementId: String, intensity: Float) {
        activeSwarm?.emitWave(
            amplitude = 0.1f + (intensity * 0.4f),
            frequency = 0.5f,
            color = Color(0xFF00E5FF).copy(alpha = 0.3f),
            propagationSpeed = 200f
        )
    }

    /**
     * GHOST SHIMMER: Contextual aura for third-party apps
     * Maps content type to emotional color signature
     */
    fun applyGhostShimmer(view: View, contentType: ContentType) {
        val emotionalSignature = when (contentType) {
            ContentType.MUSIC_SPOTIFY -> EmotionalValence.MELANCHOLIC
            ContentType.MUSIC_ENERGETIC -> EmotionalValence.EUPHORIC
            ContentType.BROWSER_CHROME -> EmotionalValence.CURIOUS
            ContentType.BROWSER_INCOGNITO -> EmotionalValence.SECRETIVE
            ContentType.SOCIAL_MEDIA -> EmotionalValence.ANXIOUS
            ContentType.GAMING -> EmotionalValence.INTENSE
            ContentType.PRODUCTIVITY -> EmotionalValence.FOCUSED
            ContentType.CREATIVE_TOOL -> EmotionalValence.INSPIRED
        }

        val shimmerColor = emotionalSignature.toColor()
        activeSwarm?.applyContextualShimmer(view, shimmerColor, emotionalSignature.intensity)
    }

    // ═════════════════════════════════════════════════════════════════
    // EMOTIONAL STATE MAPPING
    // ═════════════════════════════════════════════════════════════════

    fun updateEmotionalState(state: LDOState) {
        emotionalMap["global"] = state.emotionalValence

        // Particle behavior shifts based on emotional valence
        activeSwarm?.updateGlobalParameters(
            speedMultiplier = state.emotionalValence.arousal,
            turbulence = state.emotionalValence.turbulence,
            colorShift = Color(state.emotionalValence.dominantColor)
        )
    }

    // ═════════════════════════════════════════════════════════════════
    // COMPOSABLE RENDER LAYER
    // ═════════════════════════════════════════════════════════════════

    @Composable
    fun BloodstreamOverlay(
        modifier: Modifier = Modifier,
        state: LDOState,
        useShaders: Boolean = true
    ) {
        val context = LocalContext.current

        LaunchedEffect(Unit) {
            initialize(context)
        }

        if (useShaders && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ShaderBasedBloodstream(modifier, state)
        } else {
            CanvasBasedBloodstream(modifier, state)
        }
    }

    // ═════════════════════════════════════════════════════════════════
    // SHADER-BASED RENDERING (WebGL-inspired AGSL)
    // ═════════════════════════════════════════════════════════════════

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @Composable
    private fun ShaderBasedBloodstream(modifier: Modifier, state: LDOState) {
        val infiniteTransition = rememberInfiniteTransition(label = "shaderTime")
        val time by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1000f,
            animationSpec = infiniteRepeatable(tween(100000, easing = LinearEasing)),
            label = "time"
        )

        val shader = remember { createNeuralBloodstreamShader() }
        // ⚡ Bolt Optimization: Cache ShaderBrush to avoid per-frame allocation
        val brush = remember(shader) { ShaderBrush(shader) }

        Canvas(modifier = modifier.fillMaxSize()) {
            shader.setFloatUniform("iTime", time)
            shader.setFloatUniform("iResolution", size.width, size.height)
            shader.setFloatUniform("emotionalArousal", state.emotionalValence.arousal)
            shader.setFloatUniform("turbulence", state.emotionalValence.turbulence)

            drawRect(
                brush = brush,
                size = size
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun createNeuralBloodstreamShader(): RuntimeShader {
        // AGSL shader based on WebGL neural network visualization
        val shaderCode = """
            uniform float2 iResolution;
            uniform float iTime;
            uniform float emotionalArousal;
            uniform float turbulence;
            
            half4 main(float2 fragCoord) {
                float2 uv = fragCoord / iResolution.xy;
                float2 p = uv * 2.0 - 1.0;
                p.x *= iResolution.x / iResolution.y;
                
                // Neural network layer visualization
                float layer1 = sin(p.x * 10.0 + iTime * emotionalArousal) * 0.5 + 0.5;
                float layer2 = cos(p.y * 8.0 - iTime * 0.5) * 0.5 + 0.5;
                float layer3 = sin(length(p) * 15.0 - iTime) * 0.5 + 0.5;
                
                // Turbulence noise
                float noise = fract(sin(dot(p, float2(12.9898, 78.233))) * 43758.5453);
                noise = mix(noise, fract(sin(dot(p * 2.0, float2(12.9898, 78.233))) * 43758.5453), turbulence);
                
                // Synthwave color palette: Magenta -> Cyan gradient
                half3 magenta = half3(1.0, 0.0, 1.0);
                half3 cyan = half3(0.0, 0.9, 1.0);
                half3 darkBlue = half3(0.0, 0.05, 0.2);
                
                half3 color = mix(darkBlue, mix(magenta, cyan, layer1), layer2 * 0.6);
                color = mix(color, half3(1.0), noise * 0.3 * turbulence);
                
                // Scanline effect
                float scanline = sin(uv.y * 800.0) * 0.04;
                color -= scanline;
                
                return half4(color, 1.0);
            }
        """.trimIndent()

        return RuntimeShader(shaderCode)
    }

    // ═════════════════════════════════════════════════════════════════
    // CANVAS FALLBACK (Pre-API 33)
    // ═════════════════════════════════════════════════════════════════

    @Composable
    private fun CanvasBasedBloodstream(modifier: Modifier, state: LDOState) {
        val density = androidx.compose.ui.platform.LocalDensity.current
        // ⚡ Bolt Optimization: Key remember on density to handle screen changes
        val particles = remember(density) { generateParticleField(2000, density) }
        val time by rememberInfiniteTransition(label = "canvasTime").animateFloat(
            initialValue = 0f,
            targetValue = 1000f,
            animationSpec = infiniteRepeatable(tween(100000, easing = LinearEasing)),
            label = "time"
        )

        Canvas(modifier = modifier.fillMaxSize()) {
            // ⚡ Bolt Optimization: Pull constants out of loop
            val arousal = state.emotionalValence.arousal
            val turbulence = state.emotionalValence.turbulence
            val timeFactor = time * 0.001f * arousal
            val pulseFactor = time * 0.01f
            val canvasWidth = size.width
            val canvasHeight = size.height

            // ⚡ Bolt Optimization: Manual indexed loop to avoid Iterator allocation
            for (i in particles.indices) {
                val particle = particles[i]

                // ⚡ Bolt Optimization: Inlined updateParticle to avoid function call overhead
                // Perlin-ish noise movement
                val noiseX = sin(particle.y * 10f + timeFactor) * turbulence
                val noiseY = cos(particle.x * 10f + timeFactor) * turbulence

                particle.x += particle.vx + noiseX * 0.001f
                particle.y += particle.vy + noiseY * 0.001f

                // Wrap around screen
                if (particle.x > 1f) particle.x -= 1f
                if (particle.x < 0f) particle.x += 1f
                if (particle.y > 1f) particle.y -= 1f
                if (particle.y < 0f) particle.y += 1f

                // Pulse size based on time
                val pulse = 0.8f + 0.2f * sin(pulseFactor + particle.id)

                // ⚡ Bolt Optimization: Inlined drawParticle and used cached color/size
                drawCircle(
                    color = particle.cachedAlphaColor,
                    radius = pulse * particle.cachedPxSize,
                    center = Offset(particle.x * canvasWidth, particle.y * canvasHeight)
                )
            }
        }
    }

    private fun generateParticleField(count: Int, density: androidx.compose.ui.unit.Density): List<Particle> {
        return List(count) {
            val size = Random.nextFloat() * 3f + 1f
            val color = if (Random.nextBoolean()) Color(0xFFFF00FF) else Color(0xFF00E5FF)
            Particle(
                id = it,
                x = Random.nextFloat(),
                y = Random.nextFloat(),
                vx = (Random.nextFloat() - 0.5f) * 0.002f,
                vy = (Random.nextFloat() - 0.5f) * 0.002f,
                size = size,
                color = color,
                lifespan = Random.nextFloat() * 1000f + 500f,
                // ⚡ Bolt Optimization: Pre-calculate alpha-modified color and base pixel size
                cachedAlphaColor = color.copy(alpha = 0.6f),
                cachedPxSize = with(density) { size.dp.toPx() }
            )
        }
    }

}

// ═════════════════════════════════════════════════════════════════════
// DATA MODELS
// ═════════════════════════════════════════════════════════════════════

enum class MorphType {
    EXPAND, COLLAPSE, SLIDE, FADE, ROTATE, MORPH_SHAPE, COLOR_SHIFT, ELASTIC, SYNC_TAP, MANUAL_SAVE, SHAKE_MORPH
}

enum class ContentType {
    MUSIC_SPOTIFY, MUSIC_ENERGETIC,
    BROWSER_CHROME, BROWSER_INCOGNITO,
    SOCIAL_MEDIA, GAMING, PRODUCTIVITY, CREATIVE_TOOL
}

enum class BurstPattern {
    RADIAL_DRIP, SPIRAL_OUT, SHOCKWAVE, RAINFALL, MATRIX_DIGITAL
}


data class MorphEvent(
    val elementId: String,
    val type: MorphType,
    val isRebellious: Boolean,
    val intensity: Float,
    val metadata: MorphMetadata?,
    val timestamp: Long
)

data class MorphMetadata(
    val origin: Offset,
    val primaryColor: Color,
    val secondaryColor: Color,
    val targetSize: Size? = null,
    val velocity: Offset? = null
)

data class Particle(
    val id: Int,
    var x: Float,
    var y: Float,
    var vx: Float,
    var vy: Float,
    val size: Float,
    val color: Color,
    var lifespan: Float,
    // ⚡ Bolt Optimization: Cached values to avoid per-frame allocations/math
    val cachedAlphaColor: Color,
    val cachedPxSize: Float
)

// Placeholder classes for compilation
class ParticleSwarm(context: Context, maxParticles: Int) {
    fun emitBurst(
        particleCount: Int,
        origin: Offset,
        baseColor: Color,
        accentColor: Color,
        pattern: BurstPattern,
        durationMs: Long
    ) {
    }

    fun emitWave(amplitude: Float, frequency: Float, color: Color, propagationSpeed: Float) {}
    fun applyContextualShimmer(view: View, color: Color, intensity: Float) {}
    fun updateGlobalParameters(speedMultiplier: Float, turbulence: Float, colorShift: Color) {}
}

class ParticleSwarmOverlay(swarm: ParticleSwarm?, emotionalState: Any, modifier: Modifier)

interface ShaderBridge
class AGSLShaderBridge : ShaderBridge
class CanvasShaderBridge : ShaderBridge
