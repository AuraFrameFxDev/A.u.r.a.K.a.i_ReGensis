package dev.aurakai.auraframefx.ui.gates

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.aurakai.auraframefx.ui.components.hologram.AnimeHUDContainer
import dev.aurakai.auraframefx.ui.theme.LEDFontFamily

/**
 * 🎨 COLLAB CANVAS SCREEN
 * Where user and Aura design components together.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollabCanvasScreen(
    onNavigateBack: () -> Unit
) {
    val designs = remember {
        mutableStateListOf(
            CanvasProject("Neon Glass Buttons", "Drafting with Aura", 0.8f),
            CanvasProject("Holographic List", "Ready to Export", 1.0f),
            CanvasProject("Animated Tab Bar", "AI Refinement", 0.4f)
        )
    }

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFF0A0A12))) {
        AnimeHUDContainer(
            title = "COLLAB CANVAS",
            description = "AI REAL-TIME DESIGN COLLABORATION. CONNECTED TO AURA LAB.",
            glowColor = Color(0xFFB026FF)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "ACTIVE PROJECTS",
                        fontFamily = LEDFontFamily,
                        color = Color.White.copy(alpha = 0.6f),
                        fontSize = 12.sp
                    )
                    
                    Button(
                        onClick = { /* New Project */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB026FF))
                    ) {
                        Icon(Icons.Default.Add, null)
                        Text("NEW DESIGN")
                    }
                }
                
                Spacer(modifier = Modifier.height(20.dp))
                
                LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(designs) { design ->
                        DesignCanvasItem(design)
                    }
                }
            }
        }
    }
}

data class CanvasProject(val name: String, val status: String, val progress: Float)

@Composable
private fun DesignCanvasItem(project: CanvasProject) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.05f)),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFB026FF).copy(alpha = 0.2f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(project.name, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(project.status, color = Color.Gray, fontSize = 12.sp)
                }
                
                Row {
                    IconButton(onClick = { /* Export to Lab */ }) {
                        Icon(Icons.Default.CloudUpload, "Export to Lab", tint = Color.Cyan)
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            LinearProgressIndicator(
                progress = { project.progress },
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFFB026FF),
                trackColor = Color.White.copy(alpha = 0.1f)
            )
        }
    }
}
