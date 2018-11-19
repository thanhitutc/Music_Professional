package com.example.framgianguyenvanthanhd.music_professional.screens.offline.listsongaddfavorite;



import com.example.framgianguyenvanthanhd.music_professional.data.datasource.FavoriteDataSource;
import com.example.framgianguyenvanthanhd.music_professional.data.model.SongOffline;
import com.example.framgianguyenvanthanhd.music_professional.data.repository.FavoriteRepository;
import com.example.framgianguyenvanthanhd.music_professional.data.resources.local.FavoriteType;

import java.util.List;

/**
 * Created by MyPC on 01/02/2018.
 */

public class ListSongAddPresenter implements ContractListSongAdd.ListSongAddPresenter {
    private ContractListSongAdd.ListSongAddView mView;
    private FavoriteRepository mFavoriteRepository;

    public ListSongAddPresenter(FavoriteRepository favoriteRepository) {
        mFavoriteRepository = favoriteRepository;
    }

    @Override
    public void setView(ContractListSongAdd.ListSongAddView view) {
        mView = view;
    }

    @Override
    public void onStart() {
        mView.setPresenter(this);
    }

    @Override
    public void onStop() {

    }

    @Override
    public void loadListSongAdd() {
        List<SongOffline> songOfflines = mFavoriteRepository.getSongFavorite(FavoriteType.NOT_IN_TABLE_FAVORITE);
        if (songOfflines != null) {
            mView.showListSongAdd(songOfflines);
        } else {
            mView.showNoListSongAdd();
        }
    }

    @Override
    public void addSongToFavorite(List<SongOffline> songOfflines) {
        mFavoriteRepository.insertListSongToFavorite(songOfflines,
                new FavoriteDataSource.CallBackInsertFavorite() {
                    @Override
                    public void onComplete() {
                        mView.showAddSuccess();
                    }

                    @Override
                    public void onFail(List<String> songFail) {

                    }

                    @Override
                    public void onInsertNoSong() {
                        mView.showNoSongAdd();
                    }
                });
    }
}
