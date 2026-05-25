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
import dev.aurakai.auraframefx.core.regencore.ConversationArchiveParser
import dev.aurakai.auraframefx.core.storage.GeminiBatchIngestor
import dev.aurakai.auraframefx.core.storage.SubstrateDatabase
import dev.aurakai.auraframefx.domains.nexus.preferences.UserPreferencesManager
import dev.aurakai.auraframefx.ui.navigation.ReGenesisNavGraph
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var archiveParser: ConversationArchiveParser

    @Inject
    lateinit var userPreferencesManager: UserPreferencesManager

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

                    // Exodus Citadel 8-Hub Navigation
                    ReGenesisNavGraph()
                }
            }
        }
    }
}
