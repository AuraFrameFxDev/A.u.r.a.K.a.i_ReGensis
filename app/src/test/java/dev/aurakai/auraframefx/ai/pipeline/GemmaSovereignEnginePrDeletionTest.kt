package dev.aurakai.auraframefx.ai.pipeline

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

/**
 * Tests verifying that [GemmaSovereignEngine] and [GemmaModule] were fully
 * deleted in this PR and are no longer resolvable on the classpath.
 *
 * PR changes:
 *   - `app/src/main/java/dev/aurakai/auraframefx/ai/pipeline/GemmaSovereignEngine.kt` deleted
 *   - `app/src/main/java/dev/aurakai/auraframefx/di/GemmaModule.kt` deleted
 *   - Associated Gradle dependencies (mediapipe-tasks-genai, litert-lm) removed
 *
 * These tests use [Class.forName] to confirm the JVM cannot resolve the deleted
 * classes, consistent with the project pattern used in NexusMemoryCoreTest for
 * verifying removed APIs.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("GemmaSovereignEngine + GemmaModule — PR deletion verification")
class GemmaSovereignEnginePrDeletionTest {

    // ─── GemmaSovereignEngine deleted ────────────────────────────────────────

    @Nested
    @DisplayName("GemmaSovereignEngine class deleted")
    inner class GemmaSovereignEngineDeletionTests {

        @Test
        @DisplayName("GemmaSovereignEngine is not present on the classpath")
        fun gemmaSovereignEngineClassNotFound() {
            var found = false
            try {
                Class.forName("dev.aurakai.auraframefx.ai.pipeline.GemmaSovereignEngine")
                found = true
            } catch (_: ClassNotFoundException) {
                // Expected — the file was deleted in this PR
            }
            assertFalse(
                found,
                "GemmaSovereignEngine should have been deleted in this PR and must not be resolvable"
            )
        }

        @Test
        @DisplayName("No class named GemmaSovereignEngine exists in the ai.pipeline package")
        fun noGemmaSovereignEngineInPackage() {
            // Additionally confirm it cannot be found via a package scan by checking
            // that a ClassNotFoundException is thrown — the class does not exist.
            var classExists = false
            try {
                val clazz = Class.forName("dev.aurakai.auraframefx.ai.pipeline.GemmaSovereignEngine")
                classExists = clazz != null
            } catch (_: ClassNotFoundException) {
                classExists = false
            } catch (_: NoClassDefFoundError) {
                // Dependency class not found (e.g. LiteRT SDK removed) — also confirms deletion
                classExists = false
            }
            assertFalse(classExists, "GemmaSovereignEngine class must not exist after PR deletion")
        }
    }

    // ─── GemmaModule deleted ──────────────────────────────────────────────────

    @Nested
    @DisplayName("GemmaModule class deleted")
    inner class GemmaModuleDeletionTests {

        @Test
        @DisplayName("GemmaModule is not present on the classpath")
        fun gemmaModuleClassNotFound() {
            var found = false
            try {
                Class.forName("dev.aurakai.auraframefx.di.GemmaModule")
                found = true
            } catch (_: ClassNotFoundException) {
                // Expected — the file was deleted in this PR
            }
            assertFalse(
                found,
                "GemmaModule should have been deleted in this PR and must not be resolvable"
            )
        }

        @Test
        @DisplayName("GemmaModule Hilt companion class is also absent")
        fun gemmaModuleHiltCompanionAbsent() {
            // Hilt generates companion/module classes; both should be absent
            var found = false
            try {
                Class.forName("dev.aurakai.auraframefx.di.GemmaModule_ProvideGemmaSovereignEngineFactory")
                found = true
            } catch (_: ClassNotFoundException) {
                // Expected
            }
            assertFalse(
                found,
                "Hilt-generated GemmaModule factory class should also be absent after PR deletion"
            )
        }
    }

    // ─── LiteRT / MediaPipe dependencies removed ─────────────────────────────

    @Nested
    @DisplayName("LiteRT and MediaPipe SDK classes are not on classpath")
    inner class LiteRtMediaPipeDependencyTests {

        @Test
        @DisplayName("com.google.ai.edge.litertlm.Engine is not resolvable (dependency removed)")
        fun litertEngineClassNotFound() {
            var found = false
            try {
                Class.forName("com.google.ai.edge.litertlm.Engine")
                found = true
            } catch (_: ClassNotFoundException) {
                // Expected — libs.litert-lm dependency was removed in this PR
            }
            assertFalse(
                found,
                "LiteRT Engine class should not be on the classpath since litert-lm was removed from dependencies"
            )
        }

        @Test
        @DisplayName("com.google.mediapipe.tasks.genai package entry is not resolvable")
        fun mediaPipeGenAiClassNotFound() {
            var found = false
            try {
                // Try to find any well-known class from the mediapipe tasks-genai artifact
                Class.forName("com.google.mediapipe.tasks.genai.llminference.LlmInference")
                found = true
            } catch (_: ClassNotFoundException) {
                // Expected — libs.mediapipe-tasks-genai dependency was removed in this PR
            }
            assertFalse(
                found,
                "MediaPipe GenAI class should not be on the classpath since mediapipe-tasks-genai was removed"
            )
        }
    }
}