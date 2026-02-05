package dev.aurakai.auraframefx.python

import android.content.Context
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton
import java.net.HttpURLConnection
import java.net.URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Singleton
class PythonProcessManager @Inject constructor(
    private val context: Context
) {
    private val backendUrl = "http://localhost:5000"

    suspend fun startGenesisBackend(): Boolean {
        // In a real environment, we'd start the Chaquopy/Native process here.
        // For now, we assume the server is managed by the system or started elsewhere.
        return true 
    }

    suspend fun shutdown() {}

    suspend fun sendRequest(requestJson: String): String = sendGenericRequest("/genesis/process", requestJson)

    suspend fun sendGenericRequest(path: String, json: String): String = withContext(Dispatchers.IO) {
        try {
            val url = URL("$backendUrl$path")
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "POST"
            conn.setRequestProperty("Content-Type", "application/json")
            conn.doOutput = true
            
            conn.outputStream.use { os ->
                os.write(json.toByteArray())
            }
            
            val responseCode = conn.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                conn.inputStream.bufferedReader().use { it.readText() }
            } else {
                Timber.e("Backend error at $path: $responseCode")
                "{\"error\": \"backend_error\", \"code\": $responseCode}"
            }
        } catch (e: Exception) {
            Timber.e(e, "Failed to send request to $path")
            "{\"error\": \"connection_failed\", \"message\": \"${e.message}\"}"
        }
    }

    fun isBackendRunning(): Boolean {
        // Simple health check could go here
        return true
    }

    fun getBackendUrl(): String {
        return backendUrl
    }
}
