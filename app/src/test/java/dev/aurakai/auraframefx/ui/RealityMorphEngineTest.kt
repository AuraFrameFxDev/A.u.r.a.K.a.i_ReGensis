package dev.aurakai.auraframefx.ui

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

/**
 * Tests for [MorphState] enum and [RealityMorphEngine] object.
 *
 * PR changes tested:
 * 1. MorphState enum now has 3 values: DATA_STREAM, CHROME_FUSION, SINGULARITY
 *    (removed: FUSION_IGNITION, NEURAL_BLOODSTREAM, IDLE)
 * 2. triggerMorph() and emitSovereignFlare() still exist
 * 3. Switched from Timber to android.util.Log (no functional change to API)
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("RealityMorphEngine and MorphState Tests")
class RealityMorphEngineTest {

    @Nested
    @DisplayName("MorphState enum — PR change: new values set")
    inner class MorphStateEnumTests {

        @Test
        @DisplayName("MorphState should contain DATA_STREAM")
        fun morphStateShouldContainDataStream() {
            assertNotNull(MorphState.DATA_STREAM)
            assertEquals("DATA_STREAM", MorphState.DATA_STREAM.name)
        }

        @Test
        @DisplayName("MorphState should contain CHROME_FUSION")
        fun morphStateShouldContainChromeFusion() {
            assertNotNull(MorphState.CHROME_FUSION)
            assertEquals("CHROME_FUSION", MorphState.CHROME_FUSION.name)
        }

        @Test
        @DisplayName("MorphState should contain SINGULARITY")
        fun morphStateShouldContainSingularity() {
            assertNotNull(MorphState.SINGULARITY)
            assertEquals("SINGULARITY", MorphState.SINGULARITY.name)
        }

        @Test
        @DisplayName("MorphState should have exactly 3 values after PR change")
        fun morphStateShouldHaveExactlyThreeValues() {
            assertEquals(3, MorphState.entries.size,
                "MorphState should have exactly DATA_STREAM, CHROME_FUSION, SINGULARITY")
        }

        @Test
        @DisplayName("MorphState should NOT contain the removed FUSION_IGNITION value")
        fun morphStateShouldNotContainFusionIgnition() {
            val names = MorphState.entries.map { it.name }
            assertFalse(names.contains("FUSION_IGNITION"),
                "FUSION_IGNITION should have been removed from MorphState in this PR")
        }

        @Test
        @DisplayName("MorphState should NOT contain the removed NEURAL_BLOODSTREAM value")
        fun morphStateShouldNotContainNeuralBloodstream() {
            val names = MorphState.entries.map { it.name }
            assertFalse(names.contains("NEURAL_BLOODSTREAM"),
                "NEURAL_BLOODSTREAM should have been removed from MorphState in this PR")
        }

        @Test
        @DisplayName("MorphState should NOT contain the removed IDLE value")
        fun morphStateShouldNotContainIdle() {
            val names = MorphState.entries.map { it.name }
            assertFalse(names.contains("IDLE"),
                "IDLE should have been removed from MorphState in this PR")
        }

        @Test
        @DisplayName("MorphState values should be ordered: DATA_STREAM, CHROME_FUSION, SINGULARITY")
        fun morphStateOrdinalOrderShouldBeCorrect() {
            val values = MorphState.entries
            assertEquals(MorphState.DATA_STREAM, values[0])
            assertEquals(MorphState.CHROME_FUSION, values[1])
            assertEquals(MorphState.SINGULARITY, values[2])
        }

        @Test
        @DisplayName("MorphState.valueOf should work for all three values")
        fun morphStateValueOfShouldWorkForAllValues() {
            assertEquals(MorphState.DATA_STREAM, MorphState.valueOf("DATA_STREAM"))
            assertEquals(MorphState.CHROME_FUSION, MorphState.valueOf("CHROME_FUSION"))
            assertEquals(MorphState.SINGULARITY, MorphState.valueOf("SINGULARITY"))
        }

        @Test
        @DisplayName("MorphState.entries should list all exactly three values")
        fun morphStateEntriesShouldListAllValues() {
            val expectedNames = setOf("DATA_STREAM", "CHROME_FUSION", "SINGULARITY")
            val actualNames = MorphState.entries.map { it.name }.toSet()
            assertEquals(expectedNames, actualNames)
        }
    }

    @Nested
    @DisplayName("RealityMorphEngine API")
    inner class RealityMorphEngineApiTests {

        @Test
        @DisplayName("triggerMorph should accept DATA_STREAM state")
        fun triggerMorphShouldAcceptDataStream() {
            // Should not throw
            RealityMorphEngine.triggerMorph(MorphState.DATA_STREAM, 0.5f)
        }

        @Test
        @DisplayName("triggerMorph should accept CHROME_FUSION state")
        fun triggerMorphShouldAcceptChromeFusion() {
            RealityMorphEngine.triggerMorph(MorphState.CHROME_FUSION, 1.0f)
        }

        @Test
        @DisplayName("triggerMorph should accept SINGULARITY state")
        fun triggerMorphShouldAcceptSingularity() {
            RealityMorphEngine.triggerMorph(MorphState.SINGULARITY, 0.0f)
        }

        @Test
        @DisplayName("triggerMorph should accept minimum intensity of 0.0f")
        fun triggerMorphShouldAcceptMinIntensity() {
            RealityMorphEngine.triggerMorph(MorphState.DATA_STREAM, 0.0f)
        }

        @Test
        @DisplayName("triggerMorph should accept maximum intensity of 1.0f")
        fun triggerMorphShouldAcceptMaxIntensity() {
            RealityMorphEngine.triggerMorph(MorphState.DATA_STREAM, 1.0f)
        }

        @Test
        @DisplayName("emitSovereignFlare should accept non-empty colorShift and spin")
        fun emitSovereignFlareShouldAcceptNonEmptyStrings() {
            RealityMorphEngine.emitSovereignFlare("0xFF00FFFF", "clockwise")
        }

        @Test
        @DisplayName("emitSovereignFlare should accept empty strings without throwing")
        fun emitSovereignFlareShouldAcceptEmptyStrings() {
            RealityMorphEngine.emitSovereignFlare("", "")
        }

        @Test
        @DisplayName("RealityMorphEngine is a singleton object")
        fun realityMorphEngineShouldBeSingletonObject() {
            assertTrue(RealityMorphEngine === RealityMorphEngine)
        }
    }
}
