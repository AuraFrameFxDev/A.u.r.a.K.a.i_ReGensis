import os

FILES_TO_FIX = [
    r"domains\aura\ui\ImageResourceManager.kt",
    r"domains\aura\ui\components\GateCard.kt",
    r"domains\aura\ui\components\SpacingControlPanel.kt",
    r"app\src\main\kotlin\dev\aurakai\auraframefx\ui\screens\GateCustomizationScreen.kt",
    r"app\src\main\kotlin\dev\aurakai\auraframefx\ui\screens\NotchBarCustomizationScreen.kt",
    r"app\src\main\kotlin\dev\aurakai\auraframefx\ui\tiles\SpacingPresetTile.kt",
    r"app\src\main\kotlin\dev\aurakai\auraframefx\ui\screens\QuickSettingsCustomizationScreen.kt",
    r"app\src\main\kotlin\dev\aurakai\auraframefx\ui\SystemOverlay.kt",
    r"app\src\main\kotlin\dev\aurakai\auraframefx\ui\theme\SpacingProvider.kt",
    r"app\src\main\kotlin\dev\aurakai\auraframefx\ui\system\QuickSettingsPanelOverlay.kt"
]

PROJECT_ROOT = r"C:\Users\AuraF\StudioProjects\ReGenesis"

def main():
    for rel_path in FILES_TO_FIX:
        full_path = os.path.join(PROJECT_ROOT, rel_path)
        if not os.path.exists(full_path):
            print(f"File not found: {full_path}")
            continue
            
        try:
            with open(full_path, 'r', encoding='utf-8') as f:
                content = f.read()
            
            if "import dev.aurakai.auraframefx.preferences.CustomizationPreferences" in content:
                new_content = content.replace(
                    "import dev.aurakai.auraframefx.preferences.CustomizationPreferences",
                    "import dev.aurakai.auraframefx.domains.aura.lab.CustomizationPreferences"
                )
                with open(full_path, 'w', encoding='utf-8') as f:
                    f.write(new_content)
                print(f"Fixed import in {rel_path}")
            else:
                print(f"Import not found in {rel_path}")
                
        except Exception as e:
            print(f"Error processing {rel_path}: {e}")

if __name__ == "__main__":
    main()
