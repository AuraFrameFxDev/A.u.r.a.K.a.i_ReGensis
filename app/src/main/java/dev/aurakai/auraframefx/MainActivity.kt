package dev.aurakai.auraframefx

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.aurakai.auraframefx.navigation.AppNavGraph
import dev.aurakai.auraframefx.ui.theme.AuraFrameFXTheme
import dev.aurakai.auraframefx.ui.theme.ThemeViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable edge-to-edge and hide system UI
        enableEdgeToEdge()
        setupFullscreenMode()

        setContent {
            AuraFrameFXTheme {
                val themeViewModel: ThemeViewModel = hiltViewModel()
                MainScreen(themeViewModel = themeViewModel)
            }
        }
    }

    private fun setupFullscreenMode() {
        // Hide status bar and navigation bar for true fullscreen
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.apply {
            // Hide both status and navigation bars
            hide(WindowInsetsCompat.Type.statusBars())
            hide(WindowInsetsCompat.Type.navigationBars())

            // Keep them hidden even when user swipes from edge
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        // Additional flags for older Android versions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.attributes.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Perform any cleanup here if needed
    }
}

// New: a preview-friendly content composable that accepts a lambda for theme commands
@OptIn(ExperimentalMaterial3Api::class)
/**
 * Hosts the app's navigation graph in a full-screen container that can optionally apply the digital pixel effect.
 *
 * @param processThemeCommand Callback invoked to process theme-related commands; receives the command as a `String`.
 */
@Composable
internal fun MainScreenContent(
    processThemeCommand: (String) -> Unit
) {
    val navController = rememberNavController()

    var showDigitalEffects by remember { mutableStateOf(true) }
    var command by remember { mutableStateOf("") }

    // Overlay state management
    var showSidebar by remember { mutableStateOf(false) }
    var showChatBubble by remember { mutableStateOf(true) }
    val density = LocalDensity.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .let { base ->
                if (showDigitalEffects) {
                    base.digitalPixelEffect()
                } else {
                    base
                }
            }
    ) {
        AppNavGraph(navController = navController)

        // Edge trigger zone for sidebar (left edge swipe)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(999f)
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragStart = { offset ->
                            // If drag starts from left edge (within 30dp), open sidebar
                            if (offset.x < with(density) { 30.dp.toPx() }) {
                                showSidebar = true
                            }
                        }
                    )
                }
        )

        // Overlay system - Always present, system-wide
        Box(modifier = Modifier.fillMaxSize().zIndex(1000f)) {
            // Aura Presence Overlay - Always visible, bottom-right
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .zIndex(1001f)
            ) {
                AuraPresenceOverlay(
                    onSuggestClicked = { suggestion ->
                        // Navigate to relevant screen based on suggestion
                        when {
                            suggestion.contains("theme") -> navController.navigate(GenesisRoutes.THEME_ENGINE)
                            suggestion.contains("firewall") -> navController.navigate(GenesisRoutes.FIREWALL)
                            suggestion.contains("canvas") -> navController.navigate(GenesisRoutes.COLLAB_CANVAS)
                            else -> navController.navigate(GenesisRoutes.DIRECT_CHAT)
                        }
                    }
                )
            }

            // Chat Bubble Menu - Floating, draggable
            if (showChatBubble) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                        .zIndex(1002f)
                ) {
                    ChatBubbleMenu(
                        onOpenChat = {
                            navController.navigate(GenesisRoutes.DIRECT_CHAT)
                        },
                        onToggleVoice = {
                            // TODO: Implement voice toggle
                        }
                    )
                }
            }

            // Agent Sidebar Menu - Slide out from left
            AgentSidebarMenu(
                isVisible = showSidebar,
                onDismiss = { showSidebar = false },
                onAgentAction = { agentName, action ->
                    when (action) {
                        "voice" -> navController.navigate(GenesisRoutes.DIRECT_CHAT)
                        "connect" -> navController.navigate(GenesisRoutes.CONFERENCE_ROOM)
                        "assign" -> navController.navigate(GenesisRoutes.TASK_ASSIGNMENT)
                        "design" -> navController.navigate(GenesisRoutes.AURAS_LAB)
                        "create" -> navController.navigate(GenesisRoutes.APP_BUILDER)
                        else -> {}
                    }
                    showSidebar = false
                }
            )
        }
    }
}

// Keep original API used by Activity: delegate to the content with the real ViewModel
@Composable
internal fun MainScreen(
    themeViewModel: ThemeViewModel
) {
    MainScreenContent(processThemeCommand = { themeViewModel.processThemeCommand(it) })
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    AuraFrameFXTheme {
        // For preview, use a no-op lambda for the theme command handler
        MainScreenContent(processThemeCommand = { /* no-op in preview */ })
    }
}

// Extension function placeholder - this should be implemented elsewhere
fun Modifier.digitalPixelEffect(): Modifier = this
