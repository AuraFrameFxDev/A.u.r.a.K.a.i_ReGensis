package dev.aurakai.auraframefx.romtools

class FakeRomToolsManagerImpl : FakeRomToolsManager(
    safetyManager = FakeBootloaderSafetyManager()
)