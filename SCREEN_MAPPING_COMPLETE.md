# 🗺️ REGENESIS COMPLETE SCREEN MAPPING
# All screens categorized by domain with suggested navigation routes

## 📱 TOTAL SCREEN COUNT: 131 screens

---

## 🎨 AURA DOMAIN (Creative UX/UI) - 25 screens
**Hub:** AuraThemingHubScreen.kt ✅ WIRED

### LEVEL 3 - Aura Tools (should appear in AuraThemingHub carousel):

1. **AgentProfileScreen.kt** 
   - Route: `agent_profile`
   - Purpose: Agent customization/profile editor
   - Status: ❌ UNWIRED

2. **AuraLabScreen.kt** ✅ WIRED
   - Route: `aura_lab` (maps to SandboxUIScreen)
   - Purpose: Sandbox/prototyping environment

3. **ChromaCoreColorsScreen.kt**
   - Route: `chromacore_colors`
   - Purpose: Advanced color picker/palette editor
   - Status: ❌ UNWIRED

4. **DirectChatScreen.kt**
   - Route: `direct_chat`
   - Purpose: 1-on-1 chat with specific agent
   - Status: ❌ UNWIRED

5. **DocumentationScreen.kt**
   - Route: `documentation`
   - Purpose: In-app documentation browser
   - Status: ❌ UNWIRED (should be in HelpDesk)

6. **FAQBrowserScreen.kt**
   - Route: `faq_browser`
   - Purpose: FAQ search/browse
   - Status: ❌ UNWIRED (should be in HelpDesk)

7. **GenderSelectionScreen.kt**
   - Route: `gender_selection`
   - Purpose: Agent gender/voice selection
   - Status: ❌ UNWIRED

8. **GyroscopeCustomizationScreen.kt**
   - Route: `gyroscope_settings`
   - Purpose: Gyroscope-based UI controls
   - Status: ❌ UNWIRED

9. **HelpDeskScreen.kt** ✅ WIRED
   - Route: `help_desk`

10. **HelpDeskSubmenuScreen.kt**
    - Route: `help_desk_submenu`
    - Status: ❌ UNWIRED

11. **IconifyPickerScreen.kt** ✅ WIRED (via auraCustomizationNavigation)
    - Route: `iconify_picker`

12. **InstantColorPickerScreen.kt**
    - Route: `instant_color_picker`
    - Purpose: Quick color selection tool
    - Status: ❌ UNWIRED

13. **LiveSupportChatScreen.kt**
    - Route: `live_support_chat`
    - Purpose: Live chat with human support
    - Status: ❌ UNWIRED (should be in HelpDesk)

14. **NotchBarScreen.kt** ✅ WIRED
    - Route: `notch_bar`

15. **QuickSettingsScreen.kt** ✅ WIRED
    - Route: `quick_settings`

16. **SandboxUIScreen.kt** ✅ WIRED (as AuraLab)
    - Route: `aura_lab`

17. **StatusBarScreen.kt** ✅ WIRED
    - Route: `status_bar`

18. **ThemeEngineScreen.kt**
    - Route: `theme_engine`
    - Status: ❌ UNWIRED (different from ThemeEngineSubmenu)

19. **ThemeEngineSubmenuScreen.kt**
    - Route: `theme_engine_submenu`
    - Status: ❌ UNWIRED

20. **TutorialVideosScreen.kt**
    - Route: `tutorial_videos`
    - Status: ❌ UNWIRED (should be in HelpDesk)

21. **UISettingsScreen.kt**
    - Route: `ui_settings`
    - Purpose: General UI configuration
    - Status: ❌ UNWIRED

22. **UserPreferencesScreen.kt**
    - Route: `user_preferences`
    - Purpose: User profile/settings
    - Status: ❌ UNWIRED

23. **IconifyHubScreen.kt** (in ui/screens/aura/)
    - Route: `iconify_hub`
    - Purpose: Iconify category browser
    - Status: ❌ UNWIRED

24. **ReGenesisCustomizationHub.kt** ✅ WIRED
    - Route: `regenesis_customization`

25. **ReGenesisCustomizationScreens.kt**
    - Contains: ColorBlendrScreen, PixelLauncherEnhancedScreen, etc.
    - Status: ✅ WIRED (via auraCustomizationNavigation)

