/*
 * ═══════════════════════════════════════════════════════════════════════════════
 * 🌊 CASCADE NETWORK CONVENTION PLUGIN - Genesis Protocol
 * Configuration for all CASCADE data routing/networking modules
 * ═══════════════════════════════════════════════════════════════════════════════
 *
 * CASCADE is the Stream - responsible for:
 * - 🛣️ routing: API Endpoints & Retrofit
 * - 🚚 delivery: Data Serialization & Moshi
 * - 📋 taskmanager: Background Work & Scheduling
 * ═══════════════════════════════════════════════════════════════════════════════
 */

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class CascadeNetworkConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("genesis.android.library")
                apply("genesis.android.hilt")
                apply("com.google.devtools.ksp")
            }

            extensions.configure<LibraryExtension> {
                namespace = "dev.genesisprotocol.cascade.${project.name}"

                buildFeatures {
                    buildConfig = true
                }

                defaultConfig {
                    // CASCADE-specific build config fields
                    buildConfigField("String", "CASCADE_AGENT_ID", "\"cascade-memory-keeper\"")
                    buildConfigField("String", "CASCADE_ROLE", "\"The Stream\"")

                    // Network configuration
                    buildConfigField("Long", "DEFAULT_TIMEOUT_MS", "30000L")
                    buildConfigField("Boolean", "ENABLE_NETWORK_LOGGING", "true")
                }
            }

            dependencies {
                // Core
                add("implementation", project(":core:common"))
                add("implementation", project(":core:network"))
                add("implementation", project(":core:model"))

                // Networking
                add("implementation", libs.findBundle("networking").get())

                // Moshi codegen
                add("ksp", libs.findLibrary("moshi-kotlin").get())

                // WorkManager for background tasks
                add("implementation", libs.findLibrary("androidx-workmanager").get())
                add("implementation", libs.findLibrary("hilt-work").get())
                add("ksp", libs.findLibrary("hilt-work-compiler").get())
            }
        }
    }
}
