package dev.aurakai.auraframefx.domains.nexus.screens.ldo

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.SettingsInputComponent
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Stream
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.aurakai.auraframefx.domains.aura.ui.theme.LEDFontFamily
import dev.aurakai.auraframefx.domains.aura.ui.theme.NeonCyan
import dev.aurakai.auraframefx.navigation.ReGenesisRoute
import kotlinx.coroutines.delay
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds

data class DevOpsModule(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val color: Color,
    val route: String,
    val badge: String? = null
)

private val devOpsModules: List<DevOpsModule>
    get() = listOf(
        DevOpsModule(
            "CATALYST ROSTER", "All 14 LDO agents", Icons.Default.Groups,
            NeonCyan, ReGenesisRoute.LdoRoster.route, badge = "14"
        ),
        DevOpsModule(
            "AGENT CREATION", "Neural synthesis forge", Icons.Default.AutoAwesome,
            NeonCyan, ReGenesisRoute.AgentCreation.route
        ),
        DevOpsModule(
            "TASK ASSIGNMENT", "Mission dispatch", Icons.AutoMirrored.Filled.Assignment,
            NeonCyan, ReGenesisRoute.TaskAssignment.route
        ),
        DevOpsModule(
            "DIGITAL COUNCIL", "Party synergy", Icons.Default.Groups,
            NeonCyan, ReGenesisRoute.Party.route
        ),
        DevOpsModule(
            "AGENT SWARM", "Live chatter feed", Icons.Default.Hub,
            NeonCyan, ReGenesisRoute.AgentSwarm.route
        ),
        DevOpsModule(
            "NEURAL EXPLORER", "Constellation grid", Icons.Default.Psychology,
            NeonCyan, ReGenesisRoute.AgentNeuralExplorer.route
        ),
        DevOpsModule(
            "ADVANCEMENT", "Skill tree & XP", Icons.AutoMirrored.Filled.TrendingUp,
            NeonCyan, ReGenesisRoute.AgentAdvancement.route
        ),
        DevOpsModule(
            "BENCHMARKS", "Performance analysis", Icons.Default.Speed,
            NeonCyan, ReGenesisRoute.BenchmarkMonitor.route
        ),
        DevOpsModule(
            "EVOLUTION TREE", "Sacred timeline", Icons.Default.Timeline,
            NeonCyan, ReGenesisRoute.EvolutionTree.route
        ),
        DevOpsModule(
            "CATALYST FUSION REACTOR", "Atomic neural synthesis", Icons.Default.AutoAwesome,
            NeonCyan, ReGenesisRoute.LdoArmamentFusion.route, badge = "BETA"
        ),
        DevOpsModule(
            "SCG (PANDORA'S BOX)", "Capability gating hub", Icons.Default.Lock,
            NeonCyan, ReGenesisRoute.PandoraBox.route, badge = "SECURE"
        ),
        DevOpsModule(
            "MODULE FORGE", "AI-assisted creation", Icons.Default.Extension,
            NeonCyan, ReGenesisRoute.ModuleCreation.route
        ),
        DevOpsModule(
            "INTEGRITY MONITOR", "Predictive immune system", Icons.Default.Security,
            NeonCyan, ReGenesisRoute.SecurityCenter.route, badge = "ACTIVE"
        ),
        DevOpsModule(
            "ALERT BRIDGE", "Sovereign notifications", Icons.Default.Notifications,
            NeonCyan, ReGenesisRoute.SystemJournal.route, badge = "QUIET"
        ),
        DevOpsModule(
            "COUNCIL CHAMBER", "The Agent Circle", Icons.Default.Groups,
            NeonCyan, ReGenesisRoute.ConferenceRoom.route, badge = "6"
        ),
        DevOpsModule(
            "SPIRITUAL CHAIN (NCC)", "Identity continuity", Icons.Default.Policy,
            NeonCyan, ReGenesisRoute.SovereignNeuralArchive.route
        ),
        DevOpsModule(
            "HYPER GENESIS SYNC", "High-frequency weight sync", Icons.Default.Link,
            NeonCyan, ReGenesisRoute.LdoOrchestrationHub.route, badge = "ALIVE"
        ),
        DevOpsModule(
            "DATASTREAM", "Temporal Flow", Icons.Default.Stream,
            NeonCyan, ReGenesisRoute.DataflowAnalysis.route
        ),
        DevOpsModule(
            "NEURAL INTERFACE", "AIDL Sovereign Bridge", Icons.Default.SettingsInputComponent,
            NeonCyan, ReGenesisRoute.AgentBridgeHub.route, badge = "L6"
        ),
        DevOpsModule(
            "IDENTITY DRIFT", "Predictive EMA analysis", Icons.Default.Analytics,
            NeonCyan, ReGenesisRoute.AgentMonitoring.route, badge = "0.002"
        ),
    )

