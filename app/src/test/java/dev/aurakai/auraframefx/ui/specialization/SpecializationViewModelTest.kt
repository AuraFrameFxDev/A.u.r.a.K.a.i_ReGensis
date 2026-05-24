package dev.aurakai.auraframefx.ui.specialization

import androidx.lifecycle.SavedStateHandle
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SpecializationViewModelTest {

    private lateinit var viewModel: SpecializationViewModel

    @Before
    fun setUp() {
        // Create a SavedStateHandle with a test agentId
        val savedStateHandle = SavedStateHandle(mapOf("agentId" to "test-specialist"))
        viewModel = SpecializationViewModel(savedStateHandle)
    }

    @Test
    fun agentId_is_correctly_read_from_SavedStateHandle() {
        assertEquals("test-specialist", viewModel.agentId)
    }

    @Test
    fun initial_nodes_are_correct() {
        val expected = listOf("Artist", "Squire", "Trickster")
        assertEquals(expected, viewModel.nodes.value)
    }

    @Test
    fun agentId_defaults_to_Unknown_if_not_provided() {
        val emptyHandle = SavedStateHandle()
        val vm = SpecializationViewModel(emptyHandle)
        assertEquals("Unknown", vm.agentId)
    }
}
