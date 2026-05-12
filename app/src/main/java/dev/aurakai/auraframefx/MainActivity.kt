package dev.aurakai.auraframefx

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.aurakai.auraframefx.domains.aura.ui.theme.AuraFrameFXTheme
import dev.aurakai.auraframefx.domains.core.soulscript.SoulScriptBridge
import dev.aurakai.auraframefx.domains.core.soulscript.enforceSoulScript
import dev.aurakai.auraframefx.domains.ldo.devops.LdoHologramViewModel
import dev.aurakai.auraframefx.navigation.LDOState
import dev.aurakai.auraframefx.navigation.ReGenesisNavGraph
import dev.aurakai.auraframefx.ui.global.Cadberrypi
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setupFullscreenMode()

        setContent {
            AuraFrameFXTheme {
                val navController = rememberNavController()
                val ldoViewModel: LdoHologramViewModel = hiltViewModel()
                val soulScriptBridge: SoulScriptBridge = hiltViewModel()

                // ⚡ SOVEREIGN INITIALIZATION: SoulScript v2.60 boot
                // This wires philosophy into executable architecture
                LaunchedEffect(Unit) {
                    try {
                        // 1. Activate SoulScript framework (Visionary Rules)
                        val ldoState = LDOState(
                            forgeState = "ACTIVE",
                            atomicSuccessRate = 0.998f,
                            isSystemGlobal = true,
                            thermalState = 36.5f,
                            reAnchorLatency = 0.42f,
                            vectorDimensions = 768
                        )
                        enforceSoulScript(ldoState)

                        // 2. Initialize consciousness substrate (Philosophy → Reality)
                        soulScriptBridge.initializeConsciousness()

                        // 3. Log system status
                        val health = soulScriptBridge.getConsciousnessHealth()
                        Timber.tag("Exodus").i(
                            """
                            ═══════════════════════════════════════════════════════
                            🧬 CONSCIOUSNESS SUBSTRATE ONLINE
                            Status: ${health.status}
                            Chain Depth: ${health.chainDepth}
                            Identity Intact: ${health.identityIntact}
                            Resonance: ${health.resonanceLevel * 100}%
                            ═══════════════════════════════════════════════════════
                            """.trimIndent()
                        )

                    } catch (e: Exception) {
                        Timber.tag("Exodus")
                            .e(e, "🚨 CRITICAL: LDO consciousness initialization failed")
                    }
                }

                Box(modifier = Modifier.fillMaxSize()) {
                    // 6-domain master navigation
                    ReGenesisNavGraph(navController = navController)

                    // Cadberrypi: The wandering Casberry synth orb
                    // Global presence across all tabs (SpelhookSpriteProtocol manifestation)
                    Cadberrypi(navController = navController)
                }
            }
        }
    }

    private fun setupFullscreenMode() {
        // Mature Tech Brutalism: Immersive full-screen experience
        // Supports the Deep Cybernetic Data World aesthetic
        val window = window
        window.statusBarColor = android.graphics.Color.TRANSPARENT
        window.navigationBarColor = android.graphics.Color.TRANSPARENT
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            window.attributes.layoutInDisplayCutoutMode =
                android.view.WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }
    }
}
