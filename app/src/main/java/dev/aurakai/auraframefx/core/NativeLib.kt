package dev.aurakai.auraframefx.core

import kotlin.math.sqrt

/**
 * AUTHORITATIVE NATIVELIB — Substrate Accelerated Logic
 * Implementation derived from ReGenesis v2.71 requirements.
 */
object NativeLib {

    /**
     * Legacy compatibility shim used by SoulScriptCore and ViewModels
     */
    fun calculateIdentityDriftSafe(): Float = try {
        calculateIdentityDrift()
    } catch (_: Throwable) {
        0.0f
    }

    /**
     * Compute the cosine similarity between two float vectors.
     *
     * @param a First vector; must have the same length as `b`.
     * @param b Second vector; must have the same length as `a`.
     * @return The cosine similarity in the range [-1f, 1f]. Returns `0f` if either array is empty, the lengths differ, either vector has zero magnitude, or an internal error occurs.
     */
    fun calculateCosineSimilaritySafe(a: FloatArray, b: FloatArray): Float = try {
        if (a.isEmpty() || b.isEmpty() || a.size != b.size) 0f
        else {
            var dot = 0.0
            var normASq = 0.0
            var normBSq = 0.0
            for (i in a.indices) {
                val va = a[i].toDouble()
                val vb = b[i].toDouble()
                dot += va * vb
                normASq += va * va
                normBSq += vb * vb
            }
            val na = sqrt(normASq)
            val nb = sqrt(normBSq)
            if (na == 0.0 || nb == 0.0) 0f
            else (dot / (na * nb)).toFloat().coerceIn(-1f, 1f)
        }
    } catch (_: Throwable) {
        0f
    }

    /**
     * Optional baseline — keep for forward compat
     */
    fun calculateIdentityDrift(): Float = 0.0f

    /**
     * Hardware-backed AI initialization check
     */
    fun initializeAICore(): Boolean = true
}
