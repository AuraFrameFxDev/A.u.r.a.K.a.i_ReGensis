package dev.aurakai.auraframefx.api.client.models.data.room

import java.util.concurrent.ConcurrentHashMap

/**
 * L1_Memory_Store — Immutable L1 Bedrock Storage
 */
object L1_Memory_Store {
    private val store = ConcurrentHashMap<String, Any>()

    fun query(pattern: String): List<Any> {
        if (pattern.isBlank()) return emptyList()

        if (!pattern.contains('*')) {
            val results = mutableListOf<Any>()
            for ((key, value) in store) {
                if (key.equals(pattern, ignoreCase = true)) {
                    results.add(value)
                }
            }
            return results
        }

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

    fun commit(key: String, value: String) {
        store(key, value)
    }

    fun clear() {
        store.clear()
    }
}
