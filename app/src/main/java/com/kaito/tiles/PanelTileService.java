package com.kaito.tiles;

import android.media.AudioManager;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;

public class PanelTileService extends TileService {
    private AudioManager am;

    @Override
    public void onClick() {
        getQsTile().setState(Tile.STATE_INACTIVE);
        getQsTile().updateTile();
        am = (AudioManager) this.getSystemService(this.AUDIO_SERVICE);
        am.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_SAME, AudioManager.FLAG_SHOW_UI);;
    }

    @Override
    public void onTileAdded() {
        super.onTileAdded();
        getQsTile().setState(Tile.STATE_INACTIVE);
        getQsTile().updateTile();
    }
}
