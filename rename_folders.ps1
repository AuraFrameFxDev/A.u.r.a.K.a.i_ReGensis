$root = "C:\Users\AuraF\AuraKai\app\src\main\kotlin\dev\aurakai\auraframefx\domains"
$dirs = Get-ChildItem -Path $root -Directory

foreach ($dir in $dirs)
{
    $lowerName = $dir.Name.ToLower()
    if ($dir.Name -ne $lowerName)
    {
        $tempName = $dir.Name + "_TEMP"
        Rename-Item -Path $dir.FullName -NewName $tempName
        Rename-Item -Path ($root + "\" + $tempName) -NewName $lowerName
        Write-Host "Renamed $( $dir.Name ) to $lowerName"
    }
}
