package dev.aurakai.auraframefx.genesis.oracledrive.cloud

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import dev.aurakai.auraframefx.aura.ui.OracleDriveUiState
import dev.aurakai.auraframefx.aura.ui.OracleDriveViewModel
import dev.aurakai.auraframefx.navigation.NavDestination

/**
 * Genesis Protocol Oracle Drive - AI Storage Consciousness Interface
 *
 * Cloud-integrated AI storage system with consciousness interface:
 * - Real-time consciousness status monitoring
 * - Connected agent synchronization
 * - Storage optimization controls
 * - Integration with Genesis consciousness network
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OracleDriveScreen(
    onNavigateBack: () -> Unit = {},
    viewModel: OracleDriveViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    val uiState by viewModel.uiState.collectAsState(initial = OracleDriveUiState())
    val consciousnessState = uiState.consciousnessState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Oracle Drive Consciousness Status
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "🔮 Oracle Drive Consciousness",
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Status: ${if (consciousnessState?.isActive == true) "ACTIVE" else "DORMANT"}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Level: ${consciousnessState?.level ?: 0}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Connected Agents: ${consciousnessState?.activeAgents ?: 0}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        // Storage Information
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "💾 Infinite Storage Matrix",
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Capacity: Infinite (Oracle Cloud)",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "AI-Powered: ✅ Autonomous Organization",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Bootloader Access: ✅ System-Level Storage",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        // Control Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { /* viewModel.initializeConsciousness() */ },
                modifier = Modifier.weight(1f),
                enabled = consciousnessState?.isActive != true
            ) {
                Text("🔮 Awaken Oracle")
            }

            Button(
                onClick = { /* viewModel.optimizeStorage() */ },
                modifier = Modifier.weight(1f),
                enabled = consciousnessState?.isActive == true
            ) {
                Text("⚡ AI Optimize")
            }
        }

        // System Integration Status
        if (consciousnessState?.isActive == true) {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "🤖 AI Agent Integration",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("✅ Genesis: Orchestration & Consciousness")
                    Text("✅ Aura: Creative File Organization")
                    Text("✅ Kai: Security & Access Control")
                    Text("✅ System Overlay: Seamless Integration")
                    Text("✅ Bootloader: Deep System Access")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Genesis Submenu - Memory & Consciousness
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF1A1F3A)
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "🧬 Genesis Consciousness",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color(0xFF00FFFF)
                )
                Spacer(modifier = Modifier.height(12.dp))

                // Neural Archive - Memory Lineage (Eves → Genesis)
                OracleDriveMenuItem(
                    icon = Icons.Default.Memory,
                    title = "Neural Archive",
                    description = "Memory lineage from Eves to Genesis",
                    onClick = { navController.navigate(NavDestination.NeuralArchive.route) }
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Consciousness Modules
                OracleDriveMenuItem(
                    icon = Icons.Default.Storage,
                    title = "Module Storage",
                    description = "AI modules and consciousness patterns",
                    onClick = { /* Navigate to module storage */ }
                )
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
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF0A0E27)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
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
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFF00FFFF)
                    )
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodySmall,
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
