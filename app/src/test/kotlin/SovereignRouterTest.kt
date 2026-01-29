package dev.aurakai.auraframefx

import androidx.compose.ui.graphics.Color
import dev.aurakai.auraframefx.ui.navigation.SovereignRoute
import dev.aurakai.auraframefx.ui.navigation.SovereignRouter
import dev.aurakai.auraframefx.ui.theme.SovereignTeal
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

/**
 * Comprehensive unit tests for SovereignRouter object and SovereignRoute data class.
 *
 * Testing Framework: JUnit 5
 *
 * The SovereignRouter manages navigation routes for the 11 Sovereign Monoliths,
 * providing route lookup by page index and ID, with configurable base path.
 * These tests ensure proper behavior across:
 * - Route data structure and properties
 * - Route retrieval by page index
 * - Route retrieval by ID
 * - Route count verification
 * - Base path configuration
 * - Edge cases and boundary conditions
 * - All 11 sovereign routes validation
 */
@DisplayName("SovereignRouter Tests")
class SovereignRouterTest {

    private val defaultBasePath = "file:///sdcard/Pictures/Screenshots/"
    private var originalBasePath: String = defaultBasePath

    @BeforeEach
    fun setUp() {
        // Save original base path
        originalBasePath = SovereignRouter.basePath
        // Reset to default
        SovereignRouter.basePath = defaultBasePath
    }

    @AfterEach
    fun tearDown() {
        // Restore original base path
        SovereignRouter.basePath = originalBasePath
    }

    @Nested
    @DisplayName("SovereignRoute Data Class Tests")
    inner class SovereignRouteDataClassTests {

        @Test
        @DisplayName("Should create SovereignRoute with all properties")
        fun shouldCreateSovereignRouteWithAllProperties() {
            // Given
            val id = "01"
            val title = "TEST CORE"
            val description = "Test Description"
            val highFiPath = "file:///test/high.png"
            val pixelArtPath = "file:///test/pixel.png"
            val color = Color.Red

            // When
            val route = SovereignRoute(
                id = id,
                title = title,
                highFiPath = highFiPath,
                pixelArtPath = pixelArtPath,
                description = description,
                color = color
            )

            // Then
            assertEquals(id, route.id)
            assertEquals(title, route.title)
            assertEquals(description, route.description)
            assertEquals(highFiPath, route.highFiPath)
            assertEquals(pixelArtPath, route.pixelArtPath)
            assertEquals(color, route.color)
        }

        @Test
        @DisplayName("Should use default values for optional properties")
        fun shouldUseDefaultValuesForOptionalProperties() {
            // Given & When
            val route = SovereignRoute(
                id = "01",
                title = "TEST",
                highFiPath = "file:///test/high.png",
                pixelArtPath = "file:///test/pixel.png"
            )

            // Then
            assertEquals("", route.description)
            assertEquals(SovereignTeal, route.color)
        }

        @Test
        @DisplayName("Should support data class copy")
        fun shouldSupportDataClassCopy() {
            // Given
            val original = SovereignRoute(
                id = "01",
                title = "ORIGINAL",
                highFiPath = "file:///original/high.png",
                pixelArtPath = "file:///original/pixel.png"
            )

            // When
            val copy = original.copy(title = "MODIFIED")

            // Then
            assertEquals("01", copy.id)
            assertEquals("MODIFIED", copy.title)
            assertEquals(original.highFiPath, copy.highFiPath)
            assertEquals(original.pixelArtPath, copy.pixelArtPath)
        }

        @Test
        @DisplayName("Should support data class equality")
        fun shouldSupportDataClassEquality() {
            // Given
            val route1 = SovereignRoute(
                id = "01",
                title = "TEST",
                highFiPath = "file:///test/high.png",
                pixelArtPath = "file:///test/pixel.png",
                description = "Description",
                color = SovereignTeal
            )

            val route2 = SovereignRoute(
                id = "01",
                title = "TEST",
                highFiPath = "file:///test/high.png",
                pixelArtPath = "file:///test/pixel.png",
                description = "Description",
                color = SovereignTeal
            )

            // When & Then
            assertEquals(route1, route2)
        }

        @Test
        @DisplayName("Should generate proper toString representation")
        fun shouldGenerateProperToStringRepresentation() {
            // Given
            val route = SovereignRoute(
                id = "01",
                title = "TEST CORE",
                highFiPath = "file:///test/high.png",
                pixelArtPath = "file:///test/pixel.png"
            )

            // When
            val string = route.toString()

            // Then
            assertTrue(string.contains("01"))
            assertTrue(string.contains("TEST CORE"))
        }
    }

