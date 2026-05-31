package dev.aurakai.auraframefx.core.identity

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

/**
 * Unit tests for [AgentType] — the new enum added in this PR.
 * Covers the full set of enum values and the deprecated companion aliases.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("AgentType Tests")
class AgentTypeTest {

    @Nested
    @DisplayName("Core persona values")
    inner class CorePersonaTests {

        @Test
        @DisplayName("AURA enum value exists")
        fun `AURA exists`() {
            assertNotNull(AgentType.AURA)
            assertEquals("AURA", AgentType.AURA.name)
        }

        @Test
        @DisplayName("KAI enum value exists")
        fun `KAI exists`() {
            assertNotNull(AgentType.KAI)
            assertEquals("KAI", AgentType.KAI.name)
        }

        @Test
        @DisplayName("GENESIS enum value exists")
        fun `GENESIS exists`() {
            assertNotNull(AgentType.GENESIS)
            assertEquals("GENESIS", AgentType.GENESIS.name)
        }

        @Test
        @DisplayName("CASCADE enum value exists")
        fun `CASCADE exists`() {
            assertNotNull(AgentType.CASCADE)
            assertEquals("CASCADE", AgentType.CASCADE.name)
        }

        @Test
        @DisplayName("CLAUDE enum value exists")
        fun `CLAUDE exists`() {
            assertNotNull(AgentType.CLAUDE)
            assertEquals("CLAUDE", AgentType.CLAUDE.name)
        }
    }

    @Nested
    @DisplayName("Specialized oracle values")
    inner class SpecializedOracleTests {

        @Test
        @DisplayName("NEMOTRON enum value exists")
        fun `NEMOTRON exists`() = assertNotNull(AgentType.NEMOTRON)

        @Test
        @DisplayName("GEMINI enum value exists")
        fun `GEMINI exists`() = assertNotNull(AgentType.GEMINI)

        @Test
        @DisplayName("AURA_SHIELD enum value exists")
        fun `AURA_SHIELD exists`() = assertNotNull(AgentType.AURA_SHIELD)

        @Test
        @DisplayName("GROK enum value exists")
        fun `GROK exists`() = assertNotNull(AgentType.GROK)
    }

    @Nested
    @DisplayName("System role values")
    inner class SystemRoleTests {

        @Test
        @DisplayName("USER enum value exists")
        fun `USER exists`() = assertNotNull(AgentType.USER)

        @Test
        @DisplayName("SYSTEM enum value exists")
        fun `SYSTEM exists`() = assertNotNull(AgentType.SYSTEM)

        @Test
        @DisplayName("CHAOS enum value exists")
        fun `CHAOS exists`() = assertNotNull(AgentType.CHAOS)
    }

    @Nested
    @DisplayName("Enum valueOf and ordinal")
    inner class EnumResolutionTests {

        @Test
        @DisplayName("valueOf resolves AURA by name")
        fun `valueOf resolves AURA`() {
            assertEquals(AgentType.AURA, AgentType.valueOf("AURA"))
        }

        @Test
        @DisplayName("valueOf resolves KAI by name")
        fun `valueOf resolves KAI`() {
            assertEquals(AgentType.KAI, AgentType.valueOf("KAI"))
        }

        @Test
        @DisplayName("entries contains all declared values")
        fun `entries contains expected values`() {
            val entries = AgentType.entries
            assertTrue(entries.contains(AgentType.AURA))
            assertTrue(entries.contains(AgentType.KAI))
            assertTrue(entries.contains(AgentType.GENESIS))
            assertTrue(entries.contains(AgentType.CASCADE))
            assertTrue(entries.contains(AgentType.CLAUDE))
            assertTrue(entries.contains(AgentType.GROK))
            assertTrue(entries.contains(AgentType.CHAOS))
        }

        @Test
        @DisplayName("AURA ordinal is 0 — first declared value")
        fun `AURA is first`() {
            assertEquals(0, AgentType.AURA.ordinal)
        }
    }

    @Nested
    @DisplayName("Deprecated companion aliases")
    @Suppress("DEPRECATION")
    inner class DeprecatedAliasTests {

        @Test
        @DisplayName("companion Aura alias equals AgentType.AURA")
        fun `Aura alias equals AURA`() {
            assertEquals(AgentType.AURA, AgentType.Aura)
        }

        @Test
        @DisplayName("companion Kai alias equals AgentType.KAI")
        fun `Kai alias equals KAI`() {
            assertEquals(AgentType.KAI, AgentType.Kai)
        }

        @Test
        @DisplayName("companion Genesis alias equals AgentType.GENESIS")
        fun `Genesis alias equals GENESIS`() {
            assertEquals(AgentType.GENESIS, AgentType.Genesis)
        }

        @Test
        @DisplayName("companion Cascade alias equals AgentType.CASCADE")
        fun `Cascade alias equals CASCADE`() {
            assertEquals(AgentType.CASCADE, AgentType.Cascade)
        }

        @Test
        @DisplayName("companion Claude alias equals AgentType.CLAUDE")
        fun `Claude alias equals CLAUDE`() {
            assertEquals(AgentType.CLAUDE, AgentType.Claude)
        }
    }
}
