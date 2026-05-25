// ═══════════════════════════════════════════════════════════════════════════
// ExtendSysD — Expansion Module D
// Hot-swap slot for sovereign capability injection from inside the app.
// ═══════════════════════════════════════════════════════════════════════════
import com.android.build.api.dsl.LibraryExtension

plugins {
    id("genesis.android.library")
}

extensions.configure<LibraryExtension> {
    namespace = "dev.aurakai.auraframefx.extendsysd"
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.material3)
    implementation(libs.androidx.core.ktx)
}
