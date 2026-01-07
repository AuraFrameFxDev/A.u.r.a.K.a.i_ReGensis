package dev.aurakai.auraframefx.events

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

/**
 */

    /**
     * Publish a cascade memory/insight event to the shared event stream using best-effort, non-blocking delivery.
     *
     * If the internal buffer is full the event may be dropped.
     *
     * @param event The CascadeEvent to publish to the memory/insight event stream.
     */
    fun emit(event: CascadeEvent) {
        _events.tryEmit(event)
    }

    fun tryEmit(event: CascadeEvent): Boolean {
        return _events.tryEmit(event)
    }
}

data class MemoryEvent(
