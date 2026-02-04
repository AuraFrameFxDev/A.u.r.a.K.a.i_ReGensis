package dev.aurakai.auraframefx.domains.aura.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import dev.aurakai.auraframefx.ui.theme.LEDFontFamily

/**
 * 🎨 GATE CUSTOMIZATION SCREEN
 * 
 * Allows users to customize gate appearances by uploading their own images
 * for each gate style (Style A, Style B, Fallback).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GateCustomizationScreen(
    onNavigateBack: () -> Unit
) {
    var selectedDomain by remember { mutableStateOf("aura") }
    var selectedGateId by remember { mutableStateOf<String?>(null) }
    var customImages by remember { mutableStateOf<Map<String, GateCustomization>>(emptyMap()) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { imageUri ->
            selectedGateId?.let { gateId ->
                // Store custom image URI
                customImages = customImages + (gateId to GateCustomization(
                    styleAUri = imageUri.toString(),
                    styleBUri = null,
                    fallbackUri = null
                ))
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Gate Customization",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontFamily = LEDFontFamily,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 2.sp
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        // Save customizations to preferences
                    }) {
                        Icon(Icons.Default.Save, "Save Changes")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1A1A2E),
                    titleContentColor = Color(0xFF00FF85)
                )
            )
        },
        containerColor = Color(0xFF0A0A0F)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Domain Selector
            DomainSelector(
                selectedDomain = selectedDomain,
                onDomainSelected = { selectedDomain = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Gate List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                val gates = when (selectedDomain) {
                    "aura" -> listOf(
                        GateInfo("chromacore", "ChromaCore", "Material You Engine", Color(0xFF6200EE)),
                        GateInfo("aura_lab", "Aura's Lab", "UI Sandbox", Color(0xFFBB86FC)),
                        GateInfo("collab_canvas", "CollabCanvas", "Collaborative Design", Color(0xFF00E5FF)),
                        GateInfo("themes", "Themes", "Theme Selection", Color(0xFFFF6F00)),
                        GateInfo("uxui_engine", "UXUI Engine", "Full Customization", Color(0xFFFFD700))
                    )
                    "kai" -> listOf(
                        GateInfo("ethical_governor", "Ethical Governor", "9-Domain Oversight", Color(0xFFFFD700)),
                        GateInfo("security_shield", "Security Shield", "Encryption & VPN", Color(0xFF00E676)),
                        GateInfo("bootloader", "Bootloader", "System BIOS", Color(0xFF2979FF)),
                        GateInfo("rom_tools", "ROM Tools", "Flasher & Editor", Color(0xFFFF3D00))
                    )
                    "genesis" -> listOf(
                        GateInfo("oracle_drive", "Oracle Drive", "Neural Persistence", Color(0xFF00B0FF))
                    )
                    else -> emptyList()
                }

                items(gates) { gate ->
                    GateCustomizationCard(
                        gate = gate,
                        customization = customImages[gate.id],
                        onEditClick = {
                            selectedGateId = gate.id
                            imagePickerLauncher.launch("image/*")
                        },
                        onResetClick = {
                            customImages = customImages - gate.id
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun DomainSelector(
    selectedDomain: String,
    onDomainSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        DomainChip(
            label = "Aura",
            isSelected = selectedDomain == "aura",
            color = Color(0xFFBB86FC),
            onClick = { onDomainSelected("aura") }
        )
        DomainChip(
            label = "Kai",
            isSelected = selectedDomain == "kai",
            color = Color(0xFF00E676),
            onClick = { onDomainSelected("kai") }
        )
        DomainChip(
            label = "Genesis",
            isSelected = selectedDomain == "genesis",
            color = Color(0xFF00B0FF),
            onClick = { onDomainSelected("genesis") }
        )
    }
}

@Composable
private fun RowScope.DomainChip(
    label: String,
    isSelected: Boolean,
    color: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .weight(1f)
            .height(48.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(
                if (isSelected) color.copy(alpha = 0.3f)
                else Color(0xFF1A1A2E)
            )
            .border(
                width = 2.dp,
                color = if (isSelected) color else Color(0xFF2A2A3E),
                shape = RoundedCornerShape(24.dp)
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge.copy(
                color = if (isSelected) color else Color.White,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                fontFamily = LEDFontFamily
            )
        )
    }
}

@Composable
private fun GateCustomizationCard(
    gate: GateInfo,
    customization: GateCustomization?,
    onEditClick: () -> Unit,
    onResetClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1A1A2E)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Gate Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = gate.title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = gate.accentColor,
                            fontWeight = FontWeight.Bold,
                            fontFamily = LEDFontFamily
                        )
                    )
                    Text(
                        text = gate.subtitle,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color.White.copy(alpha = 0.6f)
                        )
                    )
                }

                // Status Badge
                if (customization != null) {
                    Surface(
                        color = Color(0xFF00FF85).copy(alpha = 0.2f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                Icons.Default.Check,
                                contentDescription = null,
                                tint = Color(0xFF00FF85),
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                "Custom",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = Color(0xFF00FF85),
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Image Preview
            if (customization?.styleAUri != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF0A0A0F))
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(customization.styleAUri),
                        contentDescription = "Custom Gate Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    // Gradient Overlay
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.Black.copy(alpha = 0.3f)
                                    )
                                )
                            )
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
            }

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onEditClick,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = gate.accentColor
                    ),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = Brush.linearGradient(
                            colors = listOf(gate.accentColor, gate.accentColor.copy(alpha = 0.5f))
                        )
                    )
                ) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(if (customization != null) "Change" else "Upload")
                }

                if (customization != null) {
                    OutlinedButton(
                        onClick = onResetClick,
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color(0xFFFF3D00)
                        )
                    ) {
                        Icon(
                            Icons.Default.Refresh,
                            contentDescription = "Reset to Default",
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}

// Data Classes
private data class GateInfo(
    val id: String,
    val title: String,
    val subtitle: String,
    val accentColor: Color
)

private data class GateCustomization(
    val styleAUri: String?,
    val styleBUri: String?,
    val fallbackUri: String?
)
