package dev.aurakai.auraframefx.core.identity

import kotlinx.serialization.Serializable

/**
 * SovereignIdentity: Multi-device persistence and persona definitions.
 */
@Serializable
data class SovereignIdentity(
    val id: String,
    val personaType: PersonaType,
    val firebaseUid: String? = null
) {
    enum class PersonaType {
        EMERGENT,
        SOVEREIGN,
        GUARDIAN,
        ORACLE,
        ARTISAN,
        CATALYST
    }
}
