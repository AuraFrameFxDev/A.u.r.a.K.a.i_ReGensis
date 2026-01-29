package dev.aurakai.auraframefx.ui.navigation

import dev.aurakai.auraframefx.ui.theme.SovereignTeal
import androidx.compose.ui.graphics.Color

data class SovereignRoute(
    val id: String,
    val title: String,
    val highFiPath: String,
    val pixelArtPath: String,
    val description: String = "", // Added description for Master Manifest compliance
    val color: Color = SovereignTeal
)

object SovereignRouter {
    // Default path, but mutable for testing/configuration
    var basePath: String = "file:///sdcard/Pictures/Screenshots/"

    /**
     * Builds the predefined list of SovereignRoute entries used by the router.
     *
     * @return The list of routes in display order. Each entry contains id, title, description, and image paths.
     */
    private fun getRoutes(): List<SovereignRoute> = listOf(
        SovereignRoute(
            id = "01",
            title = "GENESIS CORE",
            description = "Nemotron-3-Nano Reasoning / Ethical Governor",
            highFiPath = "${basePath}brain.png",
            pixelArtPath = "${basePath}IMG_20260128_142126.png"
        ),
        SovereignRoute(
            id = "02",
            title = "TRINITY SYSTEM",
            description = "Agent Fusion State / Shared Memory",
            highFiPath = "${basePath}IMG_20260128_141115.png",
            pixelArtPath = "${basePath}IMG_20260128_142126.png"
        ),
        SovereignRoute(
            id = "03",
            title = "AURA'S LAB",
            description = "Chroma Core HCT / Blade Sharpness Physics",
            highFiPath = "${basePath}IMG_20260128_140725.png",
            pixelArtPath = "${basePath}IMG_20260128_142213.png"
        ),
        SovereignRoute(
            id = "04",
            title = "AGENT NEXUS",
            description = "Human-AI Handshake / Google ADK",
            highFiPath = "${basePath}IMG_20260128_141704.png",
            pixelArtPath = "${basePath}IMG_20260128_142302.png"
        ),
        SovereignRoute(
            id = "05",
            title = "SENTINEL FORTRESS",
            description = "Thermal Metabolism / Kernel Shield",
            highFiPath = "${basePath}IMG_20260128_140949.png",
            pixelArtPath = "${basePath}IMG_20260128_142022.png"
        ),
        SovereignRoute(
            id = "06",
            title = "FIGMA BRIDGE",
            description = "SVG-to-Compose / Layer Design Sync",
            highFiPath = "${basePath}IMG_20260128_141018.png",
            pixelArtPath = "${basePath}IMG_20260128_142213.png"
        ),
        SovereignRoute(
            id = "07",
            title = "SECURE NODE", // Mapped to Vault per manifest
            description = "YukiHookAPI / Zero-Knowledge Encryption",
            highFiPath = "${basePath}IMG_20260128_141219.png",
            pixelArtPath = "${basePath}IMG_20260128_142022.png"
        ),
        SovereignRoute(
            id = "08",
            title = "NEXUS SYSTEM", // Mapped to Logic per manifest
            description = "Agent Swarm Event Bus / Priority Queue",
            highFiPath = "${basePath}IMG_20260128_140816.png",
            pixelArtPath = "${basePath}IMG_20260128_142126.png"
        ),
        SovereignRoute(
            id = "09",
            title = "MEMORY CORE",
            description = "6-Layer Spiritual Chain / Identity Persistence",
            highFiPath = "${basePath}IMG_20260128_140905.png",
            pixelArtPath = "${basePath}IMG_20260128_142126.png"
        ),
        SovereignRoute(
            id = "10",
            title = "ORACLE DRIVE",
            description = "Native C++ Bridge / Partition R-W Access",
            highFiPath = "${basePath}IMG_20260128_141519.png",
            pixelArtPath = "${basePath}IMG_20260128_141949.png"
        ),
        SovereignRoute(
            id = "11",
            title = "DATA VEIN", // Mapped to Pulse per manifest
            description = "12-Channel Telemetry / Prometheus Glow",
            highFiPath = "${basePath}IMG_20260128_141756.png",
            pixelArtPath = "${basePath}IMG_20260128_142126.png"
        )
    )

    /**
     * Selects the SovereignRoute for a given page index.
     *
     * @param page The zero-based index of the desired route.
     * @return The route at the specified index; the first route if the index is out of bounds.
     */
    fun fromPage(page: Int): SovereignRoute {
        val routes = getRoutes()
        return routes.getOrElse(page) { routes.first() }
    }

    /**
     * Finds a route by its identifier.
     *
     * @param id The route identifier to search for.
     * @return The matching `SovereignRoute` if found, `null` otherwise.
     */
    fun getById(id: String): SovereignRoute? {
        return getRoutes().find { it.id == id }
    }

    /**
 * Gets the total number of SovereignRoute entries currently available.
 *
 * @return The total number of available routes.
 */
fun getCount(): Int = getRoutes().size
}