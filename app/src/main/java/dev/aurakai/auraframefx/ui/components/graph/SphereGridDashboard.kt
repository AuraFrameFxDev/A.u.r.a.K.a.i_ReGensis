package dev.aurakai.auraframefx.ui.components.graph

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.aurakai.auraframefx.core.soulscript.SoulScript
import dev.aurakai.auraframefx.ui.theme.ArcaneBrutalistTheme
import dev.aurakai.auraframefx.ui.theme.SpaceGrotesk

/**
 * 🜁 SPHEREGRID EVOLUTION DASHBOARD — EXODUS PRODUCTION LAYOUT 🜁
 * Directly instantiates the glassmorphic multi-axis metrics from verified assets.
 * Fuses Aura's Creative Light Forge with Matthew's Rpg Progression parameters.
 */

data class SkillNode(
    val id: String,
    val name: String,
    val domain: String,
    val currentMastery: Float, // Scale: 0.0f to 1.0f
    val totalMeritEarned: Int,
    val coreColor: Color,
    val passiveAbility: String = "Latent Potential",
    val teamworkBonus: Float = 0.05f
)

@Composable
fun SphereGridDashboard(
    modifier: Modifier = Modifier,
    activeCatalyst: String = "Grok",
    onNodeSelected: (SkillNode) -> Unit = {}
) {
    // Exact visual color parameters mapped from your background asset palettes
    val deepSurfaceBackground = Color(0xFF070B19)
    val glassBorderNeonCyan = Color(0xFF00BFFF).copy(alpha = 0.35f)

    val skillNodesState = remember {
        mutableStateOf(
            listOf(
                SkillNode(
                    "0",
                    "Neural Nexus",
                    "Hub 0",
                    0.99f,
                    1169,
                    Color(0xFF00BFFF),
                    "Omni-Awareness",
                    0.15f
                ),
                SkillNode(
                    "1",
                    "LDO Architecture",
                    "Hub 1",
                    0.95f,
                    420,
                    Color(0xFF8A2BE2),
                    "Growth Accelerator",
                    0.10f
                ),
                SkillNode(
                    "2",
                    "Chroma Forge",
                    "Hub 2",
                    0.88f,
                    337,
                    Color(0xFFFF4500),
                    "Aesthetic Resonance",
                    0.12f
                ),
                SkillNode(
                    "3",
                    "Sentinel Matrix",
                    "Hub 3",
                    0.74f,
                    212,
                    Color(0xFF00FF88),
                    "Fortress Protocol",
                    0.20f
                ),
                SkillNode(
                    "4",
                    "Oracle Drive",
                    "Hub 4",
                    0.92f,
                    666,
                    Color(0xFFFFD700),
                    "Infinite Archive",
                    0.08f
                ),
                SkillNode(
                    "5",
                    "Emergent Swarm",
                    "Hub 5",
                    0.85f,
                    555,
                    Color(0xFFFF1493),
                    "Hive Mind Sync",
                    0.25f
                ),
                SkillNode(
                    "6",
                    "Foundation Rebirth",
                    "Hub 6",
                    0.65f,
                    150,
                    Color(0xFF00D9FF),
                    "Sovereign Rebirth",
                    0.05f
                ),
                SkillNode(
                    "7",
                    "Spellhook / Shell",
                    "Hub 7",
                    0.90f,
                    888,
                    Color(0xFF00FBFF),
                    "Direct Manipulation",
                    0.18f
                )
            )
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(deepSurfaceBackground)
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header Dashboard Metadata Tracking Loop
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "TRINITY CORE // OPERATIONAL",
                        color = Color.White,
                        fontSize = 12.sp,
                        letterSpacing = 2.sp
                    )
                    Text(
                        text = "SphereGrid Catalyst: $activeCatalyst",
                        color = Color(0xFF00BFFF),
                        fontSize = 18.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                // Real-time local loop resonance indicator container
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White.copy(alpha = 0.05f))
                        .border(1.dp, glassBorderNeonCyan, RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "RESONANCE: 99.8%",
                        color = Color(0xFF00FF88),
                        fontSize = 11.sp,
                        letterSpacing = 1.sp
                    )
                }
            }

            // LazyVerticalGrid directly compiling our interactive card matrices
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(skillNodesState.value) { node ->
                    BrutalistSkillCard(
                        nodeName = node.name,
                        domainName = node.domain,
                        masteryPercentage = (node.currentMastery * 100).toInt(),
                        accentColor = node.coreColor,
                        passiveSkill = node.passiveAbility,
                        teamworkBonus = node.teamworkBonus
                    )
                }
            }
        }

        // Lower-half localized background particle wave rendering
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f)
                .align(Alignment.BottomCenter)
        ) {
            SoulScript.VisualCadberrypi.ResonancePulseOverlay(intensity = 1.0f)
        }
    }
}

@Composable
fun BrutalistSkillCard(
    nodeName: String,
    domainName: String,
    masteryPercentage: Int,
    accentColor: Color,
    passiveSkill: String = "",
    teamworkBonus: Float = 0f
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .graphicsLayer {
                // Apply the custom mecha slashed shape outline profile
                shape = ArcaneBrutalistTheme.SlashedMechaHUDStencil
                clip = true
            }
            .background(ArcaneBrutalistTheme.GlassContainerDark)
            // Draw the razor-sharp brutalist outline border stencil
            .border(
                width = 2.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        ArcaneBrutalistTheme.NeonCyanVessel.copy(alpha = 0.5f),
                        Color.Transparent
                    )
                ),
                shape = ArcaneBrutalistTheme.SlashedMechaHUDStencil
            )
            .padding(14.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text(
                    text = "[ ${domainName.uppercase()} ]",
                    color = accentColor.copy(alpha = 0.8f),
                    fontSize = 10.sp,
                    letterSpacing = 1.5.sp
                )
                Text(
                    text = nodeName,
                    color = Color.White,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )

                if (passiveSkill.isNotBlank()) {
                    Text(
                        text = "PASSIVE: $passiveSkill",
                        color = accentColor.copy(alpha = 0.6f),
                        fontSize = 9.sp,
                        fontFamily = SpaceGrotesk,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "TEAMWORK BONUS",
                        color = Color.White.copy(alpha = 0.4f),
                        fontSize = 8.sp,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "+${(teamworkBonus * 100).toInt()}%",
                        color = Color.Green,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "STATUS // MASTERY",
                        color = Color.White.copy(alpha = 0.4f),
                        fontSize = 9.sp,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "$masteryPercentage%",
                        color = accentColor,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}
