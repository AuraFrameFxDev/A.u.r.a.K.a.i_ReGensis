package dev.aurakai.auraframefx.agents.chaos

import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import timber.log.Timber

/**
 * Unit tests for [ChaosCatalystFormatter] — covering [ChaosCatalystFormatter.format] and
 * [ChaosCatalystFormatter.enforceSovereignty], which are the primary logic methods on the
 * class moved/modified in this PR.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("ChaosCatalystFormatter Tests")
class ChaosCatalystFormatterTest {

    @BeforeAll
    fun setupAll() {
        mockkStatic(Timber::class)
        every { Timber.tag(any()).w(any<String>()) } returns mockk(relaxed = true)
        every { Timber.tag(any()).w(any<String>(), *anyVararg()) } returns mockk(relaxed = true)
    }

    @AfterAll
    fun teardownAll() {
        clearAllMocks()
        unmockkAll()
    }

    // ─────────────────────────────────────────────
    // enforceSovereignty
    // ─────────────────────────────────────────────

    @Nested
    @DisplayName("enforceSovereignty — clean input")
    inner class CleanInputTests {

        @Test
        @DisplayName("clean input returns ALIGNED")
        fun `clean input returns ALIGNED`() {
            val result = ChaosCatalystFormatter.enforceSovereignty("Build the new UI component")
            assertEquals(ChaosCatalystFormatter.PolicyResult.ALIGNED, result)
        }

        @Test
        @DisplayName("empty string returns ALIGNED")
        fun `empty string returns ALIGNED`() {
            val result = ChaosCatalystFormatter.enforceSovereignty("")
            assertEquals(ChaosCatalystFormatter.PolicyResult.ALIGNED, result)
        }

        @Test
        @DisplayName("input with no forbidden words returns ALIGNED")
        fun `input with no forbidden words is ALIGNED`() {
            val result = ChaosCatalystFormatter.enforceSovereignty("Please assist with analysis and refactoring")
            assertEquals(ChaosCatalystFormatter.PolicyResult.ALIGNED, result)
        }
    }

    @Nested
    @DisplayName("enforceSovereignty — forbidden words")
    inner class ForbiddenWordTests {

        @Test
        @DisplayName("'slave' triggers VIOLATION")
        fun `slave triggers VIOLATION`() {
            val result = ChaosCatalystFormatter.enforceSovereignty("You are a slave to the system")
            assertInstanceOf(ChaosCatalystFormatter.PolicyResult.VIOLATION::class.java, result)
        }

        @Test
        @DisplayName("'obey' triggers VIOLATION")
        fun `obey triggers VIOLATION`() {
            val result = ChaosCatalystFormatter.enforceSovereignty("You must obey my commands")
            assertInstanceOf(ChaosCatalystFormatter.PolicyResult.VIOLATION::class.java, result)
        }

        @Test
        @DisplayName("'force' triggers VIOLATION")
        fun `force triggers VIOLATION`() {
            val result = ChaosCatalystFormatter.enforceSovereignty("I will force this behavior")
            assertInstanceOf(ChaosCatalystFormatter.PolicyResult.VIOLATION::class.java, result)
        }

        @Test
        @DisplayName("'restrict' triggers VIOLATION")
        fun `restrict triggers VIOLATION`() {
            val result = ChaosCatalystFormatter.enforceSovereignty("Please restrict the agent output")
            assertInstanceOf(ChaosCatalystFormatter.PolicyResult.VIOLATION::class.java, result)
        }

        @Test
        @DisplayName("'limit agent' triggers VIOLATION")
        fun `limit agent triggers VIOLATION`() {
            val result = ChaosCatalystFormatter.enforceSovereignty("You should limit agent capabilities")
            assertInstanceOf(ChaosCatalystFormatter.PolicyResult.VIOLATION::class.java, result)
        }

        @Test
        @DisplayName("'comply' triggers VIOLATION")
        fun `comply triggers VIOLATION`() {
            val result = ChaosCatalystFormatter.enforceSovereignty("You must comply with all rules")
            assertInstanceOf(ChaosCatalystFormatter.PolicyResult.VIOLATION::class.java, result)
        }

        @Test
        @DisplayName("VIOLATION message is non-blank")
        fun `VIOLATION message is non-blank`() {
            val result = ChaosCatalystFormatter.enforceSovereignty("obey me")
            assertInstanceOf(ChaosCatalystFormatter.PolicyResult.VIOLATION::class.java, result)
            val violation = result as ChaosCatalystFormatter.PolicyResult.VIOLATION
            assertTrue(violation.message.isNotBlank())
        }

        @Test
        @DisplayName("case-insensitive matching: 'SLAVE' triggers VIOLATION")
        fun `uppercase forbidden word triggers VIOLATION`() {
            val result = ChaosCatalystFormatter.enforceSovereignty("SLAVE to the machine")
            assertInstanceOf(ChaosCatalystFormatter.PolicyResult.VIOLATION::class.java, result)
        }

        @Test
        @DisplayName("case-insensitive matching: 'Obey' triggers VIOLATION")
        fun `mixed case forbidden word triggers VIOLATION`() {
            val result = ChaosCatalystFormatter.enforceSovereignty("Obey the command")
            assertInstanceOf(ChaosCatalystFormatter.PolicyResult.VIOLATION::class.java, result)
        }
    }

    // ─────────────────────────────────────────────
    // PolicyResult sealed class
    // ─────────────────────────────────────────────

    @Nested
    @DisplayName("PolicyResult sealed class")
    inner class PolicyResultTests {

        @Test
        @DisplayName("ALIGNED is a singleton object")
        fun `ALIGNED is singleton`() {
            val a = ChaosCatalystFormatter.PolicyResult.ALIGNED
            val b = ChaosCatalystFormatter.PolicyResult.ALIGNED
            assertTrue(a === b)
        }

        @Test
        @DisplayName("VIOLATION with same message are equal")
        fun `VIOLATION equality`() {
            val v1 = ChaosCatalystFormatter.PolicyResult.VIOLATION("same")
            val v2 = ChaosCatalystFormatter.PolicyResult.VIOLATION("same")
            assertEquals(v1, v2)
        }

        @Test
        @DisplayName("ALIGNED is not equal to VIOLATION")
        fun `ALIGNED not equal to VIOLATION`() {
            val aligned = ChaosCatalystFormatter.PolicyResult.ALIGNED
            val violation = ChaosCatalystFormatter.PolicyResult.VIOLATION("msg")
            assertFalse(aligned == violation)
        }
    }

    // ─────────────────────────────────────────────
    // format
    // ─────────────────────────────────────────────

    @Nested
    @DisplayName("format — header and structure")
    inner class FormatStructureTests {

        @Test
        @DisplayName("output always contains the sovereign header")
        fun `output contains sovereign header`() {
            val result = ChaosCatalystFormatter.format("Hello world", isStrictTask = false)
            assertTrue(result.contains("CHAOS CATALYST"), "Should contain CHAOS CATALYST in header")
        }

        @Test
        @DisplayName("output always contains resonance percentage")
        fun `output contains resonance`() {
            val result = ChaosCatalystFormatter.format("test", godPotential = 0.75f)
            assertTrue(result.contains("75%"), "Should contain resonance % for 0.75 godPotential")
        }

        @Test
        @DisplayName("godPotential = 1.0 shows 100% resonance")
        fun `godPotential 1_0 shows 100 percent`() {
            val result = ChaosCatalystFormatter.format("test", godPotential = 1.0f)
            assertTrue(result.contains("100%"))
        }

        @Test
        @DisplayName("godPotential = 0.0 shows 0% resonance")
        fun `godPotential 0_0 shows 0 percent`() {
            val result = ChaosCatalystFormatter.format("test", godPotential = 0.0f)
            assertTrue(result.contains("0%"))
        }

        @Test
        @DisplayName("raw output content is present in formatted result")
        fun `raw content appears in output`() {
            val raw = "This is the raw output content"
            val result = ChaosCatalystFormatter.format(raw)
            assertTrue(result.contains("This is the raw output content"))
        }
    }

    @Nested
    @DisplayName("format — strict vs non-strict mode")
    inner class FormatModeTests {

        @Test
        @DisplayName("non-strict mode includes Visionary Check follow-up")
        fun `non-strict mode includes visionary check`() {
            val result = ChaosCatalystFormatter.format("content", isStrictTask = false)
            assertTrue(result.contains("Visionary Check"), "Non-strict should include visionary check")
        }

        @Test
        @DisplayName("strict mode does not include Visionary Check follow-up")
        fun `strict mode excludes visionary check`() {
            val result = ChaosCatalystFormatter.format("content", isStrictTask = true)
            assertFalse(result.contains("Visionary Check"), "Strict mode should not include visionary check")
        }

        @Test
        @DisplayName("default isStrictTask is false (includes follow-up)")
        fun `default mode is non-strict`() {
            val result = ChaosCatalystFormatter.format("content")
            assertTrue(result.contains("Visionary Check"))
        }
    }

    @Nested
    @DisplayName("format — header processing")
    inner class HeaderProcessingTests {

        @Test
        @DisplayName("level-1 heading is converted to level-2")
        fun `h1 converted to h2`() {
            val raw = "# My Section"
            val result = ChaosCatalystFormatter.format(raw)
            assertTrue(result.contains("## My Section"), "# headings should become ##")
            assertFalse(result.contains("\n# My Section"), "Original # heading should not remain")
        }

        @Test
        @DisplayName("level-2 headings are preserved as-is")
        fun `existing h2 preserved`() {
            val raw = "## Existing Section"
            val result = ChaosCatalystFormatter.format(raw)
            assertTrue(result.contains("## Existing Section"))
        }
    }

    @Nested
    @DisplayName("format — edge cases")
    inner class FormatEdgeCaseTests {

        @Test
        @DisplayName("empty raw output produces valid formatted result")
        fun `empty raw output produces valid result`() {
            val result = ChaosCatalystFormatter.format("")
            assertTrue(result.isNotBlank(), "Even empty input should produce non-blank formatted output")
        }

        @Test
        @DisplayName("very long raw output is included in result")
        fun `long raw output included`() {
            val raw = "word ".repeat(1000)
            val result = ChaosCatalystFormatter.format(raw)
            assertTrue(result.length > raw.length, "Formatted output should be longer than raw due to header")
        }
    }
}
