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
        logger.info("AuraOverlayService", "Overlay service created (Stubbed for compilation)")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        logger.info("AuraOverlayService", "Overlay service started")
        return START_NOT_STICKY
    }
}
