/*
 * ═══════════════════════════════════════════════════════════════════════════════
 * ANDROID LIBRARY CONVENTION PLUGIN - Genesis Protocol
 * Base configuration for all Android library modules
 * ═══════════════════════════════════════════════════════════════════════════════
 */

import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = GenesisVersions.TARGET_SDK

                // Disable unnecessary build types for faster builds
                buildTypes {
                    release {
                        isMinifyEnabled = false
                    }
                }

                // Configure test options
                testOptions {
                    unitTests {
                        isIncludeAndroidResources = true
                    }
                }
            }

            extensions.configure<LibraryAndroidComponentsExtension> {
                // Disable unused variants
                beforeVariants {
                    // Keep debug and release only
                }
            }

            dependencies {
                add("implementation", libs.findLibrary("androidx-core-ktx").get())
                add("testImplementation", libs.findLibrary("junit").get())
                add("androidTestImplementation", libs.findLibrary("androidx-junit").get())
            }
        }
    }
}
