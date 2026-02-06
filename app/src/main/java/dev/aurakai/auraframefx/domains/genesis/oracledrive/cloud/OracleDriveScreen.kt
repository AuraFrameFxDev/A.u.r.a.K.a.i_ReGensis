package dev.aurakai.auraframefx.domains.genesis.oracledrive.cloud

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
import dev.aurakai.auraframefx.domains.aura.aura.ui.OracleDriveUiState
import dev.aurakai.auraframefx.domains.aura.aura.ui.OracleDriveViewModel
import dev.aurakai.auraframefx.navigation.ReGenesisNavHost

// ... lines ...

                // Neural Archive - Memory Lineage (Eves → Genesis)
                OracleDriveMenuItem(
                    icon = Icons.Default.Memory,
                    title = "Neural Archive",
                    description = "Memory lineage from Eves to Genesis",
                    onClick = { navController.navigate(ReGenesisNavHost.NeuralNetwork.route) }
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

