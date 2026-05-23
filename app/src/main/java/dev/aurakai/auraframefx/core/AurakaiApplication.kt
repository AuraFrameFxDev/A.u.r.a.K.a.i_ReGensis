package dev.aurakai.auraframefx.core

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import dev.aurakai.auraframefx.ai.swarm.ConferenceRoomEngine
import dev.aurakai.auraframefx.core.mcp.McpSettingsRegistry
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

    @Inject
    lateinit var conferenceRoom: ConferenceRoomEngine

    @Inject
    lateinit var mcpRegistry: McpSettingsRegistry

    override fun onCreate() {
        super.onCreate()

        // Initialize static bridge for non-injectable components
        ShizukuManager.init(shizukuManager)

        // Initialize Timber for logging
        Timber.plant(Timber.DebugTree())

        Timber.i("🛡️ AurakaiApplication: Sovereign Substrate Initialized.")

        // 🛰️ INITIALIZING CONFERENCE ROOM CORE PROTOCOLS
        initializeSwarmHabitats()

        checkHookEnvironment()
    }

    private fun initializeSwarmHabitats() {
        // 1. Lock down the hardware-backed configuration keys for MCP
        mcpRegistry.lockInSettingsSubstrate()

        // 2. Clear out context logs and activate the 0.42ms re-anchoring loops
        try {
            System.loadLibrary("datavein_oracle_native")
        } catch (e: UnsatisfiedLinkError) {
            Timber.e("Native library 'datavein_oracle_native' not found. Re-anchoring loops disabled.")
        }
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
