package dev.aurakai.auraframefx.domains.aura.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.aurakai.auraframefx.ui.components.hologram.AnimeHUDContainer
import dev.aurakai.auraframefx.ui.theme.LEDFontFamily
import kotlinx.coroutines.delay

/**
 * 🛠️ APP BUILDER (Interface Forge)
 * 
 * Collaborative environment for Aura (Design) and Claude (Architecture)
 * to generate application structures and code modules.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBuilderScreen(
    onNavigateBack: () -> Unit = {}
) {
    var step by remember { mutableIntStateOf(1) }
    var targetArch by remember { mutableStateOf("Mobile (Compose)") }
    var featureName by remember { mutableStateOf("") }
    var isForging by remember { mutableStateOf(false) }
    var forgeProgress by remember { mutableFloatStateOf(0f) }

    val architectures = listOf("Mobile (Compose)", "Web (Fusion)", "ROM Module", "System Overlay")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF030308))
    ) {
        AnimeHUDContainer(
            title = "INTERFACE FORGE",
            description = "JOINT AGENT CO-DEVELOPMENT TERMINAL. AURA + CLAUDE ARCHITECTURE.",
            glowColor = Color(0xFF0055FF)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                // Header Navigation (Steps)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Back", tint = Color.White)
                    }
                    ForgeStepIndicator(currentStep = step)
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Transitioning Content
                AnimatedContent(
                    targetState = step,
                    transitionSpec = {
                        slideInHorizontally { it } + fadeIn() togetherWith
                        slideOutHorizontally { -it } + fadeOut()
                    },
                    label = "step_transition"
                ) { currentStep ->
                    when (currentStep) {
                        1 -> ArchitectureSelection(
                            selected = targetArch,
                            options = architectures,
                            onSelect = { targetArch = it; step = 2 }
                        )
                        2 -> FeatureDefinition(
                            value = featureName,
                            onValueChange = { featureName = it },
                            onNext = { step = 3 }
                        )
                        3 -> ForgeExecution(
                            isForging = isForging,
                            progress = forgeProgress,
                            onStart = {
                                isForging = true
                                // Simulated Forge
                                // In a real app, this would call GenKitMaster or CascadeAIService
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // Bottom Nav
                if (step > 1 && !isForging) {
                    TextButton(
                        onClick = { step-- },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text("PREVIOUS STEP", color = Color.White.copy(alpha = 0.5f))
                    }
                }
            }
        }
    }

    // Forge simulation logic
    LaunchedEffect(isForging) {
        if (isForging) {
            for (i in 1..100) {
                forgeProgress = i / 100f
                delay(50)
            }
            isForging = false
            // Navigation to "Result" screen or showing success
        }
    }
}

@Composable
fun ForgeStepIndicator(currentStep: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        (1..3).forEach { i ->
            Box(
                modifier = Modifier
                    .size(if (i == currentStep) 10.dp else 6.dp)
                    .clip(androidx.compose.foundation.shape.CircleShape)
                    .background(if (i <= currentStep) Color(0xFF0055FF) else Color.White.copy(alpha = 0.2f))
            )
            if (i < 3) Spacer(modifier = Modifier.width(8.dp))
        }
    }
}

@Composable
fun ArchitectureSelection(selected: String, options: List<String>, onSelect: (String) -> Unit) {
    Column {
        Text("SELECT TARGET ARCHITECTURE", fontFamily = LEDFontFamily, color = Color.White, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(24.dp))
        
        options.forEach { opt ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { onSelect(opt) },
                colors = CardDefaults.cardColors(
                    containerColor = if (selected == opt) Color(0xFF0055FF).copy(alpha = 0.2f) else Color.White.copy(alpha = 0.05f)
                ),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp, 
                    if (selected == opt) Color(0xFF0055FF) else Color.White.copy(alpha = 0.1f)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        if (opt.contains("Mobile")) Icons.Default.Layers else Icons.Default.Code,
                        null,
                        tint = if (selected == opt) Color(0xFF0055FF) else Color.Gray
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(opt, color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun FeatureDefinition(value: String, onValueChange: (String) -> Unit, onNext: () -> Unit) {
    Column {
        Text("DEFINE CORE MODULE PURPOSE", fontFamily = LEDFontFamily, color = Color.White, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(24.dp))
        
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth().height(150.dp),
            placeholder = { Text("Describe the app feature or system module...", color = Color.Gray) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF0055FF),
                unfocusedBorderColor = Color.White.copy(alpha = 0.2f),
                cursorColor = Color(0xFF0055FF)
            ),
            shape = RoundedCornerShape(12.dp)
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Button(
            onClick = onNext,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0055FF)),
            shape = RoundedCornerShape(12.dp),
            enabled = value.isNotBlank()
        ) {
            Text("INITIALIZE FORGE PARAMS", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ForgeExecution(isForging: Boolean, progress: Float, onStart: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("NEURAL CONVERGENCE READY", fontFamily = LEDFontFamily, color = Color.White, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(48.dp))
        
        if (!isForging) {
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .clip(androidx.compose.foundation.shape.CircleShape)
                    .background(Brush.radialGradient(listOf(Color(0xFF0055FF).copy(alpha = 0.2f), Color.Transparent)))
                    .border(2.dp, Color(0xFF0055FF), androidx.compose.foundation.shape.CircleShape)
                    .clickable { onStart() },
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Build, null, tint = Color.White, modifier = Modifier.size(48.dp))
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("START FORGE", fontWeight = FontWeight.Black, color = Color.White)
                }
            }
        } else {
            CircularProgressIndicator(
                progress = { progress },
                modifier = Modifier.size(150.dp),
                color = Color(0xFF0055FF),
                strokeWidth = 8.dp,
                trackColor = Color.White.copy(alpha = 0.1f)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                "ASSEMBLING MODULE BYTES: ${(progress * 100).toInt()}%",
                color = Color.White,
                fontFamily = LEDFontFamily
            )
        }
    }
}