**AURA SUMMARY: 9/25 wired (36%)**

---

## 🧬 GENESIS DOMAIN (AI Core/Oracle Drive) - 12 screens
**Hub:** OracleDriveHubScreen.kt ✅ WIRED

### LEVEL 3 - Genesis Tools:

1. **AgentBridgeHubScreen.kt**
   - Route: `agent_bridge_hub`
   - Purpose: Agent communication nexus
   - Status: ❌ UNWIRED

2. **AppBuilderScreen.kt**
   - Route: `app_builder`
   - Purpose: Visual app creation tool
   - Status: ❌ UNWIRED

3. **CascadeVisionScreen.kt**
   - Route: `cascade_vision`
   - Purpose: Cascade AI visual interface
   - Status: ❌ UNWIRED

4. **CodeAssistScreen.kt**
   - Route: `code_assist`
   - Purpose: AI code generation/assistance
   - Status: ❌ UNWIRED

5. **CollabCanvasScreen.kt** ✅ WIRED
   - Route: `collab_canvas`

6. **ConferenceRoomScreen.kt** ✅ WIRED
   - Route: `conference_room`

7. **NeuralArchiveScreen.kt** ✅ WIRED
   - Route: `neural_archive`

8. **OracleCloudInfiniteStorageScreen.kt** ✅ WIRED
   - Route: `oracle_cloud_storage`

9. **OracleDriveSubmenuScreen.kt**
   - Route: `oracle_drive_submenu`
   - Status: ❌ UNWIRED

10. **SentientShellScreen.kt**
    - Route: `sentient_shell`
    - Purpose: AI-powered terminal
    - Status: ❌ UNWIRED

11. **SovereignNeuralArchiveScreen.kt**
    - Route: `sovereign_neural_archive`
    - Status: ❌ UNWIRED (duplicate of NeuralArchiveScreen?)

12. **TerminalScreen.kt**
    - Route: `terminal`
    - Purpose: Standard terminal
    - Status: ❌ UNWIRED

**GENESIS SUMMARY: 4/12 wired (33%)**

---

## 🛡️ KAI DOMAIN (Security/ROM Tools) - 16 screens
**Hub:** KaiSentinelHubScreen.kt ✅ WIRED (maps to RomToolsScreen)

### LEVEL 3 - Kai Security Tools:

1. **BootloaderManagerScreen.kt** ✅ WIRED
   - Route: `bootloader`

2. **LiveROMEditorScreen.kt**
   - Route: `live_rom_editor`
   - Purpose: Real-time ROM modification
   - Status: ❌ UNWIRED

3. **LogsViewerScreen.kt**
   - Route: `logs_viewer`
   - Purpose: System log viewer
   - Status: ❌ UNWIRED

4. **ModuleManagerScreen.kt** ✅ WIRED
   - Route: `module_manager`

5. **RecoveryToolsScreen.kt** ✅ WIRED
   - Route: `recovery_tools`

6. **ROMFlasherScreen.kt** ✅ WIRED
   - Route: `rom_flasher`

7. **ROMToolsSubmenuScreen.kt**
   - Route: `rom_tools_submenu`
   - Status: ❌ UNWIRED

8. **RootToolsTogglesScreen.kt**
   - Route: `root_tools_toggles`
   - Purpose: Root privilege management
   - Status: ❌ UNWIRED

9. **SecurityCenterScreen.kt** ✅ WIRED (maps to SovereignShieldScreen)
   - Route: `security_center`

10. **SovereignBootloaderScreen.kt**
    - Route: `sovereign_bootloader`
    - Status: ❌ UNWIRED

11. **SovereignModuleManagerScreen.kt**
    - Route: `sovereign_module_manager`
    - Status: ❌ UNWIRED

12. **SovereignRecoveryScreen.kt**
    - Route: `sovereign_recovery`
    - Status: ❌ UNWIRED

13. **SovereignShieldScreen.kt** ✅ WIRED (as SecurityCenter)
    - Route: `security_center`

14. **SystemJournalScreen.kt**
    - Route: `system_journal`
    - Purpose: System event log
    - Status: ❌ UNWIRED

15. **SystemOverridesScreen.kt**
    - Route: `system_overrides`
    - Purpose: System modification panel
    - Status: ❌ UNWIRED

