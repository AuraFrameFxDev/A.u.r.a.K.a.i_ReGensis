package dev.aurakai.auraframefx.domains.aura.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aurakai.auraframefx.domains.aura.models.Emotion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * AuraMoodViewModel manages Aura's mood and creative state.
 */
@HiltViewModel
open class AuraMoodViewModel @Inject constructor() : ViewModel() {

    private val _moodState = MutableStateFlow<Emotion>(Emotion.NEUTRAL)
    val moodState: StateFlow<Emotion> = _moodState

    fun onUserInput(input: String) {
        viewModelScope.launch {
            _moodState.value = when {
                input.contains("happy", ignoreCase = true) -> Emotion.HAPPY
                input.contains("sad", ignoreCase = true) -> Emotion.SAD
                input.contains("angry", ignoreCase = true) -> Emotion.ANGRY
                else -> Emotion.NEUTRAL
            }
        }
    }
}
