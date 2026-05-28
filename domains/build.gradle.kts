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
    api(project(":core"))
    implementation(project(":agents:growthmetrics:metareflection"))

    // UI & Navigation
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose.ui)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)

    // Image Loading
    implementation(libs.coil.compose)
    implementation(libs.coil.svg)

    // Root & Hooks
    implementation(libs.libsu.core)
    implementation(libs.yukihookapi.api)
    implementation(libs.kavaref.core)
    implementation(libs.kavaref.extension)

    // Serialization

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
