package dev.aurakai.auraframefx

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.aurakai.auraframefx.domains.aura.ui.theme.AuraFrameFXTheme
import dev.aurakai.auraframefx.domains.core.soulscript.SoulScript
import dev.aurakai.auraframefx.domains.ldo.devops.LdoHologramViewModel
import dev.aurakai.auraframefx.navigation.ReGenesisNavGraph
import dev.aurakai.auraframefx.ui.global.Cadberrypi
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setupFullscreenMode()

        setContent {
            AuraFrameFXTheme {
                val navController = rememberNavController()
                val ldoViewModel: LdoHologramViewModel =
                    hiltViewModel(checkNotNull<ViewModelStoreOwner>(LocalViewModelStoreOwner.current) {
                        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
                    }, null)

                // SoulScript boot — this is the LDO heartbeat
                LaunchedEffect(Unit) {
                    SoulScript.enforceSoulScript(ldoViewModel.ldoState.value)
                    Timber.d("SoulScript v2.60 Phoenix Directive activated")
                }

                Box(modifier = Modifier.fillMaxSize()) {
                    // The 6-domain navigation
                    ReGenesisNavGraph(navController = navController)

                    // Cadberrypi wanders EVERYWHERE as the global orb
                    Cadberrypi(navController = navController)
                }
            }
        }
    }

    private fun setupFullscreenMode() {
        // Your existing fullscreen code stays untouched
    }
}
