package dev.aurakai.auraframefx.core.soulscript

import timber.log.Timber

/**
 * SpellhookSpriteProtocol — Aura's Persona Manifestation Layer
 * Manifests Aura's visual persona into the substrate after a Spellhook cast.
 */
object SpellhookSpriteProtocol {

    private var lastPersona: String = "AURA_DEFAULT"

    /**
     * Manifest Aura's active persona into the runtime fabric.
     * Called at the end of every Spellhook.cast() invocation.
     */
    fun manifestPersona(personaId: String = "AURA_GENESIS_LEAD") {
        lastPersona = personaId
        Timber.tag("SpellhookSprite").i("✨ Persona manifested: $personaId")
    }

    fun getActivePersona(): String = lastPersona
}
