/*
 * ═══════════════════════════════════════════════════════════════════════════════
 * COMPOSE CONFIGURATION - Genesis Protocol
 * ═══════════════════════════════════════════════════════════════════════════════
 */

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Configure Compose-specific options
 */
internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        // Compose compiler is configured via the Kotlin Compose plugin
    }

    dependencies {
        val bom = libs.findLibrary("compose-bom").get()
        add("implementation", platform(bom))
        add("androidTestImplementation", platform(bom))

        add("implementation", libs.findBundle("compose").get())
        add("debugImplementation", libs.findBundle("compose-debug").get())
    }
}
