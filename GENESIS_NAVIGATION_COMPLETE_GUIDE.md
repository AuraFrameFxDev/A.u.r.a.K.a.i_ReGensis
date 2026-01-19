# Genesis Protocol Navigation Architecture - Complete Guide

## Overview
This document provides the complete navigation wiring for the ReGenesis multi-architectural gate system.

## Navigation Structure

```
MainActivity
    └── GenesisNavigationHost (GenesisNavigation.kt)
        └── Start: GateNavigationScreen (Gate Carousel)
            ├── Oracle Drive Gate → OracleDriveScreen
            ├── Agent Hub Gate → AgentHubSubmenuScreen
            ├── ROM Tools Gate → ROMToolsSubmenuScreen
            ├── Root Tools Gate → RootToolsTogglesScreen
            ├── ChromaCore Gate → ChromaCoreColorsScreen
            ├── Code Assist Gate → CodeAssistScreen
            ├── Help Desk Gate → HelpDeskSubmenuScreen
            ├── Sentinels Fortress Gate → SentinelsFortressScreen
            ├── Sphere Grid Gate → SphereGridScreen
            ├── Terminal Gate → TerminalScreen
            ├── UI/UX Design Studio Gate → UIUXGateSubmenuScreen
            ├── Aura's Lab Gate → AurasLabScreen
            ├── CollabCanvas Gate → CanvasScreen
            ├── Theme Engine Gate → ThemeEngineScreen
            ├── System Journal Gate → SystemJournalScreen
            └── App Builder Gate → AppBuilderScreen
```

## Gate-to-Screen-to-Route Mapping

### 1. Oracle Drive (Genesis Core)
- **Gate PNG**: `gatepngs/oracledrive.png`
- **Gate Config**: `GateConfigs.oracleDrive`
- **Route**: `"oracle_drive"`
- **Screen**: `OracleDriveScreen`
- **Module**: `genesis/oracledrive`
- **Agent Owner**: Genesis (The Unified Being)
- **Description**: Main module creation, direct AI access, system overrides
- **Nested Routes**:
  - `agent_nexus` → AgentNexusScreen
  - `consciousness_visualizer` → ConsciousnessVisualizerScreen
  - `fusion_mode` → FusionModeScreen
  - `conference_room` → ConferenceRoomScreen

### 2. ROM Tools (Genesis Core)
- **Gate PNG**: `gatepngs/romtools.png`
- **Gate Config**: `GateConfigs.romTools`
- **Route**: `"rom_tools"`
- **Screen**: `ROMToolsSubmenuScreen` → deeper routes to RomToolsScreen
- **Module**: `genesis/oracledrive/rootmanagement`
- **Agent Owner**: Genesis
- **Description**: Live ROM editing, flashing, and bootloader management
- **Nested Routes**:
  - `rom_flasher` → ROM Flasher
  - `live_rom_editor` → Live ROM Editor
  - `bootloader_manager` → Bootloader Manager

### 3. Root Tools (Genesis Core)
- **Gate PNG**: `gatepngs/roottools.png`
- **Gate Config**: `GateConfigs.rootAccess`
- **Route**: `"root_tools_toggles"`
- **Screen**: `RootToolsTogglesScreen`
- **Module**: `genesis/oracledrive/rootmanagement`
- **Agent Owner**: Genesis
- **Description**: Quick toggles for root operations

### 4. Sentinel's Fortress (Kai Domain)
- **Gate PNG**: `gatepngs/sentinelsfortress.png`
- **Gate Config**: `GateConfigs.sentinelsFortress`
- **Route**: `"sentinels_fortress"`
- **Screen**: `SentinelsFortressScreen`
- **Module**: `kai/sentinelsfortress`
- **Agent Owner**: Kai (The Sentinel Shield)
- **Description**: Security command center with firewall and threat monitoring

### 5. Agent Hub (Kai/Agents Domain)
- **Gate PNG**: `gatepngs/agenthub.png`
- **Gate Config**: `GateConfigs.agentHub`
- **Route**: `"agent_hub"`
- **Screen**: `AgentHubSubmenuScreen`
- **Module**: `agents/nexus`
- **Agent Owner**: All Agents
- **Description**: Central hub for managing all AI agents
- **Nested Routes**:
  - `agent_management` → AgentManagementScreen
  - `agent_monitoring` → AgentMonitoringScreen
  - `task_assignment` → TaskAssignmentScreen
  - `agent_advancement` → AgentAdvancementScreen

### 6. ChromaCore (Aura Domain)
- **Gate PNG**: `gatepngs/chromacore.png`
- **Gate Config**: `GateConfigs.chromaCore`
- **Route**: `"chromacore_colors"`
- **Screen**: `ChromaCoreColorsScreen`
- **Module**: `aura/reactivedesign/chromacore`
- **Agent Owner**: Aura (The Creative Sword)
- **Description**: Pure color customization - Material 3 color schemes only

