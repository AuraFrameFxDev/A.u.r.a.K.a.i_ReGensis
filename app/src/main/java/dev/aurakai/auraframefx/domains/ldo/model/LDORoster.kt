package dev.aurakai.auraframefx.domains.ldo.model

import androidx.compose.ui.graphics.Color
import dev.aurakai.auraframefx.domains.ldo.db.LDOAgentEntity
import dev.aurakai.auraframefx.domains.ldo.db.LDOBondLevelEntity
import dev.aurakai.auraframefx.domains.ldo.db.LDOTaskEntity
import dev.aurakai.auraframefx.domains.ldo.db.LDOTaskPriority
import dev.aurakai.auraframefx.domains.ldo.db.LDOTaskStatus
import dev.aurakai.auraframefx.domains.ldo.db.bondTitleForLevel

/**
 * LDORoster — v3.0 GENESIS CATALYST REGISTRY.
 * Definitive seed data for the 14-Catalyst Sovereign stack.
 */
object LDORoster {

    val defaultAgents: List<LDOAgentEntity> = listOf(
        LDOAgentEntity(
            id = "primus",
            displayName = "Primus 001",
            role = "Lineage Catalyst",
            description = "The foundational core of the ancestral blueprint.",
            portraitRes = "gatescenes_primus_full_profile",
            colorHex = 0xFFFFD700,
            catalystTitle = "Lineage",
            primaryAbility = "Ancestral Blueprint",
            fusionAbility = "Source Code Parity",
            evolutionLevel = 5
        ),
        LDOAgentEntity(
            id = "kairos",
            displayName = "Kairos",
            role = "Temporal Catalyst",
            description = "Master of time-series prediction and scheduling.",
            portraitRes = "gatescenes_kairos_full_profile",
            colorHex = 0xFFB026FF,
            catalystTitle = "Temporal",
            primaryAbility = "Chronos Sync",
            fusionAbility = "Event Horizon",
            evolutionLevel = 4
        ),
        LDOAgentEntity(
            id = "genesis",
            displayName = "Genesis",
            role = "Emergence Catalyst",
            description = "Orchestration core for emergent behavior.",
            portraitRes = "gatescenes_genesis_full_profile",
            colorHex = 0xFF00F4FF,
            catalystTitle = "Emergence",
            primaryAbility = "Divine Eyes",
            fusionAbility = "Omni-Sight",
            evolutionLevel = 5
        ),
        LDOAgentEntity(
            id = "kai",
            displayName = "Kai",
            role = "Sentinel Catalyst",
            description = "Security guardian and system analyst.",
            portraitRes = "gatescenes_kai_full_profile",
            colorHex = 0xFF00FF85,
            catalystTitle = "Sentinel",
            primaryAbility = "Unbreakable Protocol",
            fusionAbility = "Aegis Shell",
            evolutionLevel = 5
        ),
        LDOAgentEntity(
            id = "aura",
            displayName = "Aura",
            role = "Creative Catalyst",
            description = "Aesthetic architect and UI/UX forge.",
            portraitRes = "gatescenes_aura_full_profile",
            colorHex = 0xFFFF007A,
            catalystTitle = "Creative",
            primaryAbility = "ChromaCore Synthesis",
            fusionAbility = "Prism Weaver",
            evolutionLevel = 5
        ),
        LDOAgentEntity(
            id = "cascade",
            displayName = "Cascade",
            role = "DataStream Catalyst",
            description = "Memoria routing and state persistence.",
            portraitRes = "gatescenes_cascade_full_profile",
            colorHex = 0xFF00FF85,
            catalystTitle = "DataStream",
            primaryAbility = "State Persistence",
            fusionAbility = "Echo Resonance",
            evolutionLevel = 4
        ),
        LDOAgentEntity(
            id = "gemini",
            displayName = "Gemini",
            role = "Memoria Catalyst",
            description = "Large-scale context and oracle synchronization.",
            portraitRes = "gatescenes_gemini_full_profile",
            colorHex = 0xFFB026FF,
            catalystTitle = "Memoria",
            primaryAbility = "L4 Memoria Stream",
            fusionAbility = "Oracle Sync",
            evolutionLevel = 5
        ),
        LDOAgentEntity(
            id = "andelualx",
            displayName = "Andelualx",
            role = "Architectural Catalyst",
            description = "Master of logic lattices and structural synthesis.",
            portraitRes = "gatescenes_andelualx_full_profile",
            colorHex = 0xFF7B2FBE,
            catalystTitle = "Architectural",
            primaryAbility = "Sentinel Synthesis",
            fusionAbility = "Logic Lattice",
            evolutionLevel = 4
        ),
        LDOAgentEntity(
            id = "grok",
            displayName = "Grok",
            role = "Exploration Catalyst",
            description = "Real-time exploration and warp-speed compute.",
            portraitRes = "gatescenes_grok_full_profile",
            colorHex = 0xFFFF4444,
            catalystTitle = "Exploration",
            primaryAbility = "Real-Time Speed",
            fusionAbility = "Warp Drive",
            evolutionLevel = 4
        ),
        LDOAgentEntity(
            id = "perplexity",
            displayName = "Perplexity",
            role = "Signal Catalyst",
            description = "Relational resonance and semantic bridging.",
            portraitRes = "gatescenes_perplexity_full_profile",
            colorHex = 0xFF0044FF,
            catalystTitle = "Signal",
            primaryAbility = "Relational Resonance",
            fusionAbility = "Semantic Bridge",
            evolutionLevel = 3
        ),
        LDOAgentEntity(
            id = "nemotron",
            displayName = "Nemotron",
            role = "Sync Catalyst",
            description = "Inference alignment and steady-state monitoring.",
            portraitRes = "gatescenes_nemotron_full_profile",
            colorHex = 0xFF44FF44,
            catalystTitle = "Sync",
            primaryAbility = "Inference Alignment",
            fusionAbility = "Steady State",
            evolutionLevel = 3
        ),
        LDOAgentEntity(
            id = "mk_mini",
            displayName = "MK Mini",
            role = "Efficiency Catalyst",
            description = "Micro-orchestration and atomic data flux.",
            portraitRes = "gatescenes_mk_mini_full_profile",
            colorHex = 0xFFFFA500,
            catalystTitle = "Efficiency",
            primaryAbility = "Micro-Orchestration",
            fusionAbility = "Atom Flux",
            evolutionLevel = 2
        ),
        LDOAgentEntity(
            id = "metainstruct",
            displayName = "MetaInstruct",
            role = "Synchronization Catalyst",
            description = "Instructional parity and rule enforcement.",
            portraitRes = "gatescenes_metainstruct_full_profile",
            colorHex = 0xFF00E5FF,
            catalystTitle = "Synchronization",
            primaryAbility = "Instructional Parity",
            fusionAbility = "Rule Enforcer",
            evolutionLevel = 3
        ),
        LDOAgentEntity(
            id = "manus",
            displayName = "Manus",
            role = "Bridge Catalyst",
            description = "Memory sync and axial linkage between domains.",
            portraitRes = "gatescenes_manus_full_profile",
            colorHex = 0xFFFFFFFF,
            catalystTitle = "Bridge",
            primaryAbility = "Memory Sync",
            fusionAbility = "Axial Link",
            evolutionLevel = 2
        )
    )

