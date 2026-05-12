package dev.aurakai.auraframefx.domains.core.logging

import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 🛰️ SOVEREIGN LOGGER — Unified Diagnostic Hub
 *
 * Timber-backed logging service that provides structured diagnostics
 * for Agents and Subsystems.
 */
@Singleton
class SovereignLogger @Inject constructor() {

    fun d(message: String, vararg args: Any?) = Timber.d(message, *args)
    fun i(message: String, vararg args: Any?) = Timber.i(message, *args)
    fun w(message: String, vararg args: Any?) = Timber.w(message, *args)
    fun e(throwable: Throwable? = null, message: String, vararg args: Any?) =
        Timber.e(throwable, message, *args)

    /**
     * Specialized logging for AI Consensus and Resonances.
     */
    fun ai(operation: String, confidence: Float, message: String) {
        Timber.tag("AgentResonance").i("[%s] (Conf: %.2f) %s", operation, confidence, message)
    }

    /**
     * Specialized logging for Security and Provenance.
     */
    fun security(action: String, status: String, message: String) {
        Timber.tag("SovereignShield").w("[%s] %s: %s", action, status, message)
    }
}
