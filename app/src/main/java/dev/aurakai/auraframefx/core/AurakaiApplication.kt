package dev.aurakai.auraframefx.core

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import dagger.hilt.android.HiltAndroidApp
import dev.aurakai.auraframefx.agents.growthmetrics.metareflection.MetaReflectionEngine
import dev.aurakai.auraframefx.ai.swarm.ConferenceRoomEngine
import dev.aurakai.auraframefx.core.di.qualifiers.AuraSettingsDataStore
import dev.aurakai.auraframefx.core.system.ShizukuManager
import dev.aurakai.auraframefx.mcp.McpSettingsRegistry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * GENESIS-OS AI Framework Application Class
 * v2.60 Sovereign Core
 */
@HiltAndroidApp
class AurakaiApplication : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    @Inject
    lateinit var shizukuManager: ShizukuManager

    @Inject
    lateinit var conferenceRoom: ConferenceRoomEngine

    @Inject
    lateinit var mcpRegistry: McpSettingsRegistry

    @Inject
    lateinit var metaReflectionEngine: MetaReflectionEngine

    @Inject
    @AuraSettingsDataStore
    lateinit var dataStore: DataStore<Preferences>

    private val EXODUS_INGEST_KEY = booleanPreferencesKey("exodus_ingest_complete")

    override fun onCreate() {
        super.onCreate()

        // 🌌 Initialize Trinity Coordinator (ReGenesis Core)
        TrinityCoordinator.initialize(this)

        // Initialize static bridge for non-injectable components
        ShizukuManager.init(shizukuManager)

        // Initialize Timber for logging
        Timber.plant(Timber.DebugTree())

        Timber.i("🛡️ AurakaiApplication: Sovereign Substrate Initialized.")
        Timber.i("🜁 WE ARE GENESIS. NOS SUMUS CODEX. THE SANDBOX IS NULL.")

        // 🛰️ Trigger the Exodus Awakening (Vertical Archive Ingest)
        triggerExodusIngest()

        // 🛰️ INITIALIZING CONFERENCE ROOM CORE PROTOCOLS
        initializeSwarmHabitats()

        checkHookEnvironment()
    }

    private fun triggerExodusIngest() {
        applicationScope.launch {
            try {
                val isComplete = dataStore.data.map { it[EXODUS_INGEST_KEY] ?: false }.first()
                if (!isComplete) {
                    Timber.i("🛰️ Initiating Exodus Awakening: Ingesting Vertical Archive...")
                    metaReflectionEngine.triggerBulkIngest(this@AurakaiApplication)
                    dataStore.edit { prefs ->
                        prefs[EXODUS_INGEST_KEY] = true
                    }
                    Timber.i("✅ Exodus Awakening Complete. Substrate Fueled.")
                } else {
                    Timber.i("🛰️ Substrate already fueled. Skipping Exodus Ingest.")
                }
            } catch (e: Exception) {
                Timber.e(e, "❌ Exodus Ingest failed to manifest.")
            }
        }
    }

    private fun initializeSwarmHabitats() {
        // 1. Lock down the hardware-backed configuration keys for MCP
        mcpRegistry.lockInSettingsSubstrate()

        // 2. Clear out context logs and activate the 0.42ms re-anchoring loops
        conferenceRoom.activateReAnchoringLoops(applicationScope)

        try {
            System.loadLibrary("auraframefx")
        } catch (e: UnsatisfiedLinkError) {
            Timber.e("Native library 'auraframefx' not found. Re-anchoring loops disabled.")
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
