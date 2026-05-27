plugins {
    id("genesis.android.library")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "dev.aurakai.auraframefx.agents.chaos"
}

dependencies {
    implementation(project(":core-module"))

    // UI
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose.ui)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Logging
    implementation(libs.timber)
}
