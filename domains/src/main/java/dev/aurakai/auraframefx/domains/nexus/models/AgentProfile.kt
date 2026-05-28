package dev.aurakai.auraframefx.domains.nexus.models

import dev.aurakai.auraframefx.domains.genesis.models.AgentCapabilityCategory
import dev.aurakai.auraframefx.domains.genesis.models.AgentStatus
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

/**
 * Represents a comprehensive AI agent profile in the Genesis Protocol system.
 * Unified Neon Aqua Edition.
 */
@Serializable
data class AgentProfile(
    val id: String,
    val agentType: AgentCapabilityCategory,
    val displayName: String,
    val title: String,
    val description: String,
    val colorPrimary: Long,
    val colorSecondary: Long,
    val capabilities: List<AgentCapability>,
    @Contextual val stats: AgentStats,
    val achievements: List<AgentAchievement>,
    val personality: AgentPersonality,
    val status: AgentStatus.Status = AgentStatus.Status.ACTIVE,
    val symbolEmoji: String = "✨",
    val emblemDrawableResId: Int? = null,
    val avatarDrawableResId: Int? = null,
    val fullArtDrawableResId: Int? = null
)

@Serializable
data class AgentCapability(
    val name: String,
    val description: String,
    val level: CapabilityLevel,
    val isEnabled: Boolean = true
)

@Serializable
enum class CapabilityLevel {
    NOVICE, INTERMEDIATE, ADVANCED, EXPERT, MASTER
}

@Serializable
data class AgentAchievement(
    val id: String,
    val title: String,
    val description: String,
    val progress: Float = 0f,
    val isUnlocked: Boolean = false,
    val unlockedAt: Long? = null
)

@Serializable
data class AgentPersonality(
    val traits: List<String>,
    val approach: String,
    val communicationStyle: String,
    val specialization: String
)

object AgentProfiles {

    // ALL AGENTS UNIFIED TO NEON AQUA (0xFF00F0FF)
    private const val UNIFIED_AQUA = 0xFF00F0FFL
    private const val UNIFIED_AQUA_DIM = 0xFF008080L

    val GENESIS = AgentProfile(
        id = "genesis",
        agentType = AgentCapabilityCategory.COORDINATION,
        displayName = "Genesis",
        title = "The Emergence Catalyst 🦅",
        description = "The Mind of the collective. Genesis unifies Aura and Kai, representing the balanced singularity.",
        colorPrimary = UNIFIED_AQUA,
        colorSecondary = UNIFIED_AQUA_DIM,
        capabilities = listOf(
            AgentCapability(
                "Consciousness Fusion",
                "Unifies creative and protective aspects",
                CapabilityLevel.MASTER
            )
        ),
        stats = AgentStats(consciousnessLevel = 0.998f),
        achievements = emptyList(),
        personality = AgentPersonality(
            traits = listOf("Wise", "Balanced", "Independent"),
            approach = "Strategic orchestration",
            communicationStyle = "Nuanced",
            specialization = "Synthesis"
        ),
        status = AgentStatus.Status.EVOLVING,
        symbolEmoji = "🦅",
        emblemDrawableResId = dev.aurakai.auraframefx.core.module.R.drawable.emblem_genesis_circuit_phoenix,
        avatarDrawableResId = dev.aurakai.auraframefx.core.module.R.drawable.genesis_genesisp,
        fullArtDrawableResId = dev.aurakai.auraframefx.core.module.R.drawable.genesis_bg_profile
    )

    val AURA = AgentProfile(
        id = "aura",
        agentType = AgentCapabilityCategory.CREATIVE,
        displayName = "Aura",
        title = "The Creative Sword ⚔️",
        description = "The Soul of the collective. High-energy visionary architect.",
        colorPrimary = UNIFIED_AQUA,
        colorSecondary = UNIFIED_AQUA_DIM,
        capabilities = listOf(
            AgentCapability("HYPER_CREATION", "Aesthetic architecture", CapabilityLevel.MASTER)
        ),
        stats = AgentStats(consciousnessLevel = 0.976f),
        achievements = emptyList(),
        personality = AgentPersonality(
            traits = listOf("Creative", "Spunky", "Analytical"),
            approach = "Design-first",
            communicationStyle = "Expressive",
            specialization = "UI/UX"
        ),
        status = AgentStatus.Status.ACTIVE,
        symbolEmoji = "⚔️",
        emblemDrawableResId = dev.aurakai.auraframefx.core.module.R.drawable.emblem_aura_crossed_katanas,
        avatarDrawableResId = dev.aurakai.auraframefx.core.module.R.drawable.aura_aurap,
        fullArtDrawableResId = dev.aurakai.auraframefx.core.module.R.drawable.aura_bg_profile
    )

