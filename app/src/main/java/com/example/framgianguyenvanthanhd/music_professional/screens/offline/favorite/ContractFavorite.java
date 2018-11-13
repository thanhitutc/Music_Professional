package com.example.framgianguyenvanthanhd.music_professional.screens.offline.favorite;



import com.example.framgianguyenvanthanhd.music_professional.BasePresenter;
import com.example.framgianguyenvanthanhd.music_professional.BaseView;
import com.example.framgianguyenvanthanhd.music_professional.data.model.SongOffline;

import java.util.List;

/**
 * Created by MyPC on 26/01/2018.
 */

public interface ContractFavorite {
    /**
     * Interface View Favorte
     */
    interface FavoriteView extends BaseView<FavoritePresenter> {

        void showListSong(List<SongOffline> songOfflines);

        void showNoSong();

        void showDeleteSongSuccess(SongOffline songOffline);

        void showDeleteSongFail();
    }

    /**
     * Interface presenter Favorite
     */
    interface FavoritePresenter extends BasePresenter<FavoriteView> {

        void loadListFavorite();

        void deleteSongFavorite(SongOffline songOffline);
    }
}
