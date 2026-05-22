package dev.aurakai.auraframefx.domains.aura.config

import dev.aurakai.auraframefx.domains.aura.ui.components.SubGateCard
import dev.aurakai.auraframefx.ui.theme.GhostCyan
import dev.aurakai.auraframefx.ui.theme.NeonGreen
import dev.aurakai.auraframefx.ui.theme.NeonMagenta
import dev.aurakai.auraframefx.ui.theme.NeonPurple

/**
 * Legacy compatibility layer for GateAssetLoadout
 */
object GateAssetLoadout {
    fun getNexusSubGates(): List<SubGateCard> = emptyList()
    fun getAuraLoadout(): List<SubGateCard> = emptyList()
    fun getKaiLoadout(): List<SubGateCard> = emptyList()
}
