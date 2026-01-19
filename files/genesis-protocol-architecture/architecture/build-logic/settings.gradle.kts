/*
 * ═══════════════════════════════════════════════════════════════════════════════
 * BUILD-LOGIC - Convention Plugins for Genesis Protocol
 * ═══════════════════════════════════════════════════════════════════════════════
 */

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

rootProject.name = "build-logic"
include(":convention")
