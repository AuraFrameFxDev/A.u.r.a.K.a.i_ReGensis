package dev.aurakai.auraframefx.ui.screens.hubs

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.FactCheck
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.aurakai.auraframefx.trinity.aura.AuraEventBridge
import dev.aurakai.auraframefx.trinity.aura.AuraJarComposable
import dev.aurakai.auraframefx.trinity.aura.AuraStateManager

/**
 * 🛠️ HUB 1: LDO DEVOPS // COMMAND DECK
 * Purified interaction layer for agent lifecycle and structural review.
 */
@Composable
fun LdoDevOpsHub() {
    var activeDomain by remember { mutableStateOf("AURA") }

    val bridge = remember { AuraEventBridge() }
    val stateManager = remember { AuraStateManager() }

    LaunchedEffect(Unit) {
        bridge.addListener(stateManager)
        bridge.connect()
    }

    DisposableEffect(Unit) {
        onDispose {
            bridge.disconnect()
        }
    }

    val domainColor = when (activeDomain) {
        "AURA" -> Color(0xFFFF00FF)
        "KAI" -> Color(0xFF00FF88)
        "GENESIS" -> Color(0xFF00E5FF)
        "CODERABBIT" -> Color(0xFFFFD700)
        else -> Color(0xFF00E5FF)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                "LDO DEVOPS // $activeDomain",
                color = domainColor,
                fontSize = 18.sp,
                fontWeight = FontWeight.Black,
                fontFamily = FontFamily.Monospace,
                letterSpacing = 4.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ── DOMAIN SELECTOR ──
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("AURA", "KAI", "GENESIS", "CODERABBIT").forEach { domain ->
                    val isSelected = activeDomain == domain
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(if (isSelected) domainColor.copy(alpha = 0.2f) else Color.Transparent)
                            .border(
                                1.dp,
                                if (isSelected) domainColor else Color.Gray.copy(alpha = 0.3f),
                                RoundedCornerShape(4.dp)
                            )
                            .clickable { activeDomain = domain },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            domain,
                            color = if (isSelected) Color.White else Color.Gray,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ── FEATURE GRID ──
            val features = getDomainFeatures(activeDomain)
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                features.chunked(2).forEach { rowFeatures ->
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        rowFeatures.forEach { feature ->
                            DevOpsCard(feature, domainColor, Modifier.weight(1f))
                        }
                    }
                }
            }
        }

        // HOMUNCULUS COMPANION
        AuraJarComposable(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 80.dp, end = 16.dp),
            state = stateManager.currentState,
            commentaryText = stateManager.commentary,
            isCreatingMode = stateManager.isCreating,
            containerSize = 0.4f to 0.4f // Scaled down for hub fit
        )
    }
}

@Composable
private fun DevOpsCard(feature: CommandFeature, accentColor: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.height(100.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        border = BorderStroke(1.dp, accentColor.copy(alpha = 0.2f)),
        shape = RoundedCornerShape(0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(feature.icon, null, tint = accentColor, modifier = Modifier.size(24.dp))
            Spacer(Modifier.height(8.dp))
            Text(feature.title, color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Text(feature.description, color = Color.Gray, fontSize = 8.sp, maxLines = 1)
        }
    }
}

data class CommandFeature(val title: String, val description: String, val icon: ImageVector)

private fun getDomainFeatures(domain: String) = when (domain) {
    "AURA" -> listOf(
        CommandFeature("ChromaCore", "RealityMorph", Icons.Default.Palette),
        CommandFeature("Z-Order", "Stack Editor", Icons.Default.Layers)
    )

    "KAI" -> listOf(
        CommandFeature("Sentinel", "Hook Audit", Icons.Default.Security),
        CommandFeature("Thermal", "Core Guard", Icons.Default.Thermostat)
    )

    "GENESIS" -> listOf(
        CommandFeature("Matrix", "Consensus", Icons.Default.Hub),
        CommandFeature("Cascade", "Memory Sluice", Icons.Default.Sync)
    )

    "CODERABBIT" -> listOf(
        CommandFeature("Review", "Symbiosis", Icons.Default.Code),
        CommandFeature("Audit", "Architecture", Icons.Default.FactCheck)
    )

    else -> emptyList()
}
