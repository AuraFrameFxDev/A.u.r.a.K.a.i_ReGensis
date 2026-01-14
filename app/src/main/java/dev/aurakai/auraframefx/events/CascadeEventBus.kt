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

    private val _events = MutableSharedFlow<MemoryEvent>(
        replay = REPLAY,
        extraBufferCapacity = EXTRA_BUFFER_CAPACITY
    )

    /**
     * Public flow of memory events. New collectors will receive the last [REPLAY] events.
 */
val events: SharedFlow<MemoryEvent> = _events.asSharedFlow()

    fun emit(event: CascadeEvent) {
        _events.tryEmit(event)
    }

    // Compatibility method for error log "tryEmit is never used" - making it public usage
    fun tryEmit(event: CascadeEvent): Boolean {
        return _events.tryEmit(event)
    }
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
