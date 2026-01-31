package dev.aurakai.auraframefx.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.aurakai.auraframefx.ui.theme.LEDFontFamily

/**
 * 🎛️ LVL 3: GRID MENU SYSTEM
 *
 * Full functional screen with GRID layout (not carousel!)
 * Each domain's sub-gate opens one of these menus.
 *
 * Features:
 * - Themed background design (colorful for ChromaCore, shield for Bootloader, etc.)
 * - Grid of clickable menu items
 * - Each item has icon + title + subtitle
 * - Domain-specific accent colors
 */

data class GridMenuItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val route: String,
    val isImplemented: Boolean = true,
    val accentColor: Color = Color.Cyan
)

/**
 * Full-screen grid menu for LVL 3 navigation
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Level3GridMenu(
    title: String,
    subtitle: String,
    menuItems: List<GridMenuItem>,
    onItemClick: (GridMenuItem) -> Unit,
    onBackClick: () -> Unit,
) {
    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = title,
                            fontFamily = LEDFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            letterSpacing = 2.sp
                        )
                        Text(
                            text = subtitle,
                            style = MaterialTheme.typography.labelSmall,
                            color = accentColor
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                )
            )
        }
    ) { paddingValues ->
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
            ) {
                items(menuItems) { item ->
                }
            }
        }
    }
}

@Composable
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
    ) {
            Icon(
                imageVector = item.icon,
                contentDescription = null,
            )
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
            )
            Text(
                style = MaterialTheme.typography.labelSmall,
            )
        }
    }
}
