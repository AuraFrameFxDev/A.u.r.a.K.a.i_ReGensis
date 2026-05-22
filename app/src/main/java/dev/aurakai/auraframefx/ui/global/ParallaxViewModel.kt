package dev.aurakai.auraframefx.ui.global

import android.app.Application
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.AndroidViewModel
import dev.aurakai.auraframefx.ui.liveui.SensorFusionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ParallaxViewModel(application: Application) : AndroidViewModel(application) {
    private val sensorManager = SensorFusionManager(application)

    private val _parallaxOffset = MutableStateFlow(Offset.Zero)
    val parallaxOffset: StateFlow<Offset> = _parallaxOffset.asStateFlow()

    init {
        sensorManager.registerForPreview { x, y ->
            _parallaxOffset.update { Offset(y * 10f, x * 10f) } // Map tilt to offset
        }
    }

    override fun onCleared() {
        super.onCleared()
        sensorManager.unregister()
    }
}
