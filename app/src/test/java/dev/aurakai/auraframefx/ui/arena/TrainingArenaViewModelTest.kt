package dev.aurakai.auraframefx.ui.arena

import androidx.lifecycle.SavedStateHandle
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class TrainingArenaViewModelTest {

    private lateinit var viewModel: TrainingArenaViewModel

    @Before
    fun setUp() {
        // Create a SavedStateHandle with a test agentId
        val savedStateHandle = SavedStateHandle(mapOf("agentId" to "sentinel-prime"))
        viewModel = TrainingArenaViewModel(savedStateHandle)
    }

    @Test
    fun agentId_is_correctly_initialized_from_SavedStateHandle() {
        assertEquals("sentinel-prime", viewModel.agentId)
    }

    @Test
    fun initial_progress_is_zero() {
        assertEquals(0f, viewModel.progress.value, 0f)
    }

    @Test
    fun startTraining_updates_progress_to_0_5() {
        viewModel.startTraining()
        assertEquals(0.5f, viewModel.progress.value, 0f)
    }

    @Test
    fun agentId_defaults_to_Unknown_when_handle_is_empty() {
        val emptyHandle = SavedStateHandle()
        val vm = TrainingArenaViewModel(emptyHandle)
        assertEquals("Unknown", vm.agentId)
    }
}
