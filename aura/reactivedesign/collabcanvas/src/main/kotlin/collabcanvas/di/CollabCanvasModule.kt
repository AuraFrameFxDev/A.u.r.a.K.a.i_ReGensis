package collabcanvas.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CollabCanvasModule {

    @Provides
    @Singleton
    @Named("BasicOkHttpClient")
    fun provideBasicOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    @CollabCanvasUrl
    fun provideCollabCanvasUrl(): String {
        return "ws://10.0.2.2:8080/canvas" // Default for emulator to localhost
    }
}
