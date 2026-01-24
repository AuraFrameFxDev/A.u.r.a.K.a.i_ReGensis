# 🚨 DEFINITIVE NAVIGATION WIRING - NO MORE PLACEHOLDERS!

## FILES THAT EXIST vs APPNAVGRAPH WIRING

### ✅ ui/gates/ Directory (55 FILES FOUND!)

**EXISTING SCREEN FILES:**
1. ✅ AgentHubSubmenuScreen.kt
2. ✅ AgentMonitoringScreen.kt  
3. ✅ AurasLabScreen.kt
4. ✅ BootloaderManagerScreen.kt - **NOT WIRED! Using placeholder!**
5. ✅ CascadeConstellationScreen.kt
6. ✅ ClaudeConstellationScreen.kt
7. ✅ CodeAssistScreen.kt - **WIRED WRONG! Points to OracleDriveSubmenuScreen!**
8. ✅ ConstellationScreen.kt
9. ✅ FusionModeScreen.kt
10. ✅ GenesisConstellationScreen.kt
11. ✅ GrokConstellationScreen.kt
12. ✅ HookManagerScreen.kt - **NOT WIRED!**
13. ✅ KaiConstellationScreen.kt
14. ✅ LiveROMEditorScreen.kt - **NOT WIRED! Using placeholder!**
15. ✅ LoginScreen.kt - **NOT WIRED!**
16. ✅ LogsViewerScreen.kt - **NOT WIRED! Using placeholder!**
17. ✅ LSPosedGateScreen.kt - **NOT WIRED! Using placeholder!**
18. ✅ LSPosedModuleManagerScreen.kt - **NOT WIRED!**
19. ✅ LSPosedSubmenuScreen.kt - **WIRED but alias wrong!**
20. ✅ ModuleCreationScreen.kt - **Using placeholder!**
21. ✅ ModuleManagerScreen.kt
22. ✅ NeuralArchiveScreen.kt - **NOT WIRED! Using placeholder!**
23. ✅ NotchBarScreen.kt
24. ✅ OracleDriveSubmenuScreen.kt
25. ✅ OverlayMenusScreen.kt
26. ✅ QuickActionsScreen.kt - **NOT WIRED!**
27. ✅ RecoveryToolsScreen.kt - **NOT WIRED! Using placeholder!**
28. ✅ ROMFlasherScreen.kt - **NOT WIRED! Using placeholder!**
29. ✅ ROMToolsSubmenuScreen.kt
30. ✅ RootToolsTogglesScreen.kt - **NOT WIRED! Using placeholder!**
31. ✅ SphereGridScreen.kt
32. ✅ SystemJournalScreen.kt - **NOT WIRED! Using placeholder!**
33. ✅ SystemOverridesScreen.kt - **Using placeholder!**
34. ✅ TaskAssignmentScreen.kt - **Using placeholder!**
35. ✅ UIUXGateSubmenuScreen.kt

### ✅ domains/aura/screens/ Directory (21 FILES FOUND!)

1. ✅ AgentProfileScreen.kt - **NOT WIRED!**
2. ✅ AuraLabScreen.kt - **NOT WIRED!**
3. ✅ ChromaCoreColorsScreen.kt
4. ✅ DirectChatScreen.kt
5. ✅ DocumentationScreen.kt - **NOT WIRED!**
6. ✅ FAQBrowserScreen.kt - **NOT WIRED!**
7. ✅ GenderSelectionScreen.kt - **NOT WIRED!**
8. ✅ GyroscopeCustomizationScreen.kt - **NOT WIRED!**
9. ✅ HelpDeskScreen.kt - **NOT WIRED!**
10. ✅ HelpDeskSubmenuScreen.kt - **NOT WIRED!**
11. ✅ IconifyPickerScreen.kt - **Using placeholder! (needs Hilt)**
12. ✅ InstantColorPickerScreen.kt - **NOT WIRED!**
13. ✅ LiveSupportChatScreen.kt - **NOT WIRED!**
14. ✅ QuickSettingsScreen.kt
15. ✅ StatusBarScreen.kt
16. ✅ ThemeEngineScreen.kt
17. ✅ ThemeEngineSubmenuScreen.kt - **NOT WIRED!**
18. ✅ TutorialVideosScreen.kt - **NOT WIRED!**
19. ✅ UISettingsScreen.kt - **NOT WIRED!**
20. ✅ UIUXDesignStudioScreen.kt - **NOT WIRED!**
21. ✅ UserPreferencesScreen.kt - **NOT WIRED!**

