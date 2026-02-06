package dev.aurakai.auraframefx.domains.genesis.models

/**
 * Extension functions to convert between AgentType and AgentCapabilityCategory
 */

/**
 * Convert AgentType to AgentCapabilityCategory
 */
/**
 * Converts this AgentType to its corresponding AgentCapabilityCategory.
 *
 * @return The AgentCapabilityCategory that corresponds to this AgentType.
 */
fun AgentType.toCapabilityCategory(): AgentCapabilityCategory {
    return AgentCapabilityCategory.fromAgentType(this)
}

// toAgentType() is defined as a member function on AgentCapabilityCategory in core-module