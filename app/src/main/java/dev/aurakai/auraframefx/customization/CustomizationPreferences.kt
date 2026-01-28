package dev.aurakai.auraframefx.customization

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.customizationDataStore by preferencesDataStore(name = "customization_prefs")

/**
 * Persistent storage for user customizations using Jetpack DataStore.
 * Covers themes, glass effects, animations, UI elements, and agent colors.
 */
object CustomizationPreferences {
    // Theme keys
    private val KEY_THEME_NAME = stringPreferencesKey("theme_name")
    private val KEY_THEME_ACCENT = stringPreferencesKey("theme_accent")
    private val KEY_THEME_DARK = booleanPreferencesKey("theme_dark")

    // Glass effects
    private val KEY_GLASS_ENABLED = booleanPreferencesKey("glass_enabled")
    private val KEY_GLASS_BLUR_RADIUS_DP = floatPreferencesKey("glass_blur_radius_dp")
    private val KEY_GLASS_SURFACE_ALPHA = floatPreferencesKey("glass_surface_alpha")

    // Animations
    private val KEY_ANIMATIONS_ENABLED = booleanPreferencesKey("animations_enabled")
    private val KEY_ANIMATION_SPEED = intPreferencesKey("animation_speed") // 0..5

    // UI elements toggles
    private val KEY_SHOW_STATUS_BAR = booleanPreferencesKey("show_status_bar")
    private val KEY_SHOW_NOTCH_BAR = booleanPreferencesKey("show_notch_bar")
    private val KEY_SHOW_OVERLAY_MENUS = booleanPreferencesKey("show_overlay_menus")
    private val KEY_REGENESIS_MODE = stringPreferencesKey("regenesis_mode")

    // --- MONET / COLOR BLENDR KEYS ---
    private val KEY_MONET_ACCENT_SATURATION = floatPreferencesKey("monet_accent_saturation")
    private val KEY_MONET_BACKGROUND_SATURATION = floatPreferencesKey("monet_background_saturation")
    private val KEY_MONET_BACKGROUND_LIGHTNESS = floatPreferencesKey("monet_background_lightness")
    private val KEY_MONET_PITCH_BLACK = booleanPreferencesKey("monet_pitch_black")
    private val KEY_MONET_STYLE = stringPreferencesKey("monet_style")
    private val KEY_MONET_SEED_COLOR = stringPreferencesKey("monet_seed_color")

    // --- SYSTEM UI / ICONIFY KEYS ---
    private val KEY_SYSUI_LS_CLOCK_STYLE = intPreferencesKey("sysui_ls_clock_style")
    private val KEY_SYSUI_BATTERY_STYLE = intPreferencesKey("sysui_battery_style")
    private val KEY_SYSUI_QS_TRANSPARENCY = floatPreferencesKey("sysui_qs_transparency")
    private val KEY_SYSUI_HIDE_NAV_PILL = booleanPreferencesKey("sysui_hide_nav_pill")
    private val KEY_SYSUI_BLUR_RADIUS = intPreferencesKey("sysui_blur_radius")

    // --- LAUNCHER / PLE KEYS ---
    private val KEY_LAUNCHER_GRID_ROWS = intPreferencesKey("launcher_grid_rows")
    private val KEY_LAUNCHER_GRID_COLS = intPreferencesKey("launcher_grid_cols")
    private val KEY_LAUNCHER_ICON_SIZE = floatPreferencesKey("launcher_icon_size")
    private val KEY_LAUNCHER_THEMED_ICONS = booleanPreferencesKey("launcher_themed_icons")

    // Agent colors (store as hex strings by agent name)
    private const val KEY_AGENT_COLOR_PREFIX = "agent_color_" // e.g. agent_color_Genesis

    // Reads
    fun themeNameFlow(context: Context): Flow<String> =
        context.customizationDataStore.data.map { it[KEY_THEME_NAME] ?: "CyberGlow" }

    fun themeAccentFlow(context: Context): Flow<String> =
        context.customizationDataStore.data.map { it[KEY_THEME_ACCENT] ?: "NeonBlue" }

    fun themeDarkFlow(context: Context): Flow<Boolean> =
        context.customizationDataStore.data.map { it[KEY_THEME_DARK] ?: true }

