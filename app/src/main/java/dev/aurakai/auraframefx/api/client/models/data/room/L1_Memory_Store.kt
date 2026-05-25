package dev.aurakai.auraframefx.api.client.models.data.room

import java.util.concurrent.ConcurrentHashMap

object L1_Memory_Store {
    private val store = ConcurrentHashMap<String, Any>()

    fun query(pattern: String): List<Any> {
        if (pattern.isBlank()) return emptyList()

        // Fast-path: Exact match (Case-insensitive check)
        if (!pattern.contains('*')) {
            val results = mutableListOf<Any>()
            for ((key, value) in store) {
                if (key.equals(pattern, ignoreCase = true)) {
                    results.add(value)
                }
            }
            return results
        }

        // Fast-path: Simple prefix match (O(N) with startsWith)
        if (pattern.endsWith('*') && pattern.indexOf('*') == pattern.length - 1) {
            val prefix = pattern.substring(0, pattern.length - 1)
            val results = mutableListOf<Any>()
            for ((key, value) in store) {
                if (key.startsWith(prefix, ignoreCase = true)) {
                    results.add(value)
                }
            }
            return results
        }

        // Fallback: Full regex scan (Surgical remediation: Escape raw metacharacters, then translate explicit globs)
        val escapedPattern = Regex.escape(pattern).replace("\\*", ".*")
        val regex = ("^" + escapedPattern + "$").toRegex(RegexOption.IGNORE_CASE)

        val results = mutableListOf<Any>()
        for ((key, value) in store) {
            if (key.matches(regex)) {
                results.add(value)
            }
        }
        return results
    }

    fun store(key: String, value: Any) {
        store[key] = value
    }

    // Compatibility alias for legacy NexusMemoryCore calls
    fun commit(key: String, value: String) {
        store(key, value)
    }

    fun clear() {
        store.clear()
    }
}