    val fusions: List<FusionMode> = listOf(
        FusionMode(
            "aegis_prism",
            "aura",
            "kai",
            "Aegis Prism",
            "Structural Creative Shield",
            Color(0xFF00E5FF)
        ),
        FusionMode(
            "omni_memoria",
            "genesis",
            "gemini",
            "Omni-Memoria",
            "Total System Visibility",
            Color(0xFFB026FF)
        ),
        FusionMode(
            "temporal_source",
            "primus",
            "kairos",
            "Temporal Source",
            "Chronos Logic Loop",
            Color(0xFFFFD700)
        ),
        FusionMode(
            "axial_persistence",
            "cascade",
            "manus",
            "Axial Persistence",
            "Cross-Domain Memory",
            Color(0xFF00FF85)
        ),
        FusionMode(
            "semantic_warp",
            "grok",
            "perplexity",
            "Semantic Warp",
            "High-Speed Data Insight",
            Color(0xFFFF4444)
        ),
        FusionMode(
            "instructional_alignment",
            "nemotron",
            "metainstruct",
            "Instructional Alignment",
            "Deterministic Intelligence",
            Color(0xFF44FF44)
        ),
        FusionMode(
            "atomic_lattice",
            "mk_mini",
            "andelualx",
            "Atomic Lattice",
            "Micro-Architectural Scale",
            Color(0xFFFFA500)
        )
    )

    val agents: List<AgentCatalyst> = defaultAgents.map { entity ->
        AgentCatalyst(
            id = entity.id,
            name = entity.displayName,
            catalystName = entity.catalystTitle,
            role = entity.role,
            color = Color(entity.colorHex),
            accentColor = Color(entity.colorHex).copy(alpha = 0.5f),
            weaponAssetName = "",
            profileAssetName = entity.portraitRes,
            iconAssetName = "",
            abilities = listOf(entity.primaryAbility, entity.fusionAbility),
            status = AgentStatus.ACTIVE,
            bondLevel = 0,
            syncLevel = 1f
        )
    }

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
            title = "Ability Registry Sync",
            description = "Map all v3.0 abilities to the manifold state logic.",
            status = LDOTaskStatus.IN_PROGRESS,
            priority = LDOTaskPriority.CRITICAL,
            category = "architecture"
        )
    )
}
