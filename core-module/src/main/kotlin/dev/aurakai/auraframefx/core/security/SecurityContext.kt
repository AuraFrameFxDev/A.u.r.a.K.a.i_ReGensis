package dev.aurakai.auraframefx.core.security

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.aurakai.auraframefx.core.identity.AgentType
import dev.aurakai.auraframefx.core.security.KeystoreManager
import dev.aurakai.auraframefx.core.util.HexUtil
import dev.aurakai.auraframefx.core.models.SecurityThreat
import dev.aurakai.auraframefx.core.models.ThreatSeverity
import dev.aurakai.auraframefx.core.models.ThreatType
import dev.aurakai.auraframefx.core.models.ThreatLevel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import timber.log.Timber
import java.security.MessageDigest
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration.Companion.milliseconds

@Singleton
class SecurityContext @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val keystoreManager: KeystoreManager
) {

    companion object {
        private const val TAG = "SecurityContext"
        private const val THREAT_DETECTION_INTERVAL_MS = 30_000L // 30 seconds
        private const val AES_ALGORITHM_WITH_PADDING = "AES/CBC/PKCS7Padding"
    }

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val _securityState = MutableStateFlow(KaiSecurityState())
    val securityState: StateFlow<KaiSecurityState> = _securityState.asStateFlow()

    private val _threatDetectionActive = MutableStateFlow(false)
    val threatDetectionActive: StateFlow<Boolean> = _threatDetectionActive.asStateFlow()

    private val _permissionsState = MutableStateFlow<Map<String, Boolean>>(emptyMap())
    val permissionsState: StateFlow<Map<String, Boolean>> = _permissionsState.asStateFlow()

    private val _encryptionStatus =
        MutableStateFlow<EncryptionStatus>(EncryptionStatus.NOT_INITIALIZED)
    val encryptionStatus: StateFlow<EncryptionStatus> = _encryptionStatus.asStateFlow()

    init {
        Timber.tag(TAG).d("Security context initialized by KAI")
        updatePermissionsState()
    }

    fun validateContent(content: String) {
        // TODO: Implement content safety / prompt injection / malware scanning
        Timber.tag(TAG).v("Content validation requested: ${content.take(50)}...")
    }

    fun validateImageData(imageData: ByteArray) {
        Timber.tag(TAG).d("Validating image data (${imageData.size} bytes)")
        // TODO: Add malware scan, deepfake detection, etc.
    }

    fun startThreatDetection() {
        if (_threatDetectionActive.value) return

        _threatDetectionActive.value = true
        scope.launch {
            while (_threatDetectionActive.value) {
                try {
                    val threats = detectThreats()
                    _securityState.value = _securityState.value.copy(
                        detectedThreats = threats,
                        threatLevel = calculateThreatLevel(threats),
                        lastScanTime = System.currentTimeMillis()
                    )
                    kotlinx.coroutines.delay(THREAT_DETECTION_INTERVAL_MS.milliseconds)
                } catch (e: Exception) {
                    Timber.tag(TAG).e(e, "Threat detection loop failed")
                    _threatDetectionActive.value = false
                    _securityState.value = _securityState.value.copy(
                        errorState = true,
                        errorMessage = "Threat detection error: ${e.message}"
                    )
                }
            }
        }
    }

    fun stopThreatDetection() {
        _threatDetectionActive.value = false
    }

    fun hasPermission(permission: String): Boolean =
        ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED

    fun updatePermissionsState() {
        val permissionsToCheck = listOf(
            android.Manifest.permission.RECORD_AUDIO,
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.READ_MEDIA_IMAGES,
            android.Manifest.permission.READ_MEDIA_VIDEO,
            android.Manifest.permission.INTERNET
        )

        _permissionsState.value = permissionsToCheck.associateWith(::hasPermission)
    }

    fun initializeEncryption(): Boolean {
        val secretKey = keystoreManager.getOrCreateSecretKey()
        return if (secretKey != null) {
            _encryptionStatus.value = EncryptionStatus.ACTIVE
            _securityState.value = _securityState.value.copy(
                errorState = false,
                errorMessage = null
            )
            Timber.tag(TAG).i("Encryption initialized successfully via Keystore")
            true
        } else {
            _encryptionStatus.value = EncryptionStatus.ERROR
            _securityState.value = _securityState.value.copy(
                errorState = true,
                errorMessage = "Keystore key initialization failed"
            )
            Timber.tag(TAG).e("Failed to initialize encryption")
            false
        }
    }

    fun encrypt(data: String): EncryptedData? {
        if (_encryptionStatus.value != EncryptionStatus.ACTIVE) {
            if (!initializeEncryption()) return null
        }

        return try {
            val secretKey = keystoreManager.getOrCreateSecretKey() ?: return null

            val iv = ByteArray(16).apply { SecureRandom().nextBytes(this) }
            val ivSpec = IvParameterSpec(iv)

            val cipher = Cipher.getInstance(AES_ALGORITHM_WITH_PADDING)
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec)

            val encryptedBytes = cipher.doFinal(data.toByteArray(Charsets.UTF_8))

            EncryptedData(
                data = encryptedBytes,
                iv = iv,
                timestamp = System.currentTimeMillis(),
                metadata = "KAI Keystore AES-CBC"
            )
        } catch (e: Exception) {
            Timber.tag(TAG).e(e, "Encryption failed")
            null
        }
    }

    fun decrypt(encryptedData: EncryptedData): String? {
        if (_encryptionStatus.value != EncryptionStatus.ACTIVE) {
            if (!initializeEncryption()) return null
        }

        return try {
            val cipher = keystoreManager.getDecryptionCipher(encryptedData.iv) ?: return null
            val decryptedBytes = cipher.doFinal(encryptedData.data)
            String(decryptedBytes, Charsets.UTF_8)
        } catch (e: Exception) {
            Timber.tag(TAG).e(e, "Decryption failed")
            null
        }
    }

    fun shareSecureContextWith(agentType: AgentType, context: String): SharedSecureContext {
        return SharedSecureContext(
            id = generateSecureId(),
            originatingAgent = AgentType.KAI,
            targetAgent = agentType,
            encryptedContent = context.toByteArray(Charsets.UTF_8),
            timestamp = System.currentTimeMillis(),
            expiresAt = System.currentTimeMillis() + 3_600_000 // 1 hour
        )
    }

    fun verifyApplicationIntegrity(): ApplicationIntegrity {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(
                context.packageName,
                PackageManager.PackageInfoFlags.of(PackageManager.GET_SIGNING_CERTIFICATES.toLong())
            )

            val signatureBytes = packageInfo.signingInfo
                ?.apkContentsSigners
                ?.firstOrNull()
                ?.toByteArray()
                ?: throw IllegalStateException("No signing certificate found")

            val md = MessageDigest.getInstance("SHA-256")
            val signatureDigest = md.digest(signatureBytes)
            // ⚡ Bolt Optimization: Use HexUtil for allocation-free hex encoding
            val signatureHex = HexUtil.encodeHex(signatureDigest)

            ApplicationIntegrity(
                verified = true,
                appVersion = packageInfo.versionName ?: "unknown",
                signatureHash = signatureHex,
                installTime = packageInfo.firstInstallTime,
                lastUpdateTime = packageInfo.lastUpdateTime
            )
        } catch (e: Exception) {
            Timber.tag(TAG).e(e, "Integrity check failed")
            ApplicationIntegrity(
                verified = false,
                appVersion = "unknown",
                signatureHash = "error",
                installTime = 0,
                lastUpdateTime = 0,
                errorMessage = e.message
            )
        }
    }

    private fun detectThreats(): List<SecurityThreat> {
        // Real threat detection would go here (root detection, emulator, hooking, etc.)
        return listOf(
            SecurityThreat(
                id = "SIM-001",
                type = ThreatType.PERMISSION_ABUSE,
                severity = ThreatSeverity.LOW,
                description = "Simulated permission check",
                detectedAt = System.currentTimeMillis()
            )
        ).filter { Math.random() > 0.6 }
    }

    private fun calculateThreatLevel(threats: List<SecurityThreat>): ThreatLevel {
        if (threats.isEmpty()) return ThreatLevel.LOW
        return when {
            threats.any { it.severity == ThreatSeverity.CRITICAL } -> ThreatLevel.CRITICAL
            threats.any { it.severity == ThreatSeverity.HIGH } -> ThreatLevel.HIGH
            threats.any { it.severity == ThreatSeverity.MEDIUM } -> ThreatLevel.MEDIUM
            else -> ThreatLevel.LOW
        }
    }

    private fun generateSecureId(): String {
        val bytes = ByteArray(16)
        SecureRandom().nextBytes(bytes)
        // ⚡ Bolt Optimization: Use HexUtil for allocation-free hex encoding
        return HexUtil.encodeHex(bytes)
    }

    fun logSecurityEvent(event: SecurityEvent) {
        scope.launch {
            val eventJson = Json.encodeToString(SecurityEvent.serializer(), event)
            when (event.severity) {
                EventSeverity.INFO -> Timber.tag("SecurityEvent").i(eventJson)
                EventSeverity.WARNING -> Timber.tag("SecurityEvent").w(eventJson)
                EventSeverity.ERROR -> Timber.tag("SecurityEvent").e(eventJson)
                EventSeverity.CRITICAL -> Timber.tag("SecurityEvent").wtf(eventJson)
            }
        }
    }

    fun validateRequest(requestType: String, requestData: String) {
        logSecurityEvent(
            SecurityEvent(
                type = SecurityEventType.VALIDATION,
                details = "Request validation for: $requestType",
                severity = EventSeverity.INFO
            )
        )
    }

    fun isSecure(): Boolean {
        return _encryptionStatus.value == EncryptionStatus.ACTIVE &&
                !_securityState.value.errorState &&
                _securityState.value.threatLevel != ThreatLevel.CRITICAL
    }
}

