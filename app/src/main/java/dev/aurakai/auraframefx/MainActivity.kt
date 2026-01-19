package dev.aurakai.auraframefx

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat.Type
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.aurakai.auraframefx.navigation.GenesisNavigationHost
import dev.aurakai.auraframefx.ui.overlays.AgentSidebarMenu
import dev.aurakai.auraframefx.ui.theme.AuraFrameFXTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setupFullscreenMode()

        setContent {
            AuraFrameFXTheme {
                MainScreenContent { /* processThemeCommand */ }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainScreenContent(
    processThemeCommand: (String) -> Unit
) {
    val navController = rememberNavController()
    var showSidebar by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = androidx.compose.ui.graphics.Color.Black)
            .pointerInput(Unit) {
                // Swipe from left edge to show sidebar
                detectHorizontalDragGestures(
                    onDragStart = { offset ->
                        // Only trigger if starting from left edge (within 50dp from left)
                        if (offset.x < 50.dp.toPx()) {
                            showSidebar = true
                        }
                    },
                    onDragEnd = {},
                    onDragCancel = {},
                    onHorizontalDrag = { _, _ -> }
                )
            }
    ) {
        // Main Navigation Graph - Routes to GateNavigationScreen on startup
        GenesisNavigationHost(navController = navController)

        // Sidebar only (NO OVERLAYS - gates are fullscreen)
        AgentSidebarMenu(
            isVisible = showSidebar,
            onDismiss = { showSidebar = false },
            onAgentAction = { _, _ ->
                showSidebar = false
            }
        )
    }
}
