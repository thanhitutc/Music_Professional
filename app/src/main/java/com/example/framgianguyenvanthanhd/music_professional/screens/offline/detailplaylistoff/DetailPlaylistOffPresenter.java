package com.example.framgianguyenvanthanhd.music_professional.screens.offline.detailplaylistoff;

import com.example.framgianguyenvanthanhd.music_professional.data.model.SongOffline;
import com.example.framgianguyenvanthanhd.music_professional.data.repository.FavoriteRepository;
import com.example.framgianguyenvanthanhd.music_professional.data.repository.SongInPlaylistRepository;

import java.util.List;

/**
 * Created by MyPC on 29/01/2018.
 */

public class DetailPlaylistOffPresenter implements ContractDetailPlaylistOff.DetailAlbumPresenter {
    private SongInPlaylistRepository mSongInPlaylistRepository;
    private FavoriteRepository mFavoriteRepository;
    private ContractDetailPlaylistOff.DetailAlbumView mDetailAlbumView;
    private int mIdAlbum;

    public DetailPlaylistOffPresenter(SongInPlaylistRepository songInPlaylistRepository,
                                      FavoriteRepository favoriteRepository) {
        mSongInPlaylistRepository = songInPlaylistRepository;
        mFavoriteRepository = favoriteRepository;
    }

    @Override
    public void setView(ContractDetailPlaylistOff.DetailAlbumView view) {
        mDetailAlbumView = view;
    }

    @Override
    public void onStart() {
        mDetailAlbumView.setPresenter(this);
    }

    @Override
    public void onStop() {

    }

    @Override
    public void loadListSongDetail(int idAlbum) {
        mIdAlbum = idAlbum;
        List<SongOffline> songOfflines = mSongInPlaylistRepository.getSongOfAlbum(idAlbum);
        if (songOfflines != null) {
            mDetailAlbumView.showLoadListDetailSong(songOfflines);
        } else {
            mDetailAlbumView.showLoadNoSong();
        }
    }

    @Override
    public void deleteSongOfAlbum(SongOffline songOffline) {
        boolean isSuccess = mSongInPlaylistRepository.deleteSongInAlbum(songOffline.getId(), mIdAlbum);
        if (isSuccess) {
            mDetailAlbumView.showDeleteSongSuccess(songOffline);
        } else {
            mDetailAlbumView.showDeleteSongFail();
        }
    }

    @Override
    public void addSongToFavorite(SongOffline songOffline) {
        boolean isSuccess = mFavoriteRepository.insertSongToFavorite(songOffline.getId());
        if (isSuccess) {
            mDetailAlbumView.showAddSongFavoriteSuccess();
        } else {
            mDetailAlbumView.showAddSongFavoriteFail();
        }
    }
}
