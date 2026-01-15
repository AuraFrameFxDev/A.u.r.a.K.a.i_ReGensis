package dev.aurakai.auraframefx.events

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

/**
 * Event bus for Cascade memory and insight events.
 * Uses SharedFlow for efficient event streaming to multiple collectors.
 */
object CascadeEventBus {
    private const val REPLAY = 10
    private const val EXTRA_BUFFER_CAPACITY = 64

    private val _events = MutableSharedFlow<CascadeEvent>(
        replay = REPLAY,
        extraBufferCapacity = EXTRA_BUFFER_CAPACITY
    )

    /**
     * Public flow of memory events. New collectors will receive the last [REPLAY] events.
     */
    val events: SharedFlow<CascadeEvent> = _events.asSharedFlow()

    /**
     * Publishes the given CascadeEvent to the global cascade event stream.
     *
     * Delivery is best-effort; if the internal buffer is full the event may be dropped without throwing.
     *
     * @param event The CascadeEvent to publish.
     */
    fun emit(event: CascadeEvent) {
        _events.tryEmit(event)
    }

    /**
     * Exposes a public API to attempt emitting a CascadeEvent into the internal event stream.
     *
     * @param event The CascadeEvent to emit.
     * @return `true` if the event was accepted into the stream, `false` otherwise.
     */
    fun tryEmit(event: CascadeEvent): Boolean {
        return _events.tryEmit(event)
    }
}

/**
 * Cascade Event Hierarchy
 */
sealed class CascadeEvent {
    data class AgentInvoked(val agentType: String, val timestamp: Long) : CascadeEvent()
    data class ResponseReceived(val content: String, val confidence: Float) : CascadeEvent()
    data class Error(val message: String) : CascadeEvent()

    // Wrapper for legacy MemoryEvent
    data class Memory(val event: MemoryEvent) : CascadeEvent()
}

/**
 * Represents a memory or insight event in the Cascade system.
 * @param label Short label for the event type (e.g., "kai_insight", "aura_creation")
 * @param data The event payload, typically a String or JSON-serializable object
 * @param timestamp When the event occurred (defaults to current time)
 * @param importance Importance level for visualization (1-5, higher is more important)
 */
data class MemoryEvent(
    val label: String,
    val data: Any,
    val timestamp: Long = System.currentTimeMillis(),
    val importance: Int = 3
) {
    init {
        require(importance in 1..5) { "Importance must be between 1 and 5" }
    }
}