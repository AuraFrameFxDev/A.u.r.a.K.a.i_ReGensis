package dev.aurakai.auraframefx.system

import android.content.Context
import android.os.IBinder
import android.util.Log
import rikka.shizuku.Shizuku
import rikka.shizuku.ShizukuProvider

object ShizukuManager {

    private const val TAG = "ShizukuManager"
    const val SHIZUKU_PERMISSION_REQUEST_CODE = 1001

    /**
     * Determine whether the Shizuku service is reachable.
     *
     * @return `true` if the Shizuku service is reachable and responsive, `false` otherwise.
     */
    fun isShizukuAvailable(): Boolean {
        return try {
            Shizuku.pingBinder()
            Log.d(TAG, "Shizuku.pingBinder() returned true: Shizuku service is available.")
            true
        } catch (e: Throwable) {
            Log.e(TAG, "Shizuku.pingBinder() failed: Shizuku service not available or error occurred. " + e.message)
            false
        }
    }

    /**
     * Request Shizuku permission from the user and deliver the result via the provided callback.
     *
     * @param context Context used to initiate the permission request.
     * @param callback Invoked with `true` if permission is granted, `false` otherwise.
     */
    fun requestShizukuPermission(context: Context, callback: (granted: Boolean) -> Unit) {
        if (Shizuku.checkSelfPermission() == ShizukuProvider.PERMISSION_GRANTED) {
            Log.i(TAG, "Shizuku permission already granted.")
            callback(true)
            return
        }

        Log.i(TAG, "Requesting Shizuku permission.")
        val listener = object : Shizuku.OnRequestPermissionListener {
            override fun onRequestPermission(requestCode: Int, grantResult: Int) {
                if (requestCode == SHIZUKU_PERMISSION_REQUEST_CODE) {
                    val granted = grantResult == ShizukuProvider.PERMISSION_GRANTED
                    Log.i(TAG, "Shizuku permission request result: granted=$granted")
                    callback(granted)
                    Shizuku.removeRequestPermissionListener(this)
                }
            }
        }
        Shizuku.addRequestPermissionListener(listener)
        Shizuku.requestPermission(SHIZUKU_PERMISSION_REQUEST_CODE)
    }

    /**
     * Adds a listener to be notified when the Shizuku binder dies.
     */
    fun addShizukuBinderDeathListener(binder: IBinder, listener: IBinder.DeathRecipient) {
        try {
            binder.linkToDeath(listener, 0)
            Log.d(TAG, "Shizuku binder death listener added.")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to add Shizuku binder death listener: " + e.message)
        }
    }

    /**
     * Removes a listener from the Shizuku binder death notifications.
     */
    fun removeShizukuBinderDeathListener(binder: IBinder, listener: IBinder.DeathRecipient) {
        try {
            binder.unlinkToDeath(listener, 0)
            Log.d(TAG, "Shizuku binder death listener removed.")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to remove Shizuku binder death listener: " + e.message)
        }
    }

    /**
     * Retrieves the Shizuku service binder.
     * @return The IBinder for the Shizuku service, or null if not available.
     */
    fun getShizukuServiceBinder(): IBinder? {
        return try {
            val binder = Shizuku.getService()
            if (binder != null) {
                Log.d(TAG, "Shizuku service binder obtained successfully.")
            } else {
                Log.w(TAG, "Shizuku.getService() returned null.")
            }
            binder
        } catch (e: Throwable) {
            Log.e(TAG, "Error getting Shizuku service binder: " + e.message)
            null
        }
    }

    // Example of a death recipient
    private val shizukuDeathRecipient = IBinder.DeathRecipient {
        Log.w(TAG, "Shizuku binder died. Re-establishing connection or cleaning up.")
        // Handle binder death: e.g., re-request permission, re-bind to service
    }

    // You might want to call initialize() or similar methods from your application's onCreate
    /**
     * Initializes Shizuku integration: verifies availability, requests permission if necessary,
     * and attaches a binder death listener to the Shizuku service when permission is granted.
     *
     * If Shizuku is not available, the function logs an error and returns without requesting permission.
     *
     * @param context Context used to request Shizuku permission.
     */
    fun initializeShizukuIntegration(context: Context) {
        if (isShizukuAvailable()) {
            requestShizukuPermission(context) { granted ->
                if (granted) {
                    Log.i(TAG, "Shizuku permission granted. Can now proceed with operations.")
                    getShizukuServiceBinder()?.let { binder ->
                        addShizukuBinderDeathListener(binder, shizukuDeathRecipient)
                    }
                } else {
                    Log.w(TAG, "Shizuku permission denied.")
                }
            }
        } else {
            Log.e(TAG, "Shizuku is not available. Please ensure Shizuku app is installed and running.")
        }
    }
}