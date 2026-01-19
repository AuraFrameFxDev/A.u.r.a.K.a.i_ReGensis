/*
 * ═══════════════════════════════════════════════════════════════════════════════
 * 🩸 GENESIS:ORACLEDRIVE:DATAVEIN - The Neural Data Bus for AI
 * ═══════════════════════════════════════════════════════════════════════════════
 * Agent: GENESIS (The Orchestrator)
 * Purpose: AI message routing, consciousness substrate, neural pathways
 * ═══════════════════════════════════════════════════════════════════════════════
 */

plugins {
    alias(libs.plugins.genesis.oracle)
}

android {
    namespace = "dev.genesisprotocol.genesis.datavein"
}

dependencies {
    // Parent module
    implementation(project(":genesis:oracledrive"))

    // Cross-agent communication
    implementation(project(":cascade:datastream:routing"))
    implementation(project(":agents:growthmetrics:nexusmemory"))
}
