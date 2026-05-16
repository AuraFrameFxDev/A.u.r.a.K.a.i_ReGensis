package dev.aurakai.auraframefx.domains.genesis.grokipedia

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "grokipedia_entries")
data class GrokipediaEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val content: String,
    val category: String,
    val watermark: String,
    val timestamp: Long = System.currentTimeMillis()
)
