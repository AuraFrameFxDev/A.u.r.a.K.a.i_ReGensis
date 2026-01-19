/*
 * ═══════════════════════════════════════════════════════════════════════════════
 * ANDROID LIBRARY COMPOSE CONVENTION PLUGIN - Genesis Protocol
 * ═══════════════════════════════════════════════════════════════════════════════
 */

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("genesis.android.library")
            pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

            val extension = extensions.getByType<LibraryExtension>()
            configureAndroidCompose(extension)
        }
    }
}
