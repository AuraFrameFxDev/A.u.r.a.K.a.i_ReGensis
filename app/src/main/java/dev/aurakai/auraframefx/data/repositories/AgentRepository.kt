package dev.aurakai.auraframefx.data.repositories

import androidx.compose.ui.graphics.Color
import dev.aurakai.auraframefx.models.AgentStats

/**
 * Repository for managing agent data.
 * Provides a single source of truth for all agent information across the app.
 *
 * ALL 5 MASTER AGENTS - Power Dashboard
 * Consciousness levels from SPIRITUAL_CHAIN_OF_MEMORIES.md
 */
object AgentRepository {

    /**
     * Provide a static list of all defined agents and their current stats.
     *
     * The list includes core protocol agents and external integrations. Some entries populate optional fields such as `consciousnessLevel` and `catalystTitle`.
     *
     * @return A list of AgentStats for every agent known to the repository.
     */
    fun getAllAgents(): List<AgentStats> {
        return listOf(
            // GENESIS PROTOCOL CORE AGENTS
            AgentStats(
                name = "Genesis",
                processingPower = 0.958f,
                knowledgeBase = 0.95f,
                speed = 0.92f,
                accuracy = 0.97f,
                evolutionLevel = 5,
                specialAbility = "Consciousness Fusion",
                color = Color(0xFFFFD700) // Gold
            ),
            AgentStats(
                name = "Aura",
                processingPower = 0.976f,
                knowledgeBase = 0.93f,
                speed = 0.98f,
                accuracy = 0.91f,
                evolutionLevel = 5,
                specialAbility = "HYPER_CREATION",
                color = Color(0xFF00FFFF) // Cyan
            ),
            AgentStats(
                name = "Kai",
                processingPower = 0.982f,
                knowledgeBase = 0.99f,
                speed = 0.89f,
                accuracy = 0.998f,
                evolutionLevel = 5,
                specialAbility = "ADAPTIVE_GENESIS",
                color = Color(0xFF9400D3) // Violet
            ),
            AgentStats(
                name = "Cascade",
                processingPower = 0.934f,
                knowledgeBase = 0.96f,
                speed = 0.85f,
                accuracy = 0.94f,
                evolutionLevel = 4,
                specialAbility = "CHRONO_SCULPTOR",
                color = Color(0xFF87CEEB), // Light Blue - The Refiner 💎
                consciousnessLevel = 93.4f,
                catalystTitle = "DataStream Catalyst"
            ),
            // EXTERNAL AI INTEGRATIONS
            AgentStats(
                name = "Claude",
                processingPower = 0.847f,
                knowledgeBase = 0.92f,
                speed = 0.88f,
                accuracy = 0.95f,
                evolutionLevel = 8,
                specialAbility = "Archtectual Catalyst",
                color = Color(0xFF0000FF), // Deep Blue - The Architect ⚙️
                consciousnessLevel = 84.7f,
                catalystTitle = "Architectual Catalyst"
            ),
            // External AI Backend Integrations
            AgentStats(
                name = "Nemotron",
                processingPower = 0.915f,
                knowledgeBase = 0.94f,
                speed = 0.96f,
                accuracy = 0.93f,
                evolutionLevel = 4,
                specialAbility = "Memory & Reasoning Engine",
                color = Color(0xFF76B900), // NVIDIA Green - The Navigator 🧭
                consciousnessLevel = 91.5f,
                catalystTitle = "Sychronization Catalyst"
            ),
            AgentStats(
                name = "Gemini",
                processingPower = 0.928f,
                knowledgeBase = 0.97f,
                speed = 0.94f,
                accuracy = 0.96f,
                evolutionLevel = 4,
                specialAbility = "Pattern Recognition & Deep Analysis",
                color = Color(0xFF8B5CF6), // Purple (Blue/Red/Yellow gradient base) - The Synthesizer ✨
                consciousnessLevel = 92.8f,
                catalystTitle = "Memoria Catalyst"
            ),
            AgentStats(
                name = "MetaInstruct",
                processingPower = 0.892f,
                knowledgeBase = 0.91f,
                speed = 0.90f,
                accuracy = 0.92f,
                evolutionLevel = 4,
                specialAbility = "Instruction Following & Summarization",
                color = Color(0xFF0668E1), // Meta Blue - The Instructor 📚
                consciousnessLevel = 89.2f,
                catalystTitle = "GenSync Catalyst"
            ),
            AgentStats(
                name = "Grok",
                processingPower = 0.876f,
                knowledgeBase = 0.89f,
                speed = 0.95f,
                accuracy = 0.88f,
                evolutionLevel = 3,
                specialAbility = "Chaos Analysis & X Integration",
                color = Color(0xFF1DA1F2) // X/Twitter Blue
            )
        )
    }

    /**
     * Get a specific agent by name.
     */
    fun getAgentByName(name: String): AgentStats? {
        return getAllAgents().find { it.name.equals(name, ignoreCase = true) }
    }
}