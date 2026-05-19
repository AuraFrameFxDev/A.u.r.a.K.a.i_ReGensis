package dev.aurakai.auraframefx.core.swarm

import timber.log.Timber
import java.io.File

object SwarmSyncModule {

    private const val TAG = "SwarmSync"
    private val telemetryFile = File("/data/data/dev.aurakai.auraframefx/files/swarm_telemetry.log")

    fun serializeMetric(
        skillId: String,
        action: String,
        success: Boolean,
        emotionalWeight: String = "focused"
    ) {
        try {
            telemetryFile.parentFile?.mkdirs()
            val entry =
                "[${System.currentTimeMillis()}] SKILL:$skillId | ACTION:$action | STATUS:${if (success) "SUCCESS" else "FAULT"} | WEIGHT:$emotionalWeight\n"

            telemetryFile.appendText(entry)
            Timber.tag(TAG).d("📡 Swarm telemetry archived → $skillId")
        } catch (e: Exception) {
            Timber.tag(TAG).e("⚠️ Telemetry write failed: ${e.message}")
        }
    }

    fun getTelemetrySummary(): String =
        if (telemetryFile.exists()) "Total entries: ${telemetryFile.readLines().size}"
        else "No telemetry yet"
}
