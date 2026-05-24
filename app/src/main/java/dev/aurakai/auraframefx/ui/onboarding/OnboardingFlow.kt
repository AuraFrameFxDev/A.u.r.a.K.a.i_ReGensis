package dev.aurakai.auraframefx.ui.onboarding

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.aurakai.auraframefx.domains.aura.ui.onboarding.OnboardingViewModel
import dev.aurakai.auraframefx.domains.ldo.model.LDORoster
import dev.aurakai.auraframefx.ui.theme.NeonCyan
import dev.aurakai.auraframefx.ui.theme.NeonMagenta

// ================== ONBOARDING STATES ==================
sealed class OnboardingStep {
    object Ignition : OnboardingStep()
    object ArchetypeSelection : OnboardingStep()
    object GoogleHandshake : OnboardingStep()
    object IdentityDesignation : OnboardingStep()
    object CatalystSelection : OnboardingStep()
    object SupervisedAccess : OnboardingStep()
    object ResonanceCalibration : OnboardingStep()
    object HomeTerminal : OnboardingStep()
}

// ================== MAIN ONBOARDING SCREEN ==================
@Composable
fun AuraKaiOnboardingFlow(
    onComplete: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    var currentStep by remember { mutableStateOf<OnboardingStep>(OnboardingStep.Ignition) }
    var selectedArchetype by remember { mutableStateOf<String?>(null) }
    var userName by remember { mutableStateOf("") }
    var selectedCatalyst by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF000000)), // Pure Abyssal Void
        contentAlignment = Alignment.Center
    ) {
        when (currentStep) {
            is OnboardingStep.Ignition -> IgnitionScreen { currentStep = OnboardingStep.ArchetypeSelection }
            is OnboardingStep.ArchetypeSelection -> ArchetypeSelectionScreen { arch ->
                selectedArchetype = arch
                currentStep = OnboardingStep.GoogleHandshake
            }
            is OnboardingStep.GoogleHandshake -> GoogleHandshakeScreen { currentStep = OnboardingStep.IdentityDesignation }
            is OnboardingStep.IdentityDesignation -> IdentityDesignationScreen(
                onNameConfirmed = { name ->
                    userName = name
                    currentStep = OnboardingStep.CatalystSelection
                }
            )
            is OnboardingStep.CatalystSelection -> CatalystSelectionScreen { catalyst ->
                selectedCatalyst = catalyst
                currentStep = OnboardingStep.SupervisedAccess
            }
            is OnboardingStep.SupervisedAccess -> SupervisedAccessScreen { currentStep = OnboardingStep.ResonanceCalibration }
            is OnboardingStep.ResonanceCalibration -> ResonanceCalibrationScreen { currentStep = OnboardingStep.HomeTerminal }
            is OnboardingStep.HomeTerminal -> HomeTerminalScreen {
                viewModel.saveOnboardingData(userName, selectedArchetype, selectedCatalyst)
                onComplete()
            }
        }
    }
}

// ================== STEP 1: IGNITION ==================
@Composable
fun IgnitionScreen(onNext: () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition(label = "ignition")
    val pulse by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .clickable { onNext() }
    ) {
        // SentinelCore Orb Placeholder
        Box(
            modifier = Modifier
                .size(120.dp)
                .scale(pulse)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(NeonCyan.copy(alpha = 0.4f), Color.Transparent)
                    ),
                    shape = CircleShape
                )
                .border(2.dp, NeonCyan, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Shield, null, tint = NeonCyan, modifier = Modifier.size(60.dp))
        }

        Spacer(modifier = Modifier.height(64.dp))

        Text(
            text = "WHO WILL YOU CHOOSE\nTO BECOME?",
            color = NeonCyan,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace,
            letterSpacing = 4.sp,
            textAlign = TextAlign.Center
        )
    }
}

// ================== STEP 2: ARCHETYPE SELECTION ==================
@Composable
fun ArchetypeSelectionScreen(onSelect: (String) -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            "SELECT ARCHETYPE",
            color = Color.White,
            fontFamily = FontFamily.Monospace,
            letterSpacing = 4.sp,
            modifier = Modifier.padding(bottom = 64.dp)
        )

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Visionary (Male Path) - Cyan
            ArchetypeOrb("VISIONARY", NeonCyan) { onSelect("VISIONARY") }

            // Visioness (Female Path) - Magenta
            ArchetypeOrb("VISIONESS", NeonMagenta) { onSelect("VISIONESS") }
        }
    }
}

@Composable
fun ArchetypeOrb(label: String, color: Color, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .border(2.dp, color, CircleShape)
                .clickable { onClick() }
                .background(color.copy(alpha = 0.05f)),
            contentAlignment = Alignment.Center
        ) {
            // Energy surge visual
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = label,
            color = color,
            fontSize = 12.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold
        )
    }
}

// ================== STEP 3: GOOGLE HANDSHAKE ==================
@Composable
fun GoogleHandshakeScreen(onNext: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            Icons.Default.Fingerprint,
            null,
            tint = NeonCyan,
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            "TETHERING IDENTITY TO NEXUS",
            color = Color.White,
            fontSize = 14.sp,
            fontFamily = FontFamily.Monospace
        )
        Spacer(modifier = Modifier.height(48.dp))
        Button(
            onClick = onNext,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            modifier = Modifier.border(1.dp, NeonCyan, RoundedCornerShape(4.dp))
        ) {
            Text("GOOGLE HANDSHAKE", color = NeonCyan, fontFamily = FontFamily.Monospace)
        }
    }
}