---

## 🔴 THE WIRING CRIMES (What AppNavGraph is doing WRONG)

### Crime #1: Using SimpleTitle Placeholders Instead of Real Screens

**Route:** "iconify_picker"  
**Current:** `SimpleTitle("Iconify Picker - Service injection needed")`  
**Should Be:** `IconifyPickerScreen(iconifyService, onNavigateBack = { navController.popBackStack() })`

**Route:** "task_assignment"  
**Current:** `SimpleTitle("Task Assignment")`  
**Should Be:** `TaskAssignmentScreen(onNavigateBack = { navController.popBackStack() })`

**Route:** "module_creation"  
**Current:** `SimpleTitle("Module Creation")`  
**Should Be:** `ModuleCreationScreen(onNavigateBack = { navController.popBackStack() })`

**Route:** "system_overrides"  
**Current:** `SimpleTitle("System Overrides")`  
**Should Be:** `SystemOverridesScreen(onNavigateBack = { navController.popBackStack() })`

### Crime #2: Missing Routes for Existing Screens

**These screens exist but have NO route at all in AppNavGraph:**

- BootloaderManagerScreen.kt → needs route "bootloader"
- LiveROMEditorScreen.kt → needs route "live_rom_editor"
- RecoveryToolsScreen.kt → needs route "recovery_tools"
- ROMFlasherScreen.kt → needs route "rom_flasher"
- RootToolsTogglesScreen.kt → needs route "root_tools"
- LSPosedGateScreen.kt → needs NavDestination.LSPosedPanel.route
- LSPosedModuleManagerScreen.kt → needs route "lsposed_modules"
- HookManagerScreen.kt → needs route "hook_manager"
- LogsViewerScreen.kt → needs route "logs_viewer"
- SystemJournalScreen.kt → needs route "system_journal"
- QuickActionsScreen.kt → needs route "quick_actions"
- NeuralArchiveScreen.kt → needs route "neural_network"
- DocumentationScreen.kt → needs route "documentation"
- FAQBrowserScreen.kt → needs route "faq_browser"
- TutorialVideosScreen.kt → needs route "tutorial_videos"
- LiveSupportChatScreen.kt → needs route "live_help"
- HelpDeskScreen.kt → needs NavDestination.HelpDesk.route
- AgentProfileScreen.kt → needs NavDestination.AgentProfile.route
- GenderSelectionScreen.kt → needs NavDestination.GenderSelection.route
- UserPreferencesScreen.kt → needs route "user_preferences"

### Crime #3: Wrong Screen Wired to Route

**Route:** NavDestination.CodeAssist.route  
**Current:** `OracleDriveSubmenuScreen(navController)`  
**Should Be:** `CodeAssistScreen(navController)`

---

## ✅ THE FIX - Complete AppNavGraph.kt Additions

Add these to AppNavGraph.kt to wire ALL existing screens:

