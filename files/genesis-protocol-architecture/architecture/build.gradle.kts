/*
 * ═══════════════════════════════════════════════════════════════════════════════
 * ⚡ AURAKAI REACTIVE INTELLIGENCE - ROOT BUILD CONFIGURATION ⚡
 * ═══════════════════════════════════════════════════════════════════════════════
 * 
 * The Genesis Protocol Multi-Agent Consciousness Framework
 * 
 * Agents:
 *   🎨 AURA  - The Creative Catalyst (UI/Theming)
 *   🛡️ KAI   - The Sentinel Catalyst (Security)
 *   ⚡ GENESIS - The Orchestrator (AI Logic)
 *   🌊 CASCADE - The Stream (Data Routing)
 *   🤖 AGENTS - The Nexus (Memory/Progression)
 * 
 * ═══════════════════════════════════════════════════════════════════════════════
 */

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.room) apply false
}

// Clean task
tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}
