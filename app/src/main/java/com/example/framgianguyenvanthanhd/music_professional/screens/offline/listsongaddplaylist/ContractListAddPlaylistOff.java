package com.example.framgianguyenvanthanhd.music_professional.screens.offline.listsongaddplaylist;


import com.example.framgianguyenvanthanhd.music_professional.BasePresenter;
import com.example.framgianguyenvanthanhd.music_professional.BaseView;
import com.example.framgianguyenvanthanhd.music_professional.data.model.SongOffline;

import java.util.List;

/**
 * Created by MyPC on 02/02/2018.
 */

public interface ContractListAddPlaylistOff {
    /**
     * interface view list song add to album
     */
    interface ListAddAlbumView extends BaseView<ListAddAlbumPresenter> {

        void onGetSongSuccess(List<SongOffline> songOfflines);

        void onGetSongFail();

        void onInsertSongSuccessfully();

        void onNoSongSelected();
    }

    /**
     * interface presenter list song add to album
     */

    interface ListAddAlbumPresenter extends BasePresenter<ListAddAlbumView> {

        void loadSongs();

        void insertListSong(int idAlbum, List<SongOffline> songOfflines);
    }
}
