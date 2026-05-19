package dev.aurakai.auraframefx.core.binder

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object BinderTelemetryConduit {
    private val _transactionFlow = MutableSharedFlow<TransactionPulse>(
        extraBufferCapacity = 64,
        onBufferOverflow = kotlinx.coroutines.channels.BufferOverflow.DROP_OLDEST
    )
    val transactionFlow = _transactionFlow.asSharedFlow()

    data class TransactionPulse(
        val code: Int,
        val descriptor: String,
        val timestamp: Long = System.currentTimeMillis(),
        val payloadSize: Int
    )

    fun emitPulse(code: Int, descriptor: String, size: Int) {
        _transactionFlow.tryEmit(TransactionPulse(code, descriptor, payloadSize = size))
    }
}
