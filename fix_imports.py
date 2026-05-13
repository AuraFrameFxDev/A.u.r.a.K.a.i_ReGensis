import json
import os
import re

class_map_path = r"C:\Users\AuraF\AuraKai\class_map_v2.json"
project_dir = r"C:\Users\AuraF\AuraKai"

with open(class_map_path, 'r', encoding='utf-8') as f:
    class_map = json.load(f)

# Hardcoded some common ones that might not be detected easily or need specific paths
class_map["ui"] = "dev.aurakai.auraframefx.domains.chromaforge.ui"
class_map["screens"] = "dev.aurakai.auraframefx.domains.chromaforge.ui.screens"
class_map["LDOCatalystHubScreen"] = "dev.aurakai.auraframefx.domains.ldo.ui.screens.LDOCatalystHubScreen"
class_map["ArkBuildScreen"] = "dev.aurakai.auraframefx.domains.ldo.ui.screens.ArkBuildScreen"
class_map["MonitoringHUDsScreen"] = "dev.aurakai.auraframefx.domains.ldo.ui.screens.MonitoringHUDsScreen"
class_map["OracleDriveHubScreen"] = "dev.aurakai.auraframefx.domains.oracledrive.ui.screens.OracleDriveHubScreen"

files_to_fix = [
    "ReGenesisNavGraph.kt",
    "CovenantGuard.kt",
    "NativeLib.kt",
    "WelcomeAndCoordination.kt",
    "AnnoyanceScenes.kt",
    "MegaManBackdropRenderer.kt",
    "SoulScriptBridge.kt",
    "SoulScriptCore.kt",
    "MCPTools.kt",
    "OperationsHubScreen.kt",
    "LdoHologramSystem.kt",
    "CascadeOrchestrator.kt",
    "BaseAgent.kt",
    "CascadeTools.kt",
    "AIAgentsApi.kt",
    "AIContentApi.kt",
    "TasksApi.kt",
    "Serializer.kt",
    "ModuleDefinitions.kt",
    "OfflineDataManager.kt",
    "ColorUtils.kt",
    "ContextManager.kt",
    "SystemOverlayManager.kt"
]

def add_import(content, pkg, class_name):
    import_stmt = f"import {pkg}.{class_name}"
    # check if already imported
    if import_stmt in content or f"import {pkg}.*" in content:
        return content
    
    # insert after existing imports or package declaration
    lines = content.split('\n')
    last_import_idx = -1
    pkg_idx = -1
    for i, line in enumerate(lines):
        if line.startswith('import '):
            last_import_idx = i
        elif line.startswith('package '):
            pkg_idx = i
            
    insert_idx = last_import_idx if last_import_idx != -1 else (pkg_idx + 1 if pkg_idx != -1 else 0)
    lines.insert(insert_idx + 1, import_stmt)
    return '\n'.join(lines)

def fix_imports(file_path):
    with open(file_path, 'r', encoding='utf-8') as f:
        content = f.read()
    
    orig_content = content
    # Find Unresolved references from the gradle output and resolve them
    # For now, we will regex extract all capitalized words and see if they are in class_map
    words = set(re.findall(r'\b[A-Z][a-zA-Z0-9_]*\b', content))
    # Also add specific known packages/lowercase words that failed
    for w in ['ui', 'screens', 'network', 'models', 'agents', 'romtools', 'embodiment', 'progress', 'dispatch', 'config', 'context']:
        if re.search(r'\b' + w + r'\b', content):
            words.add(w)

    for word in words:
        if word in class_map:
            content = add_import(content, class_map[word], word)
            
    if orig_content != content:
        with open(file_path, 'w', encoding='utf-8') as f:
            f.write(content)
        print(f"Fixed {os.path.basename(file_path)}")

for root, dirs, files in os.walk(project_dir):
    for file in files:
        if file in files_to_fix:
            fix_imports(os.path.join(root, file))

print("Import fixing complete.")
