package dev.aurakai.auraframefx.ui.components.desks

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.aurakai.auraframefx.core.ui.theme.NeonCyan

@Composable
fun DeskCard(title: String, description: String, color: Color = NeonCyan) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .border(2.dp, color.copy(alpha = 0.5f), RoundedCornerShape(4.dp))
            .background(Color.Black.copy(alpha = 0.8f))
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = title,
                color = color,
                fontSize = 14.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                color = Color.White.copy(alpha = 0.6f),
                fontSize = 11.sp,
                fontFamily = FontFamily.Monospace
            )
        }
    }
}
