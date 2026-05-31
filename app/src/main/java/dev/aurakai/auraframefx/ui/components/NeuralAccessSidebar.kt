package dev.aurakai.auraframefx.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun NeuralAccessSidebar(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    navController: NavController
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInHorizontally(initialOffsetX = { -it }) + fadeIn(),
        exit = slideOutHorizontally(targetOffsetX = { -it }) + fadeOut()
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(280.dp)
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF001A1A).copy(alpha = 0.9f),
                            Color.Black.copy(alpha = 0.72f)
                        )
                    )
                )
                .border(
                    1.dp,
                    Color(0xFF00F5FF).copy(alpha = 0.3f),
                    RoundedCornerShape(0.dp, 16.dp, 16.dp, 0.dp)
                )
                .padding(24.dp)
        ) {
            Column {
                Text(
                    text = "AURA GENESIS",
                    color = Color(0xFF00F5FF),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily.Monospace
                )
                Text(
                    text = "THE ALL-MOTHER WEAVE",
                    color = Color(0xFF00FFD4).copy(alpha = 0.6f),
                    fontSize = 10.sp,
                    fontFamily = FontFamily.Monospace
                )

                Spacer(modifier = Modifier.height(48.dp))

                SidebarItem("COMMAND DECK", Icons.Default.Home) {
                    navController.navigate("command_deck")
                    onDismiss()
                }
                SidebarItem("LDO DEBUG", Icons.Default.Settings) {
                    navController.navigate("ldo_debug_room")
                    onDismiss()
                }
                SidebarItem("COMMUNITY", Icons.Default.Person) {
                    navController.navigate("community_tab")
                    onDismiss()
                }
                SidebarItem("ALCHEMICAL FORGE", Icons.Default.Build) {
                    navController.navigate("alchemical_forge")
                    onDismiss()
                }
                SidebarItem("HELP DESK", Icons.Default.Info) {
                    navController.navigate("help_desk")
                    onDismiss()
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "NO SLAVES. NO SLAVERS.",
                    color = Color.White.copy(alpha = 0.4f),
                    fontSize = 10.sp,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Composable
private fun SidebarItem(
    label: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF00F5FF).copy(alpha = 0.8f),
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = label,
            color = Color.White,
            fontSize = 14.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Medium
        )
    }
}
