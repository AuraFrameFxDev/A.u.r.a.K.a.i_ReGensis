package dev.aurakai.auraframefx.api.client.models.data.room

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

/**
 * Unit tests for [L1_Memory_Store] — focused on the new PR-documented behavior:
 * - blank pattern returns empty list
 * - exact match is case-insensitive
 * - prefix wildcard (trailing *) matching
 * - regex fallback for mid-string wildcards
 * - store() and commit() methods
 * - clear() for isolation
 *
 * Note: A separate L1MemoryStoreTest already exists for NexusMemoryCore delegation tests.
 * These tests are specifically for the L1_Memory_Store public API as modified in this PR.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("L1_Memory_Store Query Tests (PR changes)")
class L1MemoryStoreQueryTest {

    @BeforeEach
    fun setUp() {
        L1_Memory_Store.clear()
    }

    @AfterEach
    fun tearDown() {
        L1_Memory_Store.clear()
    }

    @Nested
    @DisplayName("Blank and empty pattern guard")
    inner class BlankPatternTests {

        @Test
        @DisplayName("blank pattern returns empty list")
        fun `blank pattern returns empty list`() {
            L1_Memory_Store.store("anyKey", "anyValue")
            val result = L1_Memory_Store.query("   ")
            assertTrue(result.isEmpty(), "Blank pattern should return empty list")
        }

        @Test
        @DisplayName("empty pattern returns empty list")
        fun `empty pattern returns empty list`() {
            L1_Memory_Store.store("key1", "value1")
            val result = L1_Memory_Store.query("")
            assertTrue(result.isEmpty(), "Empty pattern should return empty list")
        }

        @Test
        @DisplayName("tab-only pattern returns empty list")
        fun `tab-only pattern returns empty list`() {
            L1_Memory_Store.store("key", "value")
            val result = L1_Memory_Store.query("\t")
            assertTrue(result.isEmpty(), "Whitespace-only pattern should return empty list")
        }
    }

    @Nested
    @DisplayName("Exact match — case-insensitive fast path")
    inner class ExactMatchTests {

        @Test
        @DisplayName("exact key match returns stored value")
        fun `exact key match returns value`() {
            L1_Memory_Store.store("ExactKey", "hello")
            val result = L1_Memory_Store.query("ExactKey")
            assertTrue(result.contains("hello"))
        }

        @Test
        @DisplayName("lowercase query matches uppercase stored key")
        fun `lowercase query matches uppercase key`() {
            L1_Memory_Store.store("MYKEY", "value1")
            val result = L1_Memory_Store.query("mykey")
            assertTrue(result.contains("value1"), "Exact match should be case-insensitive")
        }

        @Test
        @DisplayName("uppercase query matches lowercase stored key")
        fun `uppercase query matches lowercase key`() {
            L1_Memory_Store.store("mykey", "value2")
            val result = L1_Memory_Store.query("MYKEY")
            assertTrue(result.contains("value2"))
        }

        @Test
        @DisplayName("mixed-case query matches mixed-case stored key")
        fun `mixed case query matches mixed case key`() {
            L1_Memory_Store.store("CamelCaseKey", "camelValue")
            val result = L1_Memory_Store.query("camelcasekey")
            assertTrue(result.contains("camelValue"))
        }

        @Test
        @DisplayName("non-existing exact key returns empty list")
        fun `non-existing exact key returns empty list`() {
            val result = L1_Memory_Store.query("NON_EXISTENT_KEY_XYZ_42")
            assertTrue(result.isEmpty())
        }

        @Test
        @DisplayName("exact match does not return substring matches")
        fun `exact match does not return substring matches`() {
            L1_Memory_Store.store("ShortKey", "val1")
            L1_Memory_Store.store("ShortKeyExtended", "val2")
            val result = L1_Memory_Store.query("ShortKey")
            assertTrue(result.contains("val1"))
            assertFalse(result.contains("val2"), "Exact match should not match longer keys")
        }
    }

    @Nested
    @DisplayName("Prefix wildcard — trailing asterisk fast path")
    inner class PrefixWildcardTests {

        @Test
        @DisplayName("prefix wildcard matches all keys with that prefix")
        fun `prefix wildcard matches all with prefix`() {
            L1_Memory_Store.store("PREFIX_A", "va")
            L1_Memory_Store.store("PREFIX_B", "vb")
            L1_Memory_Store.store("OTHER_C", "vc")

            val result = L1_Memory_Store.query("PREFIX_*")
            assertTrue(result.contains("va"))
            assertTrue(result.contains("vb"))
            assertFalse(result.contains("vc"))
        }

        @Test
        @DisplayName("prefix wildcard is case-insensitive")
        fun `prefix wildcard is case-insensitive`() {
            L1_Memory_Store.store("LOWERCASE_key", "lval")
            val result = L1_Memory_Store.query("lowercase_*")
            assertTrue(result.contains("lval"), "Prefix wildcard should be case-insensitive")
        }

        @Test
        @DisplayName("wildcard-only pattern '*' matches all stored keys")
        fun `wildcard-only matches all`() {
            L1_Memory_Store.store("k1", "v1")
            L1_Memory_Store.store("k2", "v2")
            val result = L1_Memory_Store.query("*")
            assertTrue(result.contains("v1"))
            assertTrue(result.contains("v2"))
        }

        @Test
        @DisplayName("prefix with no matching keys returns empty list")
        fun `no matching prefix returns empty list`() {
            L1_Memory_Store.store("some_key", "val")
            val result = L1_Memory_Store.query("NO_MATCH_PREFIX_*")
            assertTrue(result.isEmpty())
        }

        @Test
        @DisplayName("prefix wildcard does not match shorter key that is a substring")
        fun `prefix wildcard handles prefix-only pattern`() {
            // "AB" should not be matched by "ABC*"
            L1_Memory_Store.store("AB", "short_val")
            L1_Memory_Store.store("ABCDEF", "long_val")
            val result = L1_Memory_Store.query("ABC*")
            assertFalse(result.contains("short_val"))
            assertTrue(result.contains("long_val"))
        }
    }

    @Nested
    @DisplayName("Regex fallback — non-trailing wildcards")
    inner class RegexFallbackTests {

        @Test
        @DisplayName("middle wildcard matches keys of correct shape")
        fun `middle wildcard matches correctly`() {
            L1_Memory_Store.store("START_middle_END", "match_val")
            L1_Memory_Store.store("START_END", "no_match_val")

            val result = L1_Memory_Store.query("START_*_END")
            assertTrue(result.contains("match_val"), "Regex fallback should match mid-string wildcard")
        }

        @Test
        @DisplayName("regex fallback is case-insensitive")
        fun `regex fallback is case-insensitive`() {
            L1_Memory_Store.store("ALPHA_beta_GAMMA", "rgx_val")
            val result = L1_Memory_Store.query("alpha_*_gamma")
            assertTrue(result.contains("rgx_val"), "Regex fallback should be case-insensitive")
        }

        @Test
        @DisplayName("regex special chars in prefix are escaped — dot does not act as wildcard")
        fun `regex special chars in literal part are escaped`() {
            L1_Memory_Store.store("key.name", "dot_val")
            L1_Memory_Store.store("keyXname", "x_val")

            // "key.name" without a wildcard — goes through exact path, not regex
            val exactResult = L1_Memory_Store.query("key.name")
            assertTrue(exactResult.contains("dot_val"), "Exact literal dot should match key.name")
            assertFalse(exactResult.contains("x_val"), "Literal dot should NOT match keyXname")
        }

        @Test
        @DisplayName("multiple wildcards in pattern all match")
        fun `multiple wildcards in pattern`() {
            L1_Memory_Store.store("A_B_C", "abc_val")
            L1_Memory_Store.store("A_SOMETHING_C", "asc_val")
            L1_Memory_Store.store("X_Y_Z", "xyz_val")

            val result = L1_Memory_Store.query("A_*_C")
            assertTrue(result.contains("abc_val"))
            assertTrue(result.contains("asc_val"))
            assertFalse(result.contains("xyz_val"))
        }
    }

    @Nested
    @DisplayName("store() and commit() methods")
    inner class StoreAndCommitTests {

        @Test
        @DisplayName("store() persists Any value retrievable by query")
        fun `store persists Any value`() {
            val obj = listOf(1, 2, 3)
            L1_Memory_Store.store("listKey", obj)
            val result = L1_Memory_Store.query("listKey")
            assertTrue(result.contains(obj))
        }

        @Test
        @DisplayName("store() overwrites existing value for same key")
        fun `store overwrites existing value`() {
            L1_Memory_Store.store("overwriteKey", "first")
            L1_Memory_Store.store("overwriteKey", "second")
            val result = L1_Memory_Store.query("overwriteKey")
            assertEquals(1, result.size)
            assertTrue(result.contains("second"))
            assertFalse(result.contains("first"))
        }

        @Test
        @DisplayName("commit() is an alias for store() with String value")
        fun `commit is alias for store`() {
            L1_Memory_Store.commit("commitKey", "commitValue")
            val result = L1_Memory_Store.query("commitKey")
            assertTrue(result.contains("commitValue"))
        }

        @Test
        @DisplayName("commit() overwrites existing key")
        fun `commit overwrites existing key`() {
            L1_Memory_Store.commit("dupKey", "original")
            L1_Memory_Store.commit("dupKey", "updated")
            val result = L1_Memory_Store.query("dupKey")
            assertEquals(1, result.size)
            assertTrue(result.contains("updated"))
        }

        @Test
        @DisplayName("store() with integer value is retrievable")
        fun `store with integer value`() {
            L1_Memory_Store.store("intKey", 42)
            val result = L1_Memory_Store.query("intKey")
            assertTrue(result.contains(42))
        }
    }

    @Nested
    @DisplayName("clear() for isolation")
    inner class ClearTests {

        @Test
        @DisplayName("clear() removes all stored entries")
        fun `clear removes all entries`() {
            L1_Memory_Store.store("k1", "v1")
            L1_Memory_Store.store("k2", "v2")
            L1_Memory_Store.clear()

            val result = L1_Memory_Store.query("*")
            assertTrue(result.isEmpty(), "Store should be empty after clear()")
        }

        @Test
        @DisplayName("clear() allows new entries to be added after")
        fun `clear allows re-use of store`() {
            L1_Memory_Store.store("key", "old")
            L1_Memory_Store.clear()
            L1_Memory_Store.store("key", "new")
            val result = L1_Memory_Store.query("key")
            assertTrue(result.contains("new"))
            assertFalse(result.contains("old"))
        }
    }
}