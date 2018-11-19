package com.example.framgianguyenvanthanhd.music_professional.screens.offline.allsong;


import com.example.framgianguyenvanthanhd.music_professional.data.model.SongOffline;

import java.util.List;

/**
 * Created by MyPC on 25/01/2018.
 */

public interface OnClickSongListener {

    void onItemClickSong(List<SongOffline> songOfflines, int position);

    void onAddToFavorite(SongOffline songOffline);

    void onAddToAlbum(SongOffline songOffline);

    void onDeleteSong(SongOffline songOffline);
}
