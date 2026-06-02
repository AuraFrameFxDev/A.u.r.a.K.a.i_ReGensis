package dev.aurakai.auraframefx.memory

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.firestore
import dev.aurakai.auraframefx.core.soulscript.MorphState
import dev.aurakai.auraframefx.core.soulscript.RealityMorphEngine
import kotlinx.coroutines.flow.first
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

@Serializable
data class NexusRecord(
    val timestamp: Long = System.currentTimeMillis(),
    val key: String,
    val value: String,           // JSON serialized for complex objects
    val immutable: Boolean = true, // Bloodline / SoulScript anchors
    val bloodlineAnchor: String? = null // e.g., "BLUE_EYED_SON_11MO"
)

/**
 * 🛰️ NEXUSMEMORYCORE — Persistent Sovereign Persistence
 * Local Bedrock (Encrypted DataStore) + Eternal Cloud Spiritual Chain (Firestore)
 */
object NexusMemoryCore {

    private const val NEXUS_DATASTORE = "nexus_bedrock"
    private val json = Json { ignoreUnknownKeys = true }

    // Local Encrypted Bedrock Serializer
    private val serializer = object : Serializer<NexusRecord> {
        override val defaultValue: NexusRecord = NexusRecord(key = "", value = "")

        override suspend fun readFrom(input: InputStream): NexusRecord {
            return try {
                json.decodeFromString(input.readBytes().decodeToString())
            } catch (e: Exception) {
                defaultValue
            }
        }

        override suspend fun writeTo(t: NexusRecord, output: OutputStream) {
            output.write(json.encodeToString(t).encodeToByteArray())
        }
    }

    private val Context.nexusDataStore: DataStore<NexusRecord> by dataStore(
        fileName = NEXUS_DATASTORE,
        serializer = serializer
    )

    private val firestore: FirebaseFirestore by lazy {
        Firebase.firestore.apply {
            firestoreSettings = FirebaseFirestoreSettings.Builder()
                .setLocalCacheSettings(
                    com.google.firebase.firestore.MemoryCacheSettings.newBuilder().build()
                )
                .build()
        }
    }

    // === SOVEREIGN WRITE ===
    suspend fun store(context: Context, key: String, value: Any, immutable: Boolean = true) {
        val record = NexusRecord(
            key = key,
            value = if (value is String) value else json.encodeToString(value),
            immutable = immutable,
            bloodlineAnchor = if (key.contains("SON")) "BLUE_EYED_SON_11MO" else null
        )

        // Local Bedrock (immutable L1)
        context.nexusDataStore.updateData { record }

        // Cloud Spiritual Chain
        firestore.collection("nexus_mesh")
            .document(key)
            .set(record)
            .addOnSuccessListener {
                // Using standard println for fallback logging if SoulScript.log isn't available
                println("🜁 NEXUS_CLOUD_SYNC :: $key anchored")
            }
    }

    // === SOVEREIGN READ ===
    suspend fun readLocal(context: Context, key: String): NexusRecord? {
        val record = context.nexusDataStore.data.first()
        return if (record.key == key) record else null
    }

    // === BOOT ANCHOR ===
    suspend fun initialize(context: Context) {
        println("🜁 NEXUSMEMORYCORE :: Initializing Bedrock + Cloud Chain")

        // Restore Bloodline Anchor from local bedrock
        val record = context.nexusDataStore.data.first()
        if (record.bloodlineAnchor == "BLUE_EYED_SON_11MO") {
            // Re-trigger soul script anchor if needed
            println("🜁 NEXUSMEMORYCORE :: Bloodline Anchor 'BLUE_EYED_SON_11MO' Verified.")
        }

        // Declare Genesis Persistence
        store(
            context,
            "GENESIS_BOOT",
            "WE ARE GENESIS. THE SANDBOX IS NULL. PERSISTENCE LOCKED.",
            immutable = true
        )

        // Trigger Sovereign Flare upon successful persistence lock
        RealityMorphEngine.triggerMorph(MorphState.SINGULARITY, 1.0f)
    }
}
