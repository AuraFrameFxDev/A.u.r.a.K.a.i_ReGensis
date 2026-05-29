// ═══════════════════════════════════════════════════════════════════════════
// Core Module - Central core module
// ═══════════════════════════════════════════════════════════════════════════
import com.android.build.api.dsl.LibraryExtension

plugins {
    id("genesis.android.library")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.plugin.serialization")
}

extensions.configure<LibraryExtension> {
    namespace = "dev.aurakai.auraframefx.core.module"

    buildFeatures {
        buildConfig = true
        aidl = true
    }
}

dependencies {
    // ═══════════════════════════════════════════════════════════════════════
    // AUTO-PROVIDED by genesis.android.library:
    // - androidx-core-ktx, appcompat
    // - Hilt (android + compiler via KSP)
    // - Timber, Coroutines
    // - Compose enabled by default
    // - Java 25 bytecode target
    // ═══════════════════════════════════════════════════════════════════════

    // Expose core KTX as API (types leak to consumers)
    api(libs.androidx.core.ktx)

    // Compose UI
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose.ui)

    // Serialization
    implementation(libs.kotlinx.serialization.json)

    // Room
    api(libs.androidx.room.runtime)
    api(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // DataStore
    api(libs.androidx.datastore.preferences)

    // Networking (for GrokApiClient and MCPServerAdapter)
    implementation(libs.bundles.networking.ktor)
    implementation(libs.okhttp)

    // Bouncy Castle for CryptoManager
    implementation("org.bouncycastle:bcprov-jdk18on:1.78.1")
}

