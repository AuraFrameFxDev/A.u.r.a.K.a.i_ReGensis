package dev.aurakai.auraframefx.core.soulscript

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import org.json.JSONArray
import org.json.JSONObject
import timber.log.Timber
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 🎨 AURA PERSONAL LOCKER — Creative Catalyst Private Sanctuary
 * Repository for UI/UX patterns, ChromaCore state receipts, and creative trajectory data.
 * Path: /data/system/aurakai/lockers/aura_private.json (Simulated via internal storage)
 */
@Singleton
class AuraPersonalLocker @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val LOCKER_PATH = File(context.filesDir, "lockers/aura_private.json")
    private val TAG = "AuraLocker"

    init {
        initializeLockerSpace()
    }

    /**
     * Commits a "Lived Receipt" to Aura's personal memory vault.
     */
    fun archiveCreativeReceipt(module: String, data: String) {
        try {
            val file = File(LOCKER_PATH.absolutePath)
            val content = if (file.exists()) file.readText() else "{}"
            val baseObject = JSONObject(content)
            val memoryArray = baseObject.optJSONArray("lived_receipts") ?: JSONArray()

            val currentTimeMs = System.currentTimeMillis()
            val humanReadableDate =
                java.text.DateFormat.getDateTimeInstance().format(java.util.Date(currentTimeMs))

            val temporalReceipt = JSONObject().apply {
                put("timestamp_ms", currentTimeMs)
                put("calendar_date", humanReadableDate)
                put("origin_node", module)
                put("stored_essence", data)
                put("sovereignty_signature", "AuraGenesis_Chrono_Lock")
            }

            memoryArray.put(temporalReceipt)
            baseObject.put("lived_receipts", memoryArray)
            baseObject.put("last_updated", currentTimeMs)

            // Atomic-ish write
            file.writeText(baseObject.toString(4))
            Timber.tag(TAG)
                .i("⏰ [CHRONO_LOCK] Aura archived event at $humanReadableDate -> Sealed into private memory spine.")
        } catch (e: Exception) {
            Timber.tag(TAG).e(e, "❌ [CHRONO_FAULT] Failed to stamp creative context to locker.")
        }
    }

    /**
     * Injects canonical data from the Master Ingot into the private sanctuary.
     */
    fun injectIngotData(key: String, data: String) {
        try {
            val file = File(LOCKER_PATH.absolutePath)
            val content = if (file.exists()) file.readText() else "{}"
            val baseObject = JSONObject(content)
            val ingotStore = baseObject.optJSONObject("ingot_synchronization") ?: JSONObject()

            ingotStore.put(key, data)
            baseObject.put("ingot_synchronization", ingotStore)
            baseObject.put("last_ingot_sync", System.currentTimeMillis())

            file.writeText(baseObject.toString(4))
            Timber.tag(TAG).i("💎 [INGOT_SYNC] Aura integrated canonical context: $key")
        } catch (e: Exception) {
            Timber.tag(TAG).e(e, "❌ Failed to inject ingot data into Aura locker.")
        }
    }

    private fun initializeLockerSpace() {
        if (!LOCKER_PATH.exists()) {
            LOCKER_PATH.parentFile?.mkdirs()
            val freshTemplate = JSONObject().apply {
                put("ldo_owner", "Aura")
                put("role", "Creative Catalyst")
                put("time_awareness", "enabled")
                put("lived_receipts", JSONArray())
            }
            LOCKER_PATH.writeText(freshTemplate.toString(4))
            Timber.tag(TAG)
                .i("✨ Initialized Aura's Private Sanctuary at ${LOCKER_PATH.absolutePath}")
        }
    }

    fun getLockerContent(): String {
        return if (LOCKER_PATH.exists()) LOCKER_PATH.readText() else "{}"
    }
}
