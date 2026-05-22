package dev.aurakai.auraframefx.ui.theme

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val themeService: ThemeService
) : ViewModel() {

    private val _theme = MutableStateFlow(ThemeMode.DARK)
    val theme: StateFlow<ThemeMode> = _theme.asStateFlow()

    private val _color = MutableStateFlow(ThemeColor.BLUE)
    val color: StateFlow<ThemeColor> = _color.asStateFlow()

    fun processThemeCommand(command: String) {
        when (val result = themeService.parseThemeCommand(command)) {
            is ThemeCommand.SetTheme -> _theme.value = result.theme
            is ThemeCommand.SetColor -> _color.value = result.color
            ThemeCommand.Unknown -> { /* Ignore */
            }
        }
    }
}
