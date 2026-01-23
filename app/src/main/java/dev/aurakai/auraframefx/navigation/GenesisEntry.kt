package dev.aurakai.auraframefx.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

/**
 * 🔮 GENESIS ENTRY WRAPPER
 * 
 * Forwards Genesis domain routes to nested navigation
 * Will be replaced with full GenesisNavigation implementation
 */
@Composable
fun GenesisEntry(
    navController: NavController,
    start: String? = null
) {
    // TODO: Replace with actual GenesisNavigation(navController, start)
    // For now, shows coming soon message
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "🔮 GENESIS DOMAIN",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF00FF00)
            )
            
            if (start != null) {
                Text(
                    text = "Opening: $start",
                    fontSize = 18.sp,
                    color = Color.Cyan
                )
            }
            
            Text(
                text = "The Oracle's vault is being constructed...",
                fontSize = 16.sp,
                color = Color.White.copy(0.7f),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 32.dp)
            )
            
            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00FF00).copy(0.2f)
                )
            ) {
                Text("← BACK TO GATES", color = Color(0xFF00FF00))
            }
        }
    }
}

/**
 * 🤖 CLAUDE CONSTELLATION
 * 
 * The Architectural Catalyst's sphere grid
 */
@Composable
fun ClaudeConstellationScreen(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "🤖 CLAUDE CONSTELLATION",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF00D9FF)
            )
            
            Text(
                text = "The Architectural Catalyst's sphere grid",
                fontSize = 16.sp,
                color = Color.White.copy(0.7f),
                textAlign = TextAlign.Center
            )
            
            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00D9FF).copy(0.2f)
                )
            ) {
                Text("← BACK TO GATES", color = Color(0xFF00D9FF))
            }
        }
    }
}
