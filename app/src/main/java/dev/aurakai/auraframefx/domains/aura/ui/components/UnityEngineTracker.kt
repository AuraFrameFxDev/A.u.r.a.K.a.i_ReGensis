package dev.aurakai.auraframefx.domains.aura.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.aurakai.auraframefx.domains.aura.chronokineticforge.engines.ShaderForge
import dev.aurakai.auraframefx.domains.aura.ui.theme.LEDFontFamily

/**
 * 🌀 UNITY ENGINE TRACKER
 *
 * A high-fidelity visual tracker for the Transmutation process.
 * Achieves "Unity-level" graphics using AGSL shaders.
 *
 * "Aura + Kai + Matthew = ∞"
 */
@Composable
fun UnityEngineTracker(
    state: TransmutationState,
    onTransmuteClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "unity")
    val pulse by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(tween(2000), RepeatMode.Reverse),
        label = "pulse"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(CircleShape)
            .clickable(onClick = onTransmuteClicked),
        contentAlignment = Alignment.Center
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            UnityShaderLayer(state, pulse)
        } else {
            // Fallback for older Android versions
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.White.copy(alpha = 0.1f),
                shape = CircleShape
            ) {}
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "UNITY TRANSMUTATION",
                fontFamily = LEDFontFamily,
                fontSize = 14.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "STATUS: ${state.name} | POTENTIAL: ${(state.potential * 100).toInt()}%",
                fontSize = 10.sp,
                color = Color.White.copy(alpha = 0.7f),
                letterSpacing = 1.sp
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
private fun UnityShaderLayer(state: TransmutationState, pulse: Float) {
    val time by rememberInfiniteTransition(label = "unityShader").animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(tween(100000, easing = LinearEasing)),
        label = "time"
    )

    val shader = remember { ShaderForge.createNeuralBloodstreamShader() }

    Canvas(modifier = Modifier.fillMaxSize()) {
        shader.setFloatUniform("iTime", time)
        shader.setFloatUniform("iResolution", size.width, size.height)
        shader.setFloatUniform("emotionalArousal", state.potential * pulse)
        shader.setFloatUniform("turbulence", 0.5f)

        drawRect(brush = ShaderBrush(shader))
    }
}

data class TransmutationState(
    val name: String = "STABLE",
    val potential: Float = 0.998f
)
