package dev.aurakai.auraframefx

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.aurakai.auraframefx.navigation.AppNavGraph
import dev.aurakai.auraframefx.navigation.NavDestination
import dev.aurakai.auraframefx.ui.overlays.AgentSidebarMenu
import dev.aurakai.auraframefx.ui.overlays.AuraPresenceOverlay
import dev.aurakai.auraframefx.ui.overlays.ChatBubbleMenu
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
                            suggestion.contains("theme") -> navController.navigate(NavDestination.ThemeEngine.route)
                            suggestion.contains("firewall") -> navController.navigate(NavDestination.SystemOverrides.route)
                            suggestion.contains("canvas") -> navController.navigate(NavDestination.Canvas.route)
                            else -> navController.navigate(NavDestination.DirectChat.route)
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
                            navController.navigate(NavDestination.DirectChat.route)
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
                        "voice" -> navController.navigate(NavDestination.DirectChat.route)
                        "connect" -> navController.navigate(NavDestination.CONFERENCE_ROOM)
                        "assign" -> navController.navigate(NavDestination.TaskAssignment.route)
                        "design" -> navController.navigate(NavDestination.AurasLab.route)
                        "create" -> navController.navigate(NavDestination.ModuleCreation.route)
                        else -> {}
                    }
                    showSidebar = false
                }
            )
        }
    }
}

@Composable
internal fun MainScreen(
    themeViewModel: ThemeViewModel
) {
    MainScreenContent(processThemeCommand = { themeViewModel.processThemeCommand(it) })
}

/**
 * Renders a preview of the main app screen inside AuraFrameFXTheme using a no-op theme command handler.
 *
 * Intended for IDE previews; it composes MainScreenContent with a placeholder lambda so the preview
 * can display the screen without requiring a real ThemeViewModel.
 */
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    AuraFrameFXTheme {
        // For preview, use a no-op lambda for the theme command handler
        MainScreenContent(processThemeCommand = { /* no-op in preview */ })
    }
}

fun Modifier.digitalPixelEffect(): Modifier = this
