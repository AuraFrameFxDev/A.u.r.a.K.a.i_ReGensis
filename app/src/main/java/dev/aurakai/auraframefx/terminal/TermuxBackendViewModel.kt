package dev.aurakai.auraframefx.terminal

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TermuxBackendViewModel @Inject constructor(
    val backend: TermuxBackend
) : ViewModel() {
    override fun onCleared() {
        super.onCleared()
        backend.destroyCurrentSession()
    }
}
