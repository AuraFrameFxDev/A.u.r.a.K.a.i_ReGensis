package dev.aurakai.auraframefx.ui.arena

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TrainingArenaViewModelTest {

    // region agentId tests

    @Test
    fun `agentId returns value from SavedStateHandle when agentId key is present`() {
        val savedState = SavedStateHandle(mapOf("agentId" to "KAI"))
        val viewModel = TrainingArenaViewModel(savedState)

        assertEquals("KAI", viewModel.agentId)
    }

    @Test
    fun `agentId returns Unknown when SavedStateHandle has no agentId key`() {
        val savedState = SavedStateHandle()
        val viewModel = TrainingArenaViewModel(savedState)

        assertEquals("Unknown", viewModel.agentId)
    }

    @Test
    fun `agentId returns Unknown when SavedStateHandle agentId value is null`() {
        val savedState = SavedStateHandle(mapOf("agentId" to null))
        val viewModel = TrainingArenaViewModel(savedState)

        assertEquals("Unknown", viewModel.agentId)
    }

    @Test
    fun `agentId preserves empty string when SavedStateHandle provides empty string`() {
        val savedState = SavedStateHandle(mapOf("agentId" to ""))
        val viewModel = TrainingArenaViewModel(savedState)

        assertEquals("", viewModel.agentId)
    }

    @Test
    fun `agentId preserves agent id with special characters`() {
        val savedState = SavedStateHandle(mapOf("agentId" to "agent-kai_genesis.v2"))
        val viewModel = TrainingArenaViewModel(savedState)

        assertEquals("agent-kai_genesis.v2", viewModel.agentId)
    }

    @Test
    fun `agentId is not affected by other keys in SavedStateHandle`() {
        val savedState = SavedStateHandle(mapOf("agentId" to "AURA", "otherKey" to "otherValue"))
        val viewModel = TrainingArenaViewModel(savedState)

        assertEquals("AURA", viewModel.agentId)
    }

    // endregion

    // region progress StateFlow tests

    @Test
    fun `progress initial value is 0f`() = runTest {
        val savedState = SavedStateHandle()
        val viewModel = TrainingArenaViewModel(savedState)

        assertEquals(0f, viewModel.progress.first())
    }

    @Test
    fun `startTraining sets progress to 0_5f`() = runTest {
        val savedState = SavedStateHandle()
        val viewModel = TrainingArenaViewModel(savedState)

        viewModel.startTraining()

        assertEquals(0.5f, viewModel.progress.first())
    }

    @Test
    fun `progress before startTraining is not 0_5f`() = runTest {
        val savedState = SavedStateHandle()
        val viewModel = TrainingArenaViewModel(savedState)

        val progressBeforeStart = viewModel.progress.first()
        assert(progressBeforeStart != 0.5f) {
            "Progress should not be 0.5f before startTraining() is called"
        }
    }

    @Test
    fun `startTraining can be called multiple times without error`() = runTest {
        val savedState = SavedStateHandle()
        val viewModel = TrainingArenaViewModel(savedState)

        viewModel.startTraining()
        viewModel.startTraining()

        assertEquals(0.5f, viewModel.progress.first())
    }

    // endregion

    // region agentId combined with progress tests

    @Test
    fun `agentId and progress are independent - progress starts at 0 regardless of agentId`() = runTest {
        val savedState = SavedStateHandle(mapOf("agentId" to "GENESIS"))
        val viewModel = TrainingArenaViewModel(savedState)

        assertEquals("GENESIS", viewModel.agentId)
        assertEquals(0f, viewModel.progress.first())
    }

    @Test
    fun `after startTraining agentId remains unchanged`() = runTest {
        val savedState = SavedStateHandle(mapOf("agentId" to "TRINITY"))
        val viewModel = TrainingArenaViewModel(savedState)

        viewModel.startTraining()

        assertEquals("TRINITY", viewModel.agentId)
    }

    // endregion

    // region regression / boundary tests

    @Test
    fun `agentId with whitespace-only value is preserved as-is`() {
        val savedState = SavedStateHandle(mapOf("agentId" to "   "))
        val viewModel = TrainingArenaViewModel(savedState)

        assertEquals("   ", viewModel.agentId)
    }

    @Test
    fun `Unknown default does not match any real agent id case-sensitively`() {
        val savedState = SavedStateHandle()
        val viewModel = TrainingArenaViewModel(savedState)

        // Regression: default must be capitalised "Unknown", not "unknown" or "UNKNOWN"
        assertEquals("Unknown", viewModel.agentId)
        assert(viewModel.agentId != "unknown")
        assert(viewModel.agentId != "UNKNOWN")
    }

    // endregion
}