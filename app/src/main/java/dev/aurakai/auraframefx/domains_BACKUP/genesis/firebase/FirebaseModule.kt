package dev.aurakai.auraframefx.domains.genesis.firebase

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import timber.log.Timber
import javax.inject.Singleton

/**
 * 🔥 FIREBASE CONFIGURATION MODULE
 *
 * Provides singleton instances of Firebase services configured and optimized for
 * the ReGenesis LDO ecosystem.
 */
@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return try {
            FirebaseAuth.getInstance().apply {
                Timber.d("🔐 FirebaseAuth initialized")
            }
        } catch (e: Exception) {
            Timber.e(e, "❌ FirebaseAuth initialization failed")
            throw e
        }
    }

    @Singleton
    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return try {
            FirebaseFirestore.getInstance().apply {
                Timber.d("☁️ Firestore initialized with offline persistence (100 MB cache)")
                firestoreSettings =
                    com.google.firebase.firestore.FirebaseFirestoreSettings.Builder()
                        .setPersistenceEnabled(true)
                        .setCacheSizeBytes(100L * 1024L * 1024L) // 100 MB
                        .build()
            }
        } catch (e: Exception) {
            Timber.e(e, "❌ Firestore initialization failed")
            throw e
        }
    }

    @Singleton
    @Provides
    fun provideFirebaseStorage(): FirebaseStorage {
        return try {
            FirebaseStorage.getInstance().apply {
                Timber.d("📦 Firebase Storage initialized")
            }
        } catch (e: Exception) {
            Timber.e(e, "❌ Firebase Storage initialization failed")
            throw e
        }
    }

    @Singleton
    @Provides
    fun provideFirebaseRemoteConfig(): FirebaseRemoteConfig {
        return try {
            FirebaseRemoteConfig.getInstance().apply {
                val configSettings =
                    com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings.Builder()
                        .setMinimumFetchIntervalInSeconds(3600)
                        .build()
                setConfigSettingsAsync(configSettings)
                Timber.d("⚙️ Firebase Remote Config initialized")
            }
        } catch (e: Exception) {
            Timber.e(e, "❌ Firebase Remote Config initialization failed")
            throw e
        }
    }

    @Singleton
    @Provides
    fun provideFirebaseAnalytics(@ApplicationContext context: Context): FirebaseAnalytics {
        return try {
            FirebaseAnalytics.getInstance(context).apply {
                Timber.d("📊 Firebase Analytics initialized")
            }
        } catch (e: Exception) {
            Timber.e(e, "❌ Firebase Analytics initialization failed")
            throw e
        }
    }
}
