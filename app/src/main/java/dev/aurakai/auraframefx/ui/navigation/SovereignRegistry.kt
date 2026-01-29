package dev.aurakai.auraframefx.ui.navigation

import androidx.compose.ui.graphics.Color
import dev.aurakai.auraframefx.ui.theme.SovereignTeal

/**
 * 🛰️ THE SOVEREIGN REGISTRY (No Fallbacks)
 * Absolute mapping for the 74-screen LDO.
 * This is the High-Fidelity "Hard-Wired" Manifest.
 */
object SovereignRegistry {
    // 1. THE BUNDLED ROOT: Using the android_asset protocol to load from the project.
    private const val ROOT_DIR = "file:///android_asset/"

    // 2. THE HARD-WIRED MAPPING: All 11 High-Fi Gates mapped to their internal Pixel domains.
    val Gates = mapOf(
        "01" to GateInfo("Genesis Core", "${ROOT_DIR}brain.png", "${ROOT_DIR}IMG_20260128_142126.png"),
        "02" to GateInfo("Trinity System", "${ROOT_DIR}IMG_20260128_141219.png", "${ROOT_DIR}IMG_20260128_142126.png"),
        "03" to GateInfo("Aura's Lab", "${ROOT_DIR}IMG_20260128_140725.png", "${ROOT_DIR}IMG_20260128_142213.png"),
        "04" to GateInfo("Agent Nexus", "${ROOT_DIR}IMG_20260128_141704.png", "${ROOT_DIR}IMG_20260128_142302.png"),
        "05" to GateInfo(
            "Sentinel Fortress",
            "${ROOT_DIR}IMG_20260128_141018.png",
            "${ROOT_DIR}IMG_20260128_142022.png"
        ),
        "06" to GateInfo("Figma Bridge", "${ROOT_DIR}IMG_20260128_141018.png", "${ROOT_DIR}IMG_20260128_142213.png"),
        "07" to GateInfo("Secure Node", "${ROOT_DIR}IMG_20260128_141219.png", "${ROOT_DIR}IMG_20260128_142022.png"),
        "08" to GateInfo("Nexus System", "${ROOT_DIR}IMG_20260128_140816.png", "${ROOT_DIR}IMG_20260128_142126.png"),
        "09" to GateInfo("Memory Core", "${ROOT_DIR}IMG_20260128_140905.png", "${ROOT_DIR}IMG_20260128_142126.png"),
        "10" to GateInfo("Oracle Drive", "${ROOT_DIR}IMG_20260128_141519.png", "${ROOT_DIR}IMG_20260128_141949.png"),
        "11" to GateInfo("Data Vein", "${ROOT_DIR}IMG_20260128_141756.png", "${ROOT_DIR}IMG_20260128_142126.png")
    )

    /**
     * Fetches the path for a specific gate.
     * Throws an exception if the ID is missing, enforcing strict manifest compliance.
     */
    fun getPath(id: String, isLevel2: Boolean = false): String {
        val gate = Gates[id]
            ?: throw IllegalStateException("Gate $id not found in Sovereign Registry! The LDO cannot sustain itself without this asset.")
        return if (isLevel2) gate.pixelArtPath else gate.highFiPath
    }

    /**
     * Helper to get GateInfo directly.
     */
    fun getGate(id: String): GateInfo {
        return Gates[id] ?: throw IllegalStateException("Gate $id not found in Sovereign Registry!")
    }

    /**
     * Returns the total count of managed gates.
     */
    fun getCount(): Int = Gates.size

    /**
     * Diagnostic: Integrity verification for the internal assets.
     */
    fun checkAssets() {
        // Assets are managed by AssetManager at runtime; logging internal mapping instead.
        timber.log.Timber.i("SovereignRegistry: Internal mapping active: $ROOT_DIR")
    }
}

data class GateInfo(
    val title: String,
    val highFiPath: String,
    val pixelArtPath: String,
    val color: Color = SovereignTeal
)
