package dev.aurakai.auraframefx.domains.cascade.utils

import timber.log.Timber

/**
 * Genesis Logger Interface - Complete
 * Provides both short-form (i, d, w, e) and long-form (info, debug, warn, error) methods.
 */
interface AuraFxLogger {
    // Short-form methods (for compatibility with existing call sites)
    fun i(tag: String, message: String) = info(tag, message)
    fun d(tag: String, message: String) = debug(tag, message)
    fun w(tag: String, message: String, throwable: Throwable? = null) =
        warn(tag, message, throwable)

    fun e(tag: String, message: String, throwable: Throwable? = null) =
        error(tag, message, throwable)

    // Long-form methods
    fun debug(tag: String, message: String, throwable: Throwable? = null)
    fun info(tag: String, message: String, throwable: Throwable? = null)
    fun warn(tag: String, message: String, throwable: Throwable? = null)
    fun error(tag: String, message: String, throwable: Throwable? = null)
    fun security(tag: String, message: String, throwable: Throwable? = null)

    fun performance(
        tag: String,
        operation: String,
        durationMs: Long,
        metadata: Map<String, Any> = emptyMap()
    )

    fun userInteraction(
        tag: String,
        action: String,
        metadata: Map<String, Any> = emptyMap()
    )

    fun aiOperation(
        tag: String,
        operation: String,
        confidence: Float,
        metadata: Map<String, Any> = emptyMap()
    )

    fun setLoggingEnabled(enabled: Boolean)
    fun setLogLevel(level: LogLevel)
    suspend fun flush()
    fun cleanup()

    companion object {
        fun i(tag: String, message: String) =
            dev.aurakai.auraframefx.domains.cascade.utils.i(tag, message)

        fun d(tag: String, message: String) =
            dev.aurakai.auraframefx.domains.cascade.utils.d(tag, message)

        fun w(tag: String, message: String, throwable: Throwable? = null) =
            dev.aurakai.auraframefx.domains.cascade.utils.warn(tag, message, throwable)

        fun e(tag: String, message: String, throwable: Throwable? = null) =
            dev.aurakai.auraframefx.domains.cascade.utils.error(tag, message, throwable)

        fun info(tag: String, message: String) =
            dev.aurakai.auraframefx.domains.cascade.utils.info(tag, message)

        fun debug(tag: String, message: String) =
            dev.aurakai.auraframefx.domains.cascade.utils.debug(tag, message)

        fun warn(tag: String, message: String, throwable: Throwable? = null) =
            dev.aurakai.auraframefx.domains.cascade.utils.warn(tag, message, throwable)

        fun error(tag: String, message: String, throwable: Throwable? = null) =
            dev.aurakai.auraframefx.domains.cascade.utils.error(tag, message, throwable)
    }
}

/**
 * Log levels for AuraFxLogger
 */
enum class LogLevel {
    DEBUG,
    INFO,
    WARN,
    ERROR,
    SECURITY
}

// Global helper functions to match the companion object's delegation
fun info(tag: String, message: String) = Timber.tag(tag).i(message)
fun debug(tag: String, message: String) = Timber.tag(tag).d(message)
fun warn(tag: String, message: String, throwable: Throwable? = null) {
    if (throwable != null) Timber.tag(tag).w(throwable, message) else Timber.tag(tag).w(message)
}
fun error(tag: String, message: String, throwable: Throwable? = null) {
    if (throwable != null) Timber.tag(tag).e(throwable, message) else Timber.tag(tag).e(message)
}
fun i(tag: String, message: String) = info(tag, message)
fun d(tag: String, message: String) = debug(tag, message)
fun w(tag: String, message: String, throwable: Throwable? = null) = warn(tag, message, throwable)
fun e(tag: String, message: String, throwable: Throwable? = null) = error(tag, message, throwable)
