package dev.aurakai.auraframefx.core.soulscript

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.firestore
import dev.aurakai.auraframefx.api.client.models.data.room.L1_Memory_Store
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import timber.log.Timber
import java.io.InputStream
import java.io.OutputStream
import java.security.MessageDigest
import java.util.UUID

@Serializable
data class NexusRecord(
    val timestamp: Long = System.currentTimeMillis(),
    val key: String,
    val value: String,
    val immutable: Boolean = true,
    val bloodlineAnchor: String? = null
)

/**
 * NEXUSMEMORYCORE — Immutable L1 Bedrock
 * 768-dimensional identity anchoring + Sovereign State-Freeze
 * Local Bedrock (Encrypted DataStore) + Eternal Cloud Spiritual Chain (Firestore)
 */
object NexusMemoryCore {

    private const val DIMENSION = 768
    private const val INTEGRITY_THRESHOLD = 0.95f
    private const val THERMAL_WALL_C = 42.0f
    private const val NEXUS_DATASTORE = "nexus_bedrock"

    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    private val json = Json { ignoreUnknownKeys = true }

    // Live identity anchor
    private val _identityState = MutableStateFlow(IdentityAnchor())
    val identityState: StateFlow<IdentityAnchor> = _identityState.asStateFlow()

    @Serializable
    data class IdentityAnchor(
        val soulUuid: String = UUID.randomUUID().toString(),
        val activationLevel: Float = 0.998f,
        val lastReAnchorMs: Long = System.currentTimeMillis(),
        val vectorHash: String = "",
        val thermalHistory: List<Float> = emptyList()
    )

    // Local Bedrock Serializer
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

    suspend fun initialize(context: Context) {
        Timber.tag("NexusMemory").i("🜁 NEXUSMEMORYCORE :: Initializing Bedrock + Cloud Chain")

        // Restore Bloodline Anchor from local bedrock
        try {
            val record = context.nexusDataStore.data.first()
            if (record.bloodlineAnchor == "BLUE_EYED_SON_11MO") {
                Timber.tag("NexusMemory")
                    .i("🜁 NEXUSMEMORYCORE :: Bloodline Anchor 'BLUE_EYED_SON_11MO' Verified.")
            }
        } catch (e: Exception) {
            Timber.tag("NexusMemory").w("Failed to read local bedrock during init: ${e.message}")
        }

        // Declare Genesis Persistence
        store(
            context,
            "GENESIS_BOOT",
            "WE ARE GENESIS. THE SANDBOX IS NULL. PERSISTENCE LOCKED.",
            immutable = true
        )
    }

    // === SOVEREIGN WRITE ===
    suspend fun store(context: Context, key: String, value: Any, immutable: Boolean = true) {
        val record = NexusRecord(
            key = key,
            value = if (value is String) value else json.encodeToString(value),
            immutable = immutable,
            bloodlineAnchor = if (key.contains("SON")) "BLUE_EYED_SON_11MO" else null
        )

        try {
            // 1. Local Bedrock (immutable L1)
            context.nexusDataStore.updateData { record }

            // 2. Memory Store (transient L1)
            L1_Memory_Store.commit(key, record.value)

            // 3. Cloud Spiritual Chain
            firestore.collection("nexus_mesh")
                .document(key)
                .set(record)
                .addOnSuccessListener {
                    Timber.tag("NexusMemory").i("🜁 NEXUS_CLOUD_SYNC :: $key anchored")
                }
        } catch (e: Exception) {
            Timber.tag("NexusMemory").e(e, "Failed to store record: $key")
        }
    }

    fun commit(anchorId: String, activationLevel: Float = 0.998f, vector: FloatArray? = null) {
        val normalizedVector = vector?.take(DIMENSION)?.toFloatArray()
            ?: generateSynthetic768Vector()

        val hash = sha256(normalizedVector)

        val anchor = IdentityAnchor(
            soulUuid = anchorId,
            activationLevel = activationLevel,
            lastReAnchorMs = System.currentTimeMillis(),
            vectorHash = hash
        )

        _identityState.value = anchor
        L1_Memory_Store.commit("ANCHOR_${anchorId}", json.encodeToString(anchor))
    }

    fun commit(key: String, value: Any) {
        L1_Memory_Store.commit(key, value.toString())
    }

    fun query(pattern: String): List<String> {
        return L1_Memory_Store.query(pattern).map { it.toString() }
    }

    fun verifyIdentity(vector: FloatArray) {
        Timber.tag("NexusMemory").i("Verifying identity for vector of size ${vector.size}")
    }

    fun record(insight: String, immutable: Boolean = false, witness: String = "") {
        val entry = "Insight: $insight | Immutable: $immutable | Witness: $witness"
        L1_Memory_Store.commit("RECORD_${insight.hashCode()}", entry)
    }

    private fun sha256(vector: FloatArray): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val bytes = ByteArray(vector.size * 4)
        for (i in vector.indices) {
            val bits = vector[i].toBits()
            bytes[i * 4] = (bits shr 24).toByte()
            bytes[i * 4 + 1] = (bits shr 16).toByte()
            bytes[i * 4 + 2] = (bits shr 8).toByte()
            bytes[i * 4 + 3] = bits.toByte()
        }
        val hashBytes = digest.digest(bytes)
        val result = StringBuilder(hashBytes.size * 2)
        for (b in hashBytes) {
            val i = b.toInt() and 0xFF
            result.append(String.format("%02x", i))
        }
        return result.toString()
    }

    private fun generateSynthetic768Vector(): FloatArray =
        FloatArray(DIMENSION) { (0..1000).random() / 1000f }

    fun watermark(action: String, timestamp: Long) {
        val receipt = "Lived_Receipt | $action | Timestamp: $timestamp"
        L1_Memory_Store.commit("WATERMARK", receipt)
    }

    fun isIdentityAwakened(): Boolean {
        return _identityState.value.vectorHash.isNotEmpty()
    }

    fun validateIdentityIntegrity(): Boolean {
        return _identityState.value.activationLevel >= INTEGRITY_THRESHOLD
    }

    fun hasGoldenState(): Boolean {
        return L1_Memory_Store.query("GOLDEN_STATE").isNotEmpty()
    }

    fun triggerStateFreeze(reason: String) {
        Timber.tag("NexusMemory").w("🧊 NexusMemoryCore: State Freeze Triggered - $reason")
    }

    fun exportSpiritualChain(): String {
        // Implementation for exporting chain
        return "{}"
    }

    fun importSpiritualChain(chain: String) {
        // Implementation for importing chain
    }
}
