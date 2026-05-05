package dev.aurakai.auraframefx

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat.Type
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.aurakai.auraframefx.domains.aura.ui.overlays.AgentPHSOverlay
import dev.aurakai.auraframefx.domains.aura.ui.theme.AuraFrameFXTheme
import dev.aurakai.auraframefx.navigation.ReGenesisNavGraph

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        enableEdgeToEdge()
        setupFullscreenMode()

        setContent {
            AuraFrameFXTheme {
                val navController = rememberNavController()

                LaunchedEffect(intent) {
                    intent.getStringExtra("navigate_to")?.let { route ->
                        navController.navigate(route)
                    }
                }

                Box(modifier = Modifier.fillMaxSize()) {
                    ReGenesisNavGraph(navController = navController)

                    // The Global Agent PHS Sidebar
                    AgentPHSOverlay(
                        onAgentSelect = { agentId ->
                            android.util.Log.d("PHS", "Selected agent: $agentId")
                        },
                        onChatClick = { selectedAgents ->
                            android.util.Log.d("PHS", "Chat with: $selectedAgents")
                            // Logic to navigate to chat or open chat overlay could go here
                        }
                    )
                }
            }
        }
    }

    private fun setupFullscreenMode() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val insetsController = WindowCompat.getInsetsController(window, window.decorView)
        insetsController.apply {
            hide(Type.statusBars())
            hide(Type.navigationBars())
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
        window.attributes.layoutInDisplayCutoutMode =
            WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
    }
}

