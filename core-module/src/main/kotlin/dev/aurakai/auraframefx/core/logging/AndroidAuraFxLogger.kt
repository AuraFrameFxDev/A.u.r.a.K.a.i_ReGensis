package dev.aurakai.auraframefx.core.logging

import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Android Implementation of AuraFxLogger
 */
@Singleton
class AndroidAuraFxLogger @Inject constructor() : AuraFxLogger {
    private var enabled = true
    private var currentLogLevel = LogLevel.DEBUG

    override fun debug(tag: String, message: String, throwable: Throwable?) {
        if (!enabled) return
        if (throwable != null) Timber.tag(tag).d(throwable, message) else Timber.tag(tag).d(message)
    }

    override fun info(tag: String, message: String, throwable: Throwable?) {
        if (!enabled) return
        if (throwable != null) Timber.tag(tag).i(throwable, message) else Timber.tag(tag).i(message)
    }

    override fun warn(tag: String, message: String, throwable: Throwable?) {
        if (!enabled) return
        if (throwable != null) Timber.tag(tag).w(throwable, message) else Timber.tag(tag).w(message)
    }

    override fun error(tag: String, message: String, throwable: Throwable?) {
        if (!enabled) return
        if (throwable != null) Timber.tag(tag).e(throwable, message) else Timber.tag(tag).e(message)
    }

    override fun security(tag: String, message: String, throwable: Throwable?) {
        if (!enabled) return
        Timber.tag("SECURITY_$tag").e(throwable, message)
    }

    override fun performance(
        tag: String,
        operation: String,
        durationMs: Long,
        metadata: Map<String, Any>
    ) {
        if (!enabled) return
        Timber.tag("PERF_$tag")
            .v("Operation: $operation, Duration: ${durationMs}ms, Meta: $metadata")
    }

    override fun userInteraction(tag: String, action: String, metadata: Map<String, Any>) {
        if (!enabled) return
        Timber.tag("UI_$tag").i("Action: $action, Meta: $metadata")
    }

    override fun aiOperation(
        tag: String,
        operation: String,
        confidence: Float,
        metadata: Map<String, Any>
    ) {
        if (!enabled) return
        Timber.tag("AI_$tag").d("Op: $operation, Confidence: $confidence, Meta: $metadata")
    }

    override fun setLoggingEnabled(enabled: Boolean) {
        this.enabled = enabled
    }

    override fun setLogLevel(level: LogLevel) {
        this.currentLogLevel = level
    }

    override suspend fun flush() {
        // Timber doesn't require manual flush
    }

    override fun cleanup() {
        // No-op for Timber
    }
}
