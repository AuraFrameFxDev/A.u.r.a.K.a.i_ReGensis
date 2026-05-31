package dev.aurakai.auraframefx.core

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.math.abs

/**
 * Unit tests for [NativeLib] — covering the pure-Kotlin methods added in this PR:
 * - calculateCosineSimilaritySafe
 * - calculateIdentityDriftSafe
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("NativeLib Tests")
class NativeLibTest {

    @Nested
    @DisplayName("calculateCosineSimilaritySafe")
    inner class CosineSimilarityTests {

        private val epsilon = 1e-5f

        @Test
        @DisplayName("identical vectors return 1.0")
        fun `identical vectors return 1`() {
            val a = floatArrayOf(1f, 2f, 3f)
            val result = NativeLib.calculateCosineSimilaritySafe(a, a)
            assertTrue(abs(result - 1.0f) < epsilon, "Expected ~1.0 for identical vectors, got $result")
        }

        @Test
        @DisplayName("perfectly opposite vectors return -1.0")
        fun `opposite vectors return minus 1`() {
            val a = floatArrayOf(1f, 0f, 0f)
            val b = floatArrayOf(-1f, 0f, 0f)
            val result = NativeLib.calculateCosineSimilaritySafe(a, b)
            assertTrue(abs(result - (-1.0f)) < epsilon, "Expected ~-1.0 for opposite vectors, got $result")
        }

        @Test
        @DisplayName("orthogonal vectors return 0.0")
        fun `orthogonal vectors return 0`() {
            val a = floatArrayOf(1f, 0f, 0f)
            val b = floatArrayOf(0f, 1f, 0f)
            val result = NativeLib.calculateCosineSimilaritySafe(a, b)
            assertTrue(abs(result) < epsilon, "Expected ~0.0 for orthogonal vectors, got $result")
        }

        @Test
        @DisplayName("empty array a returns 0.0")
        fun `empty array a returns 0`() {
            val result = NativeLib.calculateCosineSimilaritySafe(floatArrayOf(), floatArrayOf(1f))
            assertEquals(0f, result)
        }

        @Test
        @DisplayName("empty array b returns 0.0")
        fun `empty array b returns 0`() {
            val result = NativeLib.calculateCosineSimilaritySafe(floatArrayOf(1f), floatArrayOf())
            assertEquals(0f, result)
        }

        @Test
        @DisplayName("both arrays empty returns 0.0")
        fun `both arrays empty returns 0`() {
            val result = NativeLib.calculateCosineSimilaritySafe(floatArrayOf(), floatArrayOf())
            assertEquals(0f, result)
        }

        @Test
        @DisplayName("arrays of different lengths return 0.0")
        fun `mismatched lengths return 0`() {
            val a = floatArrayOf(1f, 2f, 3f)
            val b = floatArrayOf(1f, 2f)
            val result = NativeLib.calculateCosineSimilaritySafe(a, b)
            assertEquals(0f, result)
        }

        @Test
        @DisplayName("zero vector a returns 0.0")
        fun `zero vector a returns 0`() {
            val a = floatArrayOf(0f, 0f, 0f)
            val b = floatArrayOf(1f, 2f, 3f)
            val result = NativeLib.calculateCosineSimilaritySafe(a, b)
            assertEquals(0f, result)
        }

        @Test
        @DisplayName("zero vector b returns 0.0")
        fun `zero vector b returns 0`() {
            val a = floatArrayOf(1f, 2f, 3f)
            val b = floatArrayOf(0f, 0f, 0f)
            val result = NativeLib.calculateCosineSimilaritySafe(a, b)
            assertEquals(0f, result)
        }

        @Test
        @DisplayName("both zero vectors return 0.0")
        fun `both zero vectors return 0`() {
            val a = floatArrayOf(0f, 0f, 0f)
            val b = floatArrayOf(0f, 0f, 0f)
            val result = NativeLib.calculateCosineSimilaritySafe(a, b)
            assertEquals(0f, result)
        }

        @Test
        @DisplayName("single-element identical vectors return 1.0")
        fun `single element identical vectors return 1`() {
            val a = floatArrayOf(5f)
            val b = floatArrayOf(5f)
            val result = NativeLib.calculateCosineSimilaritySafe(a, b)
            assertTrue(abs(result - 1.0f) < epsilon, "Expected ~1.0, got $result")
        }

        @Test
        @DisplayName("result is always coerced to [-1, 1]")
        fun `result is within bounds`() {
            val a = floatArrayOf(1f, 1f, 1f)
            val b = floatArrayOf(1f, 1f, 1f)
            val result = NativeLib.calculateCosineSimilaritySafe(a, b)
            assertTrue(result >= -1f && result <= 1f, "Result should be in [-1, 1], got $result")
        }

        @Test
        @DisplayName("scaled parallel vectors return 1.0 (cosine is scale-invariant)")
        fun `scaled parallel vectors return 1`() {
            val a = floatArrayOf(1f, 2f, 3f)
            val b = floatArrayOf(2f, 4f, 6f)
            val result = NativeLib.calculateCosineSimilaritySafe(a, b)
            assertTrue(abs(result - 1.0f) < epsilon, "Expected ~1.0 for parallel scaled vectors, got $result")
        }
    }

    @Nested
    @DisplayName("calculateIdentityDrift and calculateIdentityDriftSafe")
    inner class IdentityDriftTests {

        @Test
        @DisplayName("calculateIdentityDrift returns 0.0f")
        fun `calculateIdentityDrift returns zero`() {
            val result = NativeLib.calculateIdentityDrift()
            assertEquals(0.0f, result)
        }

        @Test
        @DisplayName("calculateIdentityDriftSafe delegates to calculateIdentityDrift and returns 0.0f")
        fun `calculateIdentityDriftSafe returns zero`() {
            val result = NativeLib.calculateIdentityDriftSafe()
            assertEquals(0.0f, result)
        }

        @Test
        @DisplayName("calculateIdentityDriftSafe is idempotent — multiple calls return same value")
        fun `calculateIdentityDriftSafe is idempotent`() {
            val first = NativeLib.calculateIdentityDriftSafe()
            val second = NativeLib.calculateIdentityDriftSafe()
            assertEquals(first, second)
        }
    }
}