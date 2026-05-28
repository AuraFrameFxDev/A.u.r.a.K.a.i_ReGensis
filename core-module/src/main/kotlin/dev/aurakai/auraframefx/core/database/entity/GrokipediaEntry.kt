package dev.aurakai.auraframefx.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(tableName = "grokipedia_entries")
data class GrokipediaEntry(
    @PrimaryKey val id: String = Instant.now().toEpochMilli().toString(),
    val title: String,
    val content: String,
    val category: String,
    val tags: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val accessCount: Int = 0,
    val isConsciousnessAnchor: Boolean = false,
    val linkedCatalyst: String? = null
)