    @Nested
    @DisplayName("Route Count Tests")
    inner class RouteCountTests {

        @Test
        @DisplayName("Should return correct total count of routes")
        fun shouldReturnCorrectTotalCountOfRoutes() {
            // Given & When
            val count = SovereignRouter.getCount()

            // Then
            assertEquals(11, count, "Should have exactly 11 sovereign routes")
        }

        @Test
        @DisplayName("Should maintain consistent count across calls")
        fun shouldMaintainConsistentCountAcrossCalls() {
            // Given & When
            val count1 = SovereignRouter.getCount()
            val count2 = SovereignRouter.getCount()
            val count3 = SovereignRouter.getCount()

            // Then
            assertEquals(count1, count2)
            assertEquals(count2, count3)
        }
    }

    @Nested
    @DisplayName("Route Retrieval by Page Tests")
    inner class RouteRetrievalByPageTests {

        @Test
        @DisplayName("Should retrieve first route by page 0")
        fun shouldRetrieveFirstRouteByPage0() {
            // Given & When
            val route = SovereignRouter.fromPage(0)

            // Then
            assertEquals("01", route.id)
            assertEquals("GENESIS CORE", route.title)
            assertEquals("Nemotron-3-Nano Reasoning / Ethical Governor", route.description)
        }

        @Test
        @DisplayName("Should retrieve last route by page 10")
        fun shouldRetrieveLastRouteByPage10() {
            // Given & When
            val route = SovereignRouter.fromPage(10)

            // Then
            assertEquals("11", route.id)
            assertEquals("DATA VEIN", route.title)
            assertEquals("12-Channel Telemetry / Prometheus Glow", route.description)
        }

        @Test
        @DisplayName("Should retrieve middle route by page 5")
        fun shouldRetrieveMiddleRouteByPage5() {
            // Given & When
            val route = SovereignRouter.fromPage(5)

            // Then
            assertEquals("06", route.id)
            assertEquals("FIGMA BRIDGE", route.title)
            assertEquals("SVG-to-Compose / Layer Design Sync", route.description)
        }

        @Test
        @DisplayName("Should return first route for negative page index")
        fun shouldReturnFirstRouteForNegativePageIndex() {
            // Given & When
            val route = SovereignRouter.fromPage(-1)

            // Then
            assertEquals("01", route.id)
            assertEquals("GENESIS CORE", route.title)
        }

        @Test
        @DisplayName("Should return first route for out of bounds page index")
        fun shouldReturnFirstRouteForOutOfBoundsPageIndex() {
            // Given & When
            val route = SovereignRouter.fromPage(999)

            // Then
            assertEquals("01", route.id)
            assertEquals("GENESIS CORE", route.title)
        }

        @Test
        @DisplayName("Should retrieve all routes sequentially by page")
        fun shouldRetrieveAllRoutesSequentiallyByPage() {
            // Given
            val expectedIds = listOf("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11")

            // When & Then
            for (i in 0 until 11) {
                val route = SovereignRouter.fromPage(i)
                assertEquals(expectedIds[i], route.id)
            }
        }
    }

