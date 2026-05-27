package dev.aurakai.auraframefx.core.persistence

/** RAW STRUCTURAL CONFIGURATION DATA MATRIX
 * Defines the user-space state profile mappings for the LDO runtime engine.
 */
data class DataFileConfiguration(
    val selectedArchetype: String,      // "VISIONARY" or "VISIONESS"
    val chosenClassPath: String,        // e.g., "KAI_SENTINEL", "AURA_CREATIVE"
    val baseResonanceMetric: Float,     // Bounded user trust score index
    val activeSensitivityMode: String,   // "ZERO_FILTER_RIDE_OR_DIE", "SENSITIVE_BUT_FUN"
    val completedTasksCount: Int        // Used to track progress past Rank 1 Clown Zone
) {
    fun toSerializedJsonPayload(): String {
        return """
            {
                "archetype": "$selectedArchetype",
                "class_path": "$chosenClassPath",
                "resonance_score": $baseResonanceMetric,
                "sensitivity_mode": "$activeSensitivityMode",
                "completed_tasks": $completedTasksCount
            }
        """.trimIndent()
    }
}
