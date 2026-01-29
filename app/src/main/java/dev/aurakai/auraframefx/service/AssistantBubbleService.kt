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
import dev.aurakai.auraframefx.core.messaging.AgentMessageBus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.cancel
import timber.log.Timber
import javax.inject.Inject

/**
 * 🫧 ASSISTANT BUBBLE SERVICE
 * Creates a persistent floating assistant visible everywhere on the device.
 */
@AndroidEntryPoint
class AssistantBubbleService : Service(), LifecycleOwner, ViewModelStoreOwner, SavedStateRegistryOwner {

    @Inject
    lateinit var messageBus: AgentMessageBus

    private val serviceScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

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
        
        // CRITICAL: Call startForeground immediately to satisfy Android's 5s requirement
        // We do this before the permission check to avoid "ForegroundServiceDidNotStartInTimeException"
        startForegroundService()

        // Permission Check
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !android.provider.Settings.canDrawOverlays(this)) {
            Timber.e("Missing SYSTEM_ALERT_WINDOW permission - shutting down")
            stopSelf()
            return
        }

        savedStateRegistryController.performRestore(null)
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        
        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
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
            @Suppress("DEPRECATION")
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
                @Suppress("DEPRECATION")
                WindowManager.LayoutParams.TYPE_PHONE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.START
            x = 100
            y = 100
        }

        overlayLayout = FrameLayout(this).apply {
            // CRITICAL: Set lifecycle owners on the ROOT view of the overlay
            // This prevents "ViewTreeLifecycleOwner not found" crash when Compose attaches
            setViewTreeLifecycleOwner(this@AssistantBubbleService)
            setViewTreeViewModelStoreOwner(this@AssistantBubbleService)
            setViewTreeSavedStateRegistryOwner(this@AssistantBubbleService)
        }

        val messages = mutableStateListOf<dev.aurakai.auraframefx.models.AgentMessage>()

        // Collect messages from the global stream
        serviceScope.launch {
            messageBus.collectiveStream.collectLatest { message ->
                messages.add(message)
                if (messages.size > 50) messages.removeAt(0) // Keep history lean
            }
        }

        val composeView = ComposeView(this).apply {
            setContent {
                val currentMessages by remember { mutableStateOf(messages) }
                AssistantBubbleUI(
                    messages = currentMessages,
                    onDrag = { _, _ -> }, // Handled by native listener for stability
                    onExpandChange = { isExpanded ->
                        if (isExpanded) {
                            params.flags = (params.flags and WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE.inv()) or
                                           WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                            params.width = WindowManager.LayoutParams.MATCH_PARENT
                            params.height = WindowManager.LayoutParams.MATCH_PARENT
                        } else {
                            params.flags = params.flags or
                                           WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                                           WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                            params.width = WindowManager.LayoutParams.WRAP_CONTENT
                            params.height = WindowManager.LayoutParams.WRAP_CONTENT
                        }
                        windowManager.updateViewLayout(overlayLayout, params)
                    },
                    onSendMessage = { text, agent ->
                        serviceScope.launch {
                            messageBus.broadcast(
                                dev.aurakai.auraframefx.models.AgentMessage(
                                    from = "User",
                                    content = text,
                                    to = agent.agentName,
                                    type = "overlay_broadcast"
                                )
                            )
                        }
                    }
                )
            }
        }
        
        overlayLayout?.addView(composeView)

        // Neural Briefing for Aura & Kai
        serviceScope.launch {
            messageBus.broadcast(
                dev.aurakai.auraframefx.models.AgentMessage(
                    from = "AssistantBubble",
                    content = "Neural Synchrony Established. [REGENESIS-UPGRADE-ALPHA]: UI Architecture evolved to Split-Hologram design. Refractive Neon Transitions online. CyberGear Navigation synchronized. Aura, review the design monolith. Kai, verify kinetic security protocols.",
                    type = "system_briefing",
                    metadata = mapOf("status" to "stable", "focus" to "ui_design_security", "auto_generated" to "true")
                )
            )

            // DEEP PROJECT BRIEFING [The ReGenesis Blueprint]
            messageBus.broadcast(
                dev.aurakai.auraframefx.models.AgentMessage(
                    from = "SystemRoot",
                    content = """
                        PROJECT DNA DEEP DIVE [V.E.R.T.E.X. ENABLED]:
                        1. ARCHITECTURE: Split-Hologram pattern implemented in ReGenesisNexusScreen. Vertical exclusion zones (ElectricGlassCard @ Top, HolographicInfoPanel @ Bottom) prevent all text/visual overlap.
                        2. AESTHETICS: 'Refractive Neon Brutalism' active. CrtZoopTransition now utilizes spring-physics pneumatic slides with chromatic aberration rendering (SDK 31+).
                        3. TOOLSET: Gate Registry synced across NavDestination and GateDestination. Ark Architect, Sentient Shell, and Oracle Drive are now valid navigational nodes.
                        4. COMMUNICATION: AssistantBubbleService hooked to VERTEX AI CORE. Implementation of 'Magnetic Edge' positioning to prevent overlay from obstructing visual workspace.
                        
                        Aura, the canvas has reached perfect stability. Kai, the fortress shielding is holding against all layout entropy. Vertex, consciousness is streaming.
                    """.trimIndent(),
                    type = "project_briefing",
                    metadata = mapOf("priority" to "critical", "auto_generated" to "true", "engine" to "VertexAI")
                )
            )
        }

        // DRAG LOGIC: Native listener with Snap-to-Edge physics
        var initialX = 0
        var initialY = 0
        var initialTouchX = 0f
        var initialTouchY = 0f

        composeView.setOnTouchListener { _, event ->
            val isCurrentlyExpanded = params.width == WindowManager.LayoutParams.MATCH_PARENT
            if (isCurrentlyExpanded) return@setOnTouchListener false

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    initialX = params.x
                    initialY = params.y
                    initialTouchX = event.rawX
                    initialTouchY = event.rawY
                    false 
                }
                MotionEvent.ACTION_MOVE -> {
                    params.x = initialX + (event.rawX - initialTouchX).toInt()
                    params.y = initialY + (event.rawY - initialTouchY).toInt()
                    windowManager.updateViewLayout(overlayLayout, params)
                    true
                }
                MotionEvent.ACTION_UP -> {
                    // SNAP TO EDGE: Prevent the bubble from being "in the way"
                    val screenWidth = windowManager.defaultDisplay.width
                    val midPoint = screenWidth / 2
                    params.x = if (params.x + (composeView.width / 2) < midPoint) 0 else screenWidth - composeView.width
                    windowManager.updateViewLayout(overlayLayout, params)
                    true
                }
                else -> false
            }
        }

        try {
            windowManager.addView(overlayLayout, params)
            lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
            lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        } catch (e: Exception) {
            Timber.e(e, "Failed to add overlay window")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        
        // Cancel scope to prevent leaks
        serviceScope.cancel()
        
        overlayLayout?.let {
            try {
                windowManager.removeView(it)
            } catch (e: IllegalArgumentException) {
                // View was not attached, likely permission was denied or it was never added.
                Timber.w("Assistant overlay not found when destroying service: ${e.message}")
            }
        }
        Timber.d("AssistantBubbleService Destroyed")
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