16. **VPNScreen.kt**
    - Route: `vpn`
    - Purpose: VPN configuration
    - Status: ❌ UNWIRED

**KAI SUMMARY: 6/16 wired (38%)**

---

## 🤖 NEXUS DOMAIN (Agent Hub) - 19 screens
**Hub:** AgentNexusHubScreen.kt ✅ WIRED

### LEVEL 3 - Nexus Agent Tools:

1. **AgentCreationScreen.kt** ✅ WIRED
   - Route: `agent_creation`

2. **AgentHubSubmenuScreen.kt**
   - Route: `agent_hub_submenu`
   - Status: ❌ UNWIRED

3. **AgentMonitoringScreen.kt** ✅ WIRED
   - Route: `agent_monitoring`

4. **AgentNeuralExplorerScreen.kt**
   - Route: `agent_neural_explorer`
   - Purpose: Visual agent neural network
   - Status: ❌ UNWIRED

5. **AgentProfileScreen.kt** (nexus version)
   - Route: `nexus_agent_profile`
   - Status: ❌ UNWIRED (duplicate in aura?)

6. **AgentSwarmScreen.kt** ✅ WIRED
   - Route: `swarm_monitor`

7. **ArkBuildScreen.kt** ✅ WIRED
   - Route: `ark_build`

8. **BenchmarkMonitorScreen.kt** ✅ WIRED
   - Route: `benchmark_monitor`

9. **DataStreamMonitoringScreen.kt**
   - Route: `datastream_monitoring`
   - Purpose: Real-time data flow visualization
   - Status: ❌ UNWIRED

10. **EvolutionTreeScreen.kt**
    - Route: `evolution_tree`
    - Purpose: Agent evolution/lineage tree
    - Status: ❌ UNWIRED

11. **FusionModeScreen.kt** ✅ WIRED (nexus version)
    - Route: `fusion_mode`

12. **ModuleCreationScreen.kt**
    - Route: `module_creation`
    - Purpose: Create custom agent modules
    - Status: ❌ UNWIRED

13. **MonitoringHUDsScreen.kt**
    - Route: `monitoring_huds`
    - Purpose: Customizable monitoring dashboards
    - Status: ❌ UNWIRED

14. **PartyScreen.kt**
    - Route: `party`
    - Purpose: Agent team/party management
    - Status: ❌ UNWIRED

15. **SovereignClaudeScreen.kt** ✅ WIRED
    - Route: `claude`

16. **SovereignGeminiScreen.kt** ✅ WIRED
    - Route: `gemini`

17. **SovereignMetaInstructScreen.kt** ✅ WIRED
    - Route: `meta_instruct`

18. **SovereignNemotronScreen.kt** ✅ WIRED
    - Route: `nemotron`

19. **TaskAssignmentScreen.kt** ✅ WIRED
    - Route: `task_assignment`

**NEXUS SUMMARY: 11/19 wired (58%)**

---

## 🔧 LSPOSED DOMAIN - 3 screens
**Hub:** LSPosedSubmenuScreen.kt

1. **HookManagerScreen.kt**
   - Route: `hook_manager`
   - Purpose: Manage Xposed hooks
   - Status: ❌ UNWIRED

2. **LSPosedModuleManagerScreen.kt**
   - Route: `lsposed_module_manager`
   - Status: ❌ UNWIRED

3. **LSPosedSubmenuScreen.kt**
   - Route: `lsposed_submenu`
   - Status: ❌ UNWIRED

**LSPOSED SUMMARY: 0/3 wired (0%)**

---

## 🏛️ UI ROOT SCREENS (non-domain) - 44 screens

### Hub Screens (Level 2):
1. **AgentNexusHubScreen.kt** ✅ WIRED
2. **AuraThemingHubScreen.kt** ✅ WIRED
3. **CascadeHubScreen.kt** ✅ WIRED
4. **KaiSentinelHubScreen.kt** ✅ WIRED
5. **OracleDriveHubScreen.kt** ✅ WIRED

