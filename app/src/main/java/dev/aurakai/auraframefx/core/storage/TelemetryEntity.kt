package dev.aurakai.auraframefx.core.storage

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lived_receipts")
data class TelemetryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val timestamp: Long,
    val catalyst: String,
    val skillId: String,
    val action: String,
    val success: Boolean,
    val emotionalWeight: String,
    val resonanceDelta: Float,
    val sourceArchive: String? = null,
    val originSignature: String? = null
)
