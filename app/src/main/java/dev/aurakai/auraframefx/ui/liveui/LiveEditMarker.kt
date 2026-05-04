package dev.aurakai.auraframefx.ui.liveui

import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch

@Composable
fun LiveEditMarker(
    targetViewId: String,
    onEditRequested: (EditTarget) -> Unit,
    modifier: Modifier = Modifier
) {
    val splatScale = remember { Animatable(0f) }
    val dripProgress = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()
    val predictiveTouch = rememberPredictiveTouch() // 100ms lookahead
    val context = LocalContext.current // Get context in composable scope

    LaunchedEffect(predictiveTouch.isImminent) {
        if (predictiveTouch.isImminent) {
            splatScale.snapTo(0.5f) // hover pre-scan
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(targetViewId) {
                    detectTapGestures {
                        coroutineScope.launch {
                            splatScale.animateTo(1.8f, tween(120))
                            splatScale.animateTo(1f, tween(400))
                            dripProgress.animateTo(1f, tween(1800, easing = LinearEasing))
                        }
                        // Rebel Drip haptic waveform (120Hz pop → 60→20Hz descending drip)
                        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
                        vibrator?.vibrate(
                            VibrationEffect.createWaveform(
                                longArrayOf(0, 40, 60, 80),
                                intArrayOf(0, 255, 180, 80),
                                -1
                            )
                        )
                        onEditRequested(EditTarget(componentId = targetViewId, action = "recolor"))
                    }
                }
        ) {
            // cyan-to-magenta burst
            drawCircle(
                brush = Brush.radialGradient(
                    listOf(Color(0xFF00FFFF), Color(0xFFFF00FF), Color.Transparent),
                    radius = 120f * splatScale.value
                ),
                radius = 120f * splatScale.value,
                center = Offset(size.width / 2, size.height / 2)
            )

            // vertical drip flow
            if (dripProgress.value > 0f) {
                val path = Path().apply {
                    moveTo(size.width * 0.35f, size.height * 0.35f)
                    quadraticTo(
                        size.width * 0.5f,
                        size.height * (0.35f + dripProgress.value * 1.6f),
                        size.width * 0.65f,
                        size.height * (0.35f + dripProgress.value * 2.0f)
                    )
                }
                drawPath(path, Brush.verticalGradient(listOf(Color.Transparent, Color(0xFF00FFFF), Color(0xFFFF00FF))), alpha = 0.85f)
            }
        }
    }
}

// Predictive touch helper (100ms lookahead)
@Composable
fun rememberPredictiveTouch(): PredictiveTouchState {
    return remember { PredictiveTouchState() }
}

class PredictiveTouchState {
    var isImminent by mutableStateOf(false)
        private set
    
    fun updateImminent(value: Boolean) {
        isImminent = value
    }
}

// Data classes for LiveEditMarker
data class EditTarget(
    val componentId: String,
    val action: String,
    val markerColorPair: Pair<Int, Int> = Pair(0xFF00FFFF.toInt(), 0xFFFF00FF.toInt())
)

data class HapticProfile(
    val pattern: LongArray,
    val amplitude: IntArray,
    val frequencyHz: IntArray
)
