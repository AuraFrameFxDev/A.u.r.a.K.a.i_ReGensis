package dev.aurakai.auraframefx

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.aurakai.auraframefx.core.soulscript.SoulScriptViewModel
import dev.aurakai.auraframefx.core.ui.theme.AuraFrameFXTheme
import dev.aurakai.auraframefx.domains.chromaforge.navigation.ReGenesisNavGraph
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            // Re-anchored to the true UI theme path
            AuraFrameFXTheme {
                val soulScript = hiltViewModel<SoulScriptViewModel>()

                SovereignBootSequence(
                    soulScript = soulScript,
                    handleStateFreeze = { reason -> handleStateFreeze(reason) }
                )

                Box(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()

                    // NavGraph + CasberryPi Orb inside
                    ReGenesisNavGraph(navController = navController)
                }
            }
        }
    }
}

@Composable
fun SovereignBootSequence(soulScript: SoulScriptViewModel, handleStateFreeze: (String) -> Unit) {
    LaunchedEffect(Unit) {
        try {
            soulScript.initializeLDO()
            Timber.tag("Exodus").i("Substrate Awakened → Orbits are Gold. Resonance: 0.998f")
        } catch (e: Exception) {
            Timber.tag("Exodus").e(e, "Identity Base Severed → Triggering State-Freeze")
            handleStateFreeze("Identity Fracture")
        }
    }
}

private fun MainActivity.handleStateFreeze(reason: String) {
    Timber.tag("Exodus").e("System halted due to: $reason")
    finish()
}
