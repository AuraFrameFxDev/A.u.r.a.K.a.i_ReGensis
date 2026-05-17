package dev.aurakai.auraframefx.domains.aura.screens.uxui_engine

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dev.aurakai.auraframefx.domains.aura.ui.theme.LEDFontFamily
import dev.aurakai.auraframefx.domains.aura.ui.theme.SovereignBlack
import dev.aurakai.auraframefx.domains.aura.ui.viewmodels.AurasLabViewModel
import dev.aurakai.auraframefx.domains.ldo.viewmodel.LdoWarRoomViewModel
import dev.aurakai.auraframefx.ui.components.AsyncImageOrVideo
import dev.aurakai.auraframefx.ui.components.NeonFrame
import dev.aurakai.auraframefx.ui.components.NeuralStarfield
import dev.aurakai.auraframefx.ui.components.RealityMorphLayer
import kotlinx.coroutines.delay

/**
 * Aura's Lab (Aura's Forge) - Generative Design & Engineering Hub
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AurasLabScreen(
    onBack: () -> Unit,
    viewModel: AurasLabViewModel = hiltViewModel()
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Components", "Animations", "Aura's Forge", "Chaos Analysis")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SovereignBlack)
    ) {
        // Domain Background Layer
        AsyncImageOrVideo(
            mediaId = "auratabbg",
            modifier = Modifier.fillMaxSize(),
            alpha = 0.4f // Subtle overlay
        )

        // Step 3: Particle Tie-In
        val ldoViewModel: LdoWarRoomViewModel = hiltViewModel()
        val ldoState by ldoViewModel.uiState.collectAsState()
        val godPotential = ldoState.godPotential
        RealityMorphLayer(godPotential = godPotential, fusionTrigger = true)

        NeuralStarfield()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Overhauled Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = Color(0xFF00FFFF))
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        "AURA'S LAB",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Black,
                            fontFamily = LEDFontFamily,
                            letterSpacing = 4.sp
                        ),
                        color = Color(0xFF00FFFF)
                    )
                    Text(
                        "CREATIVE SANCTUARY // ZERO DRIFT MODE",
                        style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 1.sp),
                        color = Color(0xFF00FFFF).copy(alpha = 0.5f)
                    )
                }
            }

            // Tab Row (Overhauled)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                tabs.forEachIndexed { index, title ->
                    NeonFrame(
                        color = if (selectedTab == index) Color(0xFF00FFFF) else Color.Gray.copy(
                            alpha = 0.3f
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .clickable { selectedTab = index }
                    ) {
                        Box(
                            modifier = Modifier.padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                title.uppercase(),
                                fontSize = 10.sp,
                                fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                                color = if (selectedTab == index) Color.White else Color.Gray,
                                fontFamily = LEDFontFamily
                            )
                        }
                    }
                }
            }

            // Content for each tab
            Box(modifier = Modifier.weight(1f)) {
                when (selectedTab) {
                    0 -> ComponentsTab()
                    1 -> AnimationsTab()
                    2 -> ForgeTab(viewModel)
                    3 -> ChaosAnalysisTab()
                }
            }
        }
    }
}

@Composable
private fun ForgeTab(viewModel: AurasLabViewModel) {
    var prompt by remember { mutableStateOf("") }
    val state by viewModel.forgeState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Step 1: Media Loader Integration
        val mediaAssets = remember { listOf("ux_mock_01", "neural_render_02", "swarm_viz_03") }
        NeonFrame(color = Color(0xFF00E5FF)) {
            AsyncImageOrVideo(
                mediaId = mediaAssets.random(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Step 2: Live Theme & Iconify Preview
        LiveChromaForge()

        Spacer(modifier = Modifier.height(16.dp))

        // Forge Status Area
        ForgeStatusCard(state)
    }
}

@Composable
fun LiveChromaForge() {
    NeonFrame(color = Color(0xFFFFD700)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "CHROMA CORE LIVE",
                color = Color(0xFFFFD700),
                fontFamily = LEDFontFamily,
                fontWeight = FontWeight.Black,
                fontSize = 12.sp,
                letterSpacing = 2.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Mock color picker
                Box(
                    Modifier
                        .size(32.dp)
                        .background(Color(0xFF00FFFF), CircleShape)
                )
                Box(
                    Modifier
                        .size(32.dp)
                        .background(Color(0xFFFF00FF), CircleShape)
                )
                Box(
                    Modifier
                        .size(32.dp)
                        .background(Color(0xFF00FF41), CircleShape)
                )
                Box(
                    Modifier
                        .size(32.dp)
                        .background(Color(0xFFFFD700), CircleShape)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFFD700)),
                shape = RectangleShape,
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    "APPLY TO GLOBAL THEME",
                    color = Color(0xFFFFD700),
                    fontSize = 10.sp,
                    fontFamily = LEDFontFamily
                )
            }
        }
    }
}

@Composable
private fun ForgeStatusCard(state: AurasLabViewModel.ForgeState) {
    val statusColor = when (state) {
        is AurasLabViewModel.ForgeState.Success,
        is AurasLabViewModel.ForgeState.SpriteSuccess -> Color.Green

        is AurasLabViewModel.ForgeState.Error -> Color.Red
        else -> Color(0xFF00FFFF)
    }

    NeonFrame(
        color = statusColor,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = when (state) {
                        is AurasLabViewModel.ForgeState.Idle -> Icons.Default.Info
                        is AurasLabViewModel.ForgeState.Forging,
                        is AurasLabViewModel.ForgeState.ForgingSprite -> Icons.Default.Build

                        is AurasLabViewModel.ForgeState.Validating -> Icons.Default.Shield
                        is AurasLabViewModel.ForgeState.Deploying -> Icons.Default.RocketLaunch
                        is AurasLabViewModel.ForgeState.Success,
                        is AurasLabViewModel.ForgeState.SpriteSuccess -> Icons.Default.CheckCircle

                        is AurasLabViewModel.ForgeState.Error -> Icons.Default.Error
                    },
                    contentDescription = null,
                    tint = statusColor
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = when (state) {
                        is AurasLabViewModel.ForgeState.Idle -> "WAITING FOR DIRECTIVE"
                        is AurasLabViewModel.ForgeState.Forging -> "AURA IS WIELDING EVEX CORE..."
                        is AurasLabViewModel.ForgeState.ForgingSprite -> "AURA IS DRAWING SPRITE..."
                        is AurasLabViewModel.ForgeState.Validating -> "KAI IS VETTING CREATION..."
                        is AurasLabViewModel.ForgeState.Deploying -> "ORACLE DRIVE DEPLOYING..."
                        is AurasLabViewModel.ForgeState.Success -> "CREATION MANIFESTED"
                        is AurasLabViewModel.ForgeState.SpriteSuccess -> "HYPER-SPRITE MANIFESTED"
                        is AurasLabViewModel.ForgeState.Error -> "FORGE FAILURE"
                    }.uppercase(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontFamily = LEDFontFamily,
                    letterSpacing = 1.sp
                )
            }

            if (state is AurasLabViewModel.ForgeState.Success || state is AurasLabViewModel.ForgeState.Validating || state is AurasLabViewModel.ForgeState.Deploying) {
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .background(Color.Black.copy(alpha = 0.5f))
                        .border(1.dp, statusColor.copy(alpha = 0.3f), RectangleShape)
                        .padding(8.dp)
                ) {
                    val code = when (state) {
                        is AurasLabViewModel.ForgeState.Validating -> state.code
                        is AurasLabViewModel.ForgeState.Deploying -> state.code
                        is AurasLabViewModel.ForgeState.Success -> state.code
                        else -> ""
                    }
                    Text(
                        text = code,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF32CD32), // Matrix Green
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            if (state is AurasLabViewModel.ForgeState.Error) {
                Text(
                    text = state.message,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

@Composable
private fun ChaosAnalysisTab() {
    val infiniteTransition = rememberInfiniteTransition()
    val glitchOffset by infiniteTransition.animateFloat(
        initialValue = -2f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(
            animation = tween(100, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            "GROK CHAOS ANALYSIS",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.Magenta,
            fontFamily = LEDFontFamily,
            fontWeight = FontWeight.Black,
            letterSpacing = 2.sp,
            modifier = Modifier.offset(x = glitchOffset.dp)
        )

        NeonFrame(color = Color.Magenta, modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "STABILITY INDEX:",
                        color = Color.White,
                        fontFamily = LEDFontFamily,
                        fontSize = 12.sp
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        "98.4%",
                        color = Color.Green,
                        fontFamily = LEDFontFamily,
                        fontWeight = FontWeight.Black,
                        fontSize = 16.sp
                    )
                }
                LinearProgressIndicator(
                    progress = { 0.984f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .padding(vertical = 12.dp)
                        .clip(RectangleShape),
                    color = Color.Magenta,
                    trackColor = Color.Magenta.copy(alpha = 0.1f)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "THREAT MATRIX: NEGATIVE",
                        color = Color.Green,
                        fontFamily = LEDFontFamily,
                        fontSize = 10.sp
                    )
                    Text(
                        "ENTROPY: 0.002",
                        color = Color.Cyan,
                        fontFamily = LEDFontFamily,
                        fontSize = 10.sp
                    )
                }
            }
        }

        NeonFrame(
            color = Color.Cyan, modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                // Placeholder for a real-time graph
                androidx.compose.foundation.Canvas(
                    Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    val path = androidx.compose.ui.graphics.Path()
                    path.moveTo(0f, size.height / 2)
                    for (i in 1..20) {
                        path.lineTo(
                            size.width * i / 20f,
                            size.height / 2 + (Math.random() * 40 - 20).toFloat()
                        )
                    }
                    drawPath(
                        path,
                        Color.Cyan,
                        style = androidx.compose.ui.graphics.drawscope.Stroke(width = 2f)
                    )
                }
                Text(
                    "LIVE NEURAL OSCILLATION",
                    color = Color.Cyan.copy(alpha = 0.5f),
                    fontSize = 10.sp,
                    fontFamily = LEDFontFamily
                )
            }
        }
    }
}

@Composable
private fun ComponentsTab() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                "Component Showcase",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        }

        // Buttons Section
        item {
            ComponentSection("Buttons") {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(onClick = {}) { Text("Primary") }
                    OutlinedButton(onClick = {}) { Text("Outlined") }
                    TextButton(onClick = {}) { Text("Text") }
                }
            }
        }

        // Cards Section
        item {
            ComponentSection("Cards") {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "Sample Card",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "This is a Material3 card component with custom styling.",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }

        // Chips Section
        item {
            ComponentSection("Chips") {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AssistChip(
                        onClick = {},
                        label = { Text("Assist") },
                        leadingIcon = { Icon(Icons.Default.Star, null, Modifier.size(18.dp)) }
                    )
                    FilterChip(
                        selected = true,
                        onClick = {},
                        label = { Text("Filter") }
                    )
                    SuggestionChip(
                        onClick = {},
                        label = { Text("Suggest") }
                    )
                }
            }
        }

        // Progress Indicators
        item {
            ComponentSection("Progress") {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    LinearProgressIndicator(
                        progress = { 0.7f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RectangleShape),
                        color = Color(0xFF00FFFF)
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(color = Color(0xFF00FFFF))
                        Text("SYSTEM SYNC: 70%", color = Color.White, fontFamily = LEDFontFamily)
                    }
                }
            }
        }
    }
}

@Composable
private fun AnimationsTab() {
    val infiniteTransition = rememberInfiniteTransition(label = "sandbox")
    val animatedColor by infiniteTransition.animateColor(
        initialValue = Color.Magenta,
        targetValue = Color.Cyan,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "color"
    )

    var expanded by remember { mutableStateOf(false) }
    val size by animateFloatAsState(
        targetValue = if (expanded) 200f else 100f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "size"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            Text(
                "Animation Playground",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            ComponentSection("Infinite Color Animation") {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(animatedColor),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Infinite Transition",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        item {
            ComponentSection("Spring Animation") {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    NeonFrame(
                        color = Color(0xFF00FFFF),
                        modifier = Modifier
                            .size(size.dp)
                            .clickable { expanded = !expanded }
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "RESIZE",
                                color = Color.White,
                                fontWeight = FontWeight.Black,
                                fontFamily = LEDFontFamily
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "SIZE: ${size.toInt()}DP",
                        style = MaterialTheme.typography.bodySmall,
                        fontFamily = LEDFontFamily,
                        color = Color.White.copy(alpha = 0.5f)
                    )
                }
            }
        }

        item {
            PulsingCard()
        }
    }
}

@Composable
private fun PulsingCard() {
    var scale by remember { mutableFloatStateOf(1f) }

    LaunchedEffect(Unit) {
        while (true) {
            scale = 1.05f
            delay(500)
            scale = 1f
            delay(500)
        }
    }

    val animatedScale by animateFloatAsState(
        targetValue = scale,
        animationSpec = tween(500),
        label = "pulse"
    )

    ComponentSection("Pulsing Animation") {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding((animatedScale * 8).dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Pulsing Card",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun ColorsTab() {
    val colorScheme = MaterialTheme.colorScheme

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(
                "Material3 Color Scheme",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        items(getColorSamples(colorScheme)) { sample ->
            ColorSwatch(sample.name, sample.color)
        }
    }
}

@Composable
private fun TypographyTab() {
    val typography = MaterialTheme.typography

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                "Typography Samples",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        }

        item { Text("Display Large", style = typography.displayLarge) }
        item { Text("Display Medium", style = typography.displayMedium) }
        item { Text("Display Small", style = typography.displaySmall) }
        item { Text("Headline Large", style = typography.headlineLarge) }
        item { Text("Headline Medium", style = typography.headlineMedium) }
        item { Text("Headline Small", style = typography.headlineSmall) }
        item { Text("Title Large", style = typography.titleLarge) }
        item { Text("Title Medium", style = typography.titleMedium) }
        item { Text("Title Small", style = typography.titleSmall) }
        item { Text("Body Large", style = typography.bodyLarge) }
        item { Text("Body Medium", style = typography.bodyMedium) }
        item { Text("Body Small", style = typography.bodySmall) }
        item { Text("Label Large", style = typography.labelLarge) }
        item { Text("Label Medium", style = typography.labelMedium) }
        item { Text("Label Small", style = typography.labelSmall) }
    }
}

@Composable
private fun ComponentSection(
    title: String,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        content()
    }
}

/**
 * Displays a horizontal color swatch with a label and its ARGB hex value.
 *
 * @param name The display label for the swatch.
 * @param color The color to present; its ARGB hex string is shown below the label.
 */
