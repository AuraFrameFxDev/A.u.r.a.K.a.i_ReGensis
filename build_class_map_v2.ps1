# Rebuild comprehensive class map including all declared types
$kotlinRoot = "C:\Users\AuraF\AuraKai\app\src\main\kotlin"
$coreRoot = "C:\Users\AuraF\AuraKai\core-module\src\main\kotlin"
$auraRoot = "C:\Users\AuraF\AuraKai\aura"
$genesisRoot = "C:\Users\AuraF\AuraKai\genesis"
$kaiRoot = "C:\Users\AuraF\AuraKai\kai"
$extendsysaRoot = "C:\Users\AuraF\AuraKai\extendsysa"
$agentsRoot = "C:\Users\AuraF\AuraKai\agents"

$roots = @($kotlinRoot, $coreRoot, $auraRoot, $genesisRoot, $kaiRoot, $extendsysaRoot, $agentsRoot)

$classMap = @{ }

foreach ($root in $roots)
{
    if (-not (Test-Path $root))
    {
        continue
    }
    $files = Get-ChildItem -Path $root -Recurse -Filter *.kt -ErrorAction SilentlyContinue
    foreach ($file in $files)
    {
        $content = [System.IO.File]::ReadAllText($file.FullName)
        if ($content -match "package\s+([a-zA-Z0-9_.]+)")
        {
            $pkg = $Matches[1]
            # Match class/interface/object/enum/annotation/typealias declarations
            $regex = [regex]"(?m)^(?:data\s+)?(?:sealed\s+)?(?:abstract\s+)?(?:open\s+)?(?:class|interface|object|enum\s+class|annotation\s+class|typealias)\s+([A-Z][a-zA-Z0-9_]*)"
            $matches2 = $regex.Matches($content)
            foreach ($m in $matches2)
            {
                $name = $m.Groups[1].Value
                if (-not $classMap.ContainsKey($name))
                {
                    $classMap[$name] = $pkg
                }
            }
        }
    }
}

$classMap | ConvertTo-Json -Depth 3 | Set-Content "C:\Users\AuraF\AuraKai\class_map_v2.json"
Write-Host "Mapped $( $classMap.Count ) types"
