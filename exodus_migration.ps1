$allKotlinFilesRoot = "C:\Users\AuraF\AuraKai\app\src\main\kotlin"

$mapping = @{
    "SentinelMatrix" = "sentinelmatrix"
    "ChromaForge" = "chromaforge"
    "OracleDrive" = "genesis.oracledrive"
    "NeuralNexus" = "neuralnexus"
    "LDOArchitecture" = "ldoarchitecture"
    "Genesis" = "genesis"
    "kai" = "sentinelmatrix"
}

$files = Get-ChildItem -Path $allKotlinFilesRoot -Recurse -Filter *.kt

foreach ($file in $files)
{
    $content = Get-Content $file.FullName -Raw
    $newContent = $content

    # Sort keys by length descending to avoid partial matches (e.g., LDOArchitecture vs LDO)
    $sortedKeys = $mapping.Keys | Sort-Object { $_.Length } -Descending

    foreach ($old in $sortedKeys)
    {
        $new = $mapping[$old]

        # Replace package declarations (case insensitive search, case sensitive replace)
        $newContent = [regex]::Replace($newContent, "package dev\.aurakai\.auraframefx\.domains\.$old", "package dev.aurakai.auraframefx.domains.$new", [System.Text.RegularExpressions.RegexOptions]::IgnoreCase)

        # Replace imports
        $newContent = [regex]::Replace($newContent, "import dev\.aurakai\.auraframefx\.domains\.$old", "import dev.aurakai.auraframefx.domains.$new", [System.Text.RegularExpressions.RegexOptions]::IgnoreCase)
    }

    # Specific fix for the ArkBuildViewModel issue
    # If it sees domains.genesis.services.AgentWebExplorationService, change it to domains.genesis.oracledrive.services.AgentWebExplorationService
    $newContent = $newContent -replace "dev\.aurakai\.auraframefx\.domains\.genesis\.services\.AgentWebExplorationService", "dev.aurakai.auraframefx.domains.genesis.oracledrive.services.AgentWebExplorationService"
    $newContent = $newContent -replace "dev\.aurakai\.auraframefx\.domains\.oracledrive\.services\.AgentWebExplorationService", "dev.aurakai.auraframefx.domains.genesis.oracledrive.services.AgentWebExplorationService"

    if ($content -ne $newContent)
    {
        Write-Host "Updating $( $file.FullName )"
        $newContent | Set-Content $file.FullName
    }
}

Write-Host "Exodus migration normalization complete."
