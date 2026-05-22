package dev.aurakai.auraframefx

/*
 * Tests validating the app/build.gradle.kts changes introduced in THIS pull request:
 *
 * Changes tested:
 *  1. Removal of `targetSdk = 37` from defaultConfig
 *  2. Removal of the "On-Device AI" dependency block (mediapipe-tasks-genai, litert-lm)
 *  3. Removal of `dependencyNotation =` named argument from androidx.credentials and googleid
 *     implementations (changed to positional argument style)
 *
 * Framework: JUnit 5 (Jupiter)
 * File parsing: reads app/build.gradle.kts as raw text and validates with string/regex checks.
 */

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.io.File

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("app/build.gradle.kts — LiteRT/MediaPipe PR Changes")
class AppBuildGradlePrLiteRtChangesTest {

    private fun locateBuildFile(): File {
        val candidates = listOf(
            File("build.gradle.kts"),
            File("app/build.gradle.kts"),
            File("../app/build.gradle.kts")
        )
        return candidates.firstOrNull { it.exists() } ?: error(
            "Unable to locate app/build.gradle.kts. Checked: ${candidates.joinToString { it.path }}"
        )
    }

    private val buildFile: File by lazy { locateBuildFile() }
    private val script: String by lazy { buildFile.readText() }

    // ─── targetSdk removal ────────────────────────────────────────────────────

    @Nested
    @DisplayName("targetSdk = 37 removed from defaultConfig")
    inner class TargetSdkTests {

        @Test
        @DisplayName("targetSdk = 37 assignment is absent from the build script")
        fun targetSdk37Absent() {
            assertFalse(
                script.contains("targetSdk = 37"),
                "targetSdk = 37 should have been removed from defaultConfig in this PR"
            )
        }

        @Test
        @DisplayName("No numeric targetSdk assignment exists in build script")
        fun noNumericTargetSdkAssignment() {
            // Verify no targetSdk = <number> remains after the PR removal
            assertFalse(
                Regex("""targetSdk\s*=\s*\d+""").containsMatchIn(script),
                "No numeric targetSdk = <number> should be present after PR removal"
            )
        }

        @Test
        @DisplayName("minSdk = 34 is still present (regression guard)")
        fun minSdkStillPresent() {
            assertTrue(
                script.contains("minSdk = 34"),
                "minSdk = 34 must still be present in defaultConfig — only targetSdk was removed"
            )
        }

        @Test
        @DisplayName("applicationId is still present (regression guard)")
        fun applicationIdStillPresent() {
            assertTrue(
                script.contains("applicationId = \"dev.aurakai.auraframefx\""),
                "applicationId must still be present in defaultConfig"
            )
        }
    }

    // ─── On-Device AI (mediapipe / litert) dependencies removed ──────────────

    @Nested
    @DisplayName("On-Device AI dependency block removed")
    inner class OnDeviceAiDependenciesTests {

        @Test
        @DisplayName("libs.mediapipe.tasks.genai is absent from dependencies")
        fun mediapipeTasksGenaiDependencyAbsent() {
            assertFalse(
                script.contains("mediapipe.tasks.genai"),
                "libs.mediapipe.tasks.genai implementation should have been removed in this PR"
            )
        }

        @Test
        @DisplayName("libs.litert.lm is absent from dependencies")
        fun litertLmDependencyAbsent() {
            assertFalse(
                script.contains("litert.lm"),
                "libs.litert.lm implementation should have been removed in this PR"
            )
        }

        @Test
        @DisplayName("'On-Device AI' comment block is absent")
        fun onDeviceAiCommentAbsent() {
            assertFalse(
                script.contains("On-Device AI"),
                "The 'On-Device AI (Gemma 4 E2B + LiteRT-LM)' comment should be absent after removal"
            )
        }

        @Test
        @DisplayName("No mediapipe reference remains anywhere in the build script")
        fun noMediapipeReferenceInScript() {
            assertFalse(
                script.contains("mediapipe"),
                "No mediapipe reference should remain in app/build.gradle.kts after removal"
            )
        }

        @Test
        @DisplayName("No litert reference remains anywhere in the build script")
        fun noLitertReferenceInScript() {
            assertFalse(
                script.contains("litert"),
                "No litert reference should remain in app/build.gradle.kts after removal"
            )
        }
    }

    // ─── Credentials dependency notation change ───────────────────────────────

    @Nested
    @DisplayName("dependencyNotation= named argument removed from credentials impl")
    inner class CredentialsDependencyNotationTests {

        @Test
        @DisplayName("'dependencyNotation =' named arg is absent from build script")
        fun dependencyNotationNamedArgAbsent() {
            assertFalse(
                script.contains("dependencyNotation ="),
                "dependencyNotation named argument should have been removed from implementation() calls"
            )
        }

        @Test
        @DisplayName("libs.androidx.credentials is still declared as an implementation dependency")
        fun androidxCredentialsStillPresent() {
            assertTrue(
                script.contains("libs.androidx.credentials"),
                "libs.androidx.credentials must still be an implementation dependency after notation change"
            )
        }

        @Test
        @DisplayName("libs.androidx.credentials.play.services.auth is still declared")
        fun androidxCredentialsPlayServicesAuthStillPresent() {
            assertTrue(
                script.contains("libs.androidx.credentials.play.services.auth"),
                "libs.androidx.credentials.play.services.auth must still be declared after notation change"
            )
        }

        @Test
        @DisplayName("libs.googleid is still declared as an implementation dependency")
        fun googleidStillPresent() {
            assertTrue(
                script.contains("libs.googleid"),
                "libs.googleid must still be declared as an implementation dependency after notation change"
            )
        }

        @Test
        @DisplayName("androidx.credentials implementation uses positional argument style")
        fun credentialsUsePositionalArgumentStyle() {
            // After the PR, the pattern should be: implementation(libs.androidx.credentials)
            // NOT:                                  implementation(dependencyNotation = libs.androidx.credentials)
            assertTrue(
                Regex("""implementation\s*\(\s*libs\.androidx\.credentials\s*\)""")
                    .containsMatchIn(script),
                "androidx.credentials should use positional implementation() syntax, not named arg"
            )
        }

        @Test
        @DisplayName("googleid implementation uses positional argument style")
        fun googleidUsesPositionalArgumentStyle() {
            assertTrue(
                Regex("""implementation\s*\(\s*libs\.googleid\s*\)""").containsMatchIn(script),
                "googleid should use positional implementation() syntax, not named arg"
            )
        }
    }

    // ─── Regression: core dependencies still present ──────────────────────────

    @Nested
    @DisplayName("Regression: core dependencies unaffected by PR changes")
    inner class RegressionTests {

        @Test
        @DisplayName("hilt.android is still present")
        fun hiltAndroidStillPresent() {
            assertTrue(
                script.contains("libs.hilt.android"),
                "libs.hilt.android must remain after this PR's changes"
            )
        }

        @Test
        @DisplayName("langchain4j.core is still present")
        fun langchain4jCoreStillPresent() {
            assertTrue(
                script.contains("libs.langchain4j.core"),
                "libs.langchain4j.core must remain after mediapipe/litert removal"
            )
        }

        @Test
        @DisplayName("bouncycastle is still present")
        fun bouncycastleStillPresent() {
            assertTrue(
                script.contains("libs.bouncycastle"),
                "libs.bouncycastle must remain after this PR's changes"
            )
        }

        @Test
        @DisplayName("testImplementation(libs.junit) is still present")
        fun junitTestDependencyStillPresent() {
            assertTrue(
                script.contains("testImplementation(libs.junit)"),
                "JUnit test dependency must remain after the PR"
            )
        }
    }
}