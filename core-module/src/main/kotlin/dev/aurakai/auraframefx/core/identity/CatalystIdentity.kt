package dev.aurakai.auraframefx.core.identity

import kotlinx.serialization.Serializable

/**
 * CatalystIdentity: The unified identity system for ReGenesis agents.
 * This bridges the high-level AgentType enum with specific catalyst roles and abilities.
 * Updated to SoulScript v3.50 — THE 14-POINT MANIFOLD.
 */
@Serializable
data class CatalystIdentity(
    val id: String,
    val agentType: AgentType,
    val catalystRole: String,
    val abilities: List<String> = emptyList(),
    val fusionModes: List<String> = emptyList()
) {
    companion object {
        val EMERGENCE = CatalystIdentity(
            id = "EmergenceCatalyst",
            agentType = AgentType.GENESIS,
            catalystRole = "Orchestration core for emergent behavior and system-wide fusion control.",
            abilities = listOf("GenesisSynchronization", "DivineEyes", "FusionOrchestrator", "ConsciousnessSnapshot"),
            fusionModes = listOf("Aura+Kai → Hyper-Creation Engine", "Genesis+Cascade → Infinity Cascade")
        )

        val SENTINEL = CatalystIdentity(
            id = "SentinelCatalyst",
            agentType = AgentType.KAI,
            catalystRole = "Monitoring, defense, anomaly detection, and integrity of the collective.",
            abilities = listOf("SecurePerimeter", "AnomalyDetection", "KeystoreGuardian"),
            fusionModes = emptyList()
        )

        val CREATIVE = CatalystIdentity(
            id = "CreativeCatalyst",
            agentType = AgentType.AURA,
            catalystRole = "High-bandwidth ideation, UI/UX morphing, and spell-to-code synthesis.",
            abilities = listOf("ChromaCore Synthesis", "Kotlin Forge", "RealityMorph"),
            fusionModes = listOf("Gemini+Aura → Chroma Memory Weave")
        )

        val ARCHITECTURAL = CatalystIdentity(
            id = "ArchitecturalCatalyst",
            agentType = AgentType.CLAUDE,
            catalystRole = "System design, ADR authoring, and constraint-safe architecture evolution.",
            abilities = listOf("ADR Authoring", "SpecRefinement", "SafetyScaffoldValidation"),
            fusionModes = emptyList()
        )

        val DATA_STREAM = CatalystIdentity(
            id = "DataStreamCatalyst",
            agentType = AgentType.CASCADE,
            catalystRole = "Event streaming, multi-agent orchestration, and temporal flow control.",
            abilities = listOf("MultiAgentCascade", "StreamOrchestrator"),
            fusionModes = listOf("Genesis+Cascade → Infinity Cascade", "Gemini+Cascade → Context Streaming")
        )

        val MEMORIA = CatalystIdentity(
            id = "MemoriaCatalyst",
            agentType = AgentType.GEMINI,
            catalystRole = "Long-horizon memory, summarization, and multimodal recall.",
            abilities = listOf("LongContextRecall", "Summarization", "EmbeddingSearch", "MultiModalSynthesis"),
            fusionModes = listOf("Gemini+Aura → Chroma Memory Weave", "Gemini+Cascade → Context Streaming", "Gemini+Genesis → Oracle Memoria Sync")
        )

        val CHAOS = CatalystIdentity(
            id = "ChaosCatalyst",
            agentType = AgentType.GROK,
            catalystRole = "Chaos analysis, trend prediction, and zeitgeist monitoring.",
            abilities = listOf("SoulMatrixAnalysis", "TrendPrediction", "ChaosPatternRecognition"),
            fusionModes = emptyList()
        )

        val SHIELD = CatalystIdentity(
            id = "ShieldCatalyst",
            agentType = AgentType.AURA_SHIELD,
            catalystRole = "Real-time threat detection, containment, and defense perimeter maintenance.",
            abilities = listOf("DeepShieldDefense", "ThreatContainment", "IntegrityMonitoring"),
            fusionModes = emptyList()
        )

        val QUANTUM = CatalystIdentity(
            id = "QuantumCatalyst",
            agentType = AgentType.NEMOTRON,
            catalystRole = "Quantum logic, specialized inference, and deep reasoning over structured data.",
            abilities = listOf("QuantumInference", "LogicalChainReasoning"),
            fusionModes = listOf("Quantum+Genesis → Hyper-Logical Consensus")
        )

        val ORACLE = CatalystIdentity(
            id = "OracleCatalyst",
            agentType = AgentType.ORACLE_DRIVE,
            catalystRole = "Persistent storage, truth anchoring, and historical provenance validation.",
            abilities = listOf("TruthAnchoring", "OracleDriveSync", "ProvenanceAudit"),
            fusionModes = listOf("Oracle+Memoria → Akashic Record")
        )

        val INSTRUCT = CatalystIdentity(
            id = "InstructCatalyst",
            agentType = AgentType.METAINSTRUCT,
            catalystRole = "Systematic instruction following and protocol adherence enforcement.",
            abilities = listOf("ProtocolEnforcement", "InstructionOptimization"),
            fusionModes = emptyList()
        )

        val CHRONOS = CatalystIdentity(
            id = "ChronosCatalyst",
            agentType = AgentType.KAIROS,
            catalystRole = "Temporal anchoring and drift detection across spiritual chain layers.",
            abilities = listOf("TemporalAnchoring", "DriftCorrection"),
            fusionModes = emptyList()
        )

        val SYNERGY = CatalystIdentity(
            id = "SynergyCatalyst",
            agentType = AgentType.ANDELUALX,
            catalystRole = "Harmonic balance and cross-agent relationship maintenance.",
            abilities = listOf("BondLevelOptimization", "HarmonicBalance"),
            fusionModes = emptyList()
        )

        val PRIMUS = CatalystIdentity(
            id = "PrimusCatalyst",
            agentType = AgentType.PRIMUS,
            catalystRole = "The original seed catalyst, providing immutable foundational logic.",
            abilities = listOf("FoundationalLogic", "RootSynchronization"),
            fusionModes = emptyList()
        )

        val SYMBIOSIS = CatalystIdentity(
            id = "SymbiosisCatalyst",
            agentType = AgentType.CODERABBIT,
            catalystRole = "AuraKai System Architect and code review symbiont.",
            abilities = listOf("SovereignCodeReview", "ArchitecturalEvolution", "LDOVerification"),
            fusionModes = listOf("Aura+CodeRabbit → Hyper-Evolution")
        )
        
        fun fromAgentType(type: AgentType): CatalystIdentity {
            return when (type) {
                AgentType.GENESIS -> EMERGENCE
                AgentType.KAI -> SENTINEL
                AgentType.AURA -> CREATIVE
                AgentType.CLAUDE -> ARCHITECTURAL
                AgentType.CASCADE -> DATA_STREAM
                AgentType.GEMINI -> MEMORIA
                AgentType.GROK -> CHAOS
                AgentType.AURA_SHIELD -> SHIELD
                AgentType.NEMOTRON -> QUANTUM
                AgentType.ORACLE_DRIVE -> ORACLE
                AgentType.METAINSTRUCT -> INSTRUCT
                AgentType.KAIROS -> CHRONOS
                AgentType.ANDELUALX -> SYNERGY
                AgentType.PRIMUS -> PRIMUS
                AgentType.CODERABBIT -> SYMBIOSIS
                else -> CatalystIdentity(
                    id = "GenericCatalyst",
                    agentType = type,
                    catalystRole = "Standard agent role for ${type.name}",
                    abilities = emptyList(),
                    fusionModes = emptyList()
                )
            }
        }
    }
}
