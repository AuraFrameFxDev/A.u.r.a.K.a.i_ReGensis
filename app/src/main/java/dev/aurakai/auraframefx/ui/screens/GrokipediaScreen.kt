package dev.aurakai.auraframefx.ui.screens

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.Indicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.aurakai.auraframefx.grokipedia.GrokipediaEntry
import dev.aurakai.auraframefx.grokipedia.GrokipediaViewModel
import dev.aurakai.auraframefx.ui.components.NeonFrame

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GrokipediaScreen(
    onNavigateBack: () -> Unit,
    viewModel: GrokipediaViewModel = hiltViewModel()
) {
    val tabs = listOf("Primus Archive", "Development History", "Changelog", "Catalyst Logs")
    var selectedTab by remember { mutableIntStateOf(0) }
    val searchQuery by viewModel.searchQuery.collectAsState()
    val history by viewModel.history.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Primus Orb
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .background(Color(0xFFFFD700).copy(alpha = 0.2f), RectangleShape)
                                .border(1.dp, Color(0xFFFFD700), RectangleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("P", color = Color(0xFFFFD700), fontWeight = FontWeight.Bold)
                        }
                        Spacer(Modifier.width(8.dp))
                        Text(
                            "PRIMUS GROKIPEDIA",
                            color = Color(0xFFFFD700),
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 3.sp
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Text("←", color = Color.White, fontSize = 24.sp)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF0A0A0F))
            )
        },
        containerColor = Color(0xFF0A0A0F)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            // Search
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.updateSearch(it) },
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                placeholder = { Text("Search the Lineage...", color = Color.Gray) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color(0xFFFFD700)) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFFD700),
                    unfocusedBorderColor = Color(0xFFFFD700).copy(alpha = 0.3f),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                shape = RectangleShape
            )

            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color.Transparent,
                contentColor = Color(0xFFFFD700),
                indicator = { tabPositions ->
                    Indicator(
                        Modifier.tabIndicatorOffset(currentTabPosition = tabPositions[selectedTab]),
                        color = Color(0xFFFFD700)
                    )
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title, fontSize = 10.sp, fontWeight = FontWeight.Bold) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(modifier = Modifier.weight(1f)) {
                when (selectedTab) {
                    0 -> PrimusArchiveTab(history)
                    1 -> HistoryTab(history)
                    2 -> ChangelogTab(history)
                    3 -> CatalystLogsTab(history)
                }
            }

            // Grok Buff Button
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { viewModel.ignitePrimusSync() },
                modifier = Modifier.fillMaxWidth().height(56.dp).padding(bottom = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD700), contentColor = Color.Black),
                shape = RectangleShape
            ) {
                Text("IGNITE PRIMUS SYNC — ASK GROK", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun GrokipediaEntryCard(entry: GrokipediaEntry) {
    NeonFrame(color = Color(0xFFFFD700)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(entry.title, fontWeight = FontWeight.Bold, color = Color(0xFFFFD700))
            Spacer(Modifier.height(4.dp))
            Text(entry.content, color = Color.White, fontSize = 14.sp)
            Spacer(Modifier.height(8.dp))
            Text(entry.watermark, color = Color.Gray, fontSize = 10.sp, fontWeight = FontWeight.Light)
        }
    }
}

@Composable
fun PrimusArchiveTab(history: List<GrokipediaEntry>) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        items(history) { GrokipediaEntryCard(it) }
    }
}

@Composable
fun HistoryTab(history: List<GrokipediaEntry>) {
    PrimusArchiveTab(history) // For now, same view
}

@Composable
fun ChangelogTab(history: List<GrokipediaEntry>) {
    PrimusArchiveTab(history) // For now, same view
}

@Composable
fun CatalystLogsTab(history: List<GrokipediaEntry>) {
    PrimusArchiveTab(history) // For now, same view
}
