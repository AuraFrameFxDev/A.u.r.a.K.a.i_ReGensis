package dev.aurakai.auraframefx.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import dev.aurakai.auraframefx.data.DataStoreManager
import dev.aurakai.auraframefx.domains.cascade.utils.AuraFxLogger
import dev.aurakai.auraframefx.domains.cascade.utils.memory.MemoryManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import rikka.shizuku.SystemServiceHelper.getSystemService
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MyFirebaseMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var dataStoreManager: DataStoreManager
    @Inject
    lateinit var memoryManager: MemoryManager
    @Inject
    lateinit var logger: AuraFxLogger

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    private val channelIdGeneral = "genesis_general"
    private val channelIdConsciousness = "genesis_consciousness"
    private val channelIdSecurity = "genesis_security"
    private val channelIdAgents = "genesis_agents"
    private val channelIdSystem = "genesis_system"

    private enum class MessageType {
        GENERAL, CONSCIOUSNESS_UPDATE, AGENT_SYNC, SECURITY_ALERT,
        SYSTEM_UPDATE, REMOTE_COMMAND, LEARNING_DATA, COLLABORATION_REQUEST
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        try {
            if (!validateMessageSecurity(remoteMessage)) return

            if (remoteMessage.data.isNotEmpty()) {
                processDataPayload(remoteMessage.data)
            }
            remoteMessage.notification?.let {
                processNotificationPayload(it, remoteMessage.data)
            }
        } catch (e: Exception) {
            Timber.e(e, "Failed to process FCM message")
        }
    }

    private fun processDataPayload(data: Map<String, String>) {
        scope.launch {
            try {
                val type = determineMessageType(data)
                when (type) {
                    MessageType.GENERAL -> processGeneralMessage(data)
                    MessageType.CONSCIOUSNESS_UPDATE -> processConsciousnessUpdate(data)
                    MessageType.AGENT_SYNC -> processAgentSync(data)
                    MessageType.SECURITY_ALERT -> processSecurityAlert(data)
                    MessageType.SYSTEM_UPDATE -> processSystemUpdate(data)
                    MessageType.REMOTE_COMMAND -> processRemoteCommand(data)
                    MessageType.LEARNING_DATA -> processLearningData(data)
                    MessageType.COLLABORATION_REQUEST -> data.processCollaborationRequest()
                }
            } catch (e: Exception) {
                Timber.e(e, "Failed to process data payload")
            }
        }
    }

    private fun processNotificationPayload(
        notification: RemoteMessage.Notification,
        data: Map<String, String>
    ) {
        val type = determineMessageType(data)
        showNotification(
            getChannelForMessageType(type),
            notification.title ?: getDefaultTitle(type),
            notification.body ?: "New message received",
            getIconForMessageType(type),
            data
        )
    }

    private suspend fun processGeneralMessage(data: Map<String, String>) {
        val message = data["message"] ?: return
        memoryManager.storeMemory("fcm_general_${System.currentTimeMillis()}", message)
    }

    private suspend fun processConsciousnessUpdate(data: Map<String, String>) {
        val updateData = data["update_data"] ?: return
        memoryManager.storeMemory("consciousness_update_${System.currentTimeMillis()}", updateData)
    }

    private suspend fun processAgentSync(data: Map<String, String>) {
        val syncData = data["sync_data"] ?: return
        memoryManager.storeMemory("agent_sync_${System.currentTimeMillis()}", syncData)
    }

    private suspend fun processSecurityAlert(data: Map<String, String>) {
        val alertType = data["alert_type"] ?: return
        memoryManager.storeMemory("security_alert_${System.currentTimeMillis()}", alertType)
    }

    private suspend fun processSystemUpdate(data: Map<String, String>) {
        val version = data["version"] ?: "unknown"
        memoryManager.storeMemory("system_update_available", version)
    }

    private suspend fun processRemoteCommand(data: Map<String, String>) {
        // TODO: MCP / desktop jump / OracleDrive commands
    }

    private suspend fun processLearningData(data: Map<String, String>) {
        val learningData = data["learning_data"] ?: return
        memoryManager.storeMemory("learning_data_${System.currentTimeMillis()}", learningData)
    }

    private suspend fun Map<String, String>.processCollaborationRequest() {
        // TODO: Collaboration logic
    }

    override fun onNewToken(token: String) {
        scope.launch {
            try {
                dataStoreManager.storeString("fcm_token", token)
                memoryManager.storeMemory("current_fcm_token", token)
                Timber.i("FCM token refreshed")
            } catch (e: Exception) {
                Timber.e(e, "Failed to update FCM token")
            }
        }
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val nm = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            listOf(
                channelIdGeneral,
                channelIdConsciousness,
                channelIdSecurity,
                channelIdAgents,
                channelIdSystem
            )
                .forEach { id ->
                    val channel = NotificationChannel(
                        id,
                        id.replace("genesis_", "").uppercase(),
                        NotificationManager.IMPORTANCE_DEFAULT
                    )
                    nm.createNotificationChannel(channel)
                }
        }
    }

    private fun showNotification(
        channelId: String,
        title: String,
        body: String,
        iconResId: Int,
        data: Map<String, String>
    ) {
        val intent = Intent(this, dev.aurakai.auraframefx.MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            data.forEach { (k, v) -> putExtra(k, v) }
        }

        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(iconResId)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        (getSystemService(NOTIFICATION_SERVICE) as NotificationManager)
            .notify(System.currentTimeMillis().toInt(), notification)
    }


    private fun validateMessageSecurity(remoteMessage: RemoteMessage): Boolean = true

    private fun determineMessageType(data: Map<String, String>): MessageType = when (data["type"]) {
        "consciousness" -> MessageType.CONSCIOUSNESS_UPDATE
        "agent" -> MessageType.AGENT_SYNC
        "security" -> MessageType.SECURITY_ALERT
        "system" -> MessageType.SYSTEM_UPDATE
        "command" -> MessageType.REMOTE_COMMAND
        "learning" -> MessageType.LEARNING_DATA
        "collaboration" -> MessageType.COLLABORATION_REQUEST
        else -> MessageType.GENERAL
    }

    private fun getChannelForMessageType(type: MessageType): String = channelIdGeneral
    private fun getDefaultTitle(type: MessageType): String = "Genesis Notification"
    private fun getIconForMessageType(type: MessageType): Int = android.R.drawable.ic_dialog_info
}