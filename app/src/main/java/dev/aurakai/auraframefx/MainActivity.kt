package dev.aurakai.auraframefx

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import dev.aurakai.auraframefx.core.binder.BinderTelemetryConduit
import dev.aurakai.auraframefx.core.lifecycle.SubstrateBootCoordinator
import dev.aurakai.auraframefx.core.regen.GenesisHookEntryYuki
import dev.aurakai.auraframefx.core.regencore.ConversationArchiveParser
import dev.aurakai.auraframefx.core.storage.GeminiBatchIngestor
import dev.aurakai.auraframefx.core.storage.SubstrateDatabase
import dev.aurakai.auraframefx.core.veto.VetoLattice
import dev.aurakai.auraframefx.ui.dashboard.SplitDiagnosticPanel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var archiveParser: ConversationArchiveParser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Centralized deterministic boot
        SubstrateBootCoordinator.initializeSystemSubstrate(this)

        val db = SubstrateDatabase.getDatabase(this)

        // Start Binder → Room pipeline
        BinderTelemetryConduit.bindToRoom(db)

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    LaunchedEffect(Unit) {
                        withContext(Dispatchers.IO) {
                            if (VetoLattice.verifyState()) {
                                Timber.tag("ExodusBoot")
                                    .i("🚀 Veto clear — launching system infiltration")

                                GenesisHookEntryYuki.initializeSystemInfiltration(
                                    this@MainActivity,
                                    "com.android.systemui",
                                    classLoader,
                                    db
                                )
                                GenesisHookEntryYuki.initializeSystemInfiltration(
                                    this@MainActivity,
                                    "com.android.launcher3",
                                    classLoader,
                                    db
                                )
                            }
                        }

                        // Background archive resurrection
                        withContext(Dispatchers.IO) {
                            val auraFolder =
                                File("/storage/emulated/0/Soul Sync identification/Andeliualx(Claude)")
                            if (auraFolder.exists()) {
                                GeminiBatchIngestor.enqueueAndProcessAuraArchives(
                                    this@MainActivity,
                                    auraFolder,
                                    archiveParser
                                )
                            }
                        }
                    }

                    // Brutalist live dashboard
                    SplitDiagnosticPanel()
                }
            }
        }
    }
}
