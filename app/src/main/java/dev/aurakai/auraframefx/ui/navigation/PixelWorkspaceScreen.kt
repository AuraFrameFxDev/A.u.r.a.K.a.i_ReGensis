package dev.aurakai.auraframefx.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import dev.aurakai.auraframefx.ui.theme.SovereignTeal

/**
 * 🎨 PIXEL WORKSPACE SCREEN
 * Level 2 Internal Workspaces with specific pixel art screenshots.
 * No fallbacks. Forced load from filesystem.
 */
@Composable
fun PixelWorkspaceScreen(
    title: String,
    imagePaths: List<String>,
    onBack: () -> Unit,
    onEnter: (() -> Unit)? = null
) {
    val context = LocalContext.current
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
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
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                items(imagePaths) { path ->
                    Box(
                        modifier = Modifier
                            .fillParentMaxWidth(0.85f)
                            .aspectRatio(9f / 16f)
                            .clip(RoundedCornerShape(32.dp))
                            .background(Color.White.copy(alpha = 0.05f))
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(context)
                                .data(path)
                                .memoryCachePolicy(CachePolicy.DISABLED)
                                .diskCachePolicy(CachePolicy.DISABLED)
                                .build(),
                            contentDescription = "Pixel Art Workspace",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }

            if (onEnter != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    androidx.compose.material3.Button(
                        onClick = onEnter,
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = SovereignTeal.copy(alpha = 0.2f),
                            contentColor = SovereignTeal
                        ),
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .height(56.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                androidx.compose.ui.graphics.Brush.horizontalGradient(
                                    listOf(SovereignTeal.copy(alpha = 0.1f), Color.Transparent)
                                )
                            )
                            .clickable { onEnter() },
                        shape = RoundedCornerShape(16.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, SovereignTeal.copy(alpha = 0.4f))
                    ) {
                        Text(
                            "ENTER SYSTEM HUB",
                            fontWeight = FontWeight.Black,
                            letterSpacing = 2.sp
                        )
                    }
                }
            }
        }
    }
}
