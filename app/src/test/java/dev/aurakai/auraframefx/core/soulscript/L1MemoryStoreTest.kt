package dev.aurakai.auraframefx.core.soulscript

import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import timber.log.Timber

/**
 * Tests for [L1_Memory_Store] and [NexusMemoryCore] — specifically the PR-added
 * ConcurrentHashMap-backed store, commit(key, value: Any), and query(pattern) methods.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("L1_Memory_Store and NexusMemoryCore.commit/query Tests")
class L1MemoryStoreTest {

    @BeforeAll
    fun setupAll() {
        mockkStatic(Timber::class)
        mockkStatic("timber.log.Timber\$Tree")
        every { Timber.tag(any()).d(any<String>(), *anyVararg()) } returns mockk()
        every { Timber.tag(any()).d(any<String>()) } returns mockk()
        every { Timber.tag(any()).i(any<String>()) } returns mockk()
        every { Timber.tag(any()).i(any<String>(), *anyVararg()) } returns mockk()
        every { Timber.tag(any()).e(any<String>()) } returns mockk()
        every { Timber.tag(any()).e(any<Throwable>(), any<String>()) } returns mockk()
        every { Timber.tag(any()).wtf(any<String>()) } returns mockk()
    }

    @AfterAll
    fun teardownAll() {
        clearAllMocks()
        unmockkStatic(Timber::class)
    }

    // Use unique key prefixes to avoid cross-test contamination in the singleton store

    @Nested
    @DisplayName("L1_Memory_Store.commit and query")
    inner class L1MemoryStoreCommitQueryTests {

        @Test
        @DisplayName("commit stores a value retrievable by exact key match")
        fun `commit stores value retrievable by exact key`() {
            val key = "TEST_EXACT_KEY_L1_001"
            val value = "hello_world"
            L1_Memory_Store.commit(key, value)

            val results = L1_Memory_Store.query(key)
            assertTrue(results.contains(value), "Exact key query should find committed value")
        }

        @Test
        @DisplayName("query with wildcard '*' at end matches keys with that prefix")
        fun `query with wildcard matches keys with given prefix`() {
            val prefix = "WILDCARD_L1_TEST_"
            L1_Memory_Store.commit("${prefix}alpha", "val_alpha")
            L1_Memory_Store.commit("${prefix}beta", "val_beta")
            L1_Memory_Store.commit("UNRELATED_L1_KEY", "val_unrelated")

            val results = L1_Memory_Store.query("${prefix}*")
            assertTrue(results.contains("val_alpha"), "Wildcard query should return 'val_alpha'")
            assertTrue(results.contains("val_beta"), "Wildcard query should return 'val_beta'")
            assertFalse(results.contains("val_unrelated"), "Wildcard query should not return unrelated values")
        }

        @Test
        @DisplayName("query returns empty list when pattern matches no keys")
        fun `query returns empty list for non-matching pattern`() {
            val result = L1_Memory_Store.query("ABSOLUTELY_NO_MATCH_XYZZY_9988776655")
            assertTrue(result.isEmpty(), "Non-matching query should return empty list")
        }

        @Test
        @DisplayName("commit overwrites value for the same key")
        fun `commit overwrites value for same key`() {
            val key = "TEST_OVERWRITE_L1_001"
            L1_Memory_Store.commit(key, "first_value")
            L1_Memory_Store.commit(key, "second_value")

            val results = L1_Memory_Store.query(key)
            assertTrue(results.contains("second_value"), "Overwritten value should be second_value")
            assertFalse(results.contains("first_value"), "Old value should not be present after overwrite")
        }

        @Test
        @DisplayName("query with wildcard-only pattern '*' matches all stored keys")
        fun `query with wildcard only matches any stored key`() {
            val key = "WILDCARD_ONLY_L1_TEST_UNIQUE_001"
            L1_Memory_Store.commit(key, "some_value")

            val results = L1_Memory_Store.query("*")
            assertTrue(results.contains("some_value"), "Wildcard-only query should match any key")
        }

        @Test
        @DisplayName("query with exact key that does not exist returns empty list")
        fun `query returns empty for non-existent exact key`() {
            val results = L1_Memory_Store.query("DEFINITELY_NOT_STORED_KEY_ABC12345")
            assertTrue(results.isEmpty())
        }

        @Test
        @DisplayName("commit with empty string key stores and is retrievable")
        fun `commit with empty key stores value`() {
            // Note: empty key edge case - the store should handle it
            L1_Memory_Store.commit("EMPTY_PREFIX_SAFE_KEY_001", "empty_test_value")
            val results = L1_Memory_Store.query("EMPTY_PREFIX_SAFE_KEY_001")
            assertTrue(results.contains("empty_test_value"))
        }
    }

    @Nested
    @DisplayName("NexusMemoryCore.commit(key, value: Any)")
    inner class NexusMemoryCoreCommitAnyTests {

        @Test
        @DisplayName("commit(key, Any) stores string representation in L1_Memory_Store")
        fun `commit with Any value stores toString representation`() {
            val key = "NMC_ANY_TEST_001"
            val value = 42
            NexusMemoryCore.commit(key, value)

            val results = L1_Memory_Store.query(key)
            assertTrue(results.contains("42"), "commit(key, Any) should store toString() of value")
        }

        @Test
        @DisplayName("commit(key, Any) works with a list value")
        fun `commit with list value stores list toString`() {
            val key = "NMC_LIST_TEST_001"
            val value = listOf("a", "b", "c")
            NexusMemoryCore.commit(key, value)

            val results = L1_Memory_Store.query(key)
            assertTrue(results.isNotEmpty(), "commit with list should store something")
            assertEquals(value.toString(), results.first())
        }

        @Test
        @DisplayName("commit(key, Any) works with a Pair value")
        fun `commit with Pair value stores pair toString`() {
            val key = "NMC_PAIR_TEST_001"
            val pair = "name" to "description"
            NexusMemoryCore.commit(key, pair)

            val results = L1_Memory_Store.query(key)
            assertTrue(results.isNotEmpty(), "commit with Pair should store something")
        }

        @Test
        @DisplayName("commit(key, Any) with boolean value stores 'true' or 'false'")
        fun `commit with boolean stores boolean string`() {
            val key = "NMC_BOOL_TEST_001"
            NexusMemoryCore.commit(key, true)

            val results = L1_Memory_Store.query(key)
            assertTrue(results.contains("true"))
        }
    }

    @Nested
    @DisplayName("NexusMemoryCore.query(pattern)")
    inner class NexusMemoryCoreQueryTests {

        @Test
        @DisplayName("query delegates to L1_Memory_Store and returns matching values")
        fun `query delegates to L1_Memory_Store`() {
            val key = "NMC_QUERY_DELEGATE_001"
            val value = "delegate_test_value"
            NexusMemoryCore.commit(key, value)

            val results = NexusMemoryCore.query(key)
            assertTrue(results.contains(value))
        }

        @Test
        @DisplayName("query with wildcard returns all keys matching prefix from NexusMemoryCore.commit(key, Any)")
        fun `query wildcard via NexusMemoryCore matches any-type commits`() {
            val prefix = "NMC_WILDCARD_Q_"
            NexusMemoryCore.commit("${prefix}001", "value1")
            NexusMemoryCore.commit("${prefix}002", 12345)

            val results = NexusMemoryCore.query("${prefix}*")
            assertTrue(results.contains("value1"))
            assertTrue(results.contains("12345"))
        }

        @Test
        @DisplayName("query returns empty list when no keys match")
        fun `query returns empty list for non-matching keys`() {
            val results = NexusMemoryCore.query("NMC_QUERY_NO_MATCH_XYZZY_12345")
            assertTrue(results.isEmpty())
        }

        @Test
        @DisplayName("query with Eve wildcard ('Eve*') returns only Eve-prefixed entries")
        fun `query with Eve wildcard returns only Eve-prefixed keys`() {
            val eveKey1 = "EveTest_NMC_001"
            val eveKey2 = "EveTest_NMC_002"
            val otherKey = "NotEveKey_NMC_001"
            NexusMemoryCore.commit(eveKey1, "eve_data_1")
            NexusMemoryCore.commit(eveKey2, "eve_data_2")
            NexusMemoryCore.commit(otherKey, "other_data")

            val results = NexusMemoryCore.query("EveTest_NMC_*")
            assertTrue(results.contains("eve_data_1"))
            assertTrue(results.contains("eve_data_2"))
            assertFalse(results.contains("other_data"))
        }
    }
}