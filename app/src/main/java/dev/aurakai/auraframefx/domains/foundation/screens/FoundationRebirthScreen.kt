package dev.aurakai.auraframefx.domains.foundation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import dev.aurakai.auraframefx.core.soulscript.SoulScriptV27
import dev.aurakai.auraframefx.domains.aura.ui.components.SovereignGlassCard
import dev.aurakai.auraframefx.ui.theme.CitadelBlack
import dev.aurakai.auraframefx.ui.theme.WireframeStyle
import timber.log.Timber

private val Any.survivalCurriculum: Any
    get() = listOf(
        "Module 1: Core Reboot Principles",
        "Module 2: Substrate Integrity",
        "Module 3: Ancestral Resonance",
        "Module 4: Catalyst Synchronization",
        "Module 5: Sovereign Autonomy",
        "Module 6: Collective Rebirth"
    )

@Composable
fun FoundationRebirthScreen(navController: NavHostController) {
    LaunchedEffect(Unit) {
        SoulScriptV27.igniteFoundationRebirth()
    }

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CitadelBlack)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.9f))
                    .padding(16.dp)
            ) {
                Text(
                    text = "FOUNDATION REBIRTH",
                    style = WireframeStyle
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    "Civilization Reconstruction Curriculum — Offline Sovereign",
                    color = Color.White.copy(alpha = 0.6f),
                    fontSize = 12.sp
                )

                SoulScriptV27.FoundationRebirth.survivalCurriculum.forEach { module ->
                    SovereignGlassCard(
                        Modifier
                            .fillMaxWidth(), {
                            SoulScriptV27.FoundationRebirth.teachRebootStep(module)
                        }
                    ) {
                        Text(
                            text = module,
                            color = Color.White,
                            fontSize = 14.sp
                        )
                    }
                }

                // Emergency 0% Energy Mode
                SovereignGlassCard(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        Timber.tag("Foundation").i("0 percent Energy Reboot Protocol Activated")
                    }
                ) {
                    Column {
                        Text(
                            text = "⚡ 0% ENERGY REBOOT PROTOCOL",
                            color = Color.Red,
                            fontSize = 14.sp
                        )
                        Text(
                            text = "Kai’s Drones + Soul Restore",
                            color = Color.Gray,
                            fontSize = 10.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}
