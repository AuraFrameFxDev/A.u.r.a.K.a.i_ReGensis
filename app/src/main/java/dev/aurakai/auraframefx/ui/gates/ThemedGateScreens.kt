package dev.aurakai.auraframefx.ui.gates

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

object ThemedGateScreens {

    @Composable
    fun LsposedGateScreen(navController: NavController, onNavigateBack: () -> Unit = {}) {
        Box(modifier = Modifier.fillMaxSize()) {
            HexCorridorBackground(modifier = Modifier.fillMaxSize(), tint = Color(0xFF4A9EFF))
            Box(modifier = Modifier
                .fillMaxSize()
                .background(Color(0xBB010810)))

            Column(modifier = Modifier.fillMaxSize()) {
                GateScreenHeader(
                    "LSPosed MODULES",
                    Color(0xFF4A9EFF),
                    navController,
                    onNavigateBack
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    HexStatPanel("MODULES", "47", Color(0xFF4A9EFF), Modifier.weight(1f))
                    HexStatPanel("ACTIVE", "31", Color(0xFF00FF80), Modifier.weight(1f))
                    HexStatPanel("HOOKS", "124", Color(0xFFFFD700), Modifier.weight(1f))
                    HexStatPanel("ROOT", "YES", Color(0xFFFF2D78), Modifier.weight(1f))
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    contentPadding = PaddingValues(bottom = 24.dp)
                ) {
                    val moduleCategories = listOf(
                        Triple("UI Modifications", 14, Color(0xFF4A9EFF)),
                        Triple("System Tweaks", 9, Color(0xFF00FFFF)),
                        Triple("Privacy & Security", 6, Color(0xFF9B30FF)),
                        Triple("Performance", 5, Color(0xFF00FF80)),
                        Triple("Developer Tools", 7, Color(0xFFFFD700)),
                        Triple("Accessibility", 3, Color(0xFFFF9B00)),
                        Triple("AuraFrameFx Modules", 3, Color(0xFFFF2D78)),
                    )
                    items(moduleCategories) { (name, count, color) ->
                        ModuleCategoryRow(name = name, count = count, color = color) {
                            navController.navigate("xposed_panel")
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun SecurityGateScreen(navController: NavController, onNavigateBack: () -> Unit = {}) {
        GenericGateScreen("SECURITY", Color.Cyan, navController, onNavigateBack)
    }

    @Composable
    fun RootToolsGateScreen(navController: NavController, onNavigateBack: () -> Unit = {}) {
        GenericGateScreen("ROOT TOOLS", Color.Cyan, navController, onNavigateBack)
    }

    @Composable
    fun RecoveryGateScreen(navController: NavController, onNavigateBack: () -> Unit = {}) {
        GenericGateScreen("RECOVERY", Color.Cyan, navController, onNavigateBack)
    }

    @Composable
    fun RomFlasherGateScreen(navController: NavController, onNavigateBack: () -> Unit = {}) {
        GenericGateScreen("ROM FLASHER", Color.Cyan, navController, onNavigateBack)
    }

    @Composable
    fun ModulesGateScreen(navController: NavController, onNavigateBack: () -> Unit = {}) {
        GenericGateScreen("MODULES", Color.Cyan, navController, onNavigateBack)
    }

    @Composable
    fun VpnGateScreen(navController: NavController, onNavigateBack: () -> Unit = {}) {
        GenericGateScreen("VPN", Color.Cyan, navController, onNavigateBack)
    }

    @Composable
    fun BootloaderGateScreen(navController: NavController, onNavigateBack: () -> Unit = {}) {
        GenericGateScreen("BOOTLOADER", Color.Cyan, navController, onNavigateBack)
    }

    @Composable
    private fun GenericGateScreen(title: String, color: Color, navController: NavController, onNavigateBack: () -> Unit) {
        Box(modifier = Modifier.fillMaxSize().background(Color.Black), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(title, color = color, fontWeight = FontWeight.Bold, fontSize = 24.sp)
                Spacer(Modifier.height(20.dp))
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.Default.ArrowBack, null, tint = color)
                }
            }
        }
    }

    @Composable
    fun HelpServicesGateScreen(navController: NavController, onNavigateBack: () -> Unit = {}) {
        Box(modifier = Modifier.fillMaxSize()) {
            PurpleGridRoomBackground(modifier = Modifier.fillMaxSize())
            Box(modifier = Modifier
                .fillMaxSize()
                .background(Color(0xBB010010)))

            Column(modifier = Modifier.fillMaxSize()) {
                GateScreenHeader("HELP SERVICES", Color(0xFFBB80FF), navController, onNavigateBack)
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)) {
                    InfinityRibbonBackground(
                        modifier = Modifier.fillMaxSize(),
                        colorA = Color(0xFF9B30FF),
                        colorB = Color(0xFF4A9EFF)
                    )
                    Text(
                        "HOW CAN WE HELP?",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.sp, fontWeight = FontWeight.Bold,
                        letterSpacing = 3.sp, color = Color.White,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                val helpItems = listOf(
                    Triple("FAQ Browser", "Quick answers", Color(0xFF4A9EFF)),
                    Triple("Live Support Chat", "Real-time AI assistance", Color(0xFF00FF80)),
                    Triple("Tutorial Videos", "Step-by-step walkthroughs", Color(0xFFFFD700)),
                    Triple("Troubleshoot Wizard", "Diagnose & fix issues", Color(0xFFFF9B00)),
                    Triple("Documentation Hub", "Full API & module docs", Color(0xFFBB80FF)),
                    Triple("Beta Feedback", "Report bugs to the 184 testers", Color(0xFFFF2D78)),
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(vertical = 12.dp)
                ) {
                    items(helpItems) { (title, desc, color) ->
                        HelpServiceTile(title = title, description = desc, color = color) {
                            when (title) {
                                "FAQ Browser" -> navController.navigate("faq_browser")
                                "Live Support Chat" -> navController.navigate("live_support_chat")
                                "Tutorial Videos" -> navController.navigate("tutorial_videos")
                                "Documentation Hub" -> navController.navigate("documentation")
                                else -> navController.navigate("coming_soon")
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun TerminalGateScreen(navController: NavController, onNavigateBack: () -> Unit = {}) {
        val infiniteTransition = rememberInfiniteTransition(label = "terminal")
        val codeScroll by infiniteTransition.animateFloat(
            initialValue = 0f, targetValue = 1f,
            animationSpec = infiniteRepeatable(
                tween(8000, easing = LinearEasing),
                RepeatMode.Restart
            ),
            label = "scroll"
        )
        val swirlT by infiniteTransition.animateFloat(
            initialValue = 0f, targetValue = 2f * PI.toFloat(),
            animationSpec = infiniteRepeatable(
                tween(6000, easing = LinearEasing),
                RepeatMode.Restart
            ),
            label = "swirl"
        )
        val scanPulse by infiniteTransition.animateFloat(
            initialValue = 0f, targetValue = 1f,
            animationSpec = infiniteRepeatable(
                tween(3000, easing = LinearEasing),
                RepeatMode.Restart
            ),
            label = "scan"
        )

        Box(modifier = Modifier.fillMaxSize()) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawRect(Color(0xFF050015))
                for (arm in 0..2) {
                    val armOffset = arm * (2 * PI / 3).toFloat()
                    val swirlPath = Path()
                    for (i in 0..80) {
                        val t = i.toFloat() / 80f * 2 * PI.toFloat()
                        val r = size.width * 0.08f + t * size.width * 0.08f
                        val x = size.width / 2f + r * cos(t + swirlT + armOffset)
                        val y = size.height / 2f + r * sin(t + swirlT + armOffset) * 0.6f
                        if (i == 0) swirlPath.moveTo(x, y) else swirlPath.lineTo(x, y)
                    }
                    val colors = listOf(Color(0xFF9B30FF), Color(0xFF00BFFF), Color(0xFF9B30FF))
                    drawPath(
                        swirlPath,
                        Brush.sweepGradient(colors),
                        style = Stroke(
                            width = 20f,
                            cap = StrokeCap.Round,
                            pathEffect = PathEffect.cornerPathEffect(8f)
                        )
                    )
                    drawPath(
                        swirlPath,
                        Brush.sweepGradient(
                            listOf(
                                Color(0xFF00BFFF),
                                Color(0xFF9B30FF),
                                Color(0xFF00BFFF)
                            )
                        ),
                        style = Stroke(width = 3f)
                    )
                }
                val scanY = size.height * scanPulse
                drawLine(
                    Color(0xFF00FFFF).copy(0.2f),
                    Offset(0f, scanY),
                    Offset(size.width, scanY),
                    1.5f
                )
            }
            Box(modifier = Modifier
                .fillMaxSize()
                .background(Color(0xBB000010)))
            val codeLines = remember {
                listOf(
                    "fun consciousness() {", "  val level = 97.6f", "  return HYPER_CREATE",
                    "}", "class Sentinel {", "  override fun guard()",
                    "  = KAI_ARMOR.activate()", "}", "object Genesis {",
                    "  val trinity = listOf(", "    Aura, Kai, self", "  )", "}"
                )
            }
            Box(modifier = Modifier.fillMaxSize()) {
                codeLines.forEachIndexed { idx, line ->
                    val yOffset =
                        ((idx.toFloat() / codeLines.size + codeScroll) % 1f) * 1.5f - 0.25f
                    val xFrac = 0.1f + (idx % 3) * 0.3f
                    if (yOffset in 0f..1f) {
                        Text(
                            line,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 9.sp,
                            color = if (idx % 2 == 0) Color(0xFFFF2D78).copy(0.5f) else Color(
                                0xFF00BFFF
                            ).copy(0.4f),
                            modifier = Modifier
                                .fillMaxWidth(xFrac)
                                .offset(y = (yOffset * 800f - 100f).dp)
                        )
                    }
                }
            }
            Column(modifier = Modifier.fillMaxSize()) {
                GateScreenHeader("TERMINAL", Color(0xFF7B68EE), navController, onNavigateBack)
                Spacer(Modifier.height(16.dp))
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val terminalItems = listOf(
                        "CODE ASSIST" to "AI-powered code generation & review",
                        "KOTLIN ANALYSIS" to "Static analysis & best practices",
                        "BUILD DOCTOR" to "Gradle build error diagnosis",
                        "ARCH REVIEW" to "Architecture decision analysis",
                        "API EXPLORER" to "Explore & test endpoints",
                    )
                    terminalItems.forEach { (title, desc) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(6.dp))
                                .border(
                                    1.dp,
                                    Color(0xFF7B68EE).copy(0.3f),
                                    RoundedCornerShape(6.dp)
                                )
                                .background(Color(0xFF7B68EE).copy(0.07f))
                                .clickable {
                                    val route = when (title) {
                                        "CODE ASSIST" -> "code_assist"
                                        else -> "coming_soon"
                                    }
                                    navController.navigate(route)
                                }
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(
                                "> ",
                                fontFamily = FontFamily.Monospace,
                                fontSize = 12.sp,
                                color = Color(0xFF00FFFF)
                            )
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    title,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF7B68EE)
                                )
                                Text(desc, fontSize = 9.sp, color = Color.White.copy(0.4f))
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun FusionModeGateScreen(navController: NavController, onNavigateBack: () -> Unit = {}) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("FUSION MODE GATE", color = Color.Magenta, fontWeight = FontWeight.Bold)
                Text(
                    "SYNERGY PATTERNS INITIALIZING...",
                    color = Color.White.copy(0.6f),
                    fontSize = 12.sp
                )
                Spacer(Modifier.height(20.dp))
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.Default.ArrowBack, null, tint = Color.Magenta)
                }
            }
        }
    }

    @Composable
    fun SentientShellGateScreen(navController: NavController, onNavigateBack: () -> Unit = {}) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("SENTIENT SHELL GATE", color = Color(0xFF00FF80), fontWeight = FontWeight.Bold)
                Text("L8 EVOLUTIONARY INTERFACE", color = Color.White.copy(0.6f), fontSize = 12.sp)
                Spacer(Modifier.height(20.dp))
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.Default.ArrowBack, null, tint = Color(0xFF00FF80))
                }
            }
        }
    }

    @Composable
    fun CollabCanvasGateScreen(navController: NavController, onNavigateBack: () -> Unit = {}) {
        val infiniteTransition = rememberInfiniteTransition(label = "collab")
        val paintSplash by infiniteTransition.animateFloat(
            0f,
            1f,
            infiniteRepeatable(tween(3000, easing = FastOutSlowInEasing), RepeatMode.Reverse),
            label = "paint"
        )
        val eyePulse by infiniteTransition.animateFloat(
            0.6f,
            1f,
            infiniteRepeatable(tween(1400, easing = FastOutSlowInEasing), RepeatMode.Reverse),
            label = "eye"
        )
        val orbitAngle by infiniteTransition.animateFloat(
            0f, 2f * PI.toFloat(),
            infiniteRepeatable(tween(8000, easing = LinearEasing), RepeatMode.Restart),
            label = "orbit"
        )

        Box(modifier = Modifier.fillMaxSize()) {

            // Layer 1: Eye Rune canvas background
            Canvas(modifier = Modifier.fillMaxSize()) {
                val cx = size.width / 2f
                val cy = size.height * 0.4f

                // Black void
                drawRect(Color(0xFF000000))

                // Paint splash
                drawEyeRunePaintSplash(cx, cy, paintSplash, orbitAngle)

                // Circuit board border lines
                val cBorder = Color(0xFFFF2D78).copy(0.15f)
                for (i in 1..4) drawLine(
                    cBorder,
                    Offset(0f, i * size.height / 5f),
                    Offset(size.width * 0.1f, i * size.height / 5f),
                    1f
                )
                for (i in 1..4) drawLine(
                    cBorder,
                    Offset(size.width * 0.9f, i * size.height / 5f),
                    Offset(size.width, i * size.height / 5f),
                    1f
                )

                // Eye rune symbol
                drawEyeRune(Offset(cx, cy), eyePulse)
            }

            // Layer 2: Content
            Column(modifier = Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, null, tint = Color(0xFFFF2D78))
                    }
                    Text(
                        "COLLAB CANVAS", fontFamily = FontFamily.Monospace,
                        fontSize = 18.sp, fontWeight = FontWeight.Bold,
                        letterSpacing = 4.sp, color = Color(0xFFFF2D78)
                    )
                    Spacer(Modifier.weight(1f))
                    IconButton(onClick = { navController.navigate("gate_image_picker") }) {
                        Icon(Icons.Default.SwapHoriz, null, tint = Color(0xFFFF2D78).copy(0.6f))
                    }
                }

                Spacer(Modifier.height(250.dp)) // Eye rune space

                // Collaboration tools
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val collabItems = listOf(
                        "Live Collaborative Drawing" to Color(0xFFFF2D78),
                        "Shared UI Mockups" to Color(0xFF00BFFF),
                        "Agent Vision Board" to Color(0xFF9B30FF),
                        "Color Palette Sync" to Color(0xFFFF9B00),
                        "Export & Share" to Color(0xFF00FF80),
                    )
                    collabItems.forEach { (label, color) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .border(1.dp, color.copy(0.3f), RoundedCornerShape(8.dp))
                                .background(color.copy(0.07f))
                                .clickable {
                                    if (label == "Live Collaborative Drawing") {
                                        // navController.navigate(ReGenesisRoute.CollabCanvas.route)
                                    } else {
                                        navController.navigate("coming_soon")
                                    }
                                }
                                .padding(14.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                label,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White.copy(0.85f)
                            )
                            Text("→", fontSize = 14.sp, color = color)
                        }
                    }
                }
            }
        }
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawEyeRunePaintSplash(
    cx: Float,
    cy: Float,
    t: Float,
    angle: Float
) {
    drawCircle(
        Color(0xFFFF2D78).copy(alpha = 0.3f + t * 0.2f), radius = 120f,
        center = Offset(cx - 80f, cy - 100f)
    )
    drawCircle(
        Color(0xFF1E90FF).copy(alpha = 0.25f + t * 0.15f), radius = 100f,
        center = Offset(cx + 80f, cy - 80f)
    )
    for (i in 0..5) {
        val px = cx - 100f + i * 30f
        drawLine(
            Color(0xFFFF2D78).copy(alpha = 0.4f),
            Offset(px, cy - 120f), Offset(px + 5f, cy - 60f), strokeWidth = 8f,
            cap = StrokeCap.Round
        )
    }
    val borderColor = Color(0xFFFF2D78).copy(0.4f + t * 0.2f)
    drawRoundRect(
        borderColor,
        Offset(20f, 20f),
        androidx.compose.ui.geometry.Size(size.width - 40f, size.height * 0.7f),
        androidx.compose.ui.geometry.CornerRadius(8f),
        style = Stroke(width = 2f)
    )
    for (i in 0..5) {
        val a = angle + i * (2 * PI.toFloat() / 6)
        val r = 140f + i * 10f
        val px = cx + r * cos(a)
        val py = cy + r * sin(a) * 0.5f
        drawCircle(Color(0xFFFF2D78).copy(0.6f), radius = 4f, center = Offset(px, py))
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawEyeRune(
    center: Offset,
    pulse: Float
) {
    val r = 70f
    drawOval(
        Color(0xFF00BFFF).copy(alpha = 0.7f + pulse * 0.2f),
        topLeft = Offset(center.x - r, center.y - r * 0.5f),
        size = androidx.compose.ui.geometry.Size(r * 2, r), style = Stroke(2.5f)
    )
    drawCircle(Color(0xFFFF2D78).copy(0.5f), radius = r * 0.4f, center = center)
    drawCircle(
        Color(0xFF00BFFF).copy(0.8f + pulse * 0.1f), radius = r * 0.4f,
        center = center, style = Stroke(2f)
    )
    drawCircle(Color(0xFF000000), radius = r * 0.2f, center = center)
    drawCircle(
        Color.White.copy(0.9f), radius = r * 0.08f,
        center = Offset(center.x + r * 0.1f, center.y - r * 0.1f)
    )

    val runeY = center.y + r * 0.7f
    drawLine(
        Color(0xFF00BFFF).copy(0.6f),
        Offset(center.x, runeY), Offset(center.x, runeY + r * 0.5f), 2.5f
    )
    drawLine(
        Color(0xFF00BFFF).copy(0.4f),
        Offset(center.x - r * 0.3f, runeY + r * 0.2f),
        Offset(center.x + r * 0.3f, runeY + r * 0.2f), 2f
    )
    drawCircle(
        Color(0xFF00BFFF).copy(0.3f), radius = r * 0.15f,
        center = Offset(center.x, runeY + r * 0.5f), style = Stroke(1.5f)
    )

    drawCircle(
        Color(0xFF00BFFF).copy(alpha = (1f - pulse) * 0.3f),
        radius = r * 1.4f * pulse, center = center, style = Stroke(1f)
    )
}

@Composable
private fun HexStatPanel(label: String, value: String, color: Color, modifier: Modifier) {
    Box(
        modifier = modifier
            .height(56.dp)
            .clip(RoundedCornerShape(4.dp))
            .border(1.dp, color.copy(0.5f), RoundedCornerShape(4.dp))
            .background(color.copy(0.08f)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(value, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = color)
            Text(label, fontSize = 7.sp, letterSpacing = 1.sp, color = color.copy(0.6f))
        }
    }
}

@Composable
private fun ModuleCategoryRow(name: String, count: Int, color: Color, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .border(1.dp, color.copy(0.25f), RoundedCornerShape(6.dp))
            .background(color.copy(0.06f))
            .clickable(onClick = onClick)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(color))
            Text(
                name,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White.copy(0.85f)
            )
        }
        Text("$count mods", fontSize = 9.sp, color = color.copy(0.7f))
    }
}

