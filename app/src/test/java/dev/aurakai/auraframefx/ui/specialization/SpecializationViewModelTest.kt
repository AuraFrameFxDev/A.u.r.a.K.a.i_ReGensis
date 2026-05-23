package dev.aurakai.auraframefx.ui.specialization

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SpecializationViewModelTest {

    // region agentId tests

    @Test
    fun `agentId returns value from SavedStateHandle when agentId key is present`() {
        val savedState = SavedStateHandle(mapOf("agentId" to "AURA"))
        val viewModel = SpecializationViewModel(savedState)

        assertEquals("AURA", viewModel.agentId)
    }

    @Test
    fun `agentId returns Unknown when SavedStateHandle has no agentId key`() {
        val savedState = SavedStateHandle()
        val viewModel = SpecializationViewModel(savedState)

        assertEquals("Unknown", viewModel.agentId)
    }

    @Test
    fun `agentId returns Unknown when SavedStateHandle agentId value is null`() {
        val savedState = SavedStateHandle(mapOf("agentId" to null))
        val viewModel = SpecializationViewModel(savedState)

        assertEquals("Unknown", viewModel.agentId)
    }

    @Test
    fun `agentId preserves empty string when SavedStateHandle provides empty string`() {
        val savedState = SavedStateHandle(mapOf("agentId" to ""))
        val viewModel = SpecializationViewModel(savedState)

        assertEquals("", viewModel.agentId)
    }

    @Test
    fun `agentId preserves agent id with special characters`() {
        val savedState = SavedStateHandle(mapOf("agentId" to "agent-aura_kai.genesis"))
        val viewModel = SpecializationViewModel(savedState)

        assertEquals("agent-aura_kai.genesis", viewModel.agentId)
    }

    @Test
    fun `agentId is not affected by other keys in SavedStateHandle`() {
        val savedState = SavedStateHandle(mapOf("agentId" to "KAI", "unrelated" to "value"))
        val viewModel = SpecializationViewModel(savedState)

        assertEquals("KAI", viewModel.agentId)
    }

    // endregion

    // region nodes StateFlow tests

    @Test
    fun `nodes initial value contains Artist`() = runTest {
        val savedState = SavedStateHandle()
        val viewModel = SpecializationViewModel(savedState)

        assertTrue(viewModel.nodes.first().contains("Artist"))
    }

    @Test
    fun `nodes initial value contains Squire`() = runTest {
        val savedState = SavedStateHandle()
        val viewModel = SpecializationViewModel(savedState)

        assertTrue(viewModel.nodes.first().contains("Squire"))
    }

    @Test
    fun `nodes initial value contains Trickster`() = runTest {
        val savedState = SavedStateHandle()
        val viewModel = SpecializationViewModel(savedState)

        assertTrue(viewModel.nodes.first().contains("Trickster"))
    }

    @Test
    fun `nodes initial list has exactly three elements`() = runTest {
        val savedState = SavedStateHandle()
        val viewModel = SpecializationViewModel(savedState)

        assertEquals(3, viewModel.nodes.first().size)
    }

    @Test
    fun `nodes initial list equals Artist Squire Trickster in order`() = runTest {
        val savedState = SavedStateHandle()
        val viewModel = SpecializationViewModel(savedState)

        assertEquals(listOf("Artist", "Squire", "Trickster"), viewModel.nodes.first())
    }

    @Test
    fun `nodes list is the same regardless of agentId`() = runTest {
        val savedStateA = SavedStateHandle(mapOf("agentId" to "AURA"))
        val savedStateB = SavedStateHandle(mapOf("agentId" to "KAI"))
        val viewModelA = SpecializationViewModel(savedStateA)
        val viewModelB = SpecializationViewModel(savedStateB)

        assertEquals(viewModelA.nodes.first(), viewModelB.nodes.first())
    }

    // endregion

    // region combined agentId + nodes tests

    @Test
    fun `agentId and nodes are independent - nodes initialised correctly for any agentId`() = runTest {
        val savedState = SavedStateHandle(mapOf("agentId" to "GENESIS"))
        val viewModel = SpecializationViewModel(savedState)

        assertEquals("GENESIS", viewModel.agentId)
        assertEquals(listOf("Artist", "Squire", "Trickster"), viewModel.nodes.first())
    }

    // endregion

    // region regression / boundary tests

    @Test
    fun `Unknown default is capitalised correctly`() {
        val savedState = SavedStateHandle()
        val viewModel = SpecializationViewModel(savedState)

        // Regression: default must be "Unknown" (capital U), not "unknown" or "UNKNOWN"
        assertEquals("Unknown", viewModel.agentId)
        assertTrue(viewModel.agentId != "unknown")
        assertTrue(viewModel.agentId != "UNKNOWN")
    }

    @Test
    fun `agentId with whitespace-only value is preserved as-is`() {
        val savedState = SavedStateHandle(mapOf("agentId" to "   "))
        val viewModel = SpecializationViewModel(savedState)

        assertEquals("   ", viewModel.agentId)
    }

    @Test
    fun `nodes list is not empty`() = runTest {
        val savedState = SavedStateHandle()
        val viewModel = SpecializationViewModel(savedState)

        assertTrue(viewModel.nodes.first().isNotEmpty())
    }

    // endregion
}
