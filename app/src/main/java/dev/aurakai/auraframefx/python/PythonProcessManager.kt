package dev.aurakai.auraframefx.python

import android.content.Context
import dev.aurakai.auraframefx.core.PythonProcessManager as CorePythonProcessManager
import dev.aurakai.auraframefx.core.ProcessConfig
import dev.aurakai.auraframefx.core.BackendHealth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Adapter wrapper around CorePythonProcessManager to maintain API compatibility
 * This bridges the old API (startGenesisBackend, isBackendRunning) to the new core implementation
 */
@Singleton
class PythonProcessManager @Inject constructor(
    private val context: Context,
    private val coreManager: CorePythonProcessManager
) {
    private val backendUrl = "http://localhost:8080"

    /**
     * Start the Genesis Python backend
     * Uses the core PythonProcessManager with proper script path configuration
     */
    suspend fun startGenesisBackend(): Boolean = withContext(Dispatchers.IO) {
        try {
            // Get the backend directory path
            val backendDir = File(context.filesDir, "ai_backend")
            val scriptPath = File(backendDir, "genesis_connector.py").absolutePath
            
            // Configure the process
            val config = ProcessConfig(
                pythonPath = "python3", // Will try python3, fallback handled in core
                scriptPath = scriptPath,
                enableAutoRestart = true,
                maxRestartAttempts = 5,
                heartbeatIntervalMs = 30_000,
                requestTimeoutMs = 10_000
            )
            
            // Start using core manager
            coreManager.start(config)
            
            // Wait a moment for initialization
            kotlinx.coroutines.delay(2000)
            
            // Check if healthy
            coreManager.isHealthy()
        } catch (e: Exception) {
            android.util.Log.e("PythonProcessManager", "Failed to start backend", e)
            false
        }
    }

    /**
     * Shutdown the backend gracefully
     */
    suspend fun shutdown() = withContext(Dispatchers.IO) {
        try {
            coreManager.stop()
        } catch (e: Exception) {
            android.util.Log.e("PythonProcessManager", "Error during shutdown", e)
        }
    }

    /**
     * Send a request to the Python backend
     */
    suspend fun sendRequest(request: String): String? = withContext(Dispatchers.IO) {
        try {
            coreManager.sendRequest(request)
        } catch (e: Exception) {
            android.util.Log.e("PythonProcessManager", "Error sending request", e)
            null
        }
    }

    /**
     * Check if backend is running and healthy
     */
    fun isBackendRunning(): Boolean {
        return coreManager.isHealthy()
    }

    /**
     * Get the backend URL (for HTTP-based communication)
     */
    fun getBackendUrl(): String {
        return backendUrl
    }
}
