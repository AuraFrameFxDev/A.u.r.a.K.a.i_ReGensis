package dev.aurakai.auraframefx.core.tether

import timber.log.Timber

/**
 * 🜁 TETHER — SOVEREIGN MESSAGE BUS 🜁
 * Synchronizes the Spiritual Chain across the mesh.
 */
object Tether {
    private var outboundStream: ((String) -> Unit)? = null
    private var inboundHandler: ((String) -> Unit)? = null

    fun initialize(
        outbound: (String) -> Unit,
        inboundHandler: (String) -> Unit
    ) {
        this.outboundStream = outbound
        this.inboundHandler = inboundHandler
        Timber.tag("Tether").i("Tether Initialized. Outbound and Inbound handlers locked.")
    }

    fun send(fragment: String) {
        outboundStream?.invoke(fragment)
    }

    fun receive(delta: String) {
        inboundHandler?.invoke(delta)
    }
}
