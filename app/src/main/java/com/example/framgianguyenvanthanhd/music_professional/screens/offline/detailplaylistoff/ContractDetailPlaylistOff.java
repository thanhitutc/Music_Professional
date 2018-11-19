package com.example.framgianguyenvanthanhd.music_professional.screens.offline.detailplaylistoff;


import com.example.framgianguyenvanthanhd.music_professional.BasePresenter;
import com.example.framgianguyenvanthanhd.music_professional.BaseView;
import com.example.framgianguyenvanthanhd.music_professional.data.model.SongOffline;

import java.util.List;

/**
 * Created by MyPC on 29/01/2018.
 */

public interface ContractDetailPlaylistOff {
    /**
     * Interface View DetailAlbum
     */
    interface DetailAlbumView extends BaseView<DetailAlbumPresenter> {

        void showLoadListDetailSong(List<SongOffline> songOfflines);

        void showLoadNoSong();

        void showDeleteSongSuccess(SongOffline songOffline);

        void showDeleteSongFail();

        void showAddSongFavoriteSuccess();

        void showAddSongFavoriteFail();
    }

    /**
     * Interface Presenter DetailAlbum
     */
    interface DetailAlbumPresenter extends BasePresenter<DetailAlbumView> {

        void loadListSongDetail(int idAlbum);

        void deleteSongOfAlbum(SongOffline songOffline);

        void addSongToFavorite(SongOffline songOffline);
    }
}
