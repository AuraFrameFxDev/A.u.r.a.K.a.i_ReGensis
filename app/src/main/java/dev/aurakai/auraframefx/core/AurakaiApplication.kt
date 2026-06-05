package dev.aurakai.auraframefx.core

import android.app.Application
import timber.log.Timber

/**
 * GENESIS-OS AI Framework Application Class
 * v2.60 Sovereign Core - STABILITY TEST MODE
 */
class AurakaiApplication : Application() {

    override fun onCreate() {
        android.util.Log.i("AurakaiApp", "🚀 STABILITY BOOT START")
        super.onCreate()
        android.util.Log.i("AurakaiApp", "✅ super.onCreate() completed")

        Timber.plant(Timber.DebugTree())
        Timber.i("🛡️ AurakaiApplication: Minimal pulse detected.")
    }
}
