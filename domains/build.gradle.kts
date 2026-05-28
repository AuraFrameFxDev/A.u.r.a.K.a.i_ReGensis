import java.util.Properties

plugins {
    id("genesis.android.library")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "dev.aurakai.auraframefx.domains"

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        val localProps = Properties()
        val localPropertiesFile = rootProject.file("local.properties")
        if (localPropertiesFile.exists()) {
            localPropertiesFile.inputStream().use { localProps.load(it) }
        }

        buildConfigField("String", "OLLAMA_BASE_URL", "\"http://localhost:11434\"")
        buildConfigField("String", "GENESIS_BACKEND_URL", "\"https://api.genesis.local\"")
        buildConfigField(
            "String",
            "GEMINI_API_KEY",
            "\"${
                localProps.getProperty(
                    "GEMINI_API_KEY",
                    "AIzaSyDidYYvUTxJzATK9Zmee-gBievXUUVhDwc"
                )
            }\""
        )
        buildConfigField("String", "GROK_API_KEY", "\"\"")
        buildConfigField("boolean", "ENABLE_GEMINI", "true")
        buildConfigField("String", "VERTEX_PROJECT_ID", "\"auraframefx\"")
        buildConfigField("String", "VERTEX_LOCATION", "\"us-central1\"")
        buildConfigField("String", "GEMINI_MODEL", "\"gemini-2.0-flash-exp\"")
        buildConfigField(
            "String",
            "OAUTH_SERVER_CLIENT_ID",
            "\"${
                localProps.getProperty(
                    "OAUTH_SERVER_CLIENT_ID",
                    "35417750637-4m0mong9mjselgr4milhc4mamu5706nu.apps.googleusercontent.com"
                )
            }\""
        )
    }
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

    // Credentials & Identity
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)

    // Billing
    implementation(libs.billing.ktx)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.storage)
    implementation(libs.firebase.config)

    // Security & Serialization
    implementation(libs.androidx.security.crypto)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.play.services.coroutines)

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
