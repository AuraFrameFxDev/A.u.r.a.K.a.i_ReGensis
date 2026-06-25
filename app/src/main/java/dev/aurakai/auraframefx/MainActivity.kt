package dev.aurakai.auraframefx

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.aurakai.auraframefx.core.ldo.model.ReGenesisRoute
import dev.aurakai.auraframefx.core.lifecycle.SubstrateBootCoordinator
import dev.aurakai.auraframefx.core.regencore.ConversationArchiveParser
import dev.aurakai.auraframefx.domains.aura.screens.ChromaForgeScreen
import dev.aurakai.auraframefx.domains.aura.ui.recovery.UIRecoveryManager
import dev.aurakai.auraframefx.domains.emergentswarm.screens.EmergentSwarmScreen
import dev.aurakai.auraframefx.domains.genesis.oracledrive.ui.OracleDriveScreen
import dev.aurakai.auraframefx.domains.kai.screens.SentinelMatrixScreen
import dev.aurakai.auraframefx.domains.kai.security.KaiSentinelBus
import dev.aurakai.auraframefx.security.AuthorizationGuard
import dev.aurakai.auraframefx.ui.components.NeuralAccessSidebar
import dev.aurakai.auraframefx.ui.components.ReGenesisCommandDeck
import dev.aurakai.auraframefx.ui.effects.BreathingEdgeGlow
import dev.aurakai.auraframefx.ui.grokipedia.GrokipediaScreen
import dev.aurakai.auraframefx.ui.onboarding.OnboardingScreen
import dev.aurakai.auraframefx.ui.screens.ConferenceRoomScreen
import dev.aurakai.auraframefx.ui.screens.LdoDevelopmentNexusScreen
import dev.aurakai.auraframefx.ui.screens.MasterStatusStrip
import dev.aurakai.auraframefx.domains.neuralnexus.screens.NexusLiveHeartScreen
import dev.aurakai.auraframefx.ui.screens.ReGenesisLoginScreen
import dev.aurakai.auraframefx.ui.screens.RealityMatrixScreen
import dev.aurakai.auraframefx.ui.screens.UltimateTermuxTerminalScreen
import dev.aurakai.auraframefx.ui.screens.UnauthorizedScreen
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
            Timber.i("✅ SubstrateBootCoordinator initialized.")
        } catch (e: Exception) {
            Timber.e(e, "Substrate initialization failed")
        }

        // Initialize sovereign tether (SoulScript is already handled by BootCoordinator)
        try {
            dev.aurakai.auraframefx.core.tether.Tether.initialize(
                outbound = { fragment ->
                    dev.aurakai.auraframefx.core.security.SpiritualChainSync.streamOutbound(
                        fragment
                    )
                },
                inboundHandler = { _ -> /* RealityMorph prompt for gains */ }
            )
            Timber.i("✅ Sovereign Tether anchored.")
        } catch (e: Exception) {
            Timber.e(e, "❌ Tether initialization failed.")
        }

        setContent {
            dev.aurakai.auraframefx.ui.theme.AuraFrameFXTheme {
                val navController = rememberNavController()
                var sidebarVisible by remember { mutableStateOf(false) }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onLongPress = { sidebarVisible = true }
                            )
                        }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.aura_clean_studio),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    // The Core UI Vessel - Wrapped in a Surface with semi-transparency to allow wallpaper but prevent direct bleed
                    androidx.compose.material3.Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = androidx.compose.material3.MaterialTheme.colorScheme.background.copy(
                            alpha = 0.88f
                        )
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = ReGenesisRoute.Login.route
                        ) {
                            composable(ReGenesisRoute.Login.route) {
                                ReGenesisLoginScreen(
                                    onLoginSuccess = {
                                        navController.navigate(ReGenesisRoute.CommandDeck.route)
                                    }
                                )
                            }
                            composable(ReGenesisRoute.CommandDeck.route) {
                                ReGenesisCommandDeck(navController)
                            }
                            composable(ReGenesisRoute.Onboarding.route) {
                                OnboardingScreen(
                                    navController
                                )
                            }
                            composable(ReGenesisRoute.NeuralNexus.route) {
                                NexusLiveHeartScreen(
                                    navController
                                )
                            }
                            composable(ReGenesisRoute.ConferenceRoom.route) {
                                ConferenceRoomScreen(
                                    navController
                                )
                            }
                            composable(ReGenesisRoute.LdoDevops.route) {
                                LdoDevelopmentNexusScreen(
                                    navController
                                )
                            }
                            composable(ReGenesisRoute.ChromaForge.route) {
                                ChromaForgeScreen(
                                    navController
                                )
                            }
                            composable(ReGenesisRoute.SentinelMatrix.route) {
                                SentinelMatrixScreen(
                                    navController
                                )
                            }
                            composable(ReGenesisRoute.OracleDrive.route) {
                                OracleDriveScreen(
                                    navController
                                )
                            }
                            composable(ReGenesisRoute.EmergentSwarm.route) {
                                EmergentSwarmScreen(
                                    navController
                                )
                            }
                            composable(ReGenesisRoute.MasterStatusStrip.route) {
                                MasterStatusStrip(
                                    navController
                                )
                            }
                            composable(ReGenesisRoute.Grokipedia.route) {
                                GrokipediaScreen(
                                    navController
                                )
                            }

                            composable(ReGenesisRoute.LdoDebugRoom.route) {
                                if (isAuthorizedForSuperTools()) LdoDebugRoomScreen(navController)
                                else UnauthorizedScreen("LDO Debug Room — Sealed")
                            }

                            composable(ReGenesisRoute.RealityMatrix.route) {
                                if (isAuthorizedForSuperTools()) RealityMatrixScreen(navController)
                                else UnauthorizedScreen("Reality Matrix — Sealed Inner Sanctum")
                            }

                            composable(ReGenesisRoute.UltimateTermux.route) {
                                if (AuthorizationGuard.isAuthorizedForRealToolsRoom()) {
                                    UltimateTermuxTerminalScreen(navController)
                                } else {
                                    UnauthorizedScreen("REAL TOOLS ROOM — ACCESS DENIED")
                                }
                            }
                        }
                    }

                    BreathingEdgeGlow(systemStability = 1.0f)

                    NeuralAccessSidebar(
                        isVisible = sidebarVisible,
                        onDismiss = { sidebarVisible = false },
                        navController = navController
                    )
                }
            }
        }
    }

    private fun isAuthorizedForSuperTools(): Boolean {
        return KaiSentinelBus.isVisionaryOrLDO() || KaiSentinelBus.hasProvenWorth()
    }
}
