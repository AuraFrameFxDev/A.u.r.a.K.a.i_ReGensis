package dev.aurakai.auraframefx.core.soulscript

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

/**
 * Tests for [NexusMemoryCore.query] — verifies the PR-changed regex construction.
 *
 * PR change (NexusMemoryCore.kt line 136):
 *   Before: val regex = ("^$escapedPattern$").toRegex(RegexOption.IGNORE_CASE)
 *   After:  val regex = ("^" + escapedPattern + "$").toRegex(RegexOption.IGNORE_CASE)
 *
 * The string-template interpolation was replaced with explicit concatenation to
 * avoid potential issues with the `$` character inside a string template.  The
 * resulting regex strings must be functionally identical; these tests verify the
 * observable behaviour of the query() method under the new construction.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("NexusMemoryCore.query() — PR regex construction change")
class NexusMemoryCoreQueryTest {

    /**
     * Populate the in-memory store with known keys before each test so that
     * every test starts from a predictable state.  We use the public
     * [NexusMemoryCore.commit] overload that accepts a String key and Any value.
     */
    @BeforeEach
    fun seedStore() {
        NexusMemoryCore.commit("alpha", "value-alpha")
        NexusMemoryCore.commit("ALPHA", "value-ALPHA-upper")
        NexusMemoryCore.commit("alpha_beta", "value-alpha-beta")
        NexusMemoryCore.commit("gamma", "value-gamma")
        NexusMemoryCore.commit("gamma_delta", "value-gamma-delta")
        NexusMemoryCore.commit("special.key", "value-special-dot")
        NexusMemoryCore.commit("special(key)", "value-special-parens")
        NexusMemoryCore.commit("prefix_one", "value-prefix-one")
        NexusMemoryCore.commit("prefix_two", "value-prefix-two")
        NexusMemoryCore.commit("prefix_three", "value-prefix-three")
    }

    // ─── Blank / empty pattern ────────────────────────────────────────────────

    @Nested
    @DisplayName("Blank and empty pattern")
    inner class BlankPatternTests {

        @Test
        @DisplayName("query('') returns an empty list")
        fun emptyPatternReturnsEmptyList() {
            val result = NexusMemoryCore.query("")
            assertTrue(result.isEmpty(), "Blank pattern should return empty list, got: $result")
        }

        @Test
        @DisplayName("query('   ') returns an empty list")
        fun whitespaceOnlyPatternReturnsEmptyList() {
            val result = NexusMemoryCore.query("   ")
            assertTrue(result.isEmpty(), "Whitespace-only pattern should return empty list, got: $result")
        }
    }

    // ─── Exact-match patterns ─────────────────────────────────────────────────

    @Nested
    @DisplayName("Exact-match patterns")
    inner class ExactMatchTests {

        @Test
        @DisplayName("query('gamma') returns value for key 'gamma'")
        fun exactMatchReturnsExpectedValue() {
            val result = NexusMemoryCore.query("gamma")
            assertEquals(1, result.size, "Expected exactly 1 match for 'gamma', got: $result")
        }

        @Test
        @DisplayName("query for a non-existent key returns empty list")
        fun nonExistentKeyReturnsEmpty() {
            val result = NexusMemoryCore.query("does_not_exist_anywhere")
            assertTrue(result.isEmpty(), "Non-existent key should return empty list, got: $result")
        }

        @Test
        @DisplayName("query('alpha_beta') matches only the full key, not partial substrings")
        fun exactMatchDoesNotMatchSubstring() {
            // 'alpha' would be a substring of 'alpha_beta'; exact match must not include it
            val result = NexusMemoryCore.query("alpha_beta")
            assertEquals(1, result.size, "Exact match should return 1 result, got: $result")
            // Ensure the result is the exact entry and not the shorter 'alpha' key's value
            assertTrue(
                result.any { it.contains("alpha-beta") },
                "Result should be the value for 'alpha_beta', got: $result"
            )
        }

        @Test
        @DisplayName("query('alpha') does not match 'alpha_beta'")
        fun exactMatchDoesNotOvermatch() {
            // The regex must be anchored (^ and $), so 'alpha' must not match 'alpha_beta'
            // Case-insensitive: it may match 'ALPHA' as well as 'alpha'.
            val result = NexusMemoryCore.query("alpha")
            result.forEach { value ->
                assertTrue(
                    value.contains("alpha") && !value.contains("beta"),
                    "query('alpha') should not return 'alpha_beta' value, got: $result"
                )
            }
        }
    }

    // ─── Case-insensitive matching ────────────────────────────────────────────

    @Nested
    @DisplayName("Case-insensitive matching (IGNORE_CASE option)")
    inner class CaseInsensitiveTests {

        @Test
        @DisplayName("query('GAMMA') matches key 'gamma' (lowercase)")
        fun uppercasePatternMatchesLowercaseKey() {
            val result = NexusMemoryCore.query("GAMMA")
            assertTrue(result.isNotEmpty(), "IGNORE_CASE: 'GAMMA' should match 'gamma', got: $result")
        }

        @Test
        @DisplayName("query('gamma') matches key 'GAMMA' if inserted (case-insensitive)")
        fun lowercasePatternMatchesUppercaseKey() {
            // 'ALPHA' was seeded; 'alpha' pattern should match it
            val result = NexusMemoryCore.query("alpha")
            // Should match both 'alpha' and 'ALPHA' (case-insensitive)
            assertTrue(
                result.size >= 1,
                "IGNORE_CASE: 'alpha' should match at least 'alpha' and/or 'ALPHA', got: $result"
            )
        }
    }

    // ─── Wildcard (*) patterns ────────────────────────────────────────────────

    @Nested
    @DisplayName("Wildcard (*) pattern matching")
    inner class WildcardTests {

        @Test
        @DisplayName("query('prefix_*') matches all 'prefix_' prefixed keys")
        fun wildcardSuffixMatchesPrefixedKeys() {
            val result = NexusMemoryCore.query("prefix_*")
            assertEquals(3, result.size,
                "prefix_* should match prefix_one, prefix_two, prefix_three, got: $result")
        }

        @Test
        @DisplayName("query('*_beta') matches 'alpha_beta' (wildcard prefix)")
        fun wildcardPrefixMatchesSuffixedKey() {
            val result = NexusMemoryCore.query("*_beta")
            assertTrue(result.isNotEmpty(), "*_beta should match 'alpha_beta', got: $result")
            assertTrue(
                result.any { it.contains("alpha-beta") },
                "*_beta result should contain 'alpha_beta' value, got: $result"
            )
        }

        @Test
        @DisplayName("query('*') matches every key in the store")
        fun singleWildcardMatchesAllKeys() {
            val result = NexusMemoryCore.query("*")
            // Must match at least all seeded keys (store may have more from object singleton state)
            assertTrue(result.size >= 10,
                "* should match all seeded keys (at least 10), got ${result.size}")
        }

        @Test
        @DisplayName("query('gamma*') matches 'gamma' and 'gamma_delta'")
        fun wildcardSuffixMatchesBothExactAndPrefixed() {
            val result = NexusMemoryCore.query("gamma*")
            assertTrue(result.size >= 2,
                "gamma* should match 'gamma' and 'gamma_delta', got: $result")
        }

        @Test
        @DisplayName("query('*_*') matches all underscore-containing keys")
        fun wildcardAroundUnderscoreMatchesCompoundKeys() {
            val result = NexusMemoryCore.query("*_*")
            // Seeded compound keys: alpha_beta, gamma_delta, prefix_one, prefix_two, prefix_three
            assertTrue(result.size >= 5,
                "*_* should match at least 5 compound keys, got ${result.size}: $result")
        }

        @Test
        @DisplayName("wildcard does not match across key boundaries — 'prefix_*' does not match 'gamma'")
        fun wildcardDoesNotMatchUnrelatedKeys() {
            val result = NexusMemoryCore.query("prefix_*")
            result.forEach { value ->
                assertTrue(
                    value.contains("prefix"),
                    "prefix_* should only return values for prefix_ keys, got unexpected: $value"
                )
            }
        }
    }

    // ─── Special-character escaping ───────────────────────────────────────────

    @Nested
    @DisplayName("Special-character escaping in pattern")
    inner class SpecialCharacterTests {

        @Test
        @DisplayName("query('special.key') matches only the literal dot key, not as regex wildcard")
        fun dotInPatternIsEscapedNotTreatedAsWildcard() {
            // The PR code uses Regex.escape(), so '.' must be treated as a literal dot.
            // 'special.key' should match 'special.key' exactly and NOT 'specialXkey'.
            val result = NexusMemoryCore.query("special.key")
            assertTrue(result.isNotEmpty(), "special.key should match the literal 'special.key' key")
            result.forEach { value ->
                assertTrue(
                    value.contains("special-dot"),
                    "query('special.key') should return 'special.key' value, got: $value"
                )
            }
        }

        @Test
        @DisplayName("query('special(key)') matches the literal parentheses key")
        fun parenthesesInPatternAreEscaped() {
            val result = NexusMemoryCore.query("special(key)")
            assertTrue(result.isNotEmpty(),
                "special(key) should match the literal 'special(key)' key, got: $result")
        }

        @Test
        @DisplayName("query('special.*') uses wildcard AFTER the literal dot — matches 'special.key'")
        fun wildcardAfterLiteralDotMatchesDotSuffixedKeys() {
            // Pattern 'special.*' → escape gives 'special\.', then '\*' → '.*'
            // So the final regex is '^special\..*$' which should match 'special.key'
            val result = NexusMemoryCore.query("special.*")
            assertTrue(result.isNotEmpty(),
                "special.* should match 'special.key' (literal dot followed by wildcard), got: $result")
        }
    }

    // ─── Regression / boundary ────────────────────────────────────────────────

    @Nested
    @DisplayName("Regression and boundary cases")
    inner class RegressionTests {

        @Test
        @DisplayName("query returns a List (not null)")
        fun queryReturnsNonNullList() {
            val result = NexusMemoryCore.query("anything")
            // Should never be null regardless of match count
            assertTrue(result is List<*>, "query() must always return a List, got: ${result::class}")
        }

        @Test
        @DisplayName("query with only-asterisk pattern ('*') does not throw")
        fun singleWildcardDoesNotThrow() {
            var threw = false
            try {
                NexusMemoryCore.query("*")
            } catch (e: Exception) {
                threw = true
            }
            assertTrue(!threw, "query('*') must not throw an exception")
        }

        @Test
        @DisplayName("Repeated calls with the same pattern return consistent results")
        fun repeatedCallsAreIdempotent() {
            val first = NexusMemoryCore.query("gamma")
            val second = NexusMemoryCore.query("gamma")
            assertEquals(first.size, second.size,
                "Repeated query('gamma') calls should return same number of results")
        }
    }
}