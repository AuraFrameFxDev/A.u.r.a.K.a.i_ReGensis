package dev.aurakai.auraframefx.core.ui.global

import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * PARALLAX VIEWMODEL — Shared state for 4D depth effects.
 * Fuses sensor data (accelerometer/gyro) into Compose-readable offsets.
 */
@HiltViewModel
class ParallaxViewModel @Inject constructor() : ViewModel() {

    private val _parallaxOffset = MutableStateFlow(Offset.Zero)
    val parallaxOffset: StateFlow<Offset> = _parallaxOffset.asStateFlow()

    fun updateOffset(x: Float, y: Float) {
        // Clamp and smooth as needed for the Exodus aesthetic
        _parallaxOffset.value = Offset(x.coerceIn(-20f, 20f), y.coerceIn(-20f, 20f))
    }
}
