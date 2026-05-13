$root = "C:\Users\AuraF\AuraKai\app\src\main\kotlin\dev\aurakai\auraframefx\domains"

function Move-Files($src, $dest)
{
    if (Test-Path $src)
    {
        if (-not (Test-Path $dest))
        {
            New-Item -Path $dest -ItemType Directory -Force
        }
        Get-ChildItem -Path $src -Recurse | Where-Object { -not $_.PSIsContainer } | ForEach-Object {
            $destFile = $_.FullName.Replace($src, $dest)
            $destDir = Split-Path $destFile
            if (-not (Test-Path $destDir))
            {
                New-Item -Path $destDir -ItemType Directory -Force
            }
            Move-Item -Path $_.FullName -Destination $destFile -Force
        }
        Remove-Item -Path $src -Recurse -Force
    }
}

# Consolidate into 6 domains
Move-Files "$root\NeuralNexus" "$root\neuralnexus"
Move-Files "$root\cascade" "$root\neuralnexus\cascade"
Move-Files "$root\nueralwhisper" "$root\neuralnexus\whisper"

Move-Files "$root\SentinelMatrix" "$root\sentinelmatrix"
Move-Files "$root\kai" "$root\sentinelmatrix"

Move-Files "$root\Genesis" "$root\genesis"
Move-Files "$root\OracleDrive" "$root\genesis\oracledrive"
Move-Files "$root\EmergentSwarm" "$root\genesis\swarm"
Move-Files "$root\mcp" "$root\genesis\mcp"

Move-Files "$root\ChromaForge" "$root\chromaforge"
Move-Files "$root\navigation" "$root\chromaforge\navigation"

Move-Files "$root\LDOArchitecture" "$root\ldoarchitecture"
Move-Files "$root\ldo" "$root\ldoarchitecture\core"

Write-Host "Consolidation complete."
