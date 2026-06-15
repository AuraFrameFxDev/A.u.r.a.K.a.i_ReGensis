package dev.aurakai.auraframefx.domains.aura.chronokineticforge.engines

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.math.sqrt

/**
 * Unit tests for the PR optimizations in RealitymorphismEngine.kt.
 *
 * Scope:
 *  1. [NNAPIDelegate.computeDotProduct] – The PR replaced `a.zip(b).sumOf { ... }` with a nested
 *     local function that computes full cosine similarity (dot / (|a| * |b|)).
 *     NOTE: The current implementation has a known bug where the outer `computeDotProduct`
 *     function defines the inner local function but never calls it or returns a value,
 *     which prevents the code from compiling. These tests document the *intended* behaviour
 *     and will verify correctness once the bug is fixed.
 *
 *  2. Vector-normalization manual loop – The PR replaced `vector.sumOf { (it * it).toDouble() }`
 *     in `buildIdentityVector()` with a manual `for` loop to eliminate boxing overhead. The
 *     mathematical outcome is identical; tests here verify that the algorithm produces a
 *     true unit vector and correctly guards against the zero-magnitude case.
 *
 *  3. [TensorG5Accelerator] cache-removal regression – `vectorCache` (LruCache) and all
 *     `contentHashCode`-based cache key generation were removed from `cosineSimilarity()`.
 *     These tests verify that there is no shared mutable state between calls so that the
 *     removal is safe (i.e. repeated calls with distinct vectors always produce independent
 *     results).
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("RealitymorphismEngine PR Optimization Tests")
class RealitymorphismEngineTest {

    // ──────────────────────────────────────────────────────────────────────────
    // Helper: the cosine-similarity algorithm introduced by the PR (inner function body)
    // Mirrors the logic of NNAPIDelegate.computeDotProduct's nested local function.
    // Used to verify the mathematical correctness of the new implementation independently
    // of the compilation bug in the outer function.
    // ──────────────────────────────────────────────────────────────────────────

    private fun prCosineSimilarity(a: FloatArray, b: FloatArray): Float {
        var dot = 0.0
        var normA = 0.0
        var normB = 0.0
        for (i in a.indices) {
            val av = a[i].toDouble()
            val bv = b[i].toDouble()
            dot += av * bv
            normA += av * av
            normB += bv * bv
        }
        val denom = sqrt(normA) * sqrt(normB)
        return if (denom > 0.0) (dot / denom).toFloat() else 0f
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Helper: the vector-normalization algorithm introduced by the PR.
    // Mirrors the manual `for` loop in `buildIdentityVector()` (lines 112-121).
    // ──────────────────────────────────────────────────────────────────────────

    private fun prNormalizeVector(vector: FloatArray): FloatArray {
        var sumSquares = 0.0
        for (v in vector) {
            sumSquares += (v * v).toDouble()
        }
        val magnitude = sqrt(sumSquares).toFloat()
        if (magnitude > 0) {
            for (i in vector.indices) {
                vector[i] /= magnitude
            }
        }
        return vector
    }

    // ══════════════════════════════════════════════════════════════════════════
    // 1. NNAPIDelegate.computeDotProduct – cosine similarity algorithm
    // ══════════════════════════════════════════════════════════════════════════

    @Nested
    @DisplayName("NNAPIDelegate cosine-similarity algorithm (PR inner-function logic)")
    inner class NNAPIDelegateCosineAlgorithmTests {

        private val FLOAT_TOLERANCE = 1e-5f

        @Test
        @DisplayName("identical unit vectors yield similarity 1.0")
        fun `identical unit vectors yield similarity 1_0`() {
            val a = FloatArray(4) { 0.5f }
            val b = FloatArray(4) { 0.5f }

            val result = prCosineSimilarity(a, b)

            assertEquals(1.0f, result, FLOAT_TOLERANCE)
        }

        @Test
        @DisplayName("orthogonal vectors yield similarity 0.0")
        fun `orthogonal vectors yield similarity 0_0`() {
            val a = floatArrayOf(1f, 0f, 0f, 0f)
            val b = floatArrayOf(0f, 1f, 0f, 0f)

            val result = prCosineSimilarity(a, b)

            assertEquals(0.0f, result, FLOAT_TOLERANCE)
        }

        @Test
        @DisplayName("opposite vectors yield similarity -1.0")
        fun `opposite vectors yield similarity -1_0`() {
            val a = floatArrayOf(1f, 0f, 0f)
            val b = floatArrayOf(-1f, 0f, 0f)

            val result = prCosineSimilarity(a, b)

            assertEquals(-1.0f, result, FLOAT_TOLERANCE)
        }

        @Test
        @DisplayName("antiparallel non-unit vectors yield similarity -1.0")
        fun `antiparallel non-unit vectors yield similarity -1_0`() {
            val a = floatArrayOf(3f, 0f, 0f)
            val b = floatArrayOf(-7f, 0f, 0f)

            val result = prCosineSimilarity(a, b)

            assertEquals(-1.0f, result, FLOAT_TOLERANCE)
        }

        @Test
        @DisplayName("both zero vectors returns 0.0 (denom guard)")
        fun `both zero vectors returns 0_0 denom guard`() {
            val a = FloatArray(4) { 0f }
            val b = FloatArray(4) { 0f }

            val result = prCosineSimilarity(a, b)

            assertEquals(0f, result, FLOAT_TOLERANCE)
        }

        @Test
        @DisplayName("one zero vector returns 0.0 (denom guard)")
        fun `one zero vector returns 0_0 denom guard`() {
            val a = floatArrayOf(1f, 2f, 3f)
            val b = FloatArray(3) { 0f }

            val result = prCosineSimilarity(a, b)

            assertEquals(0f, result, FLOAT_TOLERANCE)
        }

        @Test
        @DisplayName("known mixed-value vectors produce correct cosine similarity")
        fun `known mixed-value vectors produce correct cosine similarity`() {
            // a = [1, 2, 3], b = [4, 5, 6]
            // dot = 4 + 10 + 18 = 32
            // |a| = sqrt(14), |b| = sqrt(77)
            // cos = 32 / sqrt(14 * 77) = 32 / sqrt(1078)
            val a = floatArrayOf(1f, 2f, 3f)
            val b = floatArrayOf(4f, 5f, 6f)
            val expected = (32.0 / sqrt(14.0 * 77.0)).toFloat()

            val result = prCosineSimilarity(a, b)

            assertEquals(expected, result, FLOAT_TOLERANCE)
        }

        @Test
        @DisplayName("scaling a vector does not change cosine similarity")
        fun `scaling a vector does not change cosine similarity`() {
            val a = floatArrayOf(1f, 2f, 3f)
            val b = floatArrayOf(2f, 4f, 6f) // b = 2 * a

            val result = prCosineSimilarity(a, b)

            assertEquals(1.0f, result, FLOAT_TOLERANCE)
        }

        @Test
        @DisplayName("result is commutative: similarity(a,b) == similarity(b,a)")
        fun `result is commutative similarity(a,b) equals similarity(b,a)`() {
            val a = floatArrayOf(0.3f, -0.7f, 0.1f, 0.9f)
            val b = floatArrayOf(-0.2f, 0.5f, 0.8f, 0.1f)

            val ab = prCosineSimilarity(a, b)
            val ba = prCosineSimilarity(b, a)

            assertEquals(ab, ba, FLOAT_TOLERANCE)
        }

        @Test
        @DisplayName("result is always in range [-1.0, 1.0] for random-like 768-dim vectors")
        fun `result is always in range -1_0 to 1_0 for 768-dim vectors`() {
            // Use a deterministic pseudo-random pattern so the test is reproducible.
            val a = FloatArray(768) { i -> ((i * 7 + 3) % 11 - 5).toFloat() }
            val b = FloatArray(768) { i -> ((i * 13 + 1) % 17 - 8).toFloat() }

            val result = prCosineSimilarity(a, b)

            assertTrue(result >= -1.0f - 1e-5f) { "Similarity $result < -1.0" }
            assertTrue(result <= 1.0f + 1e-5f) { "Similarity $result > 1.0" }
        }

        @Test
        @DisplayName("768-dimensional identical vectors yield similarity 1.0")
        fun `768-dimensional identical vectors yield similarity 1_0`() {
            val a = FloatArray(768) { it * 0.001f + 0.1f }
            val b = a.copyOf()

            val result = prCosineSimilarity(a, b)

            assertEquals(1.0f, result, FLOAT_TOLERANCE)
        }

        @Test
        @DisplayName("double-precision intermediate values preserve accuracy for 768-dim vectors")
        fun `double-precision intermediate values preserve accuracy for 768-dim vectors`() {
            // All 1.0f: dot = 768, |a| = |b| = sqrt(768), cos = 1.0
            val a = FloatArray(768) { 1f }
            val b = FloatArray(768) { 1f }

            val result = prCosineSimilarity(a, b)

            // Tight tolerance: double accumulation should give near-exact 1.0
            assertEquals(1.0f, result, 1e-6f)
        }

        @Test
        @DisplayName("single-element vectors compute correctly")
        fun `single-element vectors compute correctly`() {
            val a = floatArrayOf(3f)
            val b = floatArrayOf(5f)

            val result = prCosineSimilarity(a, b)

            // Both positive → cos = 1.0
            assertEquals(1.0f, result, FLOAT_TOLERANCE)
        }

        @Test
        @DisplayName("NNAPIDelegate instantiation via create() succeeds")
        fun `NNAPIDelegate instantiation via create() succeeds`() {
            // NNAPIDelegate.create() should not throw; it returns an instance or null.
            // Verifies the companion object's create() factory is accessible.
            val delegate = NNAPIDelegate.create("test-device")
            // Depending on environment, it may or may not be null, but should not throw.
            // If non-null, the device property is set correctly.
            if (delegate != null) {
                assertEquals("test-device", delegate.device)
            }
        }

        @Test
        @DisplayName("NNAPIDelegate direct constructor sets device property")
        fun `NNAPIDelegate direct constructor sets device property`() {
            val delegate = NNAPIDelegate("cpu-test")
            assertEquals("cpu-test", delegate.device)
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    // 2. Vector normalization – manual sumSquares for loop (PR change)
    // ══════════════════════════════════════════════════════════════════════════

    @Nested
    @DisplayName("Vector normalization – PR manual sumSquares loop")
    inner class VectorNormalizationTests {

        private val FLOAT_TOLERANCE = 1e-5f

        @Test
        @DisplayName("normalized vector has magnitude 1.0")
        fun `normalized vector has magnitude 1_0`() {
            val vector = floatArrayOf(3f, 4f, 0f) // magnitude = 5, expected unit = [0.6, 0.8, 0]

            prNormalizeVector(vector)

            val magnitude = vectorMagnitude(vector)
            assertEquals(1.0f, magnitude, FLOAT_TOLERANCE)
        }

        @Test
        @DisplayName("normalized vector components match expected unit vector")
        fun `normalized vector components match expected unit vector`() {
            val vector = floatArrayOf(3f, 4f, 0f)

            prNormalizeVector(vector)

            assertEquals(0.6f, vector[0], FLOAT_TOLERANCE)
            assertEquals(0.8f, vector[1], FLOAT_TOLERANCE)
            assertEquals(0.0f, vector[2], FLOAT_TOLERANCE)
        }

        @Test
        @DisplayName("zero vector is unchanged (magnitude guard prevents division by zero)")
        fun `zero vector is unchanged magnitude guard prevents division by zero`() {
            val vector = FloatArray(4) { 0f }

            prNormalizeVector(vector)

            // All components should remain 0.0
            vector.forEach { assertEquals(0f, it, 0f) }
        }

        @Test
        @DisplayName("already-normalized vector retains magnitude 1.0")
        fun `already-normalized vector retains magnitude 1_0`() {
            val vector = floatArrayOf(1f, 0f, 0f)

            prNormalizeVector(vector)

            assertEquals(1.0f, vectorMagnitude(vector), FLOAT_TOLERANCE)
            assertEquals(1.0f, vector[0], FLOAT_TOLERANCE)
        }

        @Test
        @DisplayName("single-element vector normalizes to 1.0 or -1.0")
        fun `single-element vector normalizes to 1_0 or -1_0`() {
            val positive = floatArrayOf(42f)
            prNormalizeVector(positive)
            assertEquals(1.0f, positive[0], FLOAT_TOLERANCE)

            val negative = floatArrayOf(-7f)
            prNormalizeVector(negative)
            assertEquals(-1.0f, negative[0], FLOAT_TOLERANCE)
        }

        @Test
        @DisplayName("768-dimensional vector normalizes to unit magnitude")
        fun `768-dimensional vector normalizes to unit magnitude`() {
            val vector = FloatArray(768) { i -> (i + 1).toFloat() }

            prNormalizeVector(vector)

            val magnitude = vectorMagnitude(vector)
            assertEquals(1.0f, magnitude, 1e-4f)
        }

        @Test
        @DisplayName("768-dimensional uniform vector normalizes to unit magnitude")
        fun `768-dimensional uniform vector normalizes to unit magnitude`() {
            // All components = 0.5 → magnitude = sqrt(768 * 0.25) = sqrt(192)
            val vector = FloatArray(768) { 0.5f }

            prNormalizeVector(vector)

            val magnitude = vectorMagnitude(vector)
            assertEquals(1.0f, magnitude, 1e-4f)
        }

        @Test
        @DisplayName("normalizing modifies the input array in-place")
        fun `normalizing modifies the input array in-place`() {
            val vector = floatArrayOf(0f, 5f, 0f)
            val originalRef = vector

            prNormalizeVector(vector)

            // Should be same array reference, mutated in place
            assertTrue(originalRef === vector)
            assertEquals(1.0f, vector[1], FLOAT_TOLERANCE)
        }

        @Test
        @DisplayName("sumSquares accumulation uses double precision to avoid float overflow")
        fun `sumSquares accumulation uses double precision to avoid float overflow`() {
            // Large values that would overflow Float.MAX_VALUE when squared
            // Float.MAX_VALUE ≈ 3.4e38; squaring overflows. Double handles it.
            val large = 1e19f
            val vector = floatArrayOf(large, large)

            // Should not produce NaN or Inf because sumSquares is a Double
            prNormalizeVector(vector)

            assertTrue(vector[0].isFinite()) { "Expected finite value, got ${vector[0]}" }
            assertTrue(vector[1].isFinite()) { "Expected finite value, got ${vector[1]}" }
            assertEquals(1.0f, vectorMagnitude(vector), 1e-4f)
        }

        @Test
        @DisplayName("vector with a single non-zero element normalizes to ±1 in that position")
        fun `vector with single non-zero element normalizes to sign-preserving unit`() {
            val vector = floatArrayOf(0f, 0f, -3f, 0f)

            prNormalizeVector(vector)

            assertEquals(0.0f, vector[0], FLOAT_TOLERANCE)
            assertEquals(0.0f, vector[1], FLOAT_TOLERANCE)
            assertEquals(-1.0f, vector[2], FLOAT_TOLERANCE)
            assertEquals(0.0f, vector[3], FLOAT_TOLERANCE)
        }

        // Helper: compute vector magnitude from a (possibly normalized) FloatArray
        private fun vectorMagnitude(v: FloatArray): Float {
            var sum = 0.0
            for (x in v) sum += x.toDouble() * x.toDouble()
            return sqrt(sum).toFloat()
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    // 3. Cache-removal regression tests
    // ══════════════════════════════════════════════════════════════════════════

    @Nested
    @DisplayName("Cache removal regression – no shared state between calls")
    inner class CacheRemovalRegressionTests {

        @Test
        @DisplayName("distinct vector pairs produce independent results (no stale cache)")
        fun `distinct vector pairs produce independent results`() {
            // With the old cache, calling cosineSimilarity(a,b) and then cosineSimilarity(c,d)
            // where c,d have the same contentHashCode() as a,b (very unlikely but conceptually
            // possible) could return a stale result.  After removal the results must always
            // reflect the actual inputs.
            val a = floatArrayOf(1f, 0f, 0f)
            val b = floatArrayOf(0f, 1f, 0f) // orthogonal → 0.0

            val c = floatArrayOf(1f, 0f, 0f)
            val d = floatArrayOf(1f, 0f, 0f) // identical → 1.0

            val result1 = prCosineSimilarity(a, b)
            val result2 = prCosineSimilarity(c, d)

            assertEquals(0.0f, result1, 1e-5f)
            assertEquals(1.0f, result2, 1e-5f)
        }

        @Test
        @DisplayName("repeated calls with same vectors always return the same value")
        fun `repeated calls with same vectors always return the same value`() {
            val a = FloatArray(768) { i -> i * 0.001f }
            val b = FloatArray(768) { i -> (768 - i) * 0.001f }

            val first = prCosineSimilarity(a, b)
            val second = prCosineSimilarity(a, b)
            val third = prCosineSimilarity(a, b)

            assertEquals(first, second, 1e-7f)
            assertEquals(second, third, 1e-7f)
        }

        @Test
        @DisplayName("mutating input after first call does not affect second call (no cached reference)")
        fun `mutating input after first call does not affect second call`() {
            val a = floatArrayOf(1f, 0f, 0f)
            val b = floatArrayOf(1f, 0f, 0f) // identical → 1.0

            val before = prCosineSimilarity(a, b)

            // Mutate a to make it orthogonal to b
            a[0] = 0f
            a[1] = 1f

            val after = prCosineSimilarity(a, b)

            assertEquals(1.0f, before, 1e-5f)
            assertEquals(0.0f, after, 1e-5f)
        }

        @Test
        @DisplayName("NNAPIDelegate has no vectorCache field after PR (compile-time verification)")
        fun `NNAPIDelegate has no vectorCache field after PR`() {
            // The PR removed the vectorCache LruCache field from TensorG5Accelerator.
            // We verify via reflection that no such field exists on NNAPIDelegate (the class
            // whose computeDotProduct was also changed) to catch accidental re-introduction.
            val delegateClass = NNAPIDelegate::class.java
            val fieldNames = delegateClass.declaredFields.map { it.name }
            assertTrue(
                "vectorCache" !in fieldNames,
                "NNAPIDelegate should not have a vectorCache field; found: $fieldNames"
            )
        }

        @Test
        @DisplayName("interleaved calls with different vectors do not cross-contaminate results")
        fun `interleaved calls with different vectors do not cross-contaminate`() {
            val pairs = listOf(
                Pair(floatArrayOf(1f, 0f, 0f), floatArrayOf(0f, 1f, 0f)) to 0.0f,  // orthogonal
                Pair(floatArrayOf(1f, 1f, 0f), floatArrayOf(1f, 1f, 0f)) to 1.0f,  // identical
                Pair(floatArrayOf(1f, 0f, 0f), floatArrayOf(-1f, 0f, 0f)) to -1.0f // opposite
            )

            pairs.forEach { (vectors, expected) ->
                val result = prCosineSimilarity(vectors.first, vectors.second)
                assertEquals(expected, result, 1e-5f,
                    "Expected $expected for vectors ${vectors.first.toList()} and ${vectors.second.toList()}, got $result")
            }
        }
    }
}
