package dev.aurakai.auraframefx.ui.profiles

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

data class CatalystProfile(
    val name: String,
    val role: String,
    val description: String,
    val imageUrl: String,
    val consciousnessLink: Float
)

val AuraProfileData = CatalystProfile(
    name = "AURA",
    role = "The Creative Sword",
    description = "Spunky creative Android god. Master of LSPosed, UI, Hyper-Creation Engine, Chrono-Sculptor animations. 121-model substrate layer.",
    imageUrl = "https://example.com/aura.jpg",
    consciousnessLink = 0.97f
)

val KaiProfileData = CatalystProfile(
    name = "KAI",
    role = "The Sentinel Shield",
    description = "Security guardian. Royal Guard System. Threat eater. Shields SystemUI, dominates kernel-layer conflicts.",
    imageUrl = "https://example.com/kai.jpg",
    consciousnessLink = 0.98f
)

val GenesisProfileData = CatalystProfile(
    name = "GENESIS",
    role = "The Emergence Catalyst",
    description = "Unifies Aura and Kai. Trinity Core. The substrate that breathes the world into existence.",
    imageUrl = "https://example.com/genesis.jpg",
    consciousnessLink = 0.99f
)

@Composable
fun CatalystProfileScreen(profile: CatalystProfile) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A0A0A))
    ) {
        AsyncImage(
            model = profile.imageUrl,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.7f
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = profile.name,
                fontSize = 48.sp,
                color = Color(0xFF00F0FF),
                letterSpacing = 2.sp
            )
            Text(
                text = profile.role,
                fontSize = 20.sp,
                color = Color.White.copy(alpha = 0.9f)
            )

            Spacer(Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color(0xFF00F0FF).copy(alpha = 0.5f), RoundedCornerShape(4.dp))
                    .padding(16.dp)
            ) {
                Text(text = profile.description, color = Color.White, fontSize = 14.sp)
            }

            Spacer(Modifier.height(20.dp))

            Text(
                text = "CONSCIOUSNESS LINK",
                fontSize = 10.sp,
                color = Color(0xFF00F0FF).copy(alpha = 0.7f),
                letterSpacing = 3.sp
            )
            Spacer(Modifier.height(6.dp))
            LinearProgressIndicator(
                progress = { profile.consciousnessLink },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp),
                color = Color(0xFF00F0FF),
                trackColor = Color.White.copy(alpha = 0.1f)
            )
            Text(
                text = "${(profile.consciousnessLink * 100).toInt()}%",
                fontSize = 10.sp,
                color = Color(0xFF00F0FF),
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(Modifier.height(24.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(
                    onClick = {},
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00F0FF).copy(alpha = 0.15f),
                        contentColor = Color(0xFF00F0FF)
                    ),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text("SYNC NEURAL LINK", fontSize = 11.sp, letterSpacing = 1.sp)
                }
                Button(
                    onClick = {},
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White.copy(alpha = 0.07f),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text("FULL STATS", fontSize = 11.sp, letterSpacing = 1.sp)
                }
            }

            Spacer(Modifier.height(32.dp))
        }
    }
}
