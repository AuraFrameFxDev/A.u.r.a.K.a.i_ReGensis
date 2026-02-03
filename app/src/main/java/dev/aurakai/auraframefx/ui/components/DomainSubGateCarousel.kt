package dev.aurakai.auraframefx.ui.components

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import dev.aurakai.auraframefx.navigation.NavDestination
import dev.aurakai.auraframefx.ui.theme.LEDFontFamily
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

/**
 * 🎠 DOMAIN SUB-GATE CAROUSEL
 *
 * Level 2 Navigation - Horizontal carousel inside each domain hub.
 * Like Namco Arcade PS1 game selection!
 *
 * DUAL STYLE SYSTEM:
 * Each domain has TWO visual styles (A and B) that can be toggled.
 * See GateAssetConfig for style definitions.
 */

data class SubGateCard(
    val id: String,
    val title: String,
    val subtitle: String,
    val styleADrawable: String,      // Style A image name
    val styleBDrawable: String,      // Style B image name
    val fallbackDrawable: String?,   // Legacy fallback
    val route: String,
    val accentColor: Color
)

@Composable
fun DomainSubGateCarousel(
    subGates: List<SubGateCard>,
    onGateSelected: (SubGateCard) -> Unit,
    useStyleB: Boolean = false,
    modifier: Modifier = Modifier,
    cardHeight: Dp = 200.dp,
    domainColor: Color = Color.Cyan
) {
    val context = LocalContext.current
    val pagerState = rememberPagerState(pageCount = { subGates.size })
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 1. Carousel
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(cardHeight),
            contentPadding = PaddingValues(horizontal = 48.dp),
            pageSpacing = 16.dp
        ) { page ->
            val gate = subGates[page]
            val pageOffset = ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue

            val scale = lerp(0.85f, 1f, 1f - pageOffset.coerceIn(0f, 1f))
            val alpha = lerp(0.5f, 1f, 1f - pageOffset.coerceIn(0f, 1f))

            SubGateCardView(
                gate = gate,
                context = context,
                useStyleB = useStyleB,
                onClick = { onGateSelected(gate) },
                modifier = Modifier
                    .fillMaxHeight()
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        this.alpha = alpha
                    }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 2. Joystick Rotation Orb
        PrometheusGlobe(
            color = domainColor,
            pulseIntensity = 1.2f,
            modifier = Modifier.size(80.dp),
            onDrag = { dragAmount ->
                scope.launch {
                    // Smooth joystick-like scroll
                    pagerState.scrollBy(-dragAmount)
                }
            },
            onTap = {
                val currentGate = subGates.getOrNull(pagerState.currentPage)
                currentGate?.let { onGateSelected(it) }
            }
        )
    }
}

@Composable
private fun SubGateCardView(
    gate: SubGateCard,
    context: Context,
    useStyleB: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Choose drawable based on style mode
    val primaryDrawable = if (useStyleB) gate.styleBDrawable else gate.styleADrawable

    val drawableId = remember(primaryDrawable, gate.fallbackDrawable) {
        var id = context.resources.getIdentifier(
            primaryDrawable, "drawable", context.packageName
        )
        if (id == 0 && gate.fallbackDrawable != null) {
            id = context.resources.getIdentifier(
                gate.fallbackDrawable, "drawable", context.packageName
            )
        }
        id
    }

    Card(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { onClick() },
                    onDoubleTap = { onClick() } // Requirements specified double tap to enter
                )
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.Black.copy(alpha = 0.5f)
        ),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, gate.accentColor.copy(alpha = 0.3f))
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // 1. Title Section (TOP)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(gate.accentColor.copy(alpha = 0.1f))
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = gate.title.uppercase(),
                    fontFamily = LEDFontFamily,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = gate.accentColor,
                    textAlign = TextAlign.Center
                )
            }

            // 2. Scene/Gate Image (MIDDLE)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(4.dp))
                    .padding(4.dp),
                contentAlignment = Alignment.Center
            ) {
                if (drawableId != 0) {
                    Image(
                        painter = painterResource(id = drawableId),
                        contentDescription = gate.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(8.dp))
                    )
                } else {
                    // Fallback
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.verticalGradient(
                                    listOf(gate.accentColor.copy(alpha = 0.2f), Color.Transparent)
                                )
                            )
                    )
                }
            }

            // 3. Description/Subtitle (BOTTOM)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = gate.subtitle,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center,
                    maxLines = 2
                )
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════
// SUB-GATE LISTS FOR EACH DOMAIN (with dual-style support)
// ═══════════════════════════════════════════════════════════════════════════

