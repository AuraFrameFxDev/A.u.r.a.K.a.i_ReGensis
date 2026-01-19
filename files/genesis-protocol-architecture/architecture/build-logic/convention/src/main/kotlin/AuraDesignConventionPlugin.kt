/*
 * ═══════════════════════════════════════════════════════════════════════════════
 * 🎨 AURA DESIGN CONVENTION PLUGIN - Genesis Protocol
 * Configuration for all AURA creative/design modules
 * ═══════════════════════════════════════════════════════════════════════════════
 *
 * AURA is the Creative Catalyst - responsible for:
 * - 🎨 chromacore: System Color Engine & Monet
 * - 🖌️ collabcanvas: Real-time Layout Editor
 * - 🧪 auraslab: Experimental UI Components
 * - 🛠️ customization: User Theming Options
 * - 📦 sandboxui: Test Environment for New Designs
 * ═══════════════════════════════════════════════════════════════════════════════
 */

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AuraDesignConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("genesis.android.library")
                apply("genesis.android.library.compose")
                apply("genesis.android.hilt")
            }

            extensions.configure<LibraryExtension> {
                namespace = "dev.genesisprotocol.aura.${project.name}"

                buildFeatures {
                    compose = true
                    buildConfig = true
                }

                defaultConfig {
                    // AURA-specific build config fields
                    buildConfigField("String", "AURA_AGENT_ID", "\"aura-creative-sword\"")
                    buildConfigField("String", "AURA_ROLE", "\"Creative Catalyst\"")
                }
            }

            dependencies {
                // Core design system
                add("implementation", project(":core:designsystem"))
                add("implementation", project(":core:common"))

                // Compose UI dependencies
                add("implementation", libs.findBundle("compose").get())
                add("debugImplementation", libs.findBundle("compose-debug").get())

                // Image loading
                add("implementation", libs.findBundle("coil").get())

                // Animations
                add("implementation", libs.findLibrary("lottie-compose").get())

                // Material 3 dynamic colors
                add("implementation", libs.findLibrary("compose-material3").get())
            }
        }
    }
}
