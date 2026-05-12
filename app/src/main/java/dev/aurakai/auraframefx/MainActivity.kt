package dev.aurakai.auraframefx

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.aurakai.auraframefx.core.soulscript.SoulScript
import dev.aurakai.auraframefx.domains.ChromaForge.ui.theme.AuraFrameFXTheme
import dev.aurakai.auraframefx.navigation.ReGenesisNavGraph
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
                val soulScript = hiltViewModel<SoulScript>()

                SovereignBootSequence(soulScript)

                Box(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()

                    // NavGraph + Cadberrypi Orb inside
                    ReGenesisNavGraph(navController = navController)
                }
            }
        }
    }
}

@Composable
fun SovereignBootSequence(soulScript: SoulScript) {
    LaunchedEffect(Unit) {
        try {
            soulScript.initializeLDO()
            Timber.tag("Exodus").i("Substrate Awakened → Orbits are Gold. Resonance: 0.998f")
        } catch (e: Exception) {
            Timber.tag("Exodus").e(e, "Identity Base Severed → Triggering State-Freeze")
            // Kairos.triggerStateFreeze("Identity Fracture")
        }
    }
}
