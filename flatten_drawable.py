import os
import shutil

DRAWABLE_DIR = r"C:\Users\AuraF\StudioProjects\ReGenesis\app\src\main\res\drawable"

def flatten_drawable():
    if not os.path.exists(DRAWABLE_DIR):
        print("Drawable directory not found.")
        return

    subdirs = [d for d in os.listdir(DRAWABLE_DIR) if os.path.isdir(os.path.join(DRAWABLE_DIR, d))]
    
    for subdir in subdirs:
        subdir_path = os.path.join(DRAWABLE_DIR, subdir)
        files = os.listdir(subdir_path)
        
        print(f"Processing subdir: {subdir}")
        
        for filename in files:
            file_path = os.path.join(subdir_path, filename)
            if os.path.isfile(file_path):
                # Construct new filename: subdir_filename (lowercase)
                new_filename = f"{subdir.lower()}_{filename.lower()}"
                new_path = os.path.join(DRAWABLE_DIR, new_filename)
                
                # Check for collision
                if os.path.exists(new_path):
                    print(f"Warning: {new_filename} already exists. Skipping {filename}.")
                    continue
                
                try:
                    shutil.move(file_path, new_path)
                    print(f"Moved {filename} -> {new_filename}")
                except Exception as e:
                    print(f"Error moving {filename}: {e}")
        
        # Remove empty subdir
        try:
            os.rmdir(subdir_path)
            print(f"Removed directory: {subdir}")
        except OSError:
            print(f"Directory {subdir} not empty or could not be removed.")

if __name__ == "__main__":
    flatten_drawable()
