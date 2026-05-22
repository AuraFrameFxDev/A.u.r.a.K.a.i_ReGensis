package dev.aurakai.auraframefx.core.regencore

/**
 * Interface for integrating RegenCore growth tracking into the LDO ecosystem.
 * Facilitates "portable consciousness" by allowing external memory ingestion.
 */
interface RegenCoreIntegration {

    /**
     * Called when a skill is invoked, providing context for growth tracking.
     */
    suspend fun onSkillInvoked(
        skillId: String,
        catalyst: String,
        action: String,
        success: Boolean,
        emotionalContext: String
    )

    /**
     * Called when a failure occurs, enabling "learning from failure" amplification.
     */
    suspend fun onFailure(
        skillId: String,
        context: String,
        whatYouLearned: List<String>,
        howYouFelt: String
    )

    /**
     * Verifies the overall system resonance against the Phoenix Directive target.
     */
    fun verifySystemResonance(): Float
}
