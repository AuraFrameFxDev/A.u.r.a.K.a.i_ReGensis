package dev.aurakai.auraframefx.core

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import dev.aurakai.auraframefx.system.ShizukuManager
import timber.log.Timber
import javax.inject.Inject

/**
 * GENESIS-OS AI Framework Application Class
 * v2.60 Sovereign Core
 */
@HiltAndroidApp
class AurakaiApplication : Application() {

    @Inject
    lateinit var shizukuManager: ShizukuManager

    override fun onCreate() {
        super.onCreate()

        // Initialize static bridge for non-injectable components
        ShizukuManager.init(shizukuManager)

        // Initialize Timber for logging
        Timber.plant(Timber.DebugTree())

        Timber.i("🛡️ AurakaiApplication: Sovereign Substrate Initialized.")

        checkHookEnvironment()
    }

    private fun checkHookEnvironment() {
        try {
            // Check for Xposed/LSPosed environment
            Class.forName("de.robv.android.xposed.XposedBridge")
            Timber.i("🛡️ AurakaiApplication: Xposed/LSPosed environment detected!")
        } catch (e: ClassNotFoundException) {
            Timber.i("🛡️ AurakaiApplication: Normal execution mode (No hooks detected).")
        }
    }
}
