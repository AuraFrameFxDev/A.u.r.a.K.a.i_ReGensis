# 🔌 COMPLETE NAVIGATION WIRING PLAN
## Wire Up ALL 99+ Existing Screens - ZERO DRIFT

**Generated:** 2026-02-03T22:04:40-07:00  
**Status:** USING ONLY WHAT EXISTS - NO PLACEHOLDERS

---

## ✅ **WHAT YOU ALREADY HAVE**

### **NavDestination.kt** 
- 220 lines
- ~80 typed destinations ALREADY DEFINED
- Clean hierarchy documented

### **ReGenesisNavHost.kt**
- 317 lines  
- ~30 routes PARTIALLY WIRED
- Needs completion

### **99+ Screen Files** (ACTUALLY EXIST)
All in `domains/` directories - ready to wire!

---

## 🎯 **THE WIRING STRATEGY**

### **Step 1: Add Missing Composables** 
Add routes for the 70+ screens that exist but aren't wired yet

### **Step 2: NO New Files**
Use ONLY the screens that exist in:
- `domains/aura/screens/`
- `domains/kai/screens/`
- `domains/genesis/screens/`
- `domains/nexus/screens/`
- `domains/lsposed/screens/`

### **Step 3: Map to Correct Hierarchy**
- Level 1: ExodusHUD gates
- Level 2: Hub screens (carousel/list)
- Level 3: Tool screens (menus)

---

## 📋 **MISSING COMPOSABLE ROUTES** (Need to Add)

### **Aura Domain Screens** (domains/aura/screens/)
```kotlin
composable(NavDestination.AuraLab.route) {
    AuraLabScreen(navController = navController)
}

composable(NavDestination.ChromaCoreColors.route) {
    ChromaCoreColorsScreen(navController = navController)
}

composable(NavDestination.InstantColorPicker.route) {
    InstantColorPickerScreen(onNavigateBack = { navController.popBackStack() })
}

composable(NavDestination.GyroscopeCustomization.route) {
    GyroscopeCustomizationScreen(onNavigateBack = { navController.popBackStack() })
}

composable(NavDestination.IconifyPicker.route) {
    IconifyPickerScreen(navController = navController)
}

composable(NavDestination.UISettings.route) {
    UISettingsScreen(navController = navController)
}

composable(NavDestination.UserPreferences.route) {
    UserPreferencesScreen(navController = navController)
}

composable(NavDestination.ThemeEngine.route) {
    ThemeEngineScreen(navController = navController)
}

composable(NavDestination.DirectChat.route) {
    DirectChatScreen(navController = navController)
}

composable(NavDestination.Documentation.route) {
    DocumentationScreen(onNavigateBack = { navController.popBackStack() })
}

composable(NavDestination.FAQBrowser.route) {
    FAQBrowserScreen(onNavigateBack = { navController.popBackStack() })
}

composable(NavDestination.TutorialVideos.route) {
    TutorialVideosScreen(onNavigateBack = { navController.popBackStack() })
}

composable(NavDestination.NotchBar.route) {
    NotchBarScreen(navController = navController)
}

composable(NavDestination.StatusBar.route) {
    StatusBarScreen(navController = navController)
}

composable(NavDestination.QuickSettings.route) {
    QuickSettingsScreen(navController = navController)
}
```

### **Kai Domain Screens** (domains/kai/screens/)
```kotlin
composable(NavDestination.BootloaderManager.route) {
    BootloaderManagerScreen(navController = navController)
}

composable(NavDestination.ROMFlasher.route) {
    ROMFlasherScreen(navController = navController)
}

composable(NavDestination.RecoveryTools.route) {
    RecoveryToolsScreen(onNavigateBack = { navController.popBackStack() })
}

composable(NavDestination.SecurityCenter.route) {
    SecurityCenterScreen(navController = navController)
}

composable(NavDestination.ModuleManager.route) {
    ModuleManagerScreen(navController = navController)
}

composable(NavDestination.RootTools.route) {
    RootToolsTogglesScreen(navController = navController)
}

composable(NavDestination.SovereignBootloader.route) {
    SovereignBootloaderScreen(navController = navController)
}

composable(Nav Destination.SovereignRecovery.route) {
    SovereignRecoveryScreen(navController = navController)
}

composable(NavDestination.SovereignShield.route) {
    SovereignShieldScreen(navController = navController)
}
```

