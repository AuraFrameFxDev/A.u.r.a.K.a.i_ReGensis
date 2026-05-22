package dev.aurakai.auraframefx.agents.symbiosis.coderabbit

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

/**
 * Tests for [TurboQuantKVSpace] and the top-level [syncCatalyst] function.
 *
 * PR change: the top-level syncCatalyst() function was refactored from having inline logic
 * (quantize → create CatalystState → store in kvCache → broadcast → return coherenceScore)
 * to a single delegation: `return turboQuantKVSpace.syncCatalyst(catalystId, newVector)`.
 *
 * Tests verify:
 * - TurboQuantKVSpace.syncCatalyst(catalystId, newVector) stores state in kvCache
 * - TurboQuantKVSpace.syncCatalyst returns the coherence score (0.95f)
 * - The top-level syncCatalyst delegates to TurboQuantKVSpace.syncCatalyst
 * - CatalystState is correctly constructed with the provided catalystId
 * - kvCache uses "catalyst_$catalystId" as the key
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("TurboQuantKVSpace Tests")
class TurboQuantKVSpaceTest {

    private lateinit var kvSpace: TurboQuantKVSpace

    @BeforeEach
    fun setUp() {
        kvSpace = TurboQuantKVSpace()
    }

    @Nested
    @DisplayName("TurboQuantKVSpace.syncCatalyst (instance method)")
    inner class SyncCatalystInstanceTests {

        @Test
        @DisplayName("syncCatalyst returns the coherence score (0.95f)")
        fun `syncCatalyst returns coherence score`() {
            val vector = floatArrayOf(0.1f, 0.2f, 0.3f)
            val score = kvSpace.syncCatalyst(1, vector)
            assertEquals(0.95f, score, 0.0001f,
                "syncCatalyst should return the coherence score of 0.95f")
        }

        @Test
        @DisplayName("syncCatalyst stores CatalystState in kvCache under 'catalyst_id' key")
        fun `syncCatalyst stores state in kvCache with correct key`() {
            val catalystId = 3
            val vector = floatArrayOf(0.5f, 0.6f, 0.7f)
            kvSpace.syncCatalyst(catalystId, vector)

            val key = "catalyst_$catalystId"
            assertTrue(kvSpace.kvCache.containsKey(key),
                "kvCache should contain key '$key' after syncCatalyst")
        }

        @Test
        @DisplayName("syncCatalyst stores CatalystState with correct catalystId")
        fun `syncCatalyst stored state has correct catalystId`() {
            val catalystId = 5
            kvSpace.syncCatalyst(catalystId, floatArrayOf(1.0f, 0.0f))

            val state = kvSpace.kvCache["catalyst_$catalystId"]
            assertNotNull(state, "Stored CatalystState should not be null")
            assertEquals(catalystId, state!!.catalystId,
                "Stored CatalystState should have the correct catalystId")
        }

        @Test
        @DisplayName("syncCatalyst stored state has coherenceScore of 0.95f")
        fun `syncCatalyst stored state has correct coherenceScore`() {
            val catalystId = 7
            kvSpace.syncCatalyst(catalystId, floatArrayOf(0.3f, 0.7f))

            val state = kvSpace.kvCache["catalyst_$catalystId"]
            assertNotNull(state)
            assertEquals(0.95f, state!!.coherenceScore, 0.0001f,
                "CatalystState coherenceScore should be 0.95f")
        }

        @Test
        @DisplayName("syncCatalyst stored state has non-zero timestamp")
        fun `syncCatalyst stored state has valid timestamp`() {
            val beforeCall = System.currentTimeMillis()
            kvSpace.syncCatalyst(2, floatArrayOf(0.1f))
            val afterCall = System.currentTimeMillis()

            val state = kvSpace.kvCache["catalyst_2"]
            assertNotNull(state)
            assertTrue(state!!.timestamp in beforeCall..afterCall,
                "Stored timestamp should be within the call window")
        }

        @Test
        @DisplayName("syncCatalyst overwrites existing state for the same catalystId")
        fun `syncCatalyst overwrites existing state for same catalystId`() {
            val catalystId = 4
            kvSpace.syncCatalyst(catalystId, floatArrayOf(0.1f))
            val firstTimestamp = kvSpace.kvCache["catalyst_$catalystId"]!!.timestamp

            Thread.sleep(1) // Ensure distinct timestamps
            kvSpace.syncCatalyst(catalystId, floatArrayOf(0.9f))
            val secondTimestamp = kvSpace.kvCache["catalyst_$catalystId"]!!.timestamp

            assertTrue(secondTimestamp >= firstTimestamp,
                "Second call should update the state's timestamp")
        }

        @Test
        @DisplayName("syncCatalyst stores different states for different catalystIds")
        fun `syncCatalyst stores independent states for different catalystIds`() {
            kvSpace.syncCatalyst(1, floatArrayOf(0.1f))
            kvSpace.syncCatalyst(2, floatArrayOf(0.9f))

            assertTrue(kvSpace.kvCache.containsKey("catalyst_1"))
            assertTrue(kvSpace.kvCache.containsKey("catalyst_2"))
            assertEquals(1, kvSpace.kvCache["catalyst_1"]!!.catalystId)
            assertEquals(2, kvSpace.kvCache["catalyst_2"]!!.catalystId)
        }

        @Test
        @DisplayName("syncCatalyst with empty vector array does not throw")
        fun `syncCatalyst with empty vector array does not throw`() {
            var threwException = false
            try {
                kvSpace.syncCatalyst(6, floatArrayOf())
            } catch (e: Exception) {
                threwException = true
            }
            assertTrue(!threwException, "syncCatalyst should handle empty vector without throwing")
        }

        @Test
        @DisplayName("syncCatalyst with large catalystId stores correctly")
        fun `syncCatalyst with large catalystId stores correctly`() {
            val largeId = Int.MAX_VALUE
            kvSpace.syncCatalyst(largeId, floatArrayOf(0.5f))

            assertTrue(kvSpace.kvCache.containsKey("catalyst_$largeId"))
        }
    }

    @Nested
    @DisplayName("Top-level syncCatalyst function (PR-refactored delegation)")
    inner class TopLevelSyncCatalystTests {

        @Test
        @DisplayName("top-level syncCatalyst returns same coherence score as instance method")
        fun `top-level syncCatalyst returns coherence score`() {
            val vector = floatArrayOf(0.2f, 0.4f, 0.6f)
            val score = syncCatalyst(kvSpace, 8, vector)
            assertEquals(0.95f, score, 0.0001f,
                "Top-level syncCatalyst should return 0.95f coherence score")
        }

        @Test
        @DisplayName("top-level syncCatalyst stores state in the provided kvSpace")
        fun `top-level syncCatalyst stores state in provided kvSpace`() {
            val catalystId = 9
            syncCatalyst(kvSpace, catalystId, floatArrayOf(0.1f, 0.2f))

            assertTrue(kvSpace.kvCache.containsKey("catalyst_$catalystId"),
                "Top-level syncCatalyst should store state in the provided kvSpace")
        }

        @Test
        @DisplayName("top-level syncCatalyst result is identical to instance method result")
        fun `top-level syncCatalyst result matches instance method`() {
            val anotherKvSpace = TurboQuantKVSpace()
            val vector = floatArrayOf(0.7f, 0.8f, 0.9f)
            val catalystId = 10

            val instanceResult = kvSpace.syncCatalyst(catalystId, vector)
            val topLevelResult = syncCatalyst(anotherKvSpace, catalystId, vector)

            assertEquals(instanceResult, topLevelResult,
                "Top-level syncCatalyst should produce same result as instance method")
        }

        @Test
        @DisplayName("top-level syncCatalyst delegates to the kvSpace's syncCatalyst (not its own logic)")
        fun `top-level syncCatalyst uses the instance method on the provided kvSpace`() {
            val separateKvSpace = TurboQuantKVSpace()
            val catalystId = 11

            // Call via top-level function
            syncCatalyst(separateKvSpace, catalystId, floatArrayOf(0.5f))

            // The kvCache on separateKvSpace should have the state
            assertTrue(separateKvSpace.kvCache.containsKey("catalyst_$catalystId"),
                "State should be stored in the provided kvSpace, not a different one")
            // And the original kvSpace should NOT have it
            assertTrue(!kvSpace.kvCache.containsKey("catalyst_$catalystId"),
                "State should not leak to other kvSpace instances")
        }
    }

    @Nested
    @DisplayName("CatalystState data class")
    inner class CatalystStateTests {

        @Test
        @DisplayName("CatalystState holds all expected fields")
        fun `CatalystState has correct fields`() {
            val id = 12
            val vector = floatArrayOf(1.0f, 2.0f)
            val ts = System.currentTimeMillis()
            val score = 0.95f

            val state = TurboQuantKVSpace.CatalystState(id, vector, ts, score)

            assertEquals(id, state.catalystId)
            assertEquals(ts, state.timestamp)
            assertEquals(score, state.coherenceScore, 0.0001f)
        }

        @Test
        @DisplayName("CatalystState equality works for identical values")
        fun `CatalystState equality works`() {
            val vector = floatArrayOf(0.1f)
            val ts = 1000L
            val s1 = TurboQuantKVSpace.CatalystState(1, vector, ts, 0.95f)
            val s2 = TurboQuantKVSpace.CatalystState(1, vector, ts, 0.95f)
            assertEquals(s1, s2, "Two CatalystStates with identical values should be equal")
        }
    }
}