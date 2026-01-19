/*
 * ═══════════════════════════════════════════════════════════════════════════════
 * JVM LIBRARY CONVENTION PLUGIN - Genesis Protocol
 * Configuration for pure Kotlin/JVM modules (no Android dependencies)
 * ═══════════════════════════════════════════════════════════════════════════════
 */

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class JvmLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.jvm")
            }

            configureKotlinJvm()

            dependencies {
                add("testImplementation", libs.findLibrary("junit").get())
            }
        }
    }
}
