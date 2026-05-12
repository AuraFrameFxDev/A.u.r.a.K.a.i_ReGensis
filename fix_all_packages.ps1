$kotlinRoot = "C:\Users\AuraF\AuraKai\app\src\main\kotlin"
$files = Get-ChildItem -Path $kotlinRoot -Recurse -Filter *.kt

foreach ($file in $files)
{
    $fullPath = $file.FullName
    if ( $fullPath.StartsWith($kotlinRoot))
    {
        $relativePath = $fullPath.Substring($kotlinRoot.Length + 1)
        $dirName = [System.IO.Path]::GetDirectoryName($relativePath)
        if ($dirName -eq $null)
        {
            continue
        }
        $expectedPackage = $dirName -replace "\\", "."

        if ($expectedPackage -eq "")
        {
            continue
        }

        $content = [System.IO.File]::ReadAllText($fullPath)
        if ($content -match "package\s+([a-zA-Z0-9.]+)")
        {
            $actualPackage = $Matches[1]
            if ($actualPackage -ne $expectedPackage)
            {
                Write-Host "Updating $( $file.Name ): $actualPackage -> $expectedPackage"
                $content = $content -replace "package\s+$actualPackage", "package $expectedPackage"
                [System.IO.File]::WriteAllText($fullPath, $content)

                # Now update all imports of this class in other files
                $className = [System.IO.Path]::GetFileNameWithoutExtension($file.Name)
                $oldFull = "$actualPackage.$className"
                $newFull = "$expectedPackage.$className"

                $allFiles = Get-ChildItem -Path $kotlinRoot -Recurse -Filter *.kt
                foreach ($otherFile in $allFiles)
                {
                    $otherContent = [System.IO.File]::ReadAllText($otherFile.FullName)
                    if ($otherContent -match "import\s+$oldFull")
                    {
                        Write-Host "  Updating import in $( $otherFile.Name ): $oldFull -> $newFull"
                        $otherContent = $otherContent -replace "import\s+$oldFull", "import $newFull"
                        [System.IO.File]::WriteAllText($otherFile.FullName, $otherContent)
                    }
                }
            }
        }
    }
}
