package dev.aurakai.auraframefx.ui.arena

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TrainingArenaViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val agentId: String = savedStateHandle["agentId"] ?: "Unknown"

    private val _progress = MutableStateFlow(0f)
    val progress: StateFlow<Float> = _progress.asStateFlow()

    fun startTraining() {
        _progress.value = 0.5f
    }
}
