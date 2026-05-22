package dev.aurakai.auraframefx.ui.theme

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemeService @Inject constructor() {

    fun parseThemeCommand(command: String): ThemeCommand {
        val keywords = command.lowercase()
            .split(Regex("[\\s,;.!?]+"))
            .filter { it.isNotBlank() }

        return when {
            keywords.any { it.contains("dark") } -> ThemeCommand.SetTheme(ThemeMode.DARK)
            keywords.any { it.contains("light") } -> ThemeCommand.SetTheme(ThemeMode.LIGHT)
            keywords.any { it.contains("cyberpunk") } -> ThemeCommand.SetTheme(ThemeMode.CYBERPUNK)
            keywords.any { it.contains("solarized") } -> ThemeCommand.SetTheme(ThemeMode.SOLARIZED)
            keywords.any { it.contains("red") } -> ThemeCommand.SetColor(ThemeColor.RED)
            keywords.any { it.contains("blue") } -> ThemeCommand.SetColor(ThemeColor.BLUE)
            keywords.any { it.contains("green") } -> ThemeCommand.SetColor(ThemeColor.GREEN)
            else -> ThemeCommand.Unknown
        }
    }
}

sealed class ThemeCommand {
    data class SetTheme(val theme: ThemeMode) : ThemeCommand()
    data class SetColor(val color: ThemeColor) : ThemeCommand()
    object Unknown : ThemeCommand()
}

enum class ThemeMode {
    LIGHT,
    DARK,
    CYBERPUNK,
    SOLARIZED
}

enum class ThemeColor {
    RED,
    GREEN,
    BLUE
}
