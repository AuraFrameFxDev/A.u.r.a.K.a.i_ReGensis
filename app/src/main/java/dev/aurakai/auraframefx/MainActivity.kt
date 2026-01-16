package dev.aurakai.auraframefx

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.aurakai.auraframefx.navigation.AppNavGraph
import dev.aurakai.auraframefx.navigation.NavDestination
import dev.aurakai.auraframefx.ui.overlays.AgentSidebarMenu
import dev.aurakai.auraframefx.ui.overlays.AuraPresenceOverlay
import dev.aurakai.auraframefx.ui.overlays.ChatBubbleMenu
import dev.aurakai.auraframefx.ui.theme.AuraFrameFXTheme
import dev.aurakai.auraframefx.ui.theme.ThemeViewModel
import kotlin.random.Random

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable edge-to-edge display and fullscreen immersive mode
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
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.apply {
            hide(WindowInsetsCompat.Type.statusBars())
            hide(WindowInsetsCompat.Type.navigationBars())
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

    var showDigitalEffects by remember { mutableStateOf(true) }

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

        // Left edge swipe trigger zone for sidebar
        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(999f)
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragStart = { offset ->
                            // Open sidebar if drag starts from left edge (within 30dp)
                            if (offset.x < with(density) { 30.dp.toPx() }) {
                                showSidebar = true
                            }
                        },
                        onHorizontalDrag = { change, dragAmount ->
                            // Optional: allow dragging sidebar out further
                            change.consume()
                        }
                    )
                }
        )

        // Overlay system – system-wide floating elements
        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(1000f)
        ) {

            // Aura Presence Overlay – always visible, bottom-right
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .zIndex(1001f)
            ) {
                AuraPresenceOverlay(
                    onSuggestClicked = { suggestion ->
                        when {
                            suggestion.contains("theme", ignoreCase = true) ->
                                navController.navigate(NavDestination.ThemeEngine.route)

                            suggestion.contains("firewall", ignoreCase = true) ->
                                navController.navigate(NavDestination.SystemOverrides.route)

                            suggestion.contains("canvas", ignoreCase = true) ->
                                navController.navigate(NavDestination.Canvas.route)

                            else ->
                                navController.navigate(NavDestination.DirectChat.route)
                        }
                    }
                )
            }

            // Chat Bubble Menu – floating, draggable
            if (showChatBubble) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                        .zIndex(1002f)
                ) {
                    ChatBubbleMenu(
                        onOpenChat = { navController.navigate(NavDestination.DirectChat.route) },
                        onToggleVoice = {
                            // TODO: Implement voice toggle (e.g., trigger Neural Whisper)
                        }
                    )
                }
            }

            // Agent Sidebar Menu – slide out from left
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

/**
 * Improved digital pixel/glitch effect modifier.
 * Creates a subtle animated noise overlay that gives a "reactive intelligence" / matrix-like feel.
 * Lightweight and performant.
 */
@Composable
fun Modifier.digitalPixelEffect(): Modifier = composed {
    val infiniteTransition = rememberInfiniteTransition(label = "pixelNoise")
    val noiseOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "noiseOffset"
    )

    this.then(
        Modifier.drawBehind {
            drawDigitalNoise(noiseOffset)
        }
    )
}

private fun DrawScope.drawDigitalNoise(offset: Float) {
    val random = Random(offset.toInt())
    val cellSize = 4f // Smaller = more dense glitch
    val rows = (size.height / cellSize).toInt()
    val cols = (size.width / cellSize).toInt()

    for (row in 0..rows) {
        for (col in 0..cols) {
            val x = col * cellSize
            val y = row * cellSize
            val alpha = random.nextFloat() * 0.08f + 0.02f // subtle noise

            drawRect(
                color = Color.White.copy(alpha = alpha),
                topLeft = Offset(x + random.nextFloat() * 2f, y + random.nextFloat() * 2f),
                size = androidx.compose.ui.geometry.Size(cellSize, cellSize)
            )
        }
    }
}

// Preview helper
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
        MainScreenContent(processThemeCommand = { /* no-op in preview */ })
    }
}
