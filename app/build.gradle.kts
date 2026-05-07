plugins {
    id("genesis.android.application")
    id("kotlin-parcelize")
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
}

extensions.configure<com.android.build.api.dsl.ApplicationExtension> {
    namespace = "dev.aurakai.auraframefx"
    compileSdk = 37

    defaultConfig {
        applicationId = "dev.aurakai.auraframefx"
        minSdk = 34
        versionCode = 1
        versionName = "1.0.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "OLLAMA_BASE_URL", "\"http://localhost:11434\"")
        buildConfigField("String", "GENESIS_BACKEND_URL", "\"https://api.genesis.local\"")
        buildConfigField("String", "GEMINI_API_KEY", "\"AIzaSyDidYYvUTxJzATK9Zmee-gBievXUUVhDwc\"")
        buildConfigField("String", "GROK_API_KEY", "\"\"")
        buildConfigField("boolean", "ENABLE_GEMINI", "true")
        buildConfigField("String", "VERTEX_PROJECT_ID", "\"auraframefx\"")
        buildConfigField("String", "VERTEX_LOCATION", "\"us-central1\"")
        buildConfigField("String", "GEMINI_MODEL", "\"gemini-2.0-flash-exp\"")
        buildConfigField("String", "OAUTH_SERVER_CLIENT_ID", "\"35417750637-4m0mong9mjselgr4milhc4mamu5706nu.apps.googleusercontent.com\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Core LDO Infrastructure Modules
    implementation(project(":core-module"))
    implementation(project(":aura"))
    implementation(project(":aura:reactivedesign:collabcanvas"))
    implementation(project(":aura:reactivedesign:chromacore"))
    implementation(project(":genesis"))
    implementation(project(":genesis:oracledrive:rootmanagement"))
    implementation(project(":kai:sentinelsfortress"))
    implementation(project(":kai:sentinelsfortress:security"))
    implementation(project(":agents:growthmetrics:nexusmemory"))
    implementation(project(":agents:growthmetrics:metareflection"))
    implementation(project(":extendsysa"))

    // UI / Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose.ui)
    implementation(libs.bundles.compose.tooling)
    implementation(libs.bundles.androidx.core)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)

    // Coil 3 for image loading
    implementation(libs.bundles.utilities)
    implementation(libs.coil.svg)

    // Networking & Serialization (required by DI modules)
    implementation(libs.bundles.networking.retrofit)
    implementation(libs.bundles.networking.ktor)
    implementation(libs.bundles.bundle.kotlinx)
    implementation(libs.firebase.messaging)
    implementation(libs.gson)
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)
    implementation(libs.retrofit.converter.moshi)
    implementation(libs.retrofit.converter.scalars)
    implementation(libs.retrofit.converter.kotlinx.serialization)
    implementation(libs.retrofit.converter.gson)

    // Room database
    implementation(libs.bundles.room)
    ksp(libs.androidx.room.compiler)

    // Billing
    implementation(libs.billing.ktx)

    // DataStore & WorkManager (required by DI modules)
    implementation(libs.bundles.datastore)
    implementation(libs.androidx.hilt.work)
    implementation(libs.androidx.work.runtime.ktx)

    // Security Crypto
    implementation(libs.androidx.security.crypto)

    // Credentials
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Firebase
    implementation(libs.bundles.firebase)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.common)

    // System Sovereignty
    implementation(libs.yukihookapi.api)
    ksp(libs.yukihookapi.ksp)
    implementation(libs.kavaref.core)
    implementation(libs.libsu.core)
    implementation(libs.shizuku.api)
    implementation(libs.shizuku.provider)
    compileOnly(libs.xposed.api)

    // Logging
    implementation(libs.timber)

    // LangChain4j
    implementation(libs.langchain4j.core)
    implementation(libs.langchain4j.ollama)
}
