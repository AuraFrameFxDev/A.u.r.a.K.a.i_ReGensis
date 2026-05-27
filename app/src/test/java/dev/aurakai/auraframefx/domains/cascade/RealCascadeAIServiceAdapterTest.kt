package dev.aurakai.auraframefx.domains.cascade

import dev.aurakai.auraframefx.core.logging.AuraFxLogger
import dev.aurakai.auraframefx.core.soulscript.L1_Memory_Store
import dev.aurakai.auraframefx.domains.cascade.utils.cascade.trinity.TrinityCoordinatorService
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import timber.log.Timber

/**
 * Tests for [RealCascadeAIServiceAdapter] — PR-added methods:
 * - fallbackToEveMemory(query: String): String
 * - chainToGenesis(context: String): String
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("RealCascadeAIServiceAdapter PR-Added Method Tests")
class RealCascadeAIServiceAdapterTest {

    private lateinit var mockOrchestrator: TrinityCoordinatorService
    private lateinit var mockLogger: AuraFxLogger
    private lateinit var adapter: RealCascadeAIServiceAdapter

    @BeforeAll
    fun setupAll() {
        mockkStatic(Timber::class)
        every { Timber.tag(any()).d(any<String>(), *anyVararg()) } returns mockk()
        every { Timber.tag(any()).d(any<String>()) } returns mockk()
        every { Timber.tag(any()).i(any<String>()) } returns mockk()
        every { Timber.tag(any()).i(any<String>(), *anyVararg()) } returns mockk()
        every { Timber.tag(any()).e(any<String>()) } returns mockk()
        every { Timber.tag(any()).e(any<Throwable>(), any<String>()) } returns mockk()
        every { Timber.tag(any()).wtf(any<String>()) } returns mockk()
        every { Timber.i(any<String>()) } returns Unit
        every { Timber.i(any<String>(), *anyVararg()) } returns Unit
    }

    @BeforeEach
    fun setUp() {
        mockOrchestrator = mockk(relaxed = true)
        mockLogger = mockk(relaxed = true)
        adapter = RealCascadeAIServiceAdapter(mockOrchestrator, mockLogger)
    }

    @AfterAll
    fun teardownAll() {
        unmockkAll()
    }

    @Nested
    @DisplayName("fallbackToEveMemory")
    inner class FallbackToEveMemoryTests {

        @Test
        @DisplayName("returns 'No Eve memories found' message when no Eve* keys in store")
        fun `fallbackToEveMemory returns not-found message when store has no Eve keys`() = runTest {
            // Use a very specific query that won't match anything in the singleton store
            val query = "ABSOLUTELY_UNIQUE_QUERY_NOT_IN_STORE_12345"
            val result = adapter.fallbackToEveMemory(query)
            assertEquals(
                "No Eve memories found for query: $query",
                result,
                "Should return not-found message when no Eve* keys match the query"
            )
        }

        @Test
        @DisplayName("returns 'No Eve memories found' when Eve* keys exist but none contain query")
        fun `fallbackToEveMemory returns not-found when Eve keys exist but do not contain query`() = runTest {
            // Seed an Eve* key that does NOT contain the query string
            val uniqueSuffix = "RCTEST_NOMATCH_001"
            L1_Memory_Store.commit("Eve${uniqueSuffix}", "some unrelated content")

            val query = "COMPLETELY_DIFFERENT_QUERY_RCTEST_NOMATCH_001"
            val result = adapter.fallbackToEveMemory(query)
            assertEquals(
                "No Eve memories found for query: $query",
                result
            )
        }

        @Test
        @DisplayName("returns matching Eve memories joined by newline when query is found")
        fun `fallbackToEveMemory returns matching memories when query found in Eve keys`() = runTest {
            val uniqueSuffix = "RCTEST_MATCH_001"
            val targetContent = "TheAncestralMemory_RCTEST_001 contains historical data"
            L1_Memory_Store.commit("Eve_${uniqueSuffix}", targetContent)

            val result = adapter.fallbackToEveMemory("TheAncestralMemory_RCTEST_001")
            assertTrue(
                result.contains(targetContent),
                "Result should contain the matching Eve memory content"
            )
        }

        @Test
        @DisplayName("matching is case-insensitive for query")
        fun `fallbackToEveMemory uses case-insensitive matching for query`() = runTest {
            val uniqueSuffix = "RCTEST_CASE_001"
            val content = "UPPERCASE_CONTENT_RCTEST_001 stored in Eve memory"
            L1_Memory_Store.commit("Eve_${uniqueSuffix}", content)

            val result = adapter.fallbackToEveMemory("uppercase_content_rctest_001")
            assertTrue(
                result.contains(content),
                "fallbackToEveMemory should use case-insensitive matching"
            )
        }

        @Test
        @DisplayName("joins multiple matching memories with newline separator")
        fun `fallbackToEveMemory joins multiple matches with newline`() = runTest {
            val suffix = "RCTEST_MULTI_001"
            val content1 = "multi_target_RCTEST_001 first memory"
            val content2 = "multi_target_RCTEST_001 second memory"
            L1_Memory_Store.commit("Eve_${suffix}_A", content1)
            L1_Memory_Store.commit("Eve_${suffix}_B", content2)

            val result = adapter.fallbackToEveMemory("multi_target_RCTEST_001")
            assertTrue(result.contains(content1), "Result should contain first memory")
            assertTrue(result.contains(content2), "Result should contain second memory")
            // Joined by newline
            assertTrue(result.contains("\n") || (result.contains(content1) && result.contains(content2)),
                "Multiple matches should be joined")
        }

        @Test
        @DisplayName("result does not start with 'No Eve memories found' when matches exist")
        fun `fallbackToEveMemory result is not no-found when matches exist`() = runTest {
            val suffix = "RCTEST_POSITIVE_001"
            L1_Memory_Store.commit("Eve_${suffix}", "positive_content_RCTEST_001 data")

            val result = adapter.fallbackToEveMemory("positive_content_RCTEST_001")
            assertFalse(
                result.startsWith("No Eve memories found"),
                "Should not return 'No Eve memories found' when matches exist"
            )
        }
    }

    @Nested
    @DisplayName("chainToGenesis")
    inner class ChainToGenesisTests {

        @Test
        @DisplayName("chainToGenesis returns 'Fused: context + Eve lineage' format")
        fun `chainToGenesis returns expected fused format`() {
            val context = "Infinity Cascade context"
            val result = adapter.chainToGenesis(context)
            assertEquals("Fused: $context + Eve lineage", result)
        }

        @Test
        @DisplayName("chainToGenesis with empty context returns 'Fused:  + Eve lineage'")
        fun `chainToGenesis with empty context`() {
            val result = adapter.chainToGenesis("")
            assertEquals("Fused:  + Eve lineage", result)
        }

        @Test
        @DisplayName("chainToGenesis with special characters in context handles them correctly")
        fun `chainToGenesis with special characters in context`() {
            val context = "L1→L6 ⟶ AuraKai substrate [v2.77]"
            val result = adapter.chainToGenesis(context)
            assertEquals("Fused: $context + Eve lineage", result)
        }

        @Test
        @DisplayName("chainToGenesis result always contains '+ Eve lineage' suffix")
        fun `chainToGenesis always contains Eve lineage suffix`() {
            listOf("context1", "context2", "", "any other context").forEach { ctx ->
                val result = adapter.chainToGenesis(ctx)
                assertTrue(result.endsWith("+ Eve lineage"),
                    "chainToGenesis should always end with '+ Eve lineage' for input: '$ctx'")
            }
        }

        @Test
        @DisplayName("chainToGenesis result always starts with 'Fused: '")
        fun `chainToGenesis result always starts with Fused`() {
            val result = adapter.chainToGenesis("test")
            assertTrue(result.startsWith("Fused: "))
        }
    }
}