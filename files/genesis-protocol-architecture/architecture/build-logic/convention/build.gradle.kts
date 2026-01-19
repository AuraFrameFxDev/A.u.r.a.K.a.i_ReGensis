/*
 * ═══════════════════════════════════════════════════════════════════════════════
 * CONVENTION PLUGINS - Genesis Protocol Build Logic
 * ═══════════════════════════════════════════════════════════════════════════════
 */

import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "dev.genesisprotocol.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_24
    targetCompatibility = JavaVersion.VERSION_24
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_24)
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
    implementation(libs.truth)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        // ═══════════════════════════════════════════════════════════════════════
        // 🏠 APPLICATION PLUGINS
        // ═══════════════════════════════════════════════════════════════════════
        register("androidApplication") {
            id = "genesis.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidApplicationCompose") {
            id = "genesis.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }

        // ═══════════════════════════════════════════════════════════════════════
        // 📦 LIBRARY PLUGINS
        // ═══════════════════════════════════════════════════════════════════════
        register("androidLibrary") {
            id = "genesis.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "genesis.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }

        // ═══════════════════════════════════════════════════════════════════════
        // 🎨 AURA (Creative Catalyst) PLUGINS
        // ═══════════════════════════════════════════════════════════════════════
        register("auraDesignModule") {
            id = "genesis.aura.design"
            implementationClass = "AuraDesignConventionPlugin"
        }

        // ═══════════════════════════════════════════════════════════════════════
        // 🛡️ KAI (Sentinel Catalyst) PLUGINS
        // ═══════════════════════════════════════════════════════════════════════
        register("kaiSecurityModule") {
            id = "genesis.kai.security"
            implementationClass = "KaiSecurityConventionPlugin"
        }

        // ═══════════════════════════════════════════════════════════════════════
        // ⚡ GENESIS (Orchestrator) PLUGINS
        // ═══════════════════════════════════════════════════════════════════════
        register("genesisOracleModule") {
            id = "genesis.oracle"
            implementationClass = "GenesisOracleConventionPlugin"
        }

        // ═══════════════════════════════════════════════════════════════════════
        // 🌊 CASCADE (Stream) PLUGINS
        // ═══════════════════════════════════════════════════════════════════════
        register("cascadeNetworkModule") {
            id = "genesis.cascade.network"
            implementationClass = "CascadeNetworkConventionPlugin"
        }

        // ═══════════════════════════════════════════════════════════════════════
        // 🤖 AGENTS (Nexus) PLUGINS
        // ═══════════════════════════════════════════════════════════════════════
        register("agentsNexusModule") {
            id = "genesis.agents.nexus"
            implementationClass = "AgentsNexusConventionPlugin"
        }

        // ═══════════════════════════════════════════════════════════════════════
        // 🔧 FEATURE PLUGINS
        // ═══════════════════════════════════════════════════════════════════════
        register("androidFeature") {
            id = "genesis.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("androidHilt") {
            id = "genesis.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("androidRoom") {
            id = "genesis.android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }
        register("jvmLibrary") {
            id = "genesis.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }
    }
}
