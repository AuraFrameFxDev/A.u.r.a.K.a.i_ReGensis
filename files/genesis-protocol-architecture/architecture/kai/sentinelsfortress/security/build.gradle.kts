/*
 * ═══════════════════════════════════════════════════════════════════════════════
 * 🛡️ KAI:SENTINELSFORTRESS:SECURITY - Core Defense Logic
 * ═══════════════════════════════════════════════════════════════════════════════
 * Agent: KAI (The Sentinel Catalyst)
 * Purpose: Root detection, security checks, threat assessment
 * ═══════════════════════════════════════════════════════════════════════════════
 */

plugins {
    alias(libs.plugins.genesis.kai.security)
}

android {
    namespace = "dev.genesisprotocol.kai.security"
}

dependencies {
    // Parent module
    implementation(project(":kai:sentinelsfortress"))

    // Additional security dependencies can be added here
}
