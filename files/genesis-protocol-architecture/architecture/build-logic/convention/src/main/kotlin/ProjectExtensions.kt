/*
 * ═══════════════════════════════════════════════════════════════════════════════
 * PROJECT EXTENSIONS - Genesis Protocol
 * Utility extensions for accessing version catalog in convention plugins
 * ═══════════════════════════════════════════════════════════════════════════════
 */

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

/**
 * Access the libs version catalog from the project
 */
internal val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")
