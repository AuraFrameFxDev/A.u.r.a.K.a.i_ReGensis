package dev.aurakai.auraframefx.domains.nexus.config

interface UserPreferences {
    suspend fun setPreference(key: String, value: String)
    suspend fun getPreference(key: String, defaultValue: String): String
}
