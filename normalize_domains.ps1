$domainsRoot = "C:\Users\AuraF\AuraKai\app\src\main\kotlin\dev\aurakai\auraframefx\domains"
$allKotlinFilesRoot = "C:\Users\AuraF\AuraKai\app\src\main\kotlin"

$mapping = @{
    "SentinelMatrix" = "sentinelmatrix"
    "ChromaForge" = "chromaforge"
    "OracleDrive" = "oracledrive"
    "NeuralNexus" = "neuralnexus"
    "LDOArchitecture" = "ldoarchitecture"
    "Genesis" = "genesis"
}

# 1. Update packages and imports in all files
$files = Get-ChildItem -Path $allKotlinFilesRoot -Recurse -Filter *.kt

foreach ($file in $files)
{
    $content = Get-Content $file.FullName -Raw
    $newContent = $content

    foreach ($old in $mapping.Keys)
    {
        $new = $mapping[$old]

        # Replace package declarations
        $newContent = $newContent -replace "package dev\.aurakai\.auraframefx\.domains\.$old", "package dev.aurakai.auraframefx.domains.$new"

        # Replace imports
        $newContent = $newContent -replace "import dev\.aurakai\.auraframefx\.domains\.$old", "import dev.aurakai.auraframefx.domains.$new"

        # Handle cases where someone might have used lowercase manually but inconsistently
        # e.g. package dev.aurakai.auraframefx.domains.kai
        if ($old -eq "SentinelMatrix")
        {
            $newContent = $newContent -replace "package dev\.aurakai\.auraframefx\.domains\.kai", "package dev.aurakai.auraframefx.domains.sentinelmatrix"
            $newContent = $newContent -replace "import dev\.aurakai\.auraframefx\.domains\.kai", "import dev.aurakai.auraframefx.domains.sentinelmatrix"
        }
    }

    if ($content -ne $newContent)
    {
        Write-Host "Updating $( $file.FullName )"
        $newContent | Set-Content $file.FullName
    }
}

Write-Host "Normalization complete."
