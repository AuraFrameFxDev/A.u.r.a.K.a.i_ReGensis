# Fix all drawable resource names for Android compatibility
# Moves files from subdirectories, converts to lowercase, replaces spaces

$drawableDir = "app\src\main\res\drawable"
$gateScenesDir = "$drawableDir\Gatescenes"

Write-Host "FIXING 183 DRAWABLE RESOURCES..." -ForegroundColor Cyan

# Function to convert filename to Android-compatible format
function Convert-ToAndroidName {
    param(
        [string]$originalName,
        [string]$subdirectory
    )
    
    # Remove file extension
    $nameWithoutExt = [System.IO.Path]::GetFileNameWithoutExtension($originalName)
    $extension = [System.IO.Path]::GetExtension($originalName)
    
    # Convert subdirectory to prefix
    $prefix = $subdirectory.ToLower() -replace '[^a-z0-9]', '_'
    
    # Convert filename
    $cleanName = $nameWithoutExt.ToLower() `
        -replace '\s+', '_' `
        -replace '[^a-z0-9_]', '' `
        -replace '__+', '_'
    
    # Build final name
    "gatescenes_${prefix}_${cleanName}${extension}"
}

# Get all files recursively from Gatescenes subdirectories
$filesToMove = Get-ChildItem -Path $gateScenesDir -Recurse -File

$count = 0
foreach ($file in $filesToMove) {
    # Get relative subdirectory name
    $relativePath = $file.DirectoryName.Replace($gateScenesDir, "").TrimStart('\')
    $subdirName = $relativePath -replace '\\', '_'
    
    # Generate new name
    $newName = Convert-ToAndroidName -originalName $file.Name -subdirectory $subdirName
    
    # Target path (flat drawable directory)
    $targetPath = Join-Path $drawableDir $newName
    
    # Check if already exists
    if (Test-Path $targetPath) {
        Write-Host "SKIP (exists): $newName" -ForegroundColor Yellow
    } else {
        # Move and rename
        Move-Item -Path $file.FullName -Destination $targetPath -Force
        Write-Host "MOVED: $($file.Name) -> $newName" -ForegroundColor Green
        $count++
    }
}

Write-Host ""
Write-Host "SUMMARY:" -ForegroundColor Cyan
Write-Host "  Files moved: $count" -ForegroundColor Green

# Remove empty subdirectories
Write-Host ""
Write-Host "CLEANING UP EMPTY DIRECTORIES..." -ForegroundColor Cyan
Get-ChildItem -Path $gateScenesDir -Recurse -Directory | 
    Where-Object { (Get-ChildItem $_.FullName).Count -eq 0 } | 
    ForEach-Object {
        Remove-Item $_.FullName -Force
        Write-Host "  Removed: $($_.Name)" -ForegroundColor Gray
    }

# Try to remove Gatescenes directory if empty
if ((Get-ChildItem $gateScenesDir -Recurse).Count -eq 0) {
    Remove-Item $gateScenesDir -Force
    Write-Host "Removed Gatescenes directory" -ForegroundColor Green
}

Write-Host ""
Write-Host "DONE! All drawables are now Android-compatible!" -ForegroundColor Green
Write-Host "Next: Update GateAssetConfig.kt with new names" -ForegroundColor Yellow
