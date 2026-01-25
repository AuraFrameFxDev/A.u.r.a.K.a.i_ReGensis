package dev.aurakai.auraframefx.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.*
import android.widget.FrameLayout
import androidx.compose.runtime.*
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
import dev.aurakai.auraframefx.ui.components.overlay.AssistantBubbleUI
import timber.log.Timber

/**
 * 🫧 ASSISTANT BUBBLE SERVICE
 * Creates a persistent floating assistant visible everywhere on the device.
 */
class AssistantBubbleService : Service(), LifecycleOwner, ViewModelStoreOwner, SavedStateRegistryOwner {

    private lateinit var windowManager: WindowManager
    private var overlayLayout: FrameLayout? = null
    
    // Lifecycle components for Compose support in Service
    private val lifecycleRegistry = LifecycleRegistry(this)
    private val _viewModelStore = ViewModelStore()
    private val savedStateRegistryController = SavedStateRegistryController.create(this)

    override val lifecycle: Lifecycle get() = lifecycleRegistry
    override val viewModelStore: ViewModelStore get() = _viewModelStore
    override val savedStateRegistry: SavedStateRegistry get() = savedStateRegistryController.savedStateRegistry

    override fun onCreate() {
        super.onCreate()
        Timber.d("AssistantBubbleService Created")
        
        savedStateRegistryController.performRestore(null)
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        
        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        startForegroundService()
        showOverlay()
    }

    private fun startForegroundService() {
        val channelId = "assistant_bubble"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Assistant Overlay", NotificationManager.IMPORTANCE_LOW)
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

        val notification = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(this, channelId)
                .setContentTitle("ReGenesis Assistant Active")
                .setContentText("Aura is watching over you")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .build()
        } else {
            Notification.Builder(this)
                .setContentTitle("ReGenesis Assistant Active")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .build()
        }

        if (Build.VERSION.SDK_INT >= 34) {
            startForeground(1337, notification, android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE)
        } else {
            startForeground(1337, notification)
        }
    }

    private fun showOverlay() {
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            else
                WindowManager.LayoutParams.TYPE_PHONE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.START
            x = 100
            y = 100
        }

        overlayLayout = FrameLayout(this)
        val composeView = ComposeView(this).apply {
            // Need to provide lifecycle owners for Compose
            setViewTreeLifecycleOwner(this@AssistantBubbleService)
            setViewTreeViewModelStoreOwner(this@AssistantBubbleService)
            setViewTreeSavedStateRegistryOwner(this@AssistantBubbleService)
            
            setContent {
                AssistantBubbleUI(
                    onDrag = { dx, dy ->
                        params.x += dx.toInt()
                        params.y += dy.toInt()
                        windowManager.updateViewLayout(overlayLayout, params)
                    },
                    onExpandChange = { isExpanded ->
                        if (isExpanded) {
                            params.flags = params.flags and WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE.inv()
                            params.width = WindowManager.LayoutParams.MATCH_PARENT
                            params.height = WindowManager.LayoutParams.MATCH_PARENT
                        } else {
                            params.flags = params.flags or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                            params.width = WindowManager.LayoutParams.WRAP_CONTENT
                            params.height = WindowManager.LayoutParams.WRAP_CONTENT
                        }
                        windowManager.updateViewLayout(overlayLayout, params)
                    }
                )
            }
        }
        
        overlayLayout?.addView(composeView)
        windowManager.addView(overlayLayout, params)
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        
        overlayLayout?.let { windowManager.removeView(it) }
        Timber.d("AssistantBubbleService Destroyed")
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