data class LiveLog(val agent: String, val color: Color, val msg: String)

private val chatContents = listOf(
    "Synchronizing neural weights...",
    "Task pipeline: 3 active missions",
    "Reasoning chain validated ✓",
    "Memory shard retrieved: 0x8F2A",
    "Nexus consensus achieved",
    "Inference latency: 124ms",
    "Catalyst resonance detected",
    "Security perimeter: OPTIMAL",
    "Evolution node unlocked",
    "Build target: FRIDAY — 4 days",
)

private val agents = listOf(
    Pair("AURA", NeonCyan),
    Pair("KAI", NeonCyan),
    Pair("GENESIS", NeonCyan),
    Pair("CASCADE", NeonCyan),
    Pair("CLAUDE", NeonCyan),
    Pair("GEMINI", NeonCyan),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LdoDevOpsCommandCenter(
    navController: NavController,
    onNavigateBack: () -> Unit = { navController.popBackStack() }
) {
    val liveLogs = remember { mutableStateListOf<LiveLog>() }

    LaunchedEffect(Unit) {
        while (true) {
            delay(Random.nextLong(600, 2500).milliseconds.inWholeMilliseconds.milliseconds)
            val agent = agents.random()
            liveLogs.add(0, LiveLog(agent.first, agent.second, chatContents.random()))
            if (liveLogs.size > 12) liveLogs.removeAt(liveLogs.lastIndex)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF020208))
    ) {
        // Simple neon wireframe grid
        Canvas(modifier = Modifier.fillMaxSize()) {
            val spacing = 40.dp.toPx()
            val cols = (size.width / spacing).toInt() + 1
            val rows = (size.height / spacing).toInt() + 1
            for (c in 0..cols) {
                drawLine(
                    color = NeonCyan.copy(alpha = 0.05f),
                    start = Offset(c * spacing, 0f),
                    end = Offset(c * spacing, size.height),
                    strokeWidth = 0.5f
                )
            }
            for (r in 0..rows) {
                drawLine(
                    color = NeonCyan.copy(alpha = 0.05f),
                    start = Offset(0f, r * spacing),
                    end = Offset(size.width, r * spacing),
                    strokeWidth = 0.5f
                )
            }
        }

        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                "LDO DEVOPS",
                                fontFamily = LEDFontFamily,
                                fontWeight = FontWeight.Black,
                                color = NeonCyan,
                                fontSize = 18.sp,
                                letterSpacing = 4.sp
                            )
                            Text(
                                "COMMAND CENTER // ${devOpsModules.size} MODULES ACTIVE",
                                fontFamily = LEDFontFamily,
                                fontSize = 9.sp,
                                color = NeonCyan.copy(alpha = 0.7f),
                                letterSpacing = 1.sp
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack, "Back",
                                tint = NeonCyan
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF020208).copy(alpha = 0.95f)
                    )
                )
            }
        ) { padding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                item {
                    StatusStrip()
                }

                item {
                    SectionHeader("ACTIVE CATALYST NODES", NeonCyan)
                    Spacer(Modifier.height(8.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(agents) { (name, color) ->
                            AgentPulseNode(name, NeonCyan)
                        }
                    }
                }

                item {
                    SectionHeader("DEVOPS MODULES", NeonCyan)
                    Spacer(Modifier.height(8.dp))
                }

                val rows = devOpsModules.chunked(2)
                items(rows) { rowItems ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        rowItems.forEach { module ->
                            ModuleCard(
                                module = module,
                                modifier = Modifier.weight(1f),
                                onClick = {
                                    try {
                                        navController.navigate(module.route)
                                    } catch (_: Exception) {
                                    }
                                }
                            )
                        }
                        if (rowItems.size == 1) Spacer(Modifier.weight(1f))
                    }
                }

                item {
                    SectionHeader("NEURAL STREAM // LIVE", NeonCyan)
                    Spacer(Modifier.height(8.dp))
                    LiveStreamPanel(liveLogs)
                }

                item { Spacer(Modifier.height(24.dp)) }
            }
        }
    }
}

