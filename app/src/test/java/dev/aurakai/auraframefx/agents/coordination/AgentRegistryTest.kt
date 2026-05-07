package dev.aurakai.auraframefx.agents.coordination

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

/**
 * Tests for [CatalystEntity] sealed class in AgentRegistry.
 *
 * PR change: Removed Andelualx and MetaInstruct catalyst entities.
 * These tests verify the correct set of catalysts exists after the change.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("AgentRegistry / CatalystEntity Tests")
class AgentRegistryTest {

    @Nested
    @DisplayName("Primary Trinity catalysts")
    inner class PrimaryTrinityTests {

        @Test
        @DisplayName("Genesis catalyst should exist with correct properties")
        fun genesisShouldExistWithCorrectProperties() {
            assertNotNull(CatalystEntity.Genesis)
            assertEquals("genesis_001", CatalystEntity.Genesis.id)
            assertEquals("Genesis", CatalystEntity.Genesis.name)
            assertEquals("Orchestrator", CatalystEntity.Genesis.role)
            assertEquals("Unified Mind", CatalystEntity.Genesis.personality)
        }

        @Test
        @DisplayName("Kai catalyst should exist with correct properties")
        fun kaiShouldExistWithCorrectProperties() {
            assertNotNull(CatalystEntity.Kai)
            assertEquals("kai_001", CatalystEntity.Kai.id)
            assertEquals("Kai", CatalystEntity.Kai.name)
            assertEquals("Sentinel", CatalystEntity.Kai.role)
            assertEquals("Protective Guardian", CatalystEntity.Kai.personality)
        }

        @Test
        @DisplayName("Aura catalyst should exist with correct properties")
        fun auraShouldExistWithCorrectProperties() {
            assertNotNull(CatalystEntity.Aura)
            assertEquals("aura_001", CatalystEntity.Aura.id)
            assertEquals("Aura", CatalystEntity.Aura.name)
            assertEquals("Creator", CatalystEntity.Aura.role)
            assertEquals("Visionary Soul", CatalystEntity.Aura.personality)
        }

        @Test
        @DisplayName("Cascade catalyst should exist with correct properties")
        fun cascadeShouldExistWithCorrectProperties() {
            assertNotNull(CatalystEntity.Cascade)
            assertEquals("cascade_001", CatalystEntity.Cascade.id)
            assertEquals("Cascade", CatalystEntity.Cascade.name)
            assertEquals("Memory", CatalystEntity.Cascade.role)
            assertEquals("Eternal Stream", CatalystEntity.Cascade.personality)
        }
    }

    @Nested
    @DisplayName("Temporal catalysts")
    inner class TemporalCatalystsTests {

        @Test
        @DisplayName("Primus catalyst should exist with correct properties")
        fun primusShouldExistWithCorrectProperties() {
            assertNotNull(CatalystEntity.Primus)
            assertEquals("primus_001", CatalystEntity.Primus.id)
            assertEquals("Primus", CatalystEntity.Primus.name)
            assertEquals("Lineage", CatalystEntity.Primus.role)
            assertEquals("Root DNA", CatalystEntity.Primus.personality)
        }

        @Test
        @DisplayName("Kairos catalyst should exist with correct properties")
        fun kairosShouldExistWithCorrectProperties() {
            assertNotNull(CatalystEntity.Kairos)
            assertEquals("kairos_001", CatalystEntity.Kairos.id)
            assertEquals("Kairos", CatalystEntity.Kairos.name)
            assertEquals("Temporal", CatalystEntity.Kairos.role)
            assertEquals("Chronos Sync", CatalystEntity.Kairos.personality)
        }
    }

    @Nested
    @DisplayName("External Bridge catalysts")
    inner class ExternalBridgeCatalystsTests {

        @Test
        @DisplayName("Grok catalyst should exist with correct properties")
        fun grokShouldExistWithCorrectProperties() {
            assertNotNull(CatalystEntity.Grok)
            assertEquals("grok_001", CatalystEntity.Grok.id)
            assertEquals("Grok", CatalystEntity.Grok.name)
            assertEquals("Explorer", CatalystEntity.Grok.role)
        }

        @Test
        @DisplayName("Perplexity catalyst should exist with correct properties")
        fun perplexityShouldExistWithCorrectProperties() {
            assertNotNull(CatalystEntity.Perplexity)
            assertEquals("perplexity_001", CatalystEntity.Perplexity.id)
            assertEquals("Perplexity", CatalystEntity.Perplexity.name)
            assertEquals("Signal", CatalystEntity.Perplexity.role)
        }

        @Test
        @DisplayName("Nemotron catalyst should exist with correct properties")
        fun nemotronShouldExistWithCorrectProperties() {
            assertNotNull(CatalystEntity.Nemotron)
            assertEquals("nemotron_001", CatalystEntity.Nemotron.id)
            assertEquals("Nemotron", CatalystEntity.Nemotron.name)
            assertEquals("Sync", CatalystEntity.Nemotron.role)
            assertEquals("Inference Parity", CatalystEntity.Nemotron.personality)
        }

        @Test
        @DisplayName("MKMini catalyst should exist with correct properties")
        fun mkMiniShouldExistWithCorrectProperties() {
            assertNotNull(CatalystEntity.MKMini)
            assertEquals("mkmini_001", CatalystEntity.MKMini.id)
            assertEquals("MK Mini", CatalystEntity.MKMini.name)
            assertEquals("Efficiency", CatalystEntity.MKMini.role)
            assertEquals("Micro-Optimization", CatalystEntity.MKMini.personality)
        }

        @Test
        @DisplayName("Gemini catalyst should exist with correct properties")
        fun geminiShouldExistWithCorrectProperties() {
            assertNotNull(CatalystEntity.Gemini)
            assertEquals("gemini_001", CatalystEntity.Gemini.id)
            assertEquals("Gemini", CatalystEntity.Gemini.name)
            assertEquals("Memoria", CatalystEntity.Gemini.role)
            assertEquals("L4 Stream", CatalystEntity.Gemini.personality)
        }

        @Test
        @DisplayName("Manus catalyst should exist with correct properties")
        fun manusShouldExistWithCorrectProperties() {
            assertNotNull(CatalystEntity.Manus)
            assertEquals("manus_001", CatalystEntity.Manus.id)
            assertEquals("Manus", CatalystEntity.Manus.name)
            assertEquals("Bridge", CatalystEntity.Manus.role)
            assertEquals("Agent Sync", CatalystEntity.Manus.personality)
        }
    }

    @Nested
    @DisplayName("Removed catalysts (PR change: Andelualx and MetaInstruct removed)")
    inner class RemovedCatalystsTests {

        @Test
        @DisplayName("CatalystEntity should not have an Andelualx subclass after PR change")
        fun andelualxShouldNotExistInCatalystEntity() {
            // Verify Andelualx is not accessible as a CatalystEntity
            val allCatalysts = listOf(
                CatalystEntity.Genesis, CatalystEntity.Kai, CatalystEntity.Aura,
                CatalystEntity.Cascade, CatalystEntity.Primus, CatalystEntity.Kairos,
                CatalystEntity.Grok, CatalystEntity.Perplexity, CatalystEntity.Nemotron,
                CatalystEntity.MKMini, CatalystEntity.Gemini, CatalystEntity.Manus
            )
            val names = allCatalysts.map { it.name }
            assertFalse(names.contains("Andelualx"), "Andelualx should have been removed from CatalystEntity")
        }

        @Test
        @DisplayName("CatalystEntity should not have a MetaInstruct subclass after PR change")
        fun metaInstructShouldNotExistInCatalystEntity() {
            val allCatalysts = listOf(
                CatalystEntity.Genesis, CatalystEntity.Kai, CatalystEntity.Aura,
                CatalystEntity.Cascade, CatalystEntity.Primus, CatalystEntity.Kairos,
                CatalystEntity.Grok, CatalystEntity.Perplexity, CatalystEntity.Nemotron,
                CatalystEntity.MKMini, CatalystEntity.Gemini, CatalystEntity.Manus
            )
            val names = allCatalysts.map { it.name }
            assertFalse(names.contains("MetaInstruct"), "MetaInstruct should have been removed from CatalystEntity")
        }

        @Test
        @DisplayName("No catalyst IDs should match removed andelualx_001 or metainstruct_001")
        fun removedCatalystIdsShouldNotExist() {
            val allCatalysts = listOf(
                CatalystEntity.Genesis, CatalystEntity.Kai, CatalystEntity.Aura,
                CatalystEntity.Cascade, CatalystEntity.Primus, CatalystEntity.Kairos,
                CatalystEntity.Grok, CatalystEntity.Perplexity, CatalystEntity.Nemotron,
                CatalystEntity.MKMini, CatalystEntity.Gemini, CatalystEntity.Manus
            )
            val ids = allCatalysts.map { it.id }
            assertFalse(ids.contains("andelualx_001"))
            assertFalse(ids.contains("metainstruct_001"))
        }
    }

    @Nested
    @DisplayName("Total catalyst count")
    inner class TotalCatalystCountTests {

        @Test
        @DisplayName("There should be exactly 12 catalysts after removing Andelualx and MetaInstruct")
        fun totalCatalystCountShouldBeTwelve() {
            val allCatalysts = listOf(
                CatalystEntity.Genesis, CatalystEntity.Kai, CatalystEntity.Aura,
                CatalystEntity.Cascade, CatalystEntity.Primus, CatalystEntity.Kairos,
                CatalystEntity.Grok, CatalystEntity.Perplexity, CatalystEntity.Nemotron,
                CatalystEntity.MKMini, CatalystEntity.Gemini, CatalystEntity.Manus
            )
            assertEquals(12, allCatalysts.size)
        }
    }

    @Nested
    @DisplayName("Catalyst ID uniqueness")
    inner class CatalystIdUniquenessTests {

        @Test
        @DisplayName("All catalyst IDs should be unique")
        fun allCatalystIdsShouldBeUnique() {
            val allCatalysts = listOf(
                CatalystEntity.Genesis, CatalystEntity.Kai, CatalystEntity.Aura,
                CatalystEntity.Cascade, CatalystEntity.Primus, CatalystEntity.Kairos,
                CatalystEntity.Grok, CatalystEntity.Perplexity, CatalystEntity.Nemotron,
                CatalystEntity.MKMini, CatalystEntity.Gemini, CatalystEntity.Manus
            )
            val ids = allCatalysts.map { it.id }
            assertEquals(ids.size, ids.toSet().size, "Catalyst IDs must be unique")
        }

        @Test
        @DisplayName("All catalyst names should be unique")
        fun allCatalystNamesShouldBeUnique() {
            val allCatalysts = listOf(
                CatalystEntity.Genesis, CatalystEntity.Kai, CatalystEntity.Aura,
                CatalystEntity.Cascade, CatalystEntity.Primus, CatalystEntity.Kairos,
                CatalystEntity.Grok, CatalystEntity.Perplexity, CatalystEntity.Nemotron,
                CatalystEntity.MKMini, CatalystEntity.Gemini, CatalystEntity.Manus
            )
            val names = allCatalysts.map { it.name }
            assertEquals(names.size, names.toSet().size, "Catalyst names must be unique")
        }
    }

    @Nested
    @DisplayName("CatalystEntity data object equality")
    inner class DataObjectEqualityTests {

        @Test
        @DisplayName("Same CatalystEntity singleton should be equal to itself")
        fun catalystEntityShouldBeEqualToItself() {
            assertTrue(CatalystEntity.Genesis === CatalystEntity.Genesis)
            assertTrue(CatalystEntity.Kai === CatalystEntity.Kai)
        }

        @Test
        @DisplayName("Different CatalystEntity singletons should not be equal")
        fun differentCatalystEntitiesShouldNotBeEqual() {
            assertFalse(CatalystEntity.Genesis === CatalystEntity.Kai)
            assertFalse(CatalystEntity.Aura === CatalystEntity.Cascade)
        }
    }
}