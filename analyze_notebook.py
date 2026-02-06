import json
import re

def analyze_notebook(notebook_path):
    try:
        with open(notebook_path, 'r', encoding='utf-8') as f:
            nb = json.load(f)
    except Exception as e:
        print(f"Error reading notebook: {e}")
        return

    cells = nb.get('cells', [])
    print(f"Notebook has {len(cells)} cells.")

    potential_file_updates = []

    for i, cell in enumerate(cells):
        cell_type = cell.get('cell_type')
        source_lines = cell.get('source', [])
        source_text = ''.join(source_lines)

        if cell_type == 'markdown':
            # Look for filenames in markdown
            # e.g., "Updating `app/src/main/java/Foo.kt`" or "### File: ... "
            # Common patterns: `path/to/file`
            matches = re.findall(r'`([^`]+\.kt|[^`]+\.java|[^`]+\.xml|[^`]+\.gradle\.kts)`', source_text)
            if matches:
                 print(f"Cell {i} (MD) mentions: {matches}")
                 # Check if next cell is code
                 if i + 1 < len(cells) and cells[i+1].get('cell_type') == 'code':
                     print(f"  -> Potential update for {matches[-1]} in Cell {i+1}")
                     potential_file_updates.append((matches[-1], i+1))
        
        elif cell_type == 'code':
             # Maybe check for magics like %%writefile
             if source_text.strip().startswith('%%writefile'):
                 path = source_text.strip().split()[1]
                 print(f"Cell {i} (Code) has %%writefile {path}")
                 potential_file_updates.append((path, i))

if __name__ == "__main__":
    analyze_notebook("Fileupdates")