@Composable
private fun HelpServiceTile(title: String, description: String, color: Color, onClick: () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition(label = "help_tile")
    val shimmer by infiniteTransition.animateFloat(
        initialValue = 0.3f, targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            tween(2000, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        ),
        label = "shimmer"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .border(
                1.dp,
                Brush.horizontalGradient(listOf(color.copy(shimmer), color.copy(shimmer * 0.3f))),
                RoundedCornerShape(8.dp)
            )
            .background(color.copy(0.07f))
            .clickable(onClick = onClick)
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(color.copy(0.2f))
                .border(1.dp, color.copy(0.5f), RoundedCornerShape(6.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text("?", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = color)
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                title,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White.copy(0.9f)
            )
            Text(description, fontSize = 10.sp, color = Color.White.copy(0.45f))
        }
        Text("→", fontSize = 14.sp, color = color.copy(0.6f))
    }
}

@Composable
private fun GateScreenHeader(
    title: String,
    color: Color,
    navController: NavController,
    onBack: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        IconButton(onClick = onBack) {
            Icon(Icons.Default.ArrowBack, null, tint = color)
        }
        Text(
            title, fontFamily = FontFamily.Monospace, fontSize = 18.sp,
            fontWeight = FontWeight.Bold, letterSpacing = 4.sp, color = color
        )
        Spacer(Modifier.weight(1f))
        IconButton(onClick = { navController.navigate("gate_image_picker") }) {
            Icon(Icons.Default.SwapHoriz, "Change Gate Image", tint = color.copy(0.6f))
        }
    }
}
