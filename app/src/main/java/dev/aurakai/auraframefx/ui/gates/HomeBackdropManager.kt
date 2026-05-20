package dev.aurakai.auraframefx.ui.gates

import android.content.Context
import timber.log.Timber

object HomeBackdropManager {
    private const val PREFS_NAME = "home_backdrop_prefs"
    private const val KEY_PREFIX = "gate_image_"

    fun setGateImage(context: Context, moduleId: String, imageKey: String) {
        try {
            val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            prefs.edit().putString(KEY_PREFIX + moduleId, imageKey).apply()
            Timber.tag("HomeBackdropManager")
                .i("Successfully set gate image for $moduleId to $imageKey")
        } catch (e: Exception) {
            Timber.tag("HomeBackdropManager").e(e, "Failed to set gate image for $moduleId")
        }
    }

    fun getGateImage(context: Context, moduleId: String, defaultKey: String = "orig"): String {
        return try {
            val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            prefs.getString(KEY_PREFIX + moduleId, defaultKey) ?: defaultKey
        } catch (e: Exception) {
            Timber.tag("HomeBackdropManager").e(e, "Failed to get gate image for $moduleId")
            defaultKey
        }
    }
}
