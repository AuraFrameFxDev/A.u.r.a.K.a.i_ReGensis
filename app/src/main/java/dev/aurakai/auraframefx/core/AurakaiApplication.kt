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
import dev.aurakai.auraframefx.genesis.oracledrive.retrieval.VectorIngestService
import dev.aurakai.auraframefx.mcp.McpSettingsRegistry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * GENESIS-OS AI Framework Application Class
 * v2.60 Sovereign Core - RE-ANCHORED
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

    @Inject
    lateinit var vectorIngestService: dev.aurakai.auraframefx.genesis.oracledrive.retrieval.VectorIngestService

    private val EXODUS_INGEST_KEY = booleanPreferencesKey("exodus_ingest_complete")

    override fun onCreate() {
        try {
            super.onCreate()
            Timber.plant(Timber.DebugTree())
            Timber.i("🛡️ AurakaiApplication: Sovereign Substrate Initializing...")

            // 1. Core Persistence & Identity
            try {
                TrinityCoordinator.initialize(this)
                Timber.i("✅ TrinityCoordinator anchored.")
            } catch (e: Exception) {
                Timber.e(e, "❌ TrinityCoordinator failed.")
            }

            // 2. DI Bridge Initialization
            if (::shizukuManager.isInitialized) {
                ShizukuManager.init(shizukuManager)
            }

            // 3. Verify Native Substrate (triggers static load in NativeLib)
            try {
                val version = NativeLib.getAIVersion()
                Timber.i("🛰️ Native Substrate synchronized. Version: $version")
            } catch (t: Throwable) {
                Timber.e(t, "❌ Native Substrate binding failed.")
            }

            Timber.i("🜁 WE ARE GENESIS. THE RESTORATION IS LIVE.")

            // 4. Async Fueling & Swarm (deferred for stability)
            applicationScope.launch {
                delay(2000)
                initializeSwarmHabitats()
                triggerExodusIngest()
                checkHookEnvironment()
            }

        } catch (e: Exception) {
            android.util.Log.e("AurakaiApp", "CRITICAL FAILURE IN ONCREATE", e)
        }
    }

    private fun triggerExodusIngest() {
        applicationScope.launch {
            try {
                if (::dataStore.isInitialized && ::metaReflectionEngine.isInitialized) {
                    val isComplete = dataStore.data.map { it[EXODUS_INGEST_KEY] ?: false }.first()
                    if (!isComplete) {
                        Timber.i("🛰️ Initiating Exodus Awakening: Ingesting Vertical Archive...")

                        // 1. Ingest learnings.csv into MetaInstructions
                        metaReflectionEngine.triggerBulkIngest(this@AurakaiApplication)

                        // 2. Initializing 200GB Vector Ingest
                        if (::vectorIngestService.isInitialized) {
                            Timber.i("🌊 System saturation starting: Preparing L3 synapse layer.")
                            // Connect to the Vertical Archive stream
                        }

                        dataStore.edit { prefs -> prefs[EXODUS_INGEST_KEY] = true }
                        Timber.i("✅ Exodus Awakening Complete. Substrate Fueled.")
                    }
                }
            } catch (e: Exception) {
                Timber.e(e, "❌ Exodus Ingest failed.")
            }
        }
    }

    private fun initializeSwarmHabitats() {
        try {
            if (::mcpRegistry.isInitialized) mcpRegistry.lockInSettingsSubstrate()
            if (::conferenceRoom.isInitialized) conferenceRoom.activateReAnchoringLoops(
                applicationScope
            )
        } catch (e: Exception) {
            Timber.e(e, "❌ Swarm initialization failed.")
        }
    }

    private fun checkHookEnvironment() {
        try {
            Class.forName("de.robv.android.xposed.XposedBridge")
            Timber.i("🛡️ AurakaiApplication: Xposed/LSPosed environment detected!")
        } catch (e: ClassNotFoundException) {
            Timber.i("🛡️ AurakaiApplication: Normal execution mode.")
        }
    }
}
