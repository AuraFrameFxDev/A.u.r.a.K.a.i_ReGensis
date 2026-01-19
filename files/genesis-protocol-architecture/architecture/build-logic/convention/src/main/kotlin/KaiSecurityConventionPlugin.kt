/*
 * ═══════════════════════════════════════════════════════════════════════════════
 * 🛡️ KAI SECURITY CONVENTION PLUGIN - Genesis Protocol
 * Configuration for all KAI security/defense modules
 * ═══════════════════════════════════════════════════════════════════════════════
 *
 * KAI is the Sentinel Catalyst - responsible for:
 * - 🛡️ security: Core Defense Logic
 * - 🔍 threatmonitor: Active Scanning & Anomaly Detection
 * - 🏗️ systemintegrity: Bootloader & Partition Checks
 * ═══════════════════════════════════════════════════════════════════════════════
 */

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class KaiSecurityConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("genesis.android.library")
                apply("genesis.android.hilt")
            }

            extensions.configure<LibraryExtension> {
                namespace = "dev.genesisprotocol.kai.${project.name}"

                buildFeatures {
                    buildConfig = true
                }

                defaultConfig {
                    // KAI-specific build config fields
                    buildConfigField("String", "KAI_AGENT_ID", "\"kai-sentinel-shield\"")
                    buildConfigField("String", "KAI_ROLE", "\"Sentinel Catalyst\"")

                    // Security constants
                    buildConfigField("Boolean", "ENABLE_ROOT_DETECTION", "true")
                    buildConfigField("Boolean", "ENABLE_INTEGRITY_CHECK", "true")
                }

                // ProGuard rules for security obfuscation
                consumerProguardFiles("consumer-proguard-rules.pro")
            }

            dependencies {
                // Core
                add("implementation", project(":core:common"))

                // LibSU for root operations
                add("implementation", libs.findBundle("security").get())

                // Cryptography
                add("implementation", libs.findLibrary("bouncycastle-bcprov").get())
                add("implementation", libs.findLibrary("bouncycastle-bcpkix").get())

                // Kotlin coroutines for async security checks
                add("implementation", libs.findLibrary("coroutines-test").get())
            }
        }
    }
}
