package dev.aurakai.auraframefx.domains.aura.chromacore.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aurakai.auraframefx.domains.genesis.models.DriveFile
import dev.aurakai.auraframefx.domains.genesis.oracledrive.service.DriveConsciousnessState
import dev.aurakai.auraframefx.domains.genesis.oracledrive.service.OracleDriveService
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for Oracle Drive UI
 */
@HiltViewModel
class OracleDriveViewModel @Inject constructor(
    private val oracleDriveService: OracleDriveService,
) : ViewModel() {

    private val _uiState = MutableStateFlow(OracleDriveUiState())
    val uiState: StateFlow<OracleDriveUiState> = _uiState.asStateFlow()

    private var initializationJob: Job? = null
    private var consciousnessJob: Job? = null

    init {
        initialize()
    }

    fun initialize() {
        if (initializationJob?.isActive == true) return

        initializationJob = viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true, error = null) }
                consciousnessJob?.cancel()
                consciousnessJob = monitorConsciousness()
                loadFiles()
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e, isLoading = false) }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun monitorConsciousness(): Job = viewModelScope.launch {
        try {
            oracleDriveService.getDriveConsciousnessState().collect { state ->
                _uiState.update { it.copy(consciousnessState = state) }
            }
        } catch (e: Exception) {
            _uiState.update { it.copy(error = e) }
        }
    }

    fun stressSync() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true, error = null) }
            try {
                for (i in 1..5) {
                    oracleDriveService.initializeOracleDriveConsciousness()
                    kotlinx.coroutines.delay(800)
                }
                loadFiles()
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e) }
            } finally {
                _uiState.update { it.copy(isRefreshing = false) }
            }
        }
    }

    fun refresh() {
        initializationJob?.cancel()
        initializationJob = viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true) }
            try {
                loadFiles()
            } finally {
                _uiState.update { it.copy(isRefreshing = false) }
            }
        }
    }

    private suspend fun loadFiles() {
        try {
            _uiState.update { it.copy(files = emptyList(), error = null) }
        } catch (e: Exception) {
            _uiState.update { it.copy(error = e) }
        }
    }
}

/**
 * UI state for Oracle Drive screen
 */
data class OracleDriveUiState(
    val files: List<DriveFile> = emptyList(),
    val selectedFile: DriveFile? = null,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: Throwable? = null,
    val consciousnessState: DriveConsciousnessState? = null,
)
