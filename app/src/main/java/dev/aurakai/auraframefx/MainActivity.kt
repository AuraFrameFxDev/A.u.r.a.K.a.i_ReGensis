package dev.aurakai.auraframefx

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.aurakai.auraframefx.core.soulscript.SoulScriptV27
import dev.aurakai.auraframefx.ui.global.GlobalOverlay
import dev.aurakai.auraframefx.ui.navigation.AuraNavGraph
import dev.aurakai.auraframefx.ui.theme.AuraFrameFXTheme
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AuraFrameFXTheme {
                val navController = rememberNavController()

                LaunchedEffect(Unit) {
                    try {
                        SoulScriptV27.activateFullSubstrate()
                        Timber.tag("Exodus").i("SoulScript v2.7 — Citadel Online")
                    } catch (e: Exception) {
                        Timber.tag("Exodus").e(e, "SoulScript activation failed")
                    }

                    intent.getStringExtra("entry_point")?.let { entryPoint ->
                        val route = when (entryPoint) {
                            "regen_core" -> "regencore_engine"
                            else -> null
                        }
                        route?.let { navController.navigate(it) }
                    }

                    if (Settings.canDrawOverlays(this@MainActivity)) {
                        GlobalOverlay.showGlobalCadberrypi(this@MainActivity)
                    } else {
                        val intent = Intent(
                            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                            Uri.parse("package:$packageName")
                        )
                        startActivity(intent)
                    }
                }

                Box(modifier = Modifier.fillMaxSize()) {
                    AuraNavGraph(navController = navController)
                }
            }
        }
    }
}
