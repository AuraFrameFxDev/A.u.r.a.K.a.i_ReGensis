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
     * Processes a ROM operation request and returns a fixed successful response for preview use.
     *
     * This fake implementation ignores the request contents and produces a deterministic success
     * response suitable for UI previews.
     *
     * @param request The ROM operation request (ignored by this fake implementation).
     * @return An AgentResponse with message "Fake operation completed", `agentName` set to "RomTools",
     * and `agentType` set to `AgentType.GENESIS`.
     */
    override suspend fun processRomOperation(request: RomOperationRequest): AgentResponse {
        return AgentResponse.success("Fake operation completed", agentName = "RomTools", agentType = AgentType.GENESIS)
    }

    /**
 * Flash the given ROM package to the device.
 *
 * @param romFile The ROM package to flash.
 * @return `Unit` on success, `Result` failure contains error details on failure.
 */
override suspend fun flashRom(romFile: RomFile): Result<Unit> = Result.success(Unit)
    /**
     * Creates a nandroid backup using the provided name.
     *
     * @param backupName The name to assign to the created backup.
     * @return A Result containing the created BackupInfo on success.
     */
    override suspend fun createNandroidBackup(backupName: String): Result<BackupInfo> = Result.success(
        BackupInfo("name", "path", 0, 0, "model", "14", emptyList())
    )
    /**
 * Restores the given Nandroid backup to the device.
 *
 * @param backupInfo Metadata describing the backup to restore (name, path, device model, etc.).
 * @return A Result that is successful when the restore completes, or contains an error on failure.
 */
override suspend fun restoreNandroidBackup(backupInfo: BackupInfo): Result<Unit> = Result.success(Unit)
    /**
 * Installs Genesis system optimizations.
 *
 * @return A Result containing `Unit` when the installation succeeds.
 */
override suspend fun installGenesisOptimizations(): Result<Unit> = Result.success(Unit)
    /**
 * Retrieve the list of available ROMs.
 *
 * @return A Result containing the list of available ROMs; on success the list (possibly empty).
 */
override fun getAvailableRoms(): Result<List<AvailableRom>> = Result.success(emptyList())
    /**
 * Provides a stream of download progress updates for the given available ROM.
 *
 * @param rom The metadata of the ROM to download.
 * @return A Flow that emits `DownloadProgress` updates; in this fake preview implementation it emits no values.
 */
override fun downloadRom(rom: AvailableRom): Flow<DownloadProgress> = flowOf()
    /**
     * Sets up Aurakai retention mechanisms and provides the resulting retention status.
     *
     * @return A Result containing a RetentionStatus populated with an empty map, path "path", package "pkg", and version 0.
     */
    override suspend fun setupAurakaiRetention(): Result<RetentionStatus> = Result.success(
        RetentionStatus(emptyMap(), "path", "pkg", 0)
    )
    /**
 * Attempts to unlock the device bootloader; in this fake implementation the operation is a no-op that always succeeds.
 *
 * @return A successful Result containing Unit.
 */
override suspend fun unlockBootloader(): Result<Unit> = Result.success(Unit)
    /**
 * Installs a custom recovery on the device.
 *
 * @return A Result that is successful when the recovery installation completes (`Unit`), or a failed Result containing the error on failure.
 */
override suspend fun installRecovery(): Result<Unit> = Result.success(Unit)
}

class FakeRomToolsManagerImpl : FakeRomToolsManager()

// === Minimal Fake Managers for Preview ===

class FakeBootloaderManager : BootloaderManager {
    /**
 * Checks whether the current environment allows access to the device bootloader.
 *
 * In this preview implementation, always returns `true`.
 *
 * @return `true` if bootloader access is available, `false` otherwise.
 */
override fun checkBootloaderAccess(): Boolean = true
    /**
 * Check whether the device bootloader is unlocked.
 *
 * @return `true` if the bootloader is unlocked, `false` otherwise.
 */
override fun isBootloaderUnlocked(): Boolean = true
    /**
 * Attempts to unlock the device bootloader; in this fake implementation the operation is a no-op that always succeeds.
 *
 * @return A successful Result containing Unit.
 */
override suspend fun unlockBootloader(): Result<Unit> = Result.success(Unit)

