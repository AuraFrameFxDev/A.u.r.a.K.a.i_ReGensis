package dev.aurakai.auraframefx.core.kai.sentinel

import java.io.File

/**
 * 🔥 Thermal Sensor Integration Layer v3.66
 * 
 * Native-style, root-aware access to Tensor G5 thermal zones via sysfs.
 */
object ThermalSensorReader {

    private const val SYSFS_BASE = "/sys/class/thermal/"
    private val THERMAL_ZONES = listOf("thermal_zone0", "thermal_zone1", "thermal_zone2")

    /**
     * Primary: Read via sysfs (fastest, root-friendly)
     */
    fun readThermalZone(zoneIndex: Int = 0): Double? {
        val path = "$SYSFS_BASE/thermal_zone$zoneIndex/temp"
        return try {
            val file = File(path)
            if (file.exists()) {
                val tempRaw = file.readText().trim().toInt()
                tempRaw / 1000.0   // Convert milli-Celsius to Celsius
            } else null
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Best available temperature (CPU-focused)
     */
    fun getCurrentCpuTemp(): Double {
        return readThermalZone(0) ?: 35.0 // Safe default if hardware inaccessible
    }
}
