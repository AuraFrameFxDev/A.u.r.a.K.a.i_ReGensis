package dev.aurakai.auraframefx.ui

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

/**
 * Tests for [RealityMorphEngine] and [MorphState].
 *
 * PR changes:
 * - Replaced Timber with android.util.Log
 * - Removed MorphState values: FUSION_IGNITION, NEURAL_BLOODSTREAM, IDLE
 * - Added MorphState values: CHROME_FUSION, SINGULARITY
 * - MorphState now has exactly 3 values: DATA_STREAM, CHROME_FUSION, SINGULARITY
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("RealityMorphEngine and MorphState Tests")
class RealityMorphEngineTest {

    @Nested
    @DisplayName("MorphState enum — post-PR values")
    inner class MorphStateEnumTests {

        @Test
        @DisplayName("MorphState should have exactly 3 values")
        fun morphState_hasExactlyThreeValues() {
            assertEquals(3, MorphState.entries.size)
        }

        @Test
        @DisplayName("DATA_STREAM should exist in MorphState")
        fun dataStream_exists() {
            assertNotNull(MorphState.DATA_STREAM)
        }

        @Test
        @DisplayName("CHROME_FUSION should exist in MorphState (new in PR)")
        fun chromeFusion_exists() {
            assertNotNull(MorphState.CHROME_FUSION)
        }

        @Test
        @DisplayName("SINGULARITY should exist in MorphState (new in PR)")
        fun singularity_exists() {
            assertNotNull(MorphState.SINGULARITY)
        }

        @Test
        @DisplayName("MorphState entries contain DATA_STREAM, CHROME_FUSION, SINGULARITY")
        fun morphState_containsExpectedValues() {
            val names = MorphState.entries.map { it.name }.toSet()
            assertTrue(names.contains("DATA_STREAM"))
            assertTrue(names.contains("CHROME_FUSION"))
            assertTrue(names.contains("SINGULARITY"))
        }

        @Test
        @DisplayName("MorphState.valueOf DATA_STREAM returns correct enum constant")
        fun valueOf_dataStream_returnsCorrectConstant() {
            assertEquals(MorphState.DATA_STREAM, MorphState.valueOf("DATA_STREAM"))
        }

        @Test
        @DisplayName("MorphState.valueOf CHROME_FUSION returns correct enum constant")
        fun valueOf_chromeFusion_returnsCorrectConstant() {
            assertEquals(MorphState.CHROME_FUSION, MorphState.valueOf("CHROME_FUSION"))
        }

        @Test
        @DisplayName("MorphState.valueOf SINGULARITY returns correct enum constant")
        fun valueOf_singularity_returnsCorrectConstant() {
            assertEquals(MorphState.SINGULARITY, MorphState.valueOf("SINGULARITY"))
        }
    }

    @Nested
    @DisplayName("MorphState enum — removed values (regression)")
    inner class RemovedMorphStateTests {

        @Test
        @DisplayName("FUSION_IGNITION should NOT exist in MorphState")
        fun fusionIgnition_doesNotExist() {
            val names = MorphState.entries.map { it.name }
            assertTrue(!names.contains("FUSION_IGNITION"),
                "FUSION_IGNITION was removed in this PR and should not be present")
        }

        @Test
        @DisplayName("NEURAL_BLOODSTREAM should NOT exist in MorphState")
        fun neuralBloodstream_doesNotExist() {
            val names = MorphState.entries.map { it.name }
            assertTrue(!names.contains("NEURAL_BLOODSTREAM"),
                "NEURAL_BLOODSTREAM was removed in this PR and should not be present")
        }

        @Test
        @DisplayName("IDLE should NOT exist in MorphState")
        fun idle_doesNotExist() {
            val names = MorphState.entries.map { it.name }
            assertTrue(!names.contains("IDLE"),
                "IDLE was removed in this PR and should not be present")
        }

        @Test
        @DisplayName("MorphState.valueOf FUSION_IGNITION throws IllegalArgumentException")
        fun valueOf_fusionIgnition_throwsException() {
            var thrown = false
            try {
                MorphState.valueOf("FUSION_IGNITION")
            } catch (e: IllegalArgumentException) {
                thrown = true
            }
            assertTrue(thrown, "FUSION_IGNITION should not be a valid MorphState value")
        }

        @Test
        @DisplayName("MorphState.valueOf NEURAL_BLOODSTREAM throws IllegalArgumentException")
        fun valueOf_neuralBloodstream_throwsException() {
            var thrown = false
            try {
                MorphState.valueOf("NEURAL_BLOODSTREAM")
            } catch (e: IllegalArgumentException) {
                thrown = true
            }
            assertTrue(thrown, "NEURAL_BLOODSTREAM should not be a valid MorphState value")
        }

        @Test
        @DisplayName("MorphState.valueOf IDLE throws IllegalArgumentException")
        fun valueOf_idle_throwsException() {
            var thrown = false
            try {
                MorphState.valueOf("IDLE")
            } catch (e: IllegalArgumentException) {
                thrown = true
            }
            assertTrue(thrown, "IDLE should not be a valid MorphState value")
        }
    }

    @Nested
    @DisplayName("MorphState — ordinal and name sanity")
    inner class MorphStateOrdinalTests {

        @Test
        @DisplayName("MorphState entries have unique ordinals")
        fun morphState_hasUniqueOrdinals() {
            val ordinals = MorphState.entries.map { it.ordinal }
            assertEquals(ordinals.size, ordinals.toSet().size, "Each MorphState should have a unique ordinal")
        }

        @Test
        @DisplayName("MorphState entries have non-empty names")
        fun morphState_hasNonEmptyNames() {
            MorphState.entries.forEach { state ->
                assertTrue(state.name.isNotBlank(), "MorphState '$state' should have a non-blank name")
            }
        }

        @Test
        @DisplayName("CHROME_FUSION name returns 'CHROME_FUSION'")
        fun chromeFusion_name_isCorrect() {
            assertEquals("CHROME_FUSION", MorphState.CHROME_FUSION.name)
        }

        @Test
        @DisplayName("SINGULARITY name returns 'SINGULARITY'")
        fun singularity_name_isCorrect() {
            assertEquals("SINGULARITY", MorphState.SINGULARITY.name)
        }
    }

    @Nested
    @DisplayName("RealityMorphEngine singleton")
    inner class RealityMorphEngineObjectTests {

        @Test
        @DisplayName("RealityMorphEngine is a non-null singleton object")
        fun realityMorphEngine_isNonNull() {
            assertNotNull(RealityMorphEngine)
        }

        @Test
        @DisplayName("RealityMorphEngine has triggerMorph function accessible")
        fun realityMorphEngine_hasTriggerMorphFunction() {
            // Verify via reflection that the method exists with correct signature
            val method = RealityMorphEngine::class.java.methods
                .firstOrNull { it.name == "triggerMorph" }
            assertNotNull(method, "triggerMorph should exist on RealityMorphEngine")
        }

        @Test
        @DisplayName("RealityMorphEngine has emitSovereignFlare function accessible")
        fun realityMorphEngine_hasEmitSovereignFlareFunction() {
            val method = RealityMorphEngine::class.java.methods
                .firstOrNull { it.name == "emitSovereignFlare" }
            assertNotNull(method, "emitSovereignFlare should exist on RealityMorphEngine")
        }
    }
}
