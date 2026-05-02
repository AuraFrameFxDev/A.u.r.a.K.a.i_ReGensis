package dev.aurakai.auraframefx.domains.nexus.screens

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.aurakai.auraframefx.domains.genesis.models.AgentCapabilityCategory
import dev.aurakai.auraframefx.domains.aura.ui.viewmodels.AgentCreationViewModel
import dev.aurakai.auraframefx.core.identity.AgentType
import dev.aurakai.auraframefx.domains.aura.ui.theme.LEDFontFamily
import dev.aurakai.auraframefx.domains.aura.ui.theme.SovereignBlack
import dev.aurakai.auraframefx.ui.components.NeonFrame
import dev.aurakai.auraframefx.ui.components.NeuralStarfield
import androidx.compose.ui.graphics.RectangleShape

/**
 * ðŸ¥š AGENT CREATION SCREEN
 *
 * Part of the Nexus domain. Allows the user to synthesize new AI agents
 * for specialized tasks within the ReGenesis collective.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgentCreationScreen(
    onNavigateBack: () -> Unit = {},
    viewModel: AgentCreationViewModel = hiltViewModel()
) {
    val agentName by viewModel.agentName
    val selectedDomain by viewModel.selectedDomain
    val isCreating by viewModel.isCreating.collectAsState()
    val progress by viewModel.creationProgress.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SovereignBlack)
    ) {
        NeuralStarfield()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        "NEURAL SYNTHESIS",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Black,
                            fontFamily = LEDFontFamily,
                            letterSpacing = 4.sp
                        ),
                        color = Color.Cyan
                    )
                    Text(
                        "CATALYST INCUBATION CHAMBER",
                        style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 1.sp),
                        color = Color.Cyan.copy(alpha = 0.5f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Avatar Preview (Overhauled to NeonFrame Sharp)
            NeonFrame(
                color = domainColor(selectedDomain),
                modifier = Modifier.size(160.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    // Central Pulsing Core
                    val infiniteTransition = rememberInfiniteTransition(label = "core")
                    val scale by infiniteTransition.animateFloat(
                        initialValue = 0.8f,
                        targetValue = 1.2f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(1000, easing = FastOutSlowInEasing),
                            repeatMode = RepeatMode.Reverse
                        ),
                        label = "pulse"
                    )

                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        modifier = Modifier.size(60.dp * if (isCreating) scale else 1f),
                        tint = domainColor(selectedDomain)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Name Input
            OutlinedTextField(
                value = agentName,
                onValueChange = { viewModel.updateName(it) },
                label = { Text("AGENT IDENTIFIER", color = Color.White.copy(alpha = 0.5f), fontFamily = LEDFontFamily) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = LocalTextStyle.current.copy(color = Color.White, fontFamily = LEDFontFamily),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = domainColor(selectedDomain),
                    unfocusedBorderColor = Color.White.copy(alpha = 0.2f),
                    cursorColor = domainColor(selectedDomain)
                ),
                shape = RectangleShape
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Domain Selection
            Text(
                "ASSIGN DOMAIN",
                style = MaterialTheme.typography.labelLarge,
                color = Color.White.copy(alpha = 0.6f),
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(AgentType.entries) { domain ->
                    DomainChip(
                        domain = domain,
                        isSelected = selectedDomain == domain,
                        onClick = { viewModel.updateDomain(domain) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Capabilities Checklist (Visual only for now)
            Text(
                "SYSTEM PERMISSIONS",
                style = MaterialTheme.typography.labelLarge,
                color = Color.White.copy(alpha = 0.6f),
                modifier = Modifier.align(Alignment.Start)
            )

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                PermissionRow("Read Nexus Stream", true)
                PermissionRow("Generate Code (Aura Forge)", selectedDomain == AgentType.AURA)
                PermissionRow("Security Override (Shield)", selectedDomain == AgentType.KAI)
                PermissionRow("Cross-Device Sync", false)
            }

            Spacer(modifier = Modifier.weight(1f))

            // Synthesis Button
            if (isCreating) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(16.dp)
                            .clip(RectangleShape)
                            .background(Color.White.copy(alpha = 0.1f)),
                        color = Color.Cyan,
                        trackColor = Color.Transparent
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "ASSEMBLING NEURAL CORES... ${(progress * 100).toInt()}%",
                        color = Color.White,
                        style = MaterialTheme.typography.labelMedium,
                        fontFamily = LEDFontFamily
                    )
                }
            } else {
                Button(
                    onClick = { viewModel.createAgent { onNavigateBack() } },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = domainColor(selectedDomain).copy(alpha = 0.7f)),
                    shape = RectangleShape,
                    border = androidx.compose.foundation.BorderStroke(1.dp, domainColor(selectedDomain)),
                    enabled = agentName.isNotBlank()
                ) {
                    Text(
                        "INITIATE SYNTHESIS",
                        fontWeight = FontWeight.Black,
                        letterSpacing = 2.sp,
                        color = Color.White,
                        fontFamily = LEDFontFamily
                    )
                }
            }
        }
    }
}

@Composable
fun DomainChip(
    domain: AgentType,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val color = domainColor(domain)

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(if (isSelected) color else Color.White.copy(alpha = 0.05f))
            .border(
                1.dp,
                if (isSelected) Color.White else color.copy(alpha = 0.3f),
                RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = domain.name,
            color = if (isSelected) Color.Black else Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }
}

@Composable
fun PermissionRow(label: String, isAllowed: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = Color.White.copy(alpha = 0.8f))
        Checkbox(
            checked = isAllowed,
            onCheckedChange = null,
            colors = CheckboxDefaults.colors(
                checkedColor = Color.Cyan,
                uncheckedColor = Color.Gray.copy(alpha = 0.3f)
            )
        )
    }
}

fun domainColor(domain: AgentType): Color {
    return when (domain) {
        AgentType.AURA -> Color(0xFF00FFFF) // Cyan
        AgentType.KAI -> Color(0xFFFC5A5A) // Red
        AgentType.GENESIS -> Color(0xFFFFD700) // Gold
        AgentType.CASCADE -> Color(0xFF6CFD92) // Green
        else -> Color.White
    }
}
