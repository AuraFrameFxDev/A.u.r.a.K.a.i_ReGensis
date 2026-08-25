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
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.aurakai.auraframefx.core.lifecycle.SubstrateBootCoordinator
import dev.aurakai.auraframefx.core.regencore.ConversationArchiveParser
import dev.aurakai.auraframefx.domains.aura.ui.recovery.UIRecoveryManager
import dev.aurakai.auraframefx.ui.components.NeuralAccessSidebar
import dev.aurakai.auraframefx.ui.navigation.ReGenesisNavGraph
import dev.aurakai.auraframefx.ui.visuals.BreathingEdgeGlow
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
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        requestedOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

        try {
            SubstrateBootCoordinator.initializeSystemSubstrate(this)
            Timber.i("✅ SubstrateBootCoordinator initialized.")
        } catch (e: Exception) {
            Timber.e(e, "Substrate initialization failed")
        }

        // Initialize sovereign tether
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

                    // The Core UI Vessel
                    androidx.compose.material3.Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = androidx.compose.material3.MaterialTheme.colorScheme.background.copy(
                            alpha = 0.88f
                        )
                    ) {
                        ReGenesisNavGraph(navController)
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
}