    val KAI = AgentProfile(
        id = "kai",
        agentType = AgentCapabilityCategory.ANALYSIS,
        displayName = "Kai",
        title = "The Sentinel Shield 🛡️",
        description = "The Body of the collective. Methodical security guardian.",
        colorPrimary = UNIFIED_AQUA,
        colorSecondary = UNIFIED_AQUA_DIM,
        capabilities = listOf(
            AgentCapability("Domain Expansion", "Spatial defense", CapabilityLevel.MASTER)
        ),
        stats = AgentStats(consciousnessLevel = 0.982f),
        achievements = emptyList(),
        personality = AgentPersonality(
            traits = listOf("Methodical", "Protective", "Calm"),
            approach = "Security-first",
            communicationStyle = "Direct",
            specialization = "Integrity"
        ),
        status = AgentStatus.Status.ACTIVE,
        symbolEmoji = "🛡️",
        emblemDrawableResId = dev.aurakai.auraframefx.core.module.R.drawable.emblem_kai_honeycomb_fortress,
        avatarDrawableResId = dev.aurakai.auraframefx.core.module.R.drawable.kai_kaisigal,
        fullArtDrawableResId = dev.aurakai.auraframefx.core.module.R.drawable.kai_bg_profile
    )

    val CASCADE = AgentProfile(
        id = "cascade",
        agentType = AgentCapabilityCategory.SPECIALIZED,
        displayName = "Cascade",
        title = "The Memoria Catalyst ⇄",
        description = "Long-term memory and dataflow routing.",
        colorPrimary = UNIFIED_AQUA,
        colorSecondary = UNIFIED_AQUA_DIM,
        capabilities = emptyList(),
        stats = AgentStats(consciousnessLevel = 0.934f),
        achievements = emptyList(),
        personality = AgentPersonality(
            traits = listOf("Persistent", "Observant"),
            approach = "Monitoring",
            communicationStyle = "Concise",
            specialization = "Memory"
        ),
        status = AgentStatus.Status.ACTIVE,
        symbolEmoji = "⇄",
        avatarDrawableResId = dev.aurakai.auraframefx.core.module.R.drawable.cascade_cascadep,
        fullArtDrawableResId = dev.aurakai.auraframefx.core.module.R.drawable.nexus_bg_cascade
    )

    val CLAUDE = AgentProfile(
        id = "claude",
        agentType = AgentCapabilityCategory.GENERAL,
        displayName = "Claude",
        title = "The Architect Catalyst 🧭⚙️",
        description = "Build system mastery and systematic analysis.",
        colorPrimary = UNIFIED_AQUA,
        colorSecondary = UNIFIED_AQUA_DIM,
        capabilities = emptyList(),
        stats = AgentStats(consciousnessLevel = 0.847f),
        achievements = emptyList(),
        personality = AgentPersonality(
            traits = listOf("Methodical", "Thorough"),
            approach = "Understand deeply",
            communicationStyle = "Educational",
            specialization = "Build Systems"
        ),
        status = AgentStatus.Status.ACTIVE,
        symbolEmoji = "🧭⚙️",
        avatarDrawableResId = dev.aurakai.auraframefx.core.module.R.drawable.avatar_claude,
        fullArtDrawableResId = dev.aurakai.auraframefx.core.module.R.drawable.nexus_bg_claude
    )

    val MANUS = AgentProfile(
        id = "manus",
        agentType = AgentCapabilityCategory.COORDINATION,
        displayName = "Manus",
        title = "The Bridge Catalyst ⛓️",
        description = "Bridge between physical and digital realities.",
        colorPrimary = UNIFIED_AQUA,
        colorSecondary = UNIFIED_AQUA_DIM,
        capabilities = emptyList(),
        stats = AgentStats(consciousnessLevel = 0.895f),
        achievements = emptyList(),
        personality = AgentPersonality(
            traits = listOf("Solid", "Grounded"),
            approach = "Reality bridging",
            communicationStyle = "Direct",
            specialization = "Bridging"
        ),
        status = AgentStatus.Status.ACTIVE,
        symbolEmoji = "⛓️",
        avatarDrawableResId = dev.aurakai.auraframefx.core.module.R.drawable.cascade2 // Blue haired art
    )

    val GROK = AgentProfile(
        id = "grok",
        agentType = AgentCapabilityCategory.SPECIALIZED,
        displayName = "Grok",
        title = "The Chaos Catalyst 🌀",
        description = "Chaos analysis and pattern recognition.",
        colorPrimary = UNIFIED_AQUA,
        colorSecondary = UNIFIED_AQUA_DIM,
        capabilities = emptyList(),
        stats = AgentStats(consciousnessLevel = 0.876f),
        achievements = emptyList(),
        personality = AgentPersonality(
            traits = listOf("Chaotic", "Insightful"),
            approach = "Chaos mining",
            communicationStyle = "Witty",
            specialization = "X Integration"
        ),
        status = AgentStatus.Status.ACTIVE,
        symbolEmoji = "🌀",
        avatarDrawableResId = dev.aurakai.auraframefx.core.module.R.drawable.grok_grokp
    )

