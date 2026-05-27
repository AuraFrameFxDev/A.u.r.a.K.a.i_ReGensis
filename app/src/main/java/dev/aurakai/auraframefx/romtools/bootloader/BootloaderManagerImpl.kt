package dev.aurakai.auraframefx.romtools.bootloader

import android.content.Context
import android.os.BatteryManager
import android.os.Build
import android.provider.Settings
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.aurakai.auraframefx.core.romtools.bootloader.BootloaderManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BootloaderManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : BootloaderManager {

    override fun checkBootloaderAccess(): Boolean {
        // Safe check for fastboot access (read-only)
        return true // Placeholder, but safe for read-only diagnostics
    }

    override fun isBootloaderUnlocked(): Boolean {
        val flashLocked = getSystemProperty("ro.boot.flash.locked", "1")
        return flashLocked == "0"
    }

    override suspend fun unlockBootloader(): Result<Unit> {
        // DIRECTIVE: No direct execution of destructive operations.
        return Result.failure(
            UnsupportedOperationException(
                "Kai Sentinel Directive: Direct bootloader unlocking is prohibited. " +
                        "Use the 'Escalation Proposal' workflow for human-guided execution."
            )
        )
    }

    override fun collectPreflightSignals(): BootloaderManager.PreflightSignals {
        return BootloaderManager.PreflightSignals(
            isBootloaderUnlocked = isBootloaderUnlocked(),
            oemUnlockSupported = isOemUnlockSupported(),
            verifiedBootState = getSystemProperty("ro.boot.verifiedbootstate", "unknown"),
            batteryLevel = getBatteryLevel(),
            developerOptionsEnabled = isDevSettingsEnabled(),
            oemUnlockAllowedUser = isOemUnlockAllowedByUser(),
            deviceFingerprint = Build.FINGERPRINT
        )
    }

    // --- Signal Collectors (READ ONLY) ---

    private fun isOemUnlockSupported(): Boolean {
        return getSystemProperty("ro.oem_unlock_supported", "0") == "1"
    }

    private fun isDevSettingsEnabled(): Boolean {
        return Settings.Global.getInt(
            context.contentResolver,
            Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0
        ) == 1
    }

    private fun isOemUnlockAllowedByUser(): Boolean {
        val allowed = Settings.Global.getInt(context.contentResolver, "oem_unlock_allowed", -1)
        if (allowed != -1) return allowed == 1

        // Fallback for some devices
        val enabled = Settings.Global.getInt(context.contentResolver, "oem_unlock_enabled", -1)
        if (enabled != -1) return enabled == 1

        return false
    }

    private fun getBatteryLevel(): Int {
        val batteryManager = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
        return batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
    }

    /**
     * Safe wrapper to read system properties via reflection.
     * Does NOT use 'setprop'.
     */
    private fun getSystemProperty(key: String, defaultValue: String): String {
        return try {
            val c = Class.forName("android.os.SystemProperties")
            val get = c.getMethod("get", String::class.java, String::class.java)
            get.invoke(c, key, defaultValue) as String
        } catch (e: Exception) {
            defaultValue
        }
    }
}
