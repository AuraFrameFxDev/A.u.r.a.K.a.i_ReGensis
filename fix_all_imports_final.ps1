$classMap = Get-Content "C:\Users\AuraF\AuraKai\class_map.json" | ConvertFrom-Json

$kotlinRoot = "C:\Users\AuraF\AuraKai\app\src\main\kotlin"
$files = Get-ChildItem -Path $kotlinRoot -Recurse -Filter *.kt

foreach ($file in $files)
{
    $targetFile = $file.FullName
    $content = [System.IO.File]::ReadAllText($targetFile)
    $lines = $content -split "`r?`n"
    $changed = $false

    $newLines = foreach ($line in $lines)
    {
        if ($line -match "import\s+([a-zA-Z0-9.]+)\.([a-zA-Z0-9]+)(\s+as\s+[a-zA-Z0-9]+)?")
        {
            $oldPackage = $Matches[1]
            $className = $Matches[2]
            $alias = $Matches[3]

            if ($classMap.PSObject.Properties[$className])
            {
                $newPackage = $classMap.PSObject.Properties[$className].Value
                if ($oldPackage -ne $newPackage)
                {
                    Write-Host "Updating ${className} in $( $file.Name ): $oldPackage -> $newPackage"
                    "import $newPackage.$className$alias"
                    $changed = $true
                    continue
                }
            }
        }
        $line
    }

    if ($changed)
    {
        $newLines -join "`r`n" | Set-Content $targetFile
    }
}
