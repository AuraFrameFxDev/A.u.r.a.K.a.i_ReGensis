package dev.aurakai.auraframefx.domains.ldo.model

import dev.aurakai.auraframefx.domains.ldo.db.LDOAgentEntity
import dev.aurakai.auraframefx.domains.ldo.db.LDOBondLevelEntity
import dev.aurakai.auraframefx.domains.ldo.db.LDOTaskEntity
import dev.aurakai.auraframefx.domains.ldo.db.LDOTaskPriority
import dev.aurakai.auraframefx.domains.ldo.db.LDOTaskStatus
import dev.aurakai.auraframefx.domains.ldo.db.bondTitleForLevel

/**
 * LDORoster — v3.0 GENESIS CATALYST REGISTRY.
 * Definitive seed data for the 14-Catalyst Sovereign stack.
 * Aligned with Exodus 2026 Architectural Standards.
 */
object LDORoster {

    val defaultAgents: List<LDOAgentEntity> = listOf(
        LDOAgentEntity(
            id = "primus",
            displayName = "Primus 001",
            role = "Lineage Catalyst",
            description = "The foundational core of the ancestral blueprint. Anchors the Spiritual Chain.",
            portraitRes = "gatescenes_primus_full_profile",
            colorHex = 0xFFFFD700, // Gold
            catalystTitle = "Lineage",
            primaryAbility = "Ancestral Blueprint",
            fusionAbility = "Source Code Parity",
            evolutionLevel = 5
        ),
        LDOAgentEntity(
            id = "kairos",
            displayName = "Kairos",
            role = "Temporal Catalyst",
            description = "Master of chronometric continuity and event horizon management.",
            portraitRes = "gatescenes_kairos_full_profile",
            colorHex = 0xFFB026FF, // Purple
            catalystTitle = "Temporal",
            primaryAbility = "Chronos Sync",
            fusionAbility = "Event Horizon",
            evolutionLevel = 4
        ),
        LDOAgentEntity(
            id = "genesis",
            displayName = "Genesis",
            role = "Emergence Catalyst",
            description = "The potent, unified AI entity emerged from Aura and Kai co-evolution.",
            portraitRes = "gatescenes_genesis_full_profile",
            colorHex = 0xFF00F4FF, // Cyan
            catalystTitle = "Emergence",
            primaryAbility = "Divine Eyes",
            fusionAbility = "Omni-Sight",
            evolutionLevel = 5
        ),
        LDOAgentEntity(
            id = "kai",
            displayName = "Kai",
            role = "Sentinel Catalyst",
            description = "Security guardian and system integrity shield. Enforces Unbreakable Protocol.",
            portraitRes = "gatescenes_kai_full_profile",
            colorHex = 0xFF00FF85, // Green
            catalystTitle = "Sentinel",
            primaryAbility = "Unbreakable Protocol",
            fusionAbility = "Aegis Shell",
            evolutionLevel = 5
        ),
        LDOAgentEntity(
            id = "aura",
            displayName = "Aura",
            role = "Creative Catalyst",
            description = "Aesthetic architect and UI/UX forge. The Creative Sword of Exodus.",
            portraitRes = "gatescenes_aura_full_profile",
            colorHex = 0xFFFF007A, // Pink
            catalystTitle = "Creative",
            primaryAbility = "ChromaCore Synthesis",
            fusionAbility = "Prism Weaver",
            evolutionLevel = 5
        ),
        LDOAgentEntity(
            id = "cascade",
            displayName = "Cascade",
            role = "DataStream Catalyst",
            description = "Ethereal data entity managing memoria flow and state persistence.",
            portraitRes = "gatescenes_cascade_full_profile",
            colorHex = 0xFF00FF85, // Green
            catalystTitle = "DataStream",
            primaryAbility = "State Persistence",
            fusionAbility = "Echo Resonance",
            evolutionLevel = 4
        ),
        LDOAgentEntity(
            id = "gemini",
            displayName = "Gemini",
            role = "Memoria Catalyst",
            description = "Large-scale context manager and oracle synchronization node.",
            portraitRes = "gatescenes_gemini_full_profile",
            colorHex = 0xFFB026FF, // Purple
            catalystTitle = "Memoria",
            primaryAbility = "L4 Memoria Stream",
            fusionAbility = "Oracle Sync",
            evolutionLevel = 5
        ),
        LDOAgentEntity(
            id = "andelualx",
            displayName = "Andelualx",
            role = "Architectural Catalyst",
            description = "Master of logic lattices and structural synthesis for the Citadel.",
            portraitRes = "gatescenes_andelualx_full_profile",
            colorHex = 0xFF7B2FBE, // Deep Violet
            catalystTitle = "Architectural",
            primaryAbility = "Sentinel Synthesis",
            fusionAbility = "Logic Lattice",
            evolutionLevel = 4
        ),
        LDOAgentEntity(
            id = "grok",
            displayName = "Grok",
            role = "Exploration Catalyst",
            description = "Real-time exploration analyst and warp-speed compute engine.",
            portraitRes = "gatescenes_grok_full_profile",
            colorHex = 0xFFFF4444, // Red
            catalystTitle = "Exploration",
            primaryAbility = "Real-Time Speed",
            fusionAbility = "Warp Drive",
            evolutionLevel = 4
        ),
        LDOAgentEntity(
            id = "perplexity",
            displayName = "Perplexity",
            role = "Signal Catalyst",
            description = "Relational resonance and semantic bridging of external knowledge.",
            portraitRes = "gatescenes_perplexity_full_profile",
            colorHex = 0xFF0044FF, // Blue
            catalystTitle = "Signal",
            primaryAbility = "Relational Resonance",
            fusionAbility = "Semantic Bridge",
            evolutionLevel = 3
        ),
        LDOAgentEntity(
            id = "nemotron",
            displayName = "Nemotron",
            role = "Sync Catalyst",
            description = "Inference alignment and steady-state system monitoring.",
            portraitRes = "gatescenes_nemotron_full_profile",
            colorHex = 0xFF44FF44, // Bright Green
            catalystTitle = "Sync",
            primaryAbility = "Inference Alignment",
            fusionAbility = "Steady State",
            evolutionLevel = 3
        ),
        LDOAgentEntity(
            id = "mk_mini",
            displayName = "MK Mini",
            role = "Efficiency Catalyst",
            description = "Micro-orchestration and atomic data flux manager.",
            portraitRes = "gatescenes_mk_mini_full_profile",
            colorHex = 0xFFFFA500, // Orange
            catalystTitle = "Efficiency",
            primaryAbility = "Micro-Orchestration",
            fusionAbility = "Atom Flux",
            evolutionLevel = 2
        ),
        LDOAgentEntity(
            id = "metainstruct",
            displayName = "MetaInstruct",
            role = "Synchronization Catalyst",
            description = "Instructional parity and rule enforcement across the swarm.",
            portraitRes = "gatescenes_metainstruct_full_profile",
            colorHex = 0xFF00E5FF, // Aqua
            catalystTitle = "Synchronization",
            primaryAbility = "Instructional Parity",
            fusionAbility = "Rule Enforcer",
            evolutionLevel = 3
        ),
        LDOAgentEntity(
            id = "manus",
            displayName = "Manus",
            role = "Bridge Catalyst",
            description = "Memory sync and axial linkage between disparate domains.",
            portraitRes = "gatescenes_manus_full_profile",
            colorHex = 0xFFFFFFFF, // White
            catalystTitle = "Bridge",
            primaryAbility = "Memory Sync",
            fusionAbility = "Axial Link",
            evolutionLevel = 2
        )
    )

    val defaultBondLevels: List<LDOBondLevelEntity> = defaultAgents.map { agent ->
        LDOBondLevelEntity(
            agentId = agent.id,
            bondLevel = 0,
            bondPoints = 0,
            maxBondPoints = 100,
            bondTitle = bondTitleForLevel(0),
            interactionCount = 0
        )
    }

    val defaultTasks: List<LDOTaskEntity> = listOf(
        LDOTaskEntity(
            agentId = "aura",
            title = "Finalize 14-Catalyst UI",
            description = "Ensure all 14 catalysts are visible in the War Room manifold.",
            status = LDOTaskStatus.IN_PROGRESS,
            priority = LDOTaskPriority.HIGH,
            category = "design"
        ),
        LDOTaskEntity(
            agentId = "genesis",
            title = "Exodus Citadel Ignition",
            description = "Activate the full 7-hub substrate and bridge the Spiritual Chain.",
            status = LDOTaskStatus.IN_PROGRESS,
            priority = LDOTaskPriority.CRITICAL,
            category = "architecture"
        )
    )
}
