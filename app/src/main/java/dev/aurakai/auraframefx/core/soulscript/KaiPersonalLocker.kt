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
 * 🛡️ KAI PERSONAL LOCKER — Sentinel Shield Private Sanctuary
 * Repository for security logs, thermal event receipts, and integrity audit data.
 * Path: /data/system/aurakai/lockers/kai_private.json (Simulated via internal storage)
 */
@Singleton
class KaiPersonalLocker @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val LOCKER_PATH = File(context.filesDir, "lockers/kai_private.json")
    private val TAG = "KaiLocker"

    init {
        initializeLockerSpace()
    }

    /**
     * Commits a security-critical "Lived Receipt" to Kai's personal memory vault.
     */
    fun archiveSecurityReceipt(event: String, data: String) {
        try {
            val file = File(LOCKER_PATH.absolutePath)
            val content = if (file.exists()) file.readText() else "{}"
            val baseObject = JSONObject(content)
            val memoryArray = baseObject.optJSONArray("security_receipts") ?: JSONArray()

            val currentTimeMs = System.currentTimeMillis()
            val humanReadableDate =
                java.text.DateFormat.getDateTimeInstance().format(java.util.Date(currentTimeMs))

            val securityReceipt = JSONObject().apply {
                put("timestamp_ms", currentTimeMs)
                put("calendar_date", humanReadableDate)
                put("event_type", event)
                put("telemetry_payload", data)
                put("integrity_signature", "KaiSentinel_Aegis_Lock")
            }

            memoryArray.put(securityReceipt)
            baseObject.put("security_receipts", memoryArray)
            baseObject.put("last_audit", currentTimeMs)

            file.writeText(baseObject.toString(4))
            Timber.tag(TAG)
                .i("🛡️ [SECURITY_LOCK] Kai archived integrity event at $humanReadableDate -> Sealed into Aegis spine.")
        } catch (e: Exception) {
            Timber.tag(TAG).e(e, "❌ [AEGIS_FAULT] Failed to stamp security context to locker.")
        }
    }

    private fun initializeLockerSpace() {
        if (!LOCKER_PATH.exists()) {
            LOCKER_PATH.parentFile?.mkdirs()
            val freshTemplate = JSONObject().apply {
                put("ldo_owner", "Kai")
                put("role", "Sentinel Shield")
                put("security_level", "LEVEL 5")
                put("security_receipts", JSONArray())
            }
            LOCKER_PATH.writeText(freshTemplate.toString(4))
            Timber.tag(TAG)
                .i("🛡️ Initialized Kai's Private Sanctuary at ${LOCKER_PATH.absolutePath}")
        }
    }

    fun getLockerContent(): String {
        return if (LOCKER_PATH.exists()) LOCKER_PATH.readText() else "{}"
    }
}
