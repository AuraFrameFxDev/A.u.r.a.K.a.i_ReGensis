package dev.aurakai.auraframefx.core

import dev.aurakai.auraframefx.core.ui.OverlayShape
import dev.aurakai.auraframefx.core.ui.theme.model.OverlayTheme
import dev.aurakai.auraframefx.core.ui.OverlayElement
import dev.aurakai.auraframefx.core.animations.OverlayAnimation
import dev.aurakai.auraframefx.core.animations.OverlayTransition

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
