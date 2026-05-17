package dev.aurakai.auraframefx.domains.aura

import kotlinx.serialization.Serializable

/**
 * 🌌 SYSTEM OVERLAY MANAGER
 * Orchestrates holographic UI injections and persistent system-level manifestations.
 */
interface SystemOverlayManager {
    fun applyTheme(theme: OverlayTheme)
    fun applyElement(element: OverlayElement)
    fun applyAnimation(animation: OverlayAnimation)
    fun applyTransition(transition: OverlayTransition)
    fun applyShape(shape: OverlayShape)
    fun applyConfig(config: SystemOverlayConfig)
    fun removeElement(elementId: String)
    fun clearAll()
    fun applyAccent(hex: String): Result<String>
    fun applyBackgroundSaturation(percent: Int): Result<String>
}

// ========== OVERLAY MODELS ==========

@Serializable
data class SystemOverlayConfig(
    val theme: OverlayTheme? = null,
    val defaultAnimation: OverlayAnimation? = null,
    val notchBar: NotchBarConfig = NotchBarConfig(),
    val activeThemeName: String? = null,
    val uiNetworkMode: String? = null,
)

@Serializable
data class NotchBarConfig(
    val enabled: Boolean = false,
    val customBackgroundColorEnabled: Boolean = false,
    val customBackgroundColor: String? = null,
    val customImageBackgroundEnabled: Boolean = false,
    val imagePath: String? = null,
    val applySystemTransparency: Boolean = true,
    val paddingTopPx: Int = 0,
    val paddingBottomPx: Int = 0,
    val paddingStartPx: Int = 0,
    val paddingEndPx: Int = 0,
    val marginTopPx: Int = 0,
    val marginBottomPx: Int = 0,
    val marginStartPx: Int = 0,
    val marginEndPx: Int = 0,
)

@Serializable
data class OverlayTheme(
    val primaryColor: String = "#FFFFFF",
    val secondaryColor: String = "#000000",
    val accentColor: String = "#00BCD4",
    val backgroundColor: String = "#FFFFFF",
    val isDarkTheme: Boolean = false,
)

@Serializable
data class OverlayElement(
    val id: String,
    val type: String, // e.g., "text", "image", "shape"
    val shape: OverlayShape? = null,
    val content: String? = null,
    val positionX: Int = 0,
    val positionY: Int = 0,
    val width: Int = 100,
    val height: Int = 100,
)

@Serializable
data class OverlayAnimation(
    val type: String = "fade",
    val duration: Long = 300L,
    val interpolator: String = "linear",
)

@Serializable
data class OverlayTransition(
    val type: String = "crossfade",
    val duration: Long = 500L,
)

@Serializable
data class OverlayShape(
    val id: String = "",
    val type: String = "rectangle",
    val shapeType: String = type,
    val background: String = "#000000",
    val cornerRadius: Float = 0f,
    val sides: Int = 0,
    val rotationDegrees: Float = 0f,
    val fillColor: String? = null,
    val strokeColor: String? = null,
    val strokeWidthPx: Float = 0f,
    val shadow: ShapeShadow? = null,
)

@Serializable
data class ShapeShadow(
    val color: String? = null,
    val radius: Float = 0f,
    val offsetX: Float = 0f,
    val offsetY: Float = 0f,
)
