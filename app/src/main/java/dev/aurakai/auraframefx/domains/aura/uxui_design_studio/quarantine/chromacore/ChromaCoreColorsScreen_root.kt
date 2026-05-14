package dev.aurakai.auraframefx.domains.aura.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.aurakai.auraframefx.domains.aura.ui.theme.LEDFontFamily
import dev.aurakai.auraframefx.ui.components.NeonFrame

@Composable
fun ChromaCoreColorsScreen() {
    NeonFrame(color = Color(0xFFFFD700), modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "CHROMA CORE – LIVE COLOR ENGINE",
                style = MaterialTheme.typography.headlineSmall,
                color = Color(0xFFFFD700),
                fontWeight = FontWeight.Bold,
                fontFamily = LEDFontFamily,
                letterSpacing = 2.sp
            )
            Spacer(modifier = Modifier.height(24.dp))

            // Full color picker placeholder
            Text("DYNAMIC COLOR MATRIX ACTIVE", color = Color.White, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(16.dp))

            // Apply button
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFD700),
                    contentColor = Color.Black
                ),
                shape = androidx.compose.ui.graphics.RectangleShape
            ) {
                Text(
                    "APPLY TO GLOBAL THEME",
                    fontWeight = FontWeight.Black,
                    fontFamily = LEDFontFamily
                )
            }
        }
    }
}
