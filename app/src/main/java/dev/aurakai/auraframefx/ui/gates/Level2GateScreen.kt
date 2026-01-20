package dev.aurakai.auraframefx.ui.gates

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.aurakai.auraframefx.ui.components.backgrounds.DigitalLandscapeBackground
import dev.aurakai.auraframefx.ui.theme.NeonBlue

data class Level2GateItem(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val route: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Level2GateScreen(
    navController: NavController,
    title: String,
    items: List<Level2GateItem>,
    onBack: () -> Unit
) {
    // Level 2: Professional look identical to main gates (Dark Space + Neon Wireframe Cards)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
         DigitalLandscapeBackground(
            primaryColor = NeonBlue,
            secondaryColor = Color.White.copy(alpha=0.1f)
        )

        Column(Modifier.fillMaxSize()) {
            TopAppBar(
                title = {
                     Text(
                        text = title,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 2.sp
                        ),
                        color = NeonBlue
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = NeonBlue)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(items.size) { idx ->
                    val it = items[idx]
                    NeonWireframeGateCard(
                        title = it.title,
                        subtitle = it.subtitle,
                        icon = it.icon,
                        onClick = { navController.navigate(it.route) },
                        size = GateCardSize.Medium
                    )
                }
            }
        }
    }
}