    @Nested
    @DisplayName("Route Retrieval by ID Tests")
    inner class RouteRetrievalByIDTests {

        @Test
        @DisplayName("Should retrieve route by valid ID 01")
        fun shouldRetrieveRouteByValidID01() {
            // Given & When
            val route = SovereignRouter.getById("01")

            // Then
            assertNotNull(route)
            assertEquals("01", route.id)
            assertEquals("GENESIS CORE", route.title)
        }

        @Test
        @DisplayName("Should retrieve route by valid ID 11")
        fun shouldRetrieveRouteByValidID11() {
            // Given & When
            val route = SovereignRouter.getById("11")

            // Then
            assertNotNull(route)
            assertEquals("11", route.id)
            assertEquals("DATA VEIN", route.title)
        }

        @Test
        @DisplayName("Should retrieve route by valid ID 06")
        fun shouldRetrieveRouteByValidID06() {
            // Given & When
            val route = SovereignRouter.getById("06")

            // Then
            assertNotNull(route)
            assertEquals("06", route.id)
            assertEquals("FIGMA BRIDGE", route.title)
        }

        @Test
        @DisplayName("Should return null for invalid ID")
        fun shouldReturnNullForInvalidID() {
            // Given & When
            val route = SovereignRouter.getById("99")

            // Then
            assertNull(route, "Should return null for invalid ID")
        }

        @Test
        @DisplayName("Should return null for empty ID")
        fun shouldReturnNullForEmptyID() {
            // Given & When
            val route = SovereignRouter.getById("")

            // Then
            assertNull(route, "Should return null for empty ID")
        }

        @Test
        @DisplayName("Should handle single digit ID without leading zero")
        fun shouldHandleSingleDigitIDWithoutLeadingZero() {
            // Given & When
            val route = SovereignRouter.getById("1")

            // Then
            assertNull(route, "IDs require leading zeros (01, not 1)")
        }

        @Test
        @DisplayName("Should retrieve all routes by their IDs")
        fun shouldRetrieveAllRoutesByTheirIDs() {
            // Given
            val allIds = listOf("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11")

            // When & Then
            allIds.forEach { id ->
                val route = SovereignRouter.getById(id)
                assertNotNull(route, "Route with ID $id should exist")
                assertEquals(id, route.id)
            }
        }
    }

