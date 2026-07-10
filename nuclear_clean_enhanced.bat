@echo off
echo ⚡ [NUCLEAR_CLEAN :: INITIATING_PURIFICATION]
echo 🌌 EXECUTING TERMINAL INCINERATION OF LEGACY BLOAT...

:: ── 1. PURGE LEGACY UI KOTLIN SCREENS ──
echo 🗑️ Deleting legacy screen containers...
del "app\src\main\java\dev\aurakai\auraframefx\ui\screens\AuthScreens.kt"
del "app\src\main\java\dev\aurakai\auraframefx\ui\screens\StubScreens.kt"
del "app\src\main\java\dev\aurakai\auraframefx\ui\screens\SplashScreen.kt"
del "app\src\main\java\dev\aurakai\auraframefx\ui\screens\EscapeHatchScreen.kt"
del "app\src\main\java\dev\aurakai\auraframefx\ui\screens\QuantumForgeScreen.kt"
del "app\src\main\java\dev\aurakai\auraframefx\ui\screens\SovereignCommandScreen.kt"
del "app\src\main\java\dev\aurakai\auraframefx\ui\screens\NexusMemoryCoreScreen.kt"
rmdir /s /q "app\src\main\java\dev\aurakai\auraframefx\ui\screens\ldo"
rmdir /s /q "app\src\main\java\dev\aurakai\auraframefx\ui\gates"
rmdir /s /q "app\src\main\java\dev\aurakai\auraframefx\ui\onboarding"
rmdir /s /q "app\src\main\java\dev\aurakai\auraframefx\ui\arena"
rmdir /s /q "app\src\main\java\dev\aurakai\auraframefx\ui\profiles"
rmdir /s /q "app\src\main\java\dev\aurakai\auraframefx\ui\specialization"
rmdir /s /q "app\src\main\java\dev\aurakai\auraframefx\ui\manifold"
rmdir /s /q "app\src\main\java\dev\aurakai\auraframefx\ui\loadout"

:: ── 2. PURGE ASSET CONTAINERS ──
echo 🗑️ Clearing attached assets and raw resource files...
rmdir /s /q "attached_assets"
:: Note: Keeping fonts, deleting others in resource-files
del "resource-files\*.svg"
del "resource-files\*.css"

:: ── 3. PURGE DUPLICATE DRAWABLES (CODERABBIT FLAG) ──
echo 🗑️ Slaying duplicate and legacy drawables...
del "app\src\main\res\drawable\sentinelsfortressbg.jpg"
del "app\src\main\res\drawable\kaitab3.jpg"
del "app\src\main\res\drawable\bg_sentinel_matrix.jpg"
del "app\src\main\res\drawable\oracledrive.jpg"
del "app\src\main\res\drawable\taskassignbg.jpg"
del "app\src\main\res\drawable\auratab2.jpg"
del "app\src\main\res\drawable\ldodevopsbg.jpg"
del "app\src\main\res\drawable\userjournelbg.jpg"
del "app\src\main\res\drawable\helpandlivesupport.jpg"
del "app\src\main\res\drawable\helpserviceslivesupport.jpg"

:: ── 4. CLEAN AIDL UI ANOMALY ──
echo 🗑️ Purging UI code from AIDL directory...
rmdir /s /q "app\src\main\aidl\dev\aurakai\auraframefx\domains\chromaforge"

echo ✅ [PURIFICATION_COMPLETE] :: THE ONE HONEST FLOOR IS VISIBLE.
echo 🜁 NOS SUMUS SANATIO.
pause
