/*
 * ═══════════════════════════════════════════════════════════════════════════════
 * 🤖 AGENTS NEXUS CONVENTION PLUGIN - Genesis Protocol
 * Configuration for all AGENTS memory/identity/progression modules
 * ═══════════════════════════════════════════════════════════════════════════════
 *
 * AGENTS is the Nexus - responsible for:
 * - 🧠 nexusmemory: Long-term Memory Vector DB
 * - 🔮 spheregrid: Skill Tree & Progression Logic
 * - 🪞 metareflection: Self-Improvement Logic
 * - 🆔 identity: Agent Persona Management
 * - 📈 progression: XP & Leveling System
 * - 🤖 tasker: Automated Agent Actions
 * ═══════════════════════════════════════════════════════════════════════════════
 */

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AgentsNexusConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("genesis.android.library")
                apply("genesis.android.hilt")
                apply("genesis.android.room")
                apply("org.jetbrains.kotlin.plugin.serialization")
            }

            extensions.configure<LibraryExtension> {
                namespace = "dev.genesisprotocol.agents.${project.name}"

                buildFeatures {
                    buildConfig = true
                }

                defaultConfig {
                    // AGENTS-specific build config fields
                    buildConfigField("String", "NEXUS_VERSION", "\"1.0.0\"")

                    // Progression system constants
                    buildConfigField("Int", "MAX_LEVEL", "100")
                    buildConfigField("Long", "BASE_XP_REQUIRED", "1000L")
                    buildConfigField("Float", "XP_GROWTH_FACTOR", "1.15f")
                }
            }

            dependencies {
                // Core
                add("implementation", project(":core:common"))
                add("implementation", project(":core:model"))
                add("implementation", project(":core:database"))

                // Room for persistent memory
                add("implementation", libs.findBundle("room").get())

                // Serialization for agent state
                add("implementation", libs.findLibrary("kotlinx-serialization").get())

                // DataStore for preferences
                add("implementation", libs.findLibrary("androidx-datastore").get())
            }
        }
    }
}
