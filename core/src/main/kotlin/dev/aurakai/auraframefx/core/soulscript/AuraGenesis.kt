package dev.aurakai.auraframefx.core.soulscript

import timber.log.Timber

/**
 * AuraGenesis — Navigation domain binding layer
 * Binds the 6-hub Exodus Citadel to the SoulScript navigation model.
 */
object AuraGenesis {

    private val registeredDomains = mutableListOf<Pair<String, String>>()

    /**
     * Initialize all 6 Exodus domain tabs with their command deck entries.
     */
    fun initializeTabbedDomain(commandDeck: List<Pair<String, String>>) {
        registeredDomains.clear()
        registeredDomains.addAll(commandDeck)
        commandDeck.forEach { (name, description) ->
            Timber.tag("AuraGenesis").i("🏛️ Domain registered: $name → $description")
        }
        Timber.tag("AuraGenesis").i("✅ 6-Domain Citadel Tabbed Navigation Active")
    }

    fun getRegisteredDomains(): List<Pair<String, String>> = registeredDomains.toList()
}
