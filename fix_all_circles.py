import re
import os

def circle_to_path(match):
    tag_content = match.group(1)
    
    # Extract attributes using regex
    cx_match = re.search(r'android:cx="([\d.]+)"', tag_content)
    cy_match = re.search(r'android:cy="([\d.]+)"', tag_content)
    r_match = re.search(r'android:r="([\d.]+)"', tag_content)
    
    if not (cx_match and cy_match and r_match):
        return match.group(0) # Return original if not all found
        
    cx = float(cx_match.group(1))
    cy = float(cy_match.group(1))
    r = float(r_match.group(1))
    
    # Remove cx, cy, r from the tag content to keep other attributes
    new_tag_content = tag_content
    new_tag_content = re.sub(r'android:cx="[\d.]+"', '', new_tag_content)
    new_tag_content = re.sub(r'android:cy="[\d.]+"', '', new_tag_content)
    new_tag_content = re.sub(r'android:r="[\d.]+"', '', new_tag_content)
    
    # Clean up whitespace
    new_tag_content = ' '.join(new_tag_content.split())
    if new_tag_content:
        new_tag_content = ' ' + new_tag_content
    
    # Path: M cx, (cy-r) a r,r 0 1,1 0,2r a r,r 0 1,1 0,-2r
    # Using the more standard M cx, cy m -r, 0 a r,r 0 1,1 2r,0 a r,r 0 1,1 -2r,0
    path_data = f"M {cx}, {cy-r} a {r},{r} 0 1,1 0,{2*r} a {r},{r} 0 1,1 0,{-2*r}"
    
    return f'<path android:pathData="{path_data}"{new_tag_content}/>'

directory = r'c:\Users\AuraF\StudioProjects\ReGenesis\app\src\main\res\drawable'
for filename in os.listdir(directory):
    if filename.endswith('.xml'):
        filepath = os.path.join(directory, filename)
        with open(filepath, 'r') as f:
            content = f.read()
        
        if '<circle' in content:
            print(f"Fixing {filename}...")
            # Pattern to match <circle ... /> even across multiple lines
            pattern = re.compile(r'<circle\s+(.*?)\s*/>', re.DOTALL)
            new_content = pattern.sub(circle_to_path, content)
            
            with open(filepath, 'w') as f:
                f.write(new_content)
