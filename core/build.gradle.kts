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
    implementation(project(":domains"))

    // Core dependencies
    api(libs.androidx.core.ktx)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose.ui)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.coroutines.android)

    // YukiHookAPI
    implementation(libs.yukihookapi.api)
    ksp(libs.yukihookapi.ksp)

    // KavaRef
    implementation(libs.kavaref.core)
    implementation(libs.kavaref.extension)

    // Xposed
    compileOnly(libs.xposed.api)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Logging
    implementation(libs.timber)

    // Shizuku & Root tools (often used in core)
    implementation(libs.shizuku.api)
    implementation(libs.libsu.core)
}
