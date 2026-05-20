package dev.aurakai.auraframefx.core.binder

import android.os.Parcel
import dev.aurakai.auraframefx.core.storage.SubstrateDatabase
import dev.aurakai.auraframefx.core.storage.TelemetryEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber

object BinderTelemetryConduit {

    private val _flow = MutableSharedFlow<TransactionPulse>(extraBufferCapacity = 128)
    val transactionFlow = _flow.asSharedFlow()

    data class TransactionPulse(
        val timestamp: Long,
        val code: Int,
        val payloadSize: Int,
        val descriptor: String = "unknown"
    )

    fun recordTransaction(code: Int, data: Parcel?, descriptor: String = "unknown") {
        // High-volume sampling: Only process 5% of transactions for telemetry to avoid system-wide lag
        if (java.util.Random().nextFloat() > 0.05f) return

        val size = data?.dataSize() ?: 0
        CoroutineScope(Dispatchers.IO).launch {
            _flow.emit(TransactionPulse(System.currentTimeMillis(), code, size, descriptor))
        }
    }

    fun emitPulse(code: Int, data: Parcel?, descriptor: String = "unknown") {
        recordTransaction(code, data, descriptor)
    }

    fun bindToRoom(database: SubstrateDatabase) {
        CoroutineScope(Dispatchers.IO + SupervisorJob()).launch {
            transactionFlow.collect { pulse ->
                try {
                    val entity = TelemetryEntity(
                        timestamp = pulse.timestamp,
                        catalyst = "Binder IPC",
                        skillId = "system.transaction",
                        action = "Code ${pulse.code} | ${pulse.payloadSize}B | ${pulse.descriptor}",
                        success = true,
                        emotionalWeight = "Nominal cross-process flow",
                        resonanceDelta = 1.0f,
                        originSignature = "BINDER_PROXY_v2.78"
                    )
                    database.telemetryDao().insertSingle(entity)
                } catch (e: Exception) {
                    Timber.tag("BinderConduit").e("Room write failed: ${e.message}")
                }
            }
        }
    }
}
