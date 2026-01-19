/*
 * ═══════════════════════════════════════════════════════════════════════════════
 * KOTLIN ANDROID CONFIGURATION - Genesis Protocol
 * Shared Kotlin/Android configuration for all modules
 * ═══════════════════════════════════════════════════════════════════════════════
 */

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.provideDelegate
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinTopLevelExtension

/**
 * Configure base Kotlin options for JVM (non-Android) modules
 */
internal fun Project.configureKotlinJvm() {
    extensions.configure<JavaPluginExtension> {
        sourceCompatibility = GenesisVersions.JAVA_VERSION
        targetCompatibility = GenesisVersions.JAVA_VERSION
    }

    configureKotlin<KotlinJvmProjectExtension>()
}

/**
 * Configure base Kotlin options for Android modules
 */
internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        compileSdk = GenesisVersions.COMPILE_SDK

        defaultConfig {
            minSdk = GenesisVersions.MIN_SDK
        }

        compileOptions {
            sourceCompatibility = GenesisVersions.JAVA_VERSION
            targetCompatibility = GenesisVersions.JAVA_VERSION
            isCoreLibraryDesugaringEnabled = true
        }
    }

    configureKotlin<KotlinAndroidProjectExtension>()

    dependencies {
        add("coreLibraryDesugaring", libs.findLibrary("android-desugar-jdk-libs").get())
    }
}

/**
 * Configure Kotlin for both JVM and Android
 */
private inline fun <reified T : KotlinTopLevelExtension> Project.configureKotlin() = configure<T> {
    // Enable explicit API mode for libraries
    val isLibrary = project.pluginManager.hasPlugin("com.android.library") ||
        project.pluginManager.hasPlugin("org.jetbrains.kotlin.jvm")

    if (isLibrary) {
        explicitApi()
    }

    when (this) {
        is KotlinAndroidProjectExtension -> compilerOptions
        is KotlinJvmProjectExtension -> compilerOptions
        else -> error("Unsupported Kotlin extension: ${this::class}")
    }.apply {
        jvmTarget.set(GenesisVersions.JVM_TARGET)

        // Enable context receivers for cleaner DSL syntax
        freeCompilerArgs.addAll(
            "-Xcontext-receivers",
            "-opt-in=kotlin.RequiresOptIn",
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-opt-in=kotlinx.coroutines.FlowPreview",
        )
    }
}

/**
 * Genesis Protocol version constants
 */
internal object GenesisVersions {
    const val COMPILE_SDK = 35
    const val TARGET_SDK = 35
    const val MIN_SDK = 26

    val JAVA_VERSION = org.gradle.api.JavaVersion.VERSION_24
    val JVM_TARGET = JvmTarget.JVM_24
}
