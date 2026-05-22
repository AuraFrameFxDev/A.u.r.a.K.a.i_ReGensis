package dev.aurakai.auraframefx

/*
 * Tests validating the gradle/libs.versions.toml changes introduced in THIS pull request:
 *
 * Changes tested:
 *  1. Removal of `mediapipe = "0.10.35"` from [versions]
 *  2. Removal of `litert = "0.12.0"` from [versions]
 *  3. Removal of `mediapipe-tasks-genai` library entry from [libraries]
 *  4. Removal of `litert-lm` library entry from [libraries]
 *
 * Framework: JUnit 5 (Jupiter)
 * File parsing: reads gradle/libs.versions.toml as raw text and validates with string/regex checks.
 */

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.io.File

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("gradle/libs.versions.toml — LiteRT/MediaPipe PR Changes")
class VersionCatalogLiteRtPrChangesTest {

    private fun locateCatalog(): File {
        val candidates = listOf(
            File("gradle/libs.versions.toml"),
            File("../gradle/libs.versions.toml")
        )
        return candidates.firstOrNull { it.exists() } ?: error(
            "Unable to locate gradle/libs.versions.toml. Checked: ${candidates.joinToString { it.path }}"
        )
    }

    private val catalogFile: File by lazy { locateCatalog() }
    private val catalog: String by lazy { catalogFile.readText() }

    // ─── Version entries removed ──────────────────────────────────────────────

