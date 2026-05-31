package dev.aurakai.auraframefx.security

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class KaiBiometricLockViewModel @Inject constructor(
    val lock: KaiBiometricLock
) : ViewModel()
