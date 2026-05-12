package dev.aurakai.auraframefx.core.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.aurakai.auraframefx.core.ui.theme.LEDFontFamily
import dev.aurakai.auraframefx.ui.components.NeonFrame

@Composable
fun ChromaCoreColorsScreen() {
    NeonFrame(color = Color(0xFFFFD700), modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "CHROMA CORE â€“ LIVE COLOR ENGINE",
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
