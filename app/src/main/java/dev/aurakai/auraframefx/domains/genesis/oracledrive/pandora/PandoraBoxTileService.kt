package dev.aurakai.auraframefx.domains.genesis.oracledrive.pandora

import android.service.quicksettings.TileService
import android.widget.Toast

class PandoraBoxTileService : TileService() {

    override fun onStartListening() {
        super.onStartListening()
        // TODO: Add real tile logic later
    }

    override fun onClick() {
        super.onClick()
        // Simple placeholder for now
        Toast.makeText(this, "PandoraBox Tile Clicked - AuraGenesis Active", Toast.LENGTH_SHORT).show()
        // You can launch MainActivity or a Pandora UI here later
    }
}