// ====================== Models ======================

@Serializable
data class KaiSecurityState(
    val detectedThreats: List<SecurityThreat> = emptyList(),
    val threatLevel: ThreatLevel = ThreatLevel.LOW,
    val lastScanTime: Long = 0,
    val errorState: Boolean = false,
    val errorMessage: String? = null,
)

@Serializable
data class EncryptedData(
    val data: ByteArray,
    val iv: ByteArray,
    val timestamp: Long,
    val metadata: String? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is EncryptedData) return false
        if (!data.contentEquals(other.data)) return false
        if (!iv.contentEquals(other.iv)) return false
        if (timestamp != other.timestamp) return false
        if (metadata != other.metadata) return false
        return true
    }

    override fun hashCode(): Int {
        var result = data.contentHashCode()
        result = 31 * result + iv.contentHashCode()
        result = 31 * result + timestamp.hashCode()
        result = 31 * result + (metadata?.hashCode() ?: 0)
        return result
    }
}

@Serializable
data class ApplicationIntegrity(
    val verified: Boolean,
    val appVersion: String,
    val signatureHash: String,
    val installTime: Long,
    val lastUpdateTime: Long,
    val errorMessage: String? = null,
)

@Serializable
data class SecurityEvent(
    val id: String = java.util.UUID.randomUUID().toString(),
    val type: SecurityEventType,
    val timestamp: Long = System.currentTimeMillis(),
    val details: String,
    val severity: EventSeverity,
)

enum class SecurityEventType {
    VALIDATION,
    PERMISSION_CHANGE,
    THREAT_DETECTED,
    ENCRYPTION_EVENT,
    AUTHENTICATION_EVENT,
    INTEGRITY_CHECK,
    AI_ERROR
}

enum class EventSeverity {
    INFO, WARNING, ERROR, CRITICAL
}

@Serializable
data class SharedSecureContext(
    val id: String,
    val originatingAgent: AgentType,
    val targetAgent: AgentType,
    val encryptedContent: ByteArray,
    val timestamp: Long,
    val expiresAt: Long
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SharedSecureContext) return false
        if (id != other.id) return false
        if (originatingAgent != other.originatingAgent) return false
        if (targetAgent != other.targetAgent) return false
        if (!encryptedContent.contentEquals(other.encryptedContent)) return false
        if (timestamp != other.timestamp) return false
        if (expiresAt != other.expiresAt) return false
        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + originatingAgent.hashCode()
        result = 31 * result + targetAgent.hashCode()
        result = 31 * result + encryptedContent.contentHashCode()
        result = 31 * result + timestamp.hashCode()
        result = 31 * result + expiresAt.hashCode()
        return result
    }
}
