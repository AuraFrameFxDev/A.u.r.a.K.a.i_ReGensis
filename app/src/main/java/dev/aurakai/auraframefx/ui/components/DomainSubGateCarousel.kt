package dev.aurakai.auraframefx.ui.components

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import dev.aurakai.auraframefx.navigation.AuraCustomizationRoute
import dev.aurakai.auraframefx.ui.theme.LEDFontFamily
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
    useStyleB: Boolean = false,      // Toggle between style A and B
    modifier: Modifier = Modifier,
    cardHeight: Dp = 200.dp,
    domainColor: Color = Color.Cyan
) {
    val context = LocalContext.current
    val pagerState = rememberPagerState(pageCount = { subGates.size })

    val currentGate = subGates.getOrNull(pagerState.currentPage)

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Carousel
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(cardHeight),
            contentPadding = PaddingValues(horizontal = 32.dp),
            pageSpacing = 12.dp
        ) { page ->
            val gate = subGates[page]

            val pageOffset = (
                (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
                ).absoluteValue

            val scale = lerp(0.9f, 1f, 1f - pageOffset.coerceIn(0f, 1f))
            val alpha = lerp(0.6f, 1f, 1f - pageOffset.coerceIn(0f, 1f))

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

        // Current gate title below carousel
        currentGate?.let { gate ->
            Column(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = gate.title,
                    fontFamily = LEDFontFamily,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = gate.accentColor,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = gate.subtitle,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White.copy(alpha = 0.6f),
                    textAlign = TextAlign.Center
                )
            }
        }
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
        // Try primary style first
        var id = context.resources.getIdentifier(
            primaryDrawable, "drawable", context.packageName
        )
        // Try fallback if primary not found
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
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (drawableId != 0) {
                Image(
                    painter = painterResource(id = drawableId),
                    contentDescription = gate.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                // Fallback gradient card
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                listOf(
                                    gate.accentColor.copy(alpha = 0.3f),
                                    Color.Black.copy(alpha = 0.8f)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = gate.title,
                            fontFamily = LEDFontFamily,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = gate.accentColor,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════
// SUB-GATE LISTS FOR EACH DOMAIN (with dual-style support)
// ═══════════════════════════════════════════════════════════════════════════

/**
 * Provides the list of Aura-domain sub-gate cards for the UI carousel.
 *
 * Each entry contains dual-style drawable names (`styleADrawable`, `styleBDrawable`),
 * an optional legacy `fallbackDrawable`, a navigation `route`, and an `accentColor`.
 *
 * @return A list of `SubGateCard` instances representing Aura's sub-gates.
 */
fun getAuraSubGates() = listOf(
    SubGateCard(
        id = "aura_chroma",
        title = "ChromaCore",
        subtitle = "System Color Engine",
        styleADrawable = "aura_splash_chroma",
        styleBDrawable = "aura_clean_chroma",
        fallbackDrawable = "card_chroma_core",
        route = AuraCustomizationRoute.ColorBlendr.route,
        accentColor = Color(0xFFB026FF)
    ),
    SubGateCard(
        id = "aura_theme",
        title = "Theme Engine",
        subtitle = "Visual Style Control",
        styleADrawable = "aura_splash_theme",
        styleBDrawable = "aura_clean_theme",
        fallbackDrawable = "gate_themeengine_final",
        route = AuraCustomizationRoute.IconifyPicker.route,
        accentColor = Color(0xFFFF00FF)
    ),
    SubGateCard(
        id = "aura_notch",
        title = "Notch Bar",
        subtitle = "Dynamic Edge Design",
        styleADrawable = "aura_splash_notch",
        styleBDrawable = "aura_clean_notch",
        fallbackDrawable = "card_notch_bar",
        route = NavDestination.NotchBar.route,
        accentColor = Color(0xFFFF1493)
    ),
    SubGateCard(
        id = "aura_status",
        title = "Status Bar",
        subtitle = "Icon & Layout Tweaks",
        styleADrawable = "aura_splash_status",
        styleBDrawable = "aura_clean_status",
        fallbackDrawable = null,
        route = NavDestination.StatusBar.route,
        accentColor = Color(0xFF00E5FF)
    ),
    SubGateCard(
        id = "aura_qs",
        title = "Quick Settings",
        subtitle = "Tile Customization",
        styleADrawable = "aura_splash_qs",
        styleBDrawable = "aura_clean_qs",
        fallbackDrawable = null,
        route = NavDestination.QuickSettings.route,
        accentColor = Color(0xFF00FF85)
    ),
    SubGateCard(
        id = "aura_collab",
        title = "CollabCanvas",
        subtitle = "Design with Agents",
        styleADrawable = "aura_splash_collab",
        styleBDrawable = "aura_clean_collab",
        fallbackDrawable = "card_collab_canvas",
        route = AuraCustomizationRoute.PixelLauncherEnhanced.route,
        accentColor = Color(0xFFB026FF)
    ),
    SubGateCard(
        id = "aura_lab",
        title = "Aura's Lab",
        subtitle = "Sandbox & Testing",
        styleADrawable = "aura_splash_lab",
        styleBDrawable = "aura_clean_lab",
        fallbackDrawable = "gate_auralab_final",
        route = NavDestination.AuraLab.route,
        accentColor = Color(0xFF00E5FF)
    )
)

/**
 * 🛡️ KAI'S SUB-GATES
 * Style A: "Pixel Fortress" - Retro pixel art, armored guards, stone walls
 * Style B: "Cyber Security" - Matrix code rain, lightning, red neon frames
 */
fun getKaiSubGates() = listOf(
    SubGateCard(
        id = "kai_rom",
        title = "ROM Flasher",
        subtitle = "Partition Management",
        styleADrawable = "kai_pixel_rom",
        styleBDrawable = "kai_cyber_rom",
        fallbackDrawable = "gate_romtools_final",
        route = NavDestination.ROMFlasher.route,
        accentColor = Color(0xFFFF3366)
    ),
    SubGateCard(
        id = "kai_boot",
        title = "Bootloader",
        subtitle = "Lock/Unlock & Fastboot",
        styleADrawable = "kai_pixel_bootloader",
        styleBDrawable = "kai_cyber_bootloader",
        fallbackDrawable = "card_bootloader",
        route = NavDestination.Bootloader.route,
        accentColor = Color(0xFFFF1111)
    ),
    SubGateCard(
        id = "kai_module",
        title = "Module Manager",
        subtitle = "Magisk & LSPosed",
        styleADrawable = "kai_pixel_modules",
        styleBDrawable = "kai_cyber_modules",
        fallbackDrawable = null,
        route = NavDestination.ModuleManager.route,
        accentColor = Color(0xFF00FF85)
    ),
    SubGateCard(
        id = "kai_recovery",
        title = "Recovery",
        subtitle = "TWRP & OrangeFox",
        styleADrawable = "kai_pixel_recovery",
        styleBDrawable = "kai_cyber_recovery",
        fallbackDrawable = null,
        route = NavDestination.RecoveryTools.route,
        accentColor = Color(0xFF00E5FF)
    ),
    SubGateCard(
        id = "kai_shield",
        title = "Security Center",
        subtitle = "Firewall & Ad-Block",
        styleADrawable = "kai_pixel_security",
        styleBDrawable = "kai_cyber_security",
        fallbackDrawable = "card_firewall",
        route = NavDestination.SecurityCenter.route,
        accentColor = Color(0xFFFF1111)
    ),
    SubGateCard(
        id = "kai_vpn",
        title = "VPN Shield",
        subtitle = "Secure Tunneling",
        styleADrawable = "kai_pixel_vpn",
        styleBDrawable = "kai_cyber_vpn",
        fallbackDrawable = "card_vpn",
        route = NavDestination.VPN.route,
        accentColor = Color(0xFF00FF85)
    ),
    SubGateCard(
        id = "kai_lsposed",
        title = "LSPosed Hub",
        subtitle = "Hook Framework",
        styleADrawable = "kai_pixel_lsposed",
        styleBDrawable = "kai_cyber_lsposed",
        fallbackDrawable = "gate_lsposed_final",
        route = NavDestination.LSPosedHub.route,
        accentColor = Color(0xFFFFCC00)
    )
)

/**
 * 🔮 GENESIS SUB-GATES
 * Style A: "Phoenix" - Ethereal wings, circuit traces, blue glow
 * Style B: "Neural" - Brain patterns, data streams, purple nodes
 */
fun getGenesisSubGates() = listOf(
    SubGateCard(
        id = "gen_code",
        title = "Code Assist",
        subtitle = "AI Logic Injection",
        styleADrawable = "genesis_phoenix_code",
        styleBDrawable = "genesis_neural_code",
        fallbackDrawable = "gate_codeassist_final",
        route = NavDestination.CodeAssist.route,
        accentColor = Color(0xFF00FF85)
    ),
    SubGateCard(
        id = "gen_neural",
        title = "Neural Archive",
        subtitle = "Memory & Vectors",
        styleADrawable = "genesis_phoenix_archive",
        styleBDrawable = "genesis_neural_archive",
        fallbackDrawable = null,
        route = NavDestination.NeuralNetwork.route,
        accentColor = Color(0xFF00FFD4)
    ),
    SubGateCard(
        id = "gen_bridge",
        title = "Agent Bridge",
        subtitle = "Data Vein Hub",
        styleADrawable = "genesis_phoenix_bridge",
        styleBDrawable = "genesis_neural_bridge",
        fallbackDrawable = null,
        route = NavDestination.AgentBridgeHub.route,
        accentColor = Color(0xFFAA00FF)
    ),
    SubGateCard(
        id = "gen_cloud",
        title = "Cloud Storage",
        subtitle = "Infinite Persistence",
        styleADrawable = "genesis_phoenix_cloud",
        styleBDrawable = "genesis_neural_cloud",
        fallbackDrawable = null,
        route = NavDestination.OracleCloudStorage.route,
        accentColor = Color(0xFF00FF85)
    ),
    SubGateCard(
        id = "gen_terminal",
        title = "Terminal",
        subtitle = "Sentient Shell",
        styleADrawable = "genesis_phoenix_terminal",
        styleBDrawable = "genesis_neural_terminal",
        fallbackDrawable = "gate_terminal_final",
        route = NavDestination.Terminal.route,
        accentColor = Color(0xFF00E5FF)
    ),
    SubGateCard(
        id = "gen_conference",
        title = "Conference Room",
        subtitle = "Multi-Agent Chat",
        styleADrawable = "genesis_phoenix_conference",
        styleBDrawable = "genesis_neural_conference",
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