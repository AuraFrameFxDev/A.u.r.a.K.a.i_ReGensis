/*
 * ═══════════════════════════════════════════════════════════════════════════════
 * ANDROID ROOM CONVENTION PLUGIN - Genesis Protocol
 * ═══════════════════════════════════════════════════════════════════════════════
 */

import androidx.room.gradle.RoomExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidRoomConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("androidx.room")
                apply("com.google.devtools.ksp")
            }

            extensions.configure<RoomExtension> {
                // The schemas directory contains a schema file for each version of the Room database.
                // This is required to enable Room auto migrations.
                schemaDirectory("$projectDir/schemas")
            }

            dependencies {
                add("implementation", libs.findBundle("room").get())
                add("ksp", libs.findLibrary("room-compiler").get())
            }
        }
    }
}
