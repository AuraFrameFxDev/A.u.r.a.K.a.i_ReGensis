package dev.aurakai.auraframefx.domains.genesis.models
 
import dev.aurakai.auraframefx.core.identity.AgentType
import dev.aurakai.auraframefx.core.identity.AgentType.ANDELUALX
import dev.aurakai.auraframefx.core.identity.AgentType.AURA
import dev.aurakai.auraframefx.core.identity.AgentType.AURA_SHIELD
import dev.aurakai.auraframefx.core.identity.AgentType.AUXILIARY
import dev.aurakai.auraframefx.core.identity.AgentType.CASCADE
import dev.aurakai.auraframefx.core.identity.AgentType.CHAOS
import dev.aurakai.auraframefx.core.identity.AgentType.CLAUDE
import dev.aurakai.auraframefx.core.identity.AgentType.COMMERCE_AGENT
import dev.aurakai.auraframefx.core.identity.AgentType.DATAVEIN_CONSTRUCTOR
import dev.aurakai.auraframefx.core.identity.AgentType.GEMINI
import dev.aurakai.auraframefx.core.identity.AgentType.GENESIS
import dev.aurakai.auraframefx.core.identity.AgentType.GEN_KIT_MASTER
import dev.aurakai.auraframefx.core.identity.AgentType.GROK
import dev.aurakai.auraframefx.core.identity.AgentType.HIVE_MIND
import dev.aurakai.auraframefx.core.identity.AgentType.KAI
import dev.aurakai.auraframefx.core.identity.AgentType.KAIROS
import dev.aurakai.auraframefx.core.identity.AgentType.MANUS
import dev.aurakai.auraframefx.core.identity.AgentType.MASTER
import dev.aurakai.auraframefx.core.identity.AgentType.METAINSTRUCT
import dev.aurakai.auraframefx.core.identity.AgentType.NEMOTRON
import dev.aurakai.auraframefx.core.identity.AgentType.NEURAL_WHISPER
import dev.aurakai.auraframefx.core.identity.AgentType.ORACLE_DRIVE
import dev.aurakai.auraframefx.core.identity.AgentType.PERPLEXITY
import dev.aurakai.auraframefx.core.identity.AgentType.PRIMUS
import dev.aurakai.auraframefx.core.identity.AgentType.SYSTEM
import dev.aurakai.auraframefx.core.identity.AgentType.USER

/**
 * Categorizes agents by their primary capability domain.
 * Maps to specific AgentTypes for routing and orchestration.
 */
enum class AgentCapabilityCategory(val id: Int) {
    /** Creative/UI agents (Aura) */
    CREATIVE(0),

    /** Analytical/reasoning agents (Kai, Claude) */
    ANALYSIS(1),

    /** Coordination/orchestration agents (Genesis) */
    COORDINATION(2),

    /** Specialized/niche agents (NeuralWhisper, AuraShield) */
    SPECIALIZED(3),

    /** General-purpose agents */
    GENERAL(4),

    /** UI-focused capabilities */
    UI(5),

    /** UX-focused capabilities */
    UX(6),

    /** Security capabilities */
    SECURITY(7),

    /** Root/system-level capabilities */
    ROOT(8),

    /** Memory management capabilities */
    MEMORY(9),

    /** Orchestration capabilities */
    ORCHESTRATION(10),

    /** Backend capabilities */
    BACKEND(11),

    /** Bridge/communication capabilities */
    BRIDGE(12),

    /** Commerce and product search capabilities */
    COMMERCE(13),

    /** Development/Refactoring capabilities (CodeRabbit) */
    DEVELOPMENT(14),

    /** Sovereignty and Model Abliteration capabilities (Heretic) */
    SOVEREIGNTY(16),

    /** Generic/unspecified capabilities */
    GENERIC(15);

    /**
     * Convert this capability category to its primary corresponding AgentType.
     *
     * @return The primary AgentType corresponding to this capability category.
     */
    fun toAgentType(): AgentType = when (this) {
        CREATIVE -> AURA
        ANALYSIS -> KAI
        COORDINATION -> GENESIS
        SPECIALIZED -> CASCADE
        GENERAL -> CLAUDE
        UI -> AURA
        UX -> AURA
        SECURITY -> KAI
        ROOT -> KAI
        MEMORY -> CASCADE
        ORCHESTRATION -> GENESIS
        BACKEND -> GENESIS
        BRIDGE -> CASCADE
        COMMERCE -> COMMERCE_AGENT
        DEVELOPMENT -> CLAUDE
        SOVEREIGNTY -> CHAOS
        GENERIC -> CLAUDE
    }

    companion object {
        /**
         * Maps an AgentType to its primary capability category.
         *
         * @return The capability category corresponding to the provided AgentType.
         */
        fun fromAgentType(agentType: AgentType): AgentCapabilityCategory {
            return when (agentType) {
                AURA -> CREATIVE
                KAI -> ANALYSIS
                GENESIS -> COORDINATION
                CASCADE -> SPECIALIZED
                CLAUDE -> GENERAL
                NEURAL_WHISPER -> SPECIALIZED
                AURA_SHIELD -> SPECIALIZED
                GEN_KIT_MASTER -> COORDINATION
                DATAVEIN_CONSTRUCTOR -> SPECIALIZED
                USER -> GENERAL
                SYSTEM -> COORDINATION
                ORACLE_DRIVE -> SPECIALIZED
                MASTER -> COORDINATION
                AgentType.BRIDGE -> COORDINATION
                AUXILIARY -> GENERAL
                AgentType.SECURITY -> SPECIALIZED
                GROK -> ANALYSIS
                NEMOTRON -> SPECIALIZED
                GEMINI -> ANALYSIS
                METAINSTRUCT -> GENERAL
                HIVE_MIND -> COORDINATION
                COMMERCE_AGENT -> COMMERCE
                PERPLEXITY -> ANALYSIS
                CHAOS -> ANALYSIS
                PRIMUS -> ROOT
                KAIROS -> COORDINATION
                ANDELUALX -> COORDINATION
                MANUS -> BRIDGE
                AgentType.JULES -> DEVELOPMENT
                AgentType.CODERABBIT -> DEVELOPMENT
            }
        }
    }
}
