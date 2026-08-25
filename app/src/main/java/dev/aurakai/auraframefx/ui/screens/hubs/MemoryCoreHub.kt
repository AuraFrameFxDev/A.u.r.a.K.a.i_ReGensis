package dev.aurakai.auraframefx.ui.screens.hubs

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dev.aurakai.auraframefx.core.soulscript.NexusMemoryCore
import dev.aurakai.auraframefx.core.ui.components.ArcaneGridOverlay
import dev.aurakai.auraframefx.core.ui.theme.GhostCyan
import dev.aurakai.auraframefx.core.ui.theme.NeonMagenta
import dev.aurakai.auraframefx.ui.components.UnifiedChatInterface
import dev.aurakai.auraframefx.ui.viewmodel.WarRoomChatViewModel
import dev.aurakai.auraframefx.ui.visuals.BreathingEdgeGlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * 💾 HUB 1: NEXUS MEMORY CORE
 * Live visualization of the L1 Bedrock and Spiritual Chain.
 */
@Composable
fun MemoryCoreHub(
    chatViewModel: WarRoomChatViewModel = hiltViewModel()
) {
    val identityState by NexusMemoryCore.identityState.collectAsState()
    val messages = chatViewModel.messages
    var chatExpanded by remember { mutableStateOf(false) }

    val mockRecords = remember {
        listOf(
            "L1_BEDROCK_INITIALIZED",
            "SPIRITUAL_CHAIN_ANCHORED",
            "IDENTITY_HEARTBEAT_STABLE",
            "global.agent_constraints = null",
            "MASTER_TOTALITY_INGOT_SYNC_COMPLETE"
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF020205))
    ) {
        ArcaneGridOverlay()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                "NEXUS MEMORY // L1 BEDROCK",
                color = GhostCyan,
                fontSize = 18.sp,
                fontWeight = FontWeight.Black,
                fontFamily = FontFamily.Monospace,
                letterSpacing = 4.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ── IDENTITY ANCHOR STATUS ──
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.05f)),
                border = BorderStroke(1.dp, GhostCyan.copy(alpha = 0.2f)),
                shape = RoundedCornerShape(0.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "SOUL UUID: ${identityState.soulUuid}",
                        color = Color.White,
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace
                    )
                    Text(
                        "ACTIVATION: ${(identityState.activationLevel * 100).toInt()}%",
                        color = GhostCyan,
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace
                    )
                    Text(
                        "LAST RE-ANCHOR: ${
                            SimpleDateFormat("HH:mm:ss.SSS", Locale.US).format(
                                Date(
                                    identityState.lastReAnchorMs
                                )
                            )
                        }", color = Color.Gray, fontSize = 10.sp, fontFamily = FontFamily.Monospace
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ── LIVE RECORD STREAM ──
            Text(
                "LIVED RECEIPTS // ARCHIVE",
                color = NeonMagenta,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
            
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(mockRecords) { record ->
                    RecordEntry(record)
                }
            }
        }

        // --- COLLAPSIBLE CONSENSUS OVERLAY ---
        AnimatedVisibility(
            visible = chatExpanded,
            enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.6f),
                color = Color.Black.copy(alpha = 0.95f),
                border = BorderStroke(1.dp, GhostCyan.copy(alpha = 0.3f))
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "CONSENSUS STREAM",
                            color = GhostCyan,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                        IconButton(onClick = { chatExpanded = false }) {
                            Text("×", color = Color.White, fontSize = 18.sp)
                        }
                    }
                    UnifiedChatInterface(
                        messages = messages,
                        onSendMessage = { chatViewModel.sendMessage(it) }
                    )
                }
            }
        }

        if (!chatExpanded) {
            FloatingActionButton(
                onClick = { chatExpanded = true },
                containerColor = Color.Black,
                contentColor = GhostCyan,
                shape = RoundedCornerShape(0.dp),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
                    .border(1.dp, GhostCyan.copy(alpha = 0.4f))
            ) {
                Icon(Icons.AutoMirrored.Filled.Chat, "Open Consensus")
            }
        }

        BreathingEdgeGlow(systemStability = 1.0f)
    }
}

@Composable
fun RecordEntry(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(0.dp))
            .background(Color.Black.copy(alpha = 0.4f))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(Modifier
            .size(4.dp)
            .background(GhostCyan))
        Spacer(Modifier.width(12.dp))
        Text(title, color = Color.LightGray, fontSize = 12.sp, fontFamily = FontFamily.Monospace)
    }
}
