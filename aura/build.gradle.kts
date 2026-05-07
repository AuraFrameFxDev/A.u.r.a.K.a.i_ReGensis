plugins {
    id("genesis.android.library")
}

extensions.configure<com.android.build.api.dsl.LibraryExtension> {
    namespace = "dev.aurakai.auraframefx.aura"
}

dependencies {
    // Shared Domain & Data
    implementation(project(":core-module"))

    // UI & Compose Stack
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose.ui)

    // Utilities (Coil, Timber)
    implementation(libs.bundles.utilities)

    // AI & System Hooks (Auto-provided by genesis.android.library, but can be explicitly refined here)
    // implementation(libs.bundles.ai-core)
}
