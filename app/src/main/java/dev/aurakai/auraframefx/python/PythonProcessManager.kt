package dev.aurakai.auraframefx.python

import android.content.Context
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PythonProcessManager @Inject constructor(
    private val context: Context
) {
    private val backendUrl = "http://localhost:8080"

    suspend fun startGenesisBackend(): Boolean = false
    suspend fun shutdown() {}
    suspend fun sendRequest(request: String): String = ""

    fun isBackendRunning(): Boolean {
        // TODO: Implement actual backend status check
        return false
    }

    fun getBackendUrl(): String {
        return backendUrl
    }
}
