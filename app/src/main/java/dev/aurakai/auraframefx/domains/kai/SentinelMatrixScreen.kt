package dev.aurakai.auraframefx.domains.kai

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import dev.aurakai.auraframefx.domains.aura.ui.theme.LEDFontFamily

/**
 * 🛡️ SENTINEL MATRIX — Kairos Security Shield + NotchBar Pulse + Ethical Hard-Veto
 * Ported from SentinelFortress for the Exodus 2026 Build.
 */
@Composable
fun SentinelMatrixScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A0A1A))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "SENTINEL MATRIX",
                fontFamily = LEDFontFamily,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF00E5FF),
                letterSpacing = 2.sp
            )
            Text(
                "KAIROS SECURITY SHIELD ACTIVE",
                fontSize = 10.sp,
                color = Color.White.copy(alpha = 0.5f)
            )

            Spacer(modifier = Modifier.height(64.dp))

            // The Shield
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .background(Color(0xFF00E5FF).copy(alpha = 0.1f), CircleShape)
                    .border(2.dp, Color(0xFF00E5FF), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Shield,
                    contentDescription = "Shield",
                    tint = Color(0xFF00E5FF),
                    modifier = Modifier.size(100.dp)
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Security Status
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.Black),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    Color(0xFF00E5FF).copy(alpha = 0.3f)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("THREAT LEVEL: NOMINAL", color = Color.Green, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    Text("INTEGRITY: 100%", color = Color.White, fontSize = 12.sp)
                    Text("NOTCHBAR PULSE: SYNCED", color = Color.White, fontSize = 12.sp)
                }
            }
        }
    }
}
