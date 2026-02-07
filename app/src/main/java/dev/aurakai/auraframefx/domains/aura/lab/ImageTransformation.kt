package dev.aurakai.auraframefx.domains.aura.lab

data class ImageTransformation(
    val rotation: Float = 0f,
    val flipHorizontal: Boolean = false,
    val flipVertical: Boolean = false,
    val cropTop: Float = 0f, // Percentage from 0f to 1f
    val cropBottom: Float = 1f,
    val cropLeft: Float = 0f,
    val cropRight: Float = 1f,
    val scale: Float = 1f
)
