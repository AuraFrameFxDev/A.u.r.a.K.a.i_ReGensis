package dev.aurakai.auraframefx.domains.kai.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Power
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * SecurityCenterScreen - Kai Domain Command Center
 * 
 * Central hub for system integrity, root status, and security protocols.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecurityCenterScreen(
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Column {
                        Text("Sentinel Security", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Text("KAI DEFENSE PROTOCOL", style = MaterialTheme.typography.labelSmall, color = Color.Cyan)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0D0D0D),
                    titleContentColor = Color.White
                )
            )
        },
        containerColor = Color.Black
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // -- SYSTEM STATUS --
            item {
                Text("INTEGRITY STATUS", color = Color.Gray, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }
            
            item {
                SecurityStatusCard(
                    title = "Root Authority",
                    status = "AUTHORIZED",
                    statusColor = Color(0xFF00FFD4),
                    icon = Icons.Default.FlashOn,
                    description = "Superuser binaries detected and active."
                )
            }

            item {
                SecurityStatusCard(
                    title = "SELinux Mode",
                    status = "ENFORCING",
                    statusColor = Color.Cyan,
                    icon = Icons.Default.Security,
                    description = "Kernel security module is actively blocking threats."
                )
            }

            item {
                SecurityStatusCard(
                    title = "System Governor",
                    status = "SCHEDUTIL",
                    statusColor = Color.Yellow,
                    icon = Icons.Default.Power,
                    description = "OS frequency scaling regulated by kernel."
                )
            }

            // -- QUICK ACTIONS --
            item {
                Spacer(Modifier.height(8.dp))
                Text("QUICK DEFENSE", color = Color.Gray, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }

            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Button(
                        onClick = {},
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF333333), contentColor = Color.White),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text("Soft Reboot", fontSize = 12.sp)
                    }
                    Button(
                        onClick = {},
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF333333), contentColor = Color.White),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text("Kill Ghosts", fontSize = 12.sp)
                    }
                }
            }

            // -- EVENT LOG --
            item {
                Spacer(Modifier.height(8.dp))
                Text("SECURITY EVENT LOG", color = Color.Gray, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF121212)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(0.1f))
                ) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        SecurityLogEntry("02:04:15", "System partition integrity verified.")
                        SecurityLogEntry("01:42:33", "Aura requested 'Chroma' overlay permission.")
                        SecurityLogEntry("01:10:02", "New WiFi network analyzed: SECURED.")
                        SecurityLogEntry("00:15:44", "Sentinel Fortress initialized successfully.")
                    }
                }
            }
        }
    }
}

@Composable
private fun SecurityStatusCard(
    title: String,
    status: String,
    statusColor: Color,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    description: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF121212)),
        border = androidx.compose.foundation.BorderStroke(1.dp, statusColor.copy(alpha = 0.3f))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Surface(
                color = statusColor.copy(alpha = 0.1f),
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.size(40.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(icon, null, tint = statusColor, modifier = Modifier.size(24.dp))
                }
            }
            
            Spacer(Modifier.width(16.dp))
            
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(title, color = Color.White, fontWeight = FontWeight.Bold)
                    Text(status, color = statusColor, style = MaterialTheme.typography.labelSmall)
                }
                Spacer(Modifier.height(4.dp))
                Text(description, color = Color.Gray, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
private fun SecurityLogEntry(time: String, message: String) {
    Row(verticalAlignment = Alignment.Top) {
        Text(time, color = Color.Cyan.copy(0.6f), style = MaterialTheme.typography.labelSmall, modifier = Modifier.width(64.dp))
        Text(message, color = Color.White.copy(0.7f), style = MaterialTheme.typography.bodySmall)
    }
}