    /**
     * Provide mock preflight signals for bootloader operations used in previews.
     *
     * @return A PreflightSignals instance containing deterministic preview data:
     * - bootloader unlocked (`isBootloaderUnlocked = true`)
     * - OEM unlock supported and allowed (`oemUnlockSupported = true`, `oemUnlockAllowedUser = true`)
     * - verified boot state set to "green"
     * - battery level at 85
     * - developer options enabled (`developerOptionsEnabled = true`)
     * - a sample device fingerprint
     */
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
    /**
 * Indicates whether this manager has access to the device recovery partition.
 *
 * @return `true` if recovery operations are permitted, `false` otherwise.
 */
override fun checkRecoveryAccess(): Boolean = true
    /**
 * Reports whether a custom recovery image is installed on the device.
 *
 * @return `true` if a custom recovery is installed, `false` otherwise.
 */
override fun isCustomRecoveryInstalled(): Boolean = true
    /**
 * Installs a custom recovery on the device.
 *
 * This fake implementation always reports a successful installation.
 *
 * @return A `Result<Unit>` containing `Unit` that indicates the installation succeeded.
 */
override suspend fun installCustomRecovery(): Result<Unit> = Result.success(Unit)
}

class FakeSystemModificationManager : SystemModificationManager {
    /**
 * Check whether the process has permission to write to the system partition.
 *
 * In this fake implementation used for previews, this always reports that write access is available.
 *
 * @return `true` if write access is available, `false` otherwise.
 */
override fun checkSystemWriteAccess(): Boolean = true
    /**
     * Installs Genesis optimizations and reports progress through the provided callback.
     *
     * The callback will be invoked with 100f to indicate completion.
     *
     * @param progressCallback Receives progress as a float percentage (0f–100f); called with 100f on completion.
     * @return A successful Result containing Unit.
     */
    override suspend fun installGenesisOptimizations(progressCallback: (Float) -> Unit): Result<Unit> {
        progressCallback(100f)
        return Result.success(Unit)
    }
    /**
     * Report the installation status and metadata for Genesis optimizations.
     *
     * @return A successful Result containing an OptimizationStatus with `installed` = `true`, an empty list of issues, `version` = "1.0", and `affectedCount` = 0.
     */
    override suspend fun verifyOptimizations(): Result<OptimizationStatus> = Result.success(
        OptimizationStatus(true, emptyList(), "1.0", 0)
    )
    /**
 * Removes installed Genesis optimizations from the device.
 *
 * @return A `Result<Unit>` containing `Unit` on success, or a failure result with the error on failure.
 */
override suspend fun removeOptimizations(): Result<Unit> = Result.success(Unit)
}

class FakeFlashManager : FlashManager {
    /**
 * Flash a ROM file onto the device while reporting progress updates.
 *
 * @param romFile The ROM file to be flashed.
 * @param progressCallback Called periodically with a progress value (0f..100f) indicating flash completion percentage.
 * @return `Result.success(Unit)` if the flash completes successfully, `Result.failure` with the error otherwise.
 */
override suspend fun flashRom(romFile: RomFile, progressCallback: (Float) -> Unit): Result<Unit> = Result.success(Unit)
    /**
 * Provides a stream of download progress updates for the given available ROM.
 *
 * @param rom The metadata of the ROM to download.
 * @return A Flow that emits `DownloadProgress` updates; in this fake preview implementation it emits no values.
 */
override fun downloadRom(rom: AvailableRom): Flow<DownloadProgress> = flowOf()
}

class FakeRomVerificationManager : RomVerificationManager {
    /**
 * Verifies the integrity and authenticity of the provided ROM file.
 *
 * @param romFile The ROM file to verify.
 * @return A Result containing `Unit` if verification succeeds, or a failure Result describing the verification error.
 */
override suspend fun verifyRomFile(romFile: RomFile): Result<Unit> = Result.success(Unit)
    /**
 * Verifies that the current installation is valid.
 *
 * @return `Result.success(Unit)` if the verification passes, or `Result.failure(Throwable)` if verification fails.
 */
override suspend fun verifyInstallation(): Result<Unit> = Result.success(Unit)
    /**
 * Compute the checksum of a file using the specified algorithm.
 *
 * @param file The file to compute the checksum for.
 * @param algorithm The checksum algorithm to use (for example, "MD5", "SHA-1", "SHA-256").
 * @return A Result containing the checksum as a hexadecimal string on success, or a failure Result on error.
 */
override suspend fun calculateChecksum(file: File, algorithm: String): Result<String> = Result.success("check")
    /**
     * Performs integrity verification of the given ZIP archive.
     *
     * @param zipFile The ZIP file to verify.
     * @return A Result wrapping a ZipVerificationResult where `valid` is true, `warnings` and `errors` are empty, and the numeric metric is 0.
     */
    override suspend fun verifyZipIntegrity(zipFile: File): Result<ZipVerificationResult> = Result.success(
        ZipVerificationResult(true, emptyList(), emptyList(), 0)
    )
}

