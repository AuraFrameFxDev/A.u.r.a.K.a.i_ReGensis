package dev.aurakai.auraframefx.romtools

import android.annotation.SuppressLint
import dev.aurakai.auraframefx.domains.genesis.models.AgentResponse
import dev.aurakai.auraframefx.domains.genesis.models.AgentType
import dev.aurakai.auraframefx.romtools.bootloader.BootloaderManager
import dev.aurakai.auraframefx.romtools.bootloader.BootloaderSafetyManager
import dev.aurakai.auraframefx.romtools.retention.AurakaiRetentionManager
import dev.aurakai.auraframefx.romtools.retention.RetentionMechanism
import dev.aurakai.auraframefx.romtools.retention.RetentionStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import java.io.File

/**
 * Fake implementation of RomToolsManager for Jetpack Compose previews.
 */
open class FakeRomToolsManager(
    private val safetyManager: BootloaderSafetyManager = FakeBootloaderSafetyManager()
) : RomToolsManager {

    private val _romToolsState = MutableStateFlow(RomToolsState(
        isInitialized = true,
        capabilities = RomCapabilities(
            hasRootAccess = true,
            hasBootloaderAccess = true,
            hasRecoveryAccess = true,
            hasSystemWriteAccess = true,
            supportedArchitectures = listOf("arm64-v8a", "armeabi-v7a"),
            deviceModel = "Pixel 7 Pro",
            androidVersion = "14",
            securityPatchLevel = "2025-10-05"
        )
    ))
    override val romToolsState: StateFlow<RomToolsState> = _romToolsState.asStateFlow()

    private val _operationProgress = MutableStateFlow<OperationProgress?>(null)
    override val operationProgress: StateFlow<OperationProgress?> = _operationProgress.asStateFlow()

    /**
     * Process a ROM operation request using the fake manager for previews.
     *
     * @param request The ROM operation request to process.
     * @return An AgentResponse indicating success with message "Fake operation completed", `agentName` set to "RomTools", and `agentType` set to `AgentType.GENESIS`.
     */
    override suspend fun processRomOperation(request: RomOperationRequest): AgentResponse {
        return AgentResponse.success("Fake operation completed", agentName = "RomTools", agentType = AgentType.GENESIS)
    }

    /**
 * Flash the provided ROM file to the device.
 *
 * @param romFile The ROM package to be flashed.
 * @return A `Result<Unit>` containing `Unit` on success, or a failure describing the error. 
 */
override suspend fun flashRom(romFile: RomFile): Result<Unit> = Result.success(Unit)
    override suspend fun createNandroidBackup(backupName: String): Result<BackupInfo> = Result.success(
        BackupInfo("name", "path", 0, 0, "model", "14", emptyList())
    )
    override suspend fun restoreNandroidBackup(backupInfo: BackupInfo): Result<Unit> = Result.success(Unit)
    override suspend fun installGenesisOptimizations(): Result<Unit> = Result.success(Unit)
    override fun getAvailableRoms(): Result<List<AvailableRom>> = Result.success(emptyList())
    override fun downloadRom(rom: AvailableRom): Flow<DownloadProgress> = flowOf()
    override suspend fun setupAurakaiRetention(): Result<RetentionStatus> = Result.success(
        RetentionStatus(emptyMap(), "path", "pkg", 0)
    )
    override suspend fun unlockBootloader(): Result<Unit> = Result.success(Unit)
    override suspend fun installRecovery(): Result<Unit> = Result.success(Unit)
}

class FakeRomToolsManagerImpl : FakeRomToolsManager()

// === Minimal Fake Managers for Preview ===

class FakeBootloaderManager : BootloaderManager {
    override fun checkBootloaderAccess(): Boolean = true
    override fun isBootloaderUnlocked(): Boolean = true
    override suspend fun unlockBootloader(): Result<Unit> = Result.success(Unit)

    override fun collectPreflightSignals(): BootloaderManager.PreflightSignals {
        return BootloaderManager.PreflightSignals(
            isBootloaderUnlocked = true,
            oemUnlockSupported = true,
            verifiedBootState = "green",
            batteryLevel = 85,
            developerOptionsEnabled = true,
            oemUnlockAllowedUser = true,
            deviceFingerprint = "google/cheetah/cheetah:14/UP1A.231105.003/11010452:user/release-keys"
        )
    }
}

