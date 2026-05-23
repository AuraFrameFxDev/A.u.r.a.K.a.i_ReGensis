package dev.aurakai.auraframefx.ui.arena

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TrainingArenaViewModelTest {

    // ── Helpers ───────────────────────────────────────────────────────────

    private fun buildViewModel(savedStateHandle: SavedStateHandle = SavedStateHandle()): TrainingArenaViewModel =
        TrainingArenaViewModel(savedStateHandle)

    // ── agentId resolution ────────────────────────────────────────────────

    @Test
    fun `agentId is read from SavedStateHandle when present`() {
        val handle = SavedStateHandle(mapOf("agentId" to "aura"))

        val viewModel = buildViewModel(handle)

        assertEquals("aura", viewModel.agentId)
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
    fun `agentId preserves the exact string value from SavedStateHandle`() {
        val handle = SavedStateHandle(mapOf("agentId" to "KAI_SOVEREIGN"))

        val viewModel = buildViewModel(handle)

        assertEquals("KAI_SOVEREIGN", viewModel.agentId)
    }

    @Test
    fun `agentId handles empty string from SavedStateHandle`() {
        val handle = SavedStateHandle(mapOf("agentId" to ""))

        val viewModel = buildViewModel(handle)

        assertEquals("", viewModel.agentId)
    }

    @Test
    fun `agentId handles special characters and spaces`() {
        val handle = SavedStateHandle(mapOf("agentId" to "Agent 47 / Genesis-Alpha"))

        val viewModel = buildViewModel(handle)

        assertEquals("Agent 47 / Genesis-Alpha", viewModel.agentId)
    }

    // ── progress StateFlow ────────────────────────────────────────────────

    @Test
    fun `progress initial value is 0f`() {
        val viewModel = buildViewModel()

        assertEquals(0f, viewModel.progress.value, 0.0001f)
    }

    @Test
    fun `startTraining sets progress to 0_5`() = runTest {
        val viewModel = buildViewModel()

        viewModel.startTraining()

        assertEquals(0.5f, viewModel.progress.value, 0.0001f)
    }

    @Test
    fun `startTraining can be called multiple times and remains at 0_5`() = runTest {
        val viewModel = buildViewModel()

        viewModel.startTraining()
        viewModel.startTraining()

        assertEquals(0.5f, viewModel.progress.value, 0.0001f)
    }

    @Test
    fun `progress is not affected by agentId value`() = runTest {
        val handle = SavedStateHandle(mapOf("agentId" to "genesis"))
        val viewModel = buildViewModel(handle)

        // progress stays at initial before training
        assertEquals(0f, viewModel.progress.value, 0.0001f)

        viewModel.startTraining()

        assertEquals(0.5f, viewModel.progress.value, 0.0001f)
    }

    // ── regression: separate instances are independent ────────────────────

    @Test
    fun `two ViewModels with different agentIds are independent`() {
        val vmAura = buildViewModel(SavedStateHandle(mapOf("agentId" to "aura")))
        val vmKai = buildViewModel(SavedStateHandle(mapOf("agentId" to "kai")))

        assertEquals("aura", vmAura.agentId)
        assertEquals("kai", vmKai.agentId)
    }

    @Test
    fun `startTraining on one instance does not affect another`() = runTest {
        val vm1 = buildViewModel()
        val vm2 = buildViewModel()

        vm1.startTraining()

        assertEquals(0.5f, vm1.progress.value, 0.0001f)
        assertEquals(0f, vm2.progress.value, 0.0001f)
    }
}