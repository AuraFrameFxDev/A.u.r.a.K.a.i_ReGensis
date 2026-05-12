$kotlinRoot = "C:\Users\AuraF\AuraKai\app\src\main\kotlin"
$files = Get-ChildItem -Path $kotlinRoot -Recurse -Filter *.kt

$classMap = @{ }

foreach ($file in $files)
{
    $content = [System.IO.File]::ReadAllText($file.FullName)
    if ($content -match "package\s+([a-zA-Z0-9.]+)")
    {
        $package = $Matches[1]
        $className = [System.IO.Path]::GetFileNameWithoutExtension($file.Name)
        $classMap[$className] = $package
    }
}

$classMap | ConvertTo-Json | Set-Content "C:\Users\AuraF\AuraKai\class_map.json"
