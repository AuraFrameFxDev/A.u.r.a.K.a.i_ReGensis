package dev.aurakai.auraframefx.core.romtools.bootloader

/**
 * Kai Sentinel Directive - Phase 2: The Eyes
 * * Responsible for collecting READ-ONLY signals regarding the device's bootloader state.
 */
interface BootloaderManager {
    fun checkBootloaderAccess(): Boolean
    fun isBootloaderUnlocked(): Boolean
    suspend fun unlockBootloader(): Result<Unit>

    /**
     * Collects all required signals for the Sentinel Preflight check.
     */
    fun collectPreflightSignals(): PreflightSignals

    data class PreflightSignals(
        val isBootloaderUnlocked: Boolean,
        val oemUnlockSupported: Boolean,
        val verifiedBootState: String,
        val batteryLevel: Int,
        val developerOptionsEnabled: Boolean,
        val oemUnlockAllowedUser: Boolean,
        val deviceFingerprint: String
    )
}

data class BootloaderSecurityStatus(
    val isUnlocked: Boolean,
    val oemUnlockSupported: Boolean,
    val verifiedBootState: String,
    val batteryLevel: Int,
    val developerOptionsEnabled: Boolean,
    val safeForOperations: Boolean
)