class FakeRecoveryManager : RecoveryManager {
    override fun checkRecoveryAccess(): Boolean = true
    override fun isCustomRecoveryInstalled(): Boolean = true
    override suspend fun installCustomRecovery(): Result<Unit> = Result.success(Unit)
}

class FakeSystemModificationManager : SystemModificationManager {
    override fun checkSystemWriteAccess(): Boolean = true
    override suspend fun installGenesisOptimizations(progressCallback: (Float) -> Unit): Result<Unit> {
        progressCallback(100f)
        return Result.success(Unit)
    }
    override suspend fun verifyOptimizations(): Result<OptimizationStatus> = Result.success(
        OptimizationStatus(true, emptyList(), "1.0", 0)
    )
    override suspend fun removeOptimizations(): Result<Unit> = Result.success(Unit)
}

class FakeFlashManager : FlashManager {
    override suspend fun flashRom(romFile: RomFile, progressCallback: (Float) -> Unit): Result<Unit> = Result.success(Unit)
    override fun downloadRom(rom: AvailableRom): Flow<DownloadProgress> = flowOf()
}

class FakeRomVerificationManager : RomVerificationManager {
    override suspend fun verifyRomFile(romFile: RomFile): Result<Unit> = Result.success(Unit)
    override suspend fun verifyInstallation(): Result<Unit> = Result.success(Unit)
    override suspend fun calculateChecksum(file: File, algorithm: String): Result<String> = Result.success("check")
    override suspend fun verifyZipIntegrity(zipFile: File): Result<ZipVerificationResult> = Result.success(
        ZipVerificationResult(true, emptyList(), emptyList(), 0)
    )
}

class FakeBackupManager : BackupManager {
    override suspend fun createFullBackup(): Result<BackupInfo> = Result.success(
        BackupInfo("name", "path", 0, 0, "model", "14", emptyList())
    )
    override suspend fun createNandroidBackup(name: String, callback: (Float) -> Unit): Result<BackupInfo> = createFullBackup()
    override suspend fun restoreNandroidBackup(info: BackupInfo, callback: (Float) -> Unit): Result<Unit> = Result.success(Unit)
    override suspend fun listBackups(): Result<List<BackupInfo>> = Result.success(emptyList())
    override suspend fun deleteBackup(info: BackupInfo): Result<Unit> = Result.success(Unit)
}

class FakeRetentionManager : AurakaiRetentionManager {
    override suspend fun setupRetentionMechanisms(): Result<RetentionStatus> = Result.success(
        RetentionStatus(emptyMap(), "path", "pkg", 0)
    )
    override suspend fun restoreAurakaiAfterRomFlash(): Result<Unit> = Result.success(Unit)
}

class FakeBootloaderSafetyManager : BootloaderSafetyManager {
    override val safetyStatus: StateFlow<dev.aurakai.auraframefx.romtools.bootloader.BootloaderSafetyStatus> = 
        MutableStateFlow(dev.aurakai.auraframefx.romtools.bootloader.BootloaderSafetyStatus(
            true, true, 100, 1000, 
            dev.aurakai.auraframefx.romtools.bootloader.BootState.VERIFIED,
            dev.aurakai.auraframefx.romtools.bootloader.SELinuxMode.ENFORCING,
            true, 0
        )).asStateFlow()

    override suspend fun performPreFlightChecks(op: dev.aurakai.auraframefx.romtools.bootloader.BootloaderOperation) = 
        dev.aurakai.auraframefx.romtools.bootloader.SafetyCheckResult(true, emptyList(), emptyList(), false)

    override suspend fun monitorOperationState() = 
        dev.aurakai.auraframefx.romtools.bootloader.StateMonitoringResult(true, true, true, false)

    override suspend fun createSafetyCheckpoint(): String = "check"

    override suspend fun validatePostOperationState(op: dev.aurakai.auraframefx.romtools.bootloader.BootloaderOperation) = 
        dev.aurakai.auraframefx.romtools.bootloader.ValidationResult(true, emptyList(), false)
}