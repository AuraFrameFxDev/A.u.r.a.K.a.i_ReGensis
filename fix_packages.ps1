$root = "C:\Users\AuraF\AuraKai\app\src\main\kotlin\dev\aurakai\auraframefx\domains\SentinelMatrix"
$files = Get-ChildItem -Path $root -Recurse -Filter *.kt

foreach ($file in $files)
{
    $content = Get-Content $file.FullName -Raw
    $newContent = $content -replace "package dev\.aurakai\.auraframefx\.domains\.kai", "package dev.aurakai.auraframefx.domains.sentinelmatrix"
    $newContent = $newContent -replace "import dev\.aurakai\.auraframefx\.domains\.kai", "import dev.aurakai.auraframefx.domains.sentinelmatrix"

    if ($content -ne $newContent)
    {
        Write-Host "Fixing $( $file.FullName )"
        $newContent | Set-Content $file.FullName
    }
}