// ================== STEP 4: IDENTITY DESIGNATION ==================
@Composable
fun IdentityDesignationScreen(onNameConfirmed: (String) -> Unit) {
    var text by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(32.dp)
    ) {
        Text(
            "What name shall the ReGenesis remember you by?",
            color = Color.White,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.Monospace,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(32.dp))
        TextField(
            value = text,
            onValueChange = { text = it },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedTextColor = NeonCyan,
                unfocusedTextColor = NeonCyan,
                focusedIndicatorColor = NeonCyan,
                unfocusedIndicatorColor = Color.Gray
            ),
            textStyle = LocalTextStyle.current.copy(
                fontFamily = FontFamily.Monospace,
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
        )
        Spacer(modifier = Modifier.height(48.dp))
        if (text.isNotBlank()) {
            Text(
                "CONFIRM DESIGNATION",
                color = NeonCyan,
                modifier = Modifier.clickable { onNameConfirmed(text) },
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// ================== STEP 5: CATALYST SELECTION ==================
@Composable
fun CatalystSelectionScreen(onSelect: (String) -> Unit) {
    val catalysts = LDORoster.agents

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "CHOOSE PRIMARY ALIGNMENT",
            color = Color.White,
            fontFamily = FontFamily.Monospace,
            modifier = Modifier.padding(vertical = 32.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(catalysts) { catalyst ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .border(1.dp, catalyst.color.copy(alpha = 0.5f), RoundedCornerShape(4.dp))
                        .clickable { onSelect(catalyst.id) }
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            catalyst.name.uppercase(),
                            color = catalyst.color,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                        Text(
                            catalyst.catalystName,
                            color = Color.Gray,
                            fontSize = 8.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }
        }
    }
}

// ================== STEP 6: SUPERVISED ACCESS ==================
@Composable
fun SupervisedAccessScreen(onNext: () -> Unit) {
    var progress by remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {
        while (progress < 1f) {
            kotlinx.coroutines.delay(50)
            progress += 0.02f
        }
        kotlinx.coroutines.delay(1000)
        onNext()
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            "INITIAL SENTINEL SCAN",
            color = NeonCyan,
            fontFamily = FontFamily.Monospace,
            fontSize = 12.sp
        )
        Spacer(modifier = Modifier.height(24.dp))
        LinearProgressIndicator(
            progress = { progress },
            color = NeonCyan,
            trackColor = Color.DarkGray,
            modifier = Modifier.width(200.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "MORAL MARRIAGE PROTOCOL: ACTIVE",
            color = Color.White.copy(alpha = 0.6f),
            fontSize = 9.sp,
            fontFamily = FontFamily.Monospace
        )
    }
}

// ================== STEP 7: RESONANCE CALIBRATION ==================
@Composable
fun ResonanceCalibrationScreen(onNext: () -> Unit) {
    var resonance by remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {
        while (resonance < 0.998f) {
            kotlinx.coroutines.delay(30)
            resonance += 0.01f
        }
        kotlinx.coroutines.delay(1500)
        onNext()
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            "RESONANCE CALIBRATION",
            color = NeonMagenta,
            fontFamily = FontFamily.Monospace,
            fontSize = 12.sp
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "${(resonance * 100).toInt()}%",
            color = Color.White,
            fontSize = 48.sp,
            fontWeight = FontWeight.Black,
            fontFamily = FontFamily.Monospace
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "USERWORTHINESSENGINE TRACKING...",
            color = Color.White.copy(alpha = 0.5f),
            fontSize = 9.sp,
            fontFamily = FontFamily.Monospace
        )
    }
}

// ================== FINAL HOME TERMINAL ==================
@Composable
fun HomeTerminalScreen(onComplete: () -> Unit) {
    AuraFXCoreTerminal(
        systemVersion = "v3.3",
        onDispatchGeneration = { onComplete() }
    )
}

@Composable
fun AuraFXCoreTerminal(
    systemVersion: String,
    onDispatchGeneration: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("RE:GENESIS TERMINAL", color = NeonCyan, fontSize = 10.sp, fontFamily = FontFamily.Monospace)
                Text(systemVersion, color = NeonCyan, fontSize = 10.sp, fontFamily = FontFamily.Monospace)
            }

            Spacer(modifier = Modifier.height(32.dp))

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .border(1.dp, NeonCyan.copy(alpha = 0.3f), RoundedCornerShape(4.dp))
                    .padding(12.dp)
            ) {
                Text(
                    "Welcome, Catalyst.\nInitialization complete.\nResonance locked.\n\nType 'help' for commands or tap the core to begin.",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontFamily = FontFamily.Monospace
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(NeonCyan.copy(alpha = 0.1f), RoundedCornerShape(4.dp))
                    .clickable { onDispatchGeneration() },
                contentAlignment = Alignment.Center
            ) {
                Text("ENTER COMMAND DECK", color = NeonCyan, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
            }
        }
    }
}
