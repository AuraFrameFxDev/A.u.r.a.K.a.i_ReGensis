package dev.aurakai.auraframefx.ui.gates

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * PURE GATE CAROUSEL - Full screen, swipeable, double-tap to enter
 * No bottom nav, no text, just gates
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GateNavigationScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val allGates = GateConfigs.allGates
    val pagerState = rememberPagerState(pageCount = { allGates.size })
    val scope = rememberCoroutineScope()
    var isTransitioning by remember { mutableStateOf(false) }
    var lastTapTime by remember { mutableLongStateOf(0L) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .pointerInput(pagerState) {
                // ALLOW SWIPING + DOUBLE-TAP
                awaitEachGesture {
                    val downEvent = awaitPointerEvent(PointerEventPass.Initial)
                    downEvent.changes.first()
                    val downTime = System.currentTimeMillis()

                    // Wait for release or cancellation
                    val upEvent = waitForUpOrCancellation()

                    if (upEvent != null) {
                        val upTime = System.currentTimeMillis()
                        val timeDiff = upTime - downTime

                        // Quick tap (not a long press) and very short duration = potential double tap
                        if (timeDiff < 300) {
                            val currentTime = System.currentTimeMillis()
                            val doubleTapWindow = currentTime - lastTapTime

                            if (doubleTapWindow < 300) {
                                // DOUBLE TAP DETECTED!
                                val config = allGates[pagerState.currentPage]

                                if (!config.comingSoon && !isTransitioning) {
                                    isTransitioning = true
                                    scope.launch {
                                        try {
                                            navController.navigate(config.route) {
                                                launchSingleTop = true
                                            }
                                        } catch (e: Exception) {
                                            Timber.tag("GateNav").e(e, "Navigation failed: ${config.route}")
                                        } finally {
                                            isTransitioning = false
                                        }
                                    }
                                }
                            }
                            lastTapTime = currentTime
                        }
                    }
                }
            }
    ) {
        // PURE PAGER - FULL SCREEN, SWIPEABLE
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            pageSpacing = 0.dp
        ) { page ->
            if (page < allGates.size) {
                val config = allGates[page]
                GateCard(
                    config = config,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}

@Composable
private fun PagerScope.GateCard(
    config: GateConfig,
    modifier: Modifier
) {
    GateCard(
        config = config,
        modifier = modifier,
        onDoubleTap = {} // Double-tap is handled at parent level
    )
}