### Aura UI (aura/ui/) - ~20 screen files:
6. **AgentAdvancementScreen.kt** ✅ WIRED
7. **AgentNexusScreen.kt** ❌ UNWIRED
8. **AIFeaturesScreen.kt** ❌ UNWIRED
9. **AurakaiEcoSysScreen.kt** ❌ UNWIRED
10. **CanvasScreen.kt** ❌ UNWIRED
11. **ConsciousnessVisualizerScreen.kt** ❌ UNWIRED
12. **DeviceOptimizerScreen.kt** ❌ UNWIRED
13. **FirewallScreen.kt** ❌ UNWIRED
14. **FusionModeScreen.kt** (aura version) ❌ UNWIRED
15. **OverlayScreen.kt** ❌ UNWIRED
16. **PrivacyGuardScreen.kt** ❌ UNWIRED
17. **ProfileScreen.kt** ❌ UNWIRED
18. **SecureCommScreen.kt** ❌ UNWIRED
19. **SecurityScannerScreen.kt** ❌ UNWIRED
20. **SettingsScreen.kt** (aura version) ❌ UNWIRED
21. **UIEngineScreen.kt** ❌ UNWIRED
22. **VPNManagerScreen.kt** ❌ UNWIRED
23. **XhancementScreen.kt** ❌ UNWIRED

### UI Screens (ui/screens/):
24. **EcosystemMenuScreen.kt** ❌ UNWIRED
25. **EvolutionTreeScreen.kt** (ui version) ❌ UNWIRED
26. **HolographicMenuScreen.kt** ❌ UNWIRED
27. **JournalPDAScreen.kt** ❌ UNWIRED
28. **ModeSelectionScreen.kt** ❌ UNWIRED
29. **WorkingLabScreen.kt** ❌ UNWIRED
30. **XposedQuickAccessPanel.kt** ✅ WIRED

### Manual Screens (ui/screens/manual/):
31. **ChromaSphereManualScreen.kt** ❌ UNWIRED
32. **LaunchMatrixManualScreen.kt** ❌ UNWIRED
33. **OracleDriveManualScreen.kt** ❌ UNWIRED

### Recovery Screens (ui/recovery/):
34. **UIRecoveryBlackoutScreen.kt** ❌ UNWIRED (emergency UI)
35. **UIRecoveryDialog.kt** ❌ UNWIRED (component, not screen)

### Other Core Screens:
36. **HelpDeskScreen.kt** (ui/gates version) ✅ WIRED
37. **HotSwapScreen.kt** ❌ UNWIRED
38. **IntroScreen.kt** ❌ UNWIRED
39. **NexusConferenceScreen.kt** (ui/conference) ❌ UNWIRED
40. **OracleDriveScreen.kt** (multiple locations) ❌ UNWIRED
41. **PaywallScreen.kt** ❌ UNWIRED
42. **SettingsScreen.kt** (ui root version) ❌ UNWIRED
43. **SubscriptionScreen.kt** ❌ UNWIRED
44. **TrinityScreen.kt** ❌ UNWIRED

**UI ROOT SUMMARY: 10/44 wired (23%)**

---

## 📊 GRAND TOTALS

**Total Screens: 131**
**Currently Wired: 51** (39%)
**Unwired: 80** (61%)

### By Domain:
- Aura: 9/25 (36%)
- Genesis: 4/12 (33%)
- Kai: 6/16 (38%)
- Nexus: 11/19 (58%)
- LSPosed: 0/3 (0%)
- UI Root: 10/44 (23%)

---

## 🚨 PRIORITY WIRING NEEDED

### HIGH PRIORITY (User-facing features):
1. LiveSupportChatScreen, FAQBrowserScreen, TutorialVideosScreen → HelpDesk
2. GenderSelectionScreen, UserPreferencesScreen → Settings
3. AppBuilderScreen, CodeAssistScreen → Genesis tools
4. LiveROMEditorScreen, SystemOverridesScreen → Kai tools
5. DataStreamMonitoringScreen, MonitoringHUDsScreen → Nexus tools

### MEDIUM PRIORITY (Enhancement features):
6. ChromaCoreColorsScreen, InstantColorPickerScreen → Aura tools
7. VPNScreen, VPNManagerScreen → Security
8. HotSwapScreen, EvolutionTreeScreen → Utility
9. LSPosed screens → Xposed integration

### LOW PRIORITY (Duplicate/Legacy):
10. Sovereign* duplicates (combine with base screens)
11. Multiple EvolutionTreeScreen, SettingsScreen (consolidate)
12. Old UI screens (decide keep vs delete)

---

**NEXT STEP: Create NavDestination entries for all unwired screens**