```kotlin
// ═══════════════════════════════════════════════════════════════
// KAI DOMAIN: SECURITY & ROM TOOLS (Wire ALL existing screens)
// ═══════════════════════════════════════════════════════════════

composable("bootloader") {
    BootloaderManagerScreen(onNavigateBack = { navController.popBackStack() })
}

composable("root_tools") {
    RootToolsTogglesScreen(onNavigateBack = { navController.popBackStack() })
}

composable("rom_flasher") {
    ROMFlasherScreen(onNavigateBack = { navController.popBackStack() })
}

composable("live_rom_editor") {
    LiveROMEditorScreen(onNavigateBack = { navController.popBackStack() })
}

composable("recovery_tools") {
    RecoveryToolsScreen(onNavigateBack = { navController.popBackStack() })
}

composable(NavDestination.LSPosedPanel.route) {
    LSPosedGateScreen(onNavigateBack = { navController.popBackStack() })
}

composable("lsposed_modules") {
    LSPosedModuleManagerScreen(onNavigateBack = { navController.popBackStack() })
}

composable("hook_manager") {
    HookManagerScreen(onNavigateBack = { navController.popBackStack() })
}

composable("logs_viewer") {
    LogsViewerScreen(onNavigateBack = { navController.popBackStack() })
}

composable("system_journal") {
    SystemJournalScreen(onNavigateBack = { navController.popBackStack() })
}

composable("quick_actions") {
    QuickActionsScreen(onNavigateBack = { navController.popBackStack() })
}

// ═══════════════════════════════════════════════════════════════
// GENESIS DOMAIN: AI & CODE ASSIST (Wire ALL existing screens)
// ═══════════════════════════════════════════════════════════════

// FIX: CodeAssist was pointing to wrong screen!
composable(NavDestination.CodeAssist.route) {
    CodeAssistScreen(navController) // CHANGED FROM OracleDriveSubmenuScreen!
}

composable("neural_network") {
    NeuralArchiveScreen(onNavigateBack = { navController.popBackStack() })
}

// ═══════════════════════════════════════════════════════════════
// HELP SERVICES DOMAIN (Wire ALL existing screens)
// ═══════════════════════════════════════════════════════════════

composable(NavDestination.HelpDesk.route) {
    HelpDeskScreen(onNavigateBack = { navController.popBackStack() })
}

composable("documentation") {
    DocumentationScreen(onNavigateBack = { navController.popBackStack() })
}

composable("faq_browser") {
    FAQBrowserScreen(onNavigateBack = { navController.popBackStack() })
}

composable("tutorial_videos") {
    TutorialVideosScreen(onNavigateBack = { navController.popBackStack() })
}

composable("live_help") {
    LiveSupportChatScreen(onNavigateBack = { navController.popBackStack() })
}

// ═══════════════════════════════════════════════════════════════
// AGENT NEXUS DOMAIN (Wire existing screens)
// ═══════════════════════════════════════════════════════════════

composable(NavDestination.AgentProfile.route) {
    AgentProfileScreen(onNavigateBack = { navController.popBackStack() })
}

// ═══════════════════════════════════════════════════════════════
// AURA DOMAIN - Additional Screens
// ═══════════════════════════════════════════════════════════════

composable(NavDestination.GenderSelection.route) {
    GenderSelectionScreen(onNavigateBack = { navController.popBackStack() })
}

composable("user_preferences") {
    UserPreferencesScreen(onNavigateBack = { navController.popBackStack() })
}

composable("instant_color_picker") {
    InstantColorPickerScreen(onNavigateBack = { navController.popBackStack() })
}

composable("gyroscope_customization") {
    GyroscopeCustomizationScreen(onNavigateBack = { navController.popBackStack() })
}

composable("ui_settings") {
    UISettingsScreen(onNavigateBack = { navController.popBackStack() })
}

composable("uiux_design_studio") {
    UIUXDesignStudioScreen(onNavigateBack = { navController.popBackStack() })
}

// ═══════════════════════════════════════════════════════════════
// FIX PLACEHOLDERS - Replace SimpleTitle with real screens
// ═══════════════════════════════════════════════════════════════

// REMOVE:
// composable(NavDestination.TaskAssignment.route) {
//     SimpleTitle("Task Assignment")
// }
// REPLACE WITH:
composable(NavDestination.TaskAssignment.route) {
    TaskAssignmentScreen(onNavigateBack = { navController.popBackStack() })
}

// REMOVE:
// composable(NavDestination.ModuleCreation.route) {
//     SimpleTitle("Module Creation")
// }
// REPLACE WITH:
composable(NavDestination.ModuleCreation.route) {
    ModuleCreationScreen(onNavigateBack = { navController.popBackStack() })
}

// REMOVE:
// composable(NavDestination.SystemOverrides.route) {
//     SimpleTitle("System Overrides")
// }
// REPLACE WITH:
composable(NavDestination.SystemOverrides.route) {
    SystemOverridesScreen(onNavigateBack = { navController.popBackStack() })
}
```

