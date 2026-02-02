package dev.aurakai.auraframefx.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.ui.graphics.Color
import dev.aurakai.auraframefx.ui.theme.SovereignTeal

/**
 * 🗺️ SOVEREIGN ROUTER (LEGACY/DEPRECATED)
 *
 * ⚠️ WARNING: This router is deprecated and not used by ExodusHUD.
 * ExodusHUD now uses SovereignRegistry for the 5 main gates.
 *
 * This file contains 11 routes from an older navigation system.
 * Used only by the pixel_domain/{id} route in ReGenesisNavHost.
 *
 * TODO: Consider removing if pixel_domain routes are no longer needed,
 * or migrate to SovereignRegistry's structure.
 */

data class SovereignRoute(
    val id: String,
    val title: String,
    val highFiPath: String,
    val pixelArtPath: String,
    val color: Color = SovereignTeal
)

object SovereignRouter {
    @SuppressLint("SdCardPath")
    private const val BASE_PATH = "file:///sdcard/Pictures/Screenshots/"

    private val routes = listOf(
        SovereignRoute(
            id = "01",
            title = "GENESIS CORE",
            highFiPath = "${BASE_PATH}brain.png",
            pixelArtPath = "${BASE_PATH}IMG_20260128_142126.png"
        ),
        SovereignRoute(
            id = "02",
            title = "TRINITY SYSTEM",
            highFiPath = "${BASE_PATH}IMG_20260128_141115.png",
            pixelArtPath = "${BASE_PATH}IMG_20260128_142126.png"
        ),
        SovereignRoute(
            id = "03",
            title = "AURA'S LAB",
            highFiPath = "${BASE_PATH}IMG_20260128_140725.png",
            pixelArtPath = "${BASE_PATH}IMG_20260128_142213.png"
        ),
        SovereignRoute(
            id = "04",
            title = "AGENT NEXUS",
            highFiPath = "${BASE_PATH}IMG_20260128_141704.png",
            pixelArtPath = "${BASE_PATH}IMG_20260128_142302.png"
        ),
        SovereignRoute(
            id = "05",
            title = "SENTINEL FORTRESS",
            highFiPath = "${BASE_PATH}IMG_20260128_140949.png",
            pixelArtPath = "${BASE_PATH}IMG_20260128_142022.png"
        ),
        SovereignRoute(
            id = "06",
            title = "FIGMA BRIDGE",
            highFiPath = "${BASE_PATH}IMG_20260128_141018.png",
            pixelArtPath = "${BASE_PATH}IMG_20260128_142213.png"
        ),
        SovereignRoute(
            id = "07",
            title = "SECURE NODE",
            highFiPath = "${BASE_PATH}IMG_20260128_141219.png",
            pixelArtPath = "${BASE_PATH}IMG_20260128_142022.png"
        ),
        SovereignRoute(
            id = "08",
            title = "NEXUS SYSTEM",
            highFiPath = "${BASE_PATH}IMG_20260128_140816.png",
            pixelArtPath = "${BASE_PATH}IMG_20260128_142126.png"
        ),
        SovereignRoute(
            id = "09",
            title = "MEMORY CORE",
            highFiPath = "${BASE_PATH}IMG_20260128_140905.png",
            pixelArtPath = "${BASE_PATH}IMG_20260128_142126.png"
        ),
        SovereignRoute(
            id = "10",
            title = "ORACLE DRIVE",
            highFiPath = "${BASE_PATH}IMG_20260128_141519.png",
            pixelArtPath = "${BASE_PATH}IMG_20260128_141949.png"
        ),
        SovereignRoute(
            id = "11",
            title = "DATA VEIN",
            highFiPath = "${BASE_PATH}IMG_20260128_141756.png",
            pixelArtPath = "${BASE_PATH}IMG_20260128_142126.png"
        )
    )

    fun fromPage(page: Int): SovereignRoute {
        return routes.getOrElse(page) { routes.first() }
    }

    fun getById(id: String): SovereignRoute? {
        return routes.find { it.id == id }
    }

    fun getCount(): Int = routes.size
}
