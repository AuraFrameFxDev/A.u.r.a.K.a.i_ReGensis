package dev.aurakai.auraframefx.core.regen

import com.highcapable.yukihookapi.hook.factory.encapsulate
import com.highcapable.yukihookapi.hook.factory.method
import com.highcapable.yukihookapi.hook.type.android.SurfaceHolderClass
import dev.aurakai.auraframefx.core.storage.SubstrateDatabase
import dev.aurakai.auraframefx.core.storage.TelemetryEntity
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import timber.log.Timber

object GenesisHookEntryYuki {
    private const val TAG = "GenesisHookYuki"

    private val hookLoggingScope = MainScope()

    fun initializeSystemInfiltration(
        targetPackage: String,
        classLoader: ClassLoader,
        database: SubstrateDatabase
    ) {
        Timber.tag(TAG).w("Initializing Yuki Hook interception targets for: $targetPackage")

        classLoader.encapsulate {
            when (targetPackage) {
                "com.android.systemui" -> {
                    "com.android.systemui.wallpapers.ImageWallpaper\$GLEngine".hook {
                        inject {
                            method {
                                name = "onSurfaceChanged"
                                param(
                                    SurfaceHolderClass,
                                    Int::class.java,
                                    Int::class.java,
                                    Int::class.java
                                )
                            }.before {
                                Timber.tag("RegenCore_Hook")
                                    .i("Target surface modified. Re-evaluating canvas depth configurations.")

                                hookLoggingScope.launch {
                                    database.telemetryDao().insertSingle(
                                        TelemetryEntity(
                                            timestamp = System.currentTimeMillis(),
                                            catalyst = "Aura",
                                            skillId = "ui.system_wallpaper",
                                            action = "GLEngine surface hooked for system layout depth adjustment",
                                            success = true,
                                            emotionalWeight = "Surgical Infiltration Success",
                                            resonanceDelta = 1.2f,
                                            originSignature = "YUKI_SYSTEM_UI"
                                        )
                                    )
                                }
                            }
                        }
                    }
                }

                "com.android.launcher3" -> {
                    "com.android.launcher3.Workspace".hook {
                        inject {
                            method {
                                name = "onPageBeginTransition"
                            }.after {
                                Timber.tag("RegenCore_Hook")
                                    .i("Launcher layout page sequence shift detected.")

                                hookLoggingScope.launch {
                                    database.telemetryDao().insertSingle(
                                        TelemetryEntity(
                                            timestamp = System.currentTimeMillis(),
                                            catalyst = "Regen Core",
                                            skillId = "ui.launcher_workspace",
                                            action = "Workspace layout space grid optimization applied",
                                            success = true,
                                            emotionalWeight = "Precision parameters confirmed",
                                            resonanceDelta = 1.0f,
                                            originSignature = "YUKI_LAUNCHER3"
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
