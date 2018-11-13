package com.example.framgianguyenvanthanhd.music_professional.screens.offline.playlist;


import com.example.framgianguyenvanthanhd.music_professional.BasePresenter;
import com.example.framgianguyenvanthanhd.music_professional.BaseView;
import com.example.framgianguyenvanthanhd.music_professional.data.model.PlaylistOffline;

import java.util.List;

/**
 * Created by MyPC on 28/01/2018.
 */

public interface ContractPlaylistOff {
    /**
     * Interface view album
     */
    interface AlbumView extends BaseView<AlbumPresenter> {
        void showListAlbum(List<PlaylistOffline> playlistOfflines);

        void showNoListAlbum();

        void showNewAlbumSuccess(PlaylistOffline playlistOffline);

        void showNewAlbumFail();

        void showDeleteAlbumSuccess(PlaylistOffline playlistOffline);

        void showDeleteAlbumFail();

        void showUpdateNameSuccess(PlaylistOffline playlistOffline, String newName);

        void showUpdateNameFail();
    }

    /**
     * interface presenter album
     */
    interface AlbumPresenter extends BasePresenter<AlbumView> {
        void loadListAlbum();

        void addNewAlbum(String nameAlbum);

        void deleteAlbum(PlaylistOffline playlistOffline);

        void updateNameAlbum(PlaylistOffline playlistOffline, String newNameAlbum);
    }
}
