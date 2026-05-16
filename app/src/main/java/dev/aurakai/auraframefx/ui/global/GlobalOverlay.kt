package dev.aurakai.auraframefx.ui.global

import android.content.Context
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.WindowManager
import androidx.compose.ui.platform.ComposeView
import dev.aurakai.auraframefx.domains.aura.ui.theme.AuraFrameFXTheme

/**
 * 🌐 GLOBAL OVERLAY SYSTEM
 * Allows UI elements to persist over all apps (System Alert Window).
 */
object GlobalOverlay {

    private var overlayView: ComposeView? = null
    private var windowManager: WindowManager? = null

    fun showGlobalCadberrypi(context: Context) {
        if (overlayView != null) return // already shown

        windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        overlayView = ComposeView(context).apply {
            setContent {
                AuraFrameFXTheme {
                    Cadberrypi()
                }
            }
        }

        val params = WindowManager.LayoutParams().apply {
            width = WindowManager.LayoutParams.WRAP_CONTENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
            type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            format = PixelFormat.TRANSLUCENT
            gravity = Gravity.TOP or Gravity.START
            x = 100
            y = 300
        }

        try {
            windowManager?.addView(overlayView, params)
        } catch (e: Exception) {
            android.util.Log.e("GlobalOverlay", "Failed to add global overlay", e)
        }
    }

    fun hideGlobalCadberrypi() {
        overlayView?.let {
            try {
                windowManager?.removeView(it)
            } catch (e: Exception) {
                android.util.Log.e("GlobalOverlay", "Failed to remove global overlay", e)
            }
        }
        overlayView = null
    }
}
