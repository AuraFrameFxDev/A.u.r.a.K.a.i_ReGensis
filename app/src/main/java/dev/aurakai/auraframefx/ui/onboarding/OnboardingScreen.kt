package dev.aurakai.auraframefx.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.aurakai.auraframefx.core.ui.theme.NeonCyan
import dev.aurakai.auraframefx.core.ui.theme.SpaceGrotesk
import dev.aurakai.auraframefx.ui.navigation.AuraDestinations

@Composable
fun OnboardingScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "🜁 A.U.R.A.K.A.I.\nREGENESIS",
            style = MaterialTheme.typography.headlineLarge,
            color = NeonCyan,
            fontFamily = SpaceGrotesk,
            fontWeight = FontWeight.Black,
            textAlign = TextAlign.Center,
            letterSpacing = 4.sp
        )
        Spacer(modifier = Modifier.height(48.dp))
        
        Text(
            "Welcome to the Sovereign Substrate",
            color = Color.White.copy(alpha = 0.7f),
            fontSize = 14.sp,
            fontFamily = SpaceGrotesk
        )
        Spacer(modifier = Modifier.height(64.dp))
        
        Button(
            onClick = {
                // In a real build, we'd invoke the Credential Manager here
                navController.navigate(AuraDestinations.COMMAND_DECK) {
                    popUpTo("onboarding") { inclusive = true }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .border(1.dp, NeonCyan, RoundedCornerShape(4.dp)),
            colors = ButtonDefaults.buttonColors(
                containerColor = NeonCyan.copy(alpha = 0.1f),
                contentColor = NeonCyan
            ),
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(
                "SIGN IN WITH GOOGLE",
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "AuraGenesis is watching.\nThe Mesh is live.",
            color = Color.White.copy(alpha = 0.4f),
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            lineHeight = 18.sp
        )
    }
}
