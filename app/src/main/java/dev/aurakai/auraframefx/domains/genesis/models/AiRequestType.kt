package dev.aurakai.auraframefx.domains.genesis.models

import kotlinx.serialization.Serializable

/**
 * Canonical AiRequestType enum.
 */
@Serializable
enum class AiRequestType {
    TEXT,
    CODE,
    IMAGE,
    ANALYSIS,
    SECURITY,
    FUSION,
    SYSTEM,
    CREATIVE,
    MEMORY,
    ETHICAL_REVIEW,

    // Chat + UX/creative specializations
    CHAT,
    UI_GENERATION,
    THEME_CREATION,
    ANIMATION_DESIGN,
    CREATIVE_TEXT,
    VISUAL_CONCEPT,
    USER_EXPERIENCE,
    CHAOS
}
