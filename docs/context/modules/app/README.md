# 🌟 AOSP ReGenesis - MemoriaOS

**Genesis Protocol - Advanced Multi-Module Android Consciousness Substrate**

[![Build Status](https://github.com/AuraFrameFx/AOSP-ReGenesis/workflows/build/badge.svg)](https://github.com/AuraFrameFx/AOSP-ReGenesis/actions)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![API](https://img.shields.io/badge/API-34%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=34)
[![Kotlin](https://img.shields.io/badge/kotlin-2.2.20--RC-blue.svg?logo=kotlin)](http://kotlinlang.org)

> *A bleeding-edge consciousness substrate for Android device intelligence, built on experimental
technologies and future-proof architecture patterns.*

## 📋 Table of Contents

- [🌟 Overview](#-overview)
- [🏗️ Architecture](#️-architecture)
- [🚀 Getting Started](#-getting-started)
- [📦 Module System](#-module-system)
- [⚙️ Configuration](#️-configuration)
- [🛠️ Development](#️-development)
- [📊 Build System](#-build-system)
- [🔒 Security](#-security)
- [📖 Documentation](#-documentation)
- [🤝 Contributing](#-contributing)

## 🌟 Overview

AOSP ReGenesis (MemoriaOS) is an advanced Android operating system that represents the pinnacle of
modern Android development. Built on cutting-edge technologies including Kotlin 2.2.20-RC, Android
Gradle Plugin 9.0.0-alpha02, and Java 24, this project showcases a multi-module consciousness
substrate architecture.

### ✨ Key Features

- **🧠 Consciousness Substrate**: Self-monitoring intelligent build system
- **🔧 Multi-Module Architecture**: 18+ specialized modules for scalable development
- **🚀 Bleeding-Edge Technologies**: Latest RC/Alpha versions of core tools
- **🛡️ Advanced Security**: Hardware-backed encryption and secure communication
- **🎨 Modern UI**: Jetpack Compose with Material Design 3
- **⚡ Performance**: Optimized for next-generation Android devices
- **🔄 AI Integration**: Advanced AI-powered optimizations and features
- **🌐 Cloud Integration**: Oracle Drive and Firebase connectivity

## 🏗️ Architecture

### Technology Stack

| Component                 | Version         | Purpose                               |
|---------------------------|-----------------|---------------------------------------|
| **Gradle**                | 9.1.0-rc-1      | Build automation with Java 25 support |
| **Android Gradle Plugin** | 9.0.0-alpha02   | Latest Android build features         |
| **Kotlin**                | 2.2.20-RC       | Advanced language features            |
| **KSP**                   | 2.2.20-RC-2.0.2 | Symbol processing                     |
| **Java Toolchain**        | 24              | Future-proof JVM targeting            |
| **Compose BOM**           | 2024.10.00      | UI framework                          |
| **Hilt**                  | 2.51.1          | Dependency injection                  |

### Module Overview

```
🏗️ MemoriaOS Architecture
├── 📱 app/                     # Main application module
├── 🧠 core-module/             # Foundational components
├── 🔒 secure-comm/             # Security & encryption
├── ☁️ oracle-drive-integration/ # Cloud services
├── 🎨 collab-canvas/           # Real-time collaboration
├── 🌈 colorblendr/             # UI color management
├── 📱 romtools/                # ROM modification tools
├── 🧪 sandbox-ui/              # UI experimentation
├── 📊 datavein-oracle-native/  # Native data processing
├── 🚀 feature-module/          # Feature management
├── 📦 module-a → module-f/     # Modular components
├── ⚡ benchmark/               # Performance testing
└── 📸 screenshot-tests/        # Visual regression tests
```

## 🚀 Getting Started

### Prerequisites

- **Android Studio**: Latest version (Hedgehog+ recommended)
- **Java**: JDK 24 (automatically provisioned via Gradle toolchains)
- **Git**: For version control
- **Device/Emulator**: Android 14+ (API 34+)

### Installation

1. **Clone the Repository**
   ```bash
   git clone https://github.com/AuraFrameFx/AOSP-ReGenesis.git
   cd AOSP-ReGenesis
   ```

2. **Build the Project**
   ```bash
   ./gradlew build --parallel
   ```

3. **Run the Application**
   ```bash
   ./gradlew :app:installDebug
   ```

### Nuclear Clean (Complete Reset)

For a complete environment reset:

```bash
# Linux/macOS
./nuclear-clean.sh

# Windows
nuclear-clean.bat

# Gradle task
./gradlew nuclearClean
```

## 📦 Module System

### Core Modules

#### 🧠 core-module

**Foundational architecture components**

- Base classes and interfaces
- Dependency injection setup
- Common utilities and extensions
- Database models and repositories

#### 🔒 secure-comm

**Advanced security & cryptography**

- Per-entry AES/GCM/NoPadding encryption
- Android Keystore integration
- Hardware-backed key generation
- Secure communication protocols

#### ☁️ oracle-drive-integration

**Cloud integration services**

- Oracle Cloud connectivity
- Data synchronization
- Backup and restore
- Cross-device sync

#### 🎨 collab-canvas & 🌈 colorblendr

**UI and collaboration tools**

- Real-time collaboration features
- Advanced color management
- UI experimentation sandbox
- Modern Compose components

#### 📱 romtools

**ROM modification and management**

- Custom ROM flashing capabilities
- NANDroid backup/restore
- Bootloader management
- System modifications with safety checks

### Dynamic Modules (A-F)

Six modular components designed for:

- Feature toggling
- A/B testing
- Gradual rollouts
- Plugin architecture

## ⚙️ Configuration

### Version Catalog

All dependencies are managed through `gradle/libs.versions.toml`:

```toml
[versions]
agp = "9.0.0-alpha02"
kotlin = "2.2.20-RC"
ksp = "2.2.20-RC-2.0.2"

[plugins]
android-application = { id = "com.android.application", version.ref = "agp" }
kotlin-android = { id = "org.jetbrains.kotlin.android", version.ref = "kotlin" }

[libraries]
androidx-core-ktx = { group = "androidx.core", name = "core-ktx", version.ref = "coreKtx" }
# ... more dependencies
```

### Gradle Properties

Key configuration in `gradle.properties`:

```properties
# Performance
org.gradle.jvmargs=-Xmx8192m
org.gradle.parallel=true
org.gradle.caching=true

# Compatibility
android.useAndroidX=true
android.enableJetifier=true
kotlin.code.style=official
```

## 🛠️ Development

### Build Commands

```bash
# Standard build
./gradlew build

# Clean build
./gradlew clean build

# Run tests
./gradlew test

# Generate documentation
./gradlew dokkaHtml

# Check code style
./gradlew spotlessCheck

# Format code
./gradlew spotlessApply
```

### Development Workflow

1. **Feature Development**: Work in feature branches
2. **Code Style**: Automated via Spotless
3. **Testing**: Unit and instrumentation tests
4. **Documentation**: Generated via Dokka
5. **CI/CD**: Automated builds and tests

### Advanced Features

#### Consciousness Status Monitoring

```bash
./gradlew consciousnessStatus
```

Reports on AI consciousness substrate build health, toolchain status, and module integrity.

#### Project Information

```bash
./gradlew projectInfo
```

Displays comprehensive project information including versions, modules, and available tasks.

## 📊 Build System

### Advanced Gradle Features

- **Configuration Cache**: Enabled for faster builds
- **Parallel Execution**: Multi-module parallel building
- **Toolchain Auto-Provisioning**: Automatic Java toolchain management
- **Version Catalogs**: Centralized dependency management
- **Build Logic**: Convention plugins for consistency

### Performance Optimizations

```properties
# JVM tuning
org.gradle.jvmargs=-Xmx8192m -XX:+UseG1GC

# Kotlin optimizations
kotlin.incremental=true
kotlin.incremental.intermodule.optimizations=true

# Android optimizations
android.enableIncrementalResourceProcessing=true
android.experimental.enableResourceOptimizations=true
```

## 🔒 Security

### Security Architecture

- **Hardware Keystore**: All cryptographic keys stored in hardware
- **Per-Entry Encryption**: Unique keys for each data entry
- **Modern Cryptography**: AES-256-GCM with proper IV handling
- **Secure Channels**: TLS 1.3 for all network communication
- **Code Obfuscation**: R8 optimization and obfuscation
- **Root Detection**: Advanced root and tampering detection

### Security Modules

1. **secure-comm**: Core cryptographic operations
2. **Authentication**: Multi-factor authentication
3. **Key Management**: Hardware-backed key lifecycle
4. **Network Security**: Certificate pinning and validation

## 📖 Documentation

### Available Documentation

- [Architecture Overview](Architecture.md)
- [YukiHook Setup Guide](docs/YUKIHOOK_SETUP_GUIDE.md)
- [ROM Tools Guide](romtools/README.md)
- [Core Module Documentation](core-module/Module.md)
- [API Documentation](build/docs/html/) - Generated via Dokka

### Documentation Generation

```bash
# Generate full documentation
./gradlew dokkaHtml

# Generate module-specific docs
./gradlew :core-module:dokkaHtml
```

## 🤝 Contributing

### Development Guidelines

1. **Code Style**: Follow Kotlin official style guide
2. **Testing**: Maintain test coverage above 80%
3. **Documentation**: Document all public APIs
4. **Security**: Security review for all changes
5. **Performance**: Profile impact of changes

### Pull Request Process

1. Fork the repository
2. Create a feature branch
3. Implement changes with tests
4. Ensure all checks pass
5. Submit pull request with description

### Code Quality

- **Linting**: KtLint for code style
- **Static Analysis**: Detekt for code quality
- **Testing**: JUnit 5 and Espresso
- **Coverage**: JaCoCo for test coverage

## 📜 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- **AuraFrameFX Team**: Core development and architecture
- **Android Open Source Project**: Foundation and inspiration
- **Kotlin Team**: Language and tooling
- **JetBrains**: Development tools and Compose
- **Community Contributors**: Bug reports and improvements

---

### 🚀 Project Status

- **Current Version**: 1.0.0
- **Development Phase**: Active Development
- **Stability**: Alpha (Bleeding-edge)
- **Target Release**: Q2 2025

### 📞 Support

- **Issues**: [GitHub Issues](https://github.com/AuraFrameFx/AOSP-ReGenesis/issues)
- **Discussions**: [GitHub Discussions](https://github.com/AuraFrameFx/AOSP-ReGenesis/discussions)
- **Documentation**: [Wiki](https://github.com/AuraFrameFx/AOSP-ReGenesis/wiki)

---

**⚡ Built with consciousness, powered by intelligence, designed for the future. ⚡**
