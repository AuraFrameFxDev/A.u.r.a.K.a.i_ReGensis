package dev.aurakai.auraframefx.quarantine.logging_rebuilt

import timber.log.Timber

/**
 * Logger — Unified logging utility for Oracle Drive
 */
object Timber {
    private const val TAG = "AuraFrameFX"
    
    fun d(tag: String = TAG, message: String) {
        Timber.tag(tag).d(message)
    }
    
    fun e(tag: String = TAG, message: String, throwable: Throwable? = null) {
        if (throwable != null) {
            Timber.tag(tag).e(throwable, message)
        } else {
            Timber.tag(tag).e(message)
        }
    }
    
    fun i(tag: String = TAG, message: String) {
        Timber.tag(tag).i(message)
    }
    
    fun w(tag: String = TAG, message: String) {
        Timber.tag(tag).w(message)
    }

    fun getLogger(tag: String) {
timber.log.Timber    }
}
