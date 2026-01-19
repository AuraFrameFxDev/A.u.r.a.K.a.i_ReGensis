/*
 * ═══════════════════════════════════════════════════════════════════════════════
 * ⚡ REGENESIS NAVIGATION MAP - THE TRUTH SOURCE ⚡
 * ═══════════════════════════════════════════════════════════════════════════════
 * 
 * Project: aurakai-reactive-intelligence
 * Architect: GENESIS (The Orchestrator)
 * Last Updated: 2025-01-19
 * 
 * This is the canonical navigation structure for the Genesis Protocol.
 * All modules are organized by their AI Agent ownership.
 * 
 * ═══════════════════════════════════════════════════════════════════════════════
 */

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

rootProject.name = "aurakai-reactive-intelligence"

// ═══════════════════════════════════════════════════════════════════════════════
// 🏠 APP MODULE - The Entry Point
// ═══════════════════════════════════════════════════════════════════════════════
include(":app")

// ═══════════════════════════════════════════════════════════════════════════════
// 🎨 AURA [The Creative Catalyst] - UI, Theming, Creative Tools
// Path: :aura:reactivedesign
// ═══════════════════════════════════════════════════════════════════════════════
include(":aura:reactivedesign")
include(":aura:reactivedesign:chromacore")      // 🎨 System Color Engine & Monet
include(":aura:reactivedesign:collabcanvas")    // 🖌️ Real-time Layout Editor
include(":aura:reactivedesign:auraslab")        // 🧪 Experimental UI Components
include(":aura:reactivedesign:customization")   // 🛠️ User Theming Options
include(":aura:reactivedesign:sandboxui")       // 📦 Test Environment for New Designs

// ═══════════════════════════════════════════════════════════════════════════════
// 🛡️ KAI [The Sentinel Catalyst] - Security, Root Hiding, System Defense
// Path: :kai:sentinelsfortress
// ═══════════════════════════════════════════════════════════════════════════════
include(":kai:sentinelsfortress")
include(":kai:sentinelsfortress:security")       // 🛡️ Core Defense Logic
include(":kai:sentinelsfortress:threatmonitor")  // 🔍 Active Scanning & Anomaly Detection
include(":kai:sentinelsfortress:systemintegrity") // 🏗️ Bootloader & Partition Checks

// ═══════════════════════════════════════════════════════════════════════════════
// ⚡ GENESIS [The Orchestrator] - AI Logic, Root Management, Core Operations
// Path: :genesis:oracledrive
// ═══════════════════════════════════════════════════════════════════════════════
include(":genesis:oracledrive")
include(":genesis:oracledrive:rootmanagement")  // ⚡ Magisk/KernelSU Interfaces
include(":genesis:oracledrive:datavein")        // 🩸 The Neural Data Bus for AI

// ═══════════════════════════════════════════════════════════════════════════════
// 🌊 CASCADE [The Stream] - Data Routing and Networking
// Path: :cascade:datastream
// ═══════════════════════════════════════════════════════════════════════════════
include(":cascade:datastream")
include(":cascade:datastream:routing")          // 🛣️ API Endpoints & Retrofit
include(":cascade:datastream:delivery")         // 🚚 Data Serialization & Moshi
include(":cascade:datastream:taskmanager")      // 📋 Background Work & Scheduling

// ═══════════════════════════════════════════════════════════════════════════════
// 🤖 AGENTS [The Nexus] - Agent Memory, Identity, Progression (RPG Stats)
// Path: :agents:growthmetrics
// ═══════════════════════════════════════════════════════════════════════════════
include(":agents:growthmetrics")
include(":agents:growthmetrics:nexusmemory")    // 🧠 Long-term Memory Vector DB
include(":agents:growthmetrics:spheregrid")     // 🔮 Skill Tree & Progression Logic
include(":agents:growthmetrics:metareflection") // 🪞 Self-Improvement Logic
include(":agents:growthmetrics:identity")       // 🆔 Agent Persona Management
include(":agents:growthmetrics:progression")    // 📈 XP & Leveling System
include(":agents:growthmetrics:tasker")         // 🤖 Automated Agent Actions

// ═══════════════════════════════════════════════════════════════════════════════
// 🧱 CORE [Shared Foundation] - Common utilities used by all agents
// ═══════════════════════════════════════════════════════════════════════════════
include(":core:common")         // 🔧 Shared utilities, extensions, constants
include(":core:model")          // 📊 Domain models & entities
include(":core:database")       // 💾 Room database & DAOs
include(":core:network")        // 🌐 Base networking setup
include(":core:designsystem")   // 🎨 Design tokens, theme, typography
include(":core:testing")        // 🧪 Test utilities & fakes
