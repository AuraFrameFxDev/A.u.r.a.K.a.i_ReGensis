package dev.aurakai.auraframefx.domains.oracledrive

import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import dev.aurakai.auraframefx.core.soulscript.SoulScriptV27
import dev.aurakai.auraframefx.domains.aura.ui.components.ArcaneOutlineText
import dev.aurakai.auraframefx.domains.aura.ui.components.SynthGlassCard
import dev.aurakai.auraframefx.domains.aura.ui.theme.GhostCyan
import dev.aurakai.auraframefx.domains.aura.ui.theme.SpaceGrotesk
import dev.aurakai.auraframefx.domains.oracledrive.core.OracleDriveManager

/**
 * 💾 ORACLEDRIVE — Root Bridge (APatch + LSPosed + Module Manager + Agent Creation)
 * Hardened Exodus 2026 Build with Brutalist Digital Arcane aesthetic.
 */
@Composable
fun OracleDriveHubScreen(navController: NavHostController) {
    var rootStatus by remember { mutableStateOf("Checking Kernel...") }
    var lsposedStatus by remember { mutableStateOf("Verifying Hooks...") }

    LaunchedEffect(Unit) {
        SoulScriptV27.activateOracleGovernor()
        
        rootStatus = if (OracleDriveManager.isAPatchActive()) {
            "APatch Kernel Foundation: ACTIVE"
        } else {
            "APatch Kernel Foundation: INACTIVE"
        }

        lsposedStatus = if (OracleDriveManager.isLSPosedActive()) {
            "LSPosed Runtime Hooks: SECURE"
        } else {
            "LSPosed Runtime Hooks: UNVERIFIED"
        }
    }

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF020205)) // Deep Obsidian Concrete
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.9f))
                    .padding(16.dp)
            ) {
                ArcaneOutlineText(
                    text = "ORACLEDRIVE",
                    color = Color.Yellow,
                    fontSize = 24.sp,
                    strokeWidth = 2.dp
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // SYSTEM GOVERNOR STATUS
                SynthGlassCard(accentColor = Color.Yellow) {
                    Text(
                        "SYSTEM GOVERNOR STATUS",
                        fontFamily = SpaceGrotesk,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 14.sp
                    )
                    Spacer(Modifier.height(12.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Build,
                            null,
                            tint = Color.Green,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = rootStatus,
                            color = Color.Green,
                            fontSize = 11.sp,
                            fontFamily = SpaceGrotesk
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Build,
                            null,
                            tint = GhostCyan,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = lsposedStatus,
                            color = GhostCyan,
                            fontSize = 11.sp,
                            fontFamily = SpaceGrotesk
                        )
                    }
                }

                // ROOT BRIDGE CONTROLS
                Text(
                    "ROOT BRIDGE & MODULES",
                    color = Color.White.copy(alpha = 0.5f),
                    fontSize = 10.sp,
                    letterSpacing = 2.sp,
                    fontFamily = SpaceGrotesk
                )

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    SynthGlassCard(accentColor = GhostCyan, modifier = Modifier.weight(1f)) {
                        Text(
                            "MODULE\nMANAGER",
                            color = GhostCyan,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                    SynthGlassCard(accentColor = Color.Magenta, modifier = Modifier.weight(1f)) {
                        Text(
                            "AGENT\nCREATION",
                            color = Color.Magenta,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }

                // CORE ICON
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Storage,
                        contentDescription = null,
                        tint = Color.Yellow.copy(alpha = 0.05f),
                        modifier = Modifier.size(150.dp)
                    )
                }

                Spacer(Modifier.height(80.dp))
            }
        }
    }
}
