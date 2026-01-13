        composable(route = NavDestination.AgentMonitoring.route) {
            AgentMonitoringScreen()
        }

        composable(route = NavDestination.FusionMode.route) {
            FusionModeScreen()
        }

        composable(route = NavDestination.CodeAssist.route) {
            CodeAssistScreen(navController = navController)
        }

        // ==================== ORACLE DRIVE ====================

        composable(route = NavDestination.OracleDrive.route) {
        }

        composable(route = NavDestination.SphereGrid.route) {
            SphereGridScreen(navController = navController)
        }

        composable(route = NavDestination.Constellation.route) {
            ConstellationScreen(navController = navController)
        }

        composable(route = NavDestination.GenesisConstellation.route) {
            GenesisConstellationScreen(navController = navController)
        }

        composable(route = NavDestination.ClaudeConstellation.route) {
            ClaudeConstellationScreen(navController = navController)
        }

        composable(route = NavDestination.KaiConstellation.route) {
            KaiConstellationScreen(navController = navController)
        }

        composable(route = NavDestination.CascadeConstellation.route) {
            CascadeConstellationScreen(navController = navController)
        }

        composable(route = NavDestination.GrokConstellation.route) {
            GrokConstellationScreen(navController = navController)
        }

        // ==================== ROM TOOLS ====================

        composable(route = NavDestination.ROMTools.route) {
            ROMToolsSubmenuScreen(navController = navController)
        }

        composable(route = NavDestination.LiveROMEditor.route) {
            LiveROMEditorScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(route = NavDestination.ROMFlasher.route) {
            ROMFlasherScreen()
        }

        composable(route = NavDestination.RecoveryTools.route) {
            RecoveryToolsScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(route = NavDestination.BootloaderManager.route) {
            BootloaderManagerScreen(onNavigateBack = { navController.popBackStack() })
        }

        // ==================== LSPOSED INTEGRATION ====================

        composable(route = NavDestination.LSPosedGate.route) {
            LSPosedSubmenuScreen(navController = navController)
        }

        composable(route = NavDestination.ModuleManager.route) {
            ModuleManagerScreen()
        }

        composable(route = NavDestination.LSPosedModuleManager.route) {
            LSPosedModuleManagerScreen()
        }

        composable(route = NavDestination.ModuleCreation.route) {
            ModuleCreationScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(route = NavDestination.HookManager.route) {
            HookManagerScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(route = NavDestination.LogsViewer.route) {
            LogsViewerScreen(onNavigateBack = { navController.popBackStack() })
        }

        // ==================== UI/UX DESIGN STUDIO ====================

        composable(route = NavDestination.UIUXDesignStudio.route) {
            UIUXGateSubmenuScreen(navController = navController)
        }

        composable(route = NavDestination.ThemeEngine.route) {
            ThemeEngineScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(route = NavDestination.StatusBar.route) {
            StatusBarScreen()
        }

        composable(route = NavDestination.NotchBar.route) {
            NotchBarScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(route = NavDestination.QuickSettings.route) {
            QuickSettingsScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(route = NavDestination.OverlayMenus.route) {
            OverlayMenusScreen()
        }

        composable(route = NavDestination.QuickActions.route) {
            QuickActionsScreen()
        }

        composable(route = NavDestination.SystemOverrides.route) {
            SystemOverridesScreen(onNavigateBack = { navController.popBackStack() })
        }

        // ==================== HELP DESK ====================

        composable(route = NavDestination.HelpDesk.route) {
            HelpDeskSubmenuScreen(navController = navController)
        }

        composable(route = NavDestination.LiveSupport.route) {
            val viewModel = hiltViewModel<SupportChatViewModel>()
            LiveSupportChatScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(route = NavDestination.Documentation.route) {
            DocumentationScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(route = NavDestination.FAQBrowser.route) {
            FAQBrowserScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(route = NavDestination.TutorialVideos.route) {
            TutorialVideosScreen(onNavigateBack = { navController.popBackStack() })
        }

        // ==================== AURA'S LAB ====================

        composable(route = NavDestination.AurasLab.route) {
            AurasLabScreen(onNavigateBack = { navController.popBackStack() })
        }

        // ==================== CUSTOMIZATION TOOLS ====================

        composable(route = NavDestination.ComponentEditor.route) {
            // Recommendation: If you have a state-holder for the selected component, use it here
            ComponentEditor(
                component = UIComponent(
                    id = "current",
                    name = "Editor",
                    type = ComponentType.STATUS_BAR,
                    height = 50f,
                    backgroundColor = Color.White,
                    animationType = AnimationType.NONE,
                ),
                onUpdate = { },
                onClose = { navController.popBackStack() }
            )
        }

        composable(route = NavDestination.ZOrderEditor.route) {
            ZOrderEditor(
                elements = emptyList(),
                onReorder = { },
                onElementSelected = { },
                onClose = { navController.popBackStack() }
            )
        }

        // ==================== IDENTITY ====================

        composable(route = NavDestination.GenderSelection.route) {
            GenderSelectionNavigator(
                onGenderSelected = { navController.popBackStack() }
            )
        }
    }
}
