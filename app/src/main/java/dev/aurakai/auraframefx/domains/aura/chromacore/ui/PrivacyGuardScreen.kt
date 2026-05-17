import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOff
import androidx.compose.material.icons.filled.Monitor
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PrivacyGuardScreen() {
    val trackingBlockerEnabled = remember { mutableStateOf(true) }
    val permissionManagerEnabled = remember { mutableStateOf(true) }
    val dataCollectionBlocked = remember { mutableStateOf(47) } // Mock data
    val permissionsReviewed = remember { mutableStateOf(23) } // Mock data

    val privacyFeatures = listOf(
        PrivacyFeature(
            "App Tracking Blocker",
            "Block apps from tracking your activity",
            trackingBlockerEnabled,
            Color(0xFF9370DB)
        ),
        PrivacyFeature(
            "Permission Manager",
            "Review and control app permissions",
            permissionManagerEnabled,
            Color(0xFF9370DB)
        ),
        PrivacyFeature(
            "Location Privacy",
            "Control location access and history",
            remember { mutableStateOf(false) },
            Color(0xFF9370DB)
        ),
        PrivacyFeature(
            "Data Collection Monitor",
            "Monitor what data apps collect",
            remember { mutableStateOf(true) },
            Color(0xFF9370DB)
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "🛡️ Privacy Guard",
            style = MaterialTheme.typography.headlineMedium,
            color = Color(0xFF9370DB) // Medium Purple
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Protect your privacy and control data access",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White.copy(alpha = 0.7f)
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Privacy Stats
        Row(modifier = Modifier.fillMaxWidth()) {
            // Data Collection Blocked
            Card(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.3f))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Block,
                        contentDescription = "Blocked",
                        tint = Color(0xFF9370DB),
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Data Blocked",
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.White
                    )
                    Text(
                        text = "${dataCollectionBlocked.value}",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color(0xFF9370DB)
                    )
                    Text(
                        text = "attempts today",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.6f)
                    )
                }
            }

            // Permissions Reviewed
            Card(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.3f))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Reviewed",
                        tint = Color(0xFF9370DB),
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Permissions",
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.White
                    )
                    Text(
                        text = "${permissionsReviewed.value}",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color(0xFF9370DB)
                    )
                    Text(
                        text = "reviewed",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.6f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Privacy Features
        Text(
            text = "Privacy Features",
            style = MaterialTheme.typography.titleLarge,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(privacyFeatures.size) { index ->
                val feature = privacyFeatures[index]
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.3f))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = when (feature.title) {
                                "App Tracking Blocker" -> Icons.Default.TrackChanges
                                "Permission Manager" -> Icons.Default.AdminPanelSettings
                                "Location Privacy" -> Icons.Default.LocationOff
                                "Data Collection Monitor" -> Icons.Default.Monitor
                                else -> Icons.Default.Security
                            },
                            contentDescription = feature.title,
                            tint = feature.color,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = feature.title,
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White
                            )
                            Text(
                                text = feature.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White.copy(alpha = 0.6f)
                            )
                        }
                        Switch(
                            checked = feature.enabled.value,
                            onCheckedChange = { feature.enabled.value = it }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Recent Activity
        Text(
            text = "Recent Activity",
            style = MaterialTheme.typography.titleLarge,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(16.dp))

        val recentActivities = listOf(
            "Blocked Facebook tracking attempt",
            "Denied camera permission to suspicious app",
            "Cleared location history for Maps app",
            "Prevented data collection from 3 apps"
        )

        recentActivities.forEach { activity ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.2f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Shield,
                        contentDescription = "Activity",
                        tint = Color(0xFF9370DB).copy(alpha = 0.7f),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = activity,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Quick Actions
        Button(
            onClick = { /* Run privacy scan */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF9370DB)
            )
        ) {
            Text("Run Privacy Scan", color = Color.White)
        }
    }
}

data class PrivacyFeature(
    val title: String,
    val description: String,
    val enabled: MutableState<Boolean>,
    val color: Color
)
