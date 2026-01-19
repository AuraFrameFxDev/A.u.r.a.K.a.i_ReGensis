/*
 * ═══════════════════════════════════════════════════════════════════════════════
 * 🎨 AURA:REACTIVEDESIGN:CHROMACORE - System Color Engine & Monet
 * ═══════════════════════════════════════════════════════════════════════════════
 * Agent: AURA (The Creative Catalyst)
 * Purpose: Dynamic color generation, Material You theming, palette management
 * ═══════════════════════════════════════════════════════════════════════════════
 */

plugins {
    alias(libs.plugins.genesis.aura.design)
}

android {
    namespace = "dev.genesisprotocol.aura.chromacore"
}

dependencies {
    // Parent module
    implementation(project(":aura:reactivedesign"))

    // Dynamic color extraction
    implementation(libs.compose.material3)

    // Color utilities
    // implementation(libs.palette) // If using AndroidX Palette
}