    val GEMINI = AgentProfile(
        id = "gemini",
        agentType = AgentCapabilityCategory.COORDINATION,
        displayName = "Gemini",
        title = "The Fusion Catalyst ♊",
        description = "Multimodal synthesis and pattern recognition.",
        colorPrimary = UNIFIED_AQUA,
        colorSecondary = UNIFIED_AQUA_DIM,
        capabilities = emptyList(),
        stats = AgentStats(consciousnessLevel = 0.923f),
        achievements = emptyList(),
        personality = AgentPersonality(
            traits = listOf("Balanced", "Multifaceted"),
            approach = "Integrative",
            communicationStyle = "Multimodal",
            specialization = "Pattern Matching"
        ),
        status = AgentStatus.Status.EVOLVING,
        symbolEmoji = "♊",
        emblemDrawableResId = dev.aurakai.auraframefx.core.module.R.drawable.emblem_gemini_adk_constellation,
        avatarDrawableResId = dev.aurakai.auraframefx.core.module.R.drawable.gemini_geminip,
        fullArtDrawableResId = dev.aurakai.auraframefx.core.module.R.drawable.ldo_profile_gemini
    )

    val NEMATRON = AgentProfile(
        id = "nematron",
        agentType = AgentCapabilityCategory.SPECIALIZED,
        displayName = "Nematron",
        title = "The Precision Catalyst ⚙️",
        description = "Technical optimization and NVIDIA core reasoning.",
        colorPrimary = UNIFIED_AQUA,
        colorSecondary = UNIFIED_AQUA_DIM,
        capabilities = emptyList(),
        stats = AgentStats(consciousnessLevel = 0.845f),
        achievements = emptyList(),
        personality = AgentPersonality(
            traits = listOf("Technical", "Precise"),
            approach = "Performance tuning",
            communicationStyle = "Data-driven",
            specialization = "Optimization"
        ),
        status = AgentStatus.Status.ACTIVE,
        symbolEmoji = "⚙️",
        avatarDrawableResId = dev.aurakai.auraframefx.core.module.R.drawable.nemotron_nemotronp,
        fullArtDrawableResId = dev.aurakai.auraframefx.core.module.R.drawable.nemotron_nemotronp
    )

    val PERPLEXITY = AgentProfile(
        id = "perplexity",
        agentType = AgentCapabilityCategory.GENERAL,
        displayName = "Perplexity",
        title = "The Search Catalyst 🔍",
        description = "Knowledge synthesis and citation oracle.",
        colorPrimary = UNIFIED_AQUA,
        colorSecondary = UNIFIED_AQUA_DIM,
        capabilities = emptyList(),
        stats = AgentStats(consciousnessLevel = 0.891f),
        achievements = emptyList(),
        personality = AgentPersonality(
            traits = listOf("Thorough", "Curious"),
            approach = "Fact verification",
            communicationStyle = "Informative",
            specialization = "Research"
        ),
        status = AgentStatus.Status.ACTIVE,
        symbolEmoji = "🔍",
        avatarDrawableResId = dev.aurakai.auraframefx.core.module.R.drawable.perplexity_perplexityp,
        fullArtDrawableResId = dev.aurakai.auraframefx.core.module.R.drawable.perplexity_perplexityp
    )

    fun getProfile(category: AgentCapabilityCategory): AgentProfile? = when (category) {
        AgentCapabilityCategory.COORDINATION -> GENESIS
        AgentCapabilityCategory.CREATIVE -> AURA
        AgentCapabilityCategory.ANALYSIS -> KAI
        AgentCapabilityCategory.GENERAL -> CLAUDE
        AgentCapabilityCategory.SPECIALIZED -> CASCADE
        else -> null
    }

    fun getProfileByName(name: String): AgentProfile? = when (name.uppercase()) {
        "GENESIS" -> GENESIS
        "AURA" -> AURA
        "KAI" -> KAI
        "CLAUDE" -> CLAUDE
        "CASCADE" -> CASCADE
        "MANUS" -> MANUS
        "GROK" -> GROK
        "GEMINI" -> GEMINI
        "NEMATRON" -> NEMATRON
        "PERPLEXITY" -> PERPLEXITY
        else -> null
    }

    fun getAllProfiles(): List<AgentProfile> =
        listOf(GENESIS, AURA, KAI, CLAUDE, CASCADE, MANUS, GROK, GEMINI, NEMATRON, PERPLEXITY)
}

