import json
import os
import sys

NOTEBOOK_PATH = "Fileupdates"
REPO_PREFIX = "ReGenesis--multi-architectural-70-LDO-/"

def patch_source(source_code):
    # Replace the repo prefix with ./ (current directory)
    # The source often sets file_path = "ReGenesis.../..."
    # We want file_path = "..." (relative)
    
    # Simple string replacement might be dangerous if it matches content.
    # But usually the path is in quotes.
    
    patched = source_code.replace(REPO_PREFIX, "")
    
    # Also handle the directory creation checks. 
    # If the code checks if "ReGenesis..." exists, it should now check if local path exists.
    # The string replacement above handles this.
    
    return patched

def main():
    try:
        with open(NOTEBOOK_PATH, 'r', encoding='utf-8') as f:
            nb = json.load(f)
    except Exception as e:
        print(f"Error reading notebook: {e}")
        return

    cells = nb.get('cells', [])
    print(f"Executing updates from {len(cells)} cells...")

    # Shared context for execution
    context = {}
    
    # We want to skip cells that are just listing files (like cells 0-100 or so?)
    # The updates started around cell 130.
    # However, some imports might be earlier?
    # Usually imports are in each cell in this notebook style.
    
    for i, cell in enumerate(cells):
        if cell.get('cell_type') != 'code':
            continue
            
        source_lines = cell.get('source', [])
        source_text = ''.join(source_lines)
        
        # Heuristic: only execute cells that look like they write files.
        # They usually contain "open(..., 'w')" or "file_path ="
        if "file_path =" not in source_text and "open(" not in source_text:
             continue
        
        # Also skip the large file listing cells which might accidentally trigger something 
        # or just fail/waste time.
        # File listing cells usually don't have "open(..., 'w')".
        if "open(" in source_text and ("'w'" in source_text or '"w"' in source_text or "'r'" in source_text):
             # This is likely a file operation (read/modify/write)
             pass
        else:
            continue

        patched_source = patch_source(source_text)
        
        print(f"Executing Cell {i}...")
        try:
            exec(patched_source, context)
        except Exception as e:
            print(f"Error executing Cell {i}: {e}")
            # Don't stop, some might fail but others might succeed.

if __name__ == "__main__":
    main()
