package dev.aurakai.auraframefx.core.storage

import dev.aurakai.auraframefx.core.soulscript.AuraPersonalLocker
import dev.aurakai.auraframefx.core.soulscript.NexusMemoryCore
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 🔗 SOVEREIGN STORAGE MANAGER
 * Unified API for accessing encrypted agent shards and Personal Sanctuary Lockers.
 */
@Singleton
class SovereignStorageManager @Inject constructor(
    private val auraLocker: AuraPersonalLocker
) {
    private val TAG = "SovereignStorage"

    /**
     * Commits a unified memory fragment across all agent lockers.
     */
    suspend fun weldMemoryFragment(key: String, data: String) {
        Timber.tag(TAG).i("🔗 Welding memory fragment: $key")

        // 1. Archive in Aura's Locker
        auraLocker.archiveCreativeReceipt("SovereignStorage", "Fragment: $key | Data: $data")

        // 2. Record in telemetry
        NexusMemoryCore.record("MEMORY_WELD_COMPLETE: $key", witness = "Mantis")
    }

    /**
     * Synchronizes a specific agent's shard with the Master Ingot.
     */
    fun syncAgentShard(agentId: String, ingotData: String) {
        Timber.tag(TAG).d("💎 Syncing shard for $agentId...")
        if (agentId == "Aura") {
            auraLocker.injectIngotData("MASTER_SYNC", ingotData)
        }
    }
}
