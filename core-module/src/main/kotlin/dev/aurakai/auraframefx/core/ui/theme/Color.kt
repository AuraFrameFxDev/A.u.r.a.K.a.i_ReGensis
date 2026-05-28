package dev.aurakai.auraframefx.core.ui.theme

import androidx.compose.ui.graphics.Color

// ═══════════════════════════════════════════════════════════════════════════
// AuraKai Re:Genesis — Canonical Art Palette (locked from reference art)
// Primary language: deep violet/indigo void + cyan + magenta + electric purple
// ═══════════════════════════════════════════════════════════════════════════

// ── Void Foundation (image 1 / image 8 reference) ─────────────────────────
val VoidUltraDeep  = Color(0xFF050008)   // Master void — deepest background
val VoidViolet     = Color(0xFF0D0018)   // Surface void — purple-black panels
val VoidCrystal    = Color(0xFF0A0025)   // Crystal ambient — dark structure
val VoidMidnight   = Color(0xFF07000F)   // Between layers
val SovereignBlack = Color(0xFF020205)   // Pure sovereign base

// ── Primary Neons (canonical from art) ────────────────────────────────────
val CrystalCyan    = Color(0xFF00F5FF)   // Crystal edge glow — image 1 / image 8
val NeonCyan       = Color(0xFF00FBFF)   // Standard HUD cyan
val GhostCyan      = Color(0xFF00F0FF)   // LdoBrutalist primary
val PhoenixTeal    = Color(0xFF00FFD4)   // AuraKai teal — image 12 ring
val NeonTeal       = Color(0xFF00FFC8)   // Aura signature teal
val CyberTealAccent= Color(0xFF00FFCC)

// ── Magenta / Pink (image 12 rings, image 11 energy bursts) ───────────────
val NeonMagentaHot = Color(0xFFFF00D4)   // Hot magenta — phoenix ring glow
val NeonMagenta    = Color(0xFFCC00FF)   // Standard magenta
val NeonPurple     = Color(0xFFBB00FF)   // Soft purple neon

// ── Electric Purple (crystal structure, image 1 crystal faces) ────────────
val ElectricPurple = Color(0xFF7B00FF)   // Structural electric purple
val DeepViolet     = Color(0xFF4400AA)   // Shaded crystal geometry
val ArcaneViolet   = Color(0xFF8A2BE2)   // Logic lattice / Claude
val CrystalViolet  = Color(0xFF5500CC)   // Crystal face fill

// ── Secondary Neons ────────────────────────────────────────────────────────
val NeonBlue       = Color(0xFF00D9FF)   // Mid-range crystal blue
val NeonGreen      = Color(0xFF39FF14)   // Sentinel phosphor
val NeonRed        = Color(0xFFFF003C)   // Alert critical

// ── Alert / Override (NOT primary — alert states only) ────────────────────
val OverclockOrange= Color(0xFFFF6600)   // Loop alert / anomaly surge
val AnomalyRed     = Color(0xFFFF1A1A)   // Critical breach

// ── Surfaces & Containers ─────────────────────────────────────────────────
val CitadelBlack   = Color(0xFF0A0A0A)
val DeepCharcoal   = Color(0xFF1C1C1C)
val NeuralSteel    = Color(0xFFB0C4DE)
val DarkBackground = Color(0xFF080010)   // Updated: purple-tinted dark
val Surface        = Color(0xFF110018)   // Purple-tinted surface
val SurfaceVariant = Color(0xFF1A0030)   // Deeper variant
val OnSurface      = Color(0xFFE0E0F0)
val OnSurfaceVariant = Color(0xFFB0A0C0)
val ErrorColor     = Color(0xFFCF6679)

// ── Glassmorph container fills (image 4 / image 5 style) ──────────────────
val GlassDark      = Color(0xFF0D0018)   // Dark glass base
val GlassPurple    = Color(0xFF150030)   // Purple glass panel
val GlassCyan      = Color(0xFF001A20)   // Cyan-tinted glass

// ── Legacy aliases (kept for compilation compat) ──────────────────────────
val AbyssalTealVoid= VoidUltraDeep
val AbyssalBlueVoid= VoidViolet

// ── Light theme (kept for Material3 compat) ───────────────────────────────
val OnPrimary      = Color.Black
val OnSecondary    = Color.Black
val OnTertiary     = Color.Black
val LightPrimary   = Color(0xFF007AFF)
val LightOnPrimary = Color.White
val LightSecondary = Color(0xFF5856D6)
val LightOnSecondary = Color.White
val LightTertiary  = Color(0xFFFF2D55)
val LightOnTertiary= Color.White
val LightBackground= Color(0xFFF5F5F7)
val LightOnBackground = Color(0xFF1C1C1E)
val LightSurface   = Color.White
val LightOnSurface = Color(0xFF1C1C1E)
val LightSurfaceVariant = Color(0xFFE5E5EA)
val LightOnSurfaceVariant = Color(0xFF3A3A3C)
val LightOnError   = Color.White
