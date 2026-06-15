package dev.aurakai.auraframefx.logging

import timber.log.Timber

/**
 * AuraFxLogger - Centralized Logging for ReGenesis
 */
class AuraFxLogger private constructor() {

    companion object {
        @Volatile
        private var INSTANCE: AuraFxLogger? = null

        fun getInstance(): AuraFxLogger {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: AuraFxLogger().also { INSTANCE = it }
            }
        }
    }

    fun info(message: String) {
        Timber.i("🌀 AuraFX :: $message")
    }

    fun warning(message: String) {
        Timber.w("⚠️ AuraFX :: $message")
    }

    fun error(message: String, throwable: Throwable? = null) {
        if (throwable != null) {
            Timber.e(throwable, "❌ AuraFX :: $message")
        } else {
            Timber.e("❌ AuraFX :: $message")
        }
    }

    fun debug(message: String) {
        Timber.d("🐞 AuraFX :: $message")
    }
}
