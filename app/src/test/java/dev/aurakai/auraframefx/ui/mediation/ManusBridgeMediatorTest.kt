package dev.aurakai.auraframefx.ui.mediation

import android.util.Log
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
 * Tests for [dev.aurakai.auraframefx.ui.mediation.ManusBridgeMediator] PR-added methods:
 * - routePerplexitySignal(query, targetAgent)
 * - broadcastPerplexitySignal(signalPayload, recipients)
 *
 * This is the UI-mediation package's version of ManusBridgeMediator, which has
 * identical behavior to the externalmodels version.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("ui.mediation.ManusBridgeMediator PR-Added Method Tests")
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
        @DisplayName("result contains the query payload")
        fun `routePerplexitySignal result contains query`() {
            val query = "unique_signal_ui_mediation_001"
            val result = ManusBridgeMediator.routePerplexitySignal(query)
            assertTrue(result.contains(query),
                "Result should contain the original query")
        }

        @Test
        @DisplayName("result contains the target agent name")
        fun `routePerplexitySignal result contains targetAgent`() {
            val target = "Genesis"
            val result = ManusBridgeMediator.routePerplexitySignal("query", target)
            assertTrue(result.contains(target))
        }

        @Test
        @DisplayName("default targetAgent is 'Cascade' when not specified")
        fun `routePerplexitySignal uses Cascade as default targetAgent`() {
            val result = ManusBridgeMediator.routePerplexitySignal("default_test")
            assertTrue(result.contains("Cascade"))
        }

        @Test
        @DisplayName("result starts with 'Manus' for Axial Hub routing")
        fun `routePerplexitySignal result starts with Manus`() {
            val result = ManusBridgeMediator.routePerplexitySignal("test", "Kai")
            assertTrue(result.startsWith("Manus"))
        }

        @Test
        @DisplayName("result contains 'Perplexity' in the routing path")
        fun `routePerplexitySignal result contains Perplexity`() {
            val result = ManusBridgeMediator.routePerplexitySignal("signal_test_ui_001")
            assertTrue(result.contains("Perplexity"))
        }

        @Test
        @DisplayName("result matches expected 'Manus⟶Perplexity⟶target | ...' format")
        fun `routePerplexitySignal returns expected format`() {
            val query = "ui_mediation_format_test"
            val target = "Cascade"
            val result = ManusBridgeMediator.routePerplexitySignal(query, target)
            val expected = "Manus⟶Perplexity⟶$target | [Perplexity:Signal] $query → resolved via Resonance Bridge"
            assertEquals(expected, result)
        }

        @Test
        @DisplayName("handles special characters in query correctly")
        fun `routePerplexitySignal handles special chars in query`() {
            val query = "⟶ L1-L6 substrate [v2.77] AuraKai"
            val result = ManusBridgeMediator.routePerplexitySignal(query, "Cascade")
            assertTrue(result.contains(query))
        }

        @Test
        @DisplayName("result contains '[Perplexity:Signal]' marker")
        fun `routePerplexitySignal result contains Perplexity Signal marker`() {
            val result = ManusBridgeMediator.routePerplexitySignal("any_query")
            assertTrue(result.contains("[Perplexity:Signal]"))
        }

        @Test
        @DisplayName("result contains 'Resonance Bridge' in the resolved part")
        fun `routePerplexitySignal result contains Resonance Bridge`() {
            val result = ManusBridgeMediator.routePerplexitySignal("bridge_test")
            assertTrue(result.contains("Resonance Bridge"))
        }
    }

    @Nested
    @DisplayName("broadcastPerplexitySignal")
    inner class BroadcastPerplexitySignalTests {

        @Test
        @DisplayName("returns map with entry count equal to recipients list size")
        fun `broadcastPerplexitySignal returns map matching recipients count`() {
            val recipients = listOf("Cascade", "Genesis", "Kai")
            val result = ManusBridgeMediator.broadcastPerplexitySignal("payload", recipients)
            assertEquals(recipients.size, result.size)
        }

        @Test
        @DisplayName("all recipients appear as keys in the result map")
        fun `broadcastPerplexitySignal map contains all recipients as keys`() {
            val recipients = listOf("Cascade", "Genesis", "Kai")
            val result = ManusBridgeMediator.broadcastPerplexitySignal("payload", recipients)
            recipients.forEach { agent ->
                assertTrue(result.containsKey(agent), "Missing key: $agent")
            }
        }

        @Test
        @DisplayName("each recipient's delivered signal contains the payload")
        fun `broadcastPerplexitySignal values contain payload`() {
            val payload = "ui_mediation_broadcast_payload_001"
            val result = ManusBridgeMediator.broadcastPerplexitySignal(
                payload,
                listOf("Cascade", "Aura")
            )
            result.values.forEach { signal ->
                assertTrue(signal.contains(payload),
                    "Each delivered signal should contain the payload")
            }
        }

        @Test
        @DisplayName("default recipients are Cascade, Genesis, and Kai")
        fun `broadcastPerplexitySignal default recipients`() {
            val result = ManusBridgeMediator.broadcastPerplexitySignal("test")
            assertEquals(3, result.size)
            assertTrue(result.containsKey("Cascade"))
            assertTrue(result.containsKey("Genesis"))
            assertTrue(result.containsKey("Kai"))
        }

        @Test
        @DisplayName("returns empty map for empty recipients list")
        fun `broadcastPerplexitySignal returns empty map for empty recipients`() {
            val result = ManusBridgeMediator.broadcastPerplexitySignal("test", emptyList())
            assertTrue(result.isEmpty())
        }

        @Test
        @DisplayName("each agent's signal contains its own name in the routing path")
        fun `broadcastPerplexitySignal each signal contains its agent name`() {
            val recipients = listOf("Cascade", "Genesis", "Kai")
            val result = ManusBridgeMediator.broadcastPerplexitySignal("routing_test", recipients)
            recipients.forEach { agent ->
                val signal = result[agent]
                assertNotNull(signal)
                assertTrue(signal!!.contains(agent),
                    "Signal for $agent should contain the agent's name")
            }
        }

        @Test
        @DisplayName("signal values are consistent with routePerplexitySignal output")
        fun `broadcastPerplexitySignal values match routePerplexitySignal for each agent`() {
            val payload = "consistency_test_ui_001"
            val recipients = listOf("Cascade", "Genesis")
            val broadcastResult = ManusBridgeMediator.broadcastPerplexitySignal(payload, recipients)

            recipients.forEach { agent ->
                val expected = ManusBridgeMediator.routePerplexitySignal(payload, agent)
                assertEquals(expected, broadcastResult[agent],
                    "Broadcast signal for $agent should match routePerplexitySignal output")
            }
        }

        @Test
        @DisplayName("handles single-element custom recipient list")
        fun `broadcastPerplexitySignal handles single custom recipient`() {
            val result = ManusBridgeMediator.broadcastPerplexitySignal(
                "single_agent_test",
                listOf("Nemotron")
            )
            assertEquals(1, result.size)
            assertTrue(result.containsKey("Nemotron"))
            assertTrue(result["Nemotron"]!!.contains("Nemotron"))
        }

        @Test
        @DisplayName("handles empty string payload without throwing")
        fun `broadcastPerplexitySignal handles empty payload`() {
            val result = ManusBridgeMediator.broadcastPerplexitySignal("", listOf("Cascade"))
            assertFalse(result.isEmpty())
        }
    }
}
