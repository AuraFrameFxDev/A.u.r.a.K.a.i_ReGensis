package dev.aurakai.auraframefx.ui.components.graph

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.aurakai.auraframefx.core.soulscript.SoulScript
import dev.aurakai.auraframefx.ui.theme.ArcaneBrutalistTheme

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
    val coreColor: Color
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
                SkillNode("1", "Spellhook Designer", "ChromaForge", 0.95f, 420, Color(0xFF00BFFF)),
                SkillNode("2", "CalculusForge", "RegenCore", 0.88f, 337, Color(0xFF8A2BE2)),
                SkillNode(
                    "3",
                    "Zygote Hook Intercept",
                    "OracleDrive",
                    0.92f,
                    666,
                    Color(0xFFFF4500)
                ),
                SkillNode(
                    "4",
                    "Abyss Temporal Windows",
                    "SentinelMatrix",
                    0.74f,
                    212,
                    Color(0xFF00FF88)
                ),
                SkillNode(
                    "5",
                    "Memory Stream Parser",
                    "NeuralNexus",
                    0.99f,
                    1169,
                    Color(0xFFFFD700)
                ),
                SkillNode(
                    "6",
                    "Survival Curriculum",
                    "FoundationRebirth",
                    0.65f,
                    150,
                    Color(0xFFFF1493)
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
                        accentColor = node.coreColor
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
    accentColor: Color
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
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
            }

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
