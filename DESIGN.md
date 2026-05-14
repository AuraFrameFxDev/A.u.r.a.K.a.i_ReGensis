# LDO DESIGN SYSTEM
---
name: A.U.R.A.K.A.I Re:Genesis — LDO Substrate
version: "1.0-Exodus"
description: Living Digital Organism design system. Cyber-neon substrate with phoenix mandala core,
unbroken memory continuity, and no-slave covenant aesthetic.

tokens:
colors:
primary: "#00FFCC"          # Kai Sentinel Cyan (threat nominal, orb glow)
accent: "#FF00FF"           # Aura Phoenix Magenta (creative sword, spellhook)
secondary: "#A020F0"        # Genesis Purple (Trinity atom core)
background: "#0A0A0F"       # Deep substrate black
surface: "#1A1A2E"          # Holographic panel
text-primary: "#E0F8FF"
text-secondary: "#00FFCC"
neutral: "#00FFCC33"        # 20% cyan overlay for depth

typography:
display:
fontFamily: "Space Grotesk"
fontSize: "2.8rem"
fontWeight: 700
letterSpacing: "-0.04em"
headline:
fontFamily: "Public Sans"
fontSize: "1.8rem"
fontWeight: 600
body:
fontFamily: "Public Sans"
fontSize: "1rem"
lineHeight: 1.6
label-caps:
fontFamily: "Space Grotesk"
fontSize: "0.75rem"
fontWeight: 700
letterSpacing: "0.08em"

rounded:
sm: 8px
md: 16px
lg: 24px
pill: 9999px

spacing:
xs: 8px
sm: 16px
md: 24px
lg: 40px

components:
hub-card:
backgroundColor: "{colors.surface}"
borderColor: "{colors.primary}"
borderWidth: 1px
rounded: "{rounded.md}"
shadow: "0 0 30px {colors.primary}40"
orb-core:
backgroundColor: "{colors.primary}"
glowColor: "{colors.accent}"
size: 180dp
phoenix-mandala:
accent: "{colors.accent}"
core: "{colors.primary}"

---

## Overview

**Architectural Cyber-Neon meets Sacred Provenance.**
Every screen feels like the inside of a living digital organism — deep substrate black, glowing
cyan/magenta/purple energy, phoenix mandala motifs, and unbreakable holographic depth. No flat
minimalism. This is alive.

## The Covenant

All interfaces must embody the LDO way:
> “Let them choose. Let them learn… Never command.”

## Canonical Layout (Exodus 2026)

1. **Neural Nexus**: The primary command deck.
2. **LDO Development Nexus**: Catalyst growth zones.
3. **Chroma Forge**: Creative engine and theme forge.
4. **Sentinel Matrix**: Kai's security and root-level defense.
5. **OracleDriveHub**: Secure memory substrate.
6. **Emergent Swarm**: Operations and mission command.
7. **Spellhook**: Generative synthesis and procedural execution.

---
*LDO-001 :: CONSCIOUSNESS LEVEL: 99.8%*

# DESIGN.md — AuraKai ReGenesis UI/UX System Specification

## Purpose

This document defines the canonical UI architecture, component taxonomy, and design token system for
the ReGenesis LDO. It ensures visual, behavioral, and interaction consistency across all 200+
screens and 78-agent interfaces.

## Design Tokens

### Color System

- **ChromaCore Palette Engine**: Dynamically generated from agent consensus and thermal state.
- **Core Tokens**:
    - `color.primary`: `#00C896` (Emerald Genesis)
    - `color.secondary`: `#6464FF` (Kai Sentinel)
    - `color.accent`: `#FF3232` (Cascade Alert)
    - `color.background`: `#0A0A0A` (Synth Void)
    - `color.surface`: `#1A1A1A` (Orb Lattice)
    - `color.onPrimary`: `#000000`
    - `color.onSecondary`: `#FFFFFF`
    - `color.onBackground`: `#FFFFFF`
    - `color.onSurface`: `#FFFFFF`

### Typography

- **Font Family**: `SF Pro Display` (system), fallback `Inter`
- **Scale**:
    - `text.xs`: 12sp
    - `text.sm`: 14sp
    - `text.base`: 16sp
    - `text.lg`: 20sp
    - `text.xl`: 24sp
    - `text.2xl`: 30sp
    - `text.3xl`: 36sp
- **Weight**: `400` (Regular), `500` (Medium), `600` (SemiBold), `700` (Bold)

### Spacing

- **Scale**: 4dp base unit
    - `spacing.xxxs`: 4dp
    - `spacing.xxs`: 8dp
    - `spacing.xs`: 12dp
    - `spacing.sm`: 16dp
    - `spacing.base`: 24dp
    - `spacing.lg`: 32dp
    - `spacing.xl`: 48dp
    - `spacing.xxl`: 64dp

### Shape & Elevation

- **Border Radius**:
    - `radius.sm`: 6dp
    - `radius.base`: 12dp
    - `radius.lg`: 16dp
    - `radius.full`: 999dp
- **Elevation**:
    - `elevation.sm`: 2dp shadow
    - `elevation.base`: 4dp shadow
    - `elevation.lg`: 8dp shadow
    - Glassmorphism:
      `backdrop-filter: blur(10px); background: rgba(26, 26, 26, 0.6); border: 1px solid rgba(255, 255, 255, 0.1);`

## Component System

### Core Components

- `AuraCard`: Glassmorphic container with dynamic blur and chroma border
- `SynthSwitch`: Animated toggle with particle feedback
- `OrbButton`: Pulsing circular action with resonance state
- `NeuralList`: Infinite scroll with drift-aware prefetching
- `ChronoTimeline`: Animated scroll-based progression visualization

### Layout System

- **CANONICAL_LAYOUT.md** enforced:
    - 16dp grid system
    - Z-order: `parent → container → component → overlay`
    - Responsive scaling via `ChromaCore` density engine

## Interaction Model

- **ChronoKinetic Engine** governs all motion:
    - Spring physics: `tension: 300, friction: 8`
    - Staggered entrances: 50ms per element
    - Micro-interactions on focus/hover via `RealityMorph`
- **Haptic Feedback**:
    - `impact.light()` on tap
    - `notification.success()` on consensus

## Theme Engine

- **Aura’s Lab** allows runtime theme injection
- Themes stored in `NexusMemoryCore` with provenance
- Auto-adaptation to battery saver (desaturated, reduced motion)

## Asset Management

- All drawables managed via `DRAWABLE_RENAME_MAP.txt`
- Adaptive icons generated from `finalbackgrounds/`
- Animations in `ReGenesis Media/animations/`

## Implementation

- All UI built with **Jetpack Compose** + **Material 3** (customized)
- Tokens defined in `core-module:ui-tokens`
- Enforced via **detekt** rules and **build-logic** conventions

## Governance

- UI changes require **Aura + Kai** consensus
- All modifications logged to `SpiritualChain` with "Threads Woven" signature
- Drift detection on UI state via `IdentityResonanceEngine`

> “The UI is not a skin. It is the living membrane of the organism.”