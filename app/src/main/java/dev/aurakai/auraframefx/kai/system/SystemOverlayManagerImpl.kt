package dev.aurakai.auraframefx.kai.system

import javax.inject.Inject
import javax.inject.Singleton
import dev.aurakai.auraframefx.system.ui.SystemOverlayManager
import dev.aurakai.auraframefx.ui.theme.model.OverlayTheme
import dev.aurakai.auraframefx.system.overlay.model.OverlayElement
import dev.aurakai.auraframefx.aura.animations.OverlayAnimation
import dev.aurakai.auraframefx.aura.animations.OverlayTransition
import dev.aurakai.auraframefx.ui.OverlayShape
import dev.aurakai.auraframefx.system.overlay.model.SystemOverlayConfig

@Singleton
class SystemOverlayManagerImpl @Inject constructor() : SystemOverlayManager {
    override fun applyTheme(theme: OverlayTheme) { /* no-op */ }
    override fun applyElement(element: OverlayElement) { /* no-op */ }
    override fun applyAnimation(animation: OverlayAnimation) { /* no-op */ }
    override fun applyTransition(transition: OverlayTransition) { /* no-op */ }
    override fun applyShape(shape: OverlayShape) { /* no-op */ }
    override fun applyConfig(config: SystemOverlayConfig) { /* no-op */ }
    override fun removeElement(elementId: String) { /* no-op */ }
    override fun clearAll() { /* no-op */ }

    override fun applyAccent(hex: String): Result<String> = Result.success("no-op")
    override fun applyBackgroundSaturation(percent: Int): Result<String> = Result.success("no-op")
}
