package com.example.framgianguyenvanthanhd.music_professional.screens.offline.addsongtoplaylist;


import com.example.framgianguyenvanthanhd.music_professional.data.model.PlaylistOffline;
import com.example.framgianguyenvanthanhd.music_professional.data.repository.PlaylistOfflineRepository;
import com.example.framgianguyenvanthanhd.music_professional.data.repository.SongInPlaylistRepository;

import java.util.List;

/**
 * Created by MyPC on 26/01/2018.
 */

public class SongAddToPlaylistPresenter implements ContractSongAddToPlaylist.SongAddToAlbumPresenter {
    private PlaylistOfflineRepository mPlaylistOfflineRepository;
    private SongInPlaylistRepository mSongInPlaylistRepository;
    private ContractSongAddToPlaylist.SongAddToAlbumView mSongAddToAlbumView;
    private String mIdSong;

    public SongAddToPlaylistPresenter(PlaylistOfflineRepository repository,
                                      SongInPlaylistRepository songInPlaylistRepository, String idSong) {
        mPlaylistOfflineRepository = repository;
        mSongInPlaylistRepository = songInPlaylistRepository;
        mIdSong = idSong;
    }

    @Override
    public void loadListSong() {
        List<PlaylistOffline> playlistOfflines = mPlaylistOfflineRepository.getListAlbum();
        if (playlistOfflines.size() != 0) {
            mSongAddToAlbumView.showListAlbum(playlistOfflines);
        } else {
            mSongAddToAlbumView.showNoListAlbum();
        }
    }

    @Override
    public void addNewAlbum(String nameAlbum) {
        boolean isSuccessful = mPlaylistOfflineRepository.insertAlbum(nameAlbum);
        if (isSuccessful) {
            int idAlbum = mPlaylistOfflineRepository.getLastIdInsert();
            if (idAlbum != -1) {
                addSongToAlbum(idAlbum);
            }
        }
    }

    @Override
    public void addSongToAlbum(int idAlbum) {
        boolean isInsertSuccess = mSongInPlaylistRepository.insertSongToAlbum(idAlbum, mIdSong);
        if (isInsertSuccess) {
            mSongAddToAlbumView.showAddSongSuccess();
        } else {
            mSongAddToAlbumView.showAddSongFail();
        }
    }

    @Override
    public void setView(ContractSongAddToPlaylist.SongAddToAlbumView view) {
        mSongAddToAlbumView = view;
    }

    @Override
    public void onStart() {
        mSongAddToAlbumView.setPresenter(this);
    }

    @Override
    public void onStop() {

    }
}
