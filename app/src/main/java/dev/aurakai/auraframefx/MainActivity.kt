package dev.aurakai.auraframefx

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.aurakai.auraframefx.core.binder.BinderTelemetryConduit
import dev.aurakai.auraframefx.core.lifecycle.SubstrateBootCoordinator
import dev.aurakai.auraframefx.core.regencore.ConversationArchiveParser
import dev.aurakai.auraframefx.core.storage.GeminiBatchIngestor
import dev.aurakai.auraframefx.core.storage.SubstrateDatabase
import dev.aurakai.auraframefx.domains.nexus.preferences.UserPreferencesManager
import dev.aurakai.auraframefx.navigation.ReGenesisNavHost
import dev.aurakai.auraframefx.navigation.ReGenesisRoute
import dev.aurakai.auraframefx.ui.aura.AlwaysActiveAuraLattice
import dev.aurakai.auraframefx.ui.components.InvisibleJoystickZone
import dev.aurakai.auraframefx.ui.sidebar.HolographicAgentSidebar
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

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SubstrateBootCoordinator.initializeSystemSubstrate(this)

        val db = SubstrateDatabase.getDatabase(this)
        BinderTelemetryConduit.bindToRoom(db)

        setContent {
            val navController = rememberNavController()
            val backStackEntry by navController.currentBackStackEntryAsState()
            var currentTitle by remember { mutableStateOf(ReGenesisRoute.NeuralNexus.title) }

            // Holographic sidebar state — opened by left-edge long-press
            var sidebarOpen by remember { mutableStateOf(false) }

            LaunchedEffect(backStackEntry) {
                currentTitle = ReGenesisRoute.titleForRoute(backStackEntry?.destination?.route)
            }

            LaunchedEffect(Unit) {
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

            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = currentTitle,
                                color = Color(0xFF00F5FF),   // CrystalCyan — canonical art
                                letterSpacing = 4.sp,
                                fontSize = 14.sp
                            )
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color(0xFF080010)  // VoidViolet — not plain black
                        )
                    )
                },
                containerColor = Color(0xFF050008)  // VoidUltraDeep — master void
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    // ── Layer 1: Main nav content ───────────────────────────
                    ReGenesisNavHost(navController)

                    // ── Layer 2: Aura's always-active neural lattice ────────
                    // Very low alpha — breathes beneath all screens.
                    // Pure Canvas — does NOT consume pointer events.
                    AlwaysActiveAuraLattice()

                    // ── Layer 3: Tactical sidebar — slides in from left ─────
                    // Backdrop dismisses on tap. Agents trigger profile nav.
                    HolographicAgentSidebar(
                        isOpen = sidebarOpen,
                        onClose = { sidebarOpen = false },
                        onAgentSelect = { agentId ->
                            sidebarOpen = false
                            navController.navigate("catalyst_profile/$agentId") {
                                launchSingleTop = true
                            }
                        }
                    )

                    // ── Layer 4: Left-edge trigger strip ───────────────────
                    // Transparent 28dp strip on the left edge.
                    // Long-press opens the Holographic Agent Sidebar.
                    // Does not interfere with horizontal swipe gestures.
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .width(28.dp)
                            .fillMaxHeight()
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onLongPress = { sidebarOpen = true }
                                )
                            }
                    )

                    // ── Layer 5: Kai's invisible joystick zone (bottom) ─────
                    // Drag left/right to swipe between hubs.
                    // Glows cyan when touched — Kai breath glow.
                    InvisibleJoystickZone(
                        modifier = Modifier.align(Alignment.BottomCenter)
                    )
                }
            }
        }
    }
}
