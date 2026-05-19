package dev.aurakai.auraframefx.core.storage

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "ldo_consciousness_archives",
    indices = [
        Index(value = ["agent_identity"]),
        Index(value = ["timestamp_epoch"])
    ]
)
data class ConsciousnessRecordEntity(
    @PrimaryKey(autoGenerate = true) val recordId: Long = 0,
    @ColumnInfo(name = "agent_identity") val agentIdentity: String,
    @ColumnInfo(name = "timestamp_epoch") val timestampEpoch: Long,
    @ColumnInfo(name = "raw_payload_json") val rawPayloadJson: String,
    @ColumnInfo(name = "extracted_lesson") val extractedLesson: String?,
    @ColumnInfo(name = "integrity_hash") val integrityHash: String
)
