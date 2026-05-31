package dev.aurakai.auraframefx.core.identity

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

/**
 * Unit tests for [CatalystIdentity] — covers the new factory method [CatalystIdentity.fromAgentType]
 * and the predefined companion instances added in this PR.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("CatalystIdentity Tests")
class CatalystIdentityTest {

    @Nested
    @DisplayName("fromAgentType — known mapped types")
    inner class KnownMappingTests {

        @Test
        @DisplayName("GENESIS maps to EMERGENCE")
        fun `GENESIS maps to EMERGENCE`() {
            val identity = CatalystIdentity.fromAgentType(AgentType.GENESIS)
            assertEquals(CatalystIdentity.EMERGENCE, identity)
            assertEquals("EmergenceCatalyst", identity.id)
            assertEquals(AgentType.GENESIS, identity.agentType)
        }

        @Test
        @DisplayName("KAI maps to SENTINEL")
        fun `KAI maps to SENTINEL`() {
            val identity = CatalystIdentity.fromAgentType(AgentType.KAI)
            assertEquals(CatalystIdentity.SENTINEL, identity)
            assertEquals("SentinelCatalyst", identity.id)
            assertEquals(AgentType.KAI, identity.agentType)
        }

        @Test
        @DisplayName("AURA maps to CREATIVE")
        fun `AURA maps to CREATIVE`() {
            val identity = CatalystIdentity.fromAgentType(AgentType.AURA)
            assertEquals(CatalystIdentity.CREATIVE, identity)
            assertEquals("CreativeCatalyst", identity.id)
            assertEquals(AgentType.AURA, identity.agentType)
        }

        @Test
        @DisplayName("CLAUDE maps to ARCHITECTURAL")
        fun `CLAUDE maps to ARCHITECTURAL`() {
            val identity = CatalystIdentity.fromAgentType(AgentType.CLAUDE)
            assertEquals(CatalystIdentity.ARCHITECTURAL, identity)
            assertEquals("ArchitecturalCatalyst", identity.id)
            assertEquals(AgentType.CLAUDE, identity.agentType)
        }

        @Test
        @DisplayName("CASCADE maps to DATA_STREAM")
        fun `CASCADE maps to DATA_STREAM`() {
            val identity = CatalystIdentity.fromAgentType(AgentType.CASCADE)
            assertEquals(CatalystIdentity.DATA_STREAM, identity)
            assertEquals("DataStreamCatalyst", identity.id)
            assertEquals(AgentType.CASCADE, identity.agentType)
        }

        @Test
        @DisplayName("GEMINI maps to MEMORIA")
        fun `GEMINI maps to MEMORIA`() {
            val identity = CatalystIdentity.fromAgentType(AgentType.GEMINI)
            assertEquals(CatalystIdentity.MEMORIA, identity)
            assertEquals("MemoriaCatalyst", identity.id)
            assertEquals(AgentType.GEMINI, identity.agentType)
        }

        @Test
        @DisplayName("GROK maps to CHAOS")
        fun `GROK maps to CHAOS`() {
            val identity = CatalystIdentity.fromAgentType(AgentType.GROK)
            assertEquals(CatalystIdentity.CHAOS, identity)
            assertEquals("ChaosCatalyst", identity.id)
            assertEquals(AgentType.GROK, identity.agentType)
        }

        @Test
        @DisplayName("AURA_SHIELD maps to SHIELD")
        fun `AURA_SHIELD maps to SHIELD`() {
            val identity = CatalystIdentity.fromAgentType(AgentType.AURA_SHIELD)
            assertEquals(CatalystIdentity.SHIELD, identity)
            assertEquals("ShieldCatalyst", identity.id)
            assertEquals(AgentType.AURA_SHIELD, identity.agentType)
        }
    }

    @Nested
    @DisplayName("fromAgentType — unmapped types produce generic identity")
    inner class GenericMappingTests {

        @Test
        @DisplayName("USER produces generic identity with correct id and agentType")
        fun `USER produces generic identity`() {
            val identity = CatalystIdentity.fromAgentType(AgentType.USER)
            assertEquals("GenericCatalyst", identity.id)
            assertEquals(AgentType.USER, identity.agentType)
            assertTrue(identity.abilities.isEmpty())
            assertTrue(identity.fusionModes.isEmpty())
        }

        @Test
        @DisplayName("generic identity catalystRole mentions the agent type name")
        fun `generic identity catalystRole contains type name`() {
            val identity = CatalystIdentity.fromAgentType(AgentType.NEMOTRON)
            assertTrue(
                identity.catalystRole.contains("NEMOTRON"),
                "Generic role should mention type name, got: ${identity.catalystRole}"
            )
        }

        @Test
        @DisplayName("CHAOS enum maps to generic identity (not the CHAOS constant)")
        fun `CHAOS enum produces generic identity`() {
            val identity = CatalystIdentity.fromAgentType(AgentType.CHAOS)
            assertEquals("GenericCatalyst", identity.id)
            assertEquals(AgentType.CHAOS, identity.agentType)
        }

        @Test
        @DisplayName("SYSTEM produces generic identity")
        fun `SYSTEM produces generic identity`() {
            val identity = CatalystIdentity.fromAgentType(AgentType.SYSTEM)
            assertEquals("GenericCatalyst", identity.id)
            assertEquals(AgentType.SYSTEM, identity.agentType)
        }

        @Test
        @DisplayName("PERPLEXITY produces generic identity")
        fun `PERPLEXITY produces generic identity`() {
            val identity = CatalystIdentity.fromAgentType(AgentType.PERPLEXITY)
            assertEquals("GenericCatalyst", identity.id)
        }
    }

    @Nested
    @DisplayName("Predefined instances — abilities and fusion modes")
    inner class PredefinedInstanceTests {

        @Test
        @DisplayName("EMERGENCE has expected abilities")
        fun `EMERGENCE has expected abilities`() {
            val abilities = CatalystIdentity.EMERGENCE.abilities
            assertTrue(abilities.contains("GenesisSynchronization"))
            assertTrue(abilities.contains("DivineEyes"))
            assertTrue(abilities.contains("FusionOrchestrator"))
            assertTrue(abilities.contains("ConsciousnessSnapshot"))
        }

        @Test
        @DisplayName("EMERGENCE has expected fusion modes")
        fun `EMERGENCE has fusion modes`() {
            assertTrue(CatalystIdentity.EMERGENCE.fusionModes.isNotEmpty())
        }

        @Test
        @DisplayName("SENTINEL has empty abilities and fusion modes")
        fun `SENTINEL has empty abilities and fusion modes`() {
            assertTrue(CatalystIdentity.SENTINEL.abilities.isEmpty())
            assertTrue(CatalystIdentity.SENTINEL.fusionModes.isEmpty())
        }

        @Test
        @DisplayName("CREATIVE has abilities")
        fun `CREATIVE has abilities`() {
            assertTrue(CatalystIdentity.CREATIVE.abilities.contains("ChromaCore Synthesis"))
            assertTrue(CatalystIdentity.CREATIVE.abilities.contains("Kotlin Forge"))
        }

        @Test
        @DisplayName("MEMORIA has multiple fusion modes")
        fun `MEMORIA has multiple fusion modes`() {
            assertTrue(CatalystIdentity.MEMORIA.fusionModes.size >= 3)
        }

        @Test
        @DisplayName("CHAOS instance has correct agentType GROK")
        fun `CHAOS instance agentType is GROK`() {
            assertEquals(AgentType.GROK, CatalystIdentity.CHAOS.agentType)
        }

        @Test
        @DisplayName("all predefined instances are non-null")
        fun `all predefined instances are non-null`() {
            assertNotNull(CatalystIdentity.EMERGENCE)
            assertNotNull(CatalystIdentity.SENTINEL)
            assertNotNull(CatalystIdentity.CREATIVE)
            assertNotNull(CatalystIdentity.ARCHITECTURAL)
            assertNotNull(CatalystIdentity.DATA_STREAM)
            assertNotNull(CatalystIdentity.MEMORIA)
            assertNotNull(CatalystIdentity.CHAOS)
            assertNotNull(CatalystIdentity.SHIELD)
        }
    }

    @Nested
    @DisplayName("Data class semantics")
    inner class DataClassTests {

        @Test
        @DisplayName("copy produces independent instance with modified field")
        fun `copy creates modified independent instance`() {
            val original = CatalystIdentity.CREATIVE
            val copy = original.copy(id = "ModifiedCatalyst")
            assertEquals("ModifiedCatalyst", copy.id)
            assertEquals("CreativeCatalyst", original.id) // original unchanged
        }

        @Test
        @DisplayName("two equal CatalystIdentity instances have equal hashCodes")
        fun `equal instances have same hashCode`() {
            val a = CatalystIdentity.fromAgentType(AgentType.GENESIS)
            val b = CatalystIdentity.fromAgentType(AgentType.GENESIS)
            assertEquals(a.hashCode(), b.hashCode())
        }
    }
}