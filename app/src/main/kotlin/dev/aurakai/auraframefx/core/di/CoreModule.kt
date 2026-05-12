package dev.aurakai.auraframefx.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.aurakai.auraframefx.data.datastore.DataStoreManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Provides
    @Singleton
    fun provideDataStoreManager(dataStoreManager: DataStoreManager): DataStoreManager =
        dataStoreManager

    @Provides
    @Singleton
    @dev.aurakai.auraframefx.di.AppStateDataStoreAnnotation
    fun provideAppStateDataStore(dataStoreManager: DataStoreManager): androidx.datastore.core.DataStore<androidx.datastore.preferences.core.Preferences> =
        dataStoreManager.dataStore

    @Provides
    @Singleton
    @dev.aurakai.auraframefx.di.AuraSettingsDataStore
    fun provideAuraSettingsDataStore(dataStoreManager: DataStoreManager): androidx.datastore.core.DataStore<androidx.datastore.preferences.core.Preferences> =
        dataStoreManager.dataStore

    // PandoraPreferences might be a SecurePreferences, let's just leave it out if we don't know the exact class, wait!
    // The error for Pandora was: @PandoraPreferences private val securePrefs: SecurePreferences
    // I can't provide SecurePreferences here unless I know how to construct it. But wait, SecurePreferences might already be provided somewhere else! The error was 'error.NonExistentClass', which means the ANNOTATION was missing, not the provider.
}
