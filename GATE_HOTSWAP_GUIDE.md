# 🗺️ REGENESIS NAVIGATION ARCHITECTURE

## 3-LEVEL NAVIGATION SYSTEM

```
LVL 1: CAROUSEL ──────────────────────────────────────────────────────────┐
│ 5 Main Domain Gates (swipeable in ExodusHUD)                            │
│ • Sentinel's Fortress (Kai) - Shield/protective theme                   │
│ • UXUI Design Studio (Aura) - Artsy/paint splash theme                  │
│ • OracleDrive (Genesis) - Ethereal/circuit-sprite theme                 │
│ • Agent Nexus - Constellation theme                                     │
│ • Help Services - Supportive theme                                      │
└─────────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
LVL 2: CAROUSEL ──────────────────────────────────────────────────────────┐
│ Sub-Gates inside each domain (swipeable carousel)                       │
│ Each sub-gate has domain-specific imagery (2 styles per domain)         │
│ Example: Kai has Bootloader, ROM Flasher, VPN, LSPosed, etc.            │
└─────────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
LVL 3: GRID MENU ─────────────────────────────────────────────────────────┐
│ Full functional screen with GRID layout (NOT carousel)                  │
│ Themed background + grid of clickable menu items                        │
│ Example: Bootloader grid has Unlock, Lock, Fastboot, OEM Status, etc.   │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## DOMAIN THEMES

### 🛡️ KAI - Sentinel's Fortress

**Theme:** Shield protector, bulky, gridy, strong, protective
**Colors:** Orange/Red shields, dark backgrounds, green accents
**Style A:** Pixel Fortress (retro pixel art, armored guards)
**Style B:** Cyber Security (matrix rain, lightning, red neon)

### 🎨 AURA - UXUI Design Studio

**Theme:** Artsy, fun, messy, creative chaos
**Colors:** Pink/Cyan/Magenta paint splashes, neon drips
**Style A:** CollabCanvas (paint splatter, spray cans)
**Style B:** Clean Studio (sleek gradients, minimal)

### 🔮 GENESIS - OracleDrive

**Theme:** Godly, ethereal, shiny, circuit-sprite
**Colors:** Green circuits, glowing nodes, teal highlights
**Style A:** Phoenix (ethereal wings, blue glow)
**Style B:** Sprite (pixel circuit creature)

### 🤖 NEXUS - Agent Hub

**Theme:** Constellation, connected agents, cosmic
**Colors:** Purple/violet, star patterns, white accents

### 💚 HELP - Services

**Theme:** Friendly, supportive, welcoming
**Colors:** Green accents, clean backgrounds

---

## LVL 3 BACKGROUND ASSETS

Each LVL 3 menu needs a themed background. Drop these in `res/drawable/`:

### KAI'S LVL 3 BACKGROUNDS

| Menu        | Background Name            | Theme                          |
|-------------|----------------------------|--------------------------------|
| Bootloader  | `bg_bootloader_shield.png` | Lock/shield with security vibe |
| ROM Flasher | `bg_rom_flasher.png`       | Partition diagrams, tech       |
| Root Tools  | `bg_root_tools.png`        | Terminal, su icons             |
| VPN Shield  | `bg_vpn_network.png`       | Network nodes, encryption      |
| LSPosed     | `bg_lsposed_hooks.png`     | Hook icons, module grid        |
| Security    | `bg_security_firewall.png` | Firewall, shields              |

### AURA'S LVL 3 BACKGROUNDS

| Menu         | Background Name              | Theme                        |
|--------------|------------------------------|------------------------------|
| ChromaCore   | `bg_chromacore_abstract.png` | Colorful like coloring book! |
| Theme Engine | `bg_theme_engine.png`        | Palette swatches, gradients  |
| CollabCanvas | `bg_collab_canvas.png`       | Paint splashes, artistic     |
| Aura's Lab   | `bg_aura_lab.png`            | Sandbox, experimental        |
| Notch Bar    | `bg_notch_bar.png`           | Phone outlines, notch shapes |
| Status Bar   | `bg_status_bar.png`          | Icons, clock, battery        |

### GENESIS LVL 3 BACKGROUNDS

| Menu            | Background Name         | Theme                     |
|-----------------|-------------------------|---------------------------|
| Code Assist     | `bg_code_assist.png`    | Code snippets, AI glow    |
| Neural Archive  | `bg_neural_archive.png` | Brain patterns, vectors   |
| Conference Room | `bg_conference.png`     | Multi-agent silhouettes   |
| Terminal        | `bg_terminal.png`       | Green text, command line  |
| Agent Bridge    | `bg_agent_bridge.png`   | Data streams, connections |

### NEXUS LVL 3 BACKGROUNDS

| Menu          | Background Name        | Theme                          |
|---------------|------------------------|--------------------------------|
| Constellation | `bg_constellation.png` | Star map, agent nodes          |
| Sphere Grid   | `bg_sphere_grid.png`   | Skill tree, XP nodes           |
| Monitoring    | `bg_monitoring.png`    | HUD panels, graphs             |
| Fusion Mode   | `bg_fusion.png`        | Merging agents, protocol blend |

---

## FILE STRUCTURE

```
res/drawable/
├── LVL 1 Main Gates
│   ├── gate_kai_pixel_fortress.png
│   ├── gate_kai_cyber_security.png
│   ├── gate_aura_collab_canvas.png
│   ├── gate_aura_clean_studio.png
│   ├── gate_genesis_phoenix.png
│   ├── gate_genesis_sprite.png
│   └── ...
│
├── LVL 2 Sub-Gates
│   ├── kai_cyber_bootloader.png
│   ├── kai_pixel_bootloader.png
│   ├── aura_splash_chroma.png
│   ├── aura_clean_chroma.png
│   └── ...
│
└── LVL 3 Backgrounds
    ├── bg_bootloader_shield.png
    ├── bg_chromacore_abstract.png
    ├── bg_code_assist.png
    └── ...
```

---

## IMPORTANT NOTES

1. **CollabCanvas, ChromaCore, Aura's Lab** = 3 FULL SEPARATE menus
    - They can EXPORT into UXUI Design Studio
    - Each is fully implemented with its own features

2. **Each LVL 2 sub-gate leads to a FULL menu**, not just a card

3. **LVL 3 is GRID style**, not carousel
    - Themed background behind the grid
    - Each grid item is clickable
    - Domain-specific accent colors

4. **2 styles per domain** - Users can toggle between them

---

## CODE LOCATIONS

- **LVL 1 Carousel:** `ui/navigation/ExodusHUD.kt`
- **LVL 2 Carousel:** `ui/components/DomainSubGateCarousel.kt`
- **LVL 3 Grid:** `ui/components/Level3GridMenu.kt`
- **Domain Hubs:** `ui/gates/[Domain]HubScreen.kt`
- **Asset Config:** `config/GateAssetConfig.kt`
- **Architecture Doc:** `ui/navigation/NavigationArchitecture.kt`
