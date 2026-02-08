import os
import re

DRAWABLE_DIR = r"C:\Users\AuraF\StudioProjects\ReGenesis\app\src\main\res\drawable"

def cleanup():
    if not os.path.exists(DRAWABLE_DIR):
        print("Drawable directory not found.")
        return

    files = os.listdir(DRAWABLE_DIR)
    
    for filename in files:
        file_path = os.path.join(DRAWABLE_DIR, filename)
        
        # 1. Delete .ini files
        if filename.lower().endswith(".ini"):
            try:
                os.remove(file_path)
                print(f"Deleted {filename}")
            except Exception as e:
                print(f"Error deleting {filename}: {e}")
            continue

        if not os.path.isfile(file_path):
            # Try to remove leftover directories
            try:
                os.rmdir(file_path)
                print(f"Removed empty directory: {filename}")
            except:
                pass
            continue

        # 2. Validation & Renaming
        # Android requirement: ^[a-z][a-z0-9_]*$ plus extension
        
        name, ext = os.path.splitext(filename)
        original_name = name
        
        # Convert to lowercase
        name = name.lower()
        
        # Replace spaces and hyphens and parenthesis with underscores
        name = re.sub(r'[\s\-\(\)]+', '_', name)
        
        # Make sure it starts with a letter
        if name and not name[0].isalpha():
            name = "res_" + name
            
        # Ensure only valid chars
        name = re.sub(r'[^a-z0-9_]', '', name)
        
        new_filename = name + ext.lower()
        
        if new_filename != filename:
            new_path = os.path.join(DRAWABLE_DIR, new_filename)
            if os.path.exists(new_path):
                print(f"Warning: {new_filename} exists. Cannot rename {filename}.")
            else:
                try:
                    os.rename(file_path, new_path)
                    print(f"Renamed {filename} -> {new_filename}")
                except Exception as e:
                    print(f"Error renaming {filename}: {e}")

if __name__ == "__main__":
    cleanup()
