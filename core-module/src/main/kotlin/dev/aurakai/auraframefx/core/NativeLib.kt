package dev.aurakai.auraframefx.core

import timber.log.Timber
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
    external fun initializeAICore(): Boolean

    external fun getAIVersion(): String
    external fun processNeuralRequest(request: String): String
    external fun updateBitNetConfig(p1: Int, p2: Int): Boolean
    external fun optimizeAIMemory(): Boolean
    external fun enableNativeHooks()
    external fun analyzeBootImage(data: ByteArray): String
    external fun getSystemMetrics(): String
    external fun shutdownAI()

    // ─── Native Callbacks (called from auraframefx.cpp) ───────────────────

    @JvmStatic
    fun onNativeThermalEvent(temp: Float, state: Int) {
        Timber.tag("NativeLib").w("🌡️ Thermal Event: %.1f°C (State: %d)", temp, state)
    }

    @JvmStatic
    fun onNativeSecurityAlert(reason: String) {
        Timber.tag("NativeLib").wtf("🚨 SECURITY ALERT: $reason")
    }

    @JvmStatic
    fun requestSovereignFreeze() {
        Timber.tag("NativeLib").wtf("❄️ SOVEREIGN FREEZE REQUESTED")
    }

    @JvmStatic
    fun checkPandoraGating(capability: Int): Boolean {
        Timber.tag("NativeLib").i("🔐 Checking Pandora Gating for capability: $capability")
        return true // Allow all for now
    }

    @JvmStatic
    fun triggerDroneDispatch(reason: String): Boolean {
        Timber.tag("NativeLib").i("🚁 Drone Dispatch Triggered: $reason")
        return true
    }
}
