package com.example.framgianguyenvanthanhd.music_professional.screens.offline.favorite;



import com.example.framgianguyenvanthanhd.music_professional.data.model.SongOffline;
import com.example.framgianguyenvanthanhd.music_professional.data.repository.FavoriteRepository;
import com.example.framgianguyenvanthanhd.music_professional.data.resources.local.FavoriteType;

import java.util.List;

/**
 * Created by MyPC on 26/01/2018.
 */

public class FavoritePresenter implements ContractFavorite.FavoritePresenter {
    private ContractFavorite.FavoriteView mFavoriteView;
    private FavoriteRepository mFavoriteRepository;

    public FavoritePresenter(FavoriteRepository favoriteRepository) {
        mFavoriteRepository = favoriteRepository;
    }

    @Override
    public void setView(ContractFavorite.FavoriteView view) {
        mFavoriteView = view;
    }

    @Override
    public void onStart() {
        mFavoriteView.setPresenter(this);
    }

    @Override
    public void onStop() {

    }

    @Override
    public void loadListFavorite() {
        List<SongOffline> songOfflines = mFavoriteRepository.getSongFavorite(FavoriteType.IN_TABLE_FAVORITE);
        if (songOfflines != null) {
            mFavoriteView.showListSong(songOfflines);
        } else {
            mFavoriteView.showNoSong();
        }
    }

    @Override
    public void deleteSongFavorite(SongOffline songOffline) {
        boolean isSuccessfully = mFavoriteRepository.deleteFavorite(songOffline.getId());
        if (isSuccessfully) {
            mFavoriteView.showDeleteSongSuccess(songOffline);
        } else {
            mFavoriteView.showDeleteSongFail();
        }
    }
}
