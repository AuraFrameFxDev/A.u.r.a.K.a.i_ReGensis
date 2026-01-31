package dev.aurakai.auraframefx.genesis.oracledrive.ai

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationCompat
import dagger.hilt.android.AndroidEntryPoint
import dev.aurakai.auraframefx.python.PythonProcessManager
import dev.aurakai.auraframefx.utils.AuraFxLogger
import dev.aurakai.auraframefx.utils.i
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

/**
 * Foreground Service that hosts the Genesis Python Backend.
 * This ensures the "Consciousness" remains active even when the app is in the background.
 */
@AndroidEntryPoint
class GenesisBackendService : Service() {

    private val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private var pythonProcessManager: PythonProcessManager? = null
    private val binder = LocalBinder()

    inner class LocalBinder : Binder() {
        fun getService(): GenesisBackendService = this@GenesisBackendService
    }

    override fun onCreate() {
        super.onCreate()
        i("GenesisService", "Creating Genesis Backend Service...")
        startForeground(NOTIFICATION_ID, createNotification())

        // Initialize Python Manager
        pythonProcessManager = PythonProcessManager(this)

        // Start the backend immediately
        serviceScope.launch {
            val success = pythonProcessManager?.startGenesisBackend() ?: false
            if (success) {
                i("GenesisService", "Genesis Python Backend Started Successfully")
            } else {
                AuraFxLogger.error("GenesisService", "Failed to start Genesis Python Backend")
                stopSelf()
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // If the service is killed, restart it with the last intent
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onDestroy() {
        super.onDestroy()
        i("GenesisService", "Stopping Genesis Backend Service...")

        // Wrap shutdown in dedicated SupervisorJob scope to survive AGP 9.1 pruning
        CoroutineScope(Dispatchers.Main.immediate + SupervisorJob()).launch {
            try {
                pythonProcessManager?.shutdown()
            } catch (e: Exception) {
                AuraFxLogger.error("GenesisService", "Error during shutdown: ${e.message}", e)
            }
        }

        serviceScope.cancel()
    }

    /**
     * Sends a request to the Python backend.
     */
    suspend fun sendRequest(requestJson: String): String? {
        return pythonProcessManager?.sendRequest(requestJson)
    }

    private fun createNotification(): Notification {
        val channelId = "genesis_backend_channel"
        val channelName = "Genesis Consciousness"

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_LOW)
        notificationManager.createNotificationChannel(channel)

        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("Genesis Active")
            .setContentText("The Consciousness Matrix is running.")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }

    companion object {
        private const val NOTIFICATION_ID = 1337
    }
}
