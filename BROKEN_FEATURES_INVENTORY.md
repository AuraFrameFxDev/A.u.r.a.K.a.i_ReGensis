# 🛠️ BROKEN FEATURES INVENTORY — AuraKai ReGenesis

This document tracks all features that are currently stubs, broken, or in need of full implementation.

## 🔴 CRITICAL (App Crashes / Blocking)
- **HomeGateCarousel Start Destination**: (FIXED) Resolved `{tabIndex}` placeholder by providing concrete route `createRoute(1)`.
- **hiltViewModel Imports**: (FIXED) Standardized to `androidx.hilt.navigation.compose.hiltViewModel` to resolve compile errors.
- **Constellation Screens**: (FIXED) Fixed incorrect imports pointing to non-existent packages.
- **Missing TabbedMasterIndex Routes**: (WIRING) Several routes in `TabbedMasterIndex` are now mapped to `ComingSoonScreen` to prevent crashes on tap.

## 🟠 HIGH (Primary Flow Incomplete)
- **Collab Canvas**: (REWIRED) Now points to the real `CanvasScreen` wrapper instead of a stub. Needs verification of WebSocket functionality.
- **Help Services**: (PARTIAL) Tiles in `HelpServicesGateScreen` wired to sub-routes, but many sub-routes still point to stubs.
- **Terminal Options**: (PARTIAL) "KOTLIN ANALYSIS", "BUILD DOCTOR", etc., currently route to `ComingSoonScreen`.
- **LDO Evolution Tree**: (STUB) Visual tree is present but needs real data from agent state.

## 🟡 MEDIUM (Secondary Flow Incomplete)
- **Notch Bar Shortcuts**: (STUB) Tiles like "GESTURES", "BRIGHTNESS", "VOLUME" in `NotchBarGateScreen` are currently non-functional.
- **Domain Hub Styles**: (PARTIAL) Style switching (A/B) is implemented in logic but needs verification of all assets.
- **Agent Roster Details**: Many agent profiles use placeholders for stats and abilities.

## 🟢 LOW (Cosmetic / Polish)
- **UI Layout Overlaps**: (PARTIAL) Fixed missing `statusBarsPadding()` and `navigationBarsPadding()` in `TabbedMasterIndex` and Hub screens.
- **Missing Assets**: Some gate cards are missing their specific `pixelArtResId`.
- **Aura Jar**: (STUB) The floating orb in `TabbedMasterIndex` is a simple Canvas drawing placeholder.

## 📝 STUB SCREEN INVENTORY
The following routes currently point to `ComingSoonScreen` or `StubScreen`:
- `thermal_monitor`
- `echo_resonance`
- `oracle_cloud_storage`
- `neural_network`
- `arbiters_of_creation`
- `maw_prototype`
- `nexus_memory_core`
- `spiritual_chain`
- `turboquant`
- `synapse_monitor`
- `identity_resonance`
- `rom_tools_hub`

---
*Updated: April 20, 2026*