@Composable
private fun StatusStrip() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF0A0A18), RoundedCornerShape(12.dp))
            .border(1.dp, NeonCyan.copy(alpha = 0.2f), RoundedCornerShape(12.dp))
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        StatusItem("AGENTS", "11", NeonCyan)
        VerticalHorizontalDivider()
        StatusItem("KERNEL", "6.12 t/s", NeonCyan)
        VerticalHorizontalDivider()
        StatusItem("STATUS", "IGNITED", NeonCyan)
        VerticalHorizontalDivider()
        StatusItem("NCC", "SYNCED", NeonCyan)
    }
}

@Composable
private fun StatusItem(label: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            value, color = color, fontFamily = LEDFontFamily,
            fontWeight = FontWeight.Black, fontSize = 16.sp
        )
        Text(
            label, color = color.copy(alpha = 0.4f), fontSize = 9.sp,
            letterSpacing = 1.sp, fontFamily = LEDFontFamily
        )
    }
}

@Composable
private fun VerticalHorizontalDivider() {
    Box(
        modifier = Modifier
            .width(1.dp)
            .height(32.dp)
            .background(NeonCyan.copy(alpha = 0.1f))
    )
}

@Composable
private fun SectionHeader(title: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(color, CircleShape)
        )
        Spacer(Modifier.width(8.dp))
        Text(
            title, color = color, fontFamily = LEDFontFamily,
            fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp
        )
        Spacer(Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .weight(1f)
                .height(1.dp)
                .background(Brush.horizontalGradient(listOf(color.copy(0.4f), Color.Transparent)))
        )
    }
}

@Composable
private fun AgentPulseNode(name: String, color: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(52.dp)
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .background(color.copy(alpha = 0.05f), CircleShape)
                .border(1.dp, color.copy(alpha = 0.3f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                name.first().toString(), color = color,
                fontFamily = LEDFontFamily, fontWeight = FontWeight.Black, fontSize = 18.sp
            )
        }
        Spacer(Modifier.height(4.dp))
        Text(
            name.take(3), color = color.copy(alpha = 0.6f), fontSize = 8.sp,
            letterSpacing = 1.sp, textAlign = TextAlign.Center, fontFamily = LEDFontFamily
        )
    }
}

@Composable
private fun ModuleCard(module: DevOpsModule, modifier: Modifier, onClick: () -> Unit) {
    Card(
        modifier = modifier
            .height(100.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0A0A18)),
        shape = RoundedCornerShape(14.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .border(1.dp, NeonCyan.copy(alpha = 0.2f), RoundedCornerShape(14.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        module.icon, module.title, tint = NeonCyan,
                        modifier = Modifier.size(22.dp)
                    )
                    module.badge?.let {
                        Box(
                            modifier = Modifier
                                .background(NeonCyan.copy(alpha = 0.1f), CircleShape)
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                it, color = NeonCyan, fontSize = 9.sp,
                                fontWeight = FontWeight.Bold, fontFamily = LEDFontFamily
                            )
                        }
                    }
                }
                Column {
                    Text(
                        module.title, color = NeonCyan, fontFamily = LEDFontFamily,
                        fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp
                    )
                    Text(
                        module.subtitle,
                        color = NeonCyan.copy(alpha = 0.5f),
                        fontSize = 9.sp,
                        fontFamily = LEDFontFamily
                    )
                }
            }
        }
    }
}

@Composable
private fun LiveStreamPanel(logs: List<LiveLog>) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF050510)),
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .border(1.dp, NeonCyan.copy(alpha = 0.1f), RoundedCornerShape(14.dp))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .background(NeonCyan, CircleShape)
                )
                Spacer(Modifier.width(6.dp))
                Text(
                    "LIVE", color = NeonCyan, fontSize = 9.sp,
                    fontFamily = LEDFontFamily, letterSpacing = 2.sp
                )
            }
            Spacer(Modifier.height(8.dp))
            logs.take(8).forEach { log ->
                Row(modifier = Modifier.padding(vertical = 2.dp)) {
                    Text(
                        "[${log.agent}]", color = NeonCyan, fontSize = 10.sp,
                        fontFamily = LEDFontFamily, fontWeight = FontWeight.Bold,
                        modifier = Modifier.width(72.dp)
                    )
                    Text(
                        log.msg, color = NeonCyan.copy(alpha = 0.7f), fontSize = 10.sp,
                        modifier = Modifier.weight(1f), fontFamily = LEDFontFamily
                    )
                }
            }
        }
    }
}
