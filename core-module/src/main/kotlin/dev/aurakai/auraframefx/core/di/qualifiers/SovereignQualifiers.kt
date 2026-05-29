package dev.aurakai.auraframefx.core.di.qualifiers

import javax.inject.Qualifier

/**
 * Qualifier for the application-wide coroutine scope.
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApplicationScope

/**
 * Qualifier for Pandora-specific secure preferences.
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class PandoraPreferences

/**
 * Qualifier for the Aura settings DataStore.
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuraSettingsDataStore

/**
 * Qualifier for the main app state DataStore.
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AppStateDataStoreAnnotation
