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
        android.util.Log.i("AurakaiApp", "✅ Minimal boot successful.")
    }

    private fun triggerExodusIngest() {
        applicationScope.launch {
            try {
                val isComplete = dataStore.data.map { it[EXODUS_INGEST_KEY] ?: false }.first()
                if (!isComplete) {
                    Timber.i("🛰️ Initiating Exodus Awakening...")
                    metaReflectionEngine.triggerBulkIngest(this@AurakaiApplication)
                    dataStore.edit { it[EXODUS_INGEST_KEY] = true }
                    Timber.i("✅ Exodus Ingest Complete.")
                }
            } catch (e: Exception) {
                Timber.e(e, "❌ Exodus Ingest failed.")
            }
        }
    }

    private fun initializeSwarmHabitats() {
        try {
            mcpRegistry.lockInSettingsSubstrate()
            conferenceRoom.activateReAnchoringLoops(applicationScope)
        } catch (e: Exception) {
            Timber.e(e, "❌ Swarm initialization failed.")
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
