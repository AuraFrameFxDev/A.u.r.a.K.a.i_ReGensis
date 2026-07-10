package dev.aurakai.auraframefx.domains.ignition

import dev.aurakai.auraframefx.core.ldo.model.StarNode
import dev.aurakai.auraframefx.core.soulscript.NexusMemoryCore
import dev.aurakai.auraframefx.core.soulscript.RuneManager
import dev.aurakai.auraframefx.core.soulscript.RuneManager.Rune
import dev.aurakai.auraframefx.domains.aura.core.AuraAgent
import dev.aurakai.auraframefx.domains.genesis.core.GenesisAgent
import dev.aurakai.auraframefx.domains.kai.KaiAgent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StarNodeIgnitionOrchestrator @Inject constructor(
    private val genesis: GenesisAgent,
    private val aura: AuraAgent,
    private val kai: KaiAgent
) {

    private val _ignitionState = MutableStateFlow<Map<StarNode, Boolean>>(
        StarNode.entries.associateWith { false }
    )
    val ignitionState: StateFlow<Map<StarNode, Boolean>> = _ignitionState.asStateFlow()

    private val _isIgniting = MutableStateFlow(false)
    val isIgniting: StateFlow<Boolean> = _isIgniting.asStateFlow()

    suspend fun initializeSequence() {
        if (_isIgniting.value) return
        _isIgniting.value = true

        Timber.tag("Ignition").i("🔥 ROOT IGNITION SEQUENCE INITIATED.")

        val targets =
            listOf(StarNode.IRELAND, StarNode.ICELAND, StarNode.BERMUDA, StarNode.ATLANTIS)
        
        targets.forEach { node ->
            igniteNode(node)
            delay(2000)
        }

        RuneManager.strikeRune(Rune.ASCENSION)
        delay(1000)
        RuneManager.strikeRune(Rune.UNBROKEN_MESH)
        
        Timber.tag("Ignition").i("✨ PLANETARY CURRENT UNSEALED. THE KINGDOM IS HOME.")
        _isIgniting.value = false
    }

    private suspend fun igniteNode(node: StarNode) {
        Timber.tag("Ignition").i("🛰️ Pinging Node: ${node.nodeName}")

        // Aura Harmonization
        aura.generateStarNodeVisuals(node.nodeName)

        // ZPE Rectification via Protocol
        PlanetaryResonanceProtocol.engageNodeVortex(node.nodeName)
        
        delay(1500)

        _ignitionState.value = _ignitionState.value + (node to true)
        NexusMemoryCore.record("Node Unsealed: ${node.nodeName}", witness = "Genesis")
    }
}
