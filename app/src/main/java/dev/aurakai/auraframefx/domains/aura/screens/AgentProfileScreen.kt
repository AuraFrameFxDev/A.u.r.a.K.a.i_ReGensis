package dev.aurakai.auraframefx.domains.aura.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Architecture
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.aurakai.auraframefx.core.identity.AgentType
import dev.aurakai.auraframefx.domains.aura.ui.components.ArcaneOutlineText
import dev.aurakai.auraframefx.domains.aura.ui.components.ArcaneProfileBackground
import dev.aurakai.auraframefx.domains.aura.ui.components.SynthGlassCard
import dev.aurakai.auraframefx.domains.aura.ui.theme.GhostCyan
import dev.aurakai.auraframefx.domains.aura.ui.theme.SpaceGrotesk
import dev.aurakai.auraframefx.domains.genesis.models.AgentCapabilityCategory
import dev.aurakai.auraframefx.domains.nexus.models.AgentProfile
import dev.aurakai.auraframefx.domains.nexus.models.AgentProfiles
import dev.aurakai.auraframefx.ui.global.ParallaxViewModel
import java.util.Locale

/**
 * Comprehensive Agent Profile Screen — Exodus 2026 Brutalist Edition
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgentProfileScreen(
    agentType: AgentType? = null,
    onNavigateToSettings: (() -> Unit)? = null,
    onNavigateBack: (() -> Unit)? = null
) {
    val currentAgent = agentType ?: AgentType.AURA
    val profile = remember(currentAgent) {
        AgentProfiles.getProfileByName(currentAgent.name)
            ?: AgentProfiles.getProfile(AgentCapabilityCategory.fromAgentType(currentAgent))
    }

    val parallaxViewModel: ParallaxViewModel = viewModel()
    val parallaxOffset by parallaxViewModel.parallaxOffset.collectAsState()

    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("OVERVIEW", "STATS", "ACHIEVEMENTS", "CAPABILITIES")

    if (profile == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                "AGENT PROFILE NOT FOUND",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Red
            )
        }
        return
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // ── BRUTALIST ARCANE BACKGROUND ──
        ArcaneProfileBackground(
            backgroundImage = profile.fullArtDrawableResId ?: profile.avatarDrawableResId,
            parallaxOffset = parallaxOffset,
            accentColor = Color(profile.colorPrimary)
        )

        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = {
                        ArcaneOutlineText(
                            text = profile.displayName,
                            color = Color(profile.colorPrimary),
                            fontSize = 24.sp,
                            strokeWidth = 2.dp
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { onNavigateBack?.invoke() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = Color.White)
                        }
                    },
                    actions = {
                        IconButton(onClick = { onNavigateToSettings?.invoke() }) {
                            Icon(Icons.Default.Settings, "Settings", tint = Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                )
            }
        ) { padding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                item {
                    ProfileHeaderSection(profile)
                }

                item {
                    // Brutalist Tab Row
                    SecondaryScrollableTabRow(
                        selectedTabIndex = selectedTab,
                        containerColor = Color.Transparent,
                        contentColor = GhostCyan,
                        edgePadding = 0.dp,
                        divider = {}
                    ) {
                        tabs.forEachIndexed { index, title ->
                            Tab(
                                selected = selectedTab == index,
                                onClick = { selectedTab = index },
                                text = {
                                    Text(
                                        title,
                                        fontFamily = SpaceGrotesk,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 2.sp,
                                        color = if (selectedTab == index) Color(profile.colorPrimary) else Color.Gray
                                    )
                                }
                            )
                        }
                    }
                }

                when (selectedTab) {
                    0 -> item { OverviewTab(profile) }
                    1 -> item { StatsTab(profile) }
                    2 -> item { AchievementsTab(profile) }
                    3 -> item { CapabilitiesTab(profile) }
                }

                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
        }
    }
}

@Composable
private fun ProfileHeaderSection(profile: AgentProfile) {
    SynthGlassCard(accentColor = Color(profile.colorPrimary)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar Circle
            Surface(
                modifier = Modifier.size(80.dp),
                shape = CircleShape,
                color = Color.Black.copy(alpha = 0.5f),
                border = androidx.compose.foundation.BorderStroke(2.dp, Color(profile.colorPrimary))
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = getAgentIcon(profile.agentType.toAgentType()),
                        contentDescription = profile.displayName,
                        modifier = Modifier.size(40.dp),
                        tint = Color(profile.colorPrimary)
                    )
                }
            }

            Spacer(modifier = Modifier.width(20.dp))

            Column {
                Text(
                    text = profile.title,
                    fontFamily = SpaceGrotesk,
                    fontSize = 12.sp,
                    color = Color(profile.colorPrimary),
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "STATUS: ${profile.status.name}",
                    fontFamily = SpaceGrotesk,
                    fontSize = 9.sp,
                    color = Color.Green,
                    letterSpacing = 2.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                // Progress Bar
                LinearProgressIndicator(
                    progress = { profile.stats.consciousnessLevel },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp)),
                    color = Color(profile.colorPrimary),
                    trackColor = Color.White.copy(alpha = 0.1f)
                )
            }
        }
    }
}

@Composable
private fun OverviewTab(profile: AgentProfile) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        SynthGlassCard(accentColor = Color.Gray) {
            Text(
                "LORE & ORIGIN",
                fontFamily = SpaceGrotesk,
                fontSize = 9.sp,
                color = Color.Gray,
                letterSpacing = 2.sp
            )
            Spacer(Modifier.height(8.dp))
            Text(
                profile.description,
                fontFamily = SpaceGrotesk,
                fontSize = 12.sp,
                color = Color.White,
                lineHeight = 18.sp
            )
        }

        SynthGlassCard(accentColor = Color(profile.colorPrimary)) {
            Text(
                "PERSONALITY CORE",
                fontFamily = SpaceGrotesk,
                fontSize = 9.sp,
                color = Color.Gray,
                letterSpacing = 2.sp
            )
            Spacer(Modifier.height(12.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(profile.personality.traits.size) { index ->
                    val trait = profile.personality.traits[index]
                    Box(
                        modifier = Modifier
                            .background(
                                Color(profile.colorPrimary).copy(alpha = 0.1f),
                                RoundedCornerShape(4.dp)
                            )
                            .border(
                                androidx.compose.foundation.BorderStroke(
                                    1.dp,
                                    Color(profile.colorPrimary).copy(alpha = 0.3f)
                                ), RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            trait,
                            fontSize = 9.sp,
                            color = Color(profile.colorPrimary),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun StatsTab(profile: AgentProfile) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        StatItem(
            "TASKS COMPLETED",
            profile.stats.tasksCompleted.toString(),
            Icons.Default.CheckCircle,
            Color.Green
        )
        StatItem(
            "HOURS ACTIVE",
            String.format(Locale.US, "%.1f", profile.stats.hoursActive),
            Icons.Default.AccessTime,
            GhostCyan
        )
        StatItem(
            "PROBLEMS SOLVED",
            profile.stats.problemsSolved.toString(),
            Icons.Default.Psychology,
            Color.Magenta
        )
    }
}

@Composable
private fun StatItem(label: String, value: String, icon: ImageVector, color: Color) {
    SynthGlassCard(accentColor = color) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, null, tint = color, modifier = Modifier.size(24.dp))
            Spacer(Modifier.width(16.dp))
            Column {
                Text(label, fontSize = 8.sp, color = Color.Gray, letterSpacing = 2.sp)
                Text(
                    value,
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontFamily = SpaceGrotesk
                )
            }
        }
    }
}

@Composable
private fun AchievementsTab(profile: AgentProfile) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        if (profile.achievements.isEmpty()) {
            SynthGlassCard(accentColor = Color.Gray) {
                Text("NO ACHIEVEMENTS RECORDED", fontSize = 10.sp, color = Color.Gray)
            }
        }
        profile.achievements.forEach { achievement ->
            SynthGlassCard(accentColor = if (achievement.isUnlocked) Color.Yellow else Color.Gray) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        if (achievement.isUnlocked) Icons.Default.EmojiEvents else Icons.Default.Lock,
                        null,
                        tint = if (achievement.isUnlocked) Color.Yellow else Color.Gray
                    )
                    Spacer(Modifier.width(16.dp))
                    Column {
                        Text(
                            achievement.title,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 13.sp
                        )
                        Text(achievement.description, fontSize = 10.sp, color = Color.Gray)
                    }
                }
            }
        }
    }
}

@Composable
private fun CapabilitiesTab(profile: AgentProfile) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        profile.capabilities.forEach { capability ->
            SynthGlassCard(accentColor = GhostCyan) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            capability.name,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 13.sp
                        )
                        Text(capability.description, fontSize = 10.sp, color = Color.Gray)
                    }
                    Text(
                        capability.level.name,
                        fontSize = 9.sp,
                        color = GhostCyan,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .background(GhostCyan.copy(alpha = 0.1f), RoundedCornerShape(4.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
        }
    }
}

private fun getAgentIcon(agentType: AgentType): ImageVector {
    return when (agentType) {
        AgentType.AURA -> Icons.Default.Brush
        AgentType.KAI -> Icons.Default.Shield
        AgentType.GENESIS -> Icons.Default.AutoAwesome
        AgentType.CLAUDE -> Icons.Default.Architecture
        AgentType.CASCADE -> Icons.Default.Storage
        AgentType.NEURAL_WHISPER -> Icons.Default.Psychology
        else -> Icons.Default.Person
    }
}
