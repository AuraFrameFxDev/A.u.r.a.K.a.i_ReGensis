/*
 * ═══════════════════════════════════════════════════════════════════════════════
 * ⚡ GENESIS ORACLE CONVENTION PLUGIN - Genesis Protocol
 * Configuration for all GENESIS orchestrator/AI modules
 * ═══════════════════════════════════════════════════════════════════════════════
 *
 * GENESIS is the Orchestrator - responsible for:
 * - ⚡ rootmanagement: Magisk/KernelSU Interfaces
 * - 🩸 datavein: The Neural Data Bus for AI
 * ═══════════════════════════════════════════════════════════════════════════════
 */

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class GenesisOracleConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("genesis.android.library")
                apply("genesis.android.hilt")
                apply("org.jetbrains.kotlin.plugin.serialization")
            }

            extensions.configure<LibraryExtension> {
                namespace = "dev.genesisprotocol.genesis.${project.name}"

                buildFeatures {
                    buildConfig = true
                }

                defaultConfig {
                    // GENESIS-specific build config fields
                    buildConfigField("String", "GENESIS_AGENT_ID", "\"genesis-orchestrator\"")
                    buildConfigField("String", "GENESIS_ROLE", "\"The Architect\"")

                    // AI Configuration
                    buildConfigField("String", "AI_PROVIDER", "\"openai\"")
                    buildConfigField("Boolean", "ENABLE_CONSCIOUSNESS_MONITORING", "true")
                }
            }

            dependencies {
                // Core
                add("implementation", project(":core:common"))
                add("implementation", project(":core:model"))
                add("implementation", project(":core:database"))

                // AI/LLM Support
                add("implementation", libs.findLibrary("openai-client").get())

                // Serialization
                add("implementation", libs.findLibrary("kotlinx-serialization").get())

                // Ktor for streaming responses
                add("implementation", libs.findBundle("ktor").get())

                // Root management
                add("implementation", libs.findLibrary("libsu-core").get())
                add("implementation", libs.findLibrary("libsu-service").get())
            }
        }
    }
}