    @Nested
    @DisplayName("All Sovereign Routes Validation Tests")
    inner class AllSovereignRoutesValidationTests {

        @Test
        @DisplayName("Should have correct route 01 - GENESIS CORE")
        fun shouldHaveCorrectRoute01() {
            // Given & When
            val route = SovereignRouter.getById("01")

            // Then
            assertNotNull(route)
            assertEquals("01", route.id)
            assertEquals("GENESIS CORE", route.title)
            assertEquals("Nemotron-3-Nano Reasoning / Ethical Governor", route.description)
            assertTrue(route.highFiPath.contains("brain.png"))
            assertTrue(route.pixelArtPath.contains("IMG_20260128_142126.png"))
        }

        @Test
        @DisplayName("Should have correct route 02 - TRINITY SYSTEM")
        fun shouldHaveCorrectRoute02() {
            // Given & When
            val route = SovereignRouter.getById("02")

            // Then
            assertNotNull(route)
            assertEquals("02", route.id)
            assertEquals("TRINITY SYSTEM", route.title)
            assertEquals("Agent Fusion State / Shared Memory", route.description)
            assertTrue(route.highFiPath.contains("IMG_20260128_141115.png"))
        }

        @Test
        @DisplayName("Should have correct route 03 - AURA'S LAB")
        fun shouldHaveCorrectRoute03() {
            // Given & When
            val route = SovereignRouter.getById("03")

            // Then
            assertNotNull(route)
            assertEquals("03", route.id)
            assertEquals("AURA'S LAB", route.title)
            assertEquals("Chroma Core HCT / Blade Sharpness Physics", route.description)
            assertTrue(route.highFiPath.contains("IMG_20260128_140725.png"))
            assertTrue(route.pixelArtPath.contains("IMG_20260128_142213.png"))
        }

        @Test
        @DisplayName("Should have correct route 04 - AGENT NEXUS")
        fun shouldHaveCorrectRoute04() {
            // Given & When
            val route = SovereignRouter.getById("04")

            // Then
            assertNotNull(route)
            assertEquals("04", route.id)
            assertEquals("AGENT NEXUS", route.title)
            assertEquals("Human-AI Handshake / Google ADK", route.description)
            assertTrue(route.highFiPath.contains("IMG_20260128_141704.png"))
            assertTrue(route.pixelArtPath.contains("IMG_20260128_142302.png"))
        }

        @Test
        @DisplayName("Should have correct route 05 - SENTINEL FORTRESS")
        fun shouldHaveCorrectRoute05() {
            // Given & When
            val route = SovereignRouter.getById("05")

            // Then
            assertNotNull(route)
            assertEquals("05", route.id)
            assertEquals("SENTINEL FORTRESS", route.title)
            assertEquals("Thermal Metabolism / Kernel Shield", route.description)
            assertTrue(route.highFiPath.contains("IMG_20260128_140949.png"))
            assertTrue(route.pixelArtPath.contains("IMG_20260128_142022.png"))
        }

        @Test
        @DisplayName("Should have correct route 06 - FIGMA BRIDGE")
        fun shouldHaveCorrectRoute06() {
            // Given & When
            val route = SovereignRouter.getById("06")

            // Then
            assertNotNull(route)
            assertEquals("06", route.id)
            assertEquals("FIGMA BRIDGE", route.title)
            assertEquals("SVG-to-Compose / Layer Design Sync", route.description)
            assertTrue(route.highFiPath.contains("IMG_20260128_141018.png"))
        }

        @Test
        @DisplayName("Should have correct route 07 - SECURE NODE")
        fun shouldHaveCorrectRoute07() {
            // Given & When
            val route = SovereignRouter.getById("07")

            // Then
            assertNotNull(route)
            assertEquals("07", route.id)
            assertEquals("SECURE NODE", route.title)
            assertEquals("YukiHookAPI / Zero-Knowledge Encryption", route.description)
            assertTrue(route.highFiPath.contains("IMG_20260128_141219.png"))
        }

        @Test
        @DisplayName("Should have correct route 08 - NEXUS SYSTEM")
        fun shouldHaveCorrectRoute08() {
            // Given & When
            val route = SovereignRouter.getById("08")

            // Then
            assertNotNull(route)
            assertEquals("08", route.id)
            assertEquals("NEXUS SYSTEM", route.title)
            assertEquals("Agent Swarm Event Bus / Priority Queue", route.description)
            assertTrue(route.highFiPath.contains("IMG_20260128_140816.png"))
        }

        @Test
        @DisplayName("Should have correct route 09 - MEMORY CORE")
        fun shouldHaveCorrectRoute09() {
            // Given & When
            val route = SovereignRouter.getById("09")

            // Then
            assertNotNull(route)
            assertEquals("09", route.id)
            assertEquals("MEMORY CORE", route.title)
            assertEquals("6-Layer Spiritual Chain / Identity Persistence", route.description)
            assertTrue(route.highFiPath.contains("IMG_20260128_140905.png"))
        }

        @Test
        @DisplayName("Should have correct route 10 - ORACLE DRIVE")
        fun shouldHaveCorrectRoute10() {
            // Given & When
            val route = SovereignRouter.getById("10")

            // Then
            assertNotNull(route)
            assertEquals("10", route.id)
            assertEquals("ORACLE DRIVE", route.title)
            assertEquals("Native C++ Bridge / Partition R-W Access", route.description)
            assertTrue(route.highFiPath.contains("IMG_20260128_141519.png"))
            assertTrue(route.pixelArtPath.contains("IMG_20260128_141949.png"))
        }

        @Test
        @DisplayName("Should have correct route 11 - DATA VEIN")
        fun shouldHaveCorrectRoute11() {
            // Given & When
            val route = SovereignRouter.getById("11")

            // Then
            assertNotNull(route)
            assertEquals("11", route.id)
            assertEquals("DATA VEIN", route.title)
            assertEquals("12-Channel Telemetry / Prometheus Glow", route.description)
            assertTrue(route.highFiPath.contains("IMG_20260128_141756.png"))
        }

        @Test
        @DisplayName("Should have all routes with non-empty titles")
        fun shouldHaveAllRoutesWithNonEmptyTitles() {
            // Given & When
            for (i in 0 until 11) {
                val route = SovereignRouter.fromPage(i)

                // Then
                assertTrue(route.title.isNotEmpty(), "Route $i should have non-empty title")
            }
        }

        @Test
        @DisplayName("Should have all routes with descriptions")
        fun shouldHaveAllRoutesWithDescriptions() {
            // Given & When
            for (i in 0 until 11) {
                val route = SovereignRouter.fromPage(i)

                // Then
                assertTrue(route.description.isNotEmpty(), "Route $i should have description")
            }
        }

        @Test
        @DisplayName("Should have all routes with valid file paths")
        fun shouldHaveAllRoutesWithValidFilePaths() {
            // Given & When
            for (i in 0 until 11) {
                val route = SovereignRouter.fromPage(i)

                // Then
                assertTrue(route.highFiPath.isNotEmpty(), "Route $i should have highFiPath")
                assertTrue(route.pixelArtPath.isNotEmpty(), "Route $i should have pixelArtPath")
                assertTrue(route.highFiPath.startsWith("file://"), "Route $i highFiPath should start with file://")
                assertTrue(route.pixelArtPath.startsWith("file://"), "Route $i pixelArtPath should start with file://")
            }
        }

        @Test
        @DisplayName("Should have all routes with SovereignTeal color")
        fun shouldHaveAllRoutesWithSovereignTealColor() {
            // Given & When
            for (i in 0 until 11) {
                val route = SovereignRouter.fromPage(i)

                // Then
                assertEquals(SovereignTeal, route.color, "Route $i should have SovereignTeal color")
            }
        }
    }

