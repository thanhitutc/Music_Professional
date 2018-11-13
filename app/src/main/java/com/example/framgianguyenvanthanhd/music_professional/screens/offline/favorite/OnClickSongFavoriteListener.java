package com.example.framgianguyenvanthanhd.music_professional.screens.offline.favorite;

import com.example.framgianguyenvanthanhd.music_professional.data.model.SongOffline;

import java.util.List;

/**
 * Created by MyPC on 28/01/2018.
 */

public interface OnClickSongFavoriteListener {

    void onItemClickFavoriteSong(List<SongOffline> songOfflines, int position);

    void onDeleteFavoriteSong(SongOffline songOffline);
}
