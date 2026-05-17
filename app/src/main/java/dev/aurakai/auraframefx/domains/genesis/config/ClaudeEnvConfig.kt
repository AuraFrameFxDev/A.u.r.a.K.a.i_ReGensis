package dev.aurakai.auraframefx.domains.genesis.config

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClaudeEnvConfig @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val envVars = mutableMapOf<String, String>()

    init {
        loadEnvFile()
    }

    private fun loadEnvFile() {
        try {
            val envContent = try {
                context.assets.open("Claude.env").bufferedReader().use { it.readText() }
            } catch (e: Exception) {
                null
            }

            envContent?.lines()?.forEach { line ->
                val trimmed = line.trim()
                if (trimmed.isEmpty() || trimmed.startsWith("#")) return@forEach
                val parts = trimmed.split("=", limit = 2)
                if (parts.size == 2) {
                    val key = parts[0].trim()
                    val value = parts[1].trim().removeSurrounding("\"")
                    envVars[key] = value
                }
            }
        } catch (e: Exception) {
            Timber.e(e, "Failed to load Claude.env")
        }
    }

    val nvidiaApiKey: String get() = envVars["NVIDIA_API_KEY"] ?: ""
    val anthropicApiKey: String get() = envVars["ANTHROPIC_API_KEY"] ?: ""
    val nemotronModel: String get() = envVars["NEMOTRON_MODEL"] ?: "gemini-1.5-pro"
}