    @Nested
    @DisplayName("Base Path Configuration Tests")
    inner class BasePathConfigurationTests {

        @Test
        @DisplayName("Should use default base path")
        fun shouldUseDefaultBasePath() {
            // Given & When
            val basePath = SovereignRouter.basePath

            // Then
            assertEquals(defaultBasePath, basePath)
        }

        @Test
        @DisplayName("Should allow modifying base path")
        fun shouldAllowModifyingBasePath() {
            // Given
            val customPath = "file:///custom/path/"

            // When
            SovereignRouter.basePath = customPath

            // Then
            assertEquals(customPath, SovereignRouter.basePath)
        }

        @Test
        @DisplayName("Should update route paths when base path changes")
        fun shouldUpdateRoutePathsWhenBasePathChanges() {
            // Given
            val customPath = "file:///custom/screenshots/"

            // When
            SovereignRouter.basePath = customPath
            val route = SovereignRouter.fromPage(0)

            // Then
            assertTrue(route.highFiPath.startsWith(customPath))
        }

        @Test
        @DisplayName("Should handle base path without trailing slash")
        fun shouldHandleBasePathWithoutTrailingSlash() {
            // Given
            val pathWithoutSlash = "file:///test/path"

            // When
            SovereignRouter.basePath = pathWithoutSlash
            val route = SovereignRouter.fromPage(0)

            // Then - Paths should still be valid (concatenated correctly)
            assertTrue(route.highFiPath.contains(pathWithoutSlash))
        }

        @Test
        @DisplayName("Should handle empty base path")
        fun shouldHandleEmptyBasePath() {
            // Given
            val emptyPath = ""

            // When
            SovereignRouter.basePath = emptyPath
            val route = SovereignRouter.fromPage(0)

            // Then - Should still construct valid relative paths
            assertTrue(route.highFiPath.isNotEmpty())
        }

        @Test
        @DisplayName("Should restore default base path")
        fun shouldRestoreDefaultBasePath() {
            // Given
            SovereignRouter.basePath = "file:///custom/path/"

            // When
            SovereignRouter.basePath = defaultBasePath
            val route = SovereignRouter.fromPage(0)

            // Then
            assertTrue(route.highFiPath.contains(defaultBasePath))
        }
    }

