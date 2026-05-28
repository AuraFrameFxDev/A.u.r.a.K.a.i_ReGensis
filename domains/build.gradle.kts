plugins {
    id("genesis.android.library")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "dev.aurakai.auraframefx.domains"
}

dependencies {
    api(project(":core-module"))
    implementation(project(":agents:growthmetrics:metareflection"))

    // Core dependencies
    api(libs.androidx.core.ktx)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose.ui)
    implementation(libs.kotlinx.serialization.json)

    // Networking (used by some domains like genesis/network)
    implementation(libs.bundles.networking.retrofit)
    implementation(libs.bundles.networking.ktor)

    // Room (some domains might have entities)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // Logging
    implementation(libs.timber)
}
