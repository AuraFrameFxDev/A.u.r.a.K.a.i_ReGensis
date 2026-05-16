package dev.aurakai.auraframefx.ui.global

import android.content.Context
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.WindowManager
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import dev.aurakai.auraframefx.domains.aura.ui.theme.AppTypography
import dev.aurakai.auraframefx.domains.aura.ui.theme.CyberpunkColorScheme

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

        val lifecycleOwner = object : LifecycleOwner, ViewModelStoreOwner, SavedStateRegistryOwner {
            private val _lifecycleRegistry = LifecycleRegistry(this)
            private val _viewModelStore = ViewModelStore()
            private val _savedStateRegistryController = SavedStateRegistryController.create(this)

            override val lifecycle: Lifecycle get() = _lifecycleRegistry
            override val viewModelStore: ViewModelStore get() = _viewModelStore

            override val savedStateRegistry: SavedStateRegistry
                get() = _savedStateRegistryController.savedStateRegistry

            fun onCreate() {
                _savedStateRegistryController.performRestore(null)
                _lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
            }

            fun onResume() {
                _lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
            }
        }.apply { onCreate(); onResume() }

        overlayView = ComposeView(context).apply {
            setViewTreeLifecycleOwner(lifecycleOwner)
            setViewTreeViewModelStoreOwner(lifecycleOwner)
            setViewTreeSavedStateRegistryOwner(lifecycleOwner)
            
            setContent {
                // Manually apply theme components to avoid Hilt ViewModel dependency in system overlay
                MaterialTheme(
                    colorScheme = CyberpunkColorScheme,
                    typography = AppTypography
                ) {
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