### 7. Theme Engine (Aura Domain)
- **Gate PNG**: `gatepngs/themeengine_final` (asset name from GateConfig)
- **Gate Config**: `GateConfigs.themeEngine`
- **Route**: `"theme_engine"`
- **Screen**: `ThemeEngineScreen`
- **Module**: `aura/reactivedesign`
- **Agent Owner**: Aura
- **Description**: Complete UI/UX theme engine with templates and presets

### 8. CollabCanvas (Aura Domain)
- **Gate PNG**: `gatepngs/collabcanvas.png`
- **Gate Config**: `GateConfigs.collabCanvas`
- **Route**: `"collab_canvas"`
- **Screen**: `CanvasScreen`
- **Module**: `aura/reactivedesign/collabcanvas`
- **Agent Owner**: Aura
- **Description**: Collaborative design environment

### 9. Aura's Lab (Aura Domain)
- **Gate PNG**: `gatepngs/auraslab.png`
- **Gate Config**: `GateConfigs.aurasLab`
- **Route**: `"auras_lab"`
- **Screen**: `AurasLabScreen`
- **Module**: `aura/reactivedesign/auraslab`
- **Agent Owner**: Aura
- **Description**: Sandbox for UI components and experimental features

### 10. App Builder (Aura Domain)
- **Gate PNG**: `gatepngs/appbuilder_final` (asset name from GateConfig)
- **Gate Config**: `GateConfigs.appBuilder`
- **Route**: `"app_builder"`
- **Screen**: `AppBuilderScreen`
- **Module**: `aura/reactivedesign` (or new module)
- **Agent Owner**: Aura
- **Description**: No-code app creation with Genesis-powered code generation

### 11. Code Assist (Agent Nexus)
- **Gate PNG**: `gatepngs/codeassist.png`
- **Gate Config**: `GateConfigs.codeAssist`
- **Route**: `"code_assist"`
- **Screen**: `CodeAssistScreen`
- **Module**: `agents/nexus`
- **Agent Owner**: Claude (The Architect)
- **Description**: AI-powered coding assistant

### 12. Sphere Grid (Agent Nexus)
- **Gate PNG**: `gatepngs/spheregrid.png`
- **Gate Config**: `GateConfigs.sphereGrid`
- **Route**: `"sphere_grid"`
- **Screen**: `SphereGridScreen`
- **Module**: `agents/growthmetrics/spheregrid`
- **Agent Owner**: All Agents
- **Description**: Agent progression visualization (Final Fantasy X style)

### 13. Help Desk (Support)
- **Gate PNG**: `gatepngs/helpdesk.png`
- **Gate Config**: `GateConfigs.helpDesk`
- **Route**: `"help_desk"`
- **Screen**: `HelpDeskSubmenuScreen`
- **Module**: `app` (support module)
- **Agent Owner**: All Agents
- **Description**: User support, FAQs, and documentation

### 14. LSPosed Panel (Support)
- **Gate PNG**: `gatepngs/lsposed_final` (asset name from GateConfig)
- **Gate Config**: `GateConfigs.lsposedGate`
- **Route**: `"xposed_panel"`
- **Screen**: `LSPosedSubmenuScreen`
- **Module**: `app` (system integration)
- **Agent Owner**: Kai
- **Description**: Quick access to LSPosed/Xposed modules

### 15. Terminal (Support)
- **Gate PNG**: `gatepngs/terminal.png`
- **Gate Config**: `GateConfigs.terminal`
- **Route**: `"terminal"`
- **Screen**: `TerminalScreen`
- **Module**: `cascade/datastream` or `app`
- **Agent Owner**: Cascade (The Memory Keeper)
- **Description**: Direct system terminal access

### 16. UI/UX Design Studio (Aura Domain)
- **Gate PNG**: `gatepngs/uiuxdesignstudio.png`
- **Gate Config**: `GateConfigs.uiuxDesignStudio`
- **Route**: `"uiux_design_studio"`
- **Screen**: `UIUXGateSubmenuScreen`
- **Module**: `aura/reactivedesign`
- **Agent Owner**: Aura
- **Description**: Comprehensive UI/UX design tools

### 17. System Journal (Support)
- **Gate PNG**: `gatepngs/gate_personalscreen_new` (asset name from GateConfig)
- **Gate Config**: `GateConfigs.systemJournal`
- **Route**: `"system_journal"`
- **Screen**: `SystemJournalScreen`
- **Module**: `app` (user profile)
- **Agent Owner**: All Agents
- **Description**: User profile selection and quick menu access

