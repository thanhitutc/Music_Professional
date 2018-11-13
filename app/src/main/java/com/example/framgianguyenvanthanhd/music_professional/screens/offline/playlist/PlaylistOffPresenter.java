package com.example.framgianguyenvanthanhd.music_professional.screens.offline.playlist;

import com.example.framgianguyenvanthanhd.music_professional.data.model.PlaylistOffline;
import com.example.framgianguyenvanthanhd.music_professional.data.repository.PlaylistOfflineRepository;
import com.example.framgianguyenvanthanhd.music_professional.data.repository.SongInPlaylistRepository;

import java.util.List;

/**
 * Created by MyPC on 28/01/2018.
 */

public class PlaylistOffPresenter implements ContractPlaylistOff.AlbumPresenter {
    private ContractPlaylistOff.AlbumView mAlbumView;
    private PlaylistOfflineRepository mPlaylistOfflineRepository;
    private SongInPlaylistRepository mSongInPlaylistRepository;

    public PlaylistOffPresenter(PlaylistOfflineRepository playlistOfflineRepository,
                                SongInPlaylistRepository songInPlaylistRepository) {
        mPlaylistOfflineRepository = playlistOfflineRepository;
        mSongInPlaylistRepository = songInPlaylistRepository;
    }

    @Override
    public void setView(ContractPlaylistOff.AlbumView view) {
        mAlbumView = view;
    }

    @Override
    public void onStart() {
        mAlbumView.setPresenter(this);
    }

    @Override
    public void onStop() {

    }

    @Override
    public void loadListAlbum() {
        List<PlaylistOffline> playlistOfflines = mPlaylistOfflineRepository.getListAlbum();
        if (playlistOfflines != null) {
            mAlbumView.showListAlbum(playlistOfflines);
        } else {
            mAlbumView.showNoListAlbum();
        }
    }

    @Override
    public void addNewAlbum(String nameAlbum) {
        boolean isSuccess = mPlaylistOfflineRepository.insertAlbum(nameAlbum);
        if (isSuccess) {
            PlaylistOffline playlistOffline = new PlaylistOffline(mPlaylistOfflineRepository.getLastIdInsert(), nameAlbum);
            mAlbumView.showNewAlbumSuccess(playlistOffline);
        } else {
            mAlbumView.showNewAlbumFail();
        }
    }

    @Override
    public void deleteAlbum(PlaylistOffline playlistOffline) {
        boolean isSuccessfully = mSongInPlaylistRepository.deleteAllSongOfAlbum(playlistOffline.getId());
        if (isSuccessfully) {
            boolean isSuccess = mPlaylistOfflineRepository.deleteAlbum(playlistOffline.getId());
            if (isSuccess) {
                mAlbumView.showDeleteAlbumSuccess(playlistOffline);
            } else {
                mAlbumView.showDeleteAlbumFail();
            }
        }
    }

    @Override
    public void updateNameAlbum(PlaylistOffline playlistOffline, String nameAlbum) {
        boolean isSuccess = mPlaylistOfflineRepository.updateName(playlistOffline.getId(), nameAlbum);
        if (isSuccess) {
            mAlbumView.showUpdateNameSuccess(playlistOffline, nameAlbum);
        } else {
            mAlbumView.showUpdateNameFail();
        }
    }
}
