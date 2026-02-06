import json
import re
import os

NOTEBOOK_PATH = "Fileupdates"
PROJECT_ROOT = "C:\\Users\\AuraF\\StudioProjects\\ReGenesis"
REPO_PREFIX = "ReGenesis--multi-architectural-70-LDO-/"

def main():
    try:
        with open(NOTEBOOK_PATH, 'r', encoding='utf-8') as f:
            nb = json.load(f)
    except Exception as e:
        print(f"Error reading notebook: {e}")
        return

    cells = nb.get('cells', [])
    print(f"Scanning {len(cells)} cells for file updates...")

    success_count = 0
    
    for i, cell in enumerate(cells):
        if cell.get('cell_type') != 'code':
            continue
            
        source_lines = cell.get('source', [])
        source_text = ''.join(source_lines)
        
        # 1. Find file_path
        path_match = re.search(r'file_path\s*=\s*"([^"]+)"', source_text)
        if not path_match:
            continue
            
        original_rel_path = path_match.group(1)
        
        # 2. Find the variable being written
        # Pattern: with open(file_path, "w") as f:\n    f.write(VAR_NAME)
        # Note: source_text might have different whitespace
        write_match = re.search(r'with open\(file_path,\s*"w"\)\s*as\s*f:\s*\n\s*f\.write\(([^)]+)\)', source_text)
        
        if not write_match:
            # Fallback for explicit write: open(..., "w").write(...)
             write_match = re.search(r'open\(file_path,\s*"w"\)\.write\(([^)]+)\)', source_text)

        if not write_match:
            print(f"Cell {i}: Found file_path '{original_rel_path}' but couldn't identify written variable.")
            continue
            
        content_var_name = write_match.group(1).strip()
        
        # 3. Find the content of that variable
        # Pattern: VAR_NAME = """CONTENT"""
        # We need to be careful about regex and triple quotes.
        # escaped regex for triple quotes: \"\"\"
        content_match = re.search(f'{re.escape(content_var_name)}\\s*=\\s*"""([\\s\\S]*?)"""', source_text)
        
        if not content_match:
            print(f"Cell {i}: Found file_path and write for '{content_var_name}', but couldn't find content definition.")
            continue
            
        file_content = content_match.group(1)
        
        # 4. Determine target path
        if original_rel_path.startswith(REPO_PREFIX):
            rel_path = original_rel_path[len(REPO_PREFIX):]
        else:
            rel_path = original_rel_path

        # Handle kotlin/java src mapping
        # If path contains src/main/kotlin but locally we only have src/main/java, switch it.
        # Check if src/main/kotlin exists locally
        
        local_path = os.path.join(PROJECT_ROOT, rel_path.replace('/', os.sep))
        dir_name = os.path.dirname(local_path)
        
        # If directory doesn't exist, check specifically for src/main/kotlin mismatch
        if "src\\main\\kotlin" in local_path and not os.path.exists(dir_name):
             # Try java instead
             alternative_path = local_path.replace("src\\main\\kotlin", "src\\main\\java")
             alternative_dir = os.path.dirname(alternative_path)
             if os.path.exists(alternative_dir):
                 print(f"Redirecting {local_path} -> {alternative_path}")
                 local_path = alternative_path
                 dir_name = alternative_dir
        
        # Create directory if it still doesn't exist
        if not os.path.exists(dir_name):
            try:
                os.makedirs(dir_name, exist_ok=True)
                print(f"Created directory: {dir_name}")
            except Exception as e:
                print(f"Error creating directory {dir_name}: {e}")
                continue

        # 5. Write file
        try:
            with open(local_path, 'w', encoding='utf-8') as f:
                f.write(file_content)
            print(f"Cell {i}: Successfully updated {local_path}")
            success_count += 1
        except Exception as e:
            print(f"Cell {i}: Error writing file {local_path}: {e}")

    print(f"Finished. Applied {success_count} updates.")

if __name__ == "__main__":
    main()
