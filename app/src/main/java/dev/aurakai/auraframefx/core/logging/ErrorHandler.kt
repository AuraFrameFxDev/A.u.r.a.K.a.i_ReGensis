package dev.aurakai.auraframefx.core.logging

import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 🛰️ SOVEREIGN ERROR HANDLER — Unified Exception Protocol
 *
 * Centralizes error reporting and pipes them into the SovereignLogger.
 */
interface ErrorHandler {
    fun handleError(throwable: Throwable, message: String? = null)
    fun logWarning(message: String, throwable: Throwable? = null)
}

@Singleton
class SovereignErrorHandler @Inject constructor(
    private val logger: SovereignLogger
) : ErrorHandler {

    override fun handleError(throwable: Throwable, message: String?) {
        val finalMessage = message ?: "An unhandled exception occurred"
        logger.e(throwable, finalMessage)
        // TODO: Integrate with Crashlytics/Sentry here if needed
    }

    override fun logWarning(message: String, throwable: Throwable?) {
        logger.w("$message | Throwable: ${throwable?.message ?: "None"}")
    }
}
