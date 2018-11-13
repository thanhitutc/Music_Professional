package com.example.framgianguyenvanthanhd.music_professional.screens.offline.playlist;


import com.example.framgianguyenvanthanhd.music_professional.data.model.PlaylistOffline;

/**
 * Created by MyPC on 28/01/2018.
 */

public interface OnClickPlaylistOffListener {
    void onItemClickAlbum(PlaylistOffline playlistOffline);

    void onClickDeleteAlbum(PlaylistOffline playlistOffline);

    void onUpdateNameAlbum(PlaylistOffline playlistOffline);
}
