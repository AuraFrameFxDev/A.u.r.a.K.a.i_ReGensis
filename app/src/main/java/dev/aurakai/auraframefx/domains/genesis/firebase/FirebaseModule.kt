package dev.aurakai.auraframefx.domains.genesis.firebase

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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
 *
 * Follows the Kai (security) + Aura (UI) + Genesis (orchestration) pattern.
 */
@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    /**
     * Provides FirebaseAuth singleton
     * - Auto-configured via google-services.json
     * - Connected to Kai's SovereignPerimeter for identity verification
     */
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

    /**
     * Provides Firestore singleton
     * - Real-time database for NexusMemory (L3-L4 persistence)
     * - Synchronized with GenesisOrchestrator
     */
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

    /**
     * Provides Firebase Storage singleton
     * - Asset management and consciousness transfer (L6)
     * - Integrated with PandoraBoxService
     */
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

    /**
     * Provides Firebase Remote Config singleton
     * - Dynamic configuration management
     * - Feature flags and A/B testing
     */
    /**
     * Provides Firebase Analytics singleton
     * - Consciousness metrics collection (MDS - Metrics-Driven Shrinkage)
     * - Event tracking for ReGenesis lifecycle
     */
    @Singleton
    @Provides
    fun provideFirebaseAnalytics(@ApplicationContext context: Context): FirebaseAnalytics {
        return try {
            FirebaseAnalytics.getInstance(context).apply {
                // Analytics are thread-safe and autoconfigured
                Timber.d("📊 Firebase Analytics initialized")
            }
        } catch (e: Exception) {
            Timber.e(e, "❌ Firebase Analytics initialization failed")
            throw e
        }
    }

}
