import com.android.build.api.dsl.LibraryExtension

plugins {
    id("genesis.android.library.hilt")
    kotlin("plugin.serialization")
}

extensions.configure<LibraryExtension> {
    namespace = "dev.aurakai.auraframefx.genesis.oracledrive.rootmanagement"

    testOptions {
        unitTests.isIncludeAndroidResources = false
    }
}

dependencies {
    // Core module (provides NexusMemoryCore, PandoraBoxService) - use api for KSP visibility
    api(project(":core-module"))

    // Core Android - Expose as API
    api(libs.androidx.core.ktx)

    // Compose BOM and UI
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose.ui)
    implementation(libs.compose.material.icons.extended)
    implementation(libs.bundles.compose.tooling)

    // Testing & AI Integrations
    implementation(libs.androidx.junit.ktx)
    implementation(libs.androidx.compose.ui.test.junit4)
    implementation(libs.firebase.vertexai)

    // Media Integrations
    implementation(libs.androidx.media3.exoplayer)

    // Compose / Lifecycle / Navigation / Hilt integrations (Extension modules)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.work.runtime.ktx)

    // Root/System Operations
    implementation(libs.libsu.core)
    implementation(libs.libsu.nio)
    implementation(libs.libsu.service)

    // Kotlinx Serialization
    implementation(libs.kotlinx.serialization.json)

    // Unit Test dependencies
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
}

ksp {
    arg("yukihookapi.modulePackageName", "dev.aurakai.auraframefx.genesis.oracledrive.rootmanagement")
}