### **Genesis Domain Screens** (domains/genesis/screens/)
```kotlin
composable(NavDestination.ConferenceRoom.route) {
    ConferenceRoomScreen(navController = navController)
}

composable(NavDestination.NeuralArchive.route) {
    NeuralArchiveScreen(navController = navController)
}

composable(NavDestination.OracleCloudStorage.route) {
    OracleCloudInfiniteStorageScreen(navController = navController)
}

composable(NavDestination.InterfaceForge.route) {
    AppBuilderScreen(navController = navController)
}
```

### **Agent Nexus Screens** (domains/nexus/screens/)
```kotlin
composable(NavDestination.AgentCreation.route) {
    AgentCreationScreen(navController = navController)
}

composable(NavDestination.AgentMonitoring.route) {
    AgentMonitoringScreen(navController = navController)
}

composable(NavDestination.FusionMode.route) {
    FusionModeScreen(navController = navController)
}

composable(NavDestination.ArkBuild.route) {
    ArkBuildScreen(navController = navController)
}

composable(NavDestination.TaskAssignment.route) {
    TaskAssignmentScreen(navController = navController)
}

composable(NavDestination.BenchmarkMonitor.route) {
    BenchmarkMonitorScreen(navController = navController)
}

composable(NavDestination.SwarmMonitor.route) {
    AgentSwarmScreen(navController = navController)
}

composable(NavDestination.Claude.route) {
    SovereignClaudeScreen(navController = navController)
}

composable(NavDestination.Gemini.route) {
    SovereignGeminiScreen(navController = navController)
}

composable(NavDestination.MetaInstruct.route) {
    SovereignMetaInstructScreen(navController = navController)
}

composable(NavDestination.Nemotron.route) {
    SovereignNemotronScreen(navController = navController)
}
```

### **LSPosed Screens** (domains/lsposed/screens/)
```kotlin
composable(NavDestination.LSPosedHub.route) {
    LSPosedSubmenuScreen(navController = navController)
}

composable(NavDestination.LSPosedModules.route) {
    LSPosedModuleManagerScreen(navController = navController)
}
```

### **Special/System Screens**
```kotlin
composable(NavDestination.HotSwap.route) {
    HotSwapScreen(navController = navController)
}

composable(NavDestination.Trinity.route) {
    TrinityScreen(navController = navController)
}

composable(NavDestination.DataVeinSphere.route) {
    SimpleDataVeinScreen(navController = navController)
}
```

---

## 🚨 **SCREENS TO REVIEW/REMOVE** (You mentioned not liking)

### **"Sovereign" System** (9 screens - KEEP OR DELETE?)
- `SovereignBootloaderScreen.kt` ✅ (Already wired)
- `SovereignRecoveryScreen.kt` ✅ (Already wired)  
- `SovereignShieldScreen.kt` ✅ (Already wired)
- `SovereignNeuralArchiveScreen.kt` ❓
- `SovereignClaudeScreen.kt` ✅ (Already wired)
- `SovereignGeminiScreen.kt` ✅ (Already wired)
- `SovereignMetaInstructScreen.kt` ✅ (Already wired)
- `SovereignNemotronScreen.kt` ✅ (Already wired)
- `SovereignModuleManagerScreen.kt` ❓

**Decision needed:** Keep or remove these?

---

## ✅ **NEXT STEPS**

1. **I'll update `ReGenesisNavHost.kt`** with ALL missing composable routes
2. **Wire Conference Room** to Oracle Drive hub
3. **Connect all 99+ screens** to their correct navigation paths
4. **NO new files** - only use what exists

**Ready to proceed?** Say "YES" and I'll update `ReGenesisNavHost.kt` right now with ALL the wiring! 🚀