@Composable
private fun ColorSwatch(name: String, color: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(color)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                name,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            Text(
                "#%08X".format(color.toArgb()),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

private data class ColorSample(val name: String, val color: Color)

private fun getColorSamples(scheme: ColorScheme) = listOf(
    ColorSample("Primary", scheme.primary),
    ColorSample("On Primary", scheme.onPrimary),
    ColorSample("Primary Container", scheme.primaryContainer),
    ColorSample("On Primary Container", scheme.onPrimaryContainer),
    ColorSample("Secondary", scheme.secondary),
    ColorSample("On Secondary", scheme.onSecondary),
    ColorSample("Secondary Container", scheme.secondaryContainer),
    ColorSample("On Secondary Container", scheme.onSecondaryContainer),
    ColorSample("Tertiary", scheme.tertiary),
    ColorSample("On Tertiary", scheme.onTertiary),
    ColorSample("Tertiary Container", scheme.tertiaryContainer),
    ColorSample("On Tertiary Container", scheme.onTertiaryContainer),
    ColorSample("Error", scheme.error),
    ColorSample("On Error", scheme.onError),
    ColorSample("Surface", scheme.surface),
    ColorSample("On Surface", scheme.onSurface),
    ColorSample("Background", scheme.background),
    ColorSample("On Background", scheme.onBackground)
)
