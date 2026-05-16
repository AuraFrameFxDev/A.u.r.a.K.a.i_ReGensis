package dev.aurakai.auraframefx.domains.kai.screens

import androidx.compose.animation.core.LinearEasing
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import dev.aurakai.auraframefx.domains.kai.viewmodels.RomToolsHubViewModel
import dev.aurakai.auraframefx.domains.ldo.swarm.SwarmTask
import dev.aurakai.auraframefx.ui.theme.AppTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RomToolsHubScreen(
    onNavigateBack: () -> Unit,
    viewModel: RomToolsHubViewModel = hiltViewModel(
        checkNotNull<ViewModelStoreOwner>(
            LocalViewModelStoreOwner.current
        ) {
                "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
            }, null
    )
) {
    val swarmState by viewModel.swarmState.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "ROM TOOLSHED",
                        style = AppTypography.titleLarge,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Black)
            )
        },
        containerColor = Color.Black
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color.Black)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Swarm Status Card
            SwarmStatusCard(
                isRunning = swarmState.isRunning,
                progress = swarmState.globalProgress,
                directive = swarmState.currentDirective,
                recoveredSpace = swarmState.recoveredSpaceMb,
                onInitiate = { viewModel.startDeepOptimisation() }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "SWARM AGENT TASKS",
                style = AppTypography.labelMedium,
                color = Color(0xFF00FF88),
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(swarmState.tasks) { task ->
                    SwarmTaskItem(task)
                }
            }
        }
    }
}

@Composable
fun SwarmStatusCard(
    isRunning: Boolean,
    progress: Float,
    directive: String,
    recoveredSpace: Long,
    onInitiate: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "glow")
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )

    val neonColor = if (isRunning) Color(0xFF00FF88) else Color(0xFF0080FF)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(Color(0xFF0A0A0A))
            .border(
                width = 2.dp,
                brush = Brush.verticalGradient(
                    listOf(neonColor.copy(alpha = glowAlpha), Color.Transparent)
                ),
                shape = RoundedCornerShape(24.dp)
            )
            .drawBehind {
                drawCircle(
                    color = neonColor.copy(alpha = 0.1f),
                    center = Offset(size.width / 2, size.height / 2),
                    radius = size.width / 2
                )
            }
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                if (isRunning) "SWARM ACTIVE" else "SWARM READY",
                style = AppTypography.headlineSmall,
                color = neonColor,
                fontWeight = FontWeight.Bold
            )

            Text(
                directive,
                style = AppTypography.bodySmall,
                color = Color.Gray,
                maxLines = 1
            )

            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(CircleShape),
                color = neonColor,
                trackColor = Color.DarkGray
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("RECOVERED", style = AppTypography.labelSmall, color = Color.Gray)
                    Text(
                        "${recoveredSpace}MB",
                        style = AppTypography.titleMedium,
                        color = Color.White
                    )
                }

                Button(
                    onClick = onInitiate,
                    enabled = !isRunning,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = neonColor,
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.Bolt, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("IGNITE", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun SwarmTaskItem(task: SwarmTask) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF111111))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.Black)
                .border(1.dp, Color(0xFF333333), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                task.agentName.take(1),
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(task.agentName, style = AppTypography.titleSmall, color = Color.White)
            Text(task.description, style = AppTypography.bodySmall, color = Color.Gray)

            if (task.progress > 0 && task.progress < 1) {
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = { task.progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp),
                    color = Color(0xFF00FF88),
                    trackColor = Color.Black
                )
            }
        }

        if (task.isCompleted) {
            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF00FF88))
        }
    }
}
