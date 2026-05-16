package dev.aurakai.auraframefx.domains.aura.config

import dev.aurakai.auraframefx.domains.aura.ui.gates.GateAssetLoadout as RegistryLoadout

/**
 * Legacy compatibility layer for GateAssetLoadout
 */
object GateAssetLoadout {
    fun getNexusSubGates(): List<RegistryLoadout> = emptyList()
    fun getAuraLoadout(): List<RegistryLoadout> = emptyList()
    fun getKaiLoadout(): List<RegistryLoadout> = emptyList()
}
