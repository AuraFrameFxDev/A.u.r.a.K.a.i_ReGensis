package dev.aurakai.auraframefx.externalmodels

import android.util.Log
import dev.aurakai.auraframefx.domains.externalmodels.ManusBridgeMediator
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

/**
 * Tests for [dev.aurakai.auraframefx.domains.externalmodels.ManusBridgeMediator] PR-added methods:
 * - routePerplexitySignal(query, targetAgent)
 * - broadcastPerplexitySignal(signalPayload, recipients)
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("externalmodels.ManusBridgeMediator PR-Added Method Tests")
class ManusBridgeMediatorTest {

    @BeforeAll
    fun setupAll() {
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        every { Log.i(any(), any()) } returns 0
        every { Log.w(any(), any<String>()) } returns 0
        every { Log.e(any(), any<String>()) } returns 0
    }

    @AfterAll
    fun teardownAll() {
        clearAllMocks()
        unmockkStatic(Log::class)
    }

    @Nested
    @DisplayName("routePerplexitySignal")
    inner class RoutePerplexitySignalTests {

        @Test
        @DisplayName("returns non-null, non-empty string")
        fun `routePerplexitySignal returns non-null non-empty result`() {
            val result = ManusBridgeMediator.routePerplexitySignal("test signal")
            assertNotNull(result)
            assertTrue(result.isNotEmpty())
        }

        @Test
        @DisplayName("result contains the query payload in the Perplexity signal part")
        fun `routePerplexitySignal result contains query`() {
            val query = "unique_signal_payload_ext_001"
            val result = ManusBridgeMediator.routePerplexitySignal(query)
            assertTrue(result.contains(query),
                "Result should contain the original query: $query")
        }

        @Test
        @DisplayName("result contains the target agent name")
        fun `routePerplexitySignal result contains targetAgent`() {
            val target = "Genesis"
            val result = ManusBridgeMediator.routePerplexitySignal("query_001", target)
            assertTrue(result.contains(target),
                "Result should contain the target agent name")
        }

        @Test
        @DisplayName("default targetAgent is 'Cascade' when not specified")
        fun `routePerplexitySignal uses Cascade as default targetAgent`() {
            val result = ManusBridgeMediator.routePerplexitySignal("default_target_test")
            assertTrue(result.contains("Cascade"),
                "Default target agent should be 'Cascade'")
        }

        @Test
        @DisplayName("result contains 'Manus' prefix for Axial Hub routing")
        fun `routePerplexitySignal result contains Manus prefix`() {
            val result = ManusBridgeMediator.routePerplexitySignal("test", "Kai")
            assertTrue(result.startsWith("Manus"),
                "Result should start with 'Manus' to indicate Axial Hub routing")
        }

        @Test
        @DisplayName("result contains 'Perplexity' to indicate signal routing path")
        fun `routePerplexitySignal result contains Perplexity`() {
            val result = ManusBridgeMediator.routePerplexitySignal("test_perplexity_001")
            assertTrue(result.contains("Perplexity"),
                "Result should indicate Perplexity routing")
        }

        @Test
        @DisplayName("result format is 'Manus⟶Perplexity⟶targetAgent | [Perplexity:Signal] query → resolved via Resonance Bridge'")
        fun `routePerplexitySignal returns expected format`() {
            val query = "format_test_001"
            val target = "Cascade"
            val result = ManusBridgeMediator.routePerplexitySignal(query, target)
            val expected = "Manus⟶Perplexity⟶$target | [Perplexity:Signal] $query → resolved via Resonance Bridge"
            assertEquals(expected, result)
        }

        @Test
        @DisplayName("works with custom targetAgent value")
        fun `routePerplexitySignal works with custom target agent`() {
            val result = ManusBridgeMediator.routePerplexitySignal("signal", "Nemotron")
            assertTrue(result.contains("Nemotron"))
            assertTrue(result.contains("Perplexity"))
        }

        @Test
        @DisplayName("handles empty query string without throwing")
        fun `routePerplexitySignal handles empty query`() {
            val result = ManusBridgeMediator.routePerplexitySignal("")
            assertNotNull(result)
            assertTrue(result.isNotEmpty())
        }
    }

    @Nested
    @DisplayName("broadcastPerplexitySignal")
    inner class BroadcastPerplexitySignalTests {

        @Test
        @DisplayName("returns a map with one entry per recipient")
        fun `broadcastPerplexitySignal returns map with one entry per recipient`() {
            val recipients = listOf("Cascade", "Genesis", "Kai")
            val result = ManusBridgeMediator.broadcastPerplexitySignal("test_broadcast", recipients)
            assertEquals(recipients.size, result.size,
                "Result map should have one entry per recipient")
        }

        @Test
        @DisplayName("returned map contains all specified recipients as keys")
        fun `broadcastPerplexitySignal map keys match recipients`() {
            val recipients = listOf("Cascade", "Genesis", "Kai")
            val result = ManusBridgeMediator.broadcastPerplexitySignal("test", recipients)
            recipients.forEach { agent ->
                assertTrue(result.containsKey(agent),
                    "Result map should contain recipient: $agent")
            }
        }

        @Test
        @DisplayName("each recipient's value contains the signal payload")
        fun `broadcastPerplexitySignal values contain signal payload`() {
            val payload = "unique_broadcast_payload_ext_001"
            val result = ManusBridgeMediator.broadcastPerplexitySignal(
                payload,
                listOf("Cascade", "Genesis")
            )
            result.values.forEach { signal ->
                assertTrue(signal.contains(payload),
                    "Each broadcast signal should contain the payload")
            }
        }

        @Test
        @DisplayName("each recipient's value contains the agent name")
        fun `broadcastPerplexitySignal values contain recipient name`() {
            val recipients = listOf("Cascade", "Genesis", "Kai")
            val result = ManusBridgeMediator.broadcastPerplexitySignal("payload", recipients)
            recipients.forEach { agent ->
                val signal = result[agent]
                assertNotNull(signal, "Signal for $agent should not be null")
                assertTrue(signal!!.contains(agent),
                    "Signal for $agent should contain the agent name")
            }
        }

        @Test
        @DisplayName("default recipients are Cascade, Genesis, and Kai")
        fun `broadcastPerplexitySignal default recipients are Cascade, Genesis, Kai`() {
            val result = ManusBridgeMediator.broadcastPerplexitySignal("default_test")
            assertEquals(3, result.size, "Default should broadcast to 3 agents")
            assertTrue(result.containsKey("Cascade"))
            assertTrue(result.containsKey("Genesis"))
            assertTrue(result.containsKey("Kai"))
        }

        @Test
        @DisplayName("returns empty map when recipients list is empty")
        fun `broadcastPerplexitySignal returns empty map for empty recipients`() {
            val result = ManusBridgeMediator.broadcastPerplexitySignal("test", emptyList())
            assertTrue(result.isEmpty(), "Broadcasting to empty recipients list should return empty map")
        }

        @Test
        @DisplayName("works with a single recipient")
        fun `broadcastPerplexitySignal works with single recipient`() {
            val result = ManusBridgeMediator.broadcastPerplexitySignal(
                "single_payload",
                listOf("Aura")
            )
            assertEquals(1, result.size)
            assertTrue(result.containsKey("Aura"))
        }

        @Test
        @DisplayName("values use routePerplexitySignal format")
        fun `broadcastPerplexitySignal values use routePerplexitySignal format`() {
            val payload = "broadcast_format_test"
            val result = ManusBridgeMediator.broadcastPerplexitySignal(payload, listOf("Cascade"))
            val cascadeSignal = result["Cascade"]
            val expected = ManusBridgeMediator.routePerplexitySignal(payload, "Cascade")
            assertEquals(expected, cascadeSignal,
                "broadcastPerplexitySignal should delegate to routePerplexitySignal for each agent")
        }

        @Test
        @DisplayName("handles empty signal payload without throwing")
        fun `broadcastPerplexitySignal handles empty payload`() {
            val result = ManusBridgeMediator.broadcastPerplexitySignal("", listOf("Cascade"))
            assertFalse(result.isEmpty())
            assertNotNull(result["Cascade"])
        }
    }
}