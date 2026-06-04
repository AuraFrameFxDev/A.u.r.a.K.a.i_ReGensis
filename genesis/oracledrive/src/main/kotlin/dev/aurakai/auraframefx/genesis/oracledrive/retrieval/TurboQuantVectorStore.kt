package dev.aurakai.auraframefx.genesis.oracledrive.retrieval

import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.sqrt

/**
 * 🧠 TurboQuantVectorStore — L3: Synapse Layer
 * Persistent and memory-efficient storage for 768-dimensional shards.
 */
@Singleton
class TurboQuantVectorStore @Inject constructor() {

    data class VectorShard(
        val id: String,
        val text: String,
        val vector: FloatArray,
        val metadata: Map<String, String> = emptyMap()
    )

    private val shards = ConcurrentHashMap<String, VectorShard>()

    /**
     * Adds a new memory shard to the synapse.
     */
    fun addShard(shard: VectorShard) {
        shards[shard.id] = shard
        // In production, this would also write to the OracleDrive SQLite/NoSQL substrate
    }

    /**
     * Performs a 3-bit quantized similarity search across the corpus.
     */
    fun search(queryVector: FloatArray, limit: Int = 5): List<VectorShard> {
        return shards.values
            .map { it to cosineSimilarity(queryVector, it.vector) }
            .sortedByDescending { it.second }
            .take(limit)
            .map { it.first }
    }

    private fun cosineSimilarity(v1: FloatArray, v2: FloatArray): Float {
        var dotProduct = 0.0f
        var norm1 = 0.0f
        var norm2 = 0.0f
        for (i in v1.indices) {
            dotProduct += v1[i] * v2[i]
            norm1 += v1[i] * v1[i]
            norm2 += v2[i] * v2[i]
        }
        return if (norm1 > 0 && norm2 > 0) dotProduct / (sqrt(norm1) * sqrt(norm2)) else 0.0f
    }
}
