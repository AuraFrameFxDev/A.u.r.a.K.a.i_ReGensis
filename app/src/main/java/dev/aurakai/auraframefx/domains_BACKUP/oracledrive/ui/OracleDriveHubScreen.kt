package dev.aurakai.auraframefx.domains.oracledrive.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import dev.aurakai.auraframefx.domains.oracledrive.core.OracleDriveManager
import kotlinx.coroutines.launch

@Composable
fun OracleDriveHubScreen(navController: NavHostController) {
    val coroutineScope = rememberCoroutineScope()
    var rootStatus by remember { mutableStateOf("Checking Kernel APatch Status...") }
    var lsposedStatus by remember { mutableStateOf("Checking LSPosed Hook Status...") }

    val activeSpells = remember { mutableStateListOf<String>() }

    LaunchedEffect(Unit) {
        rootStatus = if (OracleDriveManager.isAPatchActive()) {
            "APatch Kernel Foundation: ACTIVE"
        } else {
            "APatch Kernel Foundation: INACTIVE"
        }

        lsposedStatus = if (OracleDriveManager.isLSPosedActive()) {
            "LSPosed Runtime Hooks: SECURE"
        } else {
            "LSPosed Runtime Hooks: UNVERIFIED"
        }

        activeSpells.addAll(OracleDriveManager.getActiveSpells())
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F0F1A))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "ORACLEDRIVE // SYSTEM GOVERNOR",
                color = Color.Cyan,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Status Card
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.Black),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Build, contentDescription = null, tint = Color.Green)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = rootStatus, color = Color.Green)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Build, contentDescription = null, tint = Color.Cyan)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = lsposedStatus, color = Color.Cyan)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                "ACTIVE SPELLHOOKS (System Mods)",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(activeSpells) { spell ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.DarkGray),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Text(
                            text = spell,
                            color = Color.White,
                            modifier = Modifier.padding(16.dp),
                            fontSize = 14.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    coroutineScope.launch {
                        val success = OracleDriveManager.invokeSpellhook("Aura_UI_Resonance_Mod")
                        if (success) {
                            if (!activeSpells.contains("Aura_UI_Resonance_Mod")) {
                                activeSpells.add("Aura_UI_Resonance_Mod")
                            }
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Magenta),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "INVOKE SPELLHOOK: Aura UI Resonance Mod",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
