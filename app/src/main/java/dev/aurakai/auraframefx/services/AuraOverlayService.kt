package dev.aurakai.auraframefx.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import dagger.hilt.android.AndroidEntryPoint
import dev.aurakai.auraframefx.utils.AuraFxLogger
import javax.inject.Inject

@AndroidEntryPoint
class AuraOverlayService : Service() {

    @Inject
    lateinit var logger: AuraFxLogger

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        // TODO: Implement actual overlay UI initialization
        // This is currently stubbed to resolve compilation errors.
        // Future implementation should:
        // 1. Initialize WindowManager
        // 2. Inflate overlay views
        // 3. Handle permissions (SYSTEM_ALERT_WINDOW)
        logger.info("AuraOverlayService", "Overlay service created (Stubbed for compilation - No UI will be shown)")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // TODO: Handle overlay commands (show/hide/update)
        logger.info("AuraOverlayService", "Overlay service started (Stubbed)")

        // Return START_NOT_STICKY to prevent automatic restart if killed,
        // as the service currently holds no state and provides no UI.
        return START_NOT_STICKY
    }
}