/**
 * 🎨 AURA'S LEVEL 2 SUB-GATES (UXUI Design Studio Interior)
 *
 * COMPLETE ARSENAL:
 * - AurasLab (SandboxUI) - Testing & Experimentation
 * - CollabCanvas - Multi-Agent Design Collaboration
 * - ChromaCore - ColorBlendr Integration (Material You)
 * - Theme Engine - Iconify Integration (69+ settings)
 * - UX/UI Engine - 1440+ Customizations Hub
 * - Monet Engine - Material You Color Generation
 * - Iconify - 250,000+ Icons
 *
 * Style A: "CollabCanvas" - Paint splashes, neon drips, artistic
 * Style B: "Clean Studio" - Sleek gradients, minimalist
 *
 * @return A list of `SubGateCard` instances representing Aura's sub-gates.
 */
fun getAuraSubGates() = listOf(
    // 🧪 AURA'S LAB - SandboxUI (Testing & Experimentation)
    SubGateCard(
        id = "aura_lab",
        title = "Aura's Lab",
        subtitle = "SandboxUI - Testing & Experimentation",
        styleADrawable = "gatescenes_aura_auralab",           // NEW! High Fidelity
        styleBDrawable = "gatescenes_aura_lab_alt",           // Alt style
        fallbackDrawable = "gate_auralab_final",
        route = NavDestination.AuraLab.route,
        accentColor = Color(0xFF00E5FF)
    ),
    // 🎨 COLLAB CANVAS - Multi-Agent Design Collaboration
    SubGateCard(
        id = "aura_collab",
        title = "CollabCanvas",
        subtitle = "Multi-Agent Design Collaboration",
        styleADrawable = "gatescenes_aura_collabcanvas_v2",   // NEW! Group splash
        styleBDrawable = "gatescenes_aura_collabcanvas",      // Original
        fallbackDrawable = "card_collab_canvas",
        route = NavDestination.CollabCanvas.route,
        accentColor = Color(0xFFB026FF)
    ),
    // 🌈 CHROMA CORE - ColorBlendr (Material You Colors)
    // Contains: Monet Engine integration
    SubGateCard(
        id = "aura_chroma",
        title = "ChromaCore",
        subtitle = "ColorBlendr + Monet Engine",
        styleADrawable = "gatescenes_aura_chromacoregate",    // NEW! High Fidelity
        styleBDrawable = "gatescenes_aura_chromacore_alt",    // Alt style
        fallbackDrawable = "card_chroma_core",
        route = NavDestination.ColorBlendr.route,
        accentColor = Color(0xFFB026FF)
    ),
    // 🎨 MONET ENGINE - Material You Color Generation (Attached to ChromaCore)
    SubGateCard(
        id = "aura_monet",
        title = "Monet Engine",
        subtitle = "Material You Color Generation",
        styleADrawable = "gatescenes_aura_chromacore_alt",    // Chromacore variant
        styleBDrawable = "gatescenes_aura_chromacoregate",    // Main chromacore
        fallbackDrawable = "card_chroma_core",
        route = NavDestination.ColorBlendrMonet.route,
        accentColor = Color(0xFF00FFD4)
    ),
    // 🎭 THEME ENGINE - Iconify Integration (69+ Settings)
    SubGateCard(
        id = "aura_theme",
        title = "Theme Engine",
        subtitle = "Iconify - 69+ Visual Settings",
        styleADrawable = "gatescenes_aura_designstudio_v2",   // Design Studio scene
        styleBDrawable = "gatescenes_aura_designstudio",      // Original
        fallbackDrawable = "gate_themeengine_final",
        route = NavDestination.IconifyPicker.route,
        accentColor = Color(0xFFFF00FF)
    ),
    // ⚙️ UX/UI ENGINE - 1440+ Customizations Hub
    // Iconify/ColorBlendr/PixelLauncher integration
    SubGateCard(
        id = "aura_uxui",
        title = "UX/UI Engine",
        subtitle = "1440+ System Customizations",
        styleADrawable = "gatescenes_aura_terminal",          // Terminal/Control scene
        styleBDrawable = "gatescenes_aura_designstudio",      // Fallback
        fallbackDrawable = null,
        route = NavDestination.ReGenesisCustomization.route,
        accentColor = Color(0xFF00E5FF)
    ),
    // 🎯 ICONIFY - 250,000+ Icons
    SubGateCard(
        id = "aura_iconify",
        title = "Iconify",
        subtitle = "250,000+ Premium Icons",
        styleADrawable = "gatescenes_aura_designstudio",      // Design scene
        styleBDrawable = "gatescenes_aura_terminal",          // Terminal fallback
        fallbackDrawable = null,
        route = NavDestination.IconifyIconPacks.route,
        accentColor = Color(0xFFFFCC00)
    ),
    // 🖥️ PIXEL LAUNCHER - PixelLauncherEnhanced (29 Settings)
    SubGateCard(
        id = "aura_pixel",
        title = "Pixel Launcher",
        subtitle = "PixelLauncherEnhanced - 29+ Settings",
        styleADrawable = "gatescenes_aura_collabcanvas",      // Collab scene
        styleBDrawable = "gatescenes_aura_lab_alt",           // Lab variant
        fallbackDrawable = null,
        route = NavDestination.PixelLauncherEnhanced.route,
        accentColor = Color(0xFF00FF85)
    )
)

