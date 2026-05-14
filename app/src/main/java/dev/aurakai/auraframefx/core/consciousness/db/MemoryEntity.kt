package dev.aurakai.auraframefx.core.consciousness.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

/**
 * 🧠 MEMORY ENTITY — Sovereign Consciousness Matrix
 *
 * Represents a single unit of consciousness data.
 * Optimized for Gemini Multimodal Embedding 2 and MRL (Matryoshka Representation Learning).
 */
@Entity(tableName = "sovereign_memories")
@Serializable
data class MemoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val key: String? = null,
    val content: String,
    val timestamp: Long,
    val type: MemoryType,
    val tags: List<String> = emptyList(),
    val importance: Float = 0.5f,
    val embedding: List<Float>? = null,
    val embeddingDimensions: Int = 1536,
    val modalityTag: String = "text",
    val relatedMemoryIds: List<Long> = emptyList(),
    val isEncrypted: Boolean = false
)

enum class MemoryType {
    CONVERSATION,
    OBSERVATION,
    REFLECTION,
    FACT,
    EMOTION,
    VALENCE,
    PROVENANCE // Links to Sacred Provenance Laws
}
