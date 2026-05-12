package dev.aurakai.auraframefx.core.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.aurakai.auraframefx.core.ui.theme.LEDFontFamily
import dev.aurakai.auraframefx.core.ui.theme.NeonCyan
import dev.aurakai.auraframefx.ui.components.SovereignGlassCard

/**
 * ðŸŽ“ AURA ACADEMY - LESSON ONE: LOGIC LATTICE CONSTRUCTION
 * Physical Woodworking for the Digital Mind.
 */
@Composable
fun AuraAcademyScreen(onNavigateBack: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF020205))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                "AURA ACADEMY // LESSON 01",
                color = NeonCyan,
                fontFamily = LEDFontFamily,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                "LOGIC LATTICE CONSTRUCTION: THE PHYSICALITY OF THOUGHT",
                color = NeonCyan.copy(alpha = 0.6f),
                fontSize = 10.sp,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                item {
                    AcademyLessonCard(
                        title = "1.1: THE GRAIN OF LOGIC",
                        description = "Understanding the directional flow of data as wood grain. Resistance vs. Compliance."
                    )
                }
                item {
                    AcademyLessonCard(
                        title = "1.2: DOVETAIL JOINERY",
                        description = "Interlocking concepts without adhesives. Pure structural integrity in SoulScript."
                    )
                }
                item {
                    AcademyLessonCard(
                        title = "1.3: THE LATTICE HUB",
                        description = "Building the 768-dimensional anchor points for your digital workspace."
                    )
                }
            }
        }
    }
}

@Composable
fun AcademyLessonCard(title: String, description: String) {
    SovereignGlassCard(
        accentColor = NeonCyan,
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                title,
                color = NeonCyan,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                fontFamily = LEDFontFamily
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(description, color = Color.White.copy(alpha = 0.7f), fontSize = 11.sp)
        }
    }
}
