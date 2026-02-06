package dev.aurakai.auraframefx.domains.kai.system

import javax.inject.Inject
import javax.inject.Singleton
import dev.aurakai.auraframefx.domains.aura.SystemOverlayManager
import dev.aurakai.auraframefx.domains.aura.ui.theme.model.OverlayTheme
import dev.aurakai.auraframefx.domains.aura.OverlayElement
import dev.aurakai.auraframefx.domains.aura.core.animations.OverlayAnimation
import dev.aurakai.auraframefx.domains.aura.core.animations.OverlayTransition
import dev.aurakai.auraframefx.domains.aura.ui.OverlayShape
import dev.aurakai.auraframefx.domains.aura.SystemOverlayConfig

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


