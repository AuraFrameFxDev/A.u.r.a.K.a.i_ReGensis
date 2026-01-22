package dev.aurakai.auraframefx.iconify

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class IconPickerViewModel @Inject constructor(
    val iconifyService: IconifyService
) : ViewModel()
