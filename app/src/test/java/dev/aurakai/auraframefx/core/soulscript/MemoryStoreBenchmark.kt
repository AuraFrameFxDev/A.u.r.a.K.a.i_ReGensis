package dev.aurakai.auraframefx.core.soulscript

import dev.aurakai.auraframefx.api.client.models.data.room.L1_Memory_Store
import org.junit.jupiter.api.Test
import kotlin.system.measureNanoTime

class MemoryStoreBenchmark {

    /**
     * Populates two in-memory stores and benchmarks three query patterns against each.
     *
     * Executes population of sample key/value entries, then measures and prints the average query latency (in microseconds) for exact, prefix, and wildcard patterns against both stores.
     */
    @Test
    fun runBenchmarks() {
        val entryCount = 10000
        println("Populating stores with $entryCount entries...")

        L1_Memory_Store.clear()

        for (i in 0 until entryCount) {
            val key = "key_$i"
            val value = "value_$i"
            L1_Memory_Store.store(key, value)
            NexusMemoryCore.commit(key, value)
        }

        println("\n--- L1_Memory_Store Benchmarks ---")
        benchmarkQuery("Exact Match", "key_5000") { L1_Memory_Store.query(it) }
        benchmarkQuery("Prefix Match", "key_5*") { L1_Memory_Store.query(it) }
        benchmarkQuery("Wildcard Match", "*5000") { L1_Memory_Store.query(it) }

        println("\n--- NexusMemoryCore Benchmarks ---")
        benchmarkQuery("Exact Match", "key_5000") { NexusMemoryCore.query(it) }
        benchmarkQuery("Prefix Match", "key_5*") { NexusMemoryCore.query(it) }
        benchmarkQuery("Wildcard Match", "*5000") { NexusMemoryCore.query(it) }
    }

    /**
     * Measures and prints the average execution time of a query pattern for a provided query function.
     *
     * Runs a short warmup, then executes the query function a fixed number of iterations and prints
     * the average duration in microseconds using the given label and pattern.
     *
     * @param label Human-readable label used when printing the result.
     * @param pattern Query pattern passed to the query function.
     * @param queryBlock Function that performs the query for the given pattern and returns results.
     */
    private fun benchmarkQuery(label: String, pattern: String, queryBlock: (String) -> List<Any>) {
        // Warmup
        repeat(10) { queryBlock(pattern) }

        val iterations = 100
        val totalTime = measureNanoTime {
            repeat(iterations) {
                queryBlock(pattern)
            }
        }

        val avgTimeUs = totalTime / iterations / 1000.0
        println("$label ('$pattern'): Avg ${"%.2f".format(avgTimeUs)} us")
    }
}
