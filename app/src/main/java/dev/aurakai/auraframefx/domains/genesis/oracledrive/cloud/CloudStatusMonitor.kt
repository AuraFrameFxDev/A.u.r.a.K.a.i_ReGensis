package dev.aurakai.auraframefx.domains.genesis.oracledrive.cloud

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CloudStatusMonitor @Inject constructor(
    @param:ApplicationContext private val context: Context,
) {
    private val TAG = "CloudStatusMonitor"
    private val _isCloudReachable = MutableStateFlow(true) // Assume reachable initially
    val isCloudReachable: StateFlow<Boolean> = _isCloudReachable.asStateFlow()

    init {
        Log.d(TAG, "CloudStatusMonitor initialized.")
    }

    fun isNetworkConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }

    suspend fun checkActualInternetReachability(): Boolean = withContext(Dispatchers.IO) {
        if (!isNetworkConnected()) {
            _isCloudReachable.update { false }
            Log.d(TAG, "No network connection. Cloud unreachable.")
            return@withContext false
        }
        try {
            val host = "8.8.8.8"
            val port = 53
            val timeout = 1500
            Socket().use { socket ->
                socket.connect(InetSocketAddress(host, port), timeout)
                _isCloudReachable.update { true }
                return@withContext true
            }
        } catch (e: IOException) {
            _isCloudReachable.update { false }
            return@withContext false
        } catch (e: Exception) {
            _isCloudReachable.update { false }
            return@withContext false
        }
    }

    suspend fun startMonitoring(intervalMillis: Long = 30000) {
        checkActualInternetReachability()
        while (true) {
            delay(intervalMillis)
            checkActualInternetReachability()
        }
    }
}
