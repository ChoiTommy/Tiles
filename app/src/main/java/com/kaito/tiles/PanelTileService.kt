package com.kaito.tiles

import android.media.AudioManager
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService

class PanelTileService : TileService() {

    override fun onClick() {
        qsTile.state = Tile.STATE_INACTIVE
        qsTile.updateTile()
        val am = this.getSystemService(AUDIO_SERVICE) as AudioManager
        am.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_SAME, AudioManager.FLAG_SHOW_UI)
    }

    override fun onTileAdded() {
        super.onTileAdded()
        qsTile.state = Tile.STATE_INACTIVE
        qsTile.updateTile()
    }
}