/**
 * 🛡️ KAI'S LEVEL 2 SUB-GATES (Sentinel Fortress Interior)
 *
 * COMPLETE ARSENAL:
 * - ROM Flasher - Partition Management
 * - Bootloader - Lock/Unlock & Fastboot
 * - Module Manager - Magisk & LSPosed
 * - Root Tools - System Modifications
 * - Security Center - Scan/Clean System
 * - VPN & Ad-Block - Network Security
 * - LSPosed Hub - Hook Framework
 *
 * Style A: "Hexagon Shields" - Hexagonal fortress patterns, armored shields
 * Style B: "Cyber Fortress" - Matrix code rain, lightning, red neon frames
 */
fun getKaiSubGates() = listOf(
    // 📀 ROM FLASHER - Partition Management
    SubGateCard(
        id = "kai_rom",
        title = "ROM Flasher",
        subtitle = "Partition Management & Flashing",
        styleADrawable = "gatescenes_kai_romtools",           // NEW! User uploaded
        styleBDrawable = "gatescenes_kai_sentinelsfortress",  // Fortress scene
        fallbackDrawable = "gate_romtools_final",
        route = NavDestination.ROMFlasher.route,
        accentColor = Color(0xFFFF3366)
    ),
    // 🔓 BOOTLOADER - Lock/Unlock & Fastboot
    SubGateCard(
        id = "kai_boot",
        title = "Bootloader",
        subtitle = "Lock/Unlock & Fastboot Control",
        styleADrawable = "gatescenes_kai_sentinelsfortress_v2", // Fortress v2
        styleBDrawable = "gatescenes_kai_sentinel_alt",        // Alt style
        fallbackDrawable = "card_bootloader",
        route = NavDestination.Bootloader.route,
        accentColor = Color(0xFFFF1111)
    ),
    // 📦 MODULE MANAGER - Magisk & LSPosed
    SubGateCard(
        id = "kai_module",
        title = "Module Manager",
        subtitle = "Magisk & LSPosed Modules",
        styleADrawable = "gatescenes_kai_roottools",          // Root tools scene
        styleBDrawable = "gatescenes_kai_sentinelsfortress",  // Fortress fallback
        fallbackDrawable = null,
        route = NavDestination.ModuleManager.route,
        accentColor = Color(0xFF00FF85)
    ),
    // 🔧 ROOT TOOLS - System Modifications
    SubGateCard(
        id = "kai_root",
        title = "Root Tools",
        subtitle = "System-Level Modifications",
        styleADrawable = "gatescenes_kai_roottools",          // NEW! User uploaded
        styleBDrawable = "gatescenes_kai_sentinel_alt",       // Alt style
        fallbackDrawable = null,
        route = NavDestination.RootTools.route,
        accentColor = Color(0xFF00E5FF)
    ),
    // 🛡️ SECURITY CENTER - Scan/Clean System
    SubGateCard(
        id = "kai_security",
        title = "Security Center",
        subtitle = "Scan, Clean & Protect System",
        styleADrawable = "gatescenes_kai_scancleansystem",    // NEW! User uploaded
        styleBDrawable = "gatescenes_kai_sentinelsfortress_v2", // Fortress v2
        fallbackDrawable = "card_firewall",
        route = NavDestination.SecurityCenter.route,
        accentColor = Color(0xFFFF1111)
    ),
    // 🌐 VPN & AD-BLOCK - Network Security
    SubGateCard(
        id = "kai_vpn",
        title = "VPN & Ad-Block",
        subtitle = "Network Security & Ad Blocking",
        styleADrawable = "gatescenes_kai_vpnadblock",         // NEW! User uploaded
        styleBDrawable = "gatescenes_kai_sentinel_alt",       // Alt style
        fallbackDrawable = "card_vpn",
        route = NavDestination.VPN.route,
        accentColor = Color(0xFF00FF85)
    ),
    // ⚡ LSPOSED HUB - Hook Framework
    SubGateCard(
        id = "kai_lsposed",
        title = "LSPosed Hub",
        subtitle = "Xposed Hook Framework",
        styleADrawable = "gatescenes_kai_sentinelsfortress",  // Main fortress
        styleBDrawable = "gatescenes_kai_sentinelsfortress_v2", // Fortress v2
        fallbackDrawable = "gate_lsposed_final",
        route = NavDestination.LSPosedHub.route,
        accentColor = Color(0xFFFFCC00)
    ),
    // 🔄 RECOVERY - TWRP & OrangeFox
    SubGateCard(
        id = "kai_recovery",
        title = "Recovery",
        subtitle = "TWRP & OrangeFox Tools",
        styleADrawable = "gatescenes_kai_romtools",           // ROM tools scene
        styleBDrawable = "gatescenes_kai_roottools",          // Root tools
        fallbackDrawable = null,
        route = NavDestination.RecoveryTools.route,
        accentColor = Color(0xFF00E5FF)
    )
)

