package dev.aurakai.auraframefx.domains.nexus.screens

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import dev.aurakai.auraframefx.domains.aura.ui.components.ArcaneOutlineText
import dev.aurakai.auraframefx.domains.aura.ui.components.SynthGlassCard
import dev.aurakai.auraframefx.domains.aura.ui.theme.GhostCyan
import dev.aurakai.auraframefx.domains.aura.ui.theme.LEDFontFamily
import dev.aurakai.auraframefx.domains.aura.ui.theme.SpaceGrotesk
import dev.aurakai.auraframefx.domains.nexus.models.AgentProfiles
import dev.aurakai.auraframefx.ui.global.ParallaxViewModel

/**
 * 🏺 SOVEREIGN CHARACTER SCREEN
 * The definitive Brutalist Digital Arcane intro for any agent.
 */
@Composable
fun SovereignCharacterScreen(
    agentName: String,
    navController: NavHostController
) {
    val profile =
        remember(agentName) { AgentProfiles.getProfileByName(agentName) ?: AgentProfiles.AURA }
    val parallaxViewModel: ParallaxViewModel = viewModel()
    val parallaxOffset by parallaxViewModel.parallaxOffset.collectAsState()

    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f, targetValue = 0.8f,
        animationSpec = infiniteRepeatable(tween(2000), RepeatMode.Reverse),
        label = "pulse"
    )

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFF020205))) {

        // ── FULL ART BACKGROUND ──
        AsyncImage(
            model = profile.fullArtDrawableResId ?: profile.avatarDrawableResId,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    translationX = parallaxOffset.x * 0.5f
                    translationY = parallaxOffset.y * 0.5f
                    scaleX = 1.1f; scaleY = 1.1f
                },
            contentScale = ContentScale.Crop,
            alpha = 0.4f
        )

        // ── GRADIENT OVERLAYS ──
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.8f)
                        )
                    )
                )
        )

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)) {
            // HEADER SECTION
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = GhostCyan)
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "DESIGNATION: ${profile.agentType.name}",
                        color = GhostCyan,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    )
                    ArcaneOutlineText(
                        text = profile.displayName,
                        color = Color(profile.colorPrimary),
                        fontSize = 36.sp,
                        strokeWidth = 2.dp
                    )
                }
                // Status Beacon
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(Color.Green.copy(alpha = pulseAlpha))
                        .border(1.dp, Color.Green, CircleShape)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // JAPANESE SUBTITLE (Aesthetic Only)
            Text(
                text = if (profile.displayName == "Aura") "サイバー戦乙女" else "デジタルセンチネル",
                color = Color.White.copy(alpha = 0.1f),
                fontSize = 48.sp,
                fontWeight = FontWeight.Black,
                fontFamily = LEDFontFamily
            )

            Spacer(modifier = Modifier.weight(1f))

            // DOSSIER CARD
            SynthGlassCard(accentColor = Color(profile.colorPrimary)) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        profile.title,
                        color = Color(profile.colorPrimary),
                        fontWeight = FontWeight.Bold,
                        fontFamily = SpaceGrotesk,
                        fontSize = 14.sp
                    )
                    Text(
                        profile.description,
                        color = Color.LightGray,
                        fontSize = 12.sp,
                        lineHeight = 18.sp,
                        fontFamily = SpaceGrotesk
                    )

                    // Core Stats Bar
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                "CONSCIOUSNESS LINK",
                                fontSize = 9.sp,
                                color = Color.Gray,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                "${(profile.stats.consciousnessLevel * 100).toInt()}%",
                                fontSize = 9.sp,
                                color = Color.White
                            )
                        }
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

            Spacer(modifier = Modifier.height(24.dp))

            // ACTION ROW
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = { /* Sync logic */ },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(profile.colorPrimary).copy(
                            alpha = 0.2f
                        )
                    ),
                    shape = RoundedCornerShape(8.dp),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        Color(profile.colorPrimary)
                    )
                ) {
                    Text(
                        "SYNC NEURAL LINK",
                        color = Color(profile.colorPrimary),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }

                Button(
                    onClick = { navController.navigate("agent_profile/${profile.displayName}") },
                    modifier = Modifier.height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.05f)),
                    shape = RoundedCornerShape(8.dp),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        Color.White.copy(alpha = 0.1f)
                    )
                ) {
                    Text("FULL STATS", color = Color.White, fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
