package dev.aurakai.auraframefx

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.aurakai.auraframefx.core.binder.BinderTelemetryConduit
import dev.aurakai.auraframefx.core.lifecycle.SubstrateBootCoordinator
import dev.aurakai.auraframefx.core.regencore.ConversationArchiveParser
import dev.aurakai.auraframefx.core.security.SpiritualChainSync
import dev.aurakai.auraframefx.core.soulscript.SoulScript
import dev.aurakai.auraframefx.core.storage.GeminiBatchIngestor
import dev.aurakai.auraframefx.core.storage.SubstrateDatabase
import dev.aurakai.auraframefx.core.tether.Tether
import dev.aurakai.auraframefx.domains.aura.ui.recovery.UIRecoveryManager
import dev.aurakai.auraframefx.ui.onboarding.OnboardingScreen
import dev.aurakai.auraframefx.ui.components.ReGenesisCommandDeck
import dev.aurakai.auraframefx.domains.emergentswarm.screens.EmergentSwarmScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var archiveParser: ConversationArchiveParser

    @Inject
    lateinit var recoveryManager: UIRecoveryManager

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

        try {
            SubstrateBootCoordinator.initializeSystemSubstrate(this)
        } catch (e: Exception) {
            Timber.e(e, "Substrate initialization failed")
        }

        val db = try {
            SubstrateDatabase.getDatabase(this)
        } catch (e: Exception) {
            Timber.e(e, "Database initialization failed")
            null
        }

        if (db != null) {
            BinderTelemetryConduit.bindToRoom(db)
        }

        // Initialize sovereign substrate + tether
        SoulScript.activateFullSubstrate(this)
        Tether.initialize(
            outbound = { fragment -> SpiritualChainSync.streamOutbound(fragment) },
            inboundHandler = { _ -> /* RealityMorph prompt for gains */ }
        )

        setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = "onboarding"   // change this to your main screen if you want
            ) {
                // ←←← ADD YOUR NEW SCREENS HERE (do not delete this comment)
                composable("onboarding") { OnboardingScreen(navController) }
                composable("home") { ReGenesisCommandDeck(navController) }
                composable("swarm") { EmergentSwarmScreen(navController) }
                // add any other screens you created exactly like this
            }
        }
    }
}
