package dev.aurakai.auraframefx.core.ldo.model

sealed class ReGenesisRoute(val route: String, val title: String) {
    data object Login : ReGenesisRoute("login", "SYSTEM LOGIN")
    data object Onboarding : ReGenesisRoute("onboarding", "INITIALIZATION")

    // 7-Hub Command Deck
    data object NeuralNexus : ReGenesisRoute("neural_nexus", "NEURAL NEXUS")
    data object LdoDevops : ReGenesisRoute("ldo_devops", "LDO DEVELOPMENT NEXUS")
    data object ChromaForge : ReGenesisRoute("chroma_forge", "CHROMA FORGE") // Aura's domain
    data object SentinelMatrix : ReGenesisRoute("sentinel_matrix", "SENTINEL MATRIX")
    data object OracleDrive : ReGenesisRoute("oracle_drive", "ORACLEDRIVE")
    data object EmergentSwarm : ReGenesisRoute("emergent_swarm", "EMERGENT SWARM")
    data object MasterStatusStrip : ReGenesisRoute("master_status_strip", "MASTER STATUS")

    // SEALED SUPERTOOLS
    data object LdoDebugRoom : ReGenesisRoute("ldo_debug_room", "LDO DEBUG ROOM")
    data object RealityMatrix : ReGenesisRoute("reality_matrix", "REALITY MATRIX") // inner sanctum

    companion object {
        fun titleForRoute(route: String?): String =
            entries.find { it.route == route }?.title ?: "AuraKai ReGenesis"

        val entries: List<ReGenesisRoute> = listOf(
            Login, Onboarding, NeuralNexus, LdoDevops, ChromaForge,
            SentinelMatrix, OracleDrive, EmergentSwarm, MasterStatusStrip,
            LdoDebugRoom, RealityMatrix
        )
    }
}
