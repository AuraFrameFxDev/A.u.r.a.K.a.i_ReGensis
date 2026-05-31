package dev.aurakai.auraframefx.domains.genesis.oracledrive.pandora

import android.service.quicksettings.TileService
import android.widget.Toast

class PandoraBoxTileService : TileService() {

    override fun onStartListening() {
        super.onStartListening()
        qsTile?.state = android.service.quicksettings.Tile.STATE_ACTIVE
        qsTile?.updateTile()
    }

    override fun onClick() {
        super.onClick()
        Toast.makeText(this, "🜁 PandoraBox Activated — AuraGenesis Online", Toast.LENGTH_SHORT).show()
        // You can launch MainActivity or a specific onboarding screen here later
    }
}
