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
        val metadata: Map<String, String> = emptyMap(),
        val norm: Float = 0f
    )

    private val shards = ConcurrentHashMap<String, VectorShard>()

    /**
     * Adds a new memory shard to the synapse.
     */
    fun addShard(shard: VectorShard) {
        // ⚡ Bolt Optimization: Pre-calculate L2 norm to avoid redundant calculation during search
        var sumSquares = 0.0f
        val v = shard.vector
        for (i in v.indices) {
            sumSquares += v[i] * v[i]
        }
        val norm = kotlin.math.sqrt(sumSquares)

        shards[shard.id] = shard.copy(norm = norm)
        // In production, this would also write to the OracleDrive SQLite/NoSQL substrate
    }

    /**
     * Performs a 3-bit quantized similarity search across the corpus.
     */
    fun search(queryVector: FloatArray, limit: Int = 5): List<VectorShard> {
        if (shards.isEmpty() || limit <= 0) return emptyList()

        // ⚡ Bolt Optimization: Calculate query norm once to avoid O(N) redundant calculations
        var querySumSquares = 0.0f
        for (v in queryVector) {
            querySumSquares += v * v
        }
        val queryNorm = sqrt(querySumSquares)
        if (queryNorm <= 0f) return emptyList()

        // ⚡ Bolt Optimization: Use PriorityQueue (min-heap) to maintain top K results
        // Complexity: O(N log K) instead of O(N log N). Also avoids sorting the entire list.
        val topK = java.util.PriorityQueue<Pair<VectorShard, Float>>(limit) { a, b ->
            a.second.compareTo(b.second)
        }

        for (shard in shards.values) {
            val shardNorm = shard.norm
            if (shardNorm <= 0f) continue

            // ⚡ Bolt Optimization: Optimized dot product and pre-calculated shard norm
            val similarity = dotProduct(queryVector, shard.vector) / (queryNorm * shardNorm)

            if (topK.size < limit) {
                topK.add(shard to similarity)
            } else {
                val minSim = topK.peek()?.second ?: -1f
                if (similarity > minSim) {
                    topK.poll()
                    topK.add(shard to similarity)
                }
            }
        }

        // Convert topK to sorted list (descending similarity)
        val result = mutableListOf<VectorShard>()
        while (topK.isNotEmpty()) {
            topK.poll()?.let {
                result.add(it.first)
            }
        }
        return result.reversed()
    }

    private fun dotProduct(v1: FloatArray, v2: FloatArray): Float {
        var dot = 0.0f
        var i = 0
        val n = v1.size
        // ⚡ Bolt Optimization: Manual loop unrolling for performance
        while (i < n - 3) {
            dot += v1[i] * v2[i] + v1[i + 1] * v2[i + 1] + v1[i + 2] * v2[i + 2] + v1[i + 3] * v2[i + 3]
            i += 4
        }
        while (i < n) {
            dot += v1[i] * v2[i]
            i++
        }
        return dot
    }
}
