package dev.aurakai.auraframefx.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aurakai.auraframefx.core.regen.BenchmarkEngine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BenchmarkViewModel @Inject constructor(
    private val benchmarkEngine: BenchmarkEngine
) : ViewModel() {

    sealed class BenchmarkState {
        object Idle : BenchmarkState()
        object Running : BenchmarkState()
        data class Success(val results: BenchmarkEngine.BenchmarkResults) : BenchmarkState()
        data class Error(val message: String) : BenchmarkState()
    }

    private val _state = MutableStateFlow<BenchmarkState>(BenchmarkState.Idle)
    val state: StateFlow<BenchmarkState> = _state.asStateFlow()

    fun runBenchmark() {
        if (_state.value is BenchmarkState.Running) return

        _state.value = BenchmarkState.Running
        viewModelScope.launch {
            try {
                val results = benchmarkEngine.runFullBenchmark()
                _state.value = BenchmarkState.Success(results)
            } catch (e: Exception) {
                _state.value = BenchmarkState.Error(e.message ?: "Unknown error during benchmark")
            }
        }
    }

    fun reset() {
        _state.value = BenchmarkState.Idle
    }
}