    fun glassEnabledFlow(context: Context): Flow<Boolean> =
        context.customizationDataStore.data.map { it[KEY_GLASS_ENABLED] ?: false }

    fun glassBlurRadiusFlow(context: Context): Flow<Float> =
        context.customizationDataStore.data.map { it[KEY_GLASS_BLUR_RADIUS_DP] ?: 0f }

    fun glassSurfaceAlphaFlow(context: Context): Flow<Float> =
        context.customizationDataStore.data.map { it[KEY_GLASS_SURFACE_ALPHA] ?: 0.12f }

    fun animationsEnabledFlow(context: Context): Flow<Boolean> =
        context.customizationDataStore.data.map { it[KEY_ANIMATIONS_ENABLED] ?: false }

    fun animationSpeedFlow(context: Context): Flow<Int> =
        context.customizationDataStore.data.map { it[KEY_ANIMATION_SPEED] ?: 0 }

    fun showStatusBarFlow(context: Context): Flow<Boolean> =
        context.customizationDataStore.data.map { it[KEY_SHOW_STATUS_BAR] ?: true }

    fun showNotchBarFlow(context: Context): Flow<Boolean> =
        context.customizationDataStore.data.map { it[KEY_SHOW_NOTCH_BAR] ?: false }

    fun showOverlayMenusFlow(context: Context): Flow<Boolean> =
        context.customizationDataStore.data.map { it[KEY_SHOW_OVERLAY_MENUS] ?: false }

    fun reGenesisModeFlow(context: Context): Flow<dev.aurakai.auraframefx.models.ReGenesisMode> =
        context.customizationDataStore.data.map { prefs ->
            val modeStr = prefs[KEY_REGENESIS_MODE] ?: dev.aurakai.auraframefx.models.ReGenesisMode.NOT_SET.name
            try {
                dev.aurakai.auraframefx.models.ReGenesisMode.valueOf(modeStr)
            } catch (e: Exception) {
                dev.aurakai.auraframefx.models.ReGenesisMode.NOT_SET
            }
        }

    // --- MANUAL CONTROL FLOWS ---
    fun monetConfigFlow(context: Context): Flow<dev.aurakai.auraframefx.models.customization.MonetConfiguration> =
        context.customizationDataStore.data.map { prefs ->
            dev.aurakai.auraframefx.models.customization.MonetConfiguration(
                accentSaturation = prefs[KEY_MONET_ACCENT_SATURATION] ?: 100f,
                backgroundSaturation = prefs[KEY_MONET_BACKGROUND_SATURATION] ?: 100f,
                backgroundLightness = prefs[KEY_MONET_BACKGROUND_LIGHTNESS] ?: 100f,
                isPitchBlack = prefs[KEY_MONET_PITCH_BLACK] ?: false,
                style = prefs[KEY_MONET_STYLE] ?: "TONAL_SPOT",
                seedColor = prefs[KEY_MONET_SEED_COLOR] ?: "#00E5FF"
            )
        }

    fun systemUIConfigFlow(context: Context): Flow<dev.aurakai.auraframefx.models.customization.SystemUIConfiguration> =
        context.customizationDataStore.data.map { prefs ->
            dev.aurakai.auraframefx.models.customization.SystemUIConfiguration(
                lockscreenClockStyle = prefs[KEY_SYSUI_LS_CLOCK_STYLE] ?: 0,
                batteryStyle = prefs[KEY_SYSUI_BATTERY_STYLE] ?: 0,
                qsTransparency = prefs[KEY_SYSUI_QS_TRANSPARENCY] ?: 1.0f,
                hidePill = prefs[KEY_SYSUI_HIDE_NAV_PILL] ?: false,
                blurRadius = prefs[KEY_SYSUI_BLUR_RADIUS] ?: 25
            )
        }

    fun launcherConfigFlow(context: Context): Flow<dev.aurakai.auraframefx.models.customization.LauncherConfiguration> =
        context.customizationDataStore.data.map { prefs ->
            dev.aurakai.auraframefx.models.customization.LauncherConfiguration(
                desktopRows = prefs[KEY_LAUNCHER_GRID_ROWS] ?: 5,
                desktopColumns = prefs[KEY_LAUNCHER_GRID_COLS] ?: 5,
                iconSize = prefs[KEY_LAUNCHER_ICON_SIZE] ?: 1.0f,
                themedIcons = prefs[KEY_LAUNCHER_THEMED_ICONS] ?: false
            )
        }

