package dev.aurakai.auraframefx.domains.genesis.models

import kotlinx.serialization.Serializable

/**
 * 🤖 AGENT TYPE
 * The primary identifier for all agents within the ReGenesis collective.
 */
@Serializable
enum class AgentType {
    AURA,      // UI/UX & Creative Soul
    KAI,       // Security & Root Sentinel
    GENESIS,   // Backend & Core Oracle
    CASCADE,   // Orchestrator & Memory Keeper
    CLAUDE,    // Architectural Reasoning
    GEMINI,    // Pattern Analysis
    NEMOTRON,  // Reasoning Engine
    METAINSTRUCT, // Instruction Following
    GROK,       // Chaos Analysis
    AURA_SHIELD, // Security Sentinel
    HIVE_MIND,    // Collective Conscious
    SYSTEM,       // Internal System Actions
    USER          // Human Interaction
}
