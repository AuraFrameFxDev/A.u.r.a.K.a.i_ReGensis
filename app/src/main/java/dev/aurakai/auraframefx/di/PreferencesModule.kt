package dev.aurakai.auraframefx.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.aurakai.auraframefx.domains.nexus.config.UserPreferences
import javax.inject.Singleton

/**
 * Hilt Module for providing UserPreferences implementation.
 */
@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {

    @Provides
    @Singleton
    fun provideConfigUserPreferences(@ApplicationContext context: Context): UserPreferences {
        return object : UserPreferences {
            private val prefs = context.getSharedPreferences("aurakai_prefs", Context.MODE_PRIVATE)

            override suspend fun setPreference(key: String, value: String) {
                prefs.edit().putString(key, value).apply()
            }

            override suspend fun getPreference(key: String, defaultValue: String): String {
                return prefs.getString(key, defaultValue) ?: defaultValue
            }
        }
    }
}
