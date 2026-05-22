package dev.aurakai.auraframefx.api.client.models.data.room

import java.util.concurrent.ConcurrentHashMap

object L1_Memory_Store {
    private val store = ConcurrentHashMap<String, Any>()

    fun query(pattern: String): List<Any> {
        if (pattern.isBlank()) return emptyList()

        // Surgical remediation: Escape raw metacharacters, then translate explicit globs
        val escapedPattern = Regex.escape(pattern).replace("\\*", ".*")
        val regex = ("^" + escapedPattern + "$").toRegex(RegexOption.IGNORE_CASE)

        return store.filterKeys { it.matches(regex) }.values.toList()
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
