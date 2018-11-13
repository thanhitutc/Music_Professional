package com.example.framgianguyenvanthanhd.music_professional.screens.offline.detailplaylistoff;


import com.example.framgianguyenvanthanhd.music_professional.data.model.SongOffline;

import java.util.List;

/**
 * Created by MyPC on 29/01/2018.
 */

public interface OnClickSongDetailPlaylistOffListener {

    void onItemClickSongDetailAlbum(List<SongOffline> songOfflines, int position);

    void onAddSongToFavorite(SongOffline songOffline);

    void onDeleteSong(SongOffline songOffline);
}
