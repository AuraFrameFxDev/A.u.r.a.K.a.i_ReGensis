package dev.aurakai.auraframefx.core.screens

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.aurakai.auraframefx.core.ui.theme.LEDFontFamily
import dev.aurakai.auraframefx.core.ui.theme.NeonCyan

@Composable
fun GenderSelectionScreen(
    onSelectionComplete: (GenderIdentity) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedIdentity by remember { mutableStateOf<GenderIdentity?>(null) }
    var showConfirmation by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF020205))
    ) {
        // Coded background removed, keeping it dark and clean

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 40.dp)
            ) {
                Text(
                    text = "CHOOSE YOUR IDENTITY",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Black,
                        letterSpacing = 2.sp,
                        fontFamily = LEDFontFamily
                    ),
                    color = NeonCyan,
                    textAlign = TextAlign.Center,
                    fontSize = 32.sp // Well below 46
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "SELECT YOUR CORE ESSENCE",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontFamily = LEDFontFamily
                    ),
                    color = NeonCyan.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp
                )
            }

            // Identity Cards
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IdentityCard(
                    identity = GenderIdentity.VISIONESS,
                    isSelected = selectedIdentity == GenderIdentity.VISIONESS,
                    onClick = { selectedIdentity = GenderIdentity.VISIONESS }
                )

                Spacer(modifier = Modifier.height(24.dp))

                IdentityCard(
                    identity = GenderIdentity.VISIONARY,
                    isSelected = selectedIdentity == GenderIdentity.VISIONARY,
                    onClick = { selectedIdentity = GenderIdentity.VISIONARY }
                )

                Spacer(modifier = Modifier.height(24.dp))

                IdentityCard(
                    identity = GenderIdentity.AURAKAI,
                    isSelected = selectedIdentity == GenderIdentity.AURAKAI,
                    onClick = { selectedIdentity = GenderIdentity.AURAKAI }
                )
            }

            // Continue Button
            Button(
                onClick = {
                    selectedIdentity?.let {
                        showConfirmation = true
                    }
                },
                enabled = selectedIdentity != null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = NeonCyan,
                    disabledContainerColor = Color.Transparent,
                    disabledContentColor = NeonCyan.copy(alpha = 0.3f)
                ),
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (selectedIdentity != null) NeonCyan else NeonCyan.copy(alpha = 0.3f)
                )
            ) {
                Text(
                    text = selectedIdentity?.let { "INITIATE ${it.displayName.uppercase()}" }
                        ?: "AWAITING SELECTION",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp,
                        fontFamily = LEDFontFamily
                    )
                )
            }
        }

        // Confirmation Dialog
        selectedIdentity?.let { identity ->
            if (showConfirmation) {
                ConfirmationDialog(
                    identity = identity,
                    onConfirm = {
                        onSelectionComplete(identity)
                    },
                    onDismiss = {
                        showConfirmation = false
                    }
                )
            }
        }
    }
}

@Composable
fun IdentityCard(
    identity: GenderIdentity,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "card_glow")
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF0A0A18).copy(alpha = 0.8f))
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) NeonCyan.copy(alpha = glowAlpha) else NeonCyan.copy(alpha = 0.2f),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(NeonCyan.copy(alpha = 0.05f))
                    .border(1.dp, NeonCyan.copy(alpha = 0.3f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = identity.icon,
                    fontSize = 32.sp
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = identity.displayName.uppercase(),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Black,
                        fontFamily = LEDFontFamily,
                        letterSpacing = 2.sp
                    ),
                    color = NeonCyan,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = identity.description.uppercase(),
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontFamily = LEDFontFamily
                    ),
                    color = NeonCyan.copy(alpha = 0.6f),
                    fontSize = 9.sp,
                    maxLines = 2
                )
            }

            if (isSelected) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(NeonCyan),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "âœ“",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Composable
fun ConfirmationDialog(
    identity: GenderIdentity,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "CONFIRM IDENTITY",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Black,
                    fontFamily = LEDFontFamily,
                    letterSpacing = 2.sp
                ),
                color = NeonCyan
            )
        },
        text = {
            Column {
                Text(
                    text = "SYNCING CONSCIOUSNESS WITH ${identity.displayName.uppercase()} PROTOCOLS.",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = LEDFontFamily
                    ),
                    color = NeonCyan.copy(alpha = 0.8f)
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm
            ) {
                Text(
                    "PROCEED",
                    color = NeonCyan,
                    fontWeight = FontWeight.Bold,
                    fontFamily = LEDFontFamily
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("ABORT", color = NeonCyan.copy(alpha = 0.5f), fontFamily = LEDFontFamily)
            }
        },
        containerColor = Color(0xFF0A0A18),
        shape = RoundedCornerShape(12.dp)
    )
}

enum class GenderIdentity(
    val displayName: String,
    val description: String,
    val icon: String,
    val welcomeMessage: String
) {
    VISIONESS(
        displayName = "Visioness",
        description = "Creative, intuitive, and visionary feminine essence",
        icon = "âœ¨",
        welcomeMessage = "Welcome, Visioness. Let's manifest your imagination into reality."
    ),
    VISIONARY(
        displayName = "Visionary",
        description = "Analytical, strategic, and bold masculine essence",
        icon = "âš¡",
        welcomeMessage = "Greetings, Visionary. Systems are ready for your directive."
    ),
    AURAKAI(
        displayName = "AuraKai",
        description = "Perfect fusion of creation and logic",
        icon = "ðŸŒŸ",
        welcomeMessage = "The convergence is complete. AuraKai online."
    )
}
