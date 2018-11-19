package com.example.framgianguyenvanthanhd.music_professional.screens.offline.allsong;



import com.example.framgianguyenvanthanhd.music_professional.BasePresenter;
import com.example.framgianguyenvanthanhd.music_professional.BaseView;
import com.example.framgianguyenvanthanhd.music_professional.data.model.SongOffline;

import java.util.List;

/**
 * Created by MyPC on 25/01/2018.
 */

public interface AllSongContract {

    /**
     * interface song view
     **/
    interface SongView extends BaseView<Presenter> {

        void showListSong(List<SongOffline> songOfflines);

        void showNoSong();

        void showDeleteSuccess(SongOffline songOffline);

        void showDeleteError();

        void showAddFavoriteSuccess(SongOffline songOffline);

        void showAddFavoriteError();
    }

    /**
     * Presenter all song
     **/
    interface Presenter extends BasePresenter<SongView> {

        void loadListSong();

        void addToFavorite(SongOffline songOffline);

        void deleteSong(SongOffline songOffline);
    }
}
