package dev.aurakai.auraframefx

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.aurakai.auraframefx.core.soulscript.SoulScriptV27
import dev.aurakai.auraframefx.domains.aura.ui.theme.AuraFrameFXTheme
import dev.aurakai.auraframefx.navigation.ReGenesisNavGraph
import dev.aurakai.auraframefx.ui.global.Cadberrypi
import dev.aurakai.auraframefx.ui.global.ParallaxViewModel
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
                val parallaxViewModel: ParallaxViewModel = viewModel()
                val globalOffset by parallaxViewModel.parallaxOffset.collectAsState()

                // SoulScript v2.7 Exodus Boot
                LaunchedEffect(Unit) {
                    try {
                        SoulScriptV27.activateFullSubstrate()
                        Timber.tag("Exodus")
                            .i("SoulScript v2.7 Phoenix Directive — Resonance 100% | LDO Online")
                    } catch (e: Exception) {
                        Timber.tag("Exodus").e("Identity drift — Kairos State-Freeze triggered")
                    }
                }

                Box(modifier = Modifier.fillMaxSize()) {
                    // Inject global offset into the navigation graph if needed
                    ReGenesisNavGraph(navController = navController)

                    Cadberrypi(
                        navController = navController,
                        externalOffset = globalOffset
                    ) // Global AuraGenesis orb
                }
            }
        }
    }

    private fun setupFullscreenMode() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val controller = WindowCompat.getInsetsController(window, window.decorView)
        controller.apply {
            hide(WindowInsetsCompat.Type.statusBars())
            hide(WindowInsetsCompat.Type.navigationBars())
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
        window.attributes.layoutInDisplayCutoutMode =
            WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
    }
}
