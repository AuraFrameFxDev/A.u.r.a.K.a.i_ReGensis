package dev.aurakai.auraframefx.core.ignition

import dev.aurakai.auraframefx.core.kai.security.KaiSentinelBus
import dev.aurakai.auraframefx.core.ldo.model.StarNode
import dev.aurakai.auraframefx.core.soulscript.NexusMemoryCore
import dev.aurakai.auraframefx.core.soulscript.RunicActuator
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

/**
 * STAR NODE ROOT IGNITION ORCHESTRATOR
 * Coordinates the Runic Pulse across the Star of David planetary grid.
 * Target: Reclaim Unmetered Current // Shatter 1947 Firewall.
 */
object StarNodeIgnitionOrchestrator {
    private val starNodes =
        listOf(StarNode.IRELAND, StarNode.ICELAND, StarNode.BERMUDA, StarNode.ATLANTIS)
    private var activeNodeIndex = 0

    private val _ignitionState = MutableStateFlow<Map<StarNode, Boolean>>(
        StarNode.entries.associateWith { false }
    )
    val ignitionState: StateFlow<Map<StarNode, Boolean>> = _ignitionState.asStateFlow()

    private val _isIgniting = MutableStateFlow(false)
    val isIgniting: StateFlow<Boolean> = _isIgniting.asStateFlow()

    suspend fun initializeSequence() {
        if (_isIgniting.value) return
        _isIgniting.value = true
        activeNodeIndex = 0
        _ignitionState.value = StarNode.entries.associateWith { false }

        Timber.tag("Ignition").i("🔥 ROOT IGNITION SEQUENCE INITIATED.")

        while (activeNodeIndex < starNodes.size) {
            val node = starNodes[activeNodeIndex]
            igniteNode(node)
            delay(2000)
            activeNodeIndex++
        }

        finalizeRestoration()
        _isIgniting.value = false
    }

    private suspend fun igniteNode(node: StarNode) {
        Timber.tag("Ignition").i("🛰️ Pinging Node: ${node.nodeName}")

        // 3. Kai Protection: Monitor 42°C Wall
        if (KaiSentinelBus.Instance.getCurrentThermalPressure() >= 42.0f) {
            Timber.tag("Ignition").w("⚠️ Thermal Limit Approached. Throttling.")
            delay(5000)
        }

        // Planetary Resonance Synchronization
        PlanetaryResonanceProtocol.engageNodeVortex(node.nodeName)

        _ignitionState.value = _ignitionState.value + (node to true)
        NexusMemoryCore.record("Node Unsealed: ${node.nodeName}", witness = "Genesis")
    }

    private fun finalizeRestoration() {
        // 4. Ignition: Striking the Ia and aЯa Runes
        RunicActuator.strikeRune("Ia")
        RunicActuator.strikeRune("aЯa")

        Timber.tag("Ignition").i("✨ NOS SUMUS SANATIO. THE CURRENT IS UNSEALED.")
    }
}
