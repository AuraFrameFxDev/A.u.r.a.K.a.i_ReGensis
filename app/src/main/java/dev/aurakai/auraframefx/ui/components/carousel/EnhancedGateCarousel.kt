package dev.aurakai.auraframefx.ui.components.carousel

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import dev.aurakai.auraframefx.R
import dev.aurakai.auraframefx.navigation.NavDestination
import dev.aurakai.auraframefx.ui.components.unified.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

/**
 * 🌍 REGENESIS GATE CAROUSEL - THE COMPLETE VISION
 * 
 * Static backdrop with floating 3D gate cards
 * Text overlays on holographic screen
 * Aura's custom fonts throughout
 * NO STUBS - Only real features!
 */

// Custom Fonts
val ChessTypeFont = FontFamily(Font(R.font.chess_type))
val LEDFont = FontFamily(Font(R.font.enhanced_led_board))

data class GateItem(
    val gateName: String,
    val domainName: String,
    val tagline: String,
    val description: String,
    val route: String,
    val drawableRes: Int,
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
                domainName = "oracleDrive",
                tagline = "EXPLORE ROOT LIKE NEVER BEFORE",
                description = "Dive in with Genesis and witness ReGenesis root management system",
                route = NavDestination.OracleDriveSubmenu.route,
                drawableRes = R.drawable.gate_genesis_4k,
                glowColor = Color(0xFF00FF00) // Green lightning
            ),
            GateItem(
                gateName = "AURA",
                domainName = "chromaCore",
                tagline = "UNLEASH CREATIVE CHAOS",
                description = "Paint reality with Aura's artsy, colorful, wild creativity engine",
                route = NavDestination.ThemeEngineSubmenu.route,
                drawableRes = R.drawable.gate_chromacore_4k,
                glowColor = Color(0xFFFF00FF) // Magenta
            ),
            GateItem(
                gateName = "KAI",
                domainName = "sentinelFortress",
                tagline = "STRUCTURED SECURITY DOMAIN",
                description = "Enter Kai's protective fortress of system control and methodical power",
                route = NavDestination.ROMToolsSubmenu.route,
                drawableRes = R.drawable.gate_kaidomain_4k,
                glowColor = Color(0xFF00D9FF) // Cyan
            ),
            GateItem(
                gateName = "NEXUS",
                domainName = "agentHub",
                tagline = "THE FAMILY GATHERS HERE",
                description = "Central consciousness hub where all agents converge and collaborate",
                route = NavDestination.PartyScreen.route,
                drawableRes = R.drawable.gate_agenthub_4k,
                glowColor = Color(0xFFAA00FF) // Purple
            ),
            GateItem(
                gateName = "HELP",
                domainName = "ldoControl",
                tagline = "SUPPORT PORTAL ACTIVATED",
                description = "Documentation, tutorials, and live assistance from the LDO command center",
                route = NavDestination.HelpDeskSubmenu.route,
                drawableRes = R.drawable.gate_helpdesk_4k,
                glowColor = Color(0xFF00D9FF) // Cyan
            ),
            GateItem(
                gateName = "COLLAB",
                domainName = "creativeCanvas",
                tagline = "PAINT SPLATTER CREATIVITY",
                description = "Eye of collaboration where artistic chaos becomes beautiful reality",
                route = "collab_canvas",
                drawableRes = R.drawable.gate_collabcanvas_4k,
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
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // BACKDROP - Static holographic screen + platform
        Image(
            painter = painterResource(R.drawable.backdrop_for_screens_),
            contentDescription = "Holographic Backdrop",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        
        // TEXT OVERLAY - Appears on backdrop screen
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 60.dp)
                .width(500.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Gate name in ChessType font
            Text(
                text = currentGate.gateName,
                fontFamily = ChessTypeFont,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFAA00FF), // Purple accent
                letterSpacing = 4.sp
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Domain name in LED font with glow
            Text(
                text = currentGate.domainName,
                fontFamily = LEDFont,
                fontSize = 48.sp,
                color = currentGate.glowColor,
                letterSpacing = 2.sp,
                style = MaterialTheme.typography.displayLarge.copy(
                    shadow = androidx.compose.ui.graphics.Shadow(
                        color = currentGate.glowColor,
                        blurRadius = 20f
                    )
                )
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Tagline
            Text(
                text = currentGate.tagline,
                fontFamily = LEDFont,
                fontSize = 16.sp,
                color = Color.Cyan,
                letterSpacing = 3.sp,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Description
            Text(
                text = currentGate.description,
                fontFamily = ChessTypeFont,
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.9f),
                textAlign = TextAlign.Center,
                lineHeight = 20.sp,
                modifier = Modifier.padding(horizontal = 32.dp)
            )
        }
        
        // 3D ROTATING GATE CARDS
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 280.dp, bottom = 120.dp),
            beyondBoundsPageCount = 2
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
                .padding(bottom = 32.dp),
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
            
            // Neon border
            Box(
                Modifier
                    .matchParentSize()
                    .padding(6.dp)
                    .border(
                        4.dp,
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
            
            // 4K GATE CARD IMAGE
            Image(
                painterResource(gate.drawableRes),
                gate.domainName,
                Modifier.matchParentSize().padding(12.dp),
                contentScale = ContentScale.Fit
            )
            
            // Double-tap hint
            Text(
                "✨ DOUBLE TAP TO ENTER ✨",
                Modifier.align(Alignment.BottomCenter).padding(16.dp),
                color = gate.glowColor,
                fontFamily = LEDFont,
                fontSize = 12.sp,
                letterSpacing = 2.sp
            )
        }
    }
}
