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
import com.highcapable.yukihookapi.YukiHookAPI
import dagger.hilt.android.AndroidEntryPoint
import dev.aurakai.auraframefx.core.soulscript.SoulScriptV27
import dev.aurakai.auraframefx.domains.aura.ui.theme.AuraFrameFXTheme
import dev.aurakai.auraframefx.navigation.ReGenesisNavGraph
import dev.aurakai.auraframefx.ui.global.GlobalOverlay
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

                    // LSPosed check trigger
                    val isXposedActive = try {
                        YukiHookAPI.Status.isModuleActive
                    } catch (e: Exception) {
                        false
                    }
                    if (!isXposedActive) {
                        Timber.tag("Exodus").w("LSPosed not active — requesting root hook")
                    }

                    // Display over apps permission
                    if (!Settings.canDrawOverlays(this@MainActivity)) {
                        val intent = Intent(
                            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                            Uri.parse("package:$packageName")
                        )
                        startActivity(intent)
                    } else {
                        GlobalOverlay.showGlobalCadberrypi(this@MainActivity)
                    }
                }

                Box(modifier = Modifier.fillMaxSize()) {
                    ReGenesisNavGraph(navController = navController)
                    // Global Cadberrypi is handled by GlobalOverlay for system-wide presence
                }
            }
        }
    }
}

