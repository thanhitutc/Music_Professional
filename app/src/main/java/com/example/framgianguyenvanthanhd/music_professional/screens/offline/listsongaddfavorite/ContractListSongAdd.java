package com.example.framgianguyenvanthanhd.music_professional.screens.offline.listsongaddfavorite;



import com.example.framgianguyenvanthanhd.music_professional.BasePresenter;
import com.example.framgianguyenvanthanhd.music_professional.BaseView;
import com.example.framgianguyenvanthanhd.music_professional.data.model.SongOffline;

import java.util.List;

/**
 * Created by MyPC on 01/02/2018.
 */

public interface ContractListSongAdd {

    /**
     * Interface view list song add to favorite
     */
    interface ListSongAddView extends BaseView<ListSongAddPresenter> {
        void showListSongAdd(List<SongOffline> songOfflines);

        void showNoListSongAdd();

        void showAddSuccess();

        void showNoSongAdd();
    }

    /**
     * Interface presenter list song add to favorite
     */

    interface ListSongAddPresenter extends BasePresenter<ListSongAddView> {
        void loadListSongAdd();

        void addSongToFavorite(List<SongOffline> songOfflines);
    }
}
