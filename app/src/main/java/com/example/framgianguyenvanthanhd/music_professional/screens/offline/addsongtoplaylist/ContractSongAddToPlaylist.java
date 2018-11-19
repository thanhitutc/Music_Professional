package com.example.framgianguyenvanthanhd.music_professional.screens.offline.addsongtoplaylist;



import com.example.framgianguyenvanthanhd.music_professional.BasePresenter;
import com.example.framgianguyenvanthanhd.music_professional.BaseView;
import com.example.framgianguyenvanthanhd.music_professional.data.model.PlaylistOffline;

import java.util.List;

/**
 * Created by MyPC on 26/01/2018.
 */

public interface ContractSongAddToPlaylist {
    /**
     * Interface song add to album View
     */
    interface SongAddToAlbumView extends BaseView<SongAddToAlbumPresenter> {

        void showListAlbum(List<PlaylistOffline> playlistOfflines);

        void showNoListAlbum();

        void showAddSongSuccess();

        void showAddSongFail();
    }

    /**
     * Interface presenter song add to album presenter
     */
    interface SongAddToAlbumPresenter extends BasePresenter<SongAddToAlbumView> {

        void loadListSong();

        void addNewAlbum(String nameAlbum);

        void addSongToAlbum(int idAlbum);
    }
}
