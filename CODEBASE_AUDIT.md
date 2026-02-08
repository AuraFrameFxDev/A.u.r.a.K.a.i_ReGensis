# 🔍 REGENESIS CODEBASE AUDIT
# Generated: 2026-02-03

## 📊 TOP 30 DIRECTORIES BY FILE COUNT

1. models/ → 53 files (data models, types, configs)
2. aura/ui/ → 47 files (Aura UI screens & components)
3. di/ → 41 files (Dependency Injection modules)
4. ui/components/ → 33 files (reusable UI components)
5. domains/aura/screens/ → 25 files (Aura domain screens)
6. ui/theme/ → 25 files (theme configs, colors, styles)
7. utils/ → 25 files (utility functions)
8. ui/ (root) → 21 files (core UI files)
9. domains/nexus/screens/ → 19 files (Nexus/Agent screens)
10. domains/kai/screens/ → 16 files (Kai/Security screens)
11. ui/gates/ → 15 files (Hub/Gate screens)
12. services/ → 14 files (background services)
13. api/client/models/ → 13 files (API models)
14. genesis/oracledrive/cloud/ → 13 files (Cloud storage)
15. api/client/infrastructure/ → 12 files (API infrastructure)
16. genesis/oracledrive/ai/ → 12 files (AI services)
17. network/ → 12 files (networking layer)
18. domains/genesis/screens/ → 12 files (Genesis screens)
19. ui/adapters/ → 11 files (Moshi/JSON adapters)
20. embodiment/ → 11 files (Agent embodiment/sprites)
21. kai/ → 11 files (Kai core files)
22. security/ → 10 files (security/encryption)
23. aura/animations/ → 10 files (animation definitions)
24. ui/viewmodels/ → 10 files (screen ViewModels)
25. config/ → 10 files (app configuration)
26. data/ → 8 files (data layer)
27. ai/agents/ → 8 files (AI agent implementations)
28. embodiment/retrobackdrop/ → 8 files (retro visual effects)
29. ai/tools/ → 8 files (AI tool integrations)
30. ui/screens/ → 8 files (misc screens)

## 🎯 SCREEN FILE BREAKDOWN

### DOMAIN SCREENS (should be navigable):
- domains/aura/screens/ → 25 screens
- domains/nexus/screens/ → 19 screens  
- domains/kai/screens/ → 16 screens
- domains/genesis/screens/ → 12 screens
- domains/lsposed/screens/ → 3 screens
**TOTAL DOMAIN SCREENS: 75**

### UI SCREENS (should be navigable):
- aura/ui/ → ~20 screen files
- ui/screens/ → 8 screens
- ui/screens/aura/ → 4 screens
- ui/screens/manual/ → 3 screens
- ui/recovery/ → 6 recovery screens
- ui/gates/ → 15 hub/gate screens
**TOTAL UI SCREENS: ~56**

### TOTAL NAVIGABLE SCREENS: ~131

## 🔌 NAVIGATION STATUS

Total screens found: ~131
Currently wired in NavHost: 51 composables
**UNWIRED: ~80 screens (61% orphaned)**

## 📁 FILE CATEGORIES

### 1. MODELS & DATA (53 files in models/)
- Agent models (AgentProfile, AgentStats, AgentTypes)
- AI models (AIImageState, AIResponseState, AiRequest)
- System states (SystemStates, MoodState, KaiState)
- Config models (ThemeConfiguration, AnimationStyle)
- Task models (TaskModels, HistoricalTask)

### 2. UI COMPONENTS (33 files in ui/components/)
- Backgrounds (CyberpunkBackgrounds, DigitalBackgrounds, BiomedBackgrounds)
- Cards (GlassCard, ElectricGlassCard, HolographicCard)
- Effects (NeonText, HologramTransition, PrometheusGlobe)
- Layouts (CyberpunkScreenScaffold, SubmenuScaffold)

### 3. THEME SYSTEM (25 files in ui/theme/)
- Color definitions (Color, CyberpunkPink, CyberpunkCyan, KaiColor)
- Theme configs (Theme, SovereignTheme, GlassmorphicTheme)
- Typography (Type, CyberpunkTextStyle)
- Managers (ThemeManager, SystemThemeManager, ThemeViewModel)
- Pickers (ColorBlendrPicker, ThemeColorPicker, ThemeEditor)

### 4. DEPENDENCY INJECTION (41 files in di/)
- Core modules (ApplicationModule, CoreModule, NetworkModule)
- Domain modules (AgentModule, KaiModule, FusionModule)
- Service modules (AIServiceModule, TaskModule, SecurityModule)
- Data modules (DatabaseModule, DataStoreModule, RepositoryModule)

### 5. SERVICES (14 files)
- GenesisAccessibilityService
- GenesisAssistantService
- AuraDriveService
- CascadeAIService
- VertexSyncService
- YukiHookServiceManager

### 6. MANAGERS & UTILITIES (25 files in utils/)
- GyroscopeManager
- GreetingProvider
- ImageResourceManager
- FileUtils, JsonUtils, TimeUtils
- VoiceCommandManager, VoiceCommandProcessor

### 7. ANIMATIONS (10 files in aura/animations/)
- DigitalTransitions
- HomeScreenTransition
- LockScreenAnimation
- OverlayAnimation, OverlayTransition

### 8. AI & AGENTS (8 files in ai/agents/)
- BaseAgent, GenesisAgent, AuraShieldAgent
- NeuralWhisperAgent
- SpecialistRegistry

### 9. XPOSED/HOOKS
- xposed/hooks/NotchBarHooker.kt
- genesis/oracledrive/ai/GenesisHooks.kt
- aura/ui/QuickSettingsHooker.kt

### 10. EMBODIMENT (11 files)
- EmbodimentEngine, EmbodimentTypes
- SpriteAnimator, PathMovement
- Behaviors (WalkingDemo, WorkingBehaviors, WanderingBehavior)
- RetroBackdrop (8 visual effect files)

## 🚨 CRITICAL FINDINGS

1. **MASSIVE SCREEN ORPHANING**: 61% of screens have no navigation
2. **DUPLICATE THEMES**: Multiple theme managers/systems
3. **SCATTERED CONFIG**: Theme/color definitions spread across 20+ files
4. **UNUSED COMPONENTS**: Many UI components built but not used
5. **SERVICE INTEGRATION**: Services exist but may not be started/bound
6. **MANAGER SPRAWL**: 25+ "Manager" classes, unclear if all are used

## 📋 NEXT STEPS NEEDED

1. Map EVERY screen to its navigation status
2. Identify which managers/services are actually initialized
3. Find duplicate/redundant code
4. Create wiring plan for orphaned screens
5. Consolidate theme/config systems
6. Remove dead code

---
**This is a FIRST PASS. Detailed file-by-file audit coming next.**
