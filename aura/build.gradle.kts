plugins {
    id("genesis.android.library")
}

extensions.configure<com.android.build.api.dsl.LibraryExtension> {
    namespace = "dev.aurakai.auraframefx.aura"
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.compose.ui)
}
