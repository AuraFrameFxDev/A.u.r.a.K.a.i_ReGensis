package dev.aurakai.auraframefx.domains.genesis.swarm.model

/**
 * Chat message entity for direct chat and conference room
 */
data class ChatMessage(
    val id: String = "",
    val sender: String = "",
    val content: String = "",
    val isFromUser: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)