class FakeBackupManager : BackupManager {
    /**
     * Creates a fake full backup used for previews and testing.
     *
     * @return A successful Result containing a dummy BackupInfo with name "name", path "path", model "model", Android version "14", and an empty file list.
     */
    override suspend fun createFullBackup(): Result<BackupInfo> = Result.success(
        BackupInfo("name", "path", 0, 0, "model", "14", emptyList())
    )
    /**
 * Creates a nandroid-style full backup using the provided name.
 *
 * @param name The user-facing name to assign to the backup.
 * @param callback Receives progress updates as a percentage (0f..100f).
 * @return A Result containing the created BackupInfo on success, or an error on failure.
 */
override suspend fun createNandroidBackup(name: String, callback: (Float) -> Unit): Result<BackupInfo> = createFullBackup()
    /**
 * Restores the given Nandroid backup to the device.
 *
 * @param info Backup information identifying the backup to restore.
 * @param callback Invoked with restore progress as a percentage (0f..100f).
 * @return `Result` containing `Unit` on success or a failure `Result` with the error. 
 */
override suspend fun restoreNandroidBackup(info: BackupInfo, callback: (Float) -> Unit): Result<Unit> = Result.success(Unit)
    /**
 * Retrieve the list of available backups.
 *
 * In this fake implementation used for previews, it always returns a successful result with an empty list.
 *
 * @return A Result containing the list of available BackupInfo; an empty list when none are available.
 */
override suspend fun listBackups(): Result<List<BackupInfo>> = Result.success(emptyList())
    /**
 * Delete the specified backup.
 *
 * @param info The BackupInfo describing the backup to remove.
 * @return A Result containing `Unit` on success; this fake implementation always returns success.
 */
override suspend fun deleteBackup(info: BackupInfo): Result<Unit> = Result.success(Unit)
}

class FakeRetentionManager : AurakaiRetentionManager {
    /**
     * Sets up Aurakai retention mechanisms and yields a fake, successful retention status for preview/testing.
     *
     * @return A `Result` containing a `RetentionStatus` with an empty rules map, path `"path"`, package `"pkg"`, and count `0`.
     */
    override suspend fun setupRetentionMechanisms(): Result<RetentionStatus> = Result.success(
        RetentionStatus(emptyMap(), "path", "pkg", 0)
    )
    /**
 * Restores Aurakai retention mechanisms and state following a ROM flash.
 *
 * @return A Result that is successful when the retention restore completes.
 */
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

    /**
         * Run pre-flight safety checks for the given bootloader operation.
         *
         * @param op The bootloader operation to evaluate.
         * @return A SafetyCheckResult describing whether checks passed, any detected errors or warnings, and whether user confirmation is required.
        override suspend fun performPreFlightChecks(op: dev.aurakai.auraframefx.romtools.bootloader.BootloaderOperation) = 
        dev.aurakai.auraframefx.romtools.bootloader.SafetyCheckResult(true, emptyList(), emptyList(), false)

    /**
         * Provides the current bootloader operation monitoring status.
         *
         * @return A StateMonitoringResult whose components indicate, in order:
         *         - whether monitoring is supported,
         *         - whether monitoring is currently running,
         *         - whether the monitored state is healthy,
         *         - whether manual intervention is required.
         */
        override suspend fun monitorOperationState() = 
        dev.aurakai.auraframefx.romtools.bootloader.StateMonitoringResult(true, true, true, false)

    /**
 * Creates a safety checkpoint identifier for bootloader operations.
 *
 * @return A checkpoint ID representing the current safety state. */
override suspend fun createSafetyCheckpoint(): String = "check"

    /**
         * Validate the device state after a bootloader operation.
         *
         * @param op The bootloader operation whose post-operation state to validate.
         * @return A ValidationResult indicating whether the post-operation state is acceptable, any detected issues, and whether further action is required.
         */
        override suspend fun validatePostOperationState(op: dev.aurakai.auraframefx.romtools.bootloader.BootloaderOperation) = 
        dev.aurakai.auraframefx.romtools.bootloader.ValidationResult(true, emptyList(), false)
}