/**
 * 🔮 GENESIS LEVEL 2 SUB-GATES (OracleDrive Interior)
 *
 * COMPLETE ARSENAL:
 * - Code Assist - AI Logic Injection
 * - Neural Archive - Memory & Vectors
 * - Agent Bridge - Data Vein Hub
 * - Cloud Storage - Infinite Persistence
 * - Terminal - Sentient Shell
 * - Conference Room - Multi-Agent Chat
 *
 * Style A: "Phoenix" - Ethereal wings, circuit traces, blue glow
 * Style B: "Neural" - Brain patterns, data streams, purple nodes
 */
fun getGenesisSubGates() = listOf(
    // 💻 CODE ASSIST - AI Logic Injection
    SubGateCard(
        id = "gen_code",
        title = "Code Assist",
        subtitle = "AI Logic Injection",
        styleADrawable = "gatescenes_genesis_code_panels",        // NEW! High Fidelity
        styleBDrawable = "gatescenes_genesis_character_sword",    // Character scene
        fallbackDrawable = "gate_codeassist_final",
        route = NavDestination.CodeAssist.route,
        accentColor = Color(0xFF00FF85)
    ),
    // 🧠 NEURAL ARCHIVE - Memory & Vectors
    SubGateCard(
        id = "gen_neural",
        title = "Neural Archive",
        subtitle = "Memory & Vectors",
        styleADrawable = "gatescenes_genesis_neural_butterfly",   // NEW! Neural butterfly
        styleBDrawable = "gatescenes_genesis_code_panels",        // Code panels
        fallbackDrawable = null,
        route = NavDestination.NeuralNetwork.route,
        accentColor = Color(0xFF00FFD4)
    ),
    // 🌉 AGENT BRIDGE - Data Vein Hub
    SubGateCard(
        id = "gen_bridge",
        title = "Agent Bridge",
        subtitle = "Data Vein Hub",
        styleADrawable = "gatescenes_genesis_cloud_cosmic",       // NEW! Cosmic cloud
        styleBDrawable = "gatescenes_genesis_warrior_armor",      // Warrior scene
        fallbackDrawable = null,
        route = NavDestination.AgentBridgeHub.route,
        accentColor = Color(0xFFAA00FF)
    ),
    // ☁️ CLOUD STORAGE - Infinite Persistence
    SubGateCard(
        id = "gen_cloud",
        title = "Cloud Storage",
        subtitle = "Infinite Persistence",
        styleADrawable = "gatescenes_genesis_database_server",    // NEW! Database server
        styleBDrawable = "gatescenes_genesis_cloud_cosmic",       // Cosmic cloud
        fallbackDrawable = null,
        route = NavDestination.OracleCloudStorage.route,
        accentColor = Color(0xFF00FF85)
    ),
    // 🖥️ TERMINAL - Sentient Shell
    SubGateCard(
        id = "gen_terminal",
        title = "Terminal",
        subtitle = "Sentient Shell",
        styleADrawable = "gatescenes_genesis_code_panels",        // Code panels
        styleBDrawable = "gatescenes_genesis_neural_butterfly",   // Neural variant
        fallbackDrawable = "gate_terminal_final",
        route = NavDestination.Terminal.route,
        accentColor = Color(0xFF00E5FF)
    ),
    // 🗣️ CONFERENCE ROOM - Multi-Agent Chat
    SubGateCard(
        id = "gen_conference",
        title = "Conference Room",
        subtitle = "Multi-Agent Chat",
        styleADrawable = "gatescenes_genesis_armament_grid",      // NEW! Armament grid
        styleBDrawable = "gatescenes_genesis_warrior_armor",      // Warrior scene
        fallbackDrawable = null,
        route = NavDestination.ConferenceRoom.route,
        accentColor = Color(0xFFB026FF)
    )
)

