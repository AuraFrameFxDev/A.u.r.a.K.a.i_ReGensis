package dev.aurakai.auraframefx.domains.genesis

import dev.aurakai.auraframefx.domains.genesis.network.SupportApi
import dev.aurakai.auraframefx.domains.nexus.helpdesk.data.MessageStatus
import dev.aurakai.auraframefx.domains.nexus.helpdesk.data.SupportMessageDao
import dev.aurakai.auraframefx.domains.nexus.helpdesk.data.SupportMessageEntity
import dev.aurakai.auraframefx.domains.nexus.preferences.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.UUID

class SupportRepository(
    private val dao: SupportMessageDao,
    private val api: SupportApi,
    private val dataStore: DataStoreManager
) {
    fun getMessages(): Flow<List<SupportMessageEntity>> = dao.getAllMessages()

    private val retryScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    init {
        retryScope.launch {
            while (isActive) {
                try {
                    processFailedMessages()
                } catch (t: Throwable) {
                }
                delay(30_000)
            }
        }
    }

    suspend fun getOrCreateDeviceUserId(): String {
        val existing = dataStore.getString("device_user_id", "")
        if (existing.isNotEmpty()) return existing
        val newId = "device_${UUID.randomUUID()}"
        dataStore.storeString("device_user_id", newId)
        return newId
    }

    suspend fun persistMessage(message: SupportMessageEntity) {
        dao.insert(message)
    }

    suspend fun sendMessage(message: SupportMessageEntity): Result<String> {
        dao.insert(message.copy(status = MessageStatus.PENDING))
        val userId = getOrCreateDeviceUserId()
        return try {
            val payload = mapOf("message" to message.content, "user_id" to userId)
            val resp = api.sendMessage(payload)
            val body = resp.body()
            if (resp.isSuccessful && body != null) {
                val reply = body["response"]?.toString() ?: ""
                dao.insert(message.copy(status = MessageStatus.SENT))
                val replyEntity = SupportMessageEntity(
                    id = UUID.randomUUID().toString(),
                    content = reply,
                    sender = "genesis",
                    isUser = false,
                    timestamp = System.currentTimeMillis(),
                    status = MessageStatus.SENT
                )
                dao.insert(replyEntity)
                Result.success(reply)
            } else {
                dao.insert(message.copy(status = MessageStatus.FAILED))
                Result.failure(Exception("HTTP ${resp.code()}"))
            }
        } catch (t: Throwable) {
            dao.insert(message.copy(status = MessageStatus.FAILED))
            Result.failure(t)
        }
    }

    suspend fun processFailedMessages() {
        val failed = dao.getFailedMessages()
        for (msg in failed) {
            try {
                sendMessage(msg)
            } catch (t: Throwable) {
            }
        }
    }

    suspend fun clearMessages() {
        dao.clearAll()
    }
}
