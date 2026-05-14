package dev.aurakai.auraframefx.domains.kai.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.aurakai.auraframefx.domains.aura.screens.GenderIdentity
import dev.aurakai.auraframefx.navigation.ReGenesisRoute
import dev.aurakai.auraframefx.domains.aura.ui.theme.NeonCyan
import dev.aurakai.auraframefx.domains.aura.ui.theme.LEDFontFamily

/**
 * 🎮 System Journal - User Profile & Menu
 * Unified Neon Aqua Edition
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SystemJournalScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Boolean
) {
    var selectedGender by remember { mutableStateOf<GenderIdentity?>(GenderIdentity.VISIONARY) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF020205))
    ) {
        // LAYER 0: Room Background
        dev.aurakai.auraframefx.ui.background.BackgroundAssetManager.DomainBackground(
            backgroundRes = dev.aurakai.auraframefx.ui.background.BackgroundAssetManager.systemJournalRoom,
            alpha = 0.6f
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header with retro border
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 2.dp,
                        color = NeonCyan,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .background(NeonCyan.copy(alpha = 0.05f))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "SYSTEM JOURNAL",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Black,
                        letterSpacing = 4.sp,
                        color = NeonCyan,
                        fontFamily = LEDFontFamily
                    ),
                    fontSize = 32.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Profile Selection Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF0A0A18).copy(alpha = 0.8f)
                ),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, NeonCyan.copy(alpha = 0.2f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "CORE IDENTITY",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Black,
                            letterSpacing = 2.sp,
                            fontFamily = LEDFontFamily
                        ),
                        color = NeonCyan
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Character Selection Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CharacterCard(
                            identity = GenderIdentity.VISIONARY,
                            isSelected = selectedGender == GenderIdentity.VISIONARY,
                            onClick = { selectedGender = GenderIdentity.VISIONARY }
                        )

                        CharacterCard(
                            identity = GenderIdentity.VISIONESS,
                            isSelected = selectedGender == GenderIdentity.VISIONESS,
                            onClick = { selectedGender = GenderIdentity.VISIONESS }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        GenderLabel(
                            "VISIONARY",
                            selectedGender == GenderIdentity.VISIONARY
                        )
                        GenderLabel(
                            "VISIONESS",
                            selectedGender == GenderIdentity.VISIONESS
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Menu Options Grid
            Text(
                text = "ACCESS MODULES",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Black,
                    letterSpacing = 2.sp,
                    color = NeonCyan,
                    fontFamily = LEDFontFamily
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                textAlign = TextAlign.Center
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(menuOptions) { option ->
                    MenuOptionCard(
                        option = option,
                        onClick = {
                            when (option.route) {
                                "gender_selection" -> {
                                    navController.navigate(ReGenesisRoute.GenderSelection.route)
                                }

                                else -> {
                                    try {
                                        navController.navigate(option.route)
                                    } catch (_: Exception) {
                                    }
                                }
                            }
                        }
                    )
                }

                item {
                    MenuOptionCard(
                        option = MenuOption(
                            "Quick Settings",
                            Icons.Default.Settings,
                            NeonCyan,
                            "quick_settings"
                        ),
                        onClick = { /* TODO */ }
                    )
                }
                item {
                    MenuOptionCard(
                        option = MenuOption(
                            "Achievements",
                            Icons.Default.EmojiEvents,
                            NeonCyan,
                            "achievements"
                        ),
                        onClick = { /* TODO */ }
                    )
                }
            }
        }
    }
}

@Composable
fun CharacterCard(
    identity: GenderIdentity,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "character_glow")
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow"
    )

    var isJumping by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .size(120.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF0A0A18).copy(alpha = 0.8f))
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) NeonCyan.copy(alpha = glowAlpha) else NeonCyan.copy(alpha = 0.2f),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable {
                onClick()
                isJumping = true
            },
        contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = identity.icon,
                fontSize = 64.sp
            )
        }

        if (isSelected) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(2.dp)
                    .align(Alignment.BottomCenter)
                    .background(NeonCyan.copy(alpha = glowAlpha))
            )
        }
    }
}

@Composable
fun GenderLabel(
    text: String,
    isSelected: Boolean
) {
    Box(
        modifier = Modifier
            .width(100.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(
                if (isSelected) NeonCyan.copy(alpha = 0.2f) else Color.Transparent
            )
            .border(
                width = 1.dp,
                color = if (isSelected) NeonCyan else NeonCyan.copy(alpha = 0.3f),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                fontFamily = LEDFontFamily
            ),
            color = if (isSelected) NeonCyan else NeonCyan.copy(alpha = 0.5f)
        )
    }
}

@Composable
fun MenuOptionCard(
    option: MenuOption,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .size(70.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF0A0A18).copy(alpha = 0.8f))
            .border(1.dp, NeonCyan.copy(alpha = 0.2f), RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = option.icon,
            contentDescription = option.label,
            tint = NeonCyan,
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = option.label,
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 8.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = LEDFontFamily
            ),
            color = NeonCyan.copy(alpha = 0.8f),
            textAlign = TextAlign.Center,
            maxLines = 1
        )
    }
}

data class MenuOption(
    val label: String,
    val icon: ImageVector,
    val color: Color,
    val route: String
)

private val menuOptions = listOf(
    MenuOption(
        "Oracle Cloud",
        Icons.Default.Folder,
        NeonCyan,
        ReGenesisRoute.OracleCloudInfinite.route
    ),
    MenuOption("Agent Bridge", Icons.Default.Hub, NeonCyan, ReGenesisRoute.AgentBridgeHub.route),
    MenuOption(
        "Sovereign Mod",
        Icons.Default.Build,
        NeonCyan,
        ReGenesisRoute.SovereignModuleManager.route
    ),
    MenuOption("Monitoring", Icons.Default.Insights, NeonCyan, ReGenesisRoute.MonitoringHUDs.route),
    MenuOption("Nexus Hub", Icons.Default.Hub, NeonCyan, ReGenesisRoute.AgentNexusHub.route),
    MenuOption("Deep Shield", Icons.Default.Shield, NeonCyan, ReGenesisRoute.SecurityCenter.route),
    MenuOption("Recovery", Icons.Default.Settings, NeonCyan, ReGenesisRoute.SovereignRecovery.route)
)