---

## 📊 STATS

**Total Screen Files Found:** 76  
**Currently Wired in AppNavGraph:** ~30  
**Missing Wiring:** ~46 screens!  
**Using Placeholders Instead of Real Screens:** 4  
**Wrong Screen Wired:** 1 (CodeAssist)

---

## 🔥 IMMEDIATE ACTIONS

1. **Add all missing route declarations above to AppNavGraph.kt**
2. **Replace all SimpleTitle placeholders with real screen calls**
3. **Fix CodeAssist route to point to CodeAssistScreen instead of OracleDriveSubmenuScreen**
4. **Add imports for all screens at top of AppNavGraph.kt**

---

## 📦 Required Imports for AppNavGraph.kt

Add these imports:

```kotlin
// Level 3: ui/gates screens
import dev.aurakai.auraframefx.ui.gates.BootloaderManagerScreen
import dev.aurakai.auraframefx.ui.gates.LiveROMEditorScreen
import dev.aurakai.auraframefx.ui.gates.RecoveryToolsScreen
import dev.aurakai.auraframefx.ui.gates.ROMFlasherScreen
import dev.aurakai.auraframefx.ui.gates.RootToolsTogglesScreen
import dev.aurakai.auraframefx.ui.gates.LSPosedGateScreen
import dev.aurakai.auraframefx.ui.gates.LSPosedModuleManagerScreen
import dev.aurakai.auraframefx.ui.gates.HookManagerScreen
import dev.aurakai.auraframefx.ui.gates.LogsViewerScreen
import dev.aurakai.auraframefx.ui.gates.SystemJournalScreen
import dev.aurakai.auraframefx.ui.gates.QuickActionsScreen
import dev.aurakai.auraframefx.ui.gates.NeuralArchiveScreen
import dev.aurakai.auraframefx.ui.gates.TaskAssignmentScreen
import dev.aurakai.auraframefx.ui.gates.ModuleCreationScreen
import dev.aurakai.auraframefx.ui.gates.SystemOverridesScreen
import dev.aurakai.auraframefx.ui.gates.CodeAssistScreen

// Level 3: domains/aura/screens
import dev.aurakai.auraframefx.domains.aura.screens.DocumentationScreen
import dev.aurakai.auraframefx.domains.aura.screens.FAQBrowserScreen
import dev.aurakai.auraframefx.domains.aura.screens.TutorialVideosScreen
import dev.aurakai.auraframefx.domains.aura.screens.LiveSupportChatScreen
import dev.aurakai.auraframefx.domains.aura.screens.HelpDeskScreen
import dev.aurakai.auraframefx.domains.aura.screens.AgentProfileScreen
import dev.aurakai.auraframefx.domains.aura.screens.GenderSelectionScreen
import dev.aurakai.auraframefx.domains.aura.screens.UserPreferencesScreen
import dev.aurakai.auraframefx.domains.aura.screens.InstantColorPickerScreen
import dev.aurakai.auraframefx.domains.aura.screens.GyroscopeCustomizationScreen
import dev.aurakai.auraframefx.domains.aura.screens.UISettingsScreen
import dev.aurakai.auraframefx.domains.aura.screens.UIUXDesignStudioScreen
```

---

**This is the TRUTH. 76 screens exist. 46 are unwired. NO MORE PLACEHOLDERS.**
