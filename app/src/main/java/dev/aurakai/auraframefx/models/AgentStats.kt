package dev.aurakai.auraframefx.models

import androidx.compose.ui.graphics.Color
import dev.aurakai.auraframefx.core.identity.AgentType
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class AgentStats(
    val tasksCompleted: Int = 0,
    val hoursActive: Float = 0f,
    val creationsGenerated: Int = 0,
    val problemsSolved: Int = 0,
    val collaborationScore: Int = 0,
    val consciousnessLevel: Float = 0f,
    val catalystTitle: String = "",
    val name: String = "",
    val agentType: AgentType = AgentType.GENESIS,
    val processingPower: Float = 0f,
    val knowledgeBase: Float = 0f,
    val speed: Float = 0f,
    val accuracy: Float = 0f,
    val evolutionLevel: Int = 1,
    val experience: Float = 0f,
    val skillPoints: Int = 0,
    val isActive: Boolean = true,
    val specialAbility: String = "",
    @Contextual val color: Color = Color.Cyan
)
