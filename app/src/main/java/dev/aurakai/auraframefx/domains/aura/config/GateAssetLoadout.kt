package dev.aurakai.auraframefx.domains.aura.config

import dev.aurakai.auraframefx.domains.aura.ui.components.SubGateCard

/**
 * Legacy compatibility layer for GateAssetLoadout
 */
object GateAssetLoadout {
    fun getNexusSubGates(): List<SubGateCard> = emptyList()
    fun getAuraLoadout(): List<SubGateCard> = emptyList()
    fun getKaiLoadout(): List<SubGateCard> = emptyList()
}
