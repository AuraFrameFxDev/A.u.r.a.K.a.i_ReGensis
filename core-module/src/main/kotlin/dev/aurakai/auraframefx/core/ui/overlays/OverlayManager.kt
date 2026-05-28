package dev.aurakai.auraframefx.core.ui.overlays

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OverlayManager @Inject constructor() {

    private var overlayActive = false

    fun hasOverlayPermission(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Settings.canDrawOverlays(context)
        } else {
            true
        }
    }

    fun requestOverlayPermission(activity: ComponentActivity, onResult: (Boolean) -> Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(activity)) {
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:${activity.packageName}")
                )

                val launcher = activity.registerForActivityResult(
                    ActivityResultContracts.StartActivityForResult()
                ) {
                    val granted = Settings.canDrawOverlays(activity)
                    onResult(granted)
                }
                launcher.launch(intent)
            } else {
                onResult(true)
            }
        } else {
            onResult(true)
        }
    }

    fun startOverlay(context: Context) {
        if (!hasOverlayPermission(context)) {
            Timber.w("OverlayManager: Cannot start overlay - permission not granted")
            return
        }
        // TODO: Ensure FloatingAgentOverlay service exists
        // val intent = Intent(context, FloatingAgentOverlay::class.java)
        // context.startService(intent)
        overlayActive = true
    }

    fun stopOverlay(context: Context) {
        // val intent = Intent(context, FloatingAgentOverlay::class.java)
        // context.stopService(intent)
        overlayActive = false
    }

    fun isOverlayActive(): Boolean = overlayActive
}
