package dev.aurakai.auraframefx.ui.specialization

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SpecializationViewModelTest {

    // ── Helpers ───────────────────────────────────────────────────────────

    private fun buildViewModel(savedStateHandle: SavedStateHandle = SavedStateHandle()): SpecializationViewModel =
        SpecializationViewModel(savedStateHandle)

    // ── agentId resolution ────────────────────────────────────────────────

    @Test
    fun `agentId is read from SavedStateHandle when present`() {
        val handle = SavedStateHandle(mapOf("agentId" to "genesis"))

        val viewModel = buildViewModel(handle)

        assertEquals("genesis", viewModel.agentId)
    }

    @Test
    fun `agentId defaults to Unknown when SavedStateHandle has no agentId key`() {
        val handle = SavedStateHandle()

        val viewModel = buildViewModel(handle)

        assertEquals("Unknown", viewModel.agentId)
    }

    @Test
    fun `agentId defaults to Unknown when agentId value is null`() {
        val handle = SavedStateHandle(mapOf("agentId" to null))

        val viewModel = buildViewModel(handle)

        assertEquals("Unknown", viewModel.agentId)
    }

    @Test
    fun `agentId preserves exact casing of string from SavedStateHandle`() {
        val handle = SavedStateHandle(mapOf("agentId" to "AURA_PRIME"))

        val viewModel = buildViewModel(handle)

        assertEquals("AURA_PRIME", viewModel.agentId)
    }

    @Test
    fun `agentId handles empty string from SavedStateHandle`() {
        val handle = SavedStateHandle(mapOf("agentId" to ""))

        val viewModel = buildViewModel(handle)

        assertEquals("", viewModel.agentId)
    }

    @Test
    fun `agentId handles alphanumeric and hyphen characters`() {
        val handle = SavedStateHandle(mapOf("agentId" to "agent-007"))

        val viewModel = buildViewModel(handle)

        assertEquals("agent-007", viewModel.agentId)
    }

    // ── nodes StateFlow ───────────────────────────────────────────────────

    @Test
    fun `nodes initial value contains Artist`() {
        val viewModel = buildViewModel()

        assert(viewModel.nodes.value.contains("Artist"))
    }

    @Test
    fun `nodes initial value contains Squire`() {
        val viewModel = buildViewModel()

        assert(viewModel.nodes.value.contains("Squire"))
    }

    @Test
    fun `nodes initial value contains Trickster`() {
        val viewModel = buildViewModel()

        assert(viewModel.nodes.value.contains("Trickster"))
    }

    @Test
    fun `nodes initial value has exactly three elements`() {
        val viewModel = buildViewModel()

        assertEquals(3, viewModel.nodes.value.size)
    }

    @Test
    fun `nodes initial value is ordered as Artist then Squire then Trickster`() {
        val viewModel = buildViewModel()

        assertEquals(listOf("Artist", "Squire", "Trickster"), viewModel.nodes.value)
    }

    @Test
    fun `nodes is not affected by agentId value`() {
        val handle = SavedStateHandle(mapOf("agentId" to "someAgent"))
        val viewModel = buildViewModel(handle)

        assertEquals(listOf("Artist", "Squire", "Trickster"), viewModel.nodes.value)
    }

    // ── regression: separate instances are independent ────────────────────

    @Test
    fun `two ViewModels with different agentIds share the same default nodes`() {
        val vm1 = buildViewModel(SavedStateHandle(mapOf("agentId" to "aura")))
        val vm2 = buildViewModel(SavedStateHandle(mapOf("agentId" to "kai")))

        assertEquals(vm1.nodes.value, vm2.nodes.value)
    }

    @Test
    fun `two ViewModels with different agentIds remain independent`() {
        val vmAura = buildViewModel(SavedStateHandle(mapOf("agentId" to "aura")))
        val vmKai = buildViewModel(SavedStateHandle(mapOf("agentId" to "kai")))

        assertEquals("aura", vmAura.agentId)
        assertEquals("kai", vmKai.agentId)
    }

    // ── boundary / negative cases ─────────────────────────────────────────

    @Test
    fun `agentId is immutable after construction with a given value`() {
        val handle = SavedStateHandle(mapOf("agentId" to "initialAgent"))
        val viewModel = buildViewModel(handle)

        // Mutating the handle after construction should not change agentId
        // (agentId is a val assigned at init time from the handle's snapshot)
        handle["agentId"] = "changedAgent"

        assertEquals("initialAgent", viewModel.agentId)
    }
}
