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

    // DataStoreManager is provided via @Inject constructor on the class itself.

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
}
