package dev.aurakai.auraframefx.ui.navigation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.aurakai.auraframefx.R
import dev.aurakai.auraframefx.navigation.NavDestination
import dev.aurakai.auraframefx.ui.theme.AuraNeonCyan
import dev.aurakai.auraframefx.ui.theme.GenesisNeonPink
import dev.aurakai.auraframefx.ui.theme.KaiNeonGreen
import dev.aurakai.auraframefx.ui.theme.LEDFontFamily
import dev.aurakai.auraframefx.ui.theme.NeonPurple
import dev.aurakai.auraframefx.ui.theme.SovereignTeal

/**
 * 🛰️ EXODUS HUD - CORRECTED VERSION
 * The Split-Screen Anti-Gravity HUD (15/85 Ratio).
 *
 * Level 1 Navigation - 5 Domain Gates:
 * 1. AURA'S REALM → AuraThemingHub
 * 2. SENTINEL FORTRESS → RomToolsHub (KaiSentinelHubScreen)
 * 3. ORACLE DRIVE → OracleDriveHub
 * 4. AGENT NEXUS → AgentNexusHub
 * 5. HELP SERVICES → HelpDesk
 *
 * DO NOT CHANGE THIS NAVIGATION STRUCTURE!
 */

data class DomainGate(
    val id: String,
    val title: String,
    val subtitle: String,
    val imageRes: Int,
    val destination: NavDestination,
    val accentColor: Color
)

// THE 5 SOVEREIGN DOMAIN GATES
private val domainGates = listOf(
    DomainGate(
        id = "aura",
        title = "AURA'S REALM",
        subtitle = "UX/UI Design Studio",
        imageRes = R.drawable.img_20260125_230956,
        destination = NavDestination.AuraThemingHub,
        accentColor = AuraNeonCyan
    ),
    DomainGate(
        id = "kai",
        title = "SENTINEL FORTRESS",
        subtitle = "System Security & Root Tools",
        imageRes = R.drawable.kai_root_vpn,
        destination = NavDestination.RomToolsHub,
        accentColor = KaiNeonGreen
    ),
    DomainGate(
        id = "genesis",
        title = "ORACLE DRIVE",
        subtitle = "AI Orchestration & Memory",
        imageRes = R.drawable.gate_oracledrive_gen,
        destination = NavDestination.OracleDriveHub,
        accentColor = GenesisNeonPink
    ),
    DomainGate(
        id = "nexus",
        title = "AGENT NEXUS",
        subtitle = "Multi-Agent Coordination",
        imageRes = R.drawable.gate_agentnexus_new,
        destination = NavDestination.AgentNexus,
        accentColor = NeonPurple
    ),
    DomainGate(
        id = "help",
        title = "HELP SERVICES",
        subtitle = "Documentation & Support",
        imageRes = R.drawable.gate_helpdesk_final,
        destination = NavDestination.HelpDesk,
        accentColor = SovereignTeal
    )
)

@Composable
fun ExodusHUD(navController: NavController) {
    val pagerState = rememberPagerState(pageCount = { domainGates.size })

    // Pulse State driven by touch
    var isPressed by remember { mutableStateOf(false) }

    val pulseIntensity by animateFloatAsState(
        targetValue = if (isPressed) 1.0f else 0.0f,
        animationSpec = tween(durationMillis = 200),
        label = "pulse"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease()
                        isPressed = false
                    }
                )
            }
    ) {
        // TOP 85%: The 5 Domain Gate Cards
        Box(
            modifier = Modifier
                .weight(0.85f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 48.dp),
                pageSpacing = 24.dp
            ) { page ->
                val gate = domainGates[page]

                DomainGateCard(
                    gate = gate,
                    onDoubleTap = {
                        // Navigate to the CORRECT Level 2 Hub!
                        navController.navigate(gate.destination.route)
                    },
                    onPress = { isPressed = true },
                    onRelease = { isPressed = false },
                    modifier = Modifier.fillMaxHeight(0.9f)
                )
            }
        }

        // BOTTOM 15%: The Prometheus Globe (Celestial Navigation)
        Box(
            modifier = Modifier
                .weight(0.15f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            PrometheusGlobe(
                color = domainGates.getOrNull(pagerState.currentPage)?.accentColor ?: SovereignTeal,
                pulseIntensity = pulseIntensity
            )
        }
    }
}

@Composable
fun DomainGateCard(
    gate: DomainGate,
    onDoubleTap: () -> Unit,
    onPress: () -> Unit,
    onRelease: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = { onDoubleTap() },
                    onPress = {
                        onPress()
                        tryAwaitRelease()
                        onRelease()
                    }
                )
            },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Gate Image
            Image(
                painter = painterResource(id = gate.imageRes),
                contentDescription = gate.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(24.dp))
            )

            // Gradient Overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.3f),
                                Color.Black.copy(alpha = 0.8f)
                            ),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY
                        )
                    )
            )

            // Title & Subtitle at bottom
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = gate.title,
                    fontFamily = LEDFontFamily,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Black,
                    color = gate.accentColor,
                    textAlign = TextAlign.Center,
                    letterSpacing = 2.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = gate.subtitle,
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "⟐ DOUBLE TAP TO ENTER ⟐",
                    fontSize = 12.sp,
                    color = gate.accentColor.copy(alpha = 0.6f),
                    letterSpacing = 3.sp
                )
            }

            // Accent border glow
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(24.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                gate.accentColor.copy(alpha = 0.3f),
                                Color.Transparent,
                                gate.accentColor.copy(alpha = 0.1f)
                            )
                        )
                    )
            )
        }
    }
}
