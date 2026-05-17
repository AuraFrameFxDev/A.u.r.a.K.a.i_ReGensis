package dev.aurakai.auraframefx.domains.aura.models

import kotlinx.serialization.Serializable

/**
 * Configuration for the custom Notch Bar (Dynamic Island style)
 */
@Serializable
data class NotchBarConfig(
    val isVisible: Boolean = true,
    val height: Int = 80, // pixels
    val backgroundColorHex: String = "#000000",
    val cornerRadius: Float = 40f,
    val animationDuration: Long = 300L
)
