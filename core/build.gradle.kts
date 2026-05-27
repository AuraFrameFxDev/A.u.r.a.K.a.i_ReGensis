plugins {
    id("genesis.android.library")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "dev.aurakai.auraframefx.core"
}

dependencies {
    implementation(project(":core-module"))

    // Core dependencies
    api(libs.androidx.core.ktx)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose.ui)
    implementation(libs.kotlinx.serialization.json)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Logging
    implementation(libs.timber)

    // Shizuku & Root tools (often used in core)
    implementation(libs.shizuku.api)
    implementation(libs.libsu.core)
}
