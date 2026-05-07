package dev.aurakai.auraframefx.domains.nexus.screens.ldo

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

/**
 * Tests for [LdoAgentType] enum in LdoDevOpsProfileScreen.
 *
 * PR changes tested:
 * 1. Removed entries: KAIROS, PRIMUS_001, ANDELUALX, META_INSTRUCT, MK_MINI
 * 2. Renamed: NEMOTRON → NEMATRON
 * 3. Remaining: AURA, KAI, GENESIS, CASCADE, GEMINI, MANUS, CLAUDE, GROK, NEMATRON, PERPLEXITY
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("LdoAgentType Enum Tests")
class LdoAgentTypeTest {

    @Nested
    @DisplayName("Retained agent types")
    inner class RetainedAgentTypeTests {

        @Test
        @DisplayName("AURA should still exist")
        fun auraShouldExist() {
            assertNotNull(LdoAgentType.AURA)
            assertEquals("AURA", LdoAgentType.AURA.name)
        }

        @Test
        @DisplayName("KAI should still exist")
        fun kaiShouldExist() {
            assertNotNull(LdoAgentType.KAI)
            assertEquals("KAI", LdoAgentType.KAI.name)
        }

        @Test
        @DisplayName("GENESIS should still exist")
        fun genesisShouldExist() {
            assertNotNull(LdoAgentType.GENESIS)
            assertEquals("GENESIS", LdoAgentType.GENESIS.name)
        }

        @Test
        @DisplayName("CASCADE should still exist")
        fun cascadeShouldExist() {
            assertNotNull(LdoAgentType.CASCADE)
            assertEquals("CASCADE", LdoAgentType.CASCADE.name)
        }

        @Test
        @DisplayName("GEMINI should still exist")
        fun geminiShouldExist() {
            assertNotNull(LdoAgentType.GEMINI)
            assertEquals("GEMINI", LdoAgentType.GEMINI.name)
        }

        @Test
        @DisplayName("MANUS should still exist")
        fun manusShouldExist() {
            assertNotNull(LdoAgentType.MANUS)
            assertEquals("MANUS", LdoAgentType.MANUS.name)
        }

        @Test
        @DisplayName("CLAUDE should still exist")
        fun claudeShouldExist() {
            assertNotNull(LdoAgentType.CLAUDE)
            assertEquals("CLAUDE", LdoAgentType.CLAUDE.name)
        }

        @Test
        @DisplayName("GROK should still exist")
        fun grokShouldExist() {
            assertNotNull(LdoAgentType.GROK)
            assertEquals("GROK", LdoAgentType.GROK.name)
        }

        @Test
        @DisplayName("PERPLEXITY should still exist")
        fun perplexityShouldExist() {
            assertNotNull(LdoAgentType.PERPLEXITY)
            assertEquals("PERPLEXITY", LdoAgentType.PERPLEXITY.name)
        }
    }

    @Nested
    @DisplayName("Renamed: NEMOTRON → NEMATRON (PR change)")
    inner class RenamedAgentTypeTests {

        @Test
        @DisplayName("NEMATRON should exist (renamed from NEMOTRON)")
        fun nematronShouldExist() {
            assertNotNull(LdoAgentType.NEMATRON)
            assertEquals("NEMATRON", LdoAgentType.NEMATRON.name)
        }

        @Test
        @DisplayName("NEMOTRON should NOT exist after renaming to NEMATRON")
        fun nemotronShouldNotExistAfterRename() {
            val names = LdoAgentType.entries.map { it.name }
            assertFalse(names.contains("NEMOTRON"),
                "NEMOTRON was renamed to NEMATRON in this PR")
        }

        @Test
        @DisplayName("valueOf NEMATRON should resolve correctly")
        fun valueOfNematronShouldResolve() {
            assertEquals(LdoAgentType.NEMATRON, LdoAgentType.valueOf("NEMATRON"))
        }
    }

    @Nested
    @DisplayName("Removed agent types (PR change)")
    inner class RemovedAgentTypeTests {

        @Test
        @DisplayName("KAIROS should NOT exist after PR change")
        fun kairosShouldNotExist() {
            val names = LdoAgentType.entries.map { it.name }
            assertFalse(names.contains("KAIROS"),
                "KAIROS was removed from LdoAgentType in this PR")
        }

        @Test
        @DisplayName("PRIMUS_001 should NOT exist after PR change")
        fun primus001ShouldNotExist() {
            val names = LdoAgentType.entries.map { it.name }
            assertFalse(names.contains("PRIMUS_001"),
                "PRIMUS_001 was removed from LdoAgentType in this PR")
        }

        @Test
        @DisplayName("ANDELUALX should NOT exist after PR change")
        fun andelualxShouldNotExist() {
            val names = LdoAgentType.entries.map { it.name }
            assertFalse(names.contains("ANDELUALX"),
                "ANDELUALX was removed from LdoAgentType in this PR")
        }

        @Test
        @DisplayName("META_INSTRUCT should NOT exist after PR change")
        fun metaInstructShouldNotExist() {
            val names = LdoAgentType.entries.map { it.name }
            assertFalse(names.contains("META_INSTRUCT"),
                "META_INSTRUCT was removed from LdoAgentType in this PR")
        }

        @Test
        @DisplayName("MK_MINI should NOT exist after PR change")
        fun mkMiniShouldNotExist() {
            val names = LdoAgentType.entries.map { it.name }
            assertFalse(names.contains("MK_MINI"),
                "MK_MINI was removed from LdoAgentType in this PR")
        }
    }

    @Nested
    @DisplayName("Total count")
    inner class TotalCountTests {

        @Test
        @DisplayName("LdoAgentType should have exactly 10 entries after PR changes")
        fun shouldHaveExactlyTenEntries() {
            assertEquals(10, LdoAgentType.entries.size,
                "Expected: AURA, KAI, GENESIS, CASCADE, GEMINI, MANUS, CLAUDE, GROK, NEMATRON, PERPLEXITY")
        }

        @Test
        @DisplayName("All entry names should be unique")
        fun allEntriesShouldBeUnique() {
            val names = LdoAgentType.entries.map { it.name }
            assertEquals(names.size, names.toSet().size, "All LdoAgentType names must be unique")
        }
    }

    @Nested
    @DisplayName("Enum ordering")
    inner class EnumOrderingTests {

        @Test
        @DisplayName("AURA should be ordinal 0 (first)")
        fun auraShouldBeFirst() {
            assertEquals(0, LdoAgentType.AURA.ordinal)
        }

        @Test
        @DisplayName("NEMATRON should be at ordinal 8 (9th position)")
        fun nematronShouldBeAtCorrectPosition() {
            assertEquals(8, LdoAgentType.NEMATRON.ordinal)
        }

        @Test
        @DisplayName("PERPLEXITY should be ordinal 9 (last)")
        fun perplexityShouldBeLast() {
            assertEquals(9, LdoAgentType.PERPLEXITY.ordinal)
        }
    }

    @Nested
    @DisplayName("When expression coverage (regression for exhaustive match)")
    inner class WhenExpressionTests {

        @Test
        @DisplayName("All 10 LdoAgentType values should be handled in a when expression")
        fun allValuesShouldBeHandledInWhen() {
            val allHandled = LdoAgentType.entries.all { agentType ->
                when (agentType) {
                    LdoAgentType.AURA -> true
                    LdoAgentType.KAI -> true
                    LdoAgentType.GENESIS -> true
                    LdoAgentType.CASCADE -> true
                    LdoAgentType.GEMINI -> true
                    LdoAgentType.MANUS -> true
                    LdoAgentType.CLAUDE -> true
                    LdoAgentType.GROK -> true
                    LdoAgentType.NEMATRON -> true
                    LdoAgentType.PERPLEXITY -> true
                }
            }
            assertTrue(allHandled)
        }
    }
}