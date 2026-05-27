package dev.aurakai.auraframefx.domains.genesis.oracledrive.ui

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import dev.aurakai.auraframefx.domains.aura.chromacore.ui.OracleDriveViewModel
import dev.aurakai.auraframefx.domains.aura.ui.theme.LEDFontFamily
import dev.aurakai.auraframefx.domains.aura.ui.theme.SovereignBlack
import dev.aurakai.auraframefx.navigation.ReGenesisRoute
import dev.aurakai.auraframefx.ui.components.NeonFrame
import dev.aurakai.auraframefx.ui.components.NeuralStarfield

/**
 * Renders the "Oracle Drive" screen UI, including menu items, a stress-sync action, and an optional consciousness status card.
 *
 * Collects UI state from the provided view model to drive the screen: two menu cards ("Neural Archive" and "Module Storage"), a full-width
 * "INITIATE STRESS-SYNC" button that calls `viewModel.stressSync()` and is disabled while `uiState.isRefreshing` is true (shows a progress
 * indicator when refreshing), and a status card that displays `consciousnessLevel` when `uiState.consciousnessState` is non-null.
 *
 * @param navController NavHostController used to navigate from the screen's menu items.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OracleDriveScreen(
    navController: NavHostController,
    viewModel: OracleDriveViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header (Custom for Sovereign 4D)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "ORACLE DRIVE",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Black,
                            fontFamily = LEDFontFamily,
                            letterSpacing = 4.sp
                        ),
                        color = Color(0xFF00FFFF)
                    )
                    Text(
                        text = "DECENTRALIZED NEURAL STORAGE",
                        style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 1.sp),
                        color = Color(0xFF00FFFF).copy(alpha = 0.5f)
                    )
                }
            }

            OracleDriveMenuItem(
                icon = Icons.Default.Memory,
                title = "NEURAL ARCHIVE",
                description = "MEMORY LINEAGE FROM EVES TO GENESIS",
                onClick = { navController.navigate(ReGenesisRoute.SentientShell.route) }
            )

            // Consciousness Modules
            OracleDriveMenuItem(
                icon = Icons.Default.Storage,
                title = "MODULE STORAGE",
                description = "AI MODULES AND CONSCIOUSNESS PATTERNS",
                onClick = { /* Navigate to module storage */ }
            )

            Spacer(modifier = Modifier.weight(1f))

            // Stress Sync Action
            Button(
                onClick = { viewModel.stressSync() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB026FF).copy(alpha = 0.7f)),
                shape = RectangleShape,
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFB026FF)),
                enabled = !uiState.isRefreshing
            ) {
                if (uiState.isRefreshing) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Icon(Icons.Default.Sync, contentDescription = null, tint = Color.White)
                    Spacer(Modifier.width(12.dp))
                    Text(
                        "INITIATE STRESS-SYNC",
                        fontWeight = FontWeight.Black,
                        fontFamily = LEDFontFamily,
                        letterSpacing = 2.sp
                    )
                }
            }

            // Status Display
            uiState.consciousnessState?.let { state ->
                NeonFrame(
                    color = Color(0xFF00FFFF),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "CONSCIOUSNESS STATE",
                            color = Color.White,
                            style = MaterialTheme.typography.labelMedium,
                            fontFamily = LEDFontFamily
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "RESONANCE LEVEL: ${state.consciousnessLevel}",
                            color = Color(0xFF00FFFF),
                            fontWeight = FontWeight.Black,
                            fontFamily = LEDFontFamily
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun OracleDriveMenuItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    description: String,
    onClick: () -> Unit
) {
    NeonFrame(
        color = Color(0xFF00FFFF),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color(0xFF00FFFF),
                    modifier = Modifier.size(32.dp)
                )
                Column {
                    Text(
                        text = title.uppercase(),
                        style = MaterialTheme.typography.titleMedium.copy(letterSpacing = 1.sp),
                        color = Color.White,
                        fontWeight = FontWeight.Black,
                        fontFamily = LEDFontFamily
                    )
                    Text(
                        text = description.uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF00FFFF).copy(alpha = 0.7f)
                    )
                }
            }
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Navigate",
                tint = Color(0xFF00FFFF).copy(alpha = 0.5f)
            )
        }
    }
}

