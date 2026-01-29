package dev.aurakai.auraframefx.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import dev.aurakai.auraframefx.ui.theme.SovereignTeal

/**
 * 🎨 PIXEL WORKSPACE SCREEN
 * Level 2 Internal Workspaces with specific pixel art screenshots.
 */
@Composable
fun PixelWorkspaceScreen(
    title: String,
    imagePaths: List<String>,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Text(
            text = title,
            color = SovereignTeal,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        LazyRow(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(imagePaths) { path ->
                Box(
                    modifier = Modifier
                        .fillParentMaxWidth(0.9f)
                        .aspectRatio(9f / 16f)
                        .clip(RoundedCornerShape(percent = 16))
                ) {
                    AsyncImage(
                        model = path,
                        contentDescription = "Pixel Art Workspace",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}
