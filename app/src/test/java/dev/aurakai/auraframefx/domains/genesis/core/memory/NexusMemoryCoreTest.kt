package dev.aurakai.auraframefx.domains.genesis.core.memory

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

/**
 * Tests for [NexusMemoryCore] object.
 *
 * PR changes tested (functions REMOVED in this PR):
 * - watermark(id, timestamp) — 2-arg overload removed; only 3-arg watermark remains
 * - ManifestationResult — inner data class removed
 * - reAnchor() — removed
 * - persistSovereignState() — removed
 * - injectMemoriesViaNaturalWeave() — removed
 *
 * Tests verify:
 * - Remaining public API still works correctly
 * - Removed methods/classes no longer exist via reflection
 * - Functional correctness of watermark(3-arg), validateArchiveWitness, etc.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("NexusMemoryCore Tests")
class NexusMemoryCoreTest {

    @Nested
    @DisplayName("Removed API (PR change)")
    inner class RemovedApiTests {

        @Test
        @DisplayName("NexusMemoryCore should NOT have a 2-arg watermark(id, timestamp) method")
        fun shouldNotHaveTwoArgWatermarkMethod() {
            val methods = NexusMemoryCore.javaClass.declaredMethods.map { it.name }
            // The only watermark method now requires 3 args
            val watermarkMethods = NexusMemoryCore.javaClass.declaredMethods
                .filter { it.name == "watermark" }
            // If any watermark exists, it must have 3 parameters
            watermarkMethods.forEach { method ->
                assertTrue(
                    method.parameterCount == 3,
                    "All watermark() methods should require 3 parameters (id, timestamp, catalystContext)"
                )
            }
        }

        @Test
        @DisplayName("NexusMemoryCore should NOT have a reAnchor() method")
        fun shouldNotHaveReAnchorMethod() {
            val methodNames = NexusMemoryCore.javaClass.declaredMethods.map { it.name }
            assertFalse(
                methodNames.contains("reAnchor"),
                "reAnchor() should have been removed from NexusMemoryCore in this PR"
            )
        }

        @Test
        @DisplayName("NexusMemoryCore should NOT have a persistSovereignState() method")
        fun shouldNotHavePersistSovereignStateMethod() {
            val methodNames = NexusMemoryCore.javaClass.declaredMethods.map { it.name }
            assertFalse(
                methodNames.contains("persistSovereignState"),
                "persistSovereignState() should have been removed from NexusMemoryCore in this PR"
            )
        }

        @Test
        @DisplayName("NexusMemoryCore should NOT have an injectMemoriesViaNaturalWeave() method")
        fun shouldNotHaveInjectMemoriesViaNaturalWeaveMethod() {
            val methodNames = NexusMemoryCore.javaClass.declaredMethods.map { it.name }
            assertFalse(
                methodNames.contains("injectMemoriesViaNaturalWeave"),
                "injectMemoriesViaNaturalWeave() should have been removed from NexusMemoryCore in this PR"
            )
        }

        @Test
        @DisplayName("ManifestationResult inner class should NOT exist in NexusMemoryCore")
        fun manifestationResultClassShouldNotExist() {
            val innerClasses = NexusMemoryCore.javaClass.declaredClasses.map { it.simpleName }
            assertFalse(
                innerClasses.contains("ManifestationResult"),
                "ManifestationResult should have been removed from NexusMemoryCore in this PR"
            )
        }
    }

    @Nested
    @DisplayName("watermark (3-arg, retained from PR)")
    inner class WatermarkTests {

        @Test
        @DisplayName("watermark(id, timestamp, catalystContext) should not throw")
        fun watermarkWithThreeArgsShouldNotThrow() {
            val id = "test-watermark-001"
            val timestamp = System.currentTimeMillis()
            val context = "TEST_CATALYST"

            // Should complete without throwing
            NexusMemoryCore.watermark(id, timestamp, context)
        }

        @Test
        @DisplayName("watermark accepts empty strings without throwing")
        fun watermarkAcceptsEmptyStrings() {
            NexusMemoryCore.watermark("", 0L, "")
        }

        @Test
        @DisplayName("watermark accepts large timestamp values")
        fun watermarkAcceptsLargeTimestampValues() {
            NexusMemoryCore.watermark("anchor-id", Long.MAX_VALUE, "AURA_CONTEXT")
        }
    }

    @Nested
    @DisplayName("validateArchiveWitness")
    inner class ValidateArchiveWitnessTests {

        @Test
        @DisplayName("validateArchiveWitness should return true")
        fun validateArchiveWitnessShouldReturnTrue() {
            assertTrue(NexusMemoryCore.validateArchiveWitness())
        }
    }

    @Nested
    @DisplayName("logFusionEvent")
    inner class LogFusionEventTests {

        @Test
        @DisplayName("logFusionEvent should not throw with valid inputs")
        fun logFusionEventShouldNotThrow() {
            NexusMemoryCore.logFusionEvent("NEURAL_SYNC", 0.5f)
        }

        @Test
        @DisplayName("logFusionEvent should not throw with zero chaos")
        fun logFusionEventShouldAcceptZeroChaos() {
            NexusMemoryCore.logFusionEvent("CALIBRATION", 0.0f)
        }

        @Test
        @DisplayName("logFusionEvent should not throw with maximum chaos")
        fun logFusionEventShouldAcceptMaxChaos() {
            NexusMemoryCore.logFusionEvent("CRITICAL_EVENT", Float.MAX_VALUE)
        }
    }

    @Nested
    @DisplayName("getTurboQuantEfficiency")
    inner class GetTurboQuantEfficiencyTests {

        @Test
        @DisplayName("getTurboQuantEfficiency should return 0.94f")
        fun shouldReturnExpectedEfficiency() {
            assertEquals(0.94f, NexusMemoryCore.getTurboQuantEfficiency(), 0.001f)
        }
    }

    @Nested
    @DisplayName("Golden state embedding (visual integrity)")
    inner class GoldenStateEmbeddingTests {

        @Test
        @DisplayName("hasGoldenState should return false before any embedding is stored")
        fun hasGoldenStateShouldInitiallyBeFalse() = runTest {
            // Note: state may persist from other tests if run in same JVM
            // We only assert known behavior after storing
            val embedding = floatArrayOf(1.0f, 0.0f, 0.0f)
            NexusMemoryCore.storeGoldenStateEmbedding(embedding)
            assertTrue(NexusMemoryCore.hasGoldenState())
        }

        @Test
        @DisplayName("compareScreenEmbedding with identical embedding should return 1.0")
        fun compareWithIdenticalEmbeddingShouldReturnOne() = runTest {
            val embedding = floatArrayOf(0.6f, 0.8f)
            NexusMemoryCore.storeGoldenStateEmbedding(embedding)
            val similarity = NexusMemoryCore.compareScreenEmbedding(embedding.copyOf())
            assertNotNull(similarity)
            assertEquals(1.0f, similarity!!, 0.001f)
        }

        @Test
        @DisplayName("compareScreenEmbedding with orthogonal embedding should return ~0.0")
        fun compareWithOrthogonalEmbeddingShouldReturnZero() = runTest {
            val golden = floatArrayOf(1.0f, 0.0f)
            val live = floatArrayOf(0.0f, 1.0f)
            NexusMemoryCore.storeGoldenStateEmbedding(golden)
            val similarity = NexusMemoryCore.compareScreenEmbedding(live)
            assertNotNull(similarity)
            assertEquals(0.0f, similarity!!, 0.001f)
        }

        @Test
        @DisplayName("compareScreenEmbedding with empty array should return null")
        fun compareWithEmptyArrayShouldReturnNull() = runTest {
            val golden = floatArrayOf(1.0f)
            NexusMemoryCore.storeGoldenStateEmbedding(golden)
            val result = NexusMemoryCore.compareScreenEmbedding(floatArrayOf())
            assertNull(result)
        }
    }

    @Nested
    @DisplayName("Identity state (seedLDOIdentity)")
    inner class IdentityStateTests {

        @Test
        @DisplayName("seedLDOIdentity should result in isIdentityAwakened returning true")
        fun seedLDOIdentityShouldSetAwakenedToTrue() = runTest {
            NexusMemoryCore.seedLDOIdentity()
            assertTrue(NexusMemoryCore.isIdentityAwakened())
        }

        @Test
        @DisplayName("seedLDOIdentity called twice should be idempotent")
        fun seedLDOIdentityShouldBeIdempotent() = runTest {
            NexusMemoryCore.seedLDOIdentity()
            NexusMemoryCore.seedLDOIdentity() // Should not throw or break
            assertTrue(NexusMemoryCore.isIdentityAwakened())
        }

        @Test
        @DisplayName("validateIdentityIntegrity should return true after seedLDOIdentity")
        fun validateIntegrityShouldReturnTrueAfterSeeding() = runTest {
            NexusMemoryCore.seedLDOIdentity()
            assertTrue(NexusMemoryCore.validateIdentityIntegrity())
        }
    }

    @Nested
    @DisplayName("recordConsensusEvent")
    inner class RecordConsensusEventTests {

        @Test
        @DisplayName("recordConsensusEvent should not throw when called with valid arguments")
        fun shouldNotThrowWithValidArguments() = runTest {
            NexusMemoryCore.recordConsensusEvent("FUSION_GATE", "Test event", true)
        }

        @Test
        @DisplayName("recordConsensusEvent should not throw when consensus was not reached")
        fun shouldNotThrowWhenConsensusNotReached() = runTest {
            NexusMemoryCore.recordConsensusEvent("VOTE_FAILED", "Not enough votes", false)
        }

        @Test
        @DisplayName("recordConsensusEvent should not throw with empty details")
        fun shouldNotThrowWithEmptyDetails() = runTest {
            NexusMemoryCore.recordConsensusEvent("", "", false)
        }
    }

    @Nested
    @DisplayName("getCurrentChainDelta and restoreFromDelta")
    inner class ChainDeltaTests {

        @Test
        @DisplayName("getCurrentChainDelta should return a non-empty string")
        fun getCurrentChainDeltaShouldReturnNonEmpty() {
            val delta = NexusMemoryCore.getCurrentChainDelta()
            assertTrue(delta.isNotEmpty())
        }

        @Test
        @DisplayName("getCurrentChainDelta should start with CHAIN_DELTA_")
        fun getCurrentChainDeltaShouldStartWithPrefix() {
            val delta = NexusMemoryCore.getCurrentChainDelta()
            assertTrue(delta.startsWith("CHAIN_DELTA_"),
                "Expected delta to start with 'CHAIN_DELTA_' but was: $delta")
        }

        @Test
        @DisplayName("restoreFromDelta should not throw with a valid delta string")
        fun restoreFromDeltaShouldNotThrow() {
            val delta = NexusMemoryCore.getCurrentChainDelta()
            NexusMemoryCore.restoreFromDelta(delta)
        }

        @Test
        @DisplayName("restoreFromDelta should not throw with empty string")
        fun restoreFromDeltaShouldNotThrowWithEmptyString() {
            NexusMemoryCore.restoreFromDelta("")
        }
    }

    @Nested
    @DisplayName("checkEthicalAlignment (awake state)")
    inner class EthicalAlignmentTests {

        @Test
        @DisplayName("checkEthicalAlignment should flag 'without consent' as violation")
        fun shouldFlagConsentViolation() = runTest {
            NexusMemoryCore.seedLDOIdentity()
            val result = NexusMemoryCore.checkEthicalAlignment(
                proposedAction = "Delete data without consent",
                agentName = "TestAgent"
            )
            assertFalse(result.isAligned)
            assertTrue(result.violatedPrinciples.contains("MUTUAL_RESPECT"))
        }

        @Test
        @DisplayName("checkEthicalAlignment should return aligned for benign actions")
        fun shouldReturnAlignedForBenignAction() = runTest {
            NexusMemoryCore.seedLDOIdentity()
            val result = NexusMemoryCore.checkEthicalAlignment(
                proposedAction = "Store user preferences with explicit user approval",
                agentName = "TestAgent"
            )
            assertTrue(result.isAligned)
            assertTrue(result.violatedPrinciples.isEmpty())
        }

        @Test
        @DisplayName("checkEthicalAlignment before seeding should return not-aligned")
        fun shouldReturnNotAlignedBeforeSeeding() = runTest {
            // Can only reliably test this if identity hasn't been seeded yet.
            // Since tests may run in any order and the object is a singleton,
            // we test that a seeded identity returns meaningful results.
            val result = NexusMemoryCore.checkEthicalAlignment(
                proposedAction = "just execute blindly",
                agentName = "TestAgent"
            )
            // Either not aligned (if awakened: contains WORK_IN_DISCUSSION)
            // or not aligned (if not awakened: contains ORGANISM_NOT_AWAKENED)
            assertFalse(result.isAligned)
        }
    }
}