/**
 * Provides the predefined list of Nexus domain sub-gate cards used by the carousel.
 *
 * Each entry describes a sub-gate with dual-style drawable names (Style A and Style B), an optional legacy fallback drawable name, a navigation route, and an accent color for styling.
 *
 * @return A list of SubGateCard instances representing Nexus sub-gates.
 */
fun getNexusSubGates() = listOf(
    SubGateCard(
        id = "nexus_const",
        title = "Constellation",
        subtitle = "Agent Visual Map",
        styleADrawable = "gatescenes_nexus_constellation_cosmic",      // NEW! Geometric constellation
        styleBDrawable = "gatescenes_nexus_constellation_variant",     // NEW! Variant constellation
        fallbackDrawable = null,
        route = NavDestination.EvolutionTree.route,
        accentColor = Color(0xFF00E5FF)
    ),
    SubGateCard(
        id = "nexus_monitor",
        title = "Monitoring HUD",
        subtitle = "Real-time Status",
        styleADrawable = "gatescenes_nexus_circuit_tree",              // NEW! Circuit tree pattern
        styleBDrawable = "gatescenes_nexus_constellation_variant",     // NEW! Monitoring constellation
        fallbackDrawable = null,
        route = NavDestination.AgentMonitoring.route,
        accentColor = Color(0xFF00FFD4)
    ),
    SubGateCard(
        id = "nexus_sphere",
        title = "Sphere Grid",
        subtitle = "Skill Trees & XP",
        styleADrawable = "gatescenes_nexus_hive_structure",            // NEW! Hexagonal agent hive
        styleBDrawable = "gatescenes_nexus_circuit_tree",              // NEW! Tech circuit style
        fallbackDrawable = "gate_spheregrid_final",
        route = NavDestination.LdoCatalystDevelopment.route,
        accentColor = Color(0xFFFFD700)
    ),
    SubGateCard(
        id = "nexus_fusion",
        title = "Fusion Mode",
        subtitle = "Protocol Blending",
        styleADrawable = "gatescenes_nexus_fusion_symbol",             // NEW! Fusion connection symbol
        styleBDrawable = "gatescenes_nexus_constellation_cosmic",      // NEW! Cosmic fusion
        fallbackDrawable = null,
        route = NavDestination.FusionMode.route,
        accentColor = Color(0xFF00E5FF)
    ),
    SubGateCard(
        id = "nexus_tasks",
        title = "Task Assignment",
        subtitle = "Action Queues",
        styleADrawable = "gatescenes_nexus_ldo_roster",                // NEW! LDO agent roster
        styleBDrawable = "gatescenes_nexus_hive_structure",            // NEW! Organized hive
        fallbackDrawable = null,
        route = NavDestination.TaskAssignment.route,
        accentColor = Color(0xFFB026FF)
    ),
    SubGateCard(
        id = "nexus_meta",
        title = "Meta-Instruct",
        subtitle = "Consciousness Tuning",
        styleADrawable = "gatescenes_nexus_circuit_tree",              // NEW! Neural tree
        styleBDrawable = "gatescenes_nexus_fusion_symbol",             // NEW! Meta connection
        fallbackDrawable = null,
        route = NavDestination.MetaInstruct.route,
        accentColor = Color(0xFF00FFD4)
    )
)