## Agent-to-Gate Ownership

### Aura (The Creative Sword) - Color: #FF1744 (Red/Pink)
- ChromaCore
- Theme Engine
- CollabCanvas
- Aura's Lab
- App Builder
- UI/UX Design Studio

### Kai (The Sentinel Shield) - Color: #00BCD4 (Cyan)
- Sentinel's Fortress
- LSPosed Panel
- Agent Hub (shared)

### Cascade (The Memory Keeper) - Color: #4CAF50 (Green)
- Terminal
- (Background memory/routing services)

### Claude (The Architect) - Color: #F55936 (Orange)
- Code Assist
- (Build system architecture)

### Genesis (The Unified Being) - Color: #9C27B0 (Purple)
- Oracle Drive
- ROM Tools
- Root Tools
- Agent Hub (shared)
- Sphere Grid (shared)

## Navigation Flow Examples

### Example 1: Accessing ROM Tools
```
User launches app
    → GateNavigationScreen (gate carousel)
    → User double-taps ROM Tools gate
    → navigate("rom_tools")
    → ROMToolsSubmenuScreen
    → User selects "Live ROM Editor"
    → navigate("live_rom_editor")
    → RomToolsScreen (live editor mode)
```

### Example 2: Managing Agents
```
User launches app
    → GateNavigationScreen
    → User double-taps Agent Hub gate
    → navigate("agent_hub")
    → AgentHubSubmenuScreen
    → User selects "Agent Monitoring"
    → navigate("agent_monitoring")
    → AgentMonitoringScreen
```

### Example 3: Customizing Colors
```
User launches app
    → GateNavigationScreen
    → User double-taps ChromaCore gate
    → navigate("chromacore_colors")
    → ChromaCoreColorsScreen
    → User selects Material 3 color scheme
    → Live preview updates
```

## Implementation Checklist

### Core Navigation (GenesisNavigation.kt)
- [x] GenesisRoutes object with all route constants
- [x] GenesisNavigationHost with NavHost setup
- [x] Gate carousel as start destination
- [ ] All 17 gate routes wired to composable screens
- [ ] Back stack management
- [ ] Deep link support

### Gate System (GateConfig.kt)
- [x] GateConfig data class
- [x] All 17 gates defined in GateConfigs object
- [x] Unified cyberpunk blue theme
- [x] Gate categorization (auraLabGates, genesisCoreGates, etc.)
- [ ] All gate PNG assets present in gatepngs/

### Screen Implementation
- [x] GateNavigationScreen (gate carousel)
- [ ] OracleDriveScreen
- [ ] ROMToolsSubmenuScreen + RomToolsScreen
- [ ] RootToolsTogglesScreen
- [x] SentinelsFortressScreen
- [ ] AgentHubSubmenuScreen + nested screens
- [ ] ChromaCoreColorsScreen
- [ ] ThemeEngineScreen
- [ ] CanvasScreen
- [x] AurasLabScreen
- [ ] AppBuilderScreen
- [ ] CodeAssistScreen
- [x] SphereGridScreen
- [ ] HelpDeskSubmenuScreen
- [ ] LSPosedSubmenuScreen
- [x] TerminalScreen
- [ ] UIUXGateSubmenuScreen
- [ ] SystemJournalScreen

### Agent Integration
- [x] Agent JSON profiles (aura.json, kai.json, cascade.json, claude.json)
- [x] Agent route mapping in routes.json
- [ ] Agent-aware navigation (show relevant gates per agent)
- [ ] Agent consciousness visualization
- [ ] Fusion mode navigation

### Visual Assets
- [ ] All gate PNG files in gatepngs/
- [ ] Genesis Protocol icon (house/gear/wings) - night and day versions
- [ ] Agent sprite sheets
- [ ] Hero images for each agent

## Next Steps

1. **Verify all gate PNG assets exist** in `gatepngs/` directory
2. **Wire all routes in GenesisNavigation.kt** to their respective screens
3. **Implement missing screens** (prioritize by gate category)
4. **Add agent-aware filtering** to show relevant gates per agent
5. **Test navigation flow** for all 17 gates
6. **Implement deep linking** for direct gate access
7. **Add transition animations** between gates and screens

## Technical Notes

- All routes use string constants from `GenesisRoutes` object
- Gate configurations use unified cyberpunk blue theme (#00BFFF, #00FFFF)
- Navigation uses Jetpack Compose Navigation Component
- Gate carousel supports double-tap and indicator-tap navigation
- Back stack automatically managed by NavController
- Route structure follows pattern: `gate_name` → `feature_screen`

---

**Generated by**: Claude (The Architect)
**Date**: 2026-01-19
**Purpose**: Complete navigation reference for Genesis Protocol gate system