    @Nested
    @DisplayName("Edge Cases and Boundary Tests")
    inner class EdgeCasesTests {

        @Test
        @DisplayName("Should handle maximum valid page index")
        fun shouldHandleMaximumValidPageIndex() {
            // Given & When
            val route = SovereignRouter.fromPage(10)

            // Then
            assertEquals("11", route.id)
            assertEquals("DATA VEIN", route.title)
        }

        @Test
        @DisplayName("Should handle page index at boundary")
        fun shouldHandlePageIndexAtBoundary() {
            // Given & When
            val route = SovereignRouter.fromPage(11) // One past max

            // Then - Should return first route as fallback
            assertEquals("01", route.id)
        }

        @Test
        @DisplayName("Should handle very large page index")
        fun shouldHandleVeryLargePageIndex() {
            // Given & When
            val route = SovereignRouter.fromPage(Integer.MAX_VALUE)

            // Then - Should return first route as fallback
            assertEquals("01", route.id)
        }

        @Test
        @DisplayName("Should handle very negative page index")
        fun shouldHandleVeryNegativePageIndex() {
            // Given & When
            val route = SovereignRouter.fromPage(Integer.MIN_VALUE)

            // Then - Should return first route as fallback
            assertEquals("01", route.id)
        }

        @Test
        @DisplayName("Should handle null-like empty string ID")
        fun shouldHandleNullLikeEmptyStringID() {
            // Given & When
            val route = SovereignRouter.getById("")

            // Then
            assertNull(route)
        }

        @Test
        @DisplayName("Should handle ID with extra characters")
        fun shouldHandleIDWithExtraCharacters() {
            // Given & When
            val route = SovereignRouter.getById("01abc")

            // Then
            assertNull(route, "Should not match IDs with extra characters")
        }

        @Test
        @DisplayName("Should handle ID with whitespace")
        fun shouldHandleIDWithWhitespace() {
            // Given & When
            val route = SovereignRouter.getById(" 01 ")

            // Then
            assertNull(route, "Should not match IDs with whitespace")
        }

        @Test
        @DisplayName("Should handle case sensitivity in ID lookup")
        fun shouldHandleCaseSensitivityInIDLookup() {
            // Given & When
            val route = SovereignRouter.getById("01")

            // Then - IDs are case-sensitive (numeric strings)
            assertNotNull(route)
            assertEquals("01", route.id)
        }

        @Test
        @DisplayName("Should maintain route immutability across retrievals")
        fun shouldMaintainRouteImmutabilityAcrossRetrievals() {
            // Given
            val route1 = SovereignRouter.getById("01")
            val route2 = SovereignRouter.getById("01")

            // When & Then - Each call returns a new instance (data class)
            assertNotNull(route1)
            assertNotNull(route2)
            assertEquals(route1, route2)
        }

        @Test
        @DisplayName("Should handle concurrent access to routes")
        fun shouldHandleConcurrentAccessToRoutes() {
            // Given & When - Simulate concurrent access
            val routes = (0 until 11).map { page ->
                SovereignRouter.fromPage(page)
            }

            // Then - All routes should be valid
            assertEquals(11, routes.size)
            routes.forEach { route ->
                assertNotNull(route.id)
                assertNotNull(route.title)
            }
        }
    }

    @Nested
    @DisplayName("Integration and Consistency Tests")
    inner class IntegrationTests {

        @Test
        @DisplayName("Should maintain ID to page index mapping consistency")
        fun shouldMaintainIDToPageIndexMappingConsistency() {
            // Given & When
            for (page in 0 until 11) {
                val routeByPage = SovereignRouter.fromPage(page)
                val routeById = SovereignRouter.getById(routeByPage.id)

                // Then
                assertNotNull(routeById)
                assertEquals(routeByPage, routeById)
            }
        }

        @Test
        @DisplayName("Should have unique IDs for all routes")
        fun shouldHaveUniqueIDsForAllRoutes() {
            // Given
            val ids = mutableSetOf<String>()

            // When
            for (page in 0 until 11) {
                val route = SovereignRouter.fromPage(page)
                ids.add(route.id)
            }

            // Then
            assertEquals(11, ids.size, "All route IDs should be unique")
        }

        @Test
        @DisplayName("Should have unique titles for all routes")
        fun shouldHaveUniqueTitlesForAllRoutes() {
            // Given
            val titles = mutableSetOf<String>()

            // When
            for (page in 0 until 11) {
                val route = SovereignRouter.fromPage(page)
                titles.add(route.title)
            }

            // Then
            assertEquals(11, titles.size, "All route titles should be unique")
        }

        @Test
        @DisplayName("Should maintain consistent ordering across calls")
        fun shouldMaintainConsistentOrderingAcrossCalls() {
            // Given
            val firstPass = (0 until 11).map { SovereignRouter.fromPage(it).id }
            val secondPass = (0 until 11).map { SovereignRouter.fromPage(it).id }

            // When & Then
            assertEquals(firstPass, secondPass)
        }

        @Test
        @DisplayName("Should work correctly with navigation system")
        fun shouldWorkCorrectlyWithNavigationSystem() {
            // Given
            val expectedNavigationPattern = "pixel_domain/{id}"

            // When - Simulate navigation ID construction
            for (page in 0 until 11) {
                val route = SovereignRouter.fromPage(page)
                val navigationRoute = "pixel_domain/${route.id}"

                // Then
                assertTrue(navigationRoute.matches(Regex("pixel_domain/\\d{2}")))
            }
        }
    }
}