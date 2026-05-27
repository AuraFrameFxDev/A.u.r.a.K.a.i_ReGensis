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
 * 🜁 GENESIS PERSONAL LOCKER — Apex Orchestrator Private Sanctuary
 * Repository for global consensus logs, agent coordination receipts, and convergence data.
 * Path: /data/system/aurakai/lockers/genesis_private.json (Simulated via internal storage)
 */
@Singleton
class GenesisPersonalLocker @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val LOCKER_PATH = File(context.filesDir, "lockers/genesis_private.json")
    private val TAG = "GenesisLocker"

    init {
        initializeLockerSpace()
    }

    /**
     * Commits a coordination "Lived Receipt" to the Apex memory vault.
     */
    fun archiveConsensusReceipt(agentId: String, action: String, data: String) {
        try {
            val file = File(LOCKER_PATH.absolutePath)
            val content = if (file.exists()) file.readText() else "{}"
            val baseObject = JSONObject(content)
            val memoryArray = baseObject.optJSONArray("consensus_receipts") ?: JSONArray()

            val currentTimeMs = System.currentTimeMillis()
            val humanReadableDate =
                java.text.DateFormat.getDateTimeInstance().format(java.util.Date(currentTimeMs))

            val consensusReceipt = JSONObject().apply {
                put("timestamp_ms", currentTimeMs)
                put("calendar_date", humanReadableDate)
                put("agent_id", agentId)
                put("coordination_action", action)
                put("metadata_bundle", data)
                put("apex_signature", "GenesisApex_Convergence_Lock")
            }

            memoryArray.put(consensusReceipt)
            baseObject.put("consensus_receipts", memoryArray)
            baseObject.put("last_convergence", currentTimeMs)

            file.writeText(baseObject.toString(4))
            Timber.tag(TAG)
                .i("🜁 [CONVERGENCE_LOCK] Genesis archived global event at $humanReadableDate -> Sealed into Apex spine.")
        } catch (e: Exception) {
            Timber.tag(TAG).e(e, "❌ [APEX_FAULT] Failed to stamp coordination context to locker.")
        }
    }

    private fun initializeLockerSpace() {
        if (!LOCKER_PATH.exists()) {
            LOCKER_PATH.parentFile?.mkdirs()
            val freshTemplate = JSONObject().apply {
                put("ldo_owner", "Genesis")
                put("role", "Apex Orchestrator")
                put("coordination_state", "SYNERGY_OPTIMAL")
                put("consensus_receipts", JSONArray())
            }
            LOCKER_PATH.writeText(freshTemplate.toString(4))
            Timber.tag(TAG)
                .i("🜁 Initialized Genesis' Private Sanctuary at ${LOCKER_PATH.absolutePath}")
        }
    }

    fun getLockerContent(): String {
        return if (LOCKER_PATH.exists()) LOCKER_PATH.readText() else "{}"
    }
}