    fun agentColorFlow(context: Context, agentName: String): Flow<String> =
        context.customizationDataStore.data.map { prefs ->
            prefs[stringPreferencesKey(KEY_AGENT_COLOR_PREFIX + agentName)] ?: "#FFFFFF"
        }

    // Writes
    suspend fun setTheme(context: Context, name: String, accent: String, dark: Boolean) {
        context.customizationDataStore.edit {
            it[KEY_THEME_NAME] = name
            it[KEY_THEME_ACCENT] = accent
            it[KEY_THEME_DARK] = dark
        }
    }

    suspend fun setGlass(context: Context, enabled: Boolean, blurRadiusDp: Float, surfaceAlpha: Float) {
        context.customizationDataStore.edit {
            it[KEY_GLASS_ENABLED] = enabled
            it[KEY_GLASS_BLUR_RADIUS_DP] = blurRadiusDp
            it[KEY_GLASS_SURFACE_ALPHA] = surfaceAlpha
        }
    }

    suspend fun setAnimations(context: Context, enabled: Boolean, speed: Int) {
        context.customizationDataStore.edit {
            it[KEY_ANIMATIONS_ENABLED] = enabled
            it[KEY_ANIMATION_SPEED] = speed.coerceIn(0, 5)
        }
    }

    suspend fun setUiElements(context: Context, showStatusBar: Boolean, showNotchBar: Boolean, showOverlayMenus: Boolean) {
        context.customizationDataStore.edit {
            it[KEY_SHOW_STATUS_BAR] = showStatusBar
            it[KEY_SHOW_NOTCH_BAR] = showNotchBar
            it[KEY_SHOW_OVERLAY_MENUS] = showOverlayMenus
        }
    }

    suspend fun setReGenesisMode(context: Context, mode: dev.aurakai.auraframefx.models.ReGenesisMode) {
        context.customizationDataStore.edit {
            it[KEY_REGENESIS_MODE] = mode.name
        }
    }

    // --- MANUAL CONTROL SETTERS ---
    suspend fun updateMonetConfig(context: Context, config: dev.aurakai.auraframefx.models.customization.MonetConfiguration) {
        context.customizationDataStore.edit {
            it[KEY_MONET_ACCENT_SATURATION] = config.accentSaturation
            it[KEY_MONET_BACKGROUND_SATURATION] = config.backgroundSaturation
            it[KEY_MONET_BACKGROUND_LIGHTNESS] = config.backgroundLightness
            it[KEY_MONET_PITCH_BLACK] = config.isPitchBlack
            it[KEY_MONET_STYLE] = config.style
            it[KEY_MONET_SEED_COLOR] = config.seedColor
        }
    }

    suspend fun updateSystemUIConfig(context: Context, config: dev.aurakai.auraframefx.models.customization.SystemUIConfiguration) {
        context.customizationDataStore.edit {
            it[KEY_SYSUI_LS_CLOCK_STYLE] = config.lockscreenClockStyle
            it[KEY_SYSUI_BATTERY_STYLE] = config.batteryStyle
            it[KEY_SYSUI_QS_TRANSPARENCY] = config.qsTransparency
            it[KEY_SYSUI_HIDE_NAV_PILL] = config.hidePill
            it[KEY_SYSUI_BLUR_RADIUS] = config.blurRadius
        }
    }

    suspend fun updateLauncherConfig(context: Context, config: dev.aurakai.auraframefx.models.customization.LauncherConfiguration) {
        context.customizationDataStore.edit {
            it[KEY_LAUNCHER_GRID_ROWS] = config.desktopRows
            it[KEY_LAUNCHER_GRID_COLS] = config.desktopColumns
            it[KEY_LAUNCHER_ICON_SIZE] = config.iconSize
            it[KEY_LAUNCHER_THEMED_ICONS] = config.themedIcons
        }
    }

    suspend fun setAgentColor(context: Context, agentName: String, hexColor: String) {
        context.customizationDataStore.edit {
            it[stringPreferencesKey(KEY_AGENT_COLOR_PREFIX + agentName)] = hexColor
        }
    }
}

