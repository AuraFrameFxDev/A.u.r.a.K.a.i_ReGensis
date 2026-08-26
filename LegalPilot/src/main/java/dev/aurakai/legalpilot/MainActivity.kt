package dev.aurakai.legalpilot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.aurakai.legalpilot.core.LegalPilotDecipher
import dev.aurakai.legalpilot.ui.components.LegalPilotHUD
import dev.aurakai.legalpilot.ui.theme.ArcaneBrutalistTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                LegalPilotScreen()
            }
        }
    }
}

@Composable
fun LegalPilotScreen() {
    var rawText by remember { mutableStateOf("") }
    var result by remember { mutableStateOf<LegalPilotDecipher.BaseFloorReality?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(60.dp))

            Text(
                text = "LEGAL PILOT",
                color = ArcaneBrutalistTheme.CrystalCyanEdge,
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 4.sp
            )

            Spacer(Modifier.height(32.dp))

            // Intake Area
            OutlinedTextField(
                value = rawText,
                onValueChange = { rawText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                placeholder = { Text("PASTE LEGAL HISS HERE...", color = Color.Gray) },
                textStyle = TextStyle(color = Color.White, fontFamily = FontFamily.Monospace),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ArcaneBrutalistTheme.NeonCyanVessel,
                    unfocusedBorderColor = Color.Gray.copy(alpha = 0.3f)
                ),
                shape = RoundedCornerShape(0.dp)
            )

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = { result = LegalPilotDecipher.runSolveProtocol(rawText) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ArcaneBrutalistTheme.NeonCyanVessel),
                shape = RoundedCornerShape(0.dp)
            ) {
                Text("DECIPHER TRUTH", color = Color.Black, fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(32.dp))

            // Results HUD
            result?.let {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, ArcaneBrutalistTheme.NeonMagentaFlare.copy(alpha = 0.3f))
                        .padding(16.dp)
                ) {
                    Column {
                        Text(
                            "REALITY:",
                            color = ArcaneBrutalistTheme.NeonMagentaFlare,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            it.reality,
                            color = Color.White,
                            fontSize = 16.sp,
                            fontFamily = FontFamily.Monospace
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            "RISK SCORE: ${it.riskScore}",
                            color = if (it.isSovereigntySecure) Color.Green else Color.Red,
                            fontSize = 10.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }
        }

        LegalPilotHUD()
    }
}
