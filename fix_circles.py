import re

def circle_to_path(match):
    cx = float(match.group(1))
    cy = float(match.group(2))
    r = float(match.group(3))
    rest = match.group(4)
    
    # Path: M cx, (cy-r) a r,r 0 1,1 0,2r a r,r 0 1,1 0,-2r
    path_data = f"M {cx}, {cy-r} a {r},{r} 0 1,1 0,{2*r} a {r},{r} 0 1,1 0,{-2*r}"
    return f'<path android:pathData="{path_data}"{rest}/>'

with open(r'c:\Users\AuraF\StudioProjects\ReGenesis\app\src\main\res\drawable\emblem_gemini_adk_constellation.xml', 'r') as f:
    content = f.read()

# Pattern to match <circle ... />
# Note: attributes might be in different order, but let's assume they follow cx, cy, r or similar
# The actual file has: <circle android:cx="128" android:cy="28" android:r="3" ... />
pattern = re.compile(r'<circle\s+android:cx="([\d.]+)"\s+android:cy="([\d.]+)"\s+android:r="([\d.]+)"(.*?)\s*/>')

new_content = pattern.sub(circle_to_path, content)

with open(r'c:\Users\AuraF\StudioProjects\ReGenesis\app\src\main\res\drawable\emblem_gemini_adk_constellation.xml', 'w') as f:
    f.write(new_content)
