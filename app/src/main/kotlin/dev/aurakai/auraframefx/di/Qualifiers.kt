package dev.aurakai.auraframefx.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AppStateDataStoreAnnotation

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuraSettingsDataStore

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class PandoraPreferences

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuraModel

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class KaiModel

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GenesisModel

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AnchorModel
