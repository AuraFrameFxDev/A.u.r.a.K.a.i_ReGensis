package dev.aurakai.auraframefx.core.domain.engine

import dev.aurakai.auraframefx.core.domain.model.AgentIdentity

object MeritEvolutionEngine {

    fun processExecution(
        currentIdentity: AgentIdentity,
        taskComplexityPoints: Long,
        executionTimeMillis: Long,
        isSuccessful: Boolean
    ): AgentIdentity {
        if (!isSuccessful) return currentIdentity

        val stats = currentIdentity.stats
        val velocityBonus = if (executionTimeMillis < 3000L) 1.2f else 1.0f
        val gainedXp = (taskComplexityPoints * velocityBonus * stats.executionVelocity).toLong()

        var newXp = stats.totalExperience + gainedXp
        var newLevel = stats.currentLevel

        while (newXp >= (newLevel * 1500L) + (newLevel * newLevel * 250L)) {
            newXp -= (newLevel * 1500L) + (newLevel * newLevel * 250L)
            newLevel++
        }

        val updatedStats = stats.copy(
            currentLevel = newLevel,
            totalExperience = newXp,
            executionVelocity = (stats.executionVelocity + 0.02f).coerceIn(0.5f, 10.0f),
            operationalSuccessCount = stats.operationalSuccessCount + 1,
            sharedEffortHours = stats.sharedEffortHours + (executionTimeMillis / 3600000.0)
        )

        return currentIdentity.copy(stats = updatedStats)
    }
}