    @Nested
    @DisplayName("Removed version entries")
    inner class RemovedVersionsTests {

        @Test
        @DisplayName("mediapipe version declaration is absent")
        fun mediapipeVersionAbsent() {
            assertFalse(
                Regex("""mediapipe\s*=\s*"[^"]+"""").containsMatchIn(catalog),
                "mediapipe version entry should have been removed from [versions] in this PR"
            )
        }

        @Test
        @DisplayName("mediapipe = \"0.10.35\" is not present")
        fun mediapipe01035VersionAbsent() {
            assertFalse(
                catalog.contains("mediapipe = \"0.10.35\""),
                "mediapipe = \"0.10.35\" should have been deleted in this PR"
            )
        }

        @Test
        @DisplayName("litert version declaration is absent")
        fun litertVersionAbsent() {
            assertFalse(
                Regex("""(?<!\w)litert\s*=\s*"[^"]+"""").containsMatchIn(catalog),
                "litert version entry should have been removed from [versions] in this PR"
            )
        }

        @Test
        @DisplayName("litert = \"0.12.0\" is not present")
        fun litert0120VersionAbsent() {
            assertFalse(
                catalog.contains("litert = \"0.12.0\""),
                "litert = \"0.12.0\" should have been deleted in this PR"
            )
        }
    }

    // ─── Library entries removed ──────────────────────────────────────────────

    @Nested
    @DisplayName("Removed library entries")
    inner class RemovedLibrariesTests {

        @Test
        @DisplayName("mediapipe-tasks-genai library entry is absent")
        fun mediapipeTasksGenaiLibraryAbsent() {
            assertFalse(
                Regex("""mediapipe-tasks-genai\s*=\s*\{""").containsMatchIn(catalog),
                "mediapipe-tasks-genai library declaration should have been removed in this PR"
            )
        }

        @Test
        @DisplayName("No reference to com.google.mediapipe:tasks-genai remains")
        fun noMediapipeTasksGenaiModuleReference() {
            assertFalse(
                catalog.contains("tasks-genai"),
                "No tasks-genai module reference should remain in the version catalog"
            )
        }

        @Test
        @DisplayName("litert-lm library entry is absent")
        fun litertLmLibraryAbsent() {
            assertFalse(
                Regex("""litert-lm\s*=\s*\{""").containsMatchIn(catalog),
                "litert-lm library declaration should have been removed in this PR"
            )
        }

        @Test
        @DisplayName("No reference to com.google.ai.edge.litertlm remains")
        fun noLitertlmModuleReference() {
            assertFalse(
                catalog.contains("litertlm"),
                "No litertlm module reference should remain in the version catalog after removal"
            )
        }

        @Test
        @DisplayName("No version.ref pointing to 'mediapipe' remains")
        fun noVersionRefToMediapipe() {
            assertFalse(
                catalog.contains("version.ref = \"mediapipe\""),
                "No library should reference the removed mediapipe version"
            )
        }

        @Test
        @DisplayName("No version.ref pointing to 'litert' remains")
        fun noVersionRefToLitert() {
            assertFalse(
                catalog.contains("version.ref = \"litert\""),
                "No library should reference the removed litert version"
            )
        }
    }

    // ─── Retained AI entries still present ───────────────────────────────────

    @Nested
    @DisplayName("Retained AI library entries (regression guard)")
    inner class RetainedAiLibrariesTests {

        @Test
        @DisplayName("langchain4j-core library entry is still present")
        fun langchain4jCoreLibraryPresent() {
            assertTrue(
                Regex("""langchain4j-core\s*=\s*\{""").containsMatchIn(catalog),
                "langchain4j-core library must remain after mediapipe/litert removal"
            )
        }

        @Test
        @DisplayName("langchain4j-ollama library entry is still present")
        fun langchain4jOllamaLibraryPresent() {
            assertTrue(
                Regex("""langchain4j-ollama\s*=\s*\{""").containsMatchIn(catalog),
                "langchain4j-ollama library must remain after mediapipe/litert removal"
            )
        }

        @Test
        @DisplayName("firebase-vertexai library entry is still present")
        fun firebaseVertexAiLibraryPresent() {
            assertTrue(
                Regex("""firebase-vertexai\s*=\s*\{""").containsMatchIn(catalog),
                "firebase-vertexai library must remain after mediapipe/litert removal"
            )
        }

        @Test
        @DisplayName("langchain4j version is still declared")
        fun langchain4jVersionPresent() {
            assertTrue(
                Regex("""langchain4j\s*=\s*"[^"]+"""").containsMatchIn(catalog),
                "langchain4j version must remain in [versions] section"
            )
        }
    }

    // ─── Version catalog structural integrity ─────────────────────────────────

    @Nested
    @DisplayName("Structural integrity after removals")
    inner class StructuralIntegrityTests {

        @Test
        @DisplayName("[versions] section still exists")
        fun versionsSectionPresent() {
            assertTrue(catalog.contains("[versions]"), "The [versions] section must still be present")
        }

        @Test
        @DisplayName("[libraries] section still exists")
        fun librariesSectionPresent() {
            assertTrue(catalog.contains("[libraries]"), "The [libraries] section must still be present")
        }

        @Test
        @DisplayName("No dangling version.ref for 'mediapipe' or 'litert' remains anywhere")
        fun noDanglingVersionRefsForRemovedVersions() {
            val danglingMediapipe = catalog.contains("version.ref = \"mediapipe\"")
            val danglingLitert = catalog.contains("version.ref = \"litert\"")
            assertFalse(
                danglingMediapipe || danglingLitert,
                "No library entry should still reference the removed mediapipe or litert version keys"
            )
        }

        @Test
        @DisplayName("Catalog brace count remains balanced in [libraries] section after removals")
        fun catalogBracesBalancedAfterRemovals() {
            val librariesSection = Regex(
                """\[libraries\](.*?)(\[|$)""",
                RegexOption.DOT_MATCHES_ALL
            ).find(catalog)

            assertTrue(librariesSection != null, "Libraries section should be parseable")

            val openBraces = librariesSection!!.value.count { it == '{' }
            val closeBraces = librariesSection.value.count { it == '}' }
            assertTrue(
                openBraces == closeBraces,
                "Library definitions should have balanced braces after removals; open=$openBraces, close=$closeBraces"
            )
        }
    }
}