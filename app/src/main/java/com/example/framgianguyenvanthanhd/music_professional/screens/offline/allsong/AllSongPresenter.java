package com.example.framgianguyenvanthanhd.music_professional.screens.offline.allsong;



import com.example.framgianguyenvanthanhd.music_professional.data.model.SongOffline;
import com.example.framgianguyenvanthanhd.music_professional.data.repository.FavoriteRepository;
import com.example.framgianguyenvanthanhd.music_professional.data.repository.SongRepository;

import java.util.List;

/**
 * Created by MyPC on 25/01/2018.
 */

public class AllSongPresenter implements AllSongContract.Presenter {
    private AllSongContract.SongView mSongView;
    private SongRepository mSongRepository;
    private FavoriteRepository mFavoriteRepository;

    public AllSongPresenter(SongRepository songRepository, FavoriteRepository favoriteRepository) {
        mSongRepository = songRepository;
        mFavoriteRepository = favoriteRepository;
    }

    @Override
    public void setView(AllSongContract.SongView view) {
        mSongView = view;
    }

    @Override
    public void onStart() {
        mSongView.setPresenter(this);
    }

    @Override
    public void onStop() {

    }

    @Override
    public void loadListSong() {
        List<SongOffline> songOfflines = mSongRepository.getSong();
        if (songOfflines == null) {
            mSongView.showNoSong();
        } else {
            mSongView.showListSong(songOfflines);
        }
    }

    @Override
    public void addToFavorite(SongOffline songOffline) {
        boolean isSuccessful = mFavoriteRepository.insertSongToFavorite(songOffline.getId());
        if (isSuccessful) {
            mSongView.showAddFavoriteSuccess(songOffline);
        } else {
            mSongView.showAddFavoriteError();
        }
    }

    @Override
    public void deleteSong(SongOffline songOffline) {
        boolean isSuccessful = mSongRepository.deleteSong(songOffline);
        if (isSuccessful) {
            mSongView.showDeleteSuccess(songOffline);
        } else {
            mSongView.showDeleteError();
        }
    }

}
