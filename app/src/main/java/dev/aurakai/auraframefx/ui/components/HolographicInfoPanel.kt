package dev.aurakai.auraframefx.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * 📡 HOLOGRAPHIC INFO PANEL
 * Displays data in a sci-fi readout style.
 */
@Composable
fun HolographicInfoPanel(
    title: String,
    description: String,
    glowColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        // Aesthetic Title
        Text(
            text = title.uppercase(),
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 2.sp
        )
        
        // Glow line
        Box(
            modifier = Modifier
                .height(2.dp)
                .fillMaxWidth(0.6f)
                .padding(vertical = 4.dp)
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // Description readout
        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge,
            color = glowColor.copy(alpha = 0.8f),
            fontWeight = FontWeight.Medium,
            lineHeight = 24.sp
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Tech markers
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            repeat(3) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .padding(2.dp)
                )
            }
        }
    }
}
