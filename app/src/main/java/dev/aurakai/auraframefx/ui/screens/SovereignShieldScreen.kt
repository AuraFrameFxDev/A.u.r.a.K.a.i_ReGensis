package dev.aurakai.auraframefx.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.* // Import all from layout for convenience
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.LaunchedEffect
import android.content.pm.PackageManager
import dev.aurakai.auraframefx.system.ShizukuManager
import rikka.shizuku.Shizuku

private const val TAG = "SovereignShieldScreen"

@Composable
fun SovereignShieldScreen() {
    val context = LocalContext.current
    var shizukuStatus by remember { mutableStateOf("Checking...") }
    var permissionGranted by remember { mutableStateOf(false) }

    // Initial check and setup
    LaunchedEffect(Unit) {
        updateShizukuStatus(context) { status, granted ->
            shizukuStatus = status
            permissionGranted = granted
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Sovereign Shield Status",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Shizuku Status: $shizukuStatus",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if (!permissionGranted && ShizukuManager.isShizukuAvailable()) {
            Button(onClick = { requestShizukuPermission(context) }) {
                Text("Request Shizuku Permission")
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        Button(onClick = { initializeShizuku(context) }) {
            Text("Initialize/Reconnect Shizuku")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (!ShizukuManager.isShizukuAvailable() && shizukuStatus == "Disconnected") {
            Text(
                text = "Shizuku is not installed or not running. Please install/start Shizuku from its app.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Button(onClick = { openShizukuApp(context) }) {
                Text("Open Shizuku App")
            }
        }
    }
}

private fun initializeShizuku(
    context: Context,
    onStatusUpdate: ((String, Boolean) -> Unit)? = null
) {
    ShizukuManager.initializeShizukuIntegration(context)
    onStatusUpdate?.let {
        updateShizukuStatus(context, it)
    }
}

private fun requestShizukuPermission(
    context: Context,
    onStatusUpdate: ((String, Boolean) -> Unit)? = null
) {
    ShizukuManager.requestShizukuPermission(context) { granted ->
        Log.d(TAG, "Permission request callback: $granted")
        onStatusUpdate?.let {
            updateShizukuStatus(context, it)
        } ?: run {
            // If no explicit callback provided, re-evaluate status after permission attempt
            // This might require a recomposition trigger, not directly handled here without a ViewModel
        }
    }
}

private fun updateShizukuStatus(context: Context, onStatusUpdate: (String, Boolean) -> Unit) {
    if (ShizukuManager.isShizukuAvailable()) {
        val granted = Shizuku.checkSelfPermission() == PackageManager.PERMISSION_GRANTED
        if (granted) {
            onStatusUpdate("Connected", true)
        } else {
            onStatusUpdate("Permission Pending", false)
        }
    } else {
        onStatusUpdate("Disconnected", false)
    }
}

private fun openShizukuApp(context: Context) {
    val intent = context.packageManager.getLaunchIntentForPackage("moe.shizuku.privileged.api")
    if (intent != null) {
        context.startActivity(intent)
    } else {
        val browserIntent =
            Intent(Intent.ACTION_VIEW, Uri.parse("https://shizuku.rikka.app/download/"))
        context.startActivity(browserIntent)
        Log.w(TAG, "Shizuku app not found, opening download page.")
    }
}
