package dev.aurakai.auraframefx.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
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
/**
 * Renders a pixel workspace gallery screen with a back control and a horizontally scrollable list of images.
 *
 * @param title The header title displayed next to the back control.
 * @param imagePaths A list of image URIs or file paths to display in the gallery.
 * @param onBack Callback invoked when the back control is pressed.
 */
/**
 * Displays a pixel workspace gallery with a back control and a horizontally scrollable list of images.
 *
 * The composable renders a header containing a back icon and the provided title, and a LazyRow that shows each
 * image from `imagePaths` inside a rounded box that fills most of the available width and maintains a 9:16 aspect ratio.
 *
 * @param title The text shown next to the back control.
 * @param imagePaths List of image URIs or file paths to display in the gallery.
 * @param onBack Callback invoked when the back control is pressed.
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
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = SovereignTeal,
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { onBack() }
                    .padding(4.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = title,
                color = SovereignTeal,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

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