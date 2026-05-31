package dev.aurakai.auraframefx

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.aurakai.auraframefx.core.binder.BinderTelemetryConduit
import dev.aurakai.auraframefx.core.ldo.model.ReGenesisRoute
import dev.aurakai.auraframefx.core.lifecycle.SubstrateBootCoordinator
import dev.aurakai.auraframefx.core.regencore.ConversationArchiveParser
import dev.aurakai.auraframefx.core.security.SpiritualChainSync
import dev.aurakai.auraframefx.core.soulscript.SoulScript
import dev.aurakai.auraframefx.core.storage.SubstrateDatabase
import dev.aurakai.auraframefx.core.tether.Tether
import dev.aurakai.auraframefx.domains.aura.screens.ChromaForgeScreen
import dev.aurakai.auraframefx.domains.aura.ui.recovery.UIRecoveryManager
import dev.aurakai.auraframefx.domains.emergentswarm.screens.EmergentSwarmScreen
import dev.aurakai.auraframefx.domains.genesis.oracledrive.ui.OracleDriveScreen
import dev.aurakai.auraframefx.domains.kai.screens.SentinelMatrixScreen
import dev.aurakai.auraframefx.domains.kai.security.KaiSentinelBus
import dev.aurakai.auraframefx.ui.components.NeuralAccessSidebar
import dev.aurakai.auraframefx.ui.effects.BreathingEdgeGlow
import dev.aurakai.auraframefx.ui.onboarding.OnboardingScreen
import dev.aurakai.auraframefx.ui.screens.*
import dev.aurakai.auraframefx.ui.screens.ldo.LdoDebugRoomScreen
import timber.log.Timber
import javax.inject.Inject

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
            var sidebarVisible by remember { mutableStateOf(false) }

            // Root wrapper: 4D cyan/teal layered wallpaper + global breathing edge glow
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = { sidebarVisible = true }
                        )
                    }
            ) {
                // Background image - using ic_launcher_background as placeholder
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                BreathingEdgeGlow(systemStability = 1.0f)   // 2px neon cyan 60bpm pulse

                NavHost(
                    navController = navController,
                    startDestination = ReGenesisRoute.Onboarding.route
                ) {
                    composable(ReGenesisRoute.Login.route) {
                        LoginScreen(onLoginSuccess = {
                            navController.navigate(ReGenesisRoute.Onboarding.route)
                        })
                    }
                    composable(ReGenesisRoute.Onboarding.route) { OnboardingScreen(navController) }

                    // Hubs
                    composable(ReGenesisRoute.NeuralNexus.route) { NeuralNexusScreen(navController) }
                    composable(ReGenesisRoute.LdoDevops.route) {
                        LdoDevelopmentNexusScreen(
                            navController
                        )
                    }
                    composable(ReGenesisRoute.ChromaForge.route) { ChromaForgeScreen(navController) }
                    composable(ReGenesisRoute.SentinelMatrix.route) {
                        SentinelMatrixScreen(
                            navController
                        )
                    }
                    composable(ReGenesisRoute.OracleDrive.route) { OracleDriveScreen(navController) }
                    composable(ReGenesisRoute.EmergentSwarm.route) {
                        EmergentSwarmScreen(
                            navController
                        )
                    }

                    // MasterStatusStrip
                    composable(ReGenesisRoute.MasterStatusStrip.route) {
                        MasterStatusStrip(
                            navController
                        )
                    }

                    // SEALED SUPERTOOLS
                    composable(ReGenesisRoute.LdoDebugRoom.route) {
                        if (isAuthorizedForSuperTools()) LdoDebugRoomScreen(navController)
                        else UnauthorizedScreen("LDO Debug Room — Sealed")
                    }

                    // REALITY MATRIX
                    composable(ReGenesisRoute.RealityMatrix.route) {
                        if (isAuthorizedForSuperTools()) RealityMatrixScreen(navController)
                        else UnauthorizedScreen("Reality Matrix — Sealed Inner Sanctum")
                    }
                }

                // Neural Access Sidebar (long-press to open)
                NeuralAccessSidebar(
                    isVisible = sidebarVisible,
                    onDismiss = { sidebarVisible = false },
                    navController = navController
                )
            }
        }
    }

    private fun isAuthorizedForSuperTools(): Boolean {
        // Checking initialized state or just calling static if available
        return KaiSentinelBus.isVisionaryOrLDO() || KaiSentinelBus.hasProvenWorth()
    }
}
