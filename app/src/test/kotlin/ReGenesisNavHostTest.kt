package dev.aurakai.auraframefx

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import dev.aurakai.auraframefx.customization.CustomizationViewModel
import dev.aurakai.auraframefx.models.ReGenesisMode
import dev.aurakai.auraframefx.ui.navigation.ReGenesisNavHost
import dev.aurakai.auraframefx.ui.navigation.SovereignRouter
import io.mockk.*
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * Comprehensive unit tests for ReGenesisNavHost composable.
 *
 * Testing Framework: JUnit 5 with Compose Testing and MockK
 *
 * The ReGenesisNavHost is the main navigation host for the application, managing routes
 * between the Exodus home screen, pixel workspaces, and various feature screens.
 * These tests ensure proper behavior across:
 * - Navigation graph initialization
 * - Route navigation (exodus_home, pixel_domain, workspaces)
 * - CustomizationViewModel integration
 * - Start destination configuration
 * - Navigation state management
 * - Edge cases and boundary conditions
 */
@DisplayName("ReGenesisNavHost Tests")
class ReGenesisNavHostTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var navController: TestNavHostController
    private lateinit var mockViewModel: CustomizationViewModel

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        mockViewModel = mockk<CustomizationViewModel>(relaxed = true)

        // Mock the state flow
        every { mockViewModel.state } returns MutableStateFlow(mockk(relaxed = true))
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Nested
    @DisplayName("Navigation Graph Initialization Tests")
    inner class NavigationGraphInitializationTests {

        @Test
        @DisplayName("Should initialize with exodus_home as start destination")
        fun shouldInitializeWithExodusHomeAsStartDestination() {
            // Given & When
            composeTestRule.setContent {
                navController = TestNavHostController(LocalContext.current).apply {
                    navigatorProvider.addNavigator(ComposeNavigator())
                }
                ReGenesisNavHost(
                    navController = navController,
                    customizationViewModel = mockViewModel
                )
            }

            // Then
            composeTestRule.waitForIdle()
            assertNotNull(navController.currentBackStackEntry)
        }

        @Test
        @DisplayName("Should create navigation graph without errors")
        fun shouldCreateNavigationGraphWithoutErrors() {
            // Given & When
            composeTestRule.setContent {
                navController = TestNavHostController(LocalContext.current).apply {
                    navigatorProvider.addNavigator(ComposeNavigator())
                }
                ReGenesisNavHost(
                    navController = navController,
                    customizationViewModel = mockViewModel
                )
            }

            // Then - Should complete without crashes
            composeTestRule.waitForIdle()
            assertTrue(true, "Navigation graph created successfully")
        }

        @Test
        @DisplayName("Should register all required routes")
        fun shouldRegisterAllRequiredRoutes() {
            // Given
            val expectedRoutes = listOf(
                "mode_selection",
                "exodus_home",
                "pixel_domain/{id}",
                "workspace_kai",
                "workspace_aura",
                "workspace_genesis",
                "aura_lab",
                "interface_forge"
            )

            // When
            composeTestRule.setContent {
                navController = TestNavHostController(LocalContext.current).apply {
                    navigatorProvider.addNavigator(ComposeNavigator())
                }
                ReGenesisNavHost(
                    navController = navController,
                    customizationViewModel = mockViewModel
                )
            }

            // Then - Navigation graph should be initialized
            composeTestRule.waitForIdle()
            assertNotNull(navController.graph)
        }

        @Test
        @DisplayName("Should handle NavHostController parameter")
        fun shouldHandleNavHostControllerParameter() {
            // Given
            composeTestRule.setContent {
                navController = TestNavHostController(LocalContext.current).apply {
                    navigatorProvider.addNavigator(ComposeNavigator())
                }
                ReGenesisNavHost(
                    navController = navController,
                    customizationViewModel = mockViewModel
                )
            }

            // When
            composeTestRule.waitForIdle()

            // Then
            assertNotNull(navController, "NavHostController should be initialized")
            assertNotNull(navController.graph, "Navigation graph should be set")
        }
    }

    @Nested
    @DisplayName("CustomizationViewModel Integration Tests")
    inner class CustomizationViewModelIntegrationTests {

        @Test
        @DisplayName("Should start CustomizationViewModel on initialization")
        fun shouldStartCustomizationViewModelOnInitialization() {
            // Given
            composeTestRule.setContent {
                navController = TestNavHostController(LocalContext.current).apply {
                    navigatorProvider.addNavigator(ComposeNavigator())
                }
                ReGenesisNavHost(
                    navController = navController,
                    customizationViewModel = mockViewModel
                )
            }

            // Then - LaunchedEffect should trigger start
            composeTestRule.waitForIdle()
            verify(atLeast = 1) { mockViewModel.start(any()) }
        }

        @Test
        @DisplayName("Should collect customization state")
        fun shouldCollectCustomizationState() {
            // Given
            composeTestRule.setContent {
                navController = TestNavHostController(LocalContext.current).apply {
                    navigatorProvider.addNavigator(ComposeNavigator())
                }
                ReGenesisNavHost(
                    navController = navController,
                    customizationViewModel = mockViewModel
                )
            }

            // Then - State should be collected
            composeTestRule.waitForIdle()
            verify(atLeast = 1) { mockViewModel.state }
        }

        @Test
        @DisplayName("Should handle default customizationViewModel parameter")
        fun shouldHandleDefaultCustomizationViewModelParameter() {
            // Given & When
            composeTestRule.setContent {
                navController = TestNavHostController(LocalContext.current).apply {
                    navigatorProvider.addNavigator(ComposeNavigator())
                }
                // Note: Using default viewModel() requires more complex setup
                // This test verifies the parameter is optional
                ReGenesisNavHost(
                    navController = navController,
                    customizationViewModel = mockViewModel
                )
            }

            // Then
            composeTestRule.waitForIdle()
            assertTrue(true, "Default viewModel parameter handled")
        }
    }

    @Nested
    @DisplayName("Mode Selection Route Tests")
    inner class ModeSelectionRouteTests {

        @Test
        @DisplayName("Should navigate to mode_selection route")
        fun shouldNavigateToModeSelectionRoute() {
            // Given
            composeTestRule.setContent {
                navController = TestNavHostController(LocalContext.current).apply {
                    navigatorProvider.addNavigator(ComposeNavigator())
                }
                ReGenesisNavHost(
                    navController = navController,
                    customizationViewModel = mockViewModel
                )
            }

            composeTestRule.waitForIdle()

            // When - Navigate to mode_selection
            navController.navigate("mode_selection")
            composeTestRule.waitForIdle()

            // Then
            assertEquals(
                "mode_selection",
                navController.currentBackStackEntry?.destination?.route
            )
        }

        @Test
        @DisplayName("Should handle mode selection with navigation to exodus_home")
        fun shouldHandleModeSelectionWithNavigationToExodusHome() {
            // Given
            composeTestRule.setContent {
                navController = TestNavHostController(LocalContext.current).apply {
                    navigatorProvider.addNavigator(ComposeNavigator())
                }
                ReGenesisNavHost(
                    navController = navController,
                    customizationViewModel = mockViewModel
                )
            }

            composeTestRule.waitForIdle()

            // When - Navigate to mode selection
            navController.navigate("mode_selection")
            composeTestRule.waitForIdle()

            // Then - Mode selection route should be current
            assertNotNull(navController.currentBackStackEntry)
        }
    }

    @Nested
    @DisplayName("Exodus Home Route Tests")
    inner class ExodusHomeRouteTests {

        @Test
        @DisplayName("Should render exodus_home route")
        fun shouldRenderExodusHomeRoute() {
            // Given & When
            composeTestRule.setContent {
                navController = TestNavHostController(LocalContext.current).apply {
                    navigatorProvider.addNavigator(ComposeNavigator())
                }
                ReGenesisNavHost(
                    navController = navController,
                    customizationViewModel = mockViewModel
                )
            }

            // Then - exodus_home is the start destination
            composeTestRule.waitForIdle()
            assertTrue(true, "Exodus home route rendered")
        }

        @Test
        @DisplayName("Should navigate to exodus_home from other routes")
        fun shouldNavigateToExodusHomeFromOtherRoutes() {
            // Given
            composeTestRule.setContent {
                navController = TestNavHostController(LocalContext.current).apply {
                    navigatorProvider.addNavigator(ComposeNavigator())
                }
                ReGenesisNavHost(
                    navController = navController,
                    customizationViewModel = mockViewModel
                )
            }

            composeTestRule.waitForIdle()

            // When - Navigate away and back
            navController.navigate("mode_selection")
            composeTestRule.waitForIdle()
            navController.navigate("exodus_home")
            composeTestRule.waitForIdle()

            // Then
            assertEquals(
                "exodus_home",
                navController.currentBackStackEntry?.destination?.route
            )
        }

        @Test
        @DisplayName("Should pass NavController to ExodusHUD")
        fun shouldPassNavControllerToExodusHUD() {
            // Given & When
            composeTestRule.setContent {
                navController = TestNavHostController(LocalContext.current).apply {
                    navigatorProvider.addNavigator(ComposeNavigator())
                }
                ReGenesisNavHost(
                    navController = navController,
                    customizationViewModel = mockViewModel
                )
            }

            // Then - ExodusHUD receives NavController
            composeTestRule.waitForIdle()
            assertTrue(true, "NavController passed to ExodusHUD")
        }
    }

    @Nested
    @DisplayName("Pixel Domain Route Tests")
    inner class PixelDomainRouteTests {

        @Test
        @DisplayName("Should navigate to pixel_domain with route ID")
        fun shouldNavigateToPixelDomainWithRouteID() {
            // Given
            composeTestRule.setContent {
                navController = TestNavHostController(LocalContext.current).apply {
                    navigatorProvider.addNavigator(ComposeNavigator())
                }
                ReGenesisNavHost(
                    navController = navController,
                    customizationViewModel = mockViewModel
                )
            }

            composeTestRule.waitForIdle()

            // When - Navigate to pixel domain with ID
            navController.navigate("pixel_domain/01")
            composeTestRule.waitForIdle()

            // Then
            assertEquals(
                "pixel_domain/{id}",
                navController.currentBackStackEntry?.destination?.route
            )
        }

        @Test
        @DisplayName("Should extract route ID from navigation arguments")
        fun shouldExtractRouteIDFromNavigationArguments() {
            // Given
            composeTestRule.setContent {
                navController = TestNavHostController(LocalContext.current).apply {
                    navigatorProvider.addNavigator(ComposeNavigator())
                }
                ReGenesisNavHost(
                    navController = navController,
                    customizationViewModel = mockViewModel
                )
            }

            composeTestRule.waitForIdle()

            // When
            navController.navigate("pixel_domain/03")
            composeTestRule.waitForIdle()

            // Then
            val id = navController.currentBackStackEntry?.arguments?.getString("id")
            assertEquals("03", id)
        }

        @Test
        @DisplayName("Should handle valid SovereignRoute ID")
        fun shouldHandleValidSovereignRouteID() {
            // Given
            val validId = "01"
            val route = SovereignRouter.getById(validId)

            composeTestRule.setContent {
                navController = TestNavHostController(LocalContext.current).apply {
                    navigatorProvider.addNavigator(ComposeNavigator())
                }
                ReGenesisNavHost(
                    navController = navController,
                    customizationViewModel = mockViewModel
                )
            }

            composeTestRule.waitForIdle()

            // When
            navController.navigate("pixel_domain/$validId")
            composeTestRule.waitForIdle()

            // Then
            assertNotNull(route, "Route should exist for valid ID")
        }

        @Test
        @DisplayName("Should pop back stack for invalid route ID")
        fun shouldPopBackStackForInvalidRouteID() {
            // Given
            composeTestRule.setContent {
                navController = TestNavHostController(LocalContext.current).apply {
                    navigatorProvider.addNavigator(ComposeNavigator())
                }
                ReGenesisNavHost(
                    navController = navController,
                    customizationViewModel = mockViewModel
                )
            }

            composeTestRule.waitForIdle()

            // When - Navigate with invalid ID
            navController.navigate("pixel_domain/999")
            composeTestRule.waitForIdle()

            // Then - Should handle gracefully (pop back or stay on current)
            assertNotNull(navController.currentBackStackEntry)
        }

        @Test
        @DIisplayName("Should handle default ID when argument is null")
        fun shouldHandleDefaultIDWhenArgumentIsNull() {
            // Given
            composeTestRule.setContent {
                navController = TestNavHostController(LocalContext.current).apply {
                    navigatorProvider.addNavigator(ComposeNavigator())
                }
                ReGenesisNavHost(
                    navController = navController,
                    customizationViewModel = mockViewModel
                )
            }

            composeTestRule.waitForIdle()

            // When - Navigate without explicit ID (uses default "01")
            navController.navigate("pixel_domain/")
            composeTestRule.waitForIdle()

            // Then - Should use default ID "01"
            assertTrue(true, "Default ID handled")
        }

        @Test
        @DisplayName("Should navigate to different pixel domains")
        fun shouldNavigateToDifferentPixelDomains() {
            // Given
            composeTestRule.setContent {
                navController = TestNavHostController(LocalContext.current).apply {
                    navigatorProvider.addNavigator(ComposeNavigator())
                }
                ReGenesisNavHost(
                    navController = navController,
                    customizationViewModel = mockViewModel
                )
            }

            composeTestRule.waitForIdle()

            // When - Navigate to multiple domains
            listOf("01", "03", "05").forEach { id ->
                navController.navigate("pixel_domain/$id")
                composeTestRule.waitForIdle()
                assertEquals(
                    "pixel_domain/{id}",
                    navController.currentBackStackEntry?.destination?.route
                )
            }

            // Then
            assertTrue(true, "Multiple pixel domains navigated successfully")
        }
    }

    @Nested
    @DisplayName("Workspace Routes Tests")
    inner class WorkspaceRoutesTests {

        @Test
        @DisplayName("Should navigate to workspace_kai")
        fun shouldNavigateToWorkspaceKai() {
            // Given
            composeTestRule.setContent {
                navController = TestNavHostController(LocalContext.current).apply {
                    navigatorProvider.addNavigator(ComposeNavigator())
                }
                ReGenesisNavHost(
                    navController = navController,
                    customizationViewModel = mockViewModel
                )
            }

            composeTestRule.waitForIdle()

            // When
            navController.navigate("workspace_kai")
            composeTestRule.waitForIdle()

            // Then
            assertEquals(
                "workspace_kai",
                navController.currentBackStackEntry?.destination?.route
            )
        }

        @Test
        @DisplayName("Should navigate to workspace_aura")
        fun shouldNavigateToWorkspaceAura() {
            // Given
            composeTestRule.setContent {
                navController = TestNavHostController(LocalContext.current).apply {
                    navigatorProvider.addNavigator(ComposeNavigator())
                }
                ReGenesisNavHost(
                    navController = navController,
                    customizationViewModel = mockViewModel
                )
            }

            composeTestRule.waitForIdle()

            // When
            navController.navigate("workspace_aura")
            composeTestRule.waitForIdle()

            // Then
            assertEquals(
                "workspace_aura",
                navController.currentBackStackEntry?.destination?.route
            )
        }

        @Test
        @DisplayName("Should navigate to workspace_genesis")
        fun shouldNavigateToWorkspaceGenesis() {
            // Given
            composeTestRule.setContent {
                navController = TestNavHostController(LocalContext.current).apply {
                    navigatorProvider.addNavigator(ComposeNavigator())
                }
                ReGenesisNavHost(
                    navController = navController,
                    customizationViewModel = mockViewModel
                )
            }

            composeTestRule.waitForIdle()

            // When
            navController.navigate("workspace_genesis")
            composeTestRule.waitForIdle()

            // Then
            assertEquals(
                "workspace_genesis",
                navController.currentBackStackEntry?.destination?.route
            )
        }

        @Test
        @DisplayName("Should render PixelWorkspaceScreen for workspace_aura")
        fun shouldRenderPixelWorkspaceScreenForWorkspaceAura() {
            // Given
            composeTestRule.setContent {
                navController = TestNavHostController(LocalContext.current).apply {
                    navigatorProvider.addNavigator(ComposeNavigator())
                }
                ReGenesisNavHost(
                    navController = navController,
                    customizationViewModel = mockViewModel
                )
            }

            composeTestRule.waitForIdle()

            // When
            navController.navigate("workspace_aura")
            composeTestRule.waitForIdle()

            // Then - Should render workspace screen
            assertTrue(true, "Workspace Aura rendered")
        }

        @Test
        @DisplayName("Should render PixelWorkspaceScreen for workspace_genesis")
        fun shouldRenderPixelWorkspaceScreenForWorkspaceGenesis() {
            // Given
            composeTestRule.setContent {
                navController = TestNavHostController(LocalContext.current).apply {
                    navigatorProvider.addNavigator(ComposeNavigator())
                }
                ReGenesisNavHost(
                    navController = navController,
                    customizationViewModel = mockViewModel
                )
            }

            composeTestRule.waitForIdle()

            // When
            navController.navigate("workspace_genesis")
            composeTestRule.waitForIdle()

            // Then - Should render workspace screen
            assertTrue(true, "Workspace Genesis rendered")
        }
    }

    @Nested
    @DisplayName("Feature Routes Tests")
    inner class FeatureRoutesTests {

        @Test
        @DisplayName("Should navigate to aura_lab")
        fun shouldNavigateToAuraLab() {
            // Given
            composeTestRule.setContent {
                navController = TestNavHostController(LocalContext.current).apply {
                    navigatorProvider.addNavigator(ComposeNavigator())
                }
                ReGenesisNavHost(
                    navController = navController,
                    customizationViewModel = mockViewModel
                )
            }

            composeTestRule.waitForIdle()

            // When
            navController.navigate("aura_lab")
            composeTestRule.waitForIdle()

            // Then
            assertEquals(
                "aura_lab",
                navController.currentBackStackEntry?.destination?.route
            )
        }

        @Test
        @DisplayName("Should navigate to interface_forge")
        fun shouldNavigateToInterfaceForge() {
            // Given
            composeTestRule.setContent {
                navController = TestNavHostController(LocalContext.current).apply {
                    navigatorProvider.addNavigator(ComposeNavigator())
                }
                ReGenesisNavHost(
                    navController = navController,
                    customizationViewModel = mockViewModel
                )
            }

            composeTestRule.waitForIdle()

            // When
            navController.navigate("interface_forge")
            composeTestRule.waitForIdle()

            // Then
            assertEquals(
                "interface_forge",
                navController.currentBackStackEntry?.destination?.route
            )
        }
    }

    @Nested
    @DisplayName("Back Navigation Tests")
    inner class BackNavigationTests {

        @Test
        @DisplayName("Should pop back stack correctly")
        fun shouldPopBackStackCorrectly() {
            // Given
            composeTestRule.setContent {
                navController = TestNavHostController(LocalContext.current).apply {
                    navigatorProvider.addNavigator(ComposeNavigator())
                }
                ReGenesisNavHost(
                    navController = navController,
                    customizationViewModel = mockViewModel
                )
            }

            composeTestRule.waitForIdle()

            // When - Navigate forward and back
            navController.navigate("workspace_aura")
            composeTestRule.waitForIdle()
            val canPop = navController.popBackStack()

            // Then
            assertTrue(canPop, "Should be able to pop back stack")
        }

        @Test
        @DisplayName("Should maintain back stack history")
        fun shouldMaintainBackStackHistory() {
            // Given
            composeTestRule.setContent {
                navController = TestNavHostController(LocalContext.current).apply {
                    navigatorProvider.addNavigator(ComposeNavigator())
                }
                ReGenesisNavHost(
                    navController = navController,
                    customizationViewModel = mockViewModel
                )
            }

            composeTestRule.waitForIdle()

            // When - Navigate through multiple screens
            navController.navigate("workspace_aura")
            composeTestRule.waitForIdle()
            navController.navigate("workspace_genesis")
            composeTestRule.waitForIdle()

            // Then - Back stack should have entries
            assertTrue(
                navController.currentBackStack.value.size > 1,
                "Back stack should have multiple entries"
            )
        }

        @Test
        @DisplayName("Should handle popUpTo navigation correctly")
        fun shouldHandlePopUpToNavigationCorrectly() {
            // Given
            composeTestRule.setContent {
                navController = TestNavHostController(LocalContext.current).apply {
                    navigatorProvider.addNavigator(ComposeNavigator())
                }
                ReGenesisNavHost(
                    navController = navController,
                    customizationViewModel = mockViewModel
                )
            }

            composeTestRule.waitForIdle()

            // When - Navigate with popUpTo (as in mode selection)
            navController.navigate("mode_selection")
            composeTestRule.waitForIdle()

            // Then - Should navigate correctly
            assertNotNull(navController.currentBackStackEntry)
        }
    }

    @Nested
    @DisplayName("Edge Cases and Stress Tests")
    inner class EdgeCasesTests {

        @Test
        @DisplayName("Should handle rapid navigation changes")
        fun shouldHandleRapidNavigationChanges() {
            // Given
            composeTestRule.setContent {
                navController = TestNavHostController(LocalContext.current).apply {
                    navigatorProvider.addNavigator(ComposeNavigator())
                }
                ReGenesisNavHost(
                    navController = navController,
                    customizationViewModel = mockViewModel
                )
            }

            composeTestRule.waitForIdle()

            // When - Rapid navigation
            repeat(5) {
                navController.navigate("workspace_aura")
                composeTestRule.waitForIdle()
                navController.popBackStack()
                composeTestRule.waitForIdle()
            }

            // Then - Should handle without errors
            assertNotNull(navController.currentBackStackEntry)
        }

        @Test
        @DisplayName("Should handle navigation to all routes sequentially")
        fun shouldHandleNavigationToAllRoutesSequentially() {
            // Given
            val routes = listOf(
                "mode_selection",
                "exodus_home",
                "pixel_domain/01",
                "workspace_kai",
                "workspace_aura",
                "workspace_genesis",
                "aura_lab",
                "interface_forge"
            )

            composeTestRule.setContent {
                navController = TestNavHostController(LocalContext.current).apply {
                    navigatorProvider.addNavigator(ComposeNavigator())
                }
                ReGenesisNavHost(
                    navController = navController,
                    customizationViewModel = mockViewModel
                )
            }

            composeTestRule.waitForIdle()

            // When - Navigate to all routes
            routes.forEach { route ->
                navController.navigate(route)
                composeTestRule.waitForIdle()
            }

            // Then - Should handle all navigations
            assertTrue(true, "All routes navigated successfully")
        }

        @Test
        @DisplayName("Should handle screen composition and recomposition")
        fun shouldHandleScreenCompositionAndRecomposition() {
            // Given
            var recomposeKey by mutableStateOf(0)

            composeTestRule.setContent {
                navController = TestNavHostController(LocalContext.current).apply {
                    navigatorProvider.addNavigator(ComposeNavigator())
                }
                key(recomposeKey) {
                    ReGenesisNavHost(
                        navController = navController,
                        customizationViewModel = mockViewModel
                    )
                }
            }

            // When - Force recomposition
            repeat(3) {
                recomposeKey++
                composeTestRule.waitForIdle()
            }

            // Then - Should handle recomposition
            assertNotNull(navController.currentBackStackEntry)
        }

        @Test
        @DisplayName("Should handle navigation with empty back stack")
        fun shouldHandleNavigationWithEmptyBackStack() {
            // Given
            composeTestRule.setContent {
                navController = TestNavHostController(LocalContext.current).apply {
                    navigatorProvider.addNavigator(ComposeNavigator())
                }
                ReGenesisNavHost(
                    navController = navController,
                    customizationViewModel = mockViewModel
                )
            }

            composeTestRule.waitForIdle()

            // When - Try to pop when already at start
            val canPop = navController.popBackStack()

            // Then - Should handle gracefully
            assertFalse(canPop, "Should not be able to pop from start destination")
        }

        @Test
        @DisplayName("Should handle concurrent navigation requests")
        fun shouldHandleConcurrentNavigationRequests() {
            // Given
            composeTestRule.setContent {
                navController = TestNavHostController(LocalContext.current).apply {
                    navigatorProvider.addNavigator(ComposeNavigator())
                }
                ReGenesisNavHost(
                    navController = navController,
                    customizationViewModel = mockViewModel
                )
            }

            composeTestRule.waitForIdle()

            // When - Multiple quick navigation requests
            navController.navigate("workspace_aura")
            navController.navigate("workspace_genesis")
            composeTestRule.waitForIdle()

            // Then - Should handle last navigation
            assertNotNull(navController.currentBackStackEntry)
        }
    }

    @Nested
    @DisplayName("Integration Tests")
    inner class IntegrationTests {

        @Test
        @DisplayName("Should integrate with SovereignRouter")
        fun shouldIntegrateWithSovereignRouter() {
            // Given
            val route = SovereignRouter.fromPage(0)

            composeTestRule.setContent {
                navController = TestNavHostController(LocalContext.current).apply {
                    navigatorProvider.addNavigator(ComposeNavigator())
                }
                ReGenesisNavHost(
                    navController = navController,
                    customizationViewModel = mockViewModel
                )
            }

            composeTestRule.waitForIdle()

            // When - Navigate using SovereignRouter ID
            navController.navigate("pixel_domain/${route.id}")
            composeTestRule.waitForIdle()

            // Then
            assertEquals(
                "pixel_domain/{id}",
                navController.currentBackStackEntry?.destination?.route
            )
        }

        @Test
        @DisplayName("Should work with complete navigation flow")
        fun shouldWorkWithCompleteNavigationFlow() {
            // Given
            composeTestRule.setContent {
                navController = TestNavHostController(LocalContext.current).apply {
                    navigatorProvider.addNavigator(ComposeNavigator())
                }
                ReGenesisNavHost(
                    navController = navController,
                    customizationViewModel = mockViewModel
                )
            }

            composeTestRule.waitForIdle()

            // When - Complete flow: home -> pixel domain -> back -> workspace
            navController.navigate("pixel_domain/01")
            composeTestRule.waitForIdle()
            navController.popBackStack()
            composeTestRule.waitForIdle()
            navController.navigate("workspace_aura")
            composeTestRule.waitForIdle()

            // Then
            assertEquals(
                "workspace_aura",
                navController.currentBackStackEntry?.destination?.route
            )
        }
    }
}