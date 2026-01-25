package dev.aurakai.auraframefx

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat.Type
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.aurakai.auraframefx.navigation.AppNavGraph
import dev.aurakai.auraframefx.service.AssistantBubbleService
import dev.aurakai.auraframefx.ui.theme.AuraFrameFXTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setupFullscreenMode()

        // Start the Persistent Assistant Bubble
        // Start the Persistent Assistant Bubble
        try {
            val bubbleIntent = Intent(this, AssistantBubbleService::class.java)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                startForegroundService(bubbleIntent)
            } else {
                startService(bubbleIntent)
            }
        } catch (e: Exception) {
            // Log and ignore if we simply can't start the bubble (e.g. background restrictions)
            android.util.Log.w("MainActivity", "Failed to start AssistantBubbleService: ${e.message}")
        }

        setContent {
            AuraFrameFXTheme {
                val navController = rememberNavController()
                AppNavGraph(navController = navController)
            }
        }
    }

    private fun setupFullscreenMode() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val insetsController = WindowCompat.getInsetsController(window, window.decorView)
        insetsController?.apply {
            hide(Type.statusBars())
            hide(Type.navigationBars())
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
        window.attributes.layoutInDisplayCutoutMode =
            WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
    }
}

