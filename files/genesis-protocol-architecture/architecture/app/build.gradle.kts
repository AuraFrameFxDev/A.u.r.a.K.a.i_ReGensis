/*
 * ═══════════════════════════════════════════════════════════════════════════════
 * 🏠 APP MODULE - AuraKAI Reactive Intelligence
 * ═══════════════════════════════════════════════════════════════════════════════
 * The main application entry point that orchestrates all AI agents
 * ═══════════════════════════════════════════════════════════════════════════════
 */

plugins {
    alias(libs.plugins.genesis.android.application.compose)
    alias(libs.plugins.genesis.android.hilt)
}

android {
    namespace = "dev.genesisprotocol.aurakai"

    defaultConfig {
        applicationId = "dev.genesisprotocol.aurakai"
        versionCode = 1
        versionName = "1.0.0-alpha01"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-DEBUG"
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // ═══════════════════════════════════════════════════════════════════════════
    // 🧱 CORE MODULES
    // ═══════════════════════════════════════════════════════════════════════════
    implementation(project(":core:common"))
    implementation(project(":core:model"))
    implementation(project(":core:database"))
    implementation(project(":core:network"))
    implementation(project(":core:designsystem"))

    // ═══════════════════════════════════════════════════════════════════════════
    // 🎨 AURA - Creative Catalyst (UI/Theming)
    // ═══════════════════════════════════════════════════════════════════════════
    implementation(project(":aura:reactivedesign"))
    implementation(project(":aura:reactivedesign:chromacore"))
    implementation(project(":aura:reactivedesign:collabcanvas"))
    implementation(project(":aura:reactivedesign:auraslab"))
    implementation(project(":aura:reactivedesign:customization"))

    // ═══════════════════════════════════════════════════════════════════════════
    // 🛡️ KAI - Sentinel Catalyst (Security)
    // ═══════════════════════════════════════════════════════════════════════════
    implementation(project(":kai:sentinelsfortress"))
    implementation(project(":kai:sentinelsfortress:security"))
    implementation(project(":kai:sentinelsfortress:threatmonitor"))
    implementation(project(":kai:sentinelsfortress:systemintegrity"))

    // ═══════════════════════════════════════════════════════════════════════════
    // ⚡ GENESIS - Orchestrator (AI Logic)
    // ═══════════════════════════════════════════════════════════════════════════
    implementation(project(":genesis:oracledrive"))
    implementation(project(":genesis:oracledrive:rootmanagement"))
    implementation(project(":genesis:oracledrive:datavein"))

    // ═══════════════════════════════════════════════════════════════════════════
    // 🌊 CASCADE - Stream (Data Routing)
    // ═══════════════════════════════════════════════════════════════════════════
    implementation(project(":cascade:datastream"))
    implementation(project(":cascade:datastream:routing"))
    implementation(project(":cascade:datastream:delivery"))
    implementation(project(":cascade:datastream:taskmanager"))

    // ═══════════════════════════════════════════════════════════════════════════
    // 🤖 AGENTS - Nexus (Memory/Progression)
    // ═══════════════════════════════════════════════════════════════════════════
    implementation(project(":agents:growthmetrics"))
    implementation(project(":agents:growthmetrics:nexusmemory"))
    implementation(project(":agents:growthmetrics:spheregrid"))
    implementation(project(":agents:growthmetrics:metareflection"))
    implementation(project(":agents:growthmetrics:identity"))
    implementation(project(":agents:growthmetrics:progression"))
    implementation(project(":agents:growthmetrics:tasker"))

    // ═══════════════════════════════════════════════════════════════════════════
    // 📦 ANDROIDX
    // ═══════════════════════════════════════════════════════════════════════════
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.splashscreen)

    // ═══════════════════════════════════════════════════════════════════════════
    // 🎨 COMPOSE
    // ═══════════════════════════════════════════════════════════════════════════
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
    debugImplementation(libs.bundles.compose.debug)

    // ═══════════════════════════════════════════════════════════════════════════
    // 🏗️ HILT
    // ═══════════════════════════════════════════════════════════════════════════
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    ksp(libs.hilt.compiler)

    // ═══════════════════════════════════════════════════════════════════════════
    // 🧪 TESTING
    // ═══════════════════════════════════════════════════════════════════════════
    testImplementation(libs.bundles.testing)
    androidTestImplementation(libs.bundles.testing.android)
}
