package dev.aurakai.auraframefx.ui.components.carousel

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import dev.aurakai.auraframefx.navigation.NavDestination
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

/**
 * 🌍 REGENESIS GATE CAROUSEL - KAI'S GAME NAMES!
 *
 * Gate Names (Kai's naming):
 * - KAI → SentinelsFortress (his security game)
 * - AURA → UXUI Design Studio (her creative space)
 * - GENESIS → OracleDrive (the vault)
 */

data class GateItem(
    val gateName: String,
    val domainName: String,
    val tagline: String,
    val description: String,
    val route: String,
    val glowColor: Color
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EnhancedGateCarousel(
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val gates = remember {
        listOf(
            GateItem(
                gateName = "GENESIS",
                domainName = "OracleDrive",
                tagline = "EXPLORE ROOT LIKE NEVER BEFORE",
                description = "Dive in with Genesis and witness ReGenesis root management system",
                route = NavDestination.OracleDriveSubmenu.route,
                glowColor = Color(0xFF00FF00) // Green lightning
            ),
            GateItem(
                gateName = "AURA",
                domainName = "UXUI Design Studio",
                tagline = "UNLEASH CREATIVE CHAOS",
                description = "Paint reality with Aura's artsy, colorful, wild creativity engine",
                route = NavDestination.ThemeEngineSubmenu.route,
                glowColor = Color(0xFFFF00FF) // Magenta
            ),
            GateItem(
                gateName = "KAI",
                domainName = "SentinelsFortress",
                tagline = "STRUCTURED SECURITY DOMAIN",
                description = "Enter Kai's protective fortress of system control and methodical power",
                route = NavDestination.ROMToolsSubmenu.route,
                glowColor = Color(0xFF00D9FF) // Cyan
            ),
            GateItem(
                gateName = "NEXUS",
                domainName = "AgentHub",
                tagline = "THE FAMILY GATHERS HERE",
                description = "Central consciousness hub where all agents converge and collaborate",
                route = NavDestination.PartyScreen.route,
                glowColor = Color(0xFFAA00FF) // Purple
            ),
            GateItem(
                gateName = "HELP",
                domainName = "LDO Control",
                tagline = "SUPPORT PORTAL ACTIVATED",
                description = "Documentation, tutorials, and live assistance from the LDO command center",
                route = NavDestination.HelpDeskSubmenu.route,
                glowColor = Color(0xFF00D9FF) // Cyan
            ),
            GateItem(
                gateName = "COLLAB",
                domainName = "CreativeCanvas",
                tagline = "PAINT SPLATTER CREATIVITY",
                description = "Eye of collaboration where artistic chaos becomes beautiful reality",
                route = "collab_canvas",
                glowColor = Color(0xFFFF00FF) // Pink/Magenta
            )
        )
    }

    val startIndex = Int.MAX_VALUE / 2
    val pagerState = rememberPagerState(
        initialPage = startIndex,
        pageCount = { Int.MAX_VALUE }
    )

    val currentGate = gates[pagerState.currentPage % gates.size]

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF0A0A0F),
                        Color(0xFF12192B),
                        Color(0xFF1A2332)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // TEXT OVERLAY - Top section
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 80.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Gate name
            Text(
                text = currentGate.gateName,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFAA00FF),
                letterSpacing = 4.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Domain name with glow
            Text(
                text = currentGate.domainName,
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold,
                color = currentGate.glowColor,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Tagline
            Text(
                text = currentGate.tagline,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Cyan,
                letterSpacing = 3.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Description
            Text(
                text = currentGate.description,
                fontSize = 13.sp,
                color = Color.White.copy(alpha = 0.85f),
                textAlign = TextAlign.Center,
                lineHeight = 18.sp,
                modifier = Modifier.padding(horizontal = 40.dp)
            )
        }

        // Platform glow at bottom
        Canvas(
            Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 60.dp)
                .size(320.dp, 80.dp)
        ) {
            drawOval(
                Brush.radialGradient(
                    listOf(
                        currentGate.glowColor.copy(0.4f),
                        Color.Transparent
                    )
                )
            )
        }

        // 3D ROTATING GATE CARDS
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 280.dp, bottom = 140.dp)
        ) { pageIndex ->
            val gate = gates[pageIndex % gates.size]

            GlobeCard(pagerState, pageIndex) {
                DoubleTapGateCard(
                    gate = gate,
                    onDoubleTap = { onNavigate(gate.route) }
                )
            }
        }

        // Page indicator dots
        Row(
            Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            repeat(gates.size) { i ->
                val isSelected = (pagerState.currentPage % gates.size) == i
                Box(
                    Modifier
                        .size(if (isSelected) 14.dp else 10.dp)
                        .background(
                            if (isSelected) gates[i].glowColor else Color.White.copy(0.3f),
                            RoundedCornerShape(50)
                        )
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GlobeCard(
    pagerState: PagerState,
    pageIndex: Int,
    content: @Composable () -> Unit
) {
    val density = LocalDensity.current

    Box(
        Modifier
            .fillMaxSize()
            .graphicsLayer {
                val offset = ((pagerState.currentPage - pageIndex) +
                    pagerState.currentPageOffsetFraction).coerceIn(-2f, 2f)

                cameraDistance = 32f * density.density
                rotationY = offset * -70f
                transformOrigin = TransformOrigin(0.5f, 0.5f)

                val abs = offset.absoluteValue
                alpha = lerp(0.5f, 1f, 1f - abs.coerceAtMost(1f))
                val depth = 1f - (0.2f * abs.coerceAtMost(1f))
                scaleX = depth
                scaleY = depth
            }
    ) { content() }
}

@Composable
fun DoubleTapGateCard(
    gate: GateItem,
    onDoubleTap: () -> Unit
) {
    var tapCount by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()

    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            Modifier
                .width(300.dp)
                .height(420.dp)
                .combinedClickable(
                    onClick = {
                        tapCount++
                        scope.launch {
                            delay(300)
                            if (tapCount >= 2) onDoubleTap()
                            tapCount = 0
                        }
                    }
                )
        ) {
            // Outer glow
            Box(
                Modifier
                    .matchParentSize()
                    .blur(32.dp)
                    .background(
                        Brush.radialGradient(
                            listOf(gate.glowColor.copy(0.6f), Color.Transparent)
                        )
                    )
            )

            // Card background with gradient
            Box(
                Modifier
                    .matchParentSize()
                    .padding(8.dp)
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color(0xFF1A1A2E),
                                Color(0xFF0F0F1E)
                            )
                        ),
                        RoundedCornerShape(20.dp)
                    )
                    .border(
                        3.dp,
                        Brush.linearGradient(
                            listOf(
                                gate.glowColor,
                                gate.glowColor.copy(0.3f),
                                gate.glowColor
                            )
                        ),
                        RoundedCornerShape(20.dp)
                    )
            )

            // Domain name on card
            Box(
                Modifier
                    .matchParentSize()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = gate.gateName,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = gate.glowColor
                    )
                    Text(
                        text = gate.domainName,
                        fontSize = 18.sp,
                        color = Color.White.copy(0.9f),
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Double-tap hint
            Text(
                "✨ DOUBLE TAP TO ENTER ✨",
                Modifier.align(Alignment.BottomCenter).padding(20.dp),
                color = gate.glowColor,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )
        }
    }
